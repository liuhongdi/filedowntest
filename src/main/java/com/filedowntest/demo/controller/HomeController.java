package com.filedowntest.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;


@RestController
@RequestMapping("/home")
public class HomeController {


    @GetMapping("/downexcel")
    public void downExcel(HttpServletResponse response, HttpServletRequest request) {

        try {
            //获取要下载的模板名称
            String fileName = "商品信息.xls";
            //设置要下载的文件的名称
            //response.setHeader("Content-disposition", "attachment;fileName=" + fileName);
            response.setHeader("Content-Disposition",
                    "attchement;filename=" + new String(fileName.getBytes("utf-8"),
                            "ISO8859-1"));
            //通知客服文件的MIME类型
            response.setContentType("application/msexcel;charset=UTF-8");
            //response.setContentType("application/msexcel");
            //获取文件的路径
            String filePath = "/data/file/html/tmb/商品信息.xls";
            FileInputStream input = new FileInputStream(filePath);
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[2048];
            int len;
            while ((len = input.read(b)) != -1) {
                out.write(b, 0, len);
            }
            //修正 Excel在“xxx.xlsx”中发现不可读取的内容。是否恢复此工作薄的内容？如果信任此工作簿的来源，请点击"是"
            response.setHeader("Content-Length", String.valueOf(input.getChannel().size()));
            input.close();
            //return Response.ok("应用导入模板下载完成");
        } catch (Exception ex) {
            //logger.error("getApplicationTemplate :", ex);
            //return Response.ok("应用导入模板下载失败！");
            System.out.println(ex);
        }

     }


}