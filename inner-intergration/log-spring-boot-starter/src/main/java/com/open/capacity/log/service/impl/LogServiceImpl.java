package com.open.capacity.log.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.open.capacity.common.model.SysLog;
import com.open.capacity.datasource.annotation.DataSource;
import com.open.capacity.log.dao.LogDao;
import com.open.capacity.log.service.LogService;

/**
 * @author owen
 * 切换数据源，存储log-center
 */
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDao logDao;

	@Async
	@Override
	@DataSource(name="log")
	public void save(SysLog log) {
		if (log.getCreateTime() == null) {
			log.setCreateTime(new Date());
		}
		if (log.getFlag() == null) {
			log.setFlag(Boolean.TRUE);
		}

		logDao.save(log);
	}

	 
}
