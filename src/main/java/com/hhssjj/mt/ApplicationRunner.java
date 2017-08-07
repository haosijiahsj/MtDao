package com.hhssjj.mt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by 胡胜钧 on 8/5 0005.
 */

@ComponentScan("com.hhssjj")
@ImportResource({"classpath:applicationContext.xml"})
@SpringBootApplication
public class ApplicationRunner implements EmbeddedServletContainerCustomizer {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
        System.out.println("==========应用启动成功！==========");
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        int port = 80;
        configurableEmbeddedServletContainer.setPort(port);
    }
}
