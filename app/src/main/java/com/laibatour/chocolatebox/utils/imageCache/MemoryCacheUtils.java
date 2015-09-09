package com.laibatour.chocolatebox.utils.imageCache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
/**
 * Created by Administrator on 2015/9/9.
 */
public class MemoryCacheUtils {
    private LruCache<String, Bitmap> lruCache;

    public MemoryCacheUtils(){
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);// 运行时最大内存的1/8
        lruCache = new LruCache<String, Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 返回一张图片占用的内存空间
                return value.getRowBytes()*value.getHeight();
            }
        };
    }
    // 内存中取图片
    public Bitmap getBitmap(String url){
        return lruCache.get(url);
    }
    // 往内存中存图片
    public void saveBitmap(String url, Bitmap bitmap){
        lruCache.put(url, bitmap);
    }
}
