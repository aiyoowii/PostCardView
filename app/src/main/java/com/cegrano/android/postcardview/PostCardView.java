package com.cegrano.android.postcardview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by cegrano on 16/2/20.
 * 歌词卡片模版
 */
public class PostCardView extends View {
    public static final String TAG = "PostCardView";
    public int width, height;

    private CardStyle mCardStyle;
    private TextPaint textPaint = new TextPaint();
    private Path path;
    private Matrix matrix;
    private Bitmap drawableBg;//模版图
    private float mScale = 0;//视图的比例
    private float mWidthScale = 1.0f;//屏幕和1080的比例
    private float mHeightScale = 1.0f;
    private float mImageScale = 1.0f;

    public PostCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        matrix = new Matrix();
        path = new Path();
        path.lineTo(0, 10000);
        postDraw();
    }

    public static void imageInsert(Context context, String filePath) {
        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    filePath, filePath.substring(0, filePath.lastIndexOf('.')), null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));
    }

    @NonNull
    public static String newPhotoPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/card/phototemp.jpg";
    }

    @NonNull
    public static String newSavePath() {
        return Environment.getExternalStorageDirectory().getPath() + "/card/" + System.currentTimeMillis() + ".jpg";
    }

    @NonNull
    public static String newPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/card/croptemp.jpg";
    }

    private void postDraw() {
        post(new Runnable() {
            @Override
            public void run() {
                width = getWidth();
                height = getHeight();
                mScale = width / DensityUtil.getWidthInPx(getContext());
                mWidthScale = width / mScale / 1080f;
                mHeightScale = height / mScale / 1560f;
//                invalidate();
                if (mScale == 0) {
                    postDraw();
                    return;
                }
                Log.d(TAG, "card height:" + height + " width:" + width + " scale:" + mScale);
                setCardStyleImp(mCardStyle);
            }
        });
    }

    /**
     * set temperate background
     *
     * @param cardStyle style
     */
    public void setCardStyle(CardStyle cardStyle) {
        this.mCardStyle = cardStyle;

        postDraw();
    }

    private void setCardStyleImp(CardStyle cardStyle) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), cardStyle.cardBack.bg, opts);
        Log.d(TAG, "bg height:" + opts.outHeight + " width:" + opts.outWidth + " before scale");
        mImageScale = mWidthScale;
        if (mHeightScale > mImageScale)
            mImageScale = mHeightScale;
        opts.outHeight = (int) Math.ceil(opts.outHeight * mImageScale * mScale);
        opts.outWidth = (int) Math.ceil(opts.outWidth * mImageScale * mScale);
        if (opts.outHeight > height)
            opts.outHeight = height;
        opts.inJustDecodeBounds = false;
        Bitmap bg = BitmapFactory.decodeResource(getResources(), cardStyle.cardBack.bg);
        drawableBg = ThumbnailUtils.extractThumbnail(bg, opts.outWidth, opts.outHeight);
//        bg.recycle();
        Log.d(TAG, "bg height:" + drawableBg.getHeight() + " width:" + drawableBg.getWidth() + " scale:" + mScale);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mCardStyle == null)
            return;
        canvas.save();
        //draw back
        if (drawableBg != null) {
            CardBgStyle bgStyle = mCardStyle.cardBack;
            canvas.drawBitmap(drawableBg, getPixX(bgStyle.posX, drawableBg.getWidth()), getPixY(bgStyle.posY, drawableBg.getHeight()), textPaint);
            Log.d(TAG, "draw bg--x:" + bgStyle.posX + " y:" + bgStyle.posY);
        }
