package com.open.capacity.es.entity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

/**
 * ELK收集mysql慢查询日志数据
 */
@Data
@Document(indexName = "mysql-slowlog-*", type = "doc")
public class SqlSlowDocument {
    @Id
    private String id;
    private Date timestamp;
    private String query;
    private String user;
    private String clientip;
    private Float query_time;
    private Float lock_time;
    private Long rows_sent;
    private Long rows_examined;
}