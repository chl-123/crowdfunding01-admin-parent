package com.chl.crowd.mvc.interceptor;

import com.chl.crowd.constant.CrowdConstant;
import com.chl.crowd.entity.Admin;
import com.chl.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    // 通过request对象获取Session对象
    HttpSession session = request.getSession();
    // 获取Session对象
    Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
    if (admin == null) {
      throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
    }
    return true;
  }
}
