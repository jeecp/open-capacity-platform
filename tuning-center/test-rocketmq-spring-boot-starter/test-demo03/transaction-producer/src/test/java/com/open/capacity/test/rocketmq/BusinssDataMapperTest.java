package com.open.capacity.test.rocketmq;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.open.capacity.MqCenter;
import com.open.capacity.message.dao.BusinssDataMapper;
import com.open.capacity.message.model.BusinessData;

/**
 * @SpringBootTest 会加载整个spring容器
 * BusinssDataMapper 需要运行在spring容器环境中
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { MqCenter.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 配置启动类
public class BusinssDataMapperTest {

	@Autowired
	private BusinssDataMapper businssDataMapper ;
	
	
	@Test
	public void testSave() {
		businssDataMapper.save(BusinessData.builder().id(UUID.randomUUID().toString()).username("gitgeek").sex("1").build());
	}
}
