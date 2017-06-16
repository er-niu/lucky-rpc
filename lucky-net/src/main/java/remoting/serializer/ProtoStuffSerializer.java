package remoting.serializer;

import com.google.common.collect.Maps;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;

import java.util.concurrent.ConcurrentMap;

/**
 * @Author:chaoqiang.zhou
 * @Description:本框架的默认实现
 * @Date:Create in 16:29 2017/6/13
 */
public class ProtoStuffSerializer extends Serializer {

    private static final ConcurrentMap<Class<?>, io.protostuff.Schema<?>> schemaCache = Maps.newConcurrentMap();


    private static final ThreadLocal<LinkedBuffer> bufThreadLocal = new ThreadLocal<LinkedBuffer>() {
        @Override
        protected LinkedBuffer initialValue() {
            return LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        }
    };


    @Override
    public <T> byte[] writeObject(T obj) {

        io.protostuff.Schema<T> schema = getSchema((Class<T>) obj.getClass());

        LinkedBuffer buf = bufThreadLocal.get();
        try {
            return ProtostuffIOUtil.toByteArray(obj, schema, buf);
        } finally {
            buf.clear(); // for reuse
        }
    }

    @Override
    public <T> T readObject(byte[] bytes, int offset, int length, Class<T> clazz) {

        try {
            T msg = clazz.newInstance();
            io.protostuff.Schema<T> schema = getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(bytes, offset, length, msg, schema);
            return msg;
        } catch (Exception e) {

        }

        return null;
    }

    private <T> io.protostuff.Schema<T> getSchema(Class<T> clazz) {
        io.protostuff.Schema<T> schema = (io.protostuff.Schema<T>) schemaCache.get(clazz);
        if (schema == null) {
            io.protostuff.Schema<T> newSchema = RuntimeSchema.createFrom(clazz);
            schema = (io.protostuff.Schema<T>) schemaCache.putIfAbsent(clazz, newSchema);
            if (schema == null) {
                schema = newSchema;
            }
        }
        return schema;
    }
}
