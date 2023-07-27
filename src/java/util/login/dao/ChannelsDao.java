package util.login.dao;

import org.example.util.entity.Student;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @className: ChannelsDao
 * @author: maozhanlei
 * @date: 2022/8/15
**/
public interface ChannelsDao extends Mapper<Student>, MySqlMapper<Student> {
}