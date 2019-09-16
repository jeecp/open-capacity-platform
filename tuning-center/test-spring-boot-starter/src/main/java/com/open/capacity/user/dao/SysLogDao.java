package com.open.capacity.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* @author 作者 owen E-mail: 624191343@qq.com
* @version 创建时间：2017年11月12日 上午22:57:51
 * 用户管理
 */
@Mapper
public interface SysLogDao {

 
	@Select("select * from sys_log t  ")
	List<Map> findList(Map<String, Object> params);
	
	 
}
