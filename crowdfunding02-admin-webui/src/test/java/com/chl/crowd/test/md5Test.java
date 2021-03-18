package com.chl.crowd.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class md5Test {
  @Test
  public void testMD5() {

    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
    System.out.println(bCryptPasswordEncoder.encode("123456789"));
  }
}
