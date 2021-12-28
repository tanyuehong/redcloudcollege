package com.redskt.security.config;

import com.redskt.security.DefaultPasswordEncoder;
import com.redskt.security.TokenLogoutHandler;
import com.redskt.security.TokenManager;
import com.redskt.security.UnauthorizedEntryPoint;
import com.redskt.security.filter.TokenAuthenticationFilter;
import com.redskt.security.filter.TokenLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class MultiHttpSecurityConfig {

    // 下面配置专门用来处理 "/admin/**" 模式的URL
    @Configuration
    @Order(1)
    public static class AdminSecurityConfig
            extends WebSecurityConfigurerAdapter {

        private UserDetailsService userDetailsService;
        private TokenManager tokenManager;
        private DefaultPasswordEncoder defaultPasswordEncoder;
        private RedisTemplate redisTemplate;


        @Autowired
        public AdminSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                                           TokenManager tokenManager, RedisTemplate redisTemplate) {
            this.userDetailsService = userDetailsService;
            this.defaultPasswordEncoder = defaultPasswordEncoder;
            this.tokenManager = tokenManager;
            this.redisTemplate = redisTemplate;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
               http.antMatcher("/admin/**").exceptionHandling()
                    .authenticationEntryPoint(new UnauthorizedEntryPoint())
                    .and().csrf().disable()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and().logout().logoutUrl("/usercenter/logout")
                    .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and()
                    .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
                    .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
        }

        /**
         * 密码处理
         * @param auth
         * @throws Exception
         */
        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
        }

        /**
         * 配置哪些请求不拦截
         * @param web
         * @throws Exception
         */
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/home/**",
                    "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
            );
        }
    }

    // 下面配置用来处理其它的URL
    @Configuration
    public static class HomeSecurityConfig extends WebSecurityConfigurerAdapter{

        private UserDetailsService userDetailsService;
        private TokenManager tokenManager;
        private DefaultPasswordEncoder defaultPasswordEncoder;
        private RedisTemplate redisTemplate;

        @Autowired
        public HomeSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                                   TokenManager tokenManager, RedisTemplate redisTemplate) {
            this.userDetailsService = userDetailsService;
            this.defaultPasswordEncoder = defaultPasswordEncoder;
            this.tokenManager = tokenManager;
            this.redisTemplate = redisTemplate;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
               http.exceptionHandling()
                    .authenticationEntryPoint(new UnauthorizedEntryPoint())
                    .and().csrf().disable()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and().logout().logoutUrl("/usercenter/logout")
                    .addLogoutHandler(new TokenLogoutHandler(tokenManager,redisTemplate)).and()
                    .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
                    .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
        }

        /**
         * 密码处理
         * @param auth
         * @throws Exception
         */
        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
        }

        /**
         * 配置哪些请求不拦截
         * @param web
         * @throws Exception
         */
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/home/**","/ucenter/**",
                    "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
            );
        }
    }
}
