package com.kandoka.mybatis.log;

import org.slf4j.Logger;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/13 14:11
 */
public class MarkableLogger {

    private final Logger logger;
    private final Mark mark;

    public MarkableLogger(Logger logger, Mark mark) {
        this.logger = logger;
        this.mark = mark;
    }

    public void info(String format, Object... arguments) {
        this.logger.info("[" + mark.code + "] - " + format, arguments);
    }
}
