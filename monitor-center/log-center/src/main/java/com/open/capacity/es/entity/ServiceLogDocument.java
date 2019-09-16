package com.open.capacity.es.entity;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

/**
 * 日志对象
 *
 * @author zlt
 */
@Data
@Document(indexName = "ocp-log-*", type = "doc")
public class ServiceLogDocument {
    @Id
    private String id;
    private Date timestamp;
    private String message;
    private String threadName;
    private String serverPort;
    private String serverIp;
    private String logLevel;
    private String appName;
    private String classname;
    private String contextTraceId;
    private String currentTraceId;
}