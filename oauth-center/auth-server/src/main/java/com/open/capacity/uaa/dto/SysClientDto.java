package com.open.capacity.uaa.dto;

import java.util.List;
import java.util.Set;

import com.open.capacity.uaa.model.SysClient;

import lombok.Data;

@Data
public class SysClientDto extends SysClient {

    private static final long serialVersionUID = 1475637288060027265L;

    private List<Long> permissionIds;

    private Set<Long> serviceIds;

}
