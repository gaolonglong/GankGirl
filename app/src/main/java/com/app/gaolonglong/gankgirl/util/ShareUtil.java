package com.app.gaolonglong.gankgirl.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.webkit.WebView;

import java.io.File;

/**
 * Created by gaohailong on 2016/5/19.
 */
public class ShareUtil {
    public static void shareText(Context context,String text){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");//纯文本

        intent.putExtra(Intent.EXTRA_TEXT,text);
        context.startActivity(Intent.createChooser(intent,"分享干货"));
    }

    public static void shareImage(Context context, Uri uri){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");//图片

        //File image = new File(Environment.getExternalStorageDirectory().getPath()+"/DCIM/Screenshots/lock_wallpaper.jpg");
        //Uri uri = Uri.fromFile(image);//图片路径

        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intent,"分享妹纸"));
    }

    public static void copyToClipboard(Context context, String url, WebView webView){
        ClipboardManager cm = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("url",url));
        Snackbar.make(webView,"链接已经拷贝成功啦.. ( ＞ω＜)",Snackbar.LENGTH_SHORT).show();
    }
}
