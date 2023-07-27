package util.login.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by mao on 2022/6/12.
 */
@Data
public class PageDTO<T> {

    private Integer pageSize;

    private Integer pageNum;

    private Long total;

    private List<T> list;

    private List<String> column;
}
