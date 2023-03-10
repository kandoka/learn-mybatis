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
        if (!isPrintableMark()) {
            return;
        }
        this.logger.info("[" + this.mark.code + "] - " + format, arguments);
    }

    public void error(String format, Object... arguments) {
        if (isPrintableMark()) {
            return;
        }
        this.logger.error("[" + this.mark.code + "] - " + format, arguments);
    }

    public void info(Mark mark, String format, Object... arguments) {
        if (!isPrintableMark()) {
            return;
        }
        this.logger.info("[" + mark.code + "] - " + format, arguments);
    }

    public void error(Mark mark, String format, Object... arguments) {
        if (isPrintableMark()) {
            return;
        }
        this.logger.error("[" + mark.code + "] - " + format, arguments);
    }

    private boolean isPrintableMark() {
        return !this.mark.equals(Mark.REFLECT);
    }
}
