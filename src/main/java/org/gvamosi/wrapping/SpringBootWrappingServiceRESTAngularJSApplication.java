package org.gvamosi.wrapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class })
public class SpringBootWrappingServiceRESTAngularJSApplication {

	public static void main(String[] args) 
	{
		SpringApplication.run(SpringBootWrappingServiceRESTAngularJSApplication.class, args);   
	}
}