package com.open.capacity.uaa.service;

import java.util.List;
import java.util.Map;

import com.open.capacity.common.web.PageResult;
import com.open.capacity.common.web.Result;
import com.open.capacity.uaa.dto.SysClientDto;
import com.open.capacity.uaa.model.SysClient;

public interface SysClientService {

	
	SysClient getById(Long id) ;
	 

    Result saveOrUpdate(SysClientDto clientDto);

    void deleteClient(Long id);
    
    public PageResult<SysClient> listRoles(Map<String, Object> params);
    
    List<SysClient> findList(Map<String, Object> params) ;
    

	Result updateEnabled(Map<String, Object> params);
    
}
