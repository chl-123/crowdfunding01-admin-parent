package com.chl.crowd.mvc.controller;

import com.chl.crowd.constant.CrowdConstant;
import com.chl.crowd.entity.Admin;
import com.chl.crowd.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
  @Autowired AdminService adminService;

  @RequestMapping(value = "/crowdTest", method = RequestMethod.POST)
  @ResponseBody
  public Admin crowdTest() {
    String a = null;
    System.out.println(a.length());
    return adminService.GetAdmin(1);
  }

  @RequestMapping(value = "/test")
  public String test() {
    String a = null;
    System.out.println(a.length());
    return "target";
  }

  @RequestMapping(value = "admin/do/logout.html")
  public String doLogout(HttpSession session) {
    // 强制session失效
    session.invalidate();
    return "redirect:/admin/to/login/page.html";
  }

  @RequestMapping(value = "admin/do/login.html")
  public String doLogin(
      @Param("loginAcct") String loginAcct,
      @Param("userPswd") String userPswd,
      HttpSession session) {
    Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
    // 将返回成功的admin对象存入Session域中
    /* session.setMaxInactiveInterval(2);*/
    session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);

    return "redirect:/admin/to/main/page.html";
  }

  @RequestMapping("/admin/get/page.html")
  public String getPageInfo(
      @RequestParam(value = "keyword", defaultValue = "") String keyword,
      // 浏览器未提供 pageNum 时， 默认前往第一页
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
      // 浏览器未提供 pageSize 时， 默认每页显示 5 条记录
      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
      ModelMap modelMap) {
    PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
    modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
    return "admin-page";
  }

  @RequestMapping("admin/remove/{adminId}/{pageNum}/{keyword}.html")
  public String Remove(
      @PathVariable("adminId") Integer adminId,
      @PathVariable("pageNum") Integer pageNum,
      @PathVariable("keyword") String keyword) {
    // 执行删除
    adminService.remove(adminId);
    return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
  }

  @RequestMapping(value = "/admin/save.html", method = RequestMethod.POST)
  public String saveAdmin(Admin admin) {
    adminService.saveAdmin(admin);
    return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
  }

  @RequestMapping(value = "/admin/to/edit/page.html")
  public String toEditPage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {
    Admin admin = adminService.getAdminById(adminId);
    modelMap.addAttribute("admin", admin);
    return "admin-edit";
  }

  @RequestMapping(value = "/admin/update.html", method = RequestMethod.POST)
  public String updateAdmin(
      Admin admin,
      @RequestParam(value = "pageNum") Integer pageNum,
      @RequestParam("keyword") String keyword) {

    adminService.updateAdmin(admin);
    return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
  }
}
