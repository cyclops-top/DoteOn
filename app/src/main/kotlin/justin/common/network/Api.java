package justin.common.network;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import justin.common.annotation.Cache;
import justin.common.annotation.Get;
import justin.common.annotation.Path;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author justin on 2017/04/13 14:21
 * @version V1.0
 */
public class Api {
    private static HashMap<Class, Object> apis = new HashMap<>();
    private static HashMap<Class, HashMap<String, ApiInfo>> methods = new HashMap<>();

    private static final int METHOD_GET = 0;
    private static final int METHOD_POST = 1;
    private static final Gson GSON = new Gson();

    public static <T> T get(Class<T> target) {
        Object out = apis.get(target);
        if (out != null) {
            //noinspection unchecked
            return (T) out;
        }
        T api = buildApi(target);
        apis.put(target, api);
        return api;
    }

    public static <T> T buildApi(Class<T> target) {
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(target.getClassLoader(),
                new Class[]{target}, (proxy, method, args) -> {
                    HashMap<String, ApiInfo> methodInfoMap = methods.get(target);
                    if (methodInfoMap == null) {
                        methodInfoMap = new HashMap<>();
                        methods.put(target, methodInfoMap);
                    }
                    ApiInfo info = methodInfoMap.get(method.getName());
                    if (info == null) {
                        info = buildMethod(target, method);
                        methodInfoMap.put(method.getName(), info);
                    }
                    return buildRequest(info, args, info.dataClass);
                });
    }


    private static <T> Observable<T> buildRequest(ApiInfo info, Object[] args, Class<T> tClass) {
        String url = info.url;
        if (info.method == METHOD_GET && args != null && args.length > 0) {
            url = String.format(Locale.ENGLISH, url, args);
        }
        Log.d("Api", "url=" + url);
        final boolean isUseCache = info.cache > 0 && info.method == METHOD_GET;

        Request.Builder requestBuilder = new Request.Builder()
                .url(url);
        if (info.method == METHOD_GET) {
            requestBuilder.get();
        } else {
            //todo  post
        }
        Request request = requestBuilder.build();
        if (isUseCache) {

        }
        //noinspection unchecked
        return Observable.create(
                (ObservableOnSubscribe<String>) source -> {
                    if(isUseCache){
                        String cache = Client.getCache().getAsString(request.url().toString());
                        if(!TextUtils.isEmpty(cache)){
                            source.onNext(cache);
                            Log.d("Api","from cache");
                            return;
                        }
                    }
                    Response response = Client.get().newCall(request).execute();
                    if (response.isSuccessful()) {
                        try {
                            String result = response.body().string();
                            if(isUseCache){
                                Client.getCache().put(request.url().toString(),result,info.cache);
                            }
                            source.onNext(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                            source.onError(new NetworkException(1001, "parse is error"));
                        }
                    } else {
                        NetworkException e = new NetworkException(response.code(), response.message());
                        Log.d("Api","network is error:\n[" + e.getCode() + "]" + e.getMessage());
                        source.onError(e);
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(result -> GSON.fromJson(result, tClass))
                .subscribeOn(Schedulers.computation());

    }

    private static ApiInfo buildMethod(Class<?> target, Method method) {
        ApiInfo info = new ApiInfo();
        Get get = method.getAnnotation(Get.class);
        if (get != null) {
            info.method = METHOD_GET;
        }
        Path path = method.getAnnotation(Path.class);
        info.url = "https://api.douban.com/" + path.value();
        info.dataClass = (Class<?>) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
        Cache cache = method.getAnnotation(Cache.class);
        if (cache != null) {
            info.cache = cache.value();
        }
        return info;
    }

    private static class ApiInfo {
        String url;
        int method = METHOD_POST;
        Class<?> dataClass;
        int cache = 0;

        @Override
        public String toString() {
            return "ApiInfo{" +
                    "url='" + url + '\'' +
                    ", method=" + method +
                    ", dataClass=" + dataClass +
                    '}';
        }
    }

}
