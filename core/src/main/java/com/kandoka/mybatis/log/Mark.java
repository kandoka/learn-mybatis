package com.kandoka.mybatis.log;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/13 10:36
 */
public enum Mark {

    REFLECT("REFLECT", "反射"),
    BUILD("BUILD", "构建"),
    MAPPING("MAPPING", "映射");

    Mark(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static final String LOG_NAME = "FUNCTION_ID";
    public final String code;
    public final String text;
}
