package com.kandoka.mybatis.session;

/**
 * @Description Handler for result
 * @Author kandoka
 * @Date 2023/2/9 15:22
 */
public interface ResultHandler {

    /**
     * 处理结果
     */
    void handleResult(ResultContext context);
}
