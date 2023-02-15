package com.kandoka.mybatis.scripting.xmltags;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/14 17:24
 */
public class StaticTextSqlNode implements SqlNode{

    private String text;

    public StaticTextSqlNode(String text) {
        this.text = text;
    }

    @Override
    public boolean apply(DynamicContext context) {
        //将文本加入context
        context.appendSql(text);
        return true;
    }

}