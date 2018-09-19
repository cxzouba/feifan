package com.feifandaiyu.feifan.utils;

import android.widget.EditText;

/**
 * Created by davidzhao on 2017/6/7.
 */

public class EditUtils {
   public static String getEditText(EditText editText){
       return editText.getText().toString().trim();
   }
}
