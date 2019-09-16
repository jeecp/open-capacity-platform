package com.open.capacity.client.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.open.capacity.client.dao.SysClientDao;
import com.open.capacity.common.constant.UaaConstant;

/**
 * @author 作者 owen E-mail: 624191343@qq.com
 * @version 创建时间：2018年4月5日 下午19:52:21 类说明
 * 查询应用绑定的资源权限
 */
@Service("sysClientService")
public class SysClientServiceImpl {

	 
    private Logger logger =LoggerFactory.getLogger(SysClientServiceImpl.class) ;

    @Autowired
    
    private RedisTemplate<String,Object> redisTemplate ;
    @Autowired
    private SysClientDao sysClientDao ;
    
	public Map getClient(String clientId){
		// 先从redis获取
		Map client ;
        String value = (String) redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).get(clientId);
        if (StringUtils.isBlank(value)) {
        	client = cacheAndGetClient(clientId);
        } else {
        	client = JSONObject.parseObject(value, Map.class);
        }
        return client ;
	}
	
	/**
     * 缓存client并返回client
     *
     * @param clientId
     * @return
     */
    private Map cacheAndGetClient(String clientId) {
        // 从数据库读取
    	Map client = null ;
        try {
        	client = sysClientDao.getClient(clientId);
            if (client != null) {
                // 写入redis缓存
                redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).put(clientId, JSONObject.toJSONString(client));
                logger.info("缓存clientId:{},{}", clientId, client);
            }
        }catch (Exception e){
            logger.info("clientId:{},{}", clientId, clientId );
        }

        return client;
    }
	
    /**
     * 将oauth_client_details全表刷入redis
     */
    public void loadAllClientToCache() {
        if (redisTemplate.hasKey(UaaConstant.CACHE_CLIENT_KEY)) {
            return;
        }
        logger.info("将oauth_client_details全表刷入redis");

        List<Map> list = sysClientDao.findAll();
        if (CollectionUtils.isEmpty(list)) {
            logger.error("oauth_client_details表数据为空，请检查");
            return;
        }

        for(Iterator<Map> it = list.iterator() ; it.hasNext();){
        	Map temp = it.next() ;
        	redisTemplate.boundHashOps(UaaConstant.CACHE_CLIENT_KEY).put(String.valueOf(temp.get("client_id")), JSONObject.toJSONString(temp));
        }
        
    }
 
}
