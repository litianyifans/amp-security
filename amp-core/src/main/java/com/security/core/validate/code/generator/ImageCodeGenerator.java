package com.security.core.validate.code.generator;

import com.security.core.properties.SecurityProperties;
import com.security.core.validate.code.ImageCode;
import com.security.core.validate.code.generator.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;


public class ImageCodeGenerator implements ValidateCodeGenerator {


    @Autowired
    private SecurityProperties securityProperties ;

    @Override
    public ImageCode generator(ServletWebRequest request) {
        int width = ServletRequestUtils.getIntParameter(request.getRequest(),"width",securityProperties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(),"heigth",securityProperties.getCode().getImage().getHeight());
        int length = ServletRequestUtils.getIntParameter(request.getRequest(),"length",securityProperties.getCode().getImage().getLength());
        int lines = 3;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = img.getGraphics();

        // 设置背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 设置字体
        g.setFont(new Font("宋体", Font.BOLD, 20));


        String sendStr = "" ;
        // 随机数字
        Random r = new Random(new Date().getTime());
        for (int i = 0; i < length; i++) {
            int a = r.nextInt(10);
            int y = 10 + r.nextInt(20);// 10~30范围内的一个整数，作为y坐标
            sendStr += a ;
            Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            g.setColor(c);

            g.drawString("" + a, 5 + i * width / length, y);
        }

        // 干扰线
        for (int i = 0; i < lines; i++) {
            Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            g.setColor(c);
            g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
        }

        g.dispose();// 类似于流中的close()带动flush()---把数据刷到img对象当中

        //ImageIO.write(img, "JPG", response.getOutputStream());
        return  new ImageCode(img,sendStr,60) ;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
