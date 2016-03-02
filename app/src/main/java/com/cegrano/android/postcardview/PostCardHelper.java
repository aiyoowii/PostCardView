package com.cegrano.android.postcardview;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cegrano on 16/2/20.
 * helper
 */
public class PostCardHelper {
    public static int COUNT = 6;
    public static PostCardView.CardStyle getTemper(int index){
        switch (index){
            case 0:
                return Temper1();
            case 1:
                return Temper2();
            case 2:
                return Temper3();
            case 3:
                return Temper4();
            case 4:
                return Temper5();
            case 5:
                return Temper6();
            default:
                return Temper0();
        }
    }

    //ps depend on 1080p
    private static PostCardView.CardStyle Temper1() {
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 70, 488, 400, 16, true, "新的战役将要开始");
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 50, 595, 400, 100, true, "Here comes another fight");
        List<PostCardView.CardTextStyle> texts = new ArrayList<>();
        texts.add(text1);
        texts.add(text2);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.bg_card_temper1), 302, 182);
        return new PostCardView.CardStyle(bgStyle, texts);
    }

    private static PostCardView.CardStyle Temper2() {
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 44, 540, 635, 16, false, "Here comes another fight");
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 60, 540, 700, 100, false, "新的战役将要开始");
        PostCardView.CardTextStyle text3 = new PostCardView.CardTextStyle(Color.WHITE, 44, 540, 800, 16, false, "Here comes another fight");
        PostCardView.CardTextStyle text4 = new PostCardView.CardTextStyle(Color.WHITE, 60, 540, 865, 100, false, "新的战役将要开始");
        List<PostCardView.CardTextStyle> texts = new ArrayList<>();
        texts.add(text1);
        texts.add(text2);
        texts.add(text3);
        texts.add(text4);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.bg_card_temper2), 115, 350);
        return new PostCardView.CardStyle(bgStyle, texts);
    }

    private static PostCardView.CardStyle Temper3() {
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 62, 50, 142, 16, true, "新的战役将要开始");
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 42, 142, 142, 100, true, "Here comes another fight");
        PostCardView.CardTextStyle text3 = new PostCardView.CardTextStyle(Color.WHITE, 62, 214, 142, 16, true, "新的战役将要开始");
        PostCardView.CardTextStyle text4 = new PostCardView.CardTextStyle(Color.WHITE, 42, 306, 142, 100, true, "Here comes another fight");
        List<PostCardView.CardTextStyle> texts = new ArrayList<>();
        texts.add(text1);
        texts.add(text2);
        texts.add(text3);
        texts.add(text4);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.bg_card_temper3), 50, 104);
        return new PostCardView.CardStyle(bgStyle, texts);
    }

    private static PostCardView.CardStyle Temper4() {
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 60, 540, 750, 16, false, "新的战役将要开始");
        List<PostCardView.CardTextStyle> texts = new ArrayList<>();
        texts.add(text1);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.bg_card_temper4), 0, 0);
        return new PostCardView.CardStyle(bgStyle, texts);
    }

    private static PostCardView.CardStyle Temper5() {
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 50, 540, 610, 16, false, "新的战役将要开始");
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 50, 540, 700, 100, false, "新的战役将要开始");
        PostCardView.CardTextStyle text3 = new PostCardView.CardTextStyle(Color.WHITE, 50, 540, 790, 16, false, "新的战役将要开始");
        PostCardView.CardTextStyle text4 = new PostCardView.CardTextStyle(Color.WHITE, 50, 540, 880, 100, false, "新的战役将要开始");
        List<PostCardView.CardTextStyle> texts = new ArrayList<>();
        texts.add(text1);
        texts.add(text2);
        texts.add(text3);
        texts.add(text4);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.bg_card_temper5), 0, 150);
        return new PostCardView.CardStyle(bgStyle, texts);
    }

    private static PostCardView.CardStyle Temper6() {
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 60, 68, 182, 2, false, "新的战役将要开始");
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 48, 68, 284, 100, false, "新的战役将要开始");
        PostCardView.CardTextStyle text3 = new PostCardView.CardTextStyle(Color.WHITE, 48, 68, 374, 16, false, "新的战役将要开始");
        PostCardView.CardTextStyle text4 = new PostCardView.CardTextStyle(Color.WHITE, 48, 1015, 1145, 100, false, "新的战役将要开始");
        PostCardView.CardTextStyle text5 = new PostCardView.CardTextStyle(Color.WHITE, 48, 1015, 1235, 16, false, "新的战役将要开始");
        PostCardView.CardTextStyle text6 = new PostCardView.CardTextStyle(Color.WHITE, 60, 1015, 1325, 2, false, "新的战役将要开始");
        List<PostCardView.CardTextStyle> texts = new ArrayList<>();
        texts.add(text1);
        texts.add(text2);
        texts.add(text3);
        texts.add(text4);
        texts.add(text5);
        texts.add(text6);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.bg_card_temper6), 0, 765);
        return new PostCardView.CardStyle(bgStyle, texts);
    }

    private static PostCardView.CardStyle Temper0() {
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.BLUE,22,100,30,16,false,"你好的拉家带口了飞机失联飞机额外饿哦如果老是打击");
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.BLACK,33,190,200,100,true,"hello world! forever forever forever");
        PostCardView.CardTextStyle text3 = new PostCardView.CardTextStyle(Color.RED,44,60,500,60,true,"你好的拉家带口了飞机失联飞机额外饿哦如果老是打击");
        PostCardView.CardTextStyle text4 = new PostCardView.CardTextStyle(Color.GREEN,33,160,200,10,false,"你好的拉家带口了飞机失联飞机额外饿哦如果老是打击");
        List<PostCardView.CardTextStyle> texts = new ArrayList<>();
        texts.add(text1);
        texts.add(text2);
        texts.add(text3);
        texts.add(text4);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.ic_launcher), 100, 100);
        return new PostCardView.CardStyle(bgStyle, texts);
    }
}