//        canvas.restore();
//        canvas.save();
        //draw text
        List<CardTextStyle> texts = mCardStyle.cardTexts;
        if (texts != null) {
            float m = 0;
            float l = 0;
            for (int pos = 0; pos < texts.size(); pos++) {
                CardTextStyle textStyle = texts.get(pos);
                textPaint.reset();
                textPaint.setColor(textStyle.textColor);
                textPaint.setTextSize(getDpPix(textStyle.textSize));
                if (textStyle.textIsTopDown) {
//                    for (int j = pos+1;j<texts.size();j++)
//                        m += getDpPix(texts.get(j).textSize+10);
                    if (pos == 0) {
                        for (int j = pos; j < texts.size(); j++)
                            m += getDpPix(texts.get(j).textSize + 10);
                        m -= getDpPix(10);
                    } else {
                        m -= getDpPix(texts.get(pos - 1).textSize + 10) * 2;
                    }
                    l = 0;
                    for (int i = 0; i < textStyle.textLength; i++) {
                        if (i >= textStyle.text.length())
                            break;
                        String c = textStyle.text.substring(i, i + 1);
                        float w = textPaint.measureText(c);
                        l += w;
                    }
                    Log.d(TAG, "draw top down text x:" + m + " y:" + l);
                    float py = getPixY(textStyle.posY, l);
                    for (int i = 0; i < textStyle.textLength; i++) {
                        if (i >= textStyle.text.length())
                            break;
                        String c = textStyle.text.substring(i, i + 1);
                        float w = textPaint.measureText(c);
                        if (isChinese(c.charAt(0))) {
                            py += w;
                            canvas.drawText(c, getPixX(textStyle.posX, m), py, textPaint);//chinese character
                        } else {
                            canvas.drawTextOnPath(c, path, py, -getPixX(textStyle.posX, m) - 2, textPaint);//其他文字处理方法
                            py += w;
                        }
                    }
                } else {
                    textPaint.setTextAlign(textStyle.align);
                    int len = textStyle.textLength;
                    if (textStyle.text.length() < len)
                        len = textStyle.text.length();

                    if (pos == 0) {
                        for (int j = pos + 1; j < texts.size(); j++)
                            l += getDpPix(texts.get(j).textSize + 10);
                        l -= getDpPix(texts.get(0).textSize);
                    } else {
                        l -= getDpPix(texts.get(pos - 1).textSize + 10) * 2;
                    }
//                    l = 0;
                    canvas.drawText(textStyle.text, 0, len, getPixX(textStyle.posX, m), getPixY(textStyle.posY, l), textPaint);
                }

                Log.d(TAG, "draw text--x:" + textStyle.posX + " y:" + textStyle.posY);
            }
        }
        canvas.restore();
    }

    //位置，大小等像素相关的使用

    /**
     * @param dp 1080p下应该的偏移dp
     * @return 对应像素
     */
    private float getPixX(float dp) {
        if (dp == -1)
            return width / 2;
//        return DensityUtil.dip2px(getContext(), dp * mWidthScale);
        return DensityUtil.dip2px(getContext(), dp * mScale);
    }

    private float getPixY(float dp) {
        if (dp == -1)
            return height / 2;
//        return DensityUtil.dip2px(getContext(), dp * mHeightScale);
        return DensityUtil.dip2px(getContext(), dp * mScale);
    }

    /**
     * -1表示居中，－2表示起始位置在中间
     *
     * @param dp 1080p下其实dp
     * @param w  当前偏移量，居中时用到，以方便不同行数字的居中
     * @return 计算后对应像素
     */
    private float getPixX(float dp, float w) {
        if (dp == -1)
            return (width - w) / 2;
        else if (dp == -2)
            return (width) / 2;
        return DensityUtil.dip2px(getContext(), dp * mScale);
    }

    private float getPixY(float dp, float l) {
        if (dp == -1)
            return (height - l) / 2;
        else if (dp == -2)
            return (height) / 2;
        return DensityUtil.dip2px(getContext(), dp * mScale);
    }

    //字体等
    private float getDpPix(float dp) {
        return DensityUtil.dip2px(getContext(), dp * mScale);
    }
//    public void save(){
//        try{
//            saveViewToFile();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    private boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public void saveViewToFile() throws IOException {
        Bitmap b = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        draw(canvas);

        File f = new File(newSavePath());
        new File(Environment.getExternalStorageDirectory().getPath() + "/card").mkdir();
        Log.d("SaveViewToFile path", f.getPath());
        if (f.exists())
            f.delete();
        FileOutputStream fos = new FileOutputStream(f);
        b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
        fos.flush();
        fos.close();
        b.recycle();
        imageInsert(getContext(), f.getPath());
    }

    /**
     * text's color,size,position,length & content
     */
    public static class CardTextStyle {
        @ColorInt
        int textColor;
        float textSize;
        float posX, posY;
        int textLength;
        boolean textIsTopDown;
        String text;
        Paint.Align align = Paint.Align.CENTER;

        public CardTextStyle(int textColor, float textSize, float posX, float posY, int textLength, boolean textIsTopDown, String text) {
            this.textColor = textColor;
            this.textSize = textSize;
            this.posX = posX;
            this.posY = posY;
            this.textLength = textLength;
            this.textIsTopDown = textIsTopDown;
            this.text = text;
        }

        public CardTextStyle(int textColor, float textSize, float posX, float posY, int textLength, boolean textIsTopDown, String text, Paint.Align align) {
            this.textColor = textColor;
            this.textSize = textSize;
            this.posX = posX;
            this.posY = posY;
            this.textLength = textLength;
            this.textIsTopDown = textIsTopDown;
            this.text = text;
            this.align = align;
        }
    }

    public static class CardBgStyle {
        @DrawableRes
        int bg;
        float posX, posY;// TODO: 16/3/8 适配

        public CardBgStyle(int bg, float posX, float posY) {
            this.bg = bg;
            this.posX = posX;
            this.posY = posY;
        }
    }

    /**
     * card's background and every text
     */
    public static class CardStyle {
        CardBgStyle cardBack;
        List<CardTextStyle> cardTexts;

        public CardStyle(CardBgStyle cardBack, List<CardTextStyle> cardTexts) {
            this.cardBack = cardBack;
            this.cardTexts = cardTexts;
        }
    }

}
