package com.baizhi.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("admin")
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;


    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> login(Admin admin, String inputCode, HttpServletRequest request){
        Map<String,Object> map=new HashMap<>();
        HttpSession session = request.getSession();
        String securityCode = (String) session.getAttribute("securityCode");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(), admin.getPassword());
        try {
            //adminService.login(admin, inputCode, request);
            if (securityCode.equalsIgnoreCase(inputCode)) {
                subject.login(token);
                map.put("status", true);
            } else {
                map.put("status", false);
                map.put("message", "验证码错误");
            }

        } catch (UnknownAccountException e) {
            e.printStackTrace();
            map.put("status",false);
            map.put("message", "用户名错误");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            map.put("status", false);
            map.put("message", "密码错误");
        }
        return map;
    }
    @RequestMapping("exit")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.jsp";
    }


   /* @RequestMapping("login")
    public String login(Admin admin,String inputCode, HttpServletRequest request){
        HttpSession session = request.getSession();
        String securityCode = (String) session.getAttribute("securityCode");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(),admin.getPassword());
        try {
            if(securityCode.equalsIgnoreCase(inputCode)){
                subject.login(token);
                return "redirect:/main.jsp";
            }else{
                throw new RuntimeException("验证码错误!!!");
            }

        } catch (UnknownAccountException e) {
            System.out.println("username is error");
            e.printStackTrace();
            return "redirect:/login/login.jsp";
        } catch (IncorrectCredentialsException e){
            System.out.println("password is error");
            e.printStackTrace();
            return "redirect:/login/login.jsp";
        }
    }

    @RequestMapping("exit")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.jsp";
    }*/



    @RequestMapping("sendMessage")
    /**
     * 发送短信验证码
     * @param phone
     */
    @ResponseBody
    public void sendMessage(HttpServletRequest req,String phone) throws Exception{
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        //final String accessKeyId = "LTAIy0EJEGb3thIj";//你的accessKeyId,参考本文档步骤2
        final String accessKeyId = "LTAI4Fu47GcdRBTN7QuVvJyE";
        //final String accessKeySecret = "OwEpFBpojC7dQ92KHaVRKpmttjKVmA";//你的accessKeySecret，参考本文档步骤2
        final String accessKeySecret = "enMuR7wdgAyARGMe9GW1QH0741G2Af";
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        //        13949121813,15732572971,13331025260,13527628084,
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("海贼王");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_176926086");
        //request.setTemplateCode("SMS_171117797");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        int i = (int) ((Math.random() * 9 + 1) * 100000);
        //request.setTemplateParam("{'code':'123456'}");
        request.setTemplateParam("{'code':"+i+"}");
        req.getSession().setAttribute("code", i+"");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println(sendSmsResponse.getCode());
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            System.out.println("请求成功");
        }
        /*Map<String,Object> map=new HashMap<>();
        map.put("status",true);
        return map;*/
    }

    @RequestMapping("save")
    @ResponseBody
    public Map<String,Object> save(Admin admin, String inputCode, HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            //获取session中验证码
            String sessionCode = (String) request.getSession().getAttribute("code");
            if (sessionCode.equals(inputCode)) {
                adminService.save(admin);
                map.put("status",true);
            }else{
                throw new RuntimeException("验证码错误!");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }



}
