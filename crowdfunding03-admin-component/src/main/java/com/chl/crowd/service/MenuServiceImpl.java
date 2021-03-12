package com.chl.crowd.service;

import com.chl.crowd.entity.Menu;
import com.chl.crowd.entity.MenuExample;
import com.chl.crowd.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService{
    @Autowired
    MenuMapper menuMapper;
    @Override
    public Menu getAll() {
        //声明一个变量来存储找到的根节点
        Menu root=null;
        //查询全部Menu对象
        List<Menu> menuList=menuMapper.selectByExample(new MenuExample());
        //创建Map对象来存储ID和Menu对象的关系便于查找父节点
        Map<Integer,Menu> menuMap=new HashMap<>();
        //遍历menuList填充menuMap
        for (Menu menu: menuList) {
            Integer id=menu.getId();
            menuMap.put(id,menu);
        }

        //再次遍历menulist查找根节点，组装父子节点
        for (Menu menu:menuList
             ) {
            //获取当前对象的pid
            Integer pid=menu.getPid();
            //如果pid的为null则为根节点
            if (pid == null) {
                root=menu;
                //如果当前节点是根节点，那么可以肯定没有父节点，不必继续执行
                continue;
            }

            //9.如果 pid 不为 null， 说明当前节点有父节点， 那么可以根据 pid 到 menuMap 查找对应的 Menu 对象
            Menu father=menuMap.get(pid);
            // 10.将当前节点存入父节点的 children 集合
            father.getChildren().add(menu);
        }
        return root;
    }
}
