package util.login.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangguang.jackson
 * @date 2023/2/10 14:40
 **/
@Configuration
@Data
public class AlgorithmEffectConfiguration {

    @Value("${algorithm.effect}")
    private String effectUrl;
}
