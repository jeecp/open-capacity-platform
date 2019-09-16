package com.open.capacity;

import com.open.capacity.config.AppDispatcherServletConfiguration;
import com.open.capacity.config.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 工作流中心
 *
 * @author persie
 * @date 2018/07/19
 */
@Import({
        ApplicationConfiguration.class,
        AppDispatcherServletConfiguration.class
})
@SpringBootApplication
        (exclude = {SecurityAutoConfiguration.class})
public class WorkCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkCenterApplication.class, args);
    }

}

