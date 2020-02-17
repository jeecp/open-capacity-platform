package com.open.capacity.redis.prefix;

public interface KeyPrefix {
		
	public int expireSeconds();
	
	public String getPrefix();
	
}
