package com.dream.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;


/**
 * @ClassName JsonUtils
 * @Description TODO
 * @Author tan
 * @Date 2019/11/5 16:57
 * @Version 1.0
 **/
public class JsonUtils {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @description 将对象转换成json字符串
     * @author tan
     * @date 2019/11/5 16:59
     * @param data 待转换对象
     * @return java.lang.String
     **/
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @description 将json结果集转化为对象
     * @author tan
     * @date 2019/11/5 17:02
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return T
     **/
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description 将json数据转换成pojo对象list
     * @author tan
     * @date 2019/11/5 17:09
     * @param jsonData
     * @param beanType
     * @return java.util.List<T>
     **/
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        List<T> list = null;
        try {
            list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
