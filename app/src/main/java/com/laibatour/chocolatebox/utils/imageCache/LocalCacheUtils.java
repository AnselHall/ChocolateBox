package com.laibatour.chocolatebox.utils.imageCache;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import com.laibatour.chocolatebox.utils.LogUtil;

/**
 * Created by Administrator on 2015/9/9.
 */
public class LocalCacheUtils {
    private String CACHE_DIR;// 缓存目录
    private MemoryCacheUtils memoryCacheUtils;

    public LocalCacheUtils(MemoryCacheUtils memoryCacheUtils){
        CACHE_DIR = "/sdcard/laibatour";
        this.memoryCacheUtils = memoryCacheUtils;
    }
    // 从本地获取图片
    public Bitmap getBitmap(String url){
        Bitmap bitmap = null;
        try {
            String fileName = MD5Encoder.encode(url);// 把文件名存为，url的md5加密后的结果
            File file = new File(CACHE_DIR, fileName);// /sdcard/zhbj61/lkjqoieooaj
            if(file.exists()){

                LogUtil.e("取的路径:", file.getAbsolutePath());
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                // 往内存中存
                memoryCacheUtils.saveBitmap(url, bitmap);
            }

        } catch (Exception e) {
        }
        return bitmap;
    }
    // 保存图片到内存中
    public void saveBitmap(String url,Bitmap bitmap){
        try {
            // 存到内存
            memoryCacheUtils.saveBitmap(url, bitmap);
            String fileName = MD5Encoder.encode(url);// 把文件名存为，url的md5加密后的结果
            File file = new File(CACHE_DIR, fileName);// /sdcard/zhbj61/lkjqoieooaj
            File parentDir = file.getParentFile();
            if(!parentDir.exists()){
                parentDir.mkdirs();
            }
            LogUtil.e("取的路径:", file.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
        }
    }
}
