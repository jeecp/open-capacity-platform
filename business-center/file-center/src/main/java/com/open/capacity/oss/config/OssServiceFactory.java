package com.open.capacity.oss.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.open.capacity.oss.model.FileType;
import com.open.capacity.oss.service.FileService;
import com.open.capacity.oss.service.impl.FastDfsOssServiceImpl;
import com.open.capacity.oss.service.impl.LocalOssServiceImpl;

 
/**
 * @author 作者 owen E-mail: 624191343@qq.com
 * @version 创建时间：2017年11月12日 上午22:57:51
 * FileService工厂<br>
 * 将各个实现类放入map
*/
@Configuration
public class OssServiceFactory {

	private Map<FileType, FileService> map = new HashMap<>();

 
	@Autowired
	private FileService aliyunOssServiceImpl;
	
	@Autowired
	private FileService qiniuOssServiceImpl;
	@Autowired
	private FileService fastDfsOssServiceImpl;
	
	@Autowired
	private FileService localOssServiceImpl;

	@PostConstruct
	public void init() {
		map.put(FileType.ALIYUN,  aliyunOssServiceImpl);
		map.put(FileType.QINIU ,  qiniuOssServiceImpl);
		map.put(FileType.LOCAL ,  localOssServiceImpl);
		map.put(FileType.FASTDFS ,  fastDfsOssServiceImpl);
	}

	public FileService getFileService(String fileType) {
	   if (StringUtils.isBlank(fileType)) {
			return localOssServiceImpl;
		}

		return map.get(FileType.valueOf(fileType));
	}
}
