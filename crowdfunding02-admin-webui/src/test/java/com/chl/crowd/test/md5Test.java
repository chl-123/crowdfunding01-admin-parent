package com.chl.crowd.test;

import com.chl.crowd.util.CrowdUtil;
import org.junit.Test;

public class md5Test {
  @Test
  public void testMD5() {
    String source = "123456789";
    String encoder = CrowdUtil.md5(source);
    System.out.println(encoder);
  }
}
