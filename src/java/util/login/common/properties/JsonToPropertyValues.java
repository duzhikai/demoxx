package util.login.common.properties;

import com.bytedance.cqc.common.utils.jackson.JsonUtils;
import com.bytedance.cqc.common.utils.logic.Null2Default;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author xuliangliang.1995
 */
public class JsonToPropertyValues {

    public static PropertyValues toPropertyValues(JsonNode jsonNode) {
        return toPropertyValues(new MutablePropertyValues(), null, jsonNode);
    }

    public static PropertyValues toPropertyValues(String json) {
        try {
            JsonNode jsonNode = JsonUtils.getObjectMapper().readTree(json);
            return toPropertyValues(new MutablePropertyValues(), null, jsonNode);
        } catch (Exception e) {
            // ignore
            return null;
        }
    }

    private static MutablePropertyValues toPropertyValues(MutablePropertyValues propertyValues, String prefix, JsonNode jsonNode) {
        boolean isVal = jsonNode.isValueNode();
        boolean isArr = jsonNode.isArray();

        if (isVal) {
            propertyValues.addPropertyValue(prefix, jsonNode.asText());
        } else if (isArr) {
            int arrSize = jsonNode.size();
            if (arrSize > 0) {
                for (int i = 0; i < arrSize; i++) {
                    toPropertyValues(propertyValues, Null2Default.of(prefix) + "[" + i + "]", jsonNode.get(i));
                }
            } else {
                propertyValues.addPropertyValue(prefix, new ArrayList<>());
            }
        } else {
            Iterator<String> fieldIterator = jsonNode.fieldNames();
            while (fieldIterator.hasNext()) {
                String field = fieldIterator.next();
                String str = prefix != null ? prefix + "." : "";
                toPropertyValues(propertyValues, str + field, jsonNode.get(field));
            }
        }
        return propertyValues;
    }


}
