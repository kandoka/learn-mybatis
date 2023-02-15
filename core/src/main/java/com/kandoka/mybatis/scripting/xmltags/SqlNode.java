package com.kandoka.mybatis.scripting.xmltags;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/14 17:24
 */
public interface SqlNode {
    boolean apply(DynamicContext context);
}
