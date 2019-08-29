package com.ailiangbao.alb.util;

import android.support.v7.app.AppCompatActivity;

import com.ailiangbao.alb.ui.base.BaseActivity;
import com.ailiangbao.provider.dal.util.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActivityManageUtils {
    private static List<AppCompatActivity> activityList = new ArrayList<>();

    public static void addActivity(AppCompatActivity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(AppCompatActivity activity) {
        activityList.remove(activity);
    }

    public static void removeActivity(Class<?> clss) {
        for (AppCompatActivity activity : activityList) {
            if (activity.getClass() == clss) {
                activityList.remove(activity);
                break;
            }
        }
    }

    public static void clearActivity() {
        Iterator<AppCompatActivity> iterator = activityList.iterator();
        if (iterator.hasNext()) {
            AppCompatActivity activity = iterator.next();
            activity.finish();
            activityList.remove(activity);
        }
    }

    public static AppCompatActivity getTopActivity() {
        if (!CollectionUtil.isEmpty(activityList)) {
            return activityList.get(0);
        }
        return null;
    }
}
