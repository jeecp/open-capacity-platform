package com.open.capacity.log.dao;

import javax.sql.DataSource;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import com.open.capacity.common.model.SysLog;

/**
 * @author owen
 * @create 2017年7月2日
 * 保存日志
 * eureka-server配置不需要datasource,不会装配bean
 */
@Mapper
@ConditionalOnBean(DataSource.class)
public interface LogDao {

	@Insert("insert into sys_log(username, module, params, remark, flag, createTime) values(#{username}, #{module}, #{params}, #{remark}, #{flag}, #{createTime})")
	int save(SysLog log);

}
