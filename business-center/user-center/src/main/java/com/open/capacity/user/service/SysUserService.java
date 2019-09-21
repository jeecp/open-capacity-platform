package com.open.capacity.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.open.capacity.common.auth.details.LoginAppUser;
import com.open.capacity.common.exception.service.ServiceException;
import com.open.capacity.common.model.SysRole;
import com.open.capacity.common.model.SysUser;
import com.open.capacity.common.web.PageResult;
import com.open.capacity.common.web.Result;
import com.open.capacity.user.model.SysUserExcel;

/**
* @author 作者 owen E-mail: 624191343@qq.com
* @version 创建时间：2017年11月12日 上午22:57:51
 */
public interface SysUserService {

	/**
	 * 添加用户
	 * @param sysUser
	 */
	void addSysUser(SysUser sysUser)  throws ServiceException;

	/**
	 * 修改用户
	 * @param sysUser
	 */
	SysUser updateSysUser(SysUser sysUser)  throws ServiceException;

	/**
	 * 获取UserDetails对象
	 * @param username
	 * @return
	 */
	LoginAppUser findByUsername(String username)  throws ServiceException;

	LoginAppUser findByMobile(String mobile)  throws ServiceException;


	SysUser findById(Long id)  throws ServiceException;

	/**
	 * 用户分配角色
	 * @param id
	 * @param roleIds
	 */
	void setRoleToUser(Long id, Set<Long> roleIds)  throws ServiceException;

	/**
	 * 更新密码
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	Result updatePassword(Long id, String oldPassword, String newPassword)  throws ServiceException;

	/**
	 * 用户列表
	 * @param params
	 * @return
	 */
	PageResult<SysUser> findUsers(Map<String, Object> params)  throws ServiceException;

	/**
	 * 用户角色列表
	 * @param userId
	 * @return
	 */
	Set<SysRole> findRolesByUserId(Long userId)  throws ServiceException;

	/**
	 * 状态变更
	 * @param params
	 * @return
	 */
	Result updateEnabled(Map<String, Object> params)  throws ServiceException;

	/**
	 * 更新
	 * @param sysUser
	 * @return
	 */
	Result saveOrUpdate(SysUser sysUser)  throws ServiceException;

	/**
	 * 查询全部用户
	 * @param params
	 * @return
	 */
	List<SysUserExcel> findAllUsers(Map<String, Object> params)  throws ServiceException;


}
