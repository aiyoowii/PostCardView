package com.cegrano.android.postcardview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by cegrano on 16/2/20.
 * 歌词卡片模版
 */
public class PostCardView extends View {
    private CardStyle cardStyle;
    private Paint textPaint = new Paint();
    private Path path;
    private Matrix matrix;
    private Bitmap drawableBg;

    public PostCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        textPaint = new Paint();
        matrix = new Matrix();
        path = new Path();
        path.lineTo(0,10000);
    }

    /**
     * set temperate background
     * @param cardStyle style
     */
    public void setCardStyle(CardStyle cardStyle){
        this.cardStyle = cardStyle;
        drawableBg = BitmapFactory.decodeResource(getResources(),cardStyle.cardBack);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw back
        if (drawableBg != null) {
            canvas.drawBitmap(drawableBg, matrix, textPaint);
        }
        //draw text
        List<CardTextStyle> texts = cardStyle.cardTexts;
        if (texts!=null){
            for (CardTextStyle textStyle:texts){
                textPaint.reset();
                textPaint.setColor(textStyle.textColor);
                textPaint.setTextSize(textStyle.textSize);
                if (textStyle.textIsTopDown){
                    float py = 0 + textStyle.posY;
                    for (int i = 0;i<textStyle.textLength;i++){
                        if (i>=textStyle.text.length())
                            break;
                        String c = textStyle.text.substring(i,i+1);
                        float w = textPaint.measureText(c);
                        if (isChinese(c.charAt(0))){
                            py+=w;
                            canvas.drawText(c,textStyle.posX,py,textPaint);//chinese character
                        }else{
                            canvas.drawTextOnPath(c, path, py, -textStyle.posX-2, textPaint);//其他文字处理方法
                            py+=w;
                        }
                    }
                } else
                    canvas.drawText(textStyle.text, 0, textStyle.textLength, textStyle.posX, textStyle.posY, textPaint);
            }
        }
    }

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

    public void save(){
        try{
            saveViewToFile();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void saveViewToFile() throws IOException {
        Bitmap b = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        draw(canvas);
        File f = new File(Environment.getExternalStorageDirectory().getPath()+"/card/"+"text.jpg");
        new File(Environment.getExternalStorageDirectory().getPath()+"/card").mkdir();
        Log.d("SaveViewToFile path",f.getPath());
        if (f.exists())
            f.delete();
        FileOutputStream fos = new FileOutputStream(f);
        b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
        fos.flush();
        fos.close();
        imageInsert(getContext(),f.getPath());
    }

    public static void imageInsert(Context context, String filePath) {
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    filePath, filePath.substring(0, filePath.lastIndexOf('.')), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));
    }

    /**
     * text's color,size,position,length & content
     */
    public static class CardTextStyle{
        @ColorInt
        int textColor;
        float textSize;
        float posX,posY;
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

    /**
     * card's background and every text
     */
    public static class CardStyle{
        @DrawableRes
        int cardBack;
        List<CardTextStyle> cardTexts;

        public CardStyle(int cardBack, List<CardTextStyle> cardTexts) {
            this.cardBack = cardBack;
            this.cardTexts = cardTexts;
        }
    }

}
