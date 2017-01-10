package cn.hyr.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.hyr.ssm.model.User;

/**
 * @category 登录认证拦截器
 * @author huangyueran
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	// 进入handler方法之前执行
	// 身份认证、身份授权
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("进行登录拦截");
		// 获取请求的url
		String url = request.getRequestURI();
		// 判断url是否是公开地址 (实际开发需要将公开地址配置在配置文件中)
		if (url.indexOf("login.action") > 0) {
			// 如果进行登录提交，放行
			return true;
		}

		// 判断session 是否登录 是否有用户信息
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null && user.getUsername() != null) {
			// 身份存在 放行
			return true;
		}

		// 登录认证失败 返回登录页面
		request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);

		// return true 表示通过，放行
		// return false 表示拦截 不向下执行
		return false;
	}

	// 进入Handler方法之后，返回modelAndView之前执行
	// 应用场景从modelAndView出发:将公用的模型数据(菜单导航)传到视图,同意指定视图/视图路劲
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("HandlerInterceptor1...postHandle");
	}

	// 执行Handler完成执行此方法
	// 统一的异常处理/日志处理
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
		System.out.println("HandlerInterceptor1...afterCompletion");
	}

}
