package com.zhen.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Description：Redis工具类
 * Author：wuhengzhen
 * Date：2018-09-20
 * Time：16:00
 */

@SuppressWarnings("unchecked")
public class RedisUtil {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * [dubbo]获取Spring上下文
     */
    // private static ApplicationContext context = ServiceBean.getSpringContext();

    /**
     * RedisTemplate是一个简化Redis数据访问的一个帮助类，
     * 此类对Redis命令进行高级封装，通过此类可以调用ValueOperations和ListOperations等等方法。
     */
    private static RedisTemplate<Serializable, Object> redisTemplate = SpringContextUtil.getBean("redisTemplate", RedisTemplate.class);
    /**
     * 对String操作的封装
     */
    private static StringRedisTemplate stringRedisTemplate = SpringContextUtil.getBean("stringRedisTemplate", StringRedisTemplate.class);

    private static String CACHE_PREFIX;

    private static boolean CACHE_CLOSED;

    private RedisUtil() {

    }

    //region =============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return true/false
     */
    public static boolean expire(String key, long time) {
        logger.debug(" expire key :{}, timeout:{}", key, time);
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public static void del(String... key) {
        logger.debug(" delete key :{}", key.toString());
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
    //endregion


    //region ============================String=============================

    /**
     * String缓存存入key-value
     *
     * @param key   缓存键
     * @param value 缓存值
     * @return true:成功 false:失败
     */
    public static boolean setString(String key, String value) {
        logger.info(" setString key :{}, value: {}", key, value);
        try {
            if (isClose() || isEmpty(key) || isEmpty(value)) {
                return false;
            }
            key = buildKey(key);
            stringRedisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 缓存存入key-value
     *
     * @param key     缓存键
     * @param value   缓存值
     * @param seconds 秒数
     * @return true:成功 false:失败
     */
    public static boolean setString(String key, String value, long seconds) {
        logger.debug(" setString key :{}, value: {}, timeout:{}", key, value, seconds);
        try {
            if (isClose() || isEmpty(key) || isEmpty(value)) {
                return false;
            }
            key = buildKey(key);
            stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 根据key取出String value
     *
     * @param key 缓存key值
     * @return String    缓存的String
     */
    public static String getString(String key) {
        logger.debug(" getString key :{}", key);
        try {
            if (isClose() || isEmpty(key)) {
                return null;
            }
            key = buildKey(key);
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    //endregion


    //region ============================Object=============================

    /**
     * 取出缓存中存储的序列化对象
     *
     * @param key 缓存key
     * @return <T>	序列化对象
     */
    public static <T> T getObject(String key) {
        logger.debug(" getObject key :{}", key);
        if (isClose() || isEmpty(key)) {
            return null;
        }
        try {
            key = buildKey(key);
            return (T) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 缓存中存入序列化的Object对象
     *
     * @param key   缓存key
     * @param value 存入的序列化对象
     * @return true成功 false失败
     */
    public static boolean set(String key, Object value) {
        logger.debug(" set key :{}, value:{}", key, value);
        if (isClose() || isEmpty(key) || isEmpty(value)) {
            return false;
        }
        try {
            key = buildKey(key);
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            return false;
        }
    }

    /**
     * 缓存中存入序列化的Object对象并设置时间
     *
     * @param key   缓存key
     * @param value 存入的序列化对象
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean set(String key, Object value, long time) {
        logger.debug(" set key :{}, value:{}, seconds:{}", key, value, time);
        if (isClose() || isEmpty(key) || isEmpty(value)) {
            return false;
        }
        try {
            key = buildKey(key);
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return long
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return long
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }
    //endregion

    //region ================================Map=================================

    /**
     * 存入Map数组
     *
     * @param <T>
     * @param key 缓存key
     * @param map 缓存map
     * @return true:成功 false:失败
     */
    public static <T> boolean setMap(String key, Map<String, T> map) {
        try {
            if (isClose() || isEmpty(key) || isEmpty(map)) {
                return false;
            }
            key = buildKey(key);
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 取出缓存的map
     *
     * @param key 缓存key
     * @return map    缓存的map
     */
    @SuppressWarnings("rawtypes")
    public static Map getMap(String key) {
        logger.debug(" getMap key :{}", key);
        try {
            if (isClose() || isEmpty(key)) {
                return null;
            }
            key = buildKey(key);
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 查询缓存的map的集合大小
     *
     * @param key 缓存key
     * @return int    缓存map的集合大小
     */
    public static long getMapSize(String key) {
        logger.debug(" getMap key :{}", key);
        try {
            if (isClose() || isEmpty(key)) {
                return 0;
            }
            key = buildKey(key);
            return redisTemplate.opsForHash().size(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }


    /**
     * 根据key以及hashKey取出对应的Object对象
     *
     * @param key     缓存key
     * @param hashKey 对应map的key
     * @return object    map中的对象
     */
    public static Object getMapKey(String key, String hashKey) {
        logger.debug(" getMapkey :{}, hashKey:{}", key, hashKey);
        try {
            if (isClose() || isEmpty(key) || isEmpty(hashKey)) {
                return null;
            }
            key = buildKey(key);
            return redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 取出缓存中map的所有key值
     *
     * @param key 缓存key
     * @return Set<String> map的key值合集
     */
    public static Set<Object> getMapKeys(String key) {
        logger.debug(" getMapKeys key :{}", key);
        try {
            if (isClose() || isEmpty(key)) {
                return null;
            }
            key = buildKey(key);
            return redisTemplate.opsForHash().keys(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 删除map中指定的key值
     *
     * @param key     缓存key
     * @param hashKey map中指定的hashKey
     * @return true:成功 false:失败
     */
    public static boolean delMapKey(String key, String hashKey) {
        logger.debug(" delMapKey key :{}, hashKey:{}", key, hashKey);
        try {
            if (isClose() || isEmpty(key) || isEmpty(hashKey)) {
                return false;
            }
            key = buildKey(key);
            redisTemplate.opsForHash().delete(key, hashKey);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 存入Map数组
     *
     * @param <T>
     * @param key     缓存key
     * @param map     缓存map
     * @param seconds 秒数
     * @return true:成功 false:失败
     */
    public static <T> boolean setMapExp(String key, Map<String, T> map, long seconds) {
        logger.debug(" setMapExp key :{}, value: {}, seconds:{}", key, map, seconds);
        try {
            if (isClose() || isEmpty(key) || isEmpty(map)) {
                return false;
            }
            key = buildKey(key);
            redisTemplate.opsForHash().putAll(key, map);
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * map中加入新的key
     *
     * @param <T>
     * @param key     缓存key
     * @param hashKey map的Key值
     * @param value   map的value值
     * @return true:成功 false:失败
     */
    public static <T> boolean addMap(String key, String hashKey, T value) {
        logger.debug(" addMap key :{}, hashKey: {}, value:{}", key, hashKey, value);
        try {
            if (isClose() || isEmpty(key) || isEmpty(hashKey) || isEmpty(value)) {
                return false;
            }
            key = buildKey(key);
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public static double hashIncr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public static double hashDecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }
    //endregion

    //region ============================set=============================

    /**
     * set集合存入缓存
     *
     * @param <T>
     * @param key 缓存key
     * @param set 缓存set集合
     * @return true:成功 false:失败
     */
    public static <T> boolean setSet(String key, Set<T> set) {
        logger.debug(" setSet key :{}, value:{}", key, set);
        try {
            if (isClose() || isEmpty(key) || isEmpty(set)) {
                return false;
            }
            key = buildKey(key);
            redisTemplate.opsForSet().add(key, set.toArray());
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * set集合中增加value
     *
     * @param key   缓存key
     * @param value 增加的value
     * @return true:成功 false:失败
     */
    public static boolean addSet(String key, Object value) {
        logger.debug(" addSet key :{}, value:{}", key, value);
        try {
            if (isClose() || isEmpty(key) || isEmpty(value)) {
                return false;
            }
            key = buildKey(key);
            redisTemplate.opsForSet().add(key, value);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * set集合存入缓存
     *
     * @param <T>
     * @param key     缓存key
     * @param set     缓存set集合
     * @param seconds 秒数
     * @return true:成功 false:失败
     */
    public static <T> boolean setSet(String key, Set<T> set, long seconds) {
        logger.debug(" setSet key :{}, value:{}, seconds:{}", key, set, seconds);
        try {
            if (isClose() || isEmpty(key) || isEmpty(set)) {
                return false;
            }
            key = buildKey(key);
            redisTemplate.opsForSet().add(key, set.toArray());
            if (seconds > 0) {
                redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 取出缓存中对应的set合集
     *
     * @param <T>
     * @param key 缓存key
     * @return Set<Object> 缓存中的set合集
     */
    public static <T> Set<T> getSet(String key) {
        logger.debug(" getSet key :{}", key);
        try {
            if (isClose() || isEmpty(key)) {
                return null;
            }
            key = buildKey(key);
            return (Set<T>) redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 有序集合存入数值
     *
     * @param key   缓存key
     * @param value 缓存value
     * @param score 评分
     * @return
     */
    public static boolean addZSet(String key, Object value, double score) {
        logger.debug(" addZSet key :{},value:{}, score:{}", key, value, score);
        try {
            if (isClose() || isEmpty(key) || isEmpty(value)) {
                return false;
            }
            key = buildKey(key);
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 从有序集合中删除指定值
     *
     * @param key    缓存key
     * @param values 缓存value 可以是多个
     * @return
     */
    public static long removeZSet(String key, Object... values) {
        logger.debug(" removeZSet key :{},values:{}", key, values);
        if (isClose() || isEmpty(key) || isEmpty(values)) {
            return 0;
        }
        try {
            key = buildKey(key);
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            return 0;
        }
    }

    /**
     * 从有序集合中删除指定位置的值
     *
     * @param key   缓存key
     * @param start 起始位置
     * @param end   结束为止
     * @return
     */
    public static boolean removeZSet(String key, long start, long end) {
        logger.debug(" removeZSet key :{},start:{}, end:{}", key, start, end);
        try {
            if (isClose() || isEmpty(key)) {
                return false;
            }
            key = buildKey(key);
            redisTemplate.opsForZSet().removeRange(key, start, end);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 从有序集合中获取指定位置的值
     *
     * @param key   缓存key
     * @param start 起始位置
     * @param end   结束为止
     * @return
     */
    public static <T> Set<T> getZSet(String key, long start, long end) {
        logger.debug(" getZSet key :{},start:{}, end:{}", key, start, end);
        try {
            if (isClose() || isEmpty(key)) {
                return Collections.emptySet();
            }
            key = buildKey(key);
            return (Set<T>) redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptySet();
    }
    //endregion

    //region ===============================list=================================

    /**
     * 缓存存入List
     *
     * @param <T>
     * @param key  缓存key
     * @param list 缓存List
     * @return true:成功 false:失败
     */
    public static <T> boolean setList(String key, List<T> list) {
        logger.debug(" setList key :{}, list: {}", key, list);
        try {
            if (isClose() || isEmpty(key) || isEmpty(list)) {
                return false;
            }
            key = buildKey(key);
            redisTemplate.opsForList().leftPushAll(key, list.toArray());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 根据key值取出对应的list合集
     *
     * @param key 缓存key
     * @return List<Object> 缓存中对应的list合集
     */
    public static <V> List<V> getList(String key) {
        logger.debug(" getList key :{}", key);
        try {
            if (isClose() || isEmpty(key)) {
                return null;
            }
            key = buildKey(key);
            return (List<V>) redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据key值截取对应的list合集
     *
     * @param key   缓存key
     * @param start 开始位置
     * @param end   结束位置
     * @return
     */
    public static void trimList(String key, int start, int end) {
        logger.debug(" trimList key :{}", key);
        try {
            if (isClose() || isEmpty(key)) {
                return;
            }
            key = buildKey(key);
            redisTemplate.opsForList().trim(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 取出list合集中指定位置的对象
     *
     * @param key   缓存key
     * @param index 索引位置
     * @return Object    list指定索引位置的对象
     */
    public static Object getIndexList(String key, int index) {
        logger.debug(" getIndexList key :{}, index:{}", key, index);
        try {
            if (isClose() || isEmpty(key) || index < 0) {
                return null;
            }
            key = buildKey(key);
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Object存入List
     *
     * @param key   缓存key
     * @param value List中的值
     * @return true:成功 false:失败
     */
    public static boolean addList(String key, Object value) {
        logger.debug(" addList key :{}, value:{}", key, value);
        try {
            if (isClose() || isEmpty(key) || isEmpty(value)) {
                return false;
            }
            key = buildKey(key);
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 缓存存入List
     *
     * @param <T>
     * @param key     缓存key
     * @param list    缓存List
     * @param seconds 秒数
     * @return true:成功 false:失败
     */
    public static <T> boolean setList(String key, List<T> list, long seconds) {
        logger.debug(" setList key :{}, value:{}, seconds:{}", key, list, seconds);
        try {
            if (isClose() || isEmpty(key) || isEmpty(list)) {
                return false;
            }
            key = buildKey(key);
            redisTemplate.opsForList().leftPushAll(key, list.toArray());
            if (seconds > 0) {
                redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }
    //endregion


    @SuppressWarnings("rawtypes")
    private static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            String str = obj.toString();
            return "".equals(str.trim());
        }
        if (obj instanceof List) {
            List<Object> list = (List<Object>) obj;
            return list.isEmpty();
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            return map.isEmpty();
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            return set.isEmpty();
        }
        if (obj instanceof Object[]) {
            Object[] objs = (Object[]) obj;
            return objs.length <= 0;
        }
        return false;
    }

    /**
     * 构建缓存key值
     *
     * @param key 缓存key
     * @return
     */
    private static String buildKey(String key) {
        if (CACHE_PREFIX == null || "".equals(CACHE_PREFIX)) {
            return key;
        }
        return CACHE_PREFIX + ":" + key;
    }

    /**
     * 返回缓存的前缀
     *
     * @return CACHE_PREFIX_FLAG
     */
    public static String getCachePrefix() {
        return CACHE_PREFIX;
    }

    /**
     * 设置缓存的前缀
     *
     * @param cachePrefix
     */
    public static void setCachePrefix(String cachePrefix) {
        if (cachePrefix != null && !"".equals(cachePrefix.trim())) {
            CACHE_PREFIX = cachePrefix.trim();
        }
    }

    /**
     * 关闭缓存
     *
     * @return true:成功 false:失败
     */
    public static boolean close() {
        logger.debug(" cache closed ! ");
        CACHE_CLOSED = true;
        return true;
    }

    /**
     * 打开缓存
     *
     * @return true:存在 false:不存在
     */
    public static boolean openCache() {
        CACHE_CLOSED = false;
        return true;
    }

    /**
     * 检查缓存是否开启
     *
     * @return true:已关闭 false:已开启
     */
    public static boolean isClose() {
        return CACHE_CLOSED;
    }
}
