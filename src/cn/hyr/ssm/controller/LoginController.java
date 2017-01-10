package cn.hyr.ssm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hyr.ssm.model.User;
import cn.hyr.ssm.service.UserService;

/**
 * @category 登录
 * @author huangyueran
 *
 */
@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	/** 登录 */
	@RequestMapping("/login")
	public String login(HttpSession session, String username, String password) throws Exception {
		// 调用service进行登录身份验证
		// ...

		User user = userService.findUserById(10);
		session.setAttribute("user", user);

		return "redirect:/items/queryItems.action";
	}

	/** 退出 */
	@RequestMapping("/logout")
	public String logout(HttpSession session) throws Exception {
		// 清除session
		session.invalidate(); // session 过期

		return "redirect:/items/queryItems.action";
	}

}
