package com.egeroo.composer;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.egeroo.composer.core.storage.StorageProperties;

@SpringBootApplication
@ComponentScan({"com.egeroo.composer"})
@EnableAutoConfiguration (exclude = { DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class })
@EnableConfigurationProperties(StorageProperties.class)
public class ComposerMediaApplication extends SpringBootServletInitializer{

	public static void main(String[] args)throws IOException {
		@SuppressWarnings("unused")
		ConfigurableApplicationContext ctx = SpringApplication.run(ComposerMediaApplication.class, args);
	}

}
