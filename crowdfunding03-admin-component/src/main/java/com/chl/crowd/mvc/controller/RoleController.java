package com.chl.crowd.mvc.controller;

import com.chl.crowd.entity.Role;
import com.chl.crowd.service.RoleService;
import com.chl.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleServices;


    @RequestMapping("/admin/to/role/info.json")
    @ResponseBody
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
            @RequestParam(value = "keyword" ,defaultValue = "")String keyword
    ){
        PageInfo<Role> pageInfo=  roleServices.getPageInfo(pageNum,pageSize,keyword);

        return ResultEntity.successWithData(pageInfo);
    }

}