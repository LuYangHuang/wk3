package com.cykj.controller;


import com.cykj.pojo.TKindergarten;
import com.cykj.pojo.TStaff;
import com.cykj.service.UserService;
import com.cykj.util.MD5Utils;
import com.cykj.util.VerifyCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    private UserService userServiceImpl;

    @RequestMapping("/login")
    public String toLogin() {
        return "staff-login";
    }

    @RequestMapping("/kinderLogin")
    public String toKinderLogin() {
        return "kindergarten-login";
    }

    @RequestMapping("/index")
    public String toIndex() {
        return "staff-main";
    }

    /**
     * 生成登录验证码
     **/
    @RequestMapping(value = "/Vcode", produces = {"application/json;charset=utf-8"})
    public void getVcode(HttpServletRequest request, HttpServletResponse response) {
        try {
            //声明验证码
            int width = 60;
            int height = 30;
            String data = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789abcdefghijklmnpqrstuvwxyz";    //随机字符字典，其中0，o，1，I 等难辨别的字符最好不要
            Random random = new Random();//随机类
            //1 创建图片数据缓存区域（核心类）
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//创建一个彩色的图片
            //2 获得画板(图片，ps图层)，绘画对象。
            Graphics g = image.getGraphics();
            //3 选择颜色，画矩形3，4步是画一个有内外边框的效果
//            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
            //4白色矩形
            g.setColor(Color.WHITE);
            g.fillRect(1, 1, width - 1, height - 1);

            /**1 提供缓存区域，为了存放4个随机字符，以便存入session */
            StringBuilder builder = new StringBuilder();

            //5 随机生成4个字符
            //设置字体颜色
            g.setFont(new Font("宋体", Font.BOLD & Font.ITALIC, 30));
            for (int i = 0; i < 4; i++) {
                //随机颜色
                g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));

                //随机字符
                int index = random.nextInt(data.length());
                String str = data.substring(index, index + 1);

                /**2 缓存*/
                builder.append(str);

                //写入
                g.drawString(str, (width / 6) * (i + 1), 20);
            }
            //给图中绘制噪音点，让图片不那么好辨别
            for (int j = 0, n = random.nextInt(100); j < n; j++) {
                g.setColor(Color.RED);
                g.fillRect(random.nextInt(width), random.nextInt(height), 1, 1);//随机噪音点
            }

            /**3 获得随机数据，并保存session*/
            String tempStr = builder.toString();
            request.getSession().setAttribute("vCode", tempStr);
            //.. 生成图片发送到浏览器 --相当于下载
            ImageIO.write(image, "jpg", response.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 工作人员登录验证
     **/
    @RequestMapping(value = "/staffLogin", produces = {"application/json;charset=utf-8"})
    public @ResponseBody
    String getStaffLogin(String account, String pwd, String vCode, HttpServletRequest req) throws Exception {
        //方法的参数需要和提交表单里面的参数一致
        String repath = null;
        System.out.println(account + "-" + pwd + "-" + vCode);
        System.out.println(MD5Utils.md5(pwd));
        //获取session中生成的验证码
        String verifyCode = (String) req.getSession().getAttribute("verifyCode");
        System.out.println("后台验证码：" + verifyCode);
//        if (vCode.equalsIgnoreCase(verifyCode)) {
        System.out.println("验证码验证通过");
        //md5加密
        TStaff TStaffUser = userServiceImpl.staffLogin(account, MD5Utils.md5(pwd));
        //session中存贮当前用户的对象
        req.getSession().setAttribute("loginTStaff", TStaffUser);

        System.out.println(TStaffUser.getName());
        if (null != TStaffUser) {
            repath = "success";
        } else {
            repath = "fail";
        }
//        }
//        else {
//            repath = "vcFail";
//        }

        return repath;
    }


    /**
     * 园长登录验证
     **/
    @RequestMapping(value = "/kinderLoginV", produces = {"application/json;charset=utf-8"})
    public @ResponseBody
    String getStaffLogin1(String account, String pwd, String vCode, HttpServletRequest req) throws Exception {
        //方法的参数需要和提交表单里面的参数一致
        String repath = null;
        System.out.println(account + "-" + pwd + "-" + vCode);
        System.out.println(MD5Utils.md5(pwd));
        //获取session中生成的验证码
        String verifyCode = (String) req.getSession().getAttribute("verifyCode");
        System.out.println("后台验证码：" + verifyCode);
//        if (vCode.equalsIgnoreCase(verifyCode)) {
        System.out.println("验证码验证通过");
        //md5加密
        TKindergarten tKindergarten = userServiceImpl.kinderLogin(account, MD5Utils.md5(pwd));
        //判断一下是否为禁用用户  ==2
        if (tKindergarten.getState() == 2) {
            repath = "stateStop";
        } else {
            //session中存贮当前用户的对象
            req.getSession().setAttribute("loginTStaff", tKindergarten);
            if (null != tKindergarten) {
                System.out.println(tKindergarten.getAccount());
                repath = "success";
            } else {
                repath = "fail";
            }
        }
//        }
//        else {
//            repath = "vcFail";
//        }
        return repath;
    }


    /**
     * 获取验证码图片，这个版本更加好看
     */
    @RequestMapping("/VerifyCode")
    public void getVerificationCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            int width = 200;
            int height = 69;
            BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //生成对应宽高的初始图片
            String randomText = VerifyCode.drawRandomText(width, height, verifyImg);
            //单独的一个类方法，出于代码复用考虑，进行了封装。
            //功能是生成验证码字符并加上噪点，干扰线，返回值为验证码字符
            request.getSession().setAttribute("verifyCode", randomText);
            response.setContentType("image/png");//必须设置响应内容类型为图片，否则前台不识别
            OutputStream os = response.getOutputStream(); //获取文件输出流
            ImageIO.write(verifyImg, "png", os);//输出图片流
            os.flush();
            os.close();//关闭流
        } catch (IOException e) {
//            this.logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
