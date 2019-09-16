package com.open.capacity.es.entity;


import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

/**
 * nginx日志对象
 *
 */
@Data
@Document(indexName = "kafka_nginxlogs-*", type = "doc" )
public class NinxLogDocument {
    @Id
    private String id;
    public Map geoip  ;
    
}