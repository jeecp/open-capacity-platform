package com.open.capacity.message.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.open.capacity.message.model.MqTransactionBusinessLog;

@Mapper
public interface MqTransactionBusinssLogMapper  {
	
	@Insert("insert into mq_trancation_business_log(id , transaction_id , description  ) values(#{id}, #{transactionId}, #{description})")
	int save(MqTransactionBusinessLog mqTransactionBusinessLog);
	
	
	@Select("select * from mq_trancation_business_log t where t.id = #{id}")
	MqTransactionBusinessLog findById(String id);

}