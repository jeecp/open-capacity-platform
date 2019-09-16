package com.open.capacity.client.vo;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatewayRoutesVO {

    private String id;

    private String uri;

    private String predicates;

    private String filters;

    private Integer order;

    private String description;

    private Integer delFlag;

    private Date createTime;

    private Date updateTime;

}
