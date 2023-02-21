package com.kandoka.mybatis.executor.result;

import com.kandoka.mybatis.session.ResultContext;

/**
 * @Description Context of current result state
 * @Author kandoka
 * @Date 2023/2/20 18:01
 */
public class DefaultResultContext implements ResultContext {

    private Object resultObject;
    private int resultCount;

    public DefaultResultContext() {
        this.resultObject = null;
        this.resultCount = 0;
    }

    @Override
    public Object getResultObject() {
        return resultObject;
    }

    @Override
    public int getResultCount() {
        return resultCount;
    }

    public void nextResultObject(Object resultObject) {
        resultCount++;
        this.resultObject = resultObject;
    }

}
