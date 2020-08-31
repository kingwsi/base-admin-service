package com.kingwsi.bs.common.handler;

import com.kingwsi.bs.common.security.TokenUtil;
import com.kingwsi.bs.entity.resource.Resource;
import com.kingwsi.bs.service.AccessControlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description: 自定义资源过滤
 * Name: CorsConfig
 * Author: wangshu
 * Date: 2020/04/17 18:34
 */
@Component
@Slf4j
public class CustomResourceFilterHandler {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final AccessControlService accessControlService;

    public CustomResourceFilterHandler(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        List<Resource> resources = accessControlService.listByUserAndMethod(TokenUtil.getUserId(), request.getMethod());
        for (Resource r : resources) {
            if (antPathMatcher.match(r.getUri(), request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }
}
