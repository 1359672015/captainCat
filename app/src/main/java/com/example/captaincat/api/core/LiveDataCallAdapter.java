package com.example.captaincat.api.core;

import android.util.Log;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<T> implements CallAdapter<T, LiveData<T>> {

    private Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @NotNull
    @Override
    public Type responseType() {
        return responseType;
    }

    @NotNull
    @Override
    public LiveData<T> adapt(@NotNull Call<T> call) {
        return new LiveData<T>() {
            AtomicBoolean stared = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (stared.compareAndSet(false, true)) {
                    call.enqueue(new Callback<T>() {
                        @Override
                        public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
                            postValue(response.body());

                            Log.d("123456", "onResponse: " + response);
                            Log.d("123456", "onResponse: " + response.body());

                        }

                        @Override
                        @SuppressWarnings("All")
                        public void onFailure(Call<T> call, Throwable t) {
                            Log.d("123456初始化", "onFailure: " + t.getMessage());
                            Log.d("123456初始化", "onFailure: " + t.toString());
                            postValue((T) new RequestType<T>(500,
                                    "数据数据加载失败，请检查网络连接", null));
                        }
                    });
                }

            }
        };
    }

}
