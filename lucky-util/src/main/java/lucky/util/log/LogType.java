package lucky.util.log;

import lucky.util.lang.EnumDisplayNameSupport;
import lucky.util.lang.EnumValueSupport;
import lucky.util.lang.Enums;
import org.apache.log4j.spi.ErrorCode;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:23 2017/5/17
 */
public enum LogType implements EnumDisplayNameSupport, EnumValueSupport {

    LOG4J(1, "log4j"),
    LOGBACK(2, "logback"),
    SLF4J(3,"slf4j");

    private int value;
    private String name;

    private LogType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public int value() {
        return this.value;
    }


    @Override
    public String displayName() {
        return this.name;
    }


    public static LogType valueOf(int value) {
        return Enums.valueOf(LogType.class, value);
    }

}
