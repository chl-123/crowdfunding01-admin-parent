package com.chl.crowd.mvc.config;

import com.chl.crowd.exception.AccessForbiddenException;
import com.chl.crowd.exception.LoginAcctAlreadyInUseException;
import com.chl.crowd.exception.LoginFailedException;
import com.chl.crowd.util.CrowdUtil;
import com.chl.crowd.util.ResultEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 表示当前类是一个基于注解的异常处理
@ControllerAdvice
public class CrowdExceptionResolver {
  // 此方法解决账户重复问题
  @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
  public ModelAndView resolveLoginAcctAlreadyInUseException(
      // 实际捕获到的异常类型
      LoginAcctAlreadyInUseException exception,
      // 当前请求对象
      HttpServletRequest request,
      // 当前响应对象
      HttpServletResponse response)
      throws Exception {

    String viewName = "admin-add";
    return commonResolve(exception, request, response, viewName);
  }
  // 此方法解决登录异常
  @ExceptionHandler(value = LoginFailedException.class)
  public ModelAndView resolveLoginFailedException(
      // 实际捕获到的异常类型
      LoginFailedException exception,
      // 当前请求对象
      HttpServletRequest request,
      // 当前响应对象
      HttpServletResponse response)
      throws Exception {
    String viewName = "admin-login";
    return commonResolve(exception, request, response, viewName);
  }
  // 此方法解决登录拦截异常
  @ExceptionHandler(value = AccessForbiddenException.class)
  public ModelAndView resolveAccessForbiddenException(
      // 实际捕获到的异常类型
      AccessForbiddenException exception,
      // 当前请求对象
      HttpServletRequest request,
      // 当前响应对象
      HttpServletResponse response)
      throws Exception {
    String viewName = "admin-login";
    return commonResolve(exception, request, response, viewName);
  }

  public ModelAndView commonResolve(

      // 实际捕获到的异常类型
      Exception exception,
      // 当前请求对象
      HttpServletRequest request,
      // 当前响应对象
      HttpServletResponse response,
      String viewName)
      throws Exception {
    boolean judgeResult = CrowdUtil.judgeRequestType(request);
    // 如果是Ajax请求
    if (judgeResult) {
      // 创建ResultEntity
      ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
      // 创建Gson对象
      Gson gson = new Gson();
      // 将ResultEntity对象转化为JSON字符串
      String json = gson.toJson(resultEntity);
      response.getWriter().write(json);
      return null;
    }
    // 如果不是Ajax请求则创建ModelAndview对象
    ModelAndView modelAndView = new ModelAndView();
    // 将Exception对象存入模型
    modelAndView.addObject("exception", exception);
    // 设置视图名称
    modelAndView.setViewName(viewName);
    return modelAndView;
  }
}
