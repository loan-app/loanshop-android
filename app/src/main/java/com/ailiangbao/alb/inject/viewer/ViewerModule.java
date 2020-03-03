package com.ailiangbao.alb.inject.viewer;

import android.content.Context;
import android.support.annotation.Nullable;

import com.ailiangbao.alb.inject.scope.Scope_Viewer;
import com.dangbei.mvparchitecture.viewer.Viewer;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;


@Module
@Scope_Viewer
public class ViewerModule {
    private WeakReference<Viewer> viewerRef;

    public ViewerModule(Viewer viewer) {
        this.viewerRef = new WeakReference<>(viewer);
    }

    @Provides
    public Viewer providerViewer() {
        return null == viewerRef ? null : viewerRef.get();
    }

    @Provides
    @Nullable
    public Context providerContext() {
        Viewer viewer;
        return null == viewerRef || null == (viewer = viewerRef.get()) ? null : viewer.context();
    }
}
