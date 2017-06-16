package lucky.util.config;

import java.math.BigDecimal;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 22:35 2017/6/8
 */
public class test {

    public static void main(String[] args) {
        float aa = 305.80f;
        int dd = (int) (aa * 100);
        int cc = BigDecimal.valueOf(aa*100).intValue();
        System.out.println(dd);
        System.out.println(cc);
    }
}
