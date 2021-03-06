package com.chl.crowd.service;

import com.chl.crowd.constant.CrowdConstant;
import com.chl.crowd.entity.Admin;
import com.chl.crowd.entity.AdminExample;
import com.chl.crowd.exception.LoginAcctAlreadyInUseException;
import com.chl.crowd.exception.LoginFailedException;
import com.chl.crowd.mapper.AdminMapper;
import com.chl.crowd.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
  @Autowired private AdminMapper adminMapper;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  @Override
  public Admin GetAdmin(Integer id) {
    return adminMapper.selectByPrimaryKey(id);
  }

  @Override
  public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
    // 1.根据登录账户查询Admin对象
    // 创建AdminExample
    AdminExample adminExample = new AdminExample();
    // 创建Criteria
    AdminExample.Criteria criteria = adminExample.createCriteria();
    criteria.andLoginAcctEqualTo(loginAcct);
    // 调用adminMapper方法
    List<Admin> list = adminMapper.selectByExample(adminExample);
    // 2.判断list是否为空
    if (list == null || list.size() == 0) {
      throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_UserNameFAILED);
    }
    if (list.size() > 1) {
      throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
    }
    // 取出Admin对象，判断是否为空
    Admin admin = list.get(0);
    if (admin == null) {
      throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_UserNameFAILED);
    }
    // 取出密文并且判断
    String userPswdDB = admin.getUserPswd();
    //String userPswdForm = CrowdUtil.md5(userPswd);
    String userPswdForm=passwordEncoder.encode(userPswd);
    System.out.println(passwordEncoder.encode("123456789"));
    if (!Objects.equals(userPswdDB, userPswdForm)) {
      throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_PassWordFAILED);
    }

    return admin;
  }

  @Override
  public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
    // 1.调用PageHelper的静态方法开启分页功能
    PageHelper.startPage(pageNum, pageSize);
    // 2,调用Mapper方法查询Admin数据
    List<Admin> adminList = adminMapper.selectAdminByKeyword(keyword);
    // 为了方便页面使用，将adminList封装为PageInfo
    PageInfo<Admin> pageInfo = new PageInfo(adminList);
    return pageInfo;
  }

  @Override
  public void remove(Integer adminId) {
    adminMapper.deleteByPrimaryKey(adminId);
  }

  @Override
  public void saveAdmin(Admin admin) {
    // 生成当前系统时间
    String DateFormat = "yyyy-MM-dd HH:mm:ss";
    String createTime = CrowdUtil.createDate(DateFormat);
    admin.setCreateTime(createTime);
    // 对提交的密码加密
    String source = admin.getUserPswd();
    //String encoded = CrowdUtil.md5(source);
    String encoded=passwordEncoder.encode(source);
    System.out.println(passwordEncoder.encode("123456789"));
    admin.setUserPswd(encoded);
    try {
      adminMapper.insert(admin);
    } catch (Exception e) {
      e.printStackTrace();
      // 检测当前捕获的异常对象， 如果是 DuplicateKeyException 类型说明是账号重复导致的
      if (e instanceof DuplicateKeyException) {
        // 抛出自定义的 LoginAcctAlreadyInUseException 异常
        throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
      }
    }
  }

  @Override
  public Admin getAdminById(Integer adminId) {
    return adminMapper.selectByPrimaryKey(adminId);
  }

  @Override
  public void updateAdmin(Admin admin) {
    adminMapper.updateByPrimaryKeySelective(admin);
  }

  @Override
  public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
    //根据adminID删除旧的关联关系数据
    adminMapper.deleteOLdRelationship(adminId);
    if (roleIdList != null&&roleIdList.size()>0) {
      adminMapper.insertNewRelationship(adminId,roleIdList);
    }
  }
/*
* 该方法是在做SpringSecurity时加上的方法
* */
  @Override
  public Admin getAdminByLoginAcct(String userName) {
    AdminExample example=new AdminExample();
    AdminExample.Criteria criteria=example.createCriteria();
    criteria.andLoginAcctEqualTo(userName);
    List<Admin> list=adminMapper.selectByExample(example);
    return list.get(0);
  }


}
