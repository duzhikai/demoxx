package util.login.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public class SheetDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * sheet页名称
     */
    private String sheetName;

    /**
     * 字段和别名，如果使用这个，properties 和 titles可以不用处理
     */
    private LinkedHashMap<String, String> fieldAndAlias;

    /**
     * 字段
     */
    private String[] properties;

    /**
     * 标题/别名
     */
    private String[] titles;

    /**
     * 列宽
     */
    private Integer[] columnWidth;

    /**
     * 数据集
     */
    private List<?> collection;

    public LinkedHashMap<String, String> getFieldAndAlias() {
        if (fieldAndAlias == null) {
            this.fieldAndAlias = new LinkedHashMap<>();
            for (int i = 0; i < properties.length; i++) {
                fieldAndAlias.put(properties[i], titles[i]);
            }
        }
        return fieldAndAlias;
    }

    public void setFieldAndAlias(LinkedHashMap<String, String> fieldAndAlias) {
        this.fieldAndAlias = fieldAndAlias;
    }
}