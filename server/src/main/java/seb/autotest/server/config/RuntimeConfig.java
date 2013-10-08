package seb.autotest.server.config;


import org.springframework.context.annotation.*;
import seb.autotest.server.SuiteService;

/**
 * @author hani
 *         Date: 6/6/12
 *         Time: 11:59 AM
 */
@Configuration
@ComponentScan(basePackageClasses = {SuiteService.class})
public class RuntimeConfig {

}