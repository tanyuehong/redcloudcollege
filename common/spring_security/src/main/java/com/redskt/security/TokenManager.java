package com.redskt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redskt.security.entity.User;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * <p>
 * token管理
 * </p>
 *
 * @author qy
 * @since 2019-11-08
 */
@Component
public class TokenManager {

    private long tokenExpiration = 24*60*60*1000;
    private static final String  tokenSignKey = "ukc8BDbRizUDaY6pZFfWus2jZWLPHO";

    public String createToken(String username) {
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public String getUserFromToken(String token) {
        String user = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return user;
    }

    /**
     * 根据token字符串获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        try {
            return  Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(jwtToken).getBody().getSubject();
        } catch (Exception e) {
            return "";
        }
    }

    public void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }

}
