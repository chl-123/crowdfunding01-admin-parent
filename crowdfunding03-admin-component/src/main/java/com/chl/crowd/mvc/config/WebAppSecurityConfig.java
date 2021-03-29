package com.chl.crowd.mvc.config;

import com.chl.crowd.constant.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//表示当前类是一个配置类
@Configuration
//启用web环境下的权限控制功能
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Component
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
   /* @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }*/
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        //临时使用内存版
        //builder.inMemoryAuthentication().withUser("chl").password("123456789").roles("ADMIN");
        builder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                ;

    }

    protected void configure(HttpSecurity security)throws Exception{
        security
                .authorizeRequests() // 对请求进行授权
                .antMatchers("/admin/to/login/page.html") // 针对登录页进行设置
                .permitAll() // 无条件访问
                .antMatchers("/bootstrap/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/crowd/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/css/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/fonts/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/img/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/jquery/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/layer/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/script/**") // 针对静态资源进行设置， 无条件访问
                .permitAll() // 针对静态资源进行设置， 无条件访问
                .antMatchers("/ztree/**") // 针对静态资源进行设置， 无条件访问
                .permitAll()
                .antMatchers("/admin/get/page.html")
                .hasRole("经理")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        request.setAttribute("exception", new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                        request.getRequestDispatcher("/WEB-INF/System-error.jsp").forward(request, response);
                    }
                })
                .and()
                .csrf()                               //防止跨站请求登录功能
                .disable()                            //关闭
                .formLogin()                          //开启表单登录功能
                .loginPage("/admin/to/login/page.html")
                .loginProcessingUrl("/security/do/login.html")//指定登录请求的地址
                .defaultSuccessUrl("/admin/to/main/page.html")//指定登录后前往的地址
                .usernameParameter("loginAcct")//账号的请求参数名称
                .passwordParameter("userPswd")//密码的请求参数名称
                .and()
                .logout()
                .logoutUrl("/security/do/logout.html")
                .logoutSuccessUrl("/admin/to/login/page.html")
                ;

    }

}
