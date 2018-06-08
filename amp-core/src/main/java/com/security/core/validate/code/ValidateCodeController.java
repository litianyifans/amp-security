package com.security.core.validate.code;



import com.security.core.validate.code.generator.ValidateCodeGenerator;
import com.security.core.validate.code.sender.DefaultSmsCodeSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class ValidateCodeController {

    private Logger logger = LoggerFactory.getLogger(ValidateCodeController.class) ;

    @Autowired
    private Map<String,ValidateCodeProcessor> validateCodeProcessors ;
   /*
    private final static  String  SESSION_KEY = "SESSION_KEY_IMAGE_CODE" ;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy() ;


    @Autowired
    private ValidateCodeGenerator imageCodeGenerator ;

    @Autowired
    private ValidateCodeGenerator smsCodeGenerator ;

    @Autowired
    private DefaultSmsCodeSender defaultSmsCodeSender ;

   @GetMapping("/code/image")
    public void createImageCode(HttpServletRequest request , HttpServletResponse response) throws IOException {
        ImageCode imageCode = (ImageCode) imageCodeGenerator.generator(new ServletWebRequest(request));
        logger.info(imageCode.getCode());
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream()) ;

    }

    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request , HttpServletResponse response) throws IOException, ServletRequestBindingException {
        ValidateCode smsCode =  smsCodeGenerator.generator(new ServletWebRequest(request)) ;
        logger.info(smsCode.getCode());
        String mobile = ServletRequestUtils.getRequiredStringParameter(request,"mobile");
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,smsCode);
        defaultSmsCodeSender.send(mobile,smsCode.getCode());
    }*/

    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request , HttpServletResponse response,@PathVariable String type) throws Exception {
        ValidateCodeProcessor validateCodeProcessor =  validateCodeProcessors.get(type+"CodeProcessor") ;
        validateCodeProcessor.create(new ServletWebRequest(request,response));
    }

}
