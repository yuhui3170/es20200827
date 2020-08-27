package com.dianzi.es.dep.controller;

import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.R;
import com.dianzi.es.dep.entity.ApplyinfoEntity;
import com.dianzi.es.dep.service.ApplyinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * 设备申请表
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@RestController
@RequestMapping("dep/file")
public class FileController {
    /**
     * 图片上传
     * @param file
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/picture/upload")
    @ResponseBody
    public String upload(@RequestBody MultipartFile file, HttpServletRequest request, HttpServletResponse response)throws Exception{
        System.out.println("上传图片是否为空："+file.isEmpty());
        if(file != null){
            String path =null;// 文件路径
            String imgType=null;//图片类型
            String  fileName = file.getOriginalFilename();// 原文件名称
            // 判断图片类型
            imgType=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            if(imgType!=null){
                if("GIF".equals(imgType.toUpperCase()) || "PNG".equals(imgType.toUpperCase()) || "JPG".equals(imgType.toUpperCase())){
                    // 项目在容器中实际发布运行的根路径
                    /* String realPath = request.getSession().getServletContext().getRealPath("/");*/
                    String realPath = "E:\\file\\images\\";
                    // 自定义的文件名称
                    String trueFileName=String.valueOf(System.currentTimeMillis())+fileName;
                    // 设置图片存放的路径
                    path=realPath+trueFileName;
                    System.out.println("图片的存放路径为"+path);
                    // 转存文件到指定路径
                    file.transferTo(new File(path)); // 转存而不是写出
                    System.out.println("文件成功上传到指定目录下");
                }else{
                    System.out.println("请上传GIF、PNG或者JPG格式的文件");
                }
            }else{
                System.out.println("文件类型为空");
            }
            return path.substring(3);
        }else{
            System.out.println("没有找到相对应的文件");
        }
        System.out.println("文件上传的原名称为:"+file.getOriginalFilename());

        return "";
    }

    /**
     * 图片下载
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/picture/download")
    @ResponseBody
    public void download(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //http://localhost:88/api/dep/file/picture/download?path=images%2F15972224135801.jpg
        String path= request.getParameter("path");
        System.out.println(path);
        FileInputStream fis = null;
        response.setContentType("image/gif");
        try {
            OutputStream out = response.getOutputStream();
            File file = new File("E:\\"+path);
            fis = new FileInputStream(file);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
