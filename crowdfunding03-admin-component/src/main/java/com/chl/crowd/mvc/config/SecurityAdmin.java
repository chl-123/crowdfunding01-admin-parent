package com.chl.crowd.mvc.config;

import com.chl.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
/*
* 考虑到User对象中仅仅包含账号和密码，为了能够获取到原始的Admin对象，专门创建这个类对User类进行扩展
* */
public class SecurityAdmin extends User {
    private static final long SerialVersionUID=1;
    //原始Admin对象，包含Admin的全部属性
    private Admin originalAdmin;
    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {
        super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(),authorities);
        this.originalAdmin=originalAdmin;
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }
}
