package com.guo.controller;


import com.guo.util.qrCodeUtil;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;


@Controller
public class qrCodeController {

    @RequestMapping(value = "/create1")
    @ResponseBody
    public static Object createQR1(HttpServletRequest request,HttpServletResponse response) throws Exception {
        StringBuffer url = request.getRequestURL();
        try {
            OutputStream os = response.getOutputStream();//输出流
            //从配置文件读取需要生成二维码的连接
//            String requestUrl = GraphUtils.getProperties("requestUrl");
            //requestUrl:需要生成二维码的连接，logoPath：内嵌图片的路径，os：响应输出流，needCompress:是否压缩内嵌的图片
            qrCodeUtil.encode("郭青雨 班级：计183  学号：161842219", "/static/guo.jpg", os, true);
        } catch (Exception e) {
            e.printStackTrace();
        }return null;
    }
    @RequestMapping(value = "/create2")
    @ResponseBody
    public static Object createQR2(HttpServletRequest request,HttpServletResponse response) throws Exception {
        StringBuffer url = request.getRequestURL();
        try {
            OutputStream os = response.getOutputStream();
            //从配置文件读取需要生成二维码的连接
//            String requestUrl = GraphUtils.getProperties("requestUrl");
            //requestUrl:需要生成二维码的连接，logoPath：内嵌图片的路径，os：响应输出流，needCompress:是否压缩内嵌的图片
            qrCodeUtil.encode("许金龙 班级：计183  学号：18402110", "/static/xu.jpg", os, true);
        } catch (Exception e) {
            e.printStackTrace();
        }return null;
    }
    @RequestMapping(value = "/create3")
    @ResponseBody
    public static Object createQR3(HttpServletRequest request,HttpServletResponse response) throws Exception {
        StringBuffer url = request.getRequestURL();
        try {
            OutputStream os = response.getOutputStream();
            //从配置文件读取需要生成二维码的连接
//            String requestUrl = GraphUtils.getProperties("requestUrl");
            //requestUrl:需要生成二维码的连接，logoPath：内嵌图片的路径，os：响应输出流，needCompress:是否压缩内嵌的图片
            qrCodeUtil.encode("余俊杰 班级：计183  学号：18402114", "/static/yu.jpg", os, true);
        } catch (Exception e) {
            e.printStackTrace();
        }return null;
    }

}
