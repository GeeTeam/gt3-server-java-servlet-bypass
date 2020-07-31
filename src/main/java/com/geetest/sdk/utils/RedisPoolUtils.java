package com.geetest.sdk.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPoolUtils {

    private static JedisPool pool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(PropertiesUtils.get("redis.max.total", "20")));
        config.setMaxIdle(Integer.parseInt(PropertiesUtils.get("redis.max.idle", "20")));
        config.setMinIdle(Integer.parseInt(PropertiesUtils.get("redis.min.idle", "20")));
        config.setMaxWaitMillis(Integer.parseInt(PropertiesUtils.get("redis.max.millis", "5")));
        config.setTestOnBorrow(Boolean.parseBoolean(PropertiesUtils.get("redis.test.borrow", "true")));
        config.setTestOnReturn(Boolean.parseBoolean(PropertiesUtils.get("redis.test.return", "true")));
        config.setBlockWhenExhausted(Boolean.parseBoolean(PropertiesUtils.get("redis.block.exhausted", "true")));
        pool = new JedisPool(config, PropertiesUtils.get("redis1.ip"), Integer.parseInt(PropertiesUtils.get("redis1.port")));
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }


    public static void close(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String set(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        close(jedis);
        return result;
    }

    public static String setex(String key, String value, int exTime) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        close(jedis);
        return result;
    }

    public static String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        close(jedis);
        return result;
    }

    public static Long del(String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        close(jedis);
        return result;
    }
}
