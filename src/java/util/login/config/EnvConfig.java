package util.login.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Kone
 * @date 2022/9/2
 */
@Component
@Order(-2)
public class EnvConfig {
    public static final String DEV_ENV = "dev";
    public static String environment = "basic";
    public static String env;

    @Value("${spring.profiles.active}")
    public void setEnv(String env) {
        EnvConfig.env = env;
    }

    @Value("${system.config.environment}")
    public void setEnvironment(String environment){
        EnvConfig.environment = environment;
    }
}
