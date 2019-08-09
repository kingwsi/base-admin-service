package com.kingwsi.bs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Description: <br>
 * Comments Name: AuditorConfig<br>
 * Date: 2019/8/2 11:38<br>
 * Author: wangshu<br>
 */
@Configuration
public class AuditorConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.empty();
    }
}
