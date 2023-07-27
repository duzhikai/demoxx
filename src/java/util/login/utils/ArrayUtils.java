package util.login.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArrayUtils {

    public static List<Long> diffList(List<Long> list1, List<Long> list2) {
        Map<Long, Long> tempMap = list2.parallelStream().collect(Collectors.toMap(Function.identity(), Function.identity(), (oldData, newData) -> newData));
        return list1.parallelStream().filter(str -> !tempMap.containsKey(str)).collect(Collectors.toList());
    }

    public static List<Long> intersectionList(List<Long> list1, List<Long> list2) {
        return list1.stream().filter(list2::contains).collect(Collectors.toList());
    }

    /**
     * 按头list排序 取值
     * @param headerlist
     * @param dataList
     * @return
     */
    public static List<String[]> coverList(List<String> headerlist, List<Map<String, String>> dataList) {
        List<String[]> list = new ArrayList<>();
        try {
            String[] header;
            if (CollectionUtils.isNotEmpty(headerlist)) {
                header = headerlist.toArray(new String[0]);
            } else {
                header = dataList.get(0).keySet().toArray(new String[0]);
            }
            int len = header.length;
            for (Map<String, String> map : dataList) {
                String[] line = new String[len];
                for (int i = 0; i < len; i++) {
                    line[i] = map.get(header[i]);
                }
                list.add(line);
            }
        } catch (Exception e) {
            throw new RuntimeException("文件转换失败",e);
        }
        return list;
    }

}
