package com.ailiangbao.provider.support.rxbus;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ailiangbao.provider.dal.util.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 02/08/2017.
 */
public final class RxBus {
    private static final String TAG = RxBus.class.getSimpleName();
    private static boolean debug = false;

    public static void setDebug(boolean debug) {
        RxBus.debug = debug;
    }

    private static class Holder {
        private static RxBus instance = new RxBus();

    }

    public static synchronized RxBus get() {
        return Holder.instance;
    }

    private RxBus() {
    }

    private ConcurrentHashMap<Object, List<FlowableProcessor>> flowableProcessorMapper = new ConcurrentHashMap<>();

    public <T> Flowable<T> register(@NonNull Class<T> clazz) {
        return register(clazz.getName(), clazz);
    }

    @SuppressWarnings("unchecked")
    public <T> Flowable<T> register(@NonNull Object tag, @NonNull Class<T> clazz) {
        List<FlowableProcessor> flowableProcessorList = flowableProcessorMapper.get(tag);
        if (null == flowableProcessorList) {
            flowableProcessorList = new ArrayList<>();
            flowableProcessorMapper.put(tag, flowableProcessorList);
        }

        FlowableProcessor<T> subject = PublishProcessor.<T>create().toSerialized();
//        subject.onBackpressureLatest();
        flowableProcessorList.add(subject);
        if (debug) {
            Log.d(TAG, "[register]flowableProcessorMapper: " + flowableProcessorMapper);
        }
        return subject;
    }

    public void unregister(@NonNull Class<?> eventClazz, @NonNull Flowable flowable) {
        unregister(eventClazz.getName(), flowable);
    }

    public void unregister(@NonNull Object tag, @NonNull Flowable flowable) {
        List<FlowableProcessor> flowableProcessors = flowableProcessorMapper.get(tag);
        if (null != flowableProcessors) {
            flowableProcessors.remove(flowable);
            if (CollectionUtil.isEmpty(flowableProcessors)) {
                flowableProcessorMapper.remove(tag);
            }
        }

        if (debug) {
            Log.d(TAG, "[unregister]flowableProcessorMapper: " + flowableProcessorMapper);
        }
    }

//    public void post(@NonNull String content) {
//        post(content, content);
//    }

    public void post(@NonNull Object content) {
        post(content.getClass().getName(), content);
    }

    @SuppressWarnings("unchecked")
    public void post(@NonNull Object tag, @NonNull Object content) {
        List<FlowableProcessor> subjectList = flowableProcessorMapper.get(tag);
        if (!CollectionUtil.isEmpty(subjectList)) {
            for (FlowableProcessor flowableProcessor : subjectList) {
                flowableProcessor.onNext(content);
            }
        }
        if (debug) {
            Log.d(TAG, "[send]flowableProcessorMapper: " + flowableProcessorMapper);
        }
    }

    public void clear() {
        flowableProcessorMapper.clear();
    }
}
