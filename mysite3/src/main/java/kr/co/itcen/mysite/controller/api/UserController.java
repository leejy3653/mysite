package kr.co.itcen.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.itcen.mysite.service.UserService;

@Controller("userApiController")
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/checkemail")
	public String checkEmail(@RequestParam(value = "email", required = true, defaultValue = "") String email) {
		Boolean exist = userService.existUser(email);
		
		return exist ? "exists" : "not exist"; //http://localhost:8088/mysite3/api/user/checkemail 여기들어가서 확인
		}
}
