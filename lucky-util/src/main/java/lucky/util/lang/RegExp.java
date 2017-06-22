package lucky.util.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:22 2017/6/22
 */
public class RegExp {
    public boolean match(String reg, String str) {
        return Pattern.matches(reg, str);
    }

    public List<String> find(String reg, String str) {
        Matcher matcher = Pattern.compile(reg).matcher(str);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    public List<String> find(String reg, String str, int index) {
        Matcher matcher = Pattern.compile(reg).matcher(str);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            list.add(matcher.group(index));
        }
        return list;
    }

    public String findString(String reg, String str, int index) {
        String returnStr = null;
        List<String> list = this.find(reg, str, index);
        if (list.size() != 0)
            returnStr = list.get(0);
        return returnStr;
    }

    public String findString(String reg, String str) {
        String returnStr = null;
        List<String> list = this.find(reg, str);
        if (list.size() != 0)
            returnStr = list.get(0);
        return returnStr;
    }

    public static String filterString(String starFilter, String endFilter, String value) {
        boolean result = value.startsWith(starFilter);
        if (result) {
            if (value.endsWith(endFilter)) {
                //去除前后缀信息
                String starVal = value.substring(starFilter.length(), value.length());
                return starVal.substring(0, starVal.length() - endFilter.length());
            }

        }
        return value;
    }


    public static void main(String[] args) {
        RegExp regExp = new RegExp();
        System.out.println(regExp.filterString("3", "}", "${sdfa.asdf.sadf}"));
    }
}
