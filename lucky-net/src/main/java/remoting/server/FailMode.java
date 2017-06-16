package remoting.server;

import lucky.util.lang.EnumValueSupport;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:52 2017/6/14
 */
public enum FailMode implements EnumValueSupport {

    //失败的话，自动转移到另一个
    FailOver(1),
    //立即报异常
    FailFast(2);
    private int value;

    FailMode(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}
