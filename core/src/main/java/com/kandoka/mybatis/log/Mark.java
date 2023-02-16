package com.kandoka.mybatis.log;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/13 10:36
 */
public enum Mark {

    REFLECT("REFLECT", "反射"),
    CONFIG("CONFIG", "构建"),
    PARAMETER("PARAMETER", "参数"),
    STATEMENT("STATEMENT", "语句"),
    SQL("SQL", "SQL语句"),
    SESSION("SESSION", "会话"),
    EXECUTE("EXECUTE", "执行"),
    RESULT("RESULT", "结果");

    Mark(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public static final String LOG_NAME = "FUNCTION_ID";
    public final String code;
    public final String text;
}
