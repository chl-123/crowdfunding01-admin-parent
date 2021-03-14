package com.chl.crowd.mvc.controller;

import com.chl.crowd.entity.Role;
import com.chl.crowd.service.AdminService;
import com.chl.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AssignController {
    @Autowired
    RoleService roleService;
    @Autowired
    AdminService adminService;

    @RequestMapping("/admin/to/assign/role/page.html")
    public String toAssignRolePage(
            @RequestParam("adminId")Integer adminId,
            ModelMap modelMap

    ){
        //查询已分配的角色
        List<Role> assignedRoleList=roleService.geAssignedRole(adminId);
        //查询未分配的角色
        List<Role> unAssignedRoleList=roleService.getUnAssignedRole(adminId);

        modelMap.addAttribute("assignedRoleList",assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList",unAssignedRoleList);
        return "assign-role";
    }
    @RequestMapping("/admin/do/role/assign.html")
    public String doAssign(
        @RequestParam("adminId")Integer adminId,
        @RequestParam("pageNum")Integer pageNum,
        @RequestParam("keyword")String keyword,
        // 我们允许用户在页面上取消所有已分配角色再提交表单， 所以可以不提供
        //roleIdList 请求参数
        // 设置 required=false 表示这个请求参数不是必须的
        @RequestParam(value = "roleIdList",required = false)List<Integer> roleIdList

    ){
        adminService.saveAdminRoleRelationship(adminId,roleIdList);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }







}