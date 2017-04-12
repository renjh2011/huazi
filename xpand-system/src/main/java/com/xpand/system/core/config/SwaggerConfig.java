package com.xpand.system.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = "com.xpand")
public class SwaggerConfig extends WebMvcConfigurationSupport {
	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()//选择哪些路径和API会生成document
				.apis(RequestHandlerSelectors.basePackage("com.xpand"))   //对所有api进行监控
                .paths(PathSelectors.any())             //对所有路径进行监控
				.build();
	}

	private ApiInfo apiInfo(){
		return new ApiInfoBuilder()
				.title("xpand")
				.version("1.0")
				.build();
	}
}