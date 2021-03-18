package com.chl.crowd.mvc.config;

import com.chl.crowd.entity.Admin;
import com.chl.crowd.entity.Role;
import com.chl.crowd.service.AdminService;
import com.chl.crowd.service.AuthService;
import com.chl.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class CrowdUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //根据账号名称查询Admin对象
        Admin admin=adminService.getAdminByLoginAcct(userName);
        //获取adminId
        Integer adminId=admin.getId();
        //根据adminId查询角色信息
        List<Role> assignedRoleList=roleService.geAssignedRole(adminId);
        //根据adminId查询权限信息
        List<String> assignedAuthNameByAdminId=authService.getAssignedAuthNameByAdminId(adminId);
        //创建集合对象用来存储GrantedAuthority
        List<GrantedAuthority> authorities=new ArrayList<>();
        //遍历assignedRoleList存入角色信息
        for (Role role: assignedRoleList) {
            String roleName="ROLE_"+role.getName();
            SimpleGrantedAuthority  simpleGrantedAuthority=new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }
        //遍历authnameList 存入权限信息
        for (String authName:assignedAuthNameByAdminId) {
            SimpleGrantedAuthority  simpleGrantedAuthority=new SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }
        //封装SecurityAdmin 对象
        SecurityAdmin securityAdmin=new SecurityAdmin(admin,authorities);
        return securityAdmin;
    }
}
