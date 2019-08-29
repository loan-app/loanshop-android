package com.ailiangbao.alb.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.ailiangbao.provider.dal.net.http.entity.ContactsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人工具类
 */
public class ContactsUtils {
    // 号码
    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;

    //上下文对象
    private final Context context;
    //联系人提供者的uri
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    private static ContactsUtils instance;

    public ContactsUtils(Context context) {
        this.context = context;
    }

    public static ContactsUtils getInstance(Context context) {
        if (instance == null) {
            instance = new ContactsUtils(context);
        }
        return instance;
    }

    public List<ContactsEntity> getContactList() {
        List<ContactsEntity> contactsList = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(phoneUri, new String[]{NUM, NAME}, null, null, null);
        while (cursor.moveToNext()) {
            ContactsEntity phoneDto = new ContactsEntity(cursor.getString(cursor.getColumnIndex(NAME)), cursor.getString(cursor.getColumnIndex(NUM)));
            contactsList.add(phoneDto);
        }
        return contactsList;
    }
}
