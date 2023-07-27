package util.login.config;

import com.mongodb.MongoClientOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangguang.jackson
 * @date 2022/10/26 14:54
 **/
@Configuration
public class MongoConfiguration {

    @Bean
    public MongoClientOptions mongoOptions() {
        return MongoClientOptions.builder().maxConnectionIdleTime(3600000).socketKeepAlive(true).minConnectionsPerHost(1).build();
    }
}
