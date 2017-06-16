package lucky.util.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:55 2017/5/18
 */
public class ConfigMap {
    private Map<String, String> settings;

    public ConfigMap() {
        this.settings = new HashMap<>();
    }

    public ConfigMap(int initialCapacity) {
        this.settings = new HashMap<>(initialCapacity);
    }

    public ConfigMap(Map<String, String> settings) {
        this.settings = settings;
    }

    @Deprecated
    public ConfigMap(HashMap<String, String> settings) {
        this.settings = settings;
    }

    public static ConfigMap newEmpty() {
        return new ConfigMap(0);
    }

    /**
     * 合并设置
     *
     * @param primary 首选设置
     * @param standby 备选设置
     * @return 合并后的对象
     */
    public static ConfigMap merge(ConfigMap primary, ConfigMap standby) {
        ConfigMap map = new ConfigMap();
        if (standby != null) {
            standby.each(map::put);
        }
        if (primary != null) {
            primary.each(map::put);
        }
        return map;
    }

    public void put(String key, String value) {
        settings.put(key, value);
    }

    public void put(String key, String value, boolean replace) {
        if (replace || !settings.containsKey(key)) {
            settings.put(key, value);
        }
    }

    public String getValue(String key) {
        return settings.get(key);
    }

    public String getValue(String... keys) {
        String value = null;
        if (keys != null && keys.length > 0) {
            for (String key : keys) {
                value = settings.get(key);
                if (value != null) {
                    break;
                }
            }
        }
        return value;
    }

    public String getString(String key) {
        return settings.get(key);
    }

    public String getString(String key, String defaultValue) {
        return settings.getOrDefault(key, defaultValue);
    }

    public int getInt32(String key) {
        String value = settings.get(key);
        if (value == null || value.isEmpty()) return 0;

        return Integer.parseInt(value);
    }

    public int getInt32(String... keys) {
        String value = getValue(keys);
        if (value == null || value.isEmpty()) return 0;

        return Integer.parseInt(value);
    }

    public int getInt32(String key, int defaultValue) {
        String value = settings.get(key);
        if (value == null || value.isEmpty()) return defaultValue;

        return StringConverter.toInt32(value, defaultValue);
    }

    public long getInt64(String key) {
        String value = settings.get(key);
        if (value == null || value.isEmpty()) return 0;

        return Long.parseLong(value);
    }

    public long getInt64(String... keys) {
        String value = getValue(keys);
        if (value == null || value.isEmpty()) return 0;

        return Long.parseLong(value);
    }

    public long getInt64(String key, long defaultValue) {
        String value = settings.get(key);
        if (value == null || value.isEmpty()) return defaultValue;

        return StringConverter.toInt64(value, defaultValue);
    }

    public boolean getBool(String key) {
        String value = settings.get(key);
        if (value == null || value.isEmpty()) return false;

        return Boolean.parseBoolean(value);
    }

    public boolean getBool(String... keys) {
        String value = getValue(keys);
        if (value == null || value.isEmpty()) return false;

        return Boolean.parseBoolean(value);
    }

    public boolean getBool(String key, boolean defaultValue) {
        String value = settings.get(key);
        if (value == null || value.isEmpty()) return defaultValue;

        return StringConverter.toBool(value, defaultValue);
    }

    public float getFloat32(String key) {
        String value = settings.get(key);
        if (value == null || value.isEmpty()) return 0;

        return Float.parseFloat(value);
    }

    public float getFloat32(String... keys) {
        String value = getValue(keys);
        if (value == null || value.isEmpty()) return 0;

        return Float.parseFloat(value);
    }

    public float getFloat32(String key, float defaultValue) {
        String value = settings.get(key);
        if (value == null || value.isEmpty()) return defaultValue;

        return StringConverter.toFloat32(value, defaultValue);
    }

    public double getFloat64(String key) {
        String value = settings.get(key);
        if (value == null || value.isEmpty()) return 0;

        return Double.parseDouble(value);
    }

    public double getFloat64(String... keys) {
        String value = getValue(keys);
        if (value == null || value.isEmpty()) return 0;

        return Double.parseDouble(value);
    }

    public double getFloat64(String key, double defaultValue) {
        String value = settings.get(key);
        if (value == null || value.isEmpty()) return defaultValue;

        return StringConverter.toFloat64(value, defaultValue);
    }

    public int size() {
        return this.settings.size();
    }

    public void each(BiConsumer<String, String> consumer) {
        this.settings.forEach(consumer);
    }
}
