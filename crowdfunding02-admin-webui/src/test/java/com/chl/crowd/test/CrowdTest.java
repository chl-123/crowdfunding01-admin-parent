package com.chl.crowd.test;

import com.chl.crowd.mapper.AdminMapper;
import com.chl.crowd.service.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring-*.xml"})
public class CrowdTest {
  @Autowired AdminService adminService;

  @Test
  public void test() {
    System.out.println("dd");

    System.out.println(adminService.GetAdmin(1));
    // 获取日志记录对象
    Logger logger = LoggerFactory.getLogger(CrowdTest.class);
    // 按照 Debug 级别打印日志
    logger.debug(adminService.GetAdmin(1).toString());
  }

  @Test
  public void test1() {
    ApplicationContext ac = new ClassPathXmlApplicationContext("config/spring-mybatis.xml");
    AdminMapper userMapper = (AdminMapper) ac.getBean("adminMapper");

    System.out.println(userMapper.selectByPrimaryKey(1).toString());
  }
}
