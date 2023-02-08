package com.kandoka.mybatis.mapping;

/**
 * @Description SQL command types
 * @Author kandoka
 * @Date 2023/2/6 17:57
 */
public enum SqlCommandType {
    /**
     * 未知
     */
    UNKNOWN,
    /**
     * 插入
     */
    INSERT,
    /**
     * 更新
     */
    UPDATE,
    /**
     * 删除
     */
    DELETE,
    /**
     * 查找
     */
    SELECT;
}
