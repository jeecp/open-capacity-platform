package com.open.capacity.oss.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.open.capacity.common.web.PageResult;
import com.open.capacity.oss.dao.FileDao;
import com.open.capacity.oss.model.FileInfo;
import com.open.capacity.oss.model.FileType;
import com.open.capacity.oss.task.TaskUnZipCall;
import com.open.capacity.oss.utils.FileUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 本地存储文件
 *
 * @author pm 1280415703@qq.com
 * @date 2019/8/11 16:22
 */
@Service("fileInfoServiceImpl")
public class LocalOssServiceImpl extends AbstractFileService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private FileDao fileInfoDao;

    @Value("${file.oss.domain}")
    private String domain;

    @Value("${file.oss.basePath}")
    private String basePath;

//    localpath
//    private String basePath = "/files";

    /**
     * 添加
     *
     * @param fileInfo
     */
//    @Override
    public int save(FileInfo fileInfo) {
        return fileInfoDao.save(fileInfo);
    }


    /**
     * 列表
     *
     * @param params
     * @return
     */
    @Override
    public PageResult<FileInfo> findList(Map<String, Object> params) {
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        if (MapUtils.getInteger(params, "page") != null && MapUtils.getInteger(params, "limit") != null) {
            PageHelper.startPage(MapUtils.getInteger(params, "page"), MapUtils.getInteger(params, "limit"), true);
        }
        List<FileInfo> list = fileInfoDao.findList(params);
        PageInfo<FileInfo> pageInfo = new PageInfo(list);

        return PageResult.<FileInfo>builder().data(pageInfo.getList()).code(0).count(pageInfo.getTotal()).build();
    }

    @Override
    protected FileDao getFileDao() {
        return fileInfoDao;
    }

    @Override
    protected FileType fileType() {
        return FileType.LOCAL;
    }

    @Override
    protected void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
        SimpleDateFormat dfYMD = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dfH = new SimpleDateFormat("HH");
        Date now = new Date();
        Long fileDirName = System.currentTimeMillis();
        String fileType = fileInfo.getName().substring(fileInfo.getName().lastIndexOf("."), fileInfo.getName().length());
        String pathDir = dfYMD.format(now) + File.separator + dfH.format(now) + File.separator + fileDirName + File.separator;
        String subPath = pathDir + fileDirName + fileType;
//        String subPath = dfYMD.format(now) + File.separator + dfH.format(now) + File.separator + fileDirName+ File.separator+UUID.randomUUID().toString().replaceAll("-", "") + fileType;
        fileInfo.setPathDir(basePath + File.separator + pathDir);
        fileInfo.setPath(basePath + File.separator + subPath);
        fileInfo.setUrl(domain + subPath);

        // 本地保存文件
        FileUtil.saveFile(file,fileInfo.getPath());
//        fileInfo.setUrl(domain +  subPath.replaceAll("/","-") );
    }

    @Override
    protected boolean deleteFile(FileInfo fileInfo) {
        // 删除
        fileInfoDao.delete(fileInfo.getId());
        return true;
    }


    @Override
    public void unZip(String filePath, String descDir) throws RuntimeException {
        //fileInfo
        final List<Future<Boolean>> futureList = new ArrayList<>();
        Charset gbk = Charset.forName("gbk");
        try {
            ZipFile zip = new ZipFile(filePath);
            AtomicReference<Integer> counts = new AtomicReference<>(0);
            for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                Future<Boolean> future = executorService.submit(new TaskUnZipCall(zip, entry, descDir));
                futureList.add(future);
                counts.getAndSet(counts.get() + 1);
            }
            futureList.forEach(resultFeatures -> {
                try {
                    Boolean isSuccess = resultFeatures.get(1, TimeUnit.SECONDS);
                    if (isSuccess != null && isSuccess) {
                        // 成功
                        counts.getAndSet(counts.get() - 1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            });
            if (counts.get() == 0) {
                // 全部解压完成
            }
            zip.close();
//            File file = new File(descDir);
//            file.delete();
        } catch (Exception e) {
        }
    }
}
