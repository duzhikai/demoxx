package util.login.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class NlpHelper {

    /**
     * nlp 返回文本格式处理
     *
     * @param data
     * @param isSplit
     * @param isEscape
     * @return
     */
    public static JSONObject txtFormat(JSONObject data, Integer isSplit, Integer isEscape) {

        isSplit = Objects.isNull(isSplit) ? 0 : isSplit;
        isEscape = Objects.isNull(isEscape) ? 0 : isEscape;

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String[] txtArry = JSONUtil.parseObj(entry.getValue()).getStr("txt").split("!!!", -1);
            List<JSONObject> txt = Arrays.stream(txtArry).map(JSONUtil::parseObj).collect(Collectors.toList());
            if (isSplit > 0) {
                String[] originArry = JSONUtil.parseObj(entry.getValue()).getStr("origin").split("!!!", -1);
                List<JSONObject> origin = Arrays.stream(originArry).map(JSONUtil::parseObj).collect(Collectors.toList());

                for (int i = 0; i < txt.size() - 1; i++) {
                    JSONObject value = new JSONObject();
                    value.set("txt", isEscape > 0 ? txt.get(i) : txt.get(i));
                    value.set("origin", origin.size() - 1 > i ? origin.get(i) : "");
                    data.set(i + "", value);
                }
            } else {
                entry.setValue(txt);
            }
        }

        return data;
    }

    public static JSONArray txtFormat(JSONArray data, Integer isSplit, Integer isEscape) {

        isSplit = Objects.isNull(isSplit) ? 0 : isSplit;
        isEscape = Objects.isNull(isEscape) ? 0 : isEscape;
        JSONArray array = new JSONArray();
        for (JSONObject entry : data.jsonIter()) {
            String[] txt = entry.getStr("txt").split("!!!", -1);
            if (isSplit > 0) {
                String[] origin = entry.getStr("origin").split("!!!", -1);

                for (int i = 0; i < txt.length; i++) {
                    JSONObject value = new JSONObject();
                    value.set("txt", txt[i]);
                    value.set("origin", origin.length > i ? origin[i] : "");
                    array.add(value);
                }
            } else {
                JSONObject value = new JSONObject();
                value.set("txt", txt);
                array.add(value);
            }
        }

        return array;
    }

    /**
     * ner任务配置中定义类型转化(关系，属性，事件，快捷键) array->string
     *
     * @param param 创建任务时传入的数据
     * @return array
     */
    public static HashMap<String, String> nerTaskConfDefinitionConversiontype(HashMap<String, Object> param) {

        JSONArray relationship_definition_arr = ReqParamUtil.getParam(param, "relationship_definition", JSONArray.class);
        JSONArray events_definition_arr = ReqParamUtil.getParam(param, "events_definition", JSONArray.class);
        JSONArray attribute_definition_arr = ReqParamUtil.getParam(param, "attribute_definition", JSONArray.class);
        JSONArray hotkey_definition_arr = ReqParamUtil.getParam(param, "hotkey_definition", JSONArray.class);

        String relationship_definition = "";
        String events_definition = "";
        String attribute_definition = "";
        String hotkey_definition = "";

        if (CollectionUtils.isNotEmpty(relationship_definition_arr)) {
            for (JSONObject item : relationship_definition_arr.jsonIter()) {
                relationship_definition += item.getStr("value1") + "||Arg1:" + item.getStr("value2") + "|*|Arg2:" + item.getStr("value3") + "\n";
            }
        }

        if (CollectionUtils.isNotEmpty(events_definition_arr)) {
            for (JSONObject item : events_definition_arr.jsonIter()) {
                events_definition += item.getStr("value1") + "||" + item.getStr("value2") + "\n";
            }
        }

        if (CollectionUtils.isNotEmpty(attribute_definition_arr)) {
            for (JSONObject item : attribute_definition_arr.jsonIter()) {
                attribute_definition += item.getStr("value2") + "<" + item.getStr("value1") + ">:" + item.getStr("value3") + "\n";
            }
        }

        if (CollectionUtils.isNotEmpty(hotkey_definition_arr)) {
            for (JSONObject item : hotkey_definition_arr.jsonIter()) {
                hotkey_definition += item.getStr("value1") + "||" + item.getStr("value2") + "\n";
            }
        }

        HashMap<String, String> data = Maps.newHashMap();
        data.put("relationship_definition", relationship_definition);
        data.put("events_definition", events_definition);
        data.put("attribute_definition", attribute_definition);
        data.put("hotkey_definition", hotkey_definition);
        return data;
    }

    /**
     * ner 任务配置中定义类型恢复 string->array
     *
     * @param task_conf 任务配置
     * @return array
     */
    public static JSONObject nerTaskConfDefinitionRestoretype(JSONObject task_conf) {

        String relationship_definition_str = task_conf.getStr("relationship_definition");
        String events_definition_str = task_conf.getStr("events_definition");
        String attribute_definition_str = task_conf.getStr("attribute_definition");
        String hotkey_definition_str = task_conf.getStr("hotkey_definition");

        JSONArray relationship_definition = new JSONArray();
        if (StringUtils.isNotEmpty(relationship_definition_str)) {
            List<String> data = Splitter.on("\n").omitEmptyStrings().splitToList(relationship_definition_str);
            if (CollectionUtils.isNotEmpty(data)) {
                for (String str : data) {
                    String re_name = ToolUtils.strStr(str, "||", true);
                    String param_str = ToolUtils.strStr(str, "||", false);
                    List<String> param_arr = Splitter.on("|*|").omitEmptyStrings().splitToList(param_str);

                    String from = "";
                    String to = "";
                    if (param_arr.size() == 2) {
                        from = ToolUtils.strStr(param_arr.get(0), ":", false).replaceAll(":", "");
                        List<String> list = Splitter.on("Arg2:").omitEmptyStrings().splitToList(param_arr.get(1));
                        to = list.get(0);
                    }

                    JSONObject object = new JSONObject();
                    object.set("value1", re_name);
                    object.set("value2", from);
                    object.set("value3", to);
                    relationship_definition.add(object);
                }
            }
        }

        JSONArray events_definition = new JSONArray();
        if (StringUtils.isNotEmpty(events_definition_str)) {
            List<String> data = Splitter.on("\n").omitEmptyStrings().splitToList(events_definition_str);
            if (CollectionUtils.isNotEmpty(data)) {
                for (String str : data) {
                    List<String> param_arr = Splitter.on("||").omitEmptyStrings().splitToList(str);

                    String re_name = param_arr.get(0);
                    String events = param_arr.get(1);

                    JSONObject object = new JSONObject();
                    object.set("value1", re_name);
                    object.set("value2", events);
                    events_definition.add(object);
                }
            }
        }

        JSONArray attribute_definition = new JSONArray();
        if (StringUtils.isNotEmpty(attribute_definition_str)) {
            List<String> data = Splitter.on("\n").omitEmptyStrings().splitToList(attribute_definition_str);
            if (CollectionUtils.isNotEmpty(data)) {
                for (String str : data) {
                    String atr_name = ToolUtils.strStr(str, "<", true);
                    String lastr_str = ToolUtils.strStr(str, "<", false).replaceAll("<", "");
                    String shiti = ToolUtils.strStr(lastr_str, ">", true);
                    String value = ToolUtils.strStr(str, ":", false).replaceAll(":", "");
                    JSONObject object = new JSONObject();
                    object.set("value1", shiti);
                    object.set("value2", atr_name);
                    object.set("value3", value);
                    attribute_definition.add(object);
                }
            }
        }

        JSONArray hotkey_definition = new JSONArray();
        if (StringUtils.isNotEmpty(hotkey_definition_str)) {
            List<String> data = Splitter.on("\n").omitEmptyStrings().splitToList(hotkey_definition_str);
            if (CollectionUtils.isNotEmpty(data)) {
                for (String str : data) {
                    List<String> param_arr = Splitter.on("||").omitEmptyStrings().splitToList(str);

                    String hotkey = param_arr.get(0);
                    String definition = param_arr.get(1);

                    JSONObject object = new JSONObject();
                    object.set("value1", hotkey);
                    object.set("value2", definition);
                    hotkey_definition.add(object);
                }
            }

        }

        task_conf.set("relationship_definition", relationship_definition);
        task_conf.set("events_definition", events_definition);
        task_conf.set("attribute_definition", attribute_definition);
        task_conf.set("hotkey_definition", hotkey_definition);
        return task_conf;
    }


    /**
     * 解析标注数据中实体个数与关系个数
     * {"global":{},"entities":[["T1","Loc",[[211,218]]],["T2","Loc",[[211,220]]]],
     * "relations":[["R1","OrgBased_In",[["Arg1","T1"],["Arg2","T2"]]],["R2","OrgBased_In",[["Arg1","T2"],["Arg2","T1"]]]]}
     */
    public static Pair<Integer, Integer> parseEntityAndRelations(String markJson) {
        if (StringUtils.isEmpty(markJson)) {
            return Pair.of(0, 0);
        }
        JSONObject mark = JSONUtil.parseObj(markJson);
        return parseEntityAndRelations(mark);
    }

    public static Pair<Integer, Integer> parseEntityAndRelations(JSONObject mark) {
        int entityCount = 0;
        int relationCount = 0;
        if (mark.containsKey("entities")) {
            entityCount = mark.getJSONArray("entities").size();
        }
        if (mark.containsKey("relations")) {
            relationCount = mark.getJSONArray("relations").size();
        }
        return Pair.of(entityCount, relationCount);
    }
}
