package com.open.capacity.generator.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.open.capacity.common.web.PageResult;

/**
 * @Author: [dawei QQ:64738479]
 * @Date: [2019-04-25 21:48]
 * @Description: [ ]
 * @Version: [1.0.1]
 * @Copy: [com.zzg]
 */
@Service
public interface SysGeneratorService {

     PageResult queryList(Map<String, Object> map);

     int queryTotal(Map<String, Object> map);

     Map<String, String> queryTable(String tableName);

     List<Map<String, String>> queryColumns(String tableName);

     byte[] generatorCode(String[] tableNames);

}
