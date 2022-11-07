package com.tcs.eas.api.tools.common.step;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author 44745
 *
 */
public class ValidatorAdapterFactory implements TypeAdapterFactory {

	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
		if (delegate instanceof ReflectiveTypeAdapterFactory.Adapter) {
			try {
				// Get reference to the existing boundFields.
				Field f = delegate.getClass().getDeclaredField("boundFields");
				f.setAccessible(true);
				Map boundFields = (Map) f.get(delegate);
				// Then replace it with our implementation throwing exception if the value is
				// null.
				boundFields = new LinkedHashMap(boundFields) {
					@Override
					public Object get(Object key) {

						Object value = super.get(key);
						if (value == null) {
							throw new JsonParseException("invalid property name: " + key);
						}
						return value;

					}

				};
				// Finally, push our custom map back using reflection.
				f.set(delegate, boundFields);

			} catch (Exception e) {
				// Should never happen if the implementation doesn't change.
				throw new IllegalStateException(e);
			}

		}
		return delegate;
	}

}