package com.open.capacity.generator.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.capacity.common.web.PageResult;
import com.open.capacity.generator.service.SysGeneratorService;

import io.swagger.annotations.Api;

/**
 * @Author: [dawei QQ:64738479]
 * @Date: [2019-04-25 21:48]
 * @Description: [ ]
 * @Version: [1.0.1]
 * @Copy: [com.zzg]
 */
@RestController
@Api(tags = "代码生成器")
@RequestMapping("/generator")
public class GeneratorController {
    @Autowired
    private SysGeneratorService sysGeneratorService;
    
	private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 列表
     * @throws JsonProcessingException 
     */
    @ResponseBody
    @RequestMapping("/list")
    public PageResult list(@RequestParam Map<String, Object> params) throws JsonProcessingException{
    	
        return sysGeneratorService.queryList(params);
    }

    /**
     * 生成代码
     */
    @RequestMapping("/code")
    public void code(String tables, HttpServletResponse response) throws IOException {
    	
    	
        byte[] data = sysGeneratorService.generatorCode(tables.split(","));
        
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"generator.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }


}
