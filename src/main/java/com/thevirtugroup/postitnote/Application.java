package com.thevirtugroup.postitnote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thevirtugroup.postitnote.config.MvcConfig;
import com.thevirtugroup.postitnote.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 */
@SpringBootApplication
@Import({MvcConfig.class, WebSecurityConfig.class})
@ComponentScan({
    "com.thevirtugroup.postitnote.services",
    "com.thevirtugroup.postitnote.security",
    "com.thevirtugroup.postitnote.repository",
    "com.thevirtugroup.postitnote.rest"
})
@EnableJpaAuditing
public class Application extends WebMvcConfigurerAdapter {

  public static void main(String[] args) throws Exception {
    System.setProperty("spring.devtools.restart.enabled", "true");

    SpringApplication.run(Application.class, args);
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

}
