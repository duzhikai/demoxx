package util.login.constant;


import org.example.util.annotation.RedisKey;
import org.example.util.annotation.RedisStructure;

/**
 * @author maozhanlei
 * @Title: rediskey常量类
 * @Description:
 * @date 2021/3/31
 */
public class RedisKeyConstant  {

    @RedisKey(type = RedisStructure.String, des = "测试key")
    public static final String TEST = "mw:test";


    public static final String  CHECK_TASK_LOCK = "check_task_lock"; //审核员任务锁
    public static final String  USER_JOIN_TASK_LOCK = "user_join_task_lock"; //用户领取任务锁
    public static final String  USER_ABANDON_TASK = "user_abandon_task"; //用户放弃任务
    public static final String  EXPORT_CRON = "export_cron"; //导出任务执行锁
    public static final String  USER_JOIN_PACKAGE_TASK_LOCK = "user_join_package_task_lock";
    public static final String  USER_JOIN_FREE_PACKAGE_TASK_LOCK = "user_join_free_package_task_lock";
    public static final String  CHECK_JOIN_PACKAGE = "check_join_package"; //审核员领包锁
    public static final String  INSPECTOR_JOIN_PACKAGE = "inspector_join_package"; //验收员领包锁
    public static final String  INSPECT_TASK_LOCK = "inspector_task_lock"; //验收员领包锁
    public static final String  EXPORT_TASKTOTAL_CACHE = "export_tasktotal_cache"; //导出任务需要导出的任务总量
    public static final String  TASK_PACKAGE_FILE = "task_package_file"; //题包文件hash key前缀
    public static final Integer  EXPORT_PROGRESS_TIME = 3 * 60;
    public static final Integer  EXPIRE_TIME = 60;

    public static final String  USER_DAY_LOGIN_TIME = "user_day_login_time"; // 用户登陆时间
    public static final Integer  USER_DAY_LOGIN_TIME_EXPIRE = 86400; // 用户信息缓存一天,晚于客户端过期,浏览器cookie过期时间为6个小时,线上已经开启单点登录，每次登录后，缓存(redis session)会重新设置过期时间

    public static final String  CUSTOMER_INFO_HASH_KEY = "customer_info_hash";//客户信息hash
    public static final Integer  CUSTOMER_EXPIRE_TIME = 86400 * 7;

    public static final String  ADMIN_INFO_HASH_KEY = "admin_info_hash";
    public static final Integer  ADMIN_EXPIRE_TIME = 86400;

    //为了提高前端程序加载效率，利用浏览器自身的缓存
    public static final String  RESOURCE_URL_CACHE_PREFIX = "resource_url_";//资源安全和浏览器文件缓存
    public static final Integer  RESOURCE_URL_EXPIRE_TIME = 3000;//url缓存3000秒过期，防止出现资源过期的情况

    //创建任务脚步进程锁
    public static final String  IMAPORT_TASK_LOCK = "import_task_lock_";
    public static final String  IMAPORT_UNCOMPRESS_LOCK = "import_uncompress_lock_";
    public static final Integer  IMAPORT_TASK_LOCK_EXPIRE = 86400;

    //import_file 创建题锁
    public static final String  IMAPORT_FILE_LOCK = "import_file_lock_";
    public static final Integer  IMAPORT_FILE_LOCK_EXPIRE = 86400;


    //任务统计;
    public static final String  STAT_HOT_TASKKEY_SET = "stat_hot_task_key_set";

    //当天活跃任务 hash +_Ymd
    public static final String  STAT_DAY_HOT_TASK_KEY = "stat_day_hot_task_key_";

    //被审核通过质检通过统计
    public static final String  STAT_PASS_TASK_QUEUE = "stat_pass_task_queue";
    public static final String  STAT_PASS_TASK_LOCK = "stat_pass_task_lock_";
    public static final Integer  STAT_PASS_TASK_LOCK_EXPIRE_TIME = 3 * 60;

    // 任务信息缓存
    public static final String  TASK_INFO_CACHE = "task_info_cache_v1_";
    public static final Integer  TASK_INFO_CACHE_EXPIRE_TIME = 3600 * 24;

    //每日任务统计
    public static final String  USER_TASK_DAY_STAT_SET_PREFIX = "user_task_day_stat_set_";
    public static final Integer  DAY_STAT_SET_EXPIRE_TIME = 86400;

