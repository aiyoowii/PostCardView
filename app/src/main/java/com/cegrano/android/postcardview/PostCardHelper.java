package com.cegrano.android.postcardview;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cegrano on 16/2/20.
 * helper
 */
public class PostCardHelper {
    public static PostCardView.CardStyle getTemper(int index){
        switch (index){
            case 0:
                return Temper1();
            default:
                return Temper1();
        }
    }

    private static PostCardView.CardStyle Temper1() {
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.BLUE,22,100,30,16,false,"你好的拉家带口了飞机失联飞机额外饿哦如果老是打击");
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.BLACK,33,190,200,100,true,"hello world! forever forever forever");
        PostCardView.CardTextStyle text3 = new PostCardView.CardTextStyle(Color.RED,44,60,500,60,true,"你好的拉家带口了飞机失联飞机额外饿哦如果老是打击");
        PostCardView.CardTextStyle text4 = new PostCardView.CardTextStyle(Color.GREEN,33,160,200,10,false,"你好的拉家带口了飞机失联飞机额外饿哦如果老是打击");
        List<PostCardView.CardTextStyle> texts = new ArrayList<>();
        texts.add(text1);
        texts.add(text2);
        texts.add(text3);
        texts.add(text4);
        return new PostCardView.CardStyle(R.mipmap.ic_launcher,texts);
    }
}
