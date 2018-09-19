package com.feifandaiyu.feifan.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.yalantis.ucrop.entity.LocalMedia;

import java.io.File;
import java.util.List;

/**
 * Created by davidzhao on 2017/6/23.
 */

public class DeleteImageUtils {

    public static void DeleteImage(List<LocalMedia> list, Context context) {
        for (int i = 0;i<list.size();i++){
            String imgPath = list.get(i).getPath();
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = MediaStore.Images.Media.query(resolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=?",
                    new String[] { imgPath }, null);

            boolean result = false;

            if (cursor.moveToFirst()) {
                long id = cursor.getLong(0);
                Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                Uri uri = ContentUris.withAppendedId(contentUri, id);
                int count = context.getContentResolver().delete(uri, null, null);
                result = count == 1;
            }else {
                File file = new File(imgPath);
                result = file.delete();
            }

            if (result) {

//                Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
            }

        }

    }

    public static void DeleteCutImage(List<LocalMedia> list, Context context) {
        for (int i = 0;i<list.size();i++){




            String cutPath = list.get(i).getCompressPath();

            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = MediaStore.Images.Media.query(resolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=?",
                    new String[] { cutPath }, null);

            boolean result = false;

            if (cursor.moveToFirst()) {
                long id = cursor.getLong(0);
                Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                Uri uri = ContentUris.withAppendedId(contentUri, id);
                int count = context.getContentResolver().delete(uri, null, null);
                result = count == 1;
            }else {
                File file = new File(cutPath);
               result = file.delete();
            }
            if (result) {

//                Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
            }

        }

    }
}
