package util.login.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by mao on 2022/6/12.
 */
@Getter
@AllArgsConstructor
public enum  StatusCode {

    //成功
    ERROR(1, "'系统异常'"),
    SUCCESS(0,"'success'"),

    //100~199 通用错误码
    EC_PARAM_EMPTY(100, "参数%s为空"),//参数为空
    EC_PARAM_INVALID(101, "参数%s不合法"),//参数不合法
    EC_UNKNOWN(102, "未知错误"),//未知错误
    EC_SIGN_EXPIRE(103, "签名过期"),//签名过期
    EC_SIGN_INVALID(104, "签名无效"),//签名无效
    EC_VERIFY_CODE_ERROR(105, "短信验证码错误"),//短信验证码错误
    EC_DATA_NULL_ERROR(106, "数据不存在"),// 数据为空错误
    EC_NO_PERMISSION_ERROR(107, "权限错误"),//权限错误
    EC_OUTRULES_ERROR(108, "违规操作"),//违规操作
    EC_SERVER_ERROR(109, "服务错误"),//服务错误
    EC_ACCESS_ERROR(110, "授权过期"),//授权过期
    EC_UPLOAD_ERROR(111, "文件上传失败"),
    EC_CRON_ERROR(112, "定时任务%s未开启"),
    EC_PERMISSION_ERROR(113, "团队内无成员，请先添加团队成员"),
    EC_NO_PERMISSION2_ERROR(114, "角色不对"),
    EC_NO_USER_ERROR(115, "用户信息查询失败"),


    //自定义错误代码
    EC_CUSTOMIZE_ERROR(199, "'自定义错误代码'"),  //用于关闭页面

    //200~299 系统错误码
    EC_DB_ERROR(200, "DB异常"),//db error
    EC_REDIS_ERROR(201, "Redis异常"),//Redis异常
    EC_SEND_CODE_FAILED(202, "验证码发送失败"),

    //用户相关 400 开头
    EC_USER_LOGIN_ERROR(400, "用户未登录"),//用户登录错误
    EC_USER_ROLE_ERROR(401, "角色错误"),//用户角色错误
    EC_USER_CHECK_ERROR(402, "原密码验证错误"),//用户验证错误
    EC_USER_START_ERROR(410, "已经进行过上班打卡"),//已经进行过上班打卡
    EC_USER_END_ERROR(411, "请先进行上班打卡"),//已经进行过下班打卡
    EC_USER_HAS_SET_INSPECTOR_ERROR(403, "用户已设置此任务验收员"),//用户已设置此任务验收员
    EC_USER_HAS_SET_CHECKER_ERROR(405, "用户已设置此任务质检员"),//用户已设置此任务质检员
    EC_USER_EXIS(406, "用户已存在"),//用户已存在
    EC_USER_TASK_OFFLINE(407, "任务下线，无法操作"),
    EC_LABELINFO_INCOMPLETE(408, "请确认是否标注完整，如果确实无法标注请标记为不可标注！"),
    EC_USER_SUB_ROLE_ERROR(410, "PM子角色错误"),

    EC_NOT_FOUND(404, "页面失踪"),

    //任务相关
    TASK_DATA_SAVE_ERROR(510, "数据保存错误"),
    TASK_IS_NOT_SUPPORTED(511, "该任务不支持这种调用"),
    TASK_ACCEPTED(513, "该任务已经验收通过"),
    TASK_NOT_PROCESSED(515, "有未处理完的任务"),
    TASK_ACCEPTANCE_FAIL(516, "该任务已经验收失败"),
    SYSTEM_IS_BUSY(504, "系统繁忙,请稍后重试"),
    TASK_PACKAGE_HAVE_SUBMIT(505, "该包已提交，请关闭后重新打开"),
    TASK_ACCEPTANCE_EXCEED_EXPR_TIME(517, "已超过确认时间，操作结果无法更改，如需更改请联系项目经理"),
    TASK_ALREADY_DELETE(519, "该任务已被删除"),
    TASK_STATUS_ERROR(521, "该任务状态不存在"),
    TASK_PERMISSION_DELAY(523, "您不是该任务的验收员，请联系PM绑定该任务"),
    TASK_NOT_EXISTS(525, "任务不存在,task_key: %s"),
    TASK_PERMISSION_DELAY_NOT_PM(527, "您不是该任务的PM"),

    //任务相关 600
    EC_COPY_TASK_SAVE_FAIL(601, "复制任务保存失败"),//复制任务保存失败
    EC_ACHIEVE_TASK_TRAIN_NUM(602, "达到最大考核次数"),//达到最大考核次数
    EC_TASK_IS_TRAINING(603, "考核题审核中"),//考核题审核中
    EC_TASK_TRAIN_PASSED(604, "你已考核通过"),//你已考核通过
    EC_TASK_NOT_NEED_TRAIN(605, "该任务不需考核"),//该任务不需考核
    EC_TASK_HAS_COMPLETE(606, "任务已经做完"),//任务已经做完
    EC_TASK_HAS_CHECKED_PASS(607, "任务已全部审核通过"),//任务已全部审核通过
    EC_TASK_HAS_INSPECTED(608, "任务已全部质检过"),//任务已全部质检过
    EC_TASK_INS_RATE_NOT_ACHIEVE(609, "验收比例没有达到标椎"),//验收比例没有达到标椎
    EC_TASK_CHANGE_VOC_HAS_ERROR_TOOL(610, "使用VOC 或者 FanVOC模板时,使用不支持工具"),//使用VOC 或者 FanVOC模板时,使用不支持工具
    EC_TASK_CHANGE_VOC_HAS_ERROR_PNAME(610, "使用VOC 或者 FanVOC模板时,pname(英文名)配置不正确"),//使用VOC 或者 FanVOC模板时,pname(英文名)配置不正确
    EC_NO_CREATE_ERROR(611, "任务不允许创建"),//任务不允许创建
    EC_CHECKER_TASK_CHECKED(612, "已经审核过任务的成员无法设置为标注员"),//已经审核过任务的成员无法设置为标注员


    EC_TASK_NOT_EXIST(695, "该任务不存在"),
    EC_TASK_UPDATE(696, "题更新错误"),
    EC_CHECKER_TASK_ONGOING(697, "该题正在审核更新中......, 请刷新"),
    EC_ROLE_TASK_NOT_RELATIONSHIP(698, "该角色与该任务无任何关系"),
    EC_CHECKER_TASK_NOT_EXISTS(699, "任务不存在"),


    //返回正确等级 700
    EC_CODE_LEVEL_YELLOW(700, ""),//操作成功一部分数据
    OSS_FILE_PARSE_ERROR(701, "文件解析失败！"),
    FILE_BUILD_FAIL(702, "文件生成失败！"),
    FILE_NOT_FOUND(703, "本地文件找不到！"),
    CANNOT_FOUND_COLUMN(704, "找不到csv文件目标列！"),
    TOS_FILE_IS_NULL(705, "Tos文件为空！"),

    // 文件分片上传 800
    FILE_PART_EXISTS(800, "文件已有上传成功记录"),
    FILE_PART_LOSE(801, "文件部分缺失，无法进行合并"),
    FILE_PART_MERGE_FAIL(802, "合并失败"),

    // 工时纠偏
    CORRECT_NOT(900, "当前有未审批记录，审批通过后再试！"),

    // 模型交互
    CALL_ERROR(1000, "接口请求失败，需要查询日志处理"),
    LOCK_ERROR(1001, "服务接口限制达到上限"),
    ;
    private final int code;

    private final String errMsg;

}
