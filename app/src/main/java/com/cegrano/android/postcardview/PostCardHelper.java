package com.cegrano.android.postcardview;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cegrano on 16/2/20.
 * helper
 */
public class PostCardHelper {
    public static int COUNT = 2;
    public static PostCardView.CardStyle getTemper(int index){
        switch (index){
            case 0:
                return Temper1();
            default:
                return Temper0();
        }
    }

    private static PostCardView.CardStyle Temper1() {
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 70, 488, 400, 16, true, "新的战役将要开始");
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 50, 595, 400, 100, true, "Here comes another fight");
        List<PostCardView.CardTextStyle> texts = new ArrayList<>();
        texts.add(text1);
        texts.add(text2);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.bg_card_temper1), 302, 182);
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
