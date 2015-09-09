package com.laibatour.chocolatebox.utils.imageCache;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ListView;
/**
 * Created by Administrator on 2015/9/9.
 * 图片缓存工具类
 */
public class ImageCacheUtils {
    private MemoryCacheUtils memoryCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private NetCacheUtils netCacheUtils;
    public ImageCacheUtils(){
        memoryCacheUtils = new MemoryCacheUtils();
        localCacheUtils = new LocalCacheUtils(memoryCacheUtils);
        netCacheUtils = new NetCacheUtils(memoryCacheUtils,localCacheUtils);
    }
    public void display(ImageView imageView ,String url, ListView list){
//		1、先从内存缓存中拿图片，拿到就展示
//		2、从本地拿图片，拿到就展示
//		3、访问网络获取图片
//			3.1 存本地
//			3.2 存内存
        Bitmap bitmap = null;
//		1、先从内存缓存中拿图片，拿到就展示
        bitmap = memoryCacheUtils.getBitmap(url);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
            System.out.println("从内存中获取");
            return;
        }

//		2、从本地拿图片，拿到就展示
        bitmap = localCacheUtils.getBitmap(url);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
            System.out.println("从本地中获取");
            return;
        }
//		3、访问网络获取图片
        netCacheUtils.display(url, imageView,list);
    }
}
