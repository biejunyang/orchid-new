package com.orchid.common.redis.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;


    /**
     * 解决fastjson自检问题：
     * 1、开始autoType自检
     * 2、设置白名单或者黑名单
     */
    static {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);

//        如果有多个包名前缀，分多次addAccept
//        ParserConfig.getGlobalInstance().addAccept("com.taobao.pac.client.sdk.dataobject.");

//        这里的xx.xxx是包名前缀，如果有多个包名前缀，用逗号隔开
//        ParserConfig.getGlobalInstance().addDeny("xx.xxx");

    }

    /**
     23      * 添加autotype白名单
     24      * 解决redis反序列化对象时报错 ：com.alibaba.fastjson.JSONException: autoType is not support
     25      */

    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);

        return (T) JSON.parseObject(str, clazz);
    }

}