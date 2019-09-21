package com.open.capacity.oss.controller;

import com.open.capacity.common.web.PageResult;
import com.open.capacity.common.web.Result;
import com.open.capacity.log.annotation.LogAnnotation;
import com.open.capacity.oss.config.OssServiceFactory;
import com.open.capacity.oss.model.FileInfo;
import com.open.capacity.oss.model.FileType;
import com.open.capacity.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 本地附件管理
 *
 * @author pm 1280415703@qq.com
 * @date 2019/8/11 16:15
 */
@RestController
@RequestMapping("fileinfo")
@Api(tags = "附件管理")
public class FileInfoController {


    @Autowired
    private OssServiceFactory fileServiceFactory;

    @Value("${file.oss.basePath}")
    private String basePath;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public PageResult list(@RequestParam Map<String, Object> params) {
        return fileServiceFactory.getFileService(FileType.LOCAL.toString()).findList(params);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "unzip", value = "是否解压 1 解压 ，0不解压，默认0", defaultValue = "0", type = "Int"),
            @ApiImplicitParam(name = "file", value = "文件", defaultValue = "0", type = "MultipartFile"),
    })
    public Result save(@RequestPart("file") MultipartFile file, @RequestParam(value = "unzip", defaultValue = "0") Integer unzip) throws Exception {
        String fileType = FileType.LOCAL.toString();
        FileService fileService = fileServiceFactory.getFileService(fileType);

        FileInfo fileInfo = fileService.upload(file);
        if (unzip == 1) {
            Map<String, Object> map = new HashMap<>(3);
            map.put("fileId", fileInfo.getId());
            map.put("filePath", fileInfo.getPath());
            map.put("filePathDir", fileInfo.getPathDir());

        fileService.unZip(fileInfo.getPath(), fileInfo.getPathDir());
//            activemqFileProducer.sendCalls(map);
        }
        return Result.succeed(fileInfo, "上传成功");
    }


    //    @PostMapping("/files/{date}/{hour}/{timestamp}/{filename}")
//    public String downloadFile(HttpServletRequest request, HttpServletResponse response,@PathVariable String date,@PathVariable String hour,@PathVariable String timestamp,@PathVariable String filename) throws UnsupportedEncodingException {
    @ApiOperation(notes = "下载文件", value = "下载文件")
    @PostMapping("/files")
    public String downloadFile(HttpServletResponse response, String url, String filename) throws UnsupportedEncodingException {

//        String url = date +File.separator+ hour +File.separator+ timestamp +File.separator+ filename;
//        File file = new File(basePath + url);
        File file = new File(url);
        // 如果文件名存在，则进行下载
        if (file.exists()) {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
//        }
        return null;
    }


    /**
     * 修改
     */
//    @RequestMapping("/update")
////    @PreAuthorize("hasAnyAuthority('generator:sysroleuser:update')")
//    public Result update(@RequestBody FileInfo fileInfo) {
////        fileInfoService.update(fileInfo);
//        String fileType = FileType.LOCAL.toString();
//        FileService fileService = fileServiceFactory.getFileService(fileType);
//        fileService.update(fileInfo);
//        return Result.succeed("修改成功");
//    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
//        fileInfoService.delete(id);

        try {
            FileInfo fileInfo = fileServiceFactory.getFileService(FileType.LOCAL.toString()).getById(id);
            if (fileInfo != null) {
                FileService fileService = fileServiceFactory.getFileService(fileInfo.getSource());
                fileService.delete(fileInfo);
            }
            return Result.succeed("操作成功");
        } catch (Exception ex) {
            return Result.failed("操作失败");
        }


//        return Result.succeed("删除成功");
    }


    /**
     * layui富文本文件自定义上传
     *
     * @param file
     * @return
     * @throws Exception
     */
    @LogAnnotation(module = "file-center", recordRequestParam = false)
    @PostMapping("/files/layui")
    public Map<String, Object> uploadLayui(@RequestParam("file") MultipartFile file)
            throws Exception {

        String fileType = FileType.LOCAL.toString();
        FileService fileService = fileServiceFactory.getFileService(fileType);

        FileInfo fileInfo = fileService.upload(file);

        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        Map<String, Object> data = new HashMap<>();
        data.put("src", fileInfo.getUrl());
        map.put("data", data);

        return map;
    }





}

