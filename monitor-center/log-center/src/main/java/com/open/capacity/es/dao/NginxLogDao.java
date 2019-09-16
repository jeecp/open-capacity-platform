package com.open.capacity.es.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.open.capacity.es.entity.NinxLogDocument;

/**
 * @author zlt
 */
@Repository
public interface NginxLogDao extends ElasticsearchRepository<NinxLogDocument, String> {

}