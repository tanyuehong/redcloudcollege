package com.redskt.security.filter;

import com.redskt.commonutils.ResponseUtil;
import com.redskt.commonutils.R;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import com.redskt.security.TokenManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 访问过滤器
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    private final TokenManager tokenManager;
    private final RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(AuthenticationManager authManager, TokenManager tokenManager,RedisTemplate redisTemplate) {
        super(authManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        logger.info("================="+req.getRequestURI());
//        if(req.getRequestURI().indexOf("admin") == -1) {
//            chain.doFilter(req, res);
//            return;
//        }

        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(req);
        } catch (ExpiredJwtException expried) {
            logger.info("============用户登录过期哦！");
            // 这里是token过期了 需要重新登录
            if(req.getRequestURI().indexOf("commonRq") == -1 ) {
                ResponseUtil.out(res, R.LoginExpired());
            } else {
                ResponseUtil.out(res, R.ok());
            }
            return ;
        } catch (Exception e) {
            logger.info("=================5"+e.getLocalizedMessage());
        }
        if (authentication != null) {
            logger.info("=================6");
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            logger.info("============请求没有token哦！");
            // 这里是token过期了 需要重新登录
            if(req.getRequestURI().indexOf("commonRq") == -1 ) {
                ResponseUtil.out(res, R.LoginNoToken());
            } else {
                ResponseUtil.out(res, R.ok());
            }
            return;
        }
        logger.info("=================8");
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里
        String token = request.getHeader("token");
        logger.info("=================12"+token);
        if (token != null && !"".equals(token.trim())) {
            String userName = TokenManager.getUserFromToken(token);

            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for(String permissionValue : permissionValueList) {
                if(StringUtils.isEmpty(permissionValue)) continue;
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
                authorities.add(authority);
            }

            if (!StringUtils.isEmpty(userName)) {
                logger.info("=================3");
                return new UsernamePasswordAuthenticationToken(userName, token, authorities);
            }
            logger.info("=================2");
            return null;
        }
        logger.info("=================1");
        return null;
    }
}