package com.redskt.security;

import com.redskt.commonutils.ResponseUtil;
import com.redskt.commonutils.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 未授权的统一处理方式
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ResponseUtil.out(response, R.error("登录验证失败"));
    }
}
