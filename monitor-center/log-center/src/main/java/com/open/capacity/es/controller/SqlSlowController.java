package com.open.capacity.es.controller;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.open.capacity.common.web.PageResult;
import com.open.capacity.es.dao.SqlSlowDao;
import com.open.capacity.es.entity.SqlSlowDocument;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * ELK收集mysql慢查询日志数据
 */
@RestController
public class SqlSlowController  {
    private static final String ES_PARAM_QUERY = "query";

    @Autowired
    public SqlSlowDao sqlSlowDao;

    @ApiOperation(value = "系统日志查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "分页起始位置", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "分页结束位置", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/slowQueryLog")
    public PageResult<SqlSlowDocument> getPage(@RequestParam Map<String, Object> params) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        String searchKey = (String) params.get("searchKey");
        String searchValue = (String) params.get("searchValue");
        if (StrUtil.isNotEmpty(searchKey) && StrUtil.isNotEmpty(searchValue)) {
            // 模糊查询
            builder.must(QueryBuilders.matchQuery(ES_PARAM_QUERY, searchValue));
        }

        
		Pageable pageable = PageRequest.of(MapUtils.getInteger(params, "page")-1, MapUtils.getInteger(params, "limit")
				//, Sort.Direction.DESC,"timestamp"
				);
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(builder).withPageable(pageable).build();
		Page<SqlSlowDocument> result = sqlSlowDao.search(query);
		return PageResult.<SqlSlowDocument>builder().data(result.getContent()).code(0).count(result.getTotalElements()).build();
    }
}