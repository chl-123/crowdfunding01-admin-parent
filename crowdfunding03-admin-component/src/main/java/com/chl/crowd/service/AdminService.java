package com.chl.crowd.service;

import com.chl.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

public interface AdminService {
  public Admin GetAdmin(Integer id);

  Admin getAdminByLoginAcct(String loginAcct, String userPswd);

  PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

  void remove(Integer adminId);

  void saveAdmin(Admin admin);

  Admin getAdminById(Integer adminId);

  void updateAdmin(Admin admin);
}
