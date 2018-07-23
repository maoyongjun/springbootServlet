package org.foxconn.bootstrapTest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("org.foxconn.bootstrapTest.dao")
public class DothillLabelAppliation  extends SpringBootServletInitializer{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DothillLabelAppliation.class); 
		
	}
	public static void main(String[] args) {
		SpringApplication.run(DothillLabelAppliation.class, args);
	}
}
	