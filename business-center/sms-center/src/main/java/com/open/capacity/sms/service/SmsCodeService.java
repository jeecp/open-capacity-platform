package com.open.capacity.sms.service;

import com.open.capacity.sms.model.SmsCode;

/**
 * * 程序名 : AliyunSmsConfig
 * 建立日期: 2018-07-09
 * 作者 : someday
 * 模块 : 短信中心
 * 描述 : 短信验证码service
 * 备注 : version20180709001
 * <p>
 * 修改历史
 * 序号 	       日期 		        修改人 		         修改原因
 */
public interface SmsCodeService {

	SmsCode generateCode(String phone);

	String matcheCodeAndGetPhone(String key, String code, Boolean delete, Integer second);
}
