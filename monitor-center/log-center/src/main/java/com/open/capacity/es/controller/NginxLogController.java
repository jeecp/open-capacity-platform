package com.open.capacity.es.controller;

import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.capacity.common.web.PageResult;
import com.open.capacity.es.dao.NginxLogDao;
import com.open.capacity.es.entity.NinxLogDocument;
/**
 * nginx日志查询
 */
@RestController
public class NginxLogController {
	
	
	private static Logger log = LoggerFactory.getLogger(NginxLogController.class);
	private ObjectMapper objectMapper = new ObjectMapper();

	 
	@Autowired
	public NginxLogDao nginxLogDao;

	 
	@GetMapping(value="/nginxLog")
	public PageResult<NinxLogDocument>    getPage(@RequestParam Map<String, Object> params) throws JsonProcessingException {

		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		String searchKey = (String)params.get("searchKey");
		String searchValue = (String)params.get("searchValue");

		// 分页参数
		Pageable pageable = PageRequest.of(1, 1000 , Sort.Direction.DESC,"@timestamp");
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(builder).withPageable(pageable).build();
		Page<NinxLogDocument> result = nginxLogDao.search(query);



		return PageResult.<NinxLogDocument>builder().data(result.getContent()).code(0).count(1000L).build() ;
	}


//	@GetMapping(value="/nginxLog")
//	public PageResult getPage(@RequestParam Map<String, Object> params) throws JsonProcessingException {
//
//		List list = new ArrayList();
//		Script script = new Script("doc['lat.keyword'].values +';'+doc['lon.keyword'].values");
//		//用于统计每一项详细数据
//		TermsAggregationBuilder app = AggregationBuilders.terms("app").script(script).size(10000);
//		TopHitsAggregationBuilder top_score_hits = AggregationBuilders.topHits("top_score_hits").fetchSource(new String[]{"lat","lon"}, new String[]{}).size(1);
//
//		app.subAggregation(top_score_hits);
//
//		BoolQueryBuilder builder = QueryBuilders.boolQuery();
//		String searchKey = (String)params.get("searchKey");
//		String searchValue = (String)params.get("searchValue");
//
//		// 分页参数
//		Pageable pageable = PageRequest.of(1, 100);
//		SearchQuery query = new NativeSearchQueryBuilder().withQuery(builder).withPageable(pageable)
//				.addAggregation(app).build();
//		Page result = nginxLogDao.search(query);
//
//		StringTerms terms = ((AggregatedPageImpl) result).getAggregations().get("app");
//
//		for (Terms.Bucket entry : terms.getBuckets()) {
//			Map map = new HashMap();
//			String key = (String) entry.getKey();                    // bucket key
//			long docCount = entry.getDocCount();            // Doc count
//
//			log.info("key [{}], doc_count [{}]", key, docCount);
//			map.put("key",key);
//			map.put("docCount",docCount);
//
//			// We ask for top_hits for each bucket
//			TopHits topHits = entry.getAggregations().get("top_score_hits");
//
//			for (SearchHit hit : topHits.getHits().getHits()) {
//				log.info(" -> id [{}], _source [{}]", hit.getId(), hit.getSourceAsString());
//				map.put("lat",hit.getSourceAsMap().get("lat"));
//				map.put("lon",hit.getSourceAsMap().get("lon"));
//			}
//			list.add(map);
//		}
//
//		return PageResult.builder().data(list).code(0).count(100L).build() ;
//	}


}
