package util.login.config;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;


/***
 * 定时任务开关配置
 * @date 2023/2/6 19:42
 **/
@Configuration
public class CronConfig {


    public static String environment = "basic";
    @Value("${system.config.environment}")
    public void setEnvironment(String environment){
        EnvConfig.environment = environment;
    }

    @Bean
    public CronParams cronParams() {
        Map<String, CronArray> options = Maps.newHashMap();
        String presetProperty = null;
        if ("xxxx".equals(EnvConfig.environment)) {
            presetProperty = ResourceUtil.readUtf8Str("config/cron/CronConfig_xxx.json");
        } else {
            presetProperty = ResourceUtil.readUtf8Str("config/cron/CronConfig.json");
        }
        JSONArray jsonArray = JSONUtil.parseArray(presetProperty);
        for (Object item : jsonArray) {
            CronOpts cronOpts = JSONUtil.toBean(JSONUtil.parseObj(item).toString(), CronOpts.class);
            for (String methodName : cronOpts.getMethodNameCollection()) {
                CronArray cronArray = new CronArray();
                cronArray.setModelName(cronOpts.getModelName());
                cronArray.setMethodName(methodName);
                cronArray.setFlag(cronOpts.getFlag());
                options.put(methodName, cronArray);
            }
        }
        CronParams cronParams = new CronParams();
        cronParams.setContent(options);
        return cronParams;
    }

    @Data
    public static class CronParams {
        private Map<String, CronArray> content;
    }

    @Data
    private static class CronOpts {
        private String modelName;
        private List<String> methodNameCollection;
        private Boolean flag;
    }

    @Data
    public static class CronArray {
        private String modelName;
        private String methodName;
        private Boolean flag;
    }
}
