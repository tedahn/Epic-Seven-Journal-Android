
package com.kumaduma.epicseveninfo.Model.JsonDeserializer;

        import com.google.gson.JsonDeserializationContext;
        import com.google.gson.JsonDeserializer;
        import com.google.gson.JsonElement;
        import com.google.gson.JsonParseException;

        import java.lang.reflect.Type;

public final class EmptyArrayAsEmptyStringAdapter<T> implements JsonDeserializer<T> {

    // Let Gson instantiate it itself
    private EmptyArrayAsEmptyStringAdapter() {
    }

    @Override
    public T deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context)
            throws JsonParseException {
        if ( jsonElement.isJsonArray() ) {
            return null;
        }
        return context.deserialize(jsonElement, type);
    }

}