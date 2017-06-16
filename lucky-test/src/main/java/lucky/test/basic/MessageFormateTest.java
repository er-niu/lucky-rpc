package lucky.test.basic;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * @Author:chaoqiang.zhou
 * @Description:占位符工具类
 * @Date:Create in 15:26 2017/6/9
 */
public class MessageFormateTest {


    public static void main(String[] args) {
        String message = "oh, {},{} is 'a' pig";

        Object[] array = new Object[]{"ZhangSan", "asdf", "asdf"};

//        String value = MessageFormat.format(message, array);
//        System.out.println(value);

        FormattingTuple ft = MessageFormatter.arrayFormat(message, array);
        System.out.println(ft.getMessage());
        FormattingTuple ft2 = MessageFormatter.format(message, "asd", "asdf");
        System.out.println(ft2.getMessage());

    }


}
