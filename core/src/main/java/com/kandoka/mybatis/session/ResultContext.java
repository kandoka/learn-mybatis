package com.kandoka.mybatis.session;

/**
 * @Description result context info
 * @Author kandoka
 * @Date 2023/2/20 18:03
 */
public interface ResultContext {
    /**
     * 获取结果
     */
    Object getResultObject();

    /**
     * 获取记录数
     */
    int getResultCount();
}
