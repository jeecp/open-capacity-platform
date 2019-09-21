package com.open.capacity.uaa.client.service;

import java.util.Map;

public interface SysClientService {

	public Map getClient(String clientId);
	
	public void loadAllClientToCache() ;
}
