package com.example.captaincat.api.core;

import android.util.Log;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * todo Howie：这方面知识不太熟，后面再整理
 */
public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    public static final String TAG = "LDCAFactory";

    public static LiveDataCallAdapterFactory create() {
        return new LiveDataCallAdapterFactory();
    }

    @SuppressWarnings("ClassGetClass")
    @Nullable
    @Override
    public CallAdapter<?, ?> get(@NotNull Type returnType, @NotNull Annotation[] annotations, @NotNull Retrofit retrofit) {
        //getRawType是提取的实例class，如果我们的User<T> 他得到的就是User.class
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }
        final Type bodyType;
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawType = getRawType(observableType);
        Log.d(TAG, "rawType: " + rawType.getClass().getSimpleName());
        if (rawType != RequestType.class) {
            if (!(observableType instanceof ParameterizedType)) {
                throw new IllegalArgumentException("type must be parameterized");
            }
            bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        } else {
            bodyType = observableType;
        }
        Log.d(TAG, "bodyType: " + bodyType.toString());
        return new LiveDataCallAdapter<>(bodyType);
    }
}
