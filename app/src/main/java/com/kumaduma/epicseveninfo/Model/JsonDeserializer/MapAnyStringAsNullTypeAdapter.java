package com.kumaduma.epicseveninfo.Model.JsonDeserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;

public final class MapAnyStringAsNullTypeAdapter<T>
        implements JsonDeserializer<T> {

    // Let Gson instantiate it itself
    private MapAnyStringAsNullTypeAdapter() {
    }

    @Override
    public T deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context)
            throws JsonParseException {
        if ( jsonElement.isJsonArray() ) {
            final JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement e: jsonArray){
                if (e.isJsonPrimitive()){
                    final JsonPrimitive primitive = e.getAsJsonPrimitive();
                    if (primitive.isString()){
                        return null;
                    }
                }
            }
        }
        return context.deserialize(jsonElement, type);
    }

}