package com.open.capacity.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.open.capacity.datasource.constant.DataSourceKey;
import com.open.capacity.datasource.util.DataSourceHolder;
import com.open.capacity.user.dao.SysLogDao;
import com.open.capacity.user.dao.SysUserDao;

@RestController
public class TestController {
	
	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
	private SysLogDao sysLogDao;
	
	@GetMapping("/user")
	public List user(){
		return  sysUserDao.findList(null);
	}
	
	
	
	@GetMapping("/log")
	public List log(){
		DataSourceHolder.setDataSourceKey(DataSourceKey.log);
		return  sysLogDao.findList(null);
	}
}
