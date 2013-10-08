package seb.autotest.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author hani
 *         Date: 10/4/12
 *         Time: 8:10 AM
 */
@Configuration
@ComponentScan(basePackages = {"ch.ralscha.extdirectspring"})
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

  @Bean
  public ch.ralscha.extdirectspring.controller.Configuration extConfig() {
    ch.ralscha.extdirectspring.controller.Configuration config = new ch.ralscha.extdirectspring.controller.Configuration();
    config.setMaxRetries(0);
    config.setSendExceptionMessage(true);
    return config;
  }
}
