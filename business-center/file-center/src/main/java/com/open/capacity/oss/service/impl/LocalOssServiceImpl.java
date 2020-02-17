package com.open.capacity.oss.service.impl;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.open.capacity.oss.dao.FileDao;
import com.open.capacity.oss.model.FileInfo;
import com.open.capacity.oss.model.FileType;
import com.open.capacity.oss.utils.FileUtil;

/**
 * 本地存储文件
 * 该实现文件服务只能部署一台 
 * 如多台机器nfs文件存储解决
 * @author pm 1280415703@qq.com
 * @date 2019/8/11 16:22
 */
  
@Service("localOssServiceImpl")
public class LocalOssServiceImpl extends AbstractFileService {

	@Autowired
	private FileDao fileDao;

	@Override
	protected FileDao getFileDao() {
		return fileDao;
	}

	@Value("${file.oss.prefix:xxxxx}")
	private String urlPrefix;
	/**
	 * 网关访问路径
	 */
	@Value("${file.oss.domain:xxxxx}")
	private String domain;
	
	@Value("${file.oss.path:xxxxx}")
	private String localFilePath;

	@Override
	protected FileType fileType() {
		return FileType.LOCAL;
	}

	@Override
	protected void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
		int index = fileInfo.getName().lastIndexOf(".");
		// 文件扩展名
		String fileSuffix = fileInfo.getName().substring(index);

		String suffix = "/" + LocalDate.now().toString().replace("-", "/") + "/" + fileInfo.getId() + fileSuffix;

		String path = localFilePath + suffix;
		String url = domain + urlPrefix + suffix;
		fileInfo.setPath(path);
		fileInfo.setUrl(url);

		FileUtil.saveFile(file, path);
	}

	@Override
	protected boolean deleteFile(FileInfo fileInfo) {
		return FileUtil.deleteFile(fileInfo.getPath());
	}
	 
}
