package util.login.dao;

import cn.hutool.json.JSONObject;
import com.github.pagehelper.Page;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;
import java.util.Map;

@RegisterMapper
public interface CommonBaseDao {

    List<JSONObject> queryListByFormatSql(Map<String,Object> map);

    Page<JSONObject> queryPageByFormatSql(Map<String, Object> values);

    Long updateByFormatSql(Map<String, Object> values);

    JSONObject queryOneByFormatSql(Map<String,Object> map);
}
