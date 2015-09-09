package com.laibatour.chocolatebox.utils.imageCache;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.ListView;

import com.laibatour.chocolatebox.utils.LogUtil;

/**
 * Created by Administrator on 2015/9/9.
 */
public class NetCacheUtils {
    private ExecutorService pool;// 线程池
    private InternalHandler handler;
    private MemoryCacheUtils memoryCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private ListView list;
    private static final int SUCCESS = 1;// 下载成功
    private static final int FAILED = 0;// 下载失败
    public NetCacheUtils(MemoryCacheUtils memoryCacheUtils, LocalCacheUtils localCacheUtils){
        pool = Executors.newFixedThreadPool(10);
        this.memoryCacheUtils = memoryCacheUtils;
        this.localCacheUtils = localCacheUtils;
    }
    public void display(String url,ImageView imageView, ListView list){
        pool.execute(new DownloadRunnable(url,imageView));
        handler = new InternalHandler();
        this.list = list;
    }

    class InternalHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    // 更新ImageView
//				Result result = (Result) msg.obj;
//				result.imageView.setImageBitmap(result.bitmap);
                    Result result = (Result) msg.obj;
                    ImageView imageView = (ImageView) list.findViewWithTag(result.position);
                    if(imageView!=null){
                        imageView.setImageBitmap(result.bitmap);
                    }

                    LogUtil.e(this,"自己写的下载图片成功");
                    break;
                case FAILED:
                    LogUtil.e(this, "下载图片失败");
                    break;
                default:
                    break;
            }
        }
    }

    class DownloadRunnable implements Runnable{

        private String mUrl;
        private ImageView mImageView;
        private int position;

        public DownloadRunnable(String url, ImageView imageView) {
            this.mUrl = url;
            this.mImageView = imageView;
            position = (Integer) imageView.getTag();
        }

        @Override
        public void run() {
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(mUrl).openConnection();
                /*//模拟访问网络的延迟
                Thread.sleep(3000);*/
                con.connect();
                int resCode = con.getResponseCode();
                if(resCode==200){
                    InputStream in = con.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    // 下载图片成功，发送消息更新图片
//					Message.obtain(handler, SUCCESS, new Result(mImageView, bitmap)).sendToTarget();
                    Message.obtain(handler, SUCCESS, new Result(position, bitmap)).sendToTarget();

                    // 缓存到本地，缓存到内存
                    localCacheUtils.saveBitmap(mUrl, bitmap);
//					memoryCacheUtils.saveBitmap(mUrl, bitmap);

                }else{
                    Message.obtain(handler,FAILED).sendToTarget();
                }

            } catch (Exception e) {
            }
        }

    }

    class Result {
        public ImageView imageView;
        public Bitmap bitmap;
        private int position;
        public Result(ImageView imageView, Bitmap bitmap) {
            this.imageView = imageView;
            this.bitmap = bitmap;
        }
        public Result(int position, Bitmap bitmap) {
            this.position = position;
            this.bitmap = bitmap;
        }
    }
}
