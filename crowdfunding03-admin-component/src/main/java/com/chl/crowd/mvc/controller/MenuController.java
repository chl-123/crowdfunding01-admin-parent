package com.chl.crowd.mvc.controller;

import com.chl.crowd.entity.Menu;
import com.chl.crowd.service.MenuService;
import com.chl.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MenuController {
    @Autowired
    MenuService menuService;

    @ResponseBody
    @RequestMapping("/admin/menu/get/tree.json")
    public ResultEntity<Menu> getWholeTreeNew(){
        Menu root=menuService.getAll();
        return ResultEntity.successWithData(root);
    }
    @ResponseBody
    @RequestMapping("/admin/to/menu/save.json")
    public ResultEntity<String> saveMenu(Menu menu){
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();
    }
    @ResponseBody
    @RequestMapping("/admin/menu/to/update.json")
    public ResultEntity<String> updateMenu(Menu menu){
        menuService.updateMenu(menu);
        return ResultEntity.successWithoutData();
    }
    @ResponseBody
    @RequestMapping("/admin/menu/to/remove.json")
    public ResultEntity<String> removeMenu(@RequestParam("id")Integer id){
        menuService.removeMenu(id);
        return ResultEntity.successWithoutData();
    }

}
