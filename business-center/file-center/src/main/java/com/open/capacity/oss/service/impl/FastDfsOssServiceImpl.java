package com.open.capacity.oss.service.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.open.capacity.oss.dao.FileDao;
import com.open.capacity.oss.model.FileInfo;
import com.open.capacity.oss.model.FileType;

import cn.hutool.core.util.StrUtil;
 
/**
 * fastdfs存储文件
 * @author pm 1280415703@qq.com
 * @date 2019/8/11 16:22
 */
@Import(FdfsClientConfig.class)
@Service("fastDfsOssServiceImpl")
public class FastDfsOssServiceImpl extends AbstractFileService {

	@Autowired
	private FileDao fileDao;
	
	@Autowired
    private FastFileStorageClient storageClient;

	@Override
	protected FileDao getFileDao() {
		return fileDao;
	}
 
	/**
	 * nginx安装了fastdfs的地址
	 */
	@Value("${fdfs.oss.domain:}")
	private String domain;
	 

	@Override
	protected FileType fileType() {
		return FileType.FASTDFS;
	}

	 @Override
     protected void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
         StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
         fileInfo.setUrl(domain+ storePath.getFullPath());
         fileInfo.setPath(storePath.getFullPath());
     }

     @Override
     protected boolean deleteFile(FileInfo fileInfo) {
         if (fileInfo != null && StrUtil.isNotEmpty(fileInfo.getPath())) {
             StorePath storePath = StorePath.parseFromUrl(fileInfo.getPath());
             storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
         }
         return true;
     } 

}
