package com.cqu.controller;

import com.cqu.annotation.MyController;
import com.cqu.annotation.MyRequestMapping;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@MyController
public class FileController {

    @MyRequestMapping("/upload")
    public String upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{// 创建磁盘工厂 缓冲区和磁盘目录
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 设置文件上传的大小限制
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(1024 * 1024 * 10);// 最大上传10M
            upload.setHeaderEncoding("utf-8");// 设置编码格式
            List<FileItem> files = upload.parseRequest(request);
            if (files.size()==0){
                request.setAttribute("message","文件上传失败");
                return "upload.jsp";
            }
            for(FileItem fileItem :files){
                //获取上传的文件名
                String name = fileItem.getName();
                String path="E:\\FileStore\\";//假设上传路径固定
                // 定义一个新的文件来接收
                File newfile = new File(path + name);
                // 从缓冲区写入磁盘
                fileItem.write(newfile);
                fileItem.delete();
            }
            request.setAttribute("message","文件上传成功");
        }catch (Exception e){
            request.setAttribute("message","文件上传失败");
            e.printStackTrace();
        }
        finally {

        }
        return "upload.jsp";
    }
}
