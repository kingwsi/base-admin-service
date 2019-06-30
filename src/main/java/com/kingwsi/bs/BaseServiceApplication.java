package com.kingwsi.bs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BaseServiceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(BaseServiceApplication.class, args);
    }

}
