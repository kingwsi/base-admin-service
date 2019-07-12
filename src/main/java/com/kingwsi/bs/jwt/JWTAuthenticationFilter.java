package com.kingwsi.bs.jwt;

import com.kingwsi.bs.entity.permission.Permission;
import com.kingwsi.bs.entity.role.Role;
import com.kingwsi.bs.service.AccessControlService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Description: token的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 * Name: JWTAuthenticationFilter
 * Author: wangshu
 * Date: 2019/6/29 23:26
 */

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private final AccessControlService accessControlService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AccessControlService accessControlService) {
        super(authenticationManager);
        this.accessControlService = accessControlService;
    }

    /**
     * 拦截请求，获取请求头信息，校验token合法性
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

    /**
     * 拦截请求，获取请求头并校验
     *
     * @param request
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String username = Optional.ofNullable(request.getHeader("Authorization"))
                .map(token -> Jwts.parser()
                        .setSigningKey("MyJwtSecret")
                        .parseClaimsJws(token.replace("Bearer ", ""))
                        .getBody()
                        .getSubject()).orElse(null);

        if (username != null) {
            List<Role> roleByUser = accessControlService.listRoleByUserName(username);
            List<Role> mustRole = accessControlService.listRoleByPermission(new Permission(request.getMethod(), request.getRequestURI()));
            if (Collections.disjoint(roleByUser, mustRole)){
                throw new RuntimeException("权限校验失败！");
            }
            return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
        }
        throw new RuntimeException("您无权访问！");
    }
}
