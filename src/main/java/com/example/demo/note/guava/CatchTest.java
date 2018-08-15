package com.example.demo.note.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhouwei on 2018/8/15
 **/
public class CatchTest {

    public static void main(String[] args) throws ExecutionException {

        Cache<String, Object> cache = CacheBuilder.newBuilder()
                .maximumSize(100)   //最大缓存数量
//                .refreshAfterWrite(30, TimeUnit.SECONDS)    //多长时间没有更新后自动刷新 {@link CacheLoader#reload}
//                .expireAfterWrite(30, TimeUnit.SECONDS)     //最近一次更新间隔指点时间后删除
                .expireAfterAccess(30, TimeUnit.SECONDS)    //多长时间后自动删除
//                .removalListener(new RemovalListener<String, Object>() {  //监听删除事件
//                    @Override
//                    public void onRemoval(RemovalNotification<String, Object> rn) {
//                        System.out.println(rn.getKey() +" ----------"+rn.getValue());
//                    }
//                })
                .build(); //可传入CacheLoader

        //无缓存时返回null
        Object value1 = cache.getIfPresent("123");
        System.out.println(value1);

        //缓存不存在时自动加载
        Object value2 = cache.get("123", new Callable<Object>() {
            @Override
            public Object call(){
                return "aaaa";
            }
        });
        System.out.println(value2);

        //批量删除缓存
        cache.invalidateAll();
    }
}
