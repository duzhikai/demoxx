package util.login.domain;

import lombok.Data;

/**
 * Created by mao on 2022/6/12.
 */
@Data
public class LoginInfo {

    private Long gid;

    private Long has_pwd;

    private Integer is_private;

    private String nickname;

    private Integer role_id;

    private Integer pm_sub_role;

    private Long user_id;

    private String user_name;

    private String watermark;

    private String web_image_path;

    private String web_name;

    private Integer wechat_status;

    private String access_key;

    private String secret_key;
    /**
     * 客户字段
     * */
    private Long id;
    /**
     * 客户名称
     */
    private String customer_name;
    /**
     * 帐号密码
     */
    private String password;
    /**
     * 联系方式
     */
    private String mobile;
    /**
     * 客户地址
     */
    private String address;
    /**
     * 客户登陆token
     */
    private String token;
    /**
     * 状态：0正常，1删除，2，冻结
     */
    private Integer status;
    /**
     * 最后访问时间
     */
    private Long last_visit_time;
    /**
     * 客户公司id
     */
    private Long company_id;

    private String avatar;

    private String userKey;

    private String email;

    private String openId;
}
