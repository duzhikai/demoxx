package util.login.common.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author xuliangliang.1995
 */
public class JsonUtils {

    static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        OBJECT_MAPPER.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OBJECT_MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        OBJECT_MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // 将 Long 类型转化成 String 类型，防止精度丢失
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);

        module.addDeserializer(String.class, new StringTrimDeserializer());

        OBJECT_MAPPER.registerModule(module);

    }

    /**
     * marshal
     * @return json
     */
    public static String marshal(Object object) {
        String json = null;
        try {
            json = OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("JsonUtils marshal {} fail .", object.getClass(), e);
        }
        return json;
    }

    /**
     * unmarshal
     * @return instance of target class
     */
    public static <T> T unmarshal(String json, Class<T> targetClass) {
        T instance = null;
        try {
            instance = OBJECT_MAPPER.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            logger.error("JsonUtils unmarshal {} fail .", targetClass.getName(), e);
        }
        return instance;
    }


    /**
     * unmarshal
     * @return list of target class
     */
    public static <T> List<T> unmarshalToList(String json, Class<T> targetClass) {
        Object list = null;
        try {
            list = OBJECT_MAPPER.readValue(json,
                    OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, targetClass));
        } catch (JsonProcessingException e) {
            logger.error("JsonUtils unmarshal {} list fail .", targetClass.getName(), e);
        }
        return list != null ? (List<T>) list : null;
    }


    /**
     * get {@link ObjectMapper}
     * @return objectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }


    static class StringTrimDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            return StringUtils.trim(parser.getValueAsString());
        }
    }
}