    //状态修改
    public static final String  TASK_STATUS_CHANGE_PREFIX = "task_status_change_";
    public static final Integer  TASK_STATE_OUTTIME = 300;

    //异步导出
    public static final String  GUILD_SETTLEMENT_EXPORT_PREFIX = "guild_settlement_export_";
    public static final Integer  GUILD_SETTLEMENT_EXPORT_OUTTIME = 3600;

    //清洗任务合格数量缓存(hash)
    public static final String  DATA_CLEAN_PASS_TOTAL_PREFIX = "data_clean_pass_total_num_";
    public static final Integer  DATA_CLEAN_PASS_TOTAL_EXPIRE = 86400 * 30;

    //亚伟导数据需要请求线上资源
    public static final String  RESOURCE_REQ_PREFIX = "resource_req_";
    public static final Integer  RESOURCE_REQ_EXPIRE = 86400 * 2;

    //任务特殊处理
    public static final String  TASK_CREATE_SPECIAL_HANDLE_QUEUE = "task_create_special_handle_queue";
    public static final String  TASK_CREATE_SPECIAL_HANDLE_SCHEDULE = "task_create_special_handle_schedule_";
    public static final String  TASK_CREATE_SPECIAL_HANDLE_JOB_ID = "task_create_special_handle_job_id_";

    // 请求youshudata接口token
    public static final String  YOUSHU_DATA_TOKEN = "youshu_data_token";
    public static final Integer  YOUSHU_DATA_TOKEN_EXPIRE = 86400;

    //develope access token
    public static final String  DEVELOPE_ACCESS_TOKEN_PREFIX = "develope_access_token_";
    public static final Integer  DEVELOPE_ACCESS_TOKEN_EXPORE = 3600 * 6;

    //develope access token
    public static final String  OPEN_ACCESS_TOKEN_PREFIX = "open_access_token_";
    public static final Integer  OPEN_ACCESS_TOKEN_EXPORE = 3600 * 6;

    //导出任务原子锁
    public static final String  CRON_EXPORT_DATA_PREFIX = "cron_export_data_prefix_";
    public static final Integer  CRON_EXPORT_DATA_EXPIRE = 60 * 5;

    //导出任务统计原子锁
    public static final String  CRON_EXPORT_STAT_PREFIX = "cron_export_stat_prefix_";
    public static final Integer  CRON_EXPORT_STAT_EXPIRE = 3600 * 24;

    //创建任务查重队列
    public static final String  TASK_RECHECK_QUEUE = "task_recheck_queue";

    //快速选择属性
    public static final String  FAST_ATTR_PREFIX = "fast_attr_";
    public static final Integer  FAST_ATTR_EXPIRE = 3600 * 24 * 30;

    //用户做题设置
    public static final String  USER_SETTING = "user_setting_";
    public static final Integer  USER_SETTING_EXPIRE = 3600 * 24 * 30;
    //task_info 表缓存
    public static final String  TASK_INFO = "task_info_v1";

    //user_clock_log 表缓存
    public static final String  USER_CLOCK_LOG = "user_clock_log_";

    //user 是否为审核员缓存
    public static final String  IS_CHECKER = "is_checker";

    // user 是否为该题包的审核员
    public static final String  IS_PACKAGE_CHECKER = "is_package_checker";
    public static final Integer  IS_PACKAGE_CHECKER_EXPIRE = 3600;

    // user 是否为该题包的质检员
    public static final String  IS_PACKAGE_INSPECTOR = "is_package_inspector";
    public static final Integer  IS_PACKAGE_INSPECTOR_EXPIRE = 3600;

    //user 是否为pm
    public static final String  IS_PM = "is_pm";

    // 团队是否被分配该任务
    public static final String  IS_TASK_GUILD = "is_task_guild";

    // 普通任务队列
    public static final String  MAGIC_COMMON_TASK_QUEUE = "magic_common_task_queue";

    // petuum 统计
    public static final String  PETUUM_STAT_SET = "petuum_stat_set";  // 需要统计的任务key集合
    public static final String  PETUUM_STAT_MODIFY_NUM = "petuum_stat_modify_num";  // 生成多边形修改数量
    public static final String  PETUUM_STAT_AVAILABLE_MARK = "petuum_stat_available_mark";  // 生成多边形现存总数量
    public static final String  PETUUM_STAT_API_SUCCESS_NUM = "petuum_stat_api_success_num";  // 接口成功调用次数
    public static final String  PETUUM_STAT_POLYGON_NUM = "petuum_stat_polygon_num";  // polygon 标注多边形的数量

