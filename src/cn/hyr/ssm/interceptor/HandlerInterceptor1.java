package cn.hyr.ssm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class HandlerInterceptor1 implements HandlerInterceptor {

	// 进入handler方法之前执行
	// 身份认证、身份授权
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("HandlerInterceptor1...preHandle");

		// return true 表示通过，放行
		// return false 表示拦截 不向下执行
		return true;
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
