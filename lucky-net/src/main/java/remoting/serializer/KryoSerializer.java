package remoting.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import io.netty.util.internal.ConcurrentSet;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * @Author:chaoqiang.zhou
 * @Description:参考https://www.oschina.net/question/54100_91828
 * @Date:Create in 16:02 2017/6/13
 */
public class KryoSerializer extends Serializer {


    private static ConcurrentSet<Class<?>> useJavaSerializerTypes = new ConcurrentSet<>();


    static {
        useJavaSerializerTypes.add(Throwable.class);
    }


    public KryoSerializer() {

    }

    private static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            for (Class<?> type : useJavaSerializerTypes) {
                kryo.addDefaultSerializer(type, JavaSerializer.class);
            }
            kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
            kryo.setRegistrationRequired(false);
            kryo.setReferences(false);
            return kryo;
        }
    };


    public static void setJavaSerializer(Class<?> type) {
        useJavaSerializerTypes.add(type);
    }


    //一个缓存的线程池
    private static final ThreadLocal<Output> outputThreadLocal = new ThreadLocal<Output>() {
        @Override
        protected Output initialValue() {
            return new Output(DEFAULT_BUF_SIZE, -1);
        }
    };


    @Override
    public <T> byte[] writeObject(T obj) {
        Output output = outputThreadLocal.get();
        try {
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(output, obj);
            return output.toBytes();
        } finally {
            output.clear();
            if (output.getBuffer().length > MAX_CACHED_BUF_SIZE) {
                output.setBuffer(new byte[DEFAULT_BUF_SIZE], -1);
            }
        }
    }

    @Override
    public <T> T readObject(byte[] bytes, int offset, int length, Class<T> clazz) {
        Input input = new Input(bytes, offset, length);
        Kryo kryo = kryoThreadLocal.get();
        return kryo.readObject(input, clazz);
    }

}
