package com.tcs.eas.api.tools.common.step;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringReader;

public class EnumValidatorAdapterFactory implements TypeAdapterFactory {

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }
        return newStrictEnumAdapter(gson.getDelegateAdapter(this, type), rawType);
    }

    private <T> TypeAdapter<T> newStrictEnumAdapter(
            final TypeAdapter<T> delegateAdapter, Class enumType) {
        return new TypeAdapter<T>() {

            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegateAdapter.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                // Ignore null values
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }

                // Peek at the next value and save it for the error message
                // if you don't need the offending value's actual name
                String enumValue = in.nextString();
                JsonReader delegateReader = new JsonReader(new StringReader('"' + enumValue + '"'));
                T value = delegateAdapter.read(delegateReader);
                delegateReader.close();
                if (value == null) {
                    throw new JsonParseException("Invalid enum value [" + enumValue + "] for " + enumType.getName());
                }
                return value;
            }
        };
    }
}
