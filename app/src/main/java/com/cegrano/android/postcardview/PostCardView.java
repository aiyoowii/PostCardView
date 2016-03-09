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

    public PostCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        textPaint = new TextPaint();
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
    public static String newPath() {
        return Environment.getExternalStorageDirectory().getPath() + "/card/" + System.currentTimeMillis() + ".jpg";
    }

    private void postDraw() {
        post(new Runnable() {
            @Override
            public void run() {
                width = getWidth();
                height = getHeight();
                mScale = width / DensityUtil.getWidthInPx(getContext());
                mWidthScale = DensityUtil.getWidthInPx(getContext()) / 1080;
                mHeightScale = DensityUtil.getHeightInPx(getContext()) / 1560;
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
        opts.outHeight = (int) (opts.outHeight * mScale * mWidthScale);
        opts.outWidth = (int) (opts.outWidth * mScale * mWidthScale);
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
            canvas.drawBitmap(drawableBg, getPix(bgStyle.posX), getPix(bgStyle.posY), textPaint);
            Log.d(TAG, "draw bg--x:" + bgStyle.posX + " y:" + bgStyle.posY);
        }
//        canvas.restore();
//        canvas.save();
        //draw text
        List<CardTextStyle> texts = mCardStyle.cardTexts;
        if (texts != null) {
            for (CardTextStyle textStyle : texts) {
                textPaint.reset();
                textPaint.setColor(textStyle.textColor);
                textPaint.setTextSize(getDpPix(textStyle.textSize));
                if (textStyle.textIsTopDown) {
                    float py = getPix(textStyle.posY);
                    for (int i = 0; i < textStyle.textLength; i++) {
                        if (i >= textStyle.text.length())
                            break;
                        String c = textStyle.text.substring(i, i + 1);
                        float w = textPaint.measureText(c);
                        if (isChinese(c.charAt(0))) {
                            py += w;
                            canvas.drawText(c, getPix(textStyle.posX), py, textPaint);//chinese character
                        } else {
                            canvas.drawTextOnPath(c, path, py, -getPix(textStyle.posX) - 2, textPaint);//其他文字处理方法
                            py += w;
                        }
                    }
                } else {
                    textPaint.setTextAlign(Paint.Align.CENTER);
                    int len = textStyle.textLength;
                    if (textStyle.text.length() < len)
                        len = textStyle.text.length();
                    canvas.drawText(textStyle.text, 0, len, getPix(textStyle.posX), getPix(textStyle.posY), textPaint);
                }

                Log.d(TAG, "draw text--x:" + textStyle.posX + " y:" + textStyle.posY);
            }
        }
        canvas.restore();
    }

    //位置，大小等像素相关的使用
    private float getPix(float dp) {
        return DensityUtil.dip2px(getContext(), dp * mScale);
    }

    private float getPixY(float dp) {
        return DensityUtil.dip2px(getContext(), dp * mScale * mHeightScale / mWidthScale);
    }

    //字体等
    private float getDpPix(float dp) {
        return DensityUtil.dip2px(getContext(), dp * mScale * mWidthScale);
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

        File f = new File(newPath());
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

        public CardTextStyle(int textColor, float textSize, float posX, float posY, int textLength, boolean textIsTopDown, String text) {
            this.textColor = textColor;
            this.textSize = textSize;
            this.posX = posX;
            this.posY = posY;
            this.textLength = textLength;
            this.textIsTopDown = textIsTopDown;
            this.text = text;
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
