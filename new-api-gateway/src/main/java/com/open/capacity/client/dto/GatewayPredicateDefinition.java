package com.open.capacity.client.dto;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 路由断言模型
 */
@EqualsAndHashCode
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatewayPredicateDefinition {
    private String name;
    private Map<String, String> args = new LinkedHashMap<>();
}
