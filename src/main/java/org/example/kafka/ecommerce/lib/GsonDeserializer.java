package org.example.kafka.ecommerce.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static java.lang.Class.forName;

public class GsonDeserializer<T> implements Deserializer<T> {
    private Class<T> type;
    private Gson gson = new GsonBuilder().create();
    private final Logger logger = LoggerFactory.getLogger(GsonDeserializer.class.getSimpleName());
    public static final String TYPE_NAME_CONFIG = "org.example.kafka.ecommerce.lib.type_config";

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String typeName = String.valueOf(configs.get(TYPE_NAME_CONFIG));
        try {
            type = (Class<T>) forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find class for name " + typeName);
        }

        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        if(type == String.class){
            return (T) new String(bytes);
        }
        return gson.fromJson(new String(bytes), type);
    }
}
