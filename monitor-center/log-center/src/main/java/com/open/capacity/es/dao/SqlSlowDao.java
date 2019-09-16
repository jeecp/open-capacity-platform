package com.open.capacity.es.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import com.open.capacity.es.entity.SqlSlowDocument;

/**
 * ELK收集mysql慢查询日志数据
 */
@Component
public interface SqlSlowDao extends ElasticsearchRepository<SqlSlowDocument, String> {

}