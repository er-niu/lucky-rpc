package lucky.util.lang;

import java.util.Objects;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:54 2017/5/18
 */
public class StringConverter {
    /**
     * 字符串 0 和 false 返回 false，
     * 字符串 1 和 true 返回 true，
     * 其他抛出 FormatException
     *
     * @param s
     * @return
     */
    public static boolean toBool(String s) {
        Objects.requireNonNull(s, "arg s");
        if ("false".equalsIgnoreCase(s) || "0".equals(s)) {
            return false;
        }
        if ("true".equalsIgnoreCase(s) || "1".equals(s)) {
            return true;
        }
        throw new IllegalArgumentException("无法识别的布尔值字符串：" + s);
    }

    public static boolean toBool(String s, boolean defaultValue) {
        if (s != null) {
            if ("true".equalsIgnoreCase(s) || "1".equals(s)) {
                return true;
            } else if ("false".equalsIgnoreCase(s) || "0".equals(s)) {
                return false;
            }
        }
        return defaultValue;
    }

    public static int toInt32(String s) {
        return Integer.parseInt(s);
    }

    public static int toInt32(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long toInt64(String s) {
        return Long.parseLong(s);
    }

    public static long toInt64(String s, long defaultValue) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static float toFloat32(String s) {
        return Float.parseFloat(s);
    }

    public static float toFloat32(String s, float defaultValue) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static double toFloat64(String s) {
        return Double.parseDouble(s);
    }

    public static double toFloat64(String s, double defaultValue) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static int[] toIntArray(String s, String sep) {
        String[] items = s.split(sep);
        int[] arr = new int[items.length];
        for (int i = 0; i < items.length; i++) {
            arr[i] = toInt32(items[i]);
        }
        return arr;
    }

    /**
     * 把整数数组用分隔符连接起来
     *
     * @param delimeter 分隔符
     * @param numbers
     * @return
     */
    public static String toString(String delimeter, int... numbers) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            if (i > 0) sb.append(delimeter);
            sb.append(numbers[i]);
        }
        return sb.toString();
    }

    /**
     * 把数组用分隔符连接起来
     *
     * @param delimiter 分隔符
     * @param numbers
     * @return
     */
    @SafeVarargs
    public static <T> String toString(String delimiter, T... numbers) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            if (i > 0) sb.append(delimiter);
            sb.append(numbers[i]);
        }
        return sb.toString();
    }

    /**
     * 如果字符串为null, 则转换为空字符串
     *
     * @param s
     * @return
     */
    public static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }
}
