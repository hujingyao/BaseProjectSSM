package com.ischoolbar.programmer.controller.admin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.admin.User;
import com.ischoolbar.programmer.service.admin.UserService;
import com.ischoolbar.programmer.util.CpachaUtil;

import sun.management.jmxremote.ConnectorBootstrap.DefaultValues;

@Controller
@RequestMapping("/system")
public class SystemController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/index" ,method = RequestMethod.GET)
	
	public ModelAndView index(ModelAndView model) {
		model.setViewName("system/index");
		model.addObject("name", "huzx");
		return model;
		
	}
	
//��ӭҳ��
	@RequestMapping(value = "/welcome" ,method = RequestMethod.GET)
	
	public ModelAndView welcome(ModelAndView model) {
		model.setViewName("system/welcome");
	
		return model;
		
	}
	
	
	
	@RequestMapping(value = "/login" ,method = RequestMethod.GET)
	
	public ModelAndView login(ModelAndView model) {
		model.setViewName("system/login");
		model.addObject("name", "huzx");
		return model;
		
	}
	@RequestMapping(value = "/login" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> loginAct(User user,String cpacha,HttpServletRequest request){
		
		System.out.println(user);
		Map<String,String> ret = new HashMap<String,String>();
		if(user == null) {
			
			ret.put("type","error");
			ret.put("msg","����д�û���Ϣ");
			return ret;
		}
	if(StringUtils.isEmpty(cpacha) ) {
				
				ret.put("type","error");
				ret.put("msg","��д��֤��");
				return ret;
			}
	if(StringUtils.isEmpty(user.getUsername()) ) {
		
		ret.put("type","error");
		ret.put("msg","��д�û���");
		return ret;
	}
	Object loginCpacha = request.getSession().getAttribute("loginCpacha");
	if(loginCpacha == null) {
		ret.put("type","error");
		ret.put("msg","�Ự��ʱ");
		return ret;
	}

	
	if(!cpacha.toUpperCase().equals(loginCpacha.toString().toUpperCase())) {
		ret.put("type","error");
		ret.put("msg","��֤�����");
		return ret;
		
	}
	
	User findbyUsername=userService.findByUsername(user.getUsername());
	if(findbyUsername == null) {
		ret.put("type","error");
		ret.put("msg","�û�������");
		return ret;
	}
	if(!user.getPassword().equals(findbyUsername.getPassword())) {
		ret.put("type","error");
		ret.put("msg","�������");
		return ret;
		
	}
	
	request.getSession().setAttribute("admin", findbyUsername);
	ret.put("type","success");
	ret.put("msg","��½�ɹ�");
		
		return ret;
	}
	
	
	
	
	@RequestMapping(value = "/get_cpacha" ,method = RequestMethod.GET)
	public void generateCpacha(
			
			@RequestParam(name="v1",required = false ,defaultValue = "4") Integer vcodeLen,
			@RequestParam(name="w" ,required = false,defaultValue = "100") Integer width,
			@RequestParam(name="h" ,required = false,defaultValue = "30") Integer height,
			@RequestParam(name="type" ,required = true,defaultValue = "loginCpacha") String cpachaType,
			
			HttpServletRequest request,HttpServletResponse response) {
		//���ö�ά�����
		CpachaUtil cpachaUtil = new CpachaUtil(vcodeLen, width, height);
		
		//��������ַ���
		
		
		String generatorVCode =cpachaUtil.generatorVCode();
		//���ַ���д��session����
		request.getSession().setAttribute(cpachaType, generatorVCode);
		System.out.println(cpachaType);
		//��Ҫ������true
	BufferedImage 	generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);	
		try {
			ImageIO.write(generatorRotateVCodeImage, "gif",response.getOutputStream());
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
	
		
	}
	
}
