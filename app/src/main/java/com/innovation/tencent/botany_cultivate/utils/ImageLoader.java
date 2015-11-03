package com.innovation.tencent.botany_cultivate.utils;

/**
 * Created by Mr.Jadyn on 15/8/27.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 三级缓存
 * <p/>
 * 数据结构
 * <p/>
 * 一级缓存：强引用缓存（内存）对象不会被垃圾回收器没有规律的回收，内存不足不会回收对象，宁愿抛出内存不足
 * //20张图片（使用最新）
 * <p/>
 * 二级缓存：软引用缓存（内存）内存不足就会回收这个对象
 * //超过20张
 * <p/>
 * 三级缓存：本地缓存（硬盘）
 * //离线缓存
 */
public class ImageLoader {

    private DefaultImage defaultImage = new DefaultImage();
    private static Context mContext;

    private ImageLoader(Context context) {
        this.mContext = context;
    }

    /**
     * 一级缓存
     */
    //一级缓存的容量,20张图片,超过20张图片放在2级缓存
    private static final int MAX_CAPACITY = 20;
    //key 图片地址 value 是图片,true访问排序，false插入排序 LRU近期最少使用算法
    private static LinkedHashMap<String, Bitmap> firstCacheMap = new LinkedHashMap<String, Bitmap>(MAX_CAPACITY, 0.75f, true) {
        //根据返回值来决定移除map中最老的键和值
        @Override
        protected boolean removeEldestEntry(Entry<String, Bitmap> eldest) {
            if (this.size() > MAX_CAPACITY) {
                //加入二级缓存
                secondCacheMap.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));
                //加入本地缓存
                diskCache(eldest.getKey(), eldest.getValue());
                //移除一级缓存
                return true;
            }
            return false;
        }
    };

    /**
     * 本地缓存
     *
     * @param key   图片路径，被当做图片的名称保存在硬盘上面
     * @param value 图片
     */
    private static void diskCache(String key, Bitmap value) {
        //路径（本地文件的标示符） 进行文件路径的加密
        //消息摘要算法 MD5 抗修改性
        String fileName = Md5Util.getMD5Str(key);
        String path = mContext.getCacheDir().getAbsolutePath() + File.pathSeparator + fileName;
        //JPG
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        value.compress(Bitmap.CompressFormat.JPEG, 100, os);

    }

    private  ImageLoader() {

    }

    private static ImageLoader instance;
    public static ImageLoader getInstance(Context context){
        new ImageLoader(context);
        if(instance==null){
            instance=new ImageLoader(context);
        }
        return instance;
    }

    /**
     * 二级缓存
     */
    //线程安全
    private static ConcurrentHashMap<String, SoftReference<Bitmap>> secondCacheMap = new ConcurrentHashMap<String, SoftReference<Bitmap>>();

    /**
     * 三级缓存
     *
     * @param key       图片地址
     * @param imageView 图片空间
     */
    public void loadImage(String key, ImageView imageView) {
        //读取缓存
        Bitmap bitmap = getFromCache(key);
        if (bitmap != null) {
            //结束该图片的异步任务
            cancelDownload(key,imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(bitmap);
        } else {
            //访问网络
            //设置空白图片
            imageView.setImageDrawable(defaultImage);
            //执行异步任务
            AsynImageLoadTask task = new AsynImageLoadTask(imageView);
            task.execute(key);
        }
    }

    private void cancelDownload(String key, ImageView imageView) {
        //可能有多个异步任务在下载同一个图片
        AsynImageLoadTask task=new AsynImageLoadTask(imageView);
        if(task!=null){
            String downloadKey=task.key;
            if(downloadKey==null||downloadKey.equals(key)){
                task.cancel(true);
            }
        }
    }

    class AsynImageLoadTask extends AsyncTask<String, Void, Bitmap> {

        //key 图片的地址
        private ImageView imageView;
        private String key;

        public AsynImageLoadTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            key = params[0];
            return downLoad(key);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(isCancelled()){
                bitmap=null;
            }if (bitmap!=null){
                //添加到一级缓存
                addFirstCache(key, bitmap);
                //显示
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * 添加到一级缓存
     *
     * @param key
     * @param bitmap
     */
    private void addFirstCache(String key, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (firstCacheMap) {
                firstCacheMap.put(key, bitmap);
            }
        }
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    private Bitmap getFromCache(String key) {
        //从一级缓存读
        synchronized (firstCacheMap) {
            Bitmap bitmap = firstCacheMap.get(key);
            //保持图片的新鲜
            if(bitmap!=null){
                firstCacheMap.remove(bitmap);
                firstCacheMap.put(key,bitmap);
                return bitmap;
            }
        }
        //从二级缓存加载
        SoftReference<Bitmap> softBitmap=secondCacheMap.get(key);
        if(softBitmap!=null){
            Bitmap bitmap=softBitmap.get();
            if(bitmap!=null){
                //添加到一级缓存
                firstCacheMap.put(key,bitmap);
                return bitmap;
            }
        }else{
            //软引用被回收了，从缓存中清楚
            secondCacheMap.remove(key);
        }
        //从本地缓存加载
        Bitmap localBitmap=getFromLocal(key);
        if(localBitmap!=null){
            //添加到一级缓存
            firstCacheMap.put(key,localBitmap);
            return localBitmap;
        }
        return null;
    }

    /**
     * 从本地缓存中读取
     * @param key
     * @return
     */
    private Bitmap getFromLocal(String key) {
        String fileName=Md5Util.getMD5Str(key);
        if(fileName==null){
            return null;
        }
        String path=mContext.getCacheDir().getAbsolutePath()+File.separator+fileName;
        System.out.println(path);
        FileInputStream is = null;
        try {
            is = new FileInputStream(path);
            return BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 下载图片
     *
     * @param key
     * @return
     */
    private Bitmap downLoad(String key) {
        InputStream is = null;
        try {
            is = HttpUtil.downLoad(key);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 默认图片
     */
    class DefaultImage extends ColorDrawable {
        public DefaultImage() {
            super(Color.GRAY);
        }

    }
}