    // 不分包
    public static final String  PACKAGE_SELECT = "package_select_";
    public static final String  PACKAGE_TYPE = "package_type_";
    public static final Integer  PACKAGE_TYPE_EXPIRE = 3600 * 24 * 30;
    public static final String  ONE_PACKAGE_GET_TASK_LOCK = "one_package_get_task_lock_";
    public static final Integer  ONE_PACKAGE_GET_TASK_LOCK_EX = 60;

    // 分包
    public static final String  MULTI_PACKAGE_GET_TASK_LOCK = "multi_package_get_task_lock_";
    public static final Integer  MULTI_PACKAGE_GET_TASK_LOCK_EX = 60;

    // 整包驳回
    public static final String  PACKAGE_LOCK = "package_lock_";
    public static final Integer  LOCK_EX = 60;

    // 更新包状态
    public static final String  UPDATE_PACKAGE_STATUS_LOCK = "update_package_status_lock_";

    // 更新题状态
    public static final String  UPDATE_TASK_STATUS_LOCK = "update_task_status_lock_";


    //pm_entities 同一实体库id下的实体记录 存为一个hash
    public static final String  PM_ENTITIES = "pm_entities_";
    public static final Integer  PM_ENTITIES_EXPIRE_TIME = 86400 * 30;

    // customer 任务统计
    public static final String  CUSTOMER_TASK_STAT_QUEUE = "customer_task_stat_queue";
    public static final String  CUSTOMER_TASK_STAT_LOCK = "customer_task_stat_lock_";

    //user 视频任务抽帧数据 string $task_id
    public static final String  VIDEO_PIC_DATA_PRE = "video_pic_data_pre_";

    // taskLog 统计
    public static final String  TASK_LOG_QUEUE = "task_log_queue";

    // 用户信息缓存,代替session的作用
    public static final String  USER_INFO_CACHE = "user_info_cache_";
    public static final String  CUSTOMER_INFO_CACHE = "customer_info_cache_";

    //用户登录次数限制
    public static final String  USER_LOGIN_COUNT = "user_login_count_";
    public static final String  ADMIN_LOGIN_COUNT = "admin_login_count_";
    public static final String  CUSTOMER_LOGIN_COUNT = "admin_login_count_";

    // 抽检锁
    public static final String  SPOT_CHECK_LOCK = "spot_check_lock_";

    // 缓存mask转多边形数据
    public static final String  MASK_DATA_CACHE = "mask_data_cache";

    // task_info 缓存版本号
    public static final String  TASK_INFO_CACHE_VERSION = "v1";

    // admin跳转缓存
    public static final String  ADMIN_JUMP_TOKEN = "admin_jump_token_";
    //验证码前缀
    public static final String  VERIFY_CODE = "verify_code_er_";

    //边标边训练模型当前送到模型未返回数据缓存
    public static final String  MODEL_PRE_DATA = "model_pre_data";

    // RLHF模型接口调用限制缓存
    public static final String RLHF_CALL_LOCK = "rlhf_call_lock";

    //模型训练状态
    public static final String  MODEL_TRAIN_DATA_STATUS = "model_train_data_status";

    //任务纬度模型训练次数
    public static final String  MODEL_TRAIN_TASK_ROUNDS = "model_train_task_rounds_";

    public static final Integer MODEL_TRAIN_TASK_ROUNDS_TIME =  86400 * 30;
    //非生产工时队列
    public static final String  WORKTIME_UNWORKING_QUEUE = "worktime_unworking_queue_";

    //生产工时队列
    public static final String  WORKTIME_WORKING_QUEUE = "worktime_working_queue_";

    //pm工时配置
    public static final String  PM_WORKTIME_CONFIG = "pm_worktime_config_";

    //多轮对话模型缓存
    public static final String  DIALOGUE_MODEL_CACHE = "dialogue_model_cache_";

    public static String buildKey(String prefix, Object suffix){
        return new StringBuilder(prefix).append(suffix).toString();
    }

    public static String buildKey(String prefix, Object suffix, Object suffix1){
        return new StringBuilder(prefix).append(suffix).append(suffix1).toString();
    }
}
