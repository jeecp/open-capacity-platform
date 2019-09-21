package com.open.capacity.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.open.capacity.common.exception.service.ServiceException;
import com.open.capacity.common.model.SysPermission;
import com.open.capacity.common.web.PageResult;
import com.open.capacity.user.dao.SysPermissionDao;
import com.open.capacity.user.dao.SysRolePermissionDao;
import com.open.capacity.user.service.SysPermissionService;

import lombok.extern.slf4j.Slf4j;

/**
* @author 作者 owen E-mail: 624191343@qq.com
* @version 创建时间：2017年11月12日 上午22:57:51
 */
@Slf4j
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

	@Autowired
	private SysPermissionDao sysPermissionDao;
	@Autowired
	private SysRolePermissionDao rolePermissionDao;

	@Override
	public Set<SysPermission> findByRoleIds(Set<Long> roleIds)  throws ServiceException {
		try {
			return rolePermissionDao.findPermissionsByRoleIds(roleIds);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Transactional
	@Override
	public void save(SysPermission sysPermission)  throws ServiceException {
		try {
			SysPermission permission = sysPermissionDao.findByPermission(sysPermission.getPermission());
			if (permission != null) {
				throw new IllegalArgumentException("权限标识已存在");
			}
			sysPermission.setCreateTime(new Date());
			sysPermission.setUpdateTime(sysPermission.getCreateTime());

			sysPermissionDao.insert(sysPermission);
			log.info("保存权限标识：{}", sysPermission);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Transactional
	@Override
	public void update(SysPermission sysPermission)  throws ServiceException {
		try {
			sysPermission.setUpdateTime(new Date());
			sysPermissionDao.updateByOps(sysPermission);
			log.info("修改权限标识：{}", sysPermission);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Transactional
	@Override
	public void delete(Long id)  throws ServiceException {
		try {
			SysPermission permission = sysPermissionDao.findById(id);
			if (permission == null) {
				throw new IllegalArgumentException("权限标识不存在");
			}

			sysPermissionDao.deleteOps(id);
			rolePermissionDao.deleteRolePermission(null, id);
			log.info("删除权限标识：{}", permission);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public PageResult<SysPermission> findPermissions(Map<String, Object> params)  throws ServiceException {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			if (MapUtils.getInteger(params, "page")!=null && MapUtils.getInteger(params, "limit")!=null)
				PageHelper.startPage(MapUtils.getInteger(params, "page"),MapUtils.getInteger(params, "limit"),true);
			List<SysPermission> list  = sysPermissionDao.findList(params);
			PageInfo<SysPermission> pageInfo = new PageInfo(list);

			return PageResult.<SysPermission>builder().data(pageInfo.getList()).code(0).count(pageInfo.getTotal()).build()  ;
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public void setAuthToRole(Long roleId, Set<Long> authIds)  throws ServiceException {
		try {
			rolePermissionDao.deleteRolePermission(roleId,null);

			if (!CollectionUtils.isEmpty(authIds)) {
				authIds.forEach(authId -> {
					rolePermissionDao.saveRolePermission(roleId, authId);
				});
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}
}
