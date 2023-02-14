package com.kandoka.mybatis.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/13 14:06
 */
public class MarkableLoggerFactory {

    public static MarkableLogger getLogger(Mark mark, Class<?> clz) {
        Logger logger = LoggerFactory.getLogger(clz);
        return new MarkableLogger(logger, mark);
    }
}
