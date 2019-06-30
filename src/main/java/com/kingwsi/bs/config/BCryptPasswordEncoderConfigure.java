package com.kingwsi.bs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Description: []
 * Name: BCryptPasswordEncoderConfigure
 * Author: wangshu
 * Date: 2019/6/29 17:26
 */
@Configuration
public class BCryptPasswordEncoderConfigure {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
