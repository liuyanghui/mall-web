package com.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
/**
 * ******************  类说明  *********************
 * class       :  App
 * @author     :  hejinyun@umpay.com
 * @version    :  1.0  
 * description :  mall-web服务启动入口
 * @see        :                        
 * ***********************************************
 */
@SpringBootApplication(scanBasePackages = { "com.mall.*" }, 
exclude = {
		DataSourceAutoConfiguration.class, 
		HibernateJpaAutoConfiguration.class 
		})
public class App {

	public static void main(String[] args) {

		SpringApplication.run(App.class, args);
	}
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

	    return new EmbeddedServletContainerCustomizer() {
	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer container) {
	            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.vm");
	            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/404.vm");

	            container.addErrorPages(error404Page, error500Page);
	        }
	    };
	}
}
