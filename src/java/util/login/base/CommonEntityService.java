package util.login.base;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.example.util.dao.CommonBaseDao;
import org.example.util.entity.PageDTO;
import org.example.util.sqlUtils.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonEntityService {

    @Autowired
    private CommonBaseDao commonBaseDao;

    public <T> PageDTO<T> queryEntityByPage(SqlHelper sqlHelper, Class<T> clazz) {

        PageDTO<T> resultPage = new PageDTO<>();
        List<T> list = Lists.newArrayList();

        Page<JSONObject> jsonObjects = commonBaseDao.queryPageByFormatSql(sqlHelper.getParams());
        List<JSONObject> result = jsonObjects.getResult();
        if (CollectionUtils.isNotEmpty(result)) {
            String s = JSONUtil.toJsonStr(result);
            list = JSONUtil.toList(s, clazz);
        }
        resultPage.setList(list);
        resultPage.setTotal(jsonObjects.getTotal());

        return resultPage;
    }

    public Page<JSONObject> queryEntityPage(SqlHelper sqlHelper) {
        return commonBaseDao.queryPageByFormatSql(sqlHelper.getParams());
    }

    public List<JSONObject> queryEntityList(SqlHelper sqlHelper) {
        List<JSONObject> list = commonBaseDao.queryListByFormatSql(sqlHelper.getParams());
        return list;
    }


    public JSONObject queryEntity(SqlHelper sqlHelper) {
        JSONObject jsonObject = commonBaseDao.queryOneByFormatSql(sqlHelper.getParams());
        return jsonObject;
    }



}
