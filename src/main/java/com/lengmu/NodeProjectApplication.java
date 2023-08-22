package com.lengmu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class NodeProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(NodeProjectApplication.class, args);
    }

}
