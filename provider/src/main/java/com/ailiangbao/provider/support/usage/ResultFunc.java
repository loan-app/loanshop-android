package com.ailiangbao.provider.support.usage;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 17/06/2017.
 */
public interface ResultFunc<T> {
    void onResult(T data);

    void onFailed(Throwable throwable);
}
