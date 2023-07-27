package util.login.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author maozhanlei
 * @version 1.0
 * @date 2020/9/7 15:42
 **/
public interface BaseService<T> extends Mapper<T>, MySqlMapper<T> {

    T selectFirstByExample(Object o);
}
