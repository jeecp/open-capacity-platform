package com.open.capacity.message.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.open.capacity.message.model.BusinessData;
/**
 * 模拟业务数据入库
 * 业务表business_data
 */
@Mapper
public interface BusinssDataMapper  {
	
	@Insert("insert into business_data(id , username , sex ) values(#{id}, #{username}, #{sex})")
	int save(BusinessData businessData);
	
	
	@Select("select * from business_data t where t.id = #{id}")
	BusinessData findById(String id);

}