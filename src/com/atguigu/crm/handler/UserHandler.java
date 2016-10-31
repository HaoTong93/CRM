package com.atguigu.crm.handler;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crm.entity.User;

@Controller
@RequestMapping("user")
public class UserHandler {

	
	@RequestMapping("login")
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpSession session){
		//获取subject
		Subject currentUser = SecurityUtils.getSubject();
		
		if (!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            
            try {
                currentUser.login(token);
                
                User user = (User) currentUser.getPrincipals().getPrimaryPrincipal();
                session.setAttribute("user", user);
            } catch (Exception e) {
            	System.out.println(e.getMessage());
            }
            return "redirect:/index";
        }
		return "plan/list.jsp";
	}
}
