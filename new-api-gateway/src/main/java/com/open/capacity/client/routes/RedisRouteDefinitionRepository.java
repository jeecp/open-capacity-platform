package com.open.capacity.client.routes;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    public static final String GATEWAY_ROUTES_PREFIX = "GETEWAY_ROUTES";

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Set<RouteDefinition> routeDefinitions = new HashSet<>();

    /**
     * 获取全部路由
     * @return
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        /**
         * 从redis 中 获取 全部路由,因为保存在redis ,mysql 中 频繁读取mysql 有可能会带来不必要的问题
         */
        
    	routeDefinitions.clear();
    	
        BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(GATEWAY_ROUTES_PREFIX);  
        
        
        Map<String ,String> map =  boundHashOperations.entries() ;
        
       
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();    
        while (it.hasNext()) {    
        	Map.Entry<String, String> entry = it.next();    
        	routeDefinitions.add(JSON.parseObject(entry.getValue(), RouteDefinition.class));
        	
        }    
     
        
        return Flux.fromIterable(routeDefinitions);
    }

    /**
     * 添加路由方法
     * @param route
     * @return
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(routeDefinition -> {
            routeDefinitions.add( routeDefinition );
            return Mono.empty();
        });
    }

    /**
     * 删除路由
     * @param routeId
     * @return
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            List<RouteDefinition> collect = routeDefinitions.stream().filter(
                    routeDefinition -> StringUtils.equals(routeDefinition.getId(), id)
            ).collect(Collectors.toList());
            routeDefinitions.removeAll(collect);
            return Mono.empty();
        });
    }
}
