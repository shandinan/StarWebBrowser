package com.star.starwebbrowser.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.Layout.Alignment;
import android.util.Log;

public class ImageDeal {
    public  static Bitmap AddTextToImage(Bitmap paramBitmap, String paramString)
    {
        Date localDate = new Date();
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = paramString + "  " + localSimpleDateFormat.format(localDate);
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        //  Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint1 = new Paint();
        localPaint1.setDither(true);
        localPaint1.setFilterBitmap(true);
        localCanvas.drawBitmap(paramBitmap, new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight()), new Rect(0, 0, i, j), localPaint1);
        Paint localPaint2 = new Paint(257);
        localPaint2.setTextSize(35.0F);
        localPaint2.setARGB(200, 255, 0, 0);
        localPaint2.setTypeface(Typeface.DEFAULT_BOLD);
        localCanvas.drawText(str, 70.0F, 56.0F, localPaint2);
        localCanvas.save();
        localCanvas.restore();
        return localBitmap;
    }

    private Bitmap decodeBitmapByArray(byte[] imgData) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //int imgLen = imgData.length;
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable=true;
        if(Build.VERSION.SDK_INT >= 11) {
            options.inMutable = true;
        }
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // return BitmapFactory.decodeByteArray(imgData, 0, imgLen, options);
        InputStream isp = new ByteArrayInputStream(imgData);
        return BitmapFactory.decodeStream(isp,null,options);
    }

    public  Bitmap AddTextToImage(byte[] paramAnonymousArrayOfByte, String paramString){
        Date localDate = new Date();
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = paramString + "  " + localSimpleDateFormat.format(localDate);
        Bitmap paramBitmap = decodeBitmapByArray(paramAnonymousArrayOfByte);
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        Log.e("===============", "获取宽高"+i+"=="+j);
        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        //  Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);

        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint1 = new Paint();
        localPaint1.setDither(true);
        localPaint1.setFilterBitmap(true);
        localCanvas.drawBitmap(paramBitmap, new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight()), new Rect(0, 0, i, j), localPaint1);
        Paint localPaint2 = new Paint(257);
        localPaint2.setTextSize(16.0F);
        localPaint2.setARGB(100, 255, 0, 0);
        localPaint2.setTypeface(Typeface.DEFAULT_BOLD);
        localCanvas.drawText(str, 20.0F, 26.0F, localPaint2);
        localCanvas.save();
        localCanvas.restore();
        return localBitmap;
    }


    //获取图片缩小的图片
    public static Bitmap scaleBitmap(String src,int max)
    {
        //获取图片的高和宽
        BitmapFactory.Options options = new BitmapFactory.Options();
        //这一个设置使 BitmapFactory.decodeFile获得的图片是空的,但是会将图片信息写到options中
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(src, options);
        // 计算比例 为了提高精度,本来是要640 这里缩为64
        max=max/10;
        int be = options.outWidth / max;
        if(be%10 !=0)
            be+=10;
        be=be/10;
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;
        //设置可以获取数据
        options.inJustDecodeBounds = false;
        //获取图片
        return BitmapFactory.decodeFile(src, options);
    }
    // 加水印 也可以加文字
    public static Bitmap watermarkBitmap(Bitmap src, Bitmap watermark,
                                         String title) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        //需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
        Bitmap newb= Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        Paint paint=new Paint();
        //加入图片
        if (watermark != null) {
            int ww = watermark.getWidth();
            int wh = watermark.getHeight();
            paint.setAlpha(50);
            cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);// 在src的右下角画入水印
        }
        //加入文字
        if(title!=null)
        {
            String familyName ="宋体";
            Typeface font = Typeface.create(familyName,Typeface.BOLD);
            TextPaint textPaint=new TextPaint();
            textPaint.setColor(Color.RED);
            textPaint.setTypeface(font);
            textPaint.setTextSize(22);
            //这里是自动换行的
            StaticLayout layout = new StaticLayout(title,textPaint,w,Alignment.ALIGN_NORMAL,1.0F,0.0F,true);
            layout.draw(cv);
            //文字就加左上角算了
            //cv.drawText(title,0,40,paint);
        }
        //cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.save();//保存
        cv.restore();// 存储
        return newb;
    }

}
