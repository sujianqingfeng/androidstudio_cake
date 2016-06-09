package com.sujian.finalandroid.uitls;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 项目名称：Sujian
 * 类描述：图片工具类
 * 创建人：SUJIAN  121116111QQ.COM
 * 创建时间：2016/3/12 16:09
 * 修改人：SUJIAN
 * 修改时间：2016/3/12 16:09
 * 修改备注：
 */
public class ImagesUitls {

    //把cotent的url类型转化为file的uri类型  最好把方法抽出去 以便其他的类使用
    public static Uri converUri(Application application, Uri uri) {
        InputStream is = null;
        try {
            is = application.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }//End

    //保存位图  并返回file的uri
    public static Uri saveBitmap(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "sujian" + File.separator + System.currentTimeMillis());
        if (!file.exists()) {
            file.mkdirs();
        }
        File img = new File(file.getAbsolutePath(), "sujian.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }//END

    //进入系统的图像裁剪界面
    public static void stratImageZoom(Activity activity, Uri uri, int ZOOM_CODE) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        intent.putExtra("aspectX", "1");
        intent.putExtra("aspectY", "1");
        intent.putExtra("outputX", "150");
        intent.putExtra("outputY", "150");
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        activity.startActivityForResult(intent, ZOOM_CODE);
    }//end


    public static Bitmap getBitmapFromUri(Uri uri, Context mContext) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
