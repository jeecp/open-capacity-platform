package com.open.capacity.uaa.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.open.capacity.common.constant.UaaConstant;
import com.open.capacity.common.web.PageResult;
import com.open.capacity.common.web.Result;
import com.open.capacity.uaa.dao.SysClientDao;
import com.open.capacity.uaa.dao.SysClientServiceDao;
import com.open.capacity.uaa.dto.SysClientDto;
import com.open.capacity.uaa.model.SysClient;
import com.open.capacity.uaa.service.SysClientService;

@Service
public class SysClientServiceImpl implements SysClientService {

    private static final Logger log = LoggerFactory.getLogger(SysClientServiceImpl.class);

     

    @Autowired
    private SysClientDao sysClientDao;

    @Autowired
    private SysClientServiceDao sysClientServiceDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService ;


     
    @Override
    public Result saveOrUpdate(SysClientDto clientDto) {
        clientDto.setClientSecret(passwordEncoder.encode(clientDto.getClientSecretStr()));

        if (clientDto.getId() != null) {// 修改
            sysClientDao.update(clientDto);
        } else {// 新增
        	SysClient r = sysClientDao.getClient(clientDto.getClientId());
            if (r != null) {
                return Result.failed(clientDto.getClientId()+"已存在");
            }
            sysClientDao.save(clientDto);
        }
        return Result.succeed("操作成功");
    }

     

    @Override
    @Transactional
    public void deleteClient(Long id) {
        sysClientDao.delete(id);

        sysClientServiceDao.delete(id,null);

        redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).delete(id) ;
        log.debug("删除应用id:{}", id);
    }

	@Override
	public PageResult<SysClient> listRoles(Map<String, Object> params) {

        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(MapUtils.getInteger(params, "page"),MapUtils.getInteger(params, "limit"),true);
        List<SysClient> list = sysClientDao.findList(params);
        PageInfo<SysClient> pageInfo = new PageInfo<>(list);
        return PageResult.<SysClient>builder().data(pageInfo.getList()).code(0).count(pageInfo.getTotal()).build()  ;

//		// TODO Auto-generated method stub
//		int total = clientDao.count(params);
//		List<Client> list = Collections.emptyList();
//
//		if (total > 0) {
//			PageUtil.pageParamConver(params, false);
//			list = clientDao.findList(params);
//
//		}
//		return PageResult.<Client>builder().data(list).code(0).count((long)total).build()  ;
	}
	public  SysClient getById(Long id) {
		return sysClientDao.getById(id);
	}

	@Override
	public List<SysClient> findList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return sysClientDao.findList(params);
	}

	@Override
	public List<SysClient> listByUserId(Long userId) {
		// TODO Auto-generated method stub
		return sysClientDao.listByUserId(userId);
	}



	@Override
	public Result updateEnabled(Map<String, Object> params) {
		Long id = MapUtils.getLong(params, "id");
		Boolean enabled = MapUtils.getBoolean(params, "status");

		SysClient client = sysClientDao.getById(id);
		if (client == null) {
			return Result.failed("应用不存在");
			//throw new IllegalArgumentException("用户不存在");
		}
		client.setStatus(enabled);

		int i = sysClientDao.update(client) ;
		
		
		ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(client.getClientId()); 
		
		if(enabled){
			redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).put(client.getClientId(), JSONObject.toJSONString(clientDetails));
		}else{
			redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).delete(client.getClientId()) ;
		}

		log.info("应用状态修改：{}", client);

		return i > 0 ? Result.succeed(client, "更新成功") : Result.failed("更新失败");
	}

}
