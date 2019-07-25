package com.my.xunwu;
  
import org.junit.runner.RunWith; 
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
 
@RunWith(SpringRunner.class)
@SpringBootTest
@Configuration
@ActiveProfiles("test")//使用application-test.properties测试，不用实际数据库
public class ApplicationTests {
  
}
 