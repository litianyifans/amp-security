package com.security.core.validate.code.processor;

import com.security.core.validate.code.ImageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;



@Component("imageCodeProcessor")
public class ImageCodeProcessor extends  AbstractValidateCodeProcessor<ImageCode> {


    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        ImageIO.write(imageCode.getImage(),"JPEG",request.getResponse().getOutputStream()) ;
    }
}
