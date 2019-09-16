package com.open.capacity.client.mapper;

import com.open.capacity.client.entity.GatewayRoutes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GatewayRoutesMapper {
    int deleteByPrimaryKey(String id);

    int insert(GatewayRoutes record);

    int insertSelective(GatewayRoutes record);

    GatewayRoutes selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GatewayRoutes record);

    int updateByPrimaryKey(GatewayRoutes record);

    List<GatewayRoutes> findAll(Map map);
}