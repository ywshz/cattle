package org.yws.cattle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by ywszjut on 15/7/24.
 */
@EnableAutoConfiguration
@ComponentScan
@EnableJpaRepositories(basePackages = "org.yws.cattle.repositories")
@EntityScan(basePackages = "org.yws.cattle.models")
public class WebUIApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebUIApplication.class, args);

    }
}
