package com.kumaduma.epicseveninfo.Model.JsonDeserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;

public final class AnyStringAsNullTypeAdapter<T>
        implements JsonDeserializer<T> {

    // Let Gson instantiate it itself
    private AnyStringAsNullTypeAdapter() {
    }

    @Override
    public T deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context)
            throws JsonParseException {
        if ( jsonElement.isJsonPrimitive() ) {
            final JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
            if ( jsonPrimitive.isString() ) {
                return null;
            }
        }
        return context.deserialize(jsonElement, type);
    }

}