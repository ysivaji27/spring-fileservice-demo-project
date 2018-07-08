package de.exb.platform.cloud.fileservice.fileserviceeureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class FileserviceEurekaApplication {

	 public static void main(String[] args) {
	        new SpringApplicationBuilder(FileserviceEurekaApplication.class)
	         .web(true)
	         .run(args);
	    }
}
