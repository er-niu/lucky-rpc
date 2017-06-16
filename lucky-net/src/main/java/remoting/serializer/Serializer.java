package remoting.serializer;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:42 2017/6/13
 */
public abstract class Serializer {


    public static final int MAX_CACHED_BUF_SIZE = 256 * 1024;
    public static final int DEFAULT_BUF_SIZE = 512;

    public abstract <T> byte[] writeObject(T obj);

    public abstract <T> T readObject(byte[] bytes, int offset, int length, Class<T> clazz);

    public <T> T readObject(byte[] bytes, Class<T> clazz) {
        return readObject(bytes, 0, bytes.length, clazz);
    }


}
