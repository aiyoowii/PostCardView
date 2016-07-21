package com.cegrano.android.postcardview;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cegrano on 16/2/20.
 * helper
 */
public class PostCardHelper {
    public static int COUNT = 6;

    private static List<String> words = new ArrayList<String>();
    private static int index = 0;

    public static PostCardView.CardStyle getTemper(int index, List<String> words) {
        if (words == null) {
            PostCardHelper.words.clear();
            PostCardHelper.words.add("新的战役即将开始");
            PostCardHelper.words.add("新的战役即将开始");
            PostCardHelper.words.add("新的战役即将开始");
            PostCardHelper.words.add("新的战役即将开始");
        } else {
            PostCardHelper.words = words;
        }
        PostCardHelper.index = index;
        switch (index) {
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

    public static int getTempLimit() {
        switch (index) {
            case 0:
                return 2;
            case 3:
                return 1;
            default:
                return 4;
        }
    }

    //ps depend on 1080p
    private static PostCardView.CardStyle Temper1() {
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 70 / 3f, -1, -1, 16, true, words.size() > 0 ? words.get(0) : "");
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 70 / 3f, -1, -1, 100, true, words.size() > 1 ? words.get(1) : "");
//        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 70 / 3f, 488 / 3f, 400 / 3f, 16, true, words.size() > 0 ? words.get(0) : "");
//        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 50 / 3f, 595 / 3f, 400 / 3f, 100, true, words.size() > 1 ? words.get(1) : "");
        List<PostCardView.CardTextStyle> texts = new ArrayList<PostCardView.CardTextStyle>();
        texts.add(text1);
        if (words.size() > 1)
        texts.add(text2);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.bg_card_temper1), (1080 - 465) / 6f, -1);
        return new PostCardView.CardStyle(bgStyle, texts);
    }

    private static PostCardView.CardStyle Temper2() {
//        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 44 / 3f, 540 / 3f, 635 / 3f, 16, false, words.size() > 0 ? words.get(0) : "");
//        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 60 / 3f, 540 / 3f, 700 / 3f, 100, false, words.size() > 1 ? words.get(1) : "");
//        PostCardView.CardTextStyle text3 = new PostCardView.CardTextStyle(Color.WHITE, 44 / 3f, 540 / 3f, 800 / 3f, 16, false, words.size() > 2 ? words.get(2) : "");
//        PostCardView.CardTextStyle text4 = new PostCardView.CardTextStyle(Color.WHITE, 60 / 3f, 540 / 3f, 865 / 3f, 100, false, words.size() > 3 ? words.get(3) : "");
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 60 / 3f, -1, -1, 16, false, words.size() > 0 ? words.get(0) : "");
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 60 / 3f, -1, -1, 100, false, words.size() > 1 ? words.get(1) : "");
        PostCardView.CardTextStyle text3 = new PostCardView.CardTextStyle(Color.WHITE, 60 / 3f, -1, -1, 16, false, words.size() > 2 ? words.get(2) : "");
        PostCardView.CardTextStyle text4 = new PostCardView.CardTextStyle(Color.WHITE, 60 / 3f, -1, -1, 100, false, words.size() > 3 ? words.get(3) : "");
        List<PostCardView.CardTextStyle> texts = new ArrayList<PostCardView.CardTextStyle>();
        texts.add(text1);
        if (words.size() > 1)
        texts.add(text2);
        if (words.size() > 2)
        texts.add(text3);
        if (words.size() > 3)
        texts.add(text4);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.bg_card_temper2), 115 / 3f, -1);
        return new PostCardView.CardStyle(bgStyle, texts);
    }

    private static PostCardView.CardStyle Temper3() {
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 62 / 3f, 50 / 3f, 142 / 3f, 16, true, words.size() > 0 ? words.get(0) : "");
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 42 / 3f, 142 / 3f, 142 / 3f, 100, true, words.size() > 1 ? words.get(1) : "");
        PostCardView.CardTextStyle text3 = new PostCardView.CardTextStyle(Color.WHITE, 62 / 3f, 214 / 3f, 142 / 3f, 166, true, words.size() > 2 ? words.get(2) : "");
        PostCardView.CardTextStyle text4 = new PostCardView.CardTextStyle(Color.WHITE, 42 / 3f, 306 / 3f, 142 / 3f, 100, true, words.size() > 3 ? words.get(3) : "");
        List<PostCardView.CardTextStyle> texts = new ArrayList<PostCardView.CardTextStyle>();
        texts.add(text1);
        texts.add(text2);
        texts.add(text3);
        texts.add(text4);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.bg_card_temper3), 50 / 3f, 104 / 3f);
        return new PostCardView.CardStyle(bgStyle, texts);
    }

    private static PostCardView.CardStyle Temper4() {
//        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 60 / 3f, 540 / 3f, 750 / 3f, 16, false, words.size() > 0 ? words.get(0) : "");
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 60 / 3f, -1, -1, 16, false, words.size() > 0 ? words.get(0) : "");
        List<PostCardView.CardTextStyle> texts = new ArrayList<PostCardView.CardTextStyle>();
        texts.add(text1);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.bg_card_temper4), 0, 0);
        return new PostCardView.CardStyle(bgStyle, texts);
    }

    private static PostCardView.CardStyle Temper5() {
//        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 50 / 3f, 540 / 3f, 610 / 3f, 16, false, words.size() > 0 ? words.get(0) : "");
//        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 50 / 3f, 540 / 3f, 700 / 3f, 100, false, words.size() > 1 ? words.get(1) : "");
//        PostCardView.CardTextStyle text3 = new PostCardView.CardTextStyle(Color.WHITE, 50 / 3f, 540 / 3f, 790 / 3f, 16, false, words.size() > 2 ? words.get(2) : "");
//        PostCardView.CardTextStyle text4 = new PostCardView.CardTextStyle(Color.WHITE, 50 / 3f, 540 / 3f, 880 / 3f, 100, false, words.size() > 3 ? words.get(3) : "");
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 50 / 3f, -1, -1, 16, false, words.size() > 0 ? words.get(0) : "");
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 50 / 3f, -1, -1, 100, false, words.size() > 1 ? words.get(1) : "");
        PostCardView.CardTextStyle text3 = new PostCardView.CardTextStyle(Color.WHITE, 50 / 3f, -1, -1, 16, false, words.size() > 2 ? words.get(2) : "");
        PostCardView.CardTextStyle text4 = new PostCardView.CardTextStyle(Color.WHITE, 50 / 3f, -1, -1, 100, false, words.size() > 3 ? words.get(3) : "");
        List<PostCardView.CardTextStyle> texts = new ArrayList<PostCardView.CardTextStyle>();
        texts.add(text1);
        if (words.size() > 1)
            texts.add(text2);
        if (words.size() > 2)
            texts.add(text3);
        if (words.size() > 3)
            texts.add(text4);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.bg_card_temper5), 0, -1);
        return new PostCardView.CardStyle(bgStyle, texts);
    }

    private static PostCardView.CardStyle Temper6() {
        String w1, w11, w4, w44;
        w1 = w11 = w4 = w44 = "";
        if (words.size() > 0) {
            if (words.get(0).length() > 2) {
                w1 = words.get(0).substring(0, 2);
                w11 = words.get(0).substring(2);
            } else {
                w1 = words.get(0);
            }
        }
        if (words.size() > 3) {
            if (words.get(3).length() > 2) {
                int len = words.get(3).length();
                w44 = words.get(3).substring(0, len - 2);
                w4 = words.get(3).substring(len - 2);
            } else {
                w4 = words.get(3);
            }
        }
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.WHITE, 60 / 3f, 68 / 3f, 182 / 3f, 2, false, w1, Paint.Align.LEFT);
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.WHITE, 48 / 3f, 68 / 3f, 284 / 3f, 100, false, w11, Paint.Align.LEFT);
        PostCardView.CardTextStyle text3 = new PostCardView.CardTextStyle(Color.WHITE, 48 / 3f, 68 / 3f, 374 / 3f, 16, false, words.size() > 1 ? words.get(1) : "", Paint.Align.LEFT);
        PostCardView.CardTextStyle text4 = new PostCardView.CardTextStyle(Color.WHITE, 48 / 3f, 1015 / 3f, 1045 / 3f, 100, false, words.size() > 2 ? words.get(2) : "", Paint.Align.RIGHT);
        PostCardView.CardTextStyle text5 = new PostCardView.CardTextStyle(Color.WHITE, 48 / 3f, 1015 / 3f, 1135 / 3f, 16, false, w44, Paint.Align.RIGHT);
        PostCardView.CardTextStyle text6 = new PostCardView.CardTextStyle(Color.WHITE, 60 / 3f, 1015 / 3f, 1225 / 3f, 2, false, w4, Paint.Align.RIGHT);
        List<PostCardView.CardTextStyle> texts = new ArrayList<PostCardView.CardTextStyle>();
        texts.add(text1);
        texts.add(text2);
        texts.add(text3);
        texts.add(text4);
        texts.add(text5);
        texts.add(text6);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.bg_card_temper6), 0, -2);
        return new PostCardView.CardStyle(bgStyle, texts);
    }

    private static PostCardView.CardStyle Temper0() {
        PostCardView.CardTextStyle text1 = new PostCardView.CardTextStyle(Color.BLUE, 22, 100, 30, 16, false, words.size() > 0 ? words.get(0) : "");
        PostCardView.CardTextStyle text2 = new PostCardView.CardTextStyle(Color.BLACK, 33, 190, 200, 100, true, words.size() > 1 ? words.get(1) : "");
        PostCardView.CardTextStyle text3 = new PostCardView.CardTextStyle(Color.RED, 44, 60, 500, 60, true, words.size() > 2 ? words.get(2) : "");
        PostCardView.CardTextStyle text4 = new PostCardView.CardTextStyle(Color.GREEN, 33, 160, 200, 10, false, words.size() > 3 ? words.get(3) : "");
        List<PostCardView.CardTextStyle> texts = new ArrayList<PostCardView.CardTextStyle>();
        texts.add(text1);
        texts.add(text2);
        texts.add(text3);
        texts.add(text4);
        PostCardView.CardBgStyle bgStyle = new PostCardView.CardBgStyle((R.mipmap.ic_launcher), 100, 100);
        return new PostCardView.CardStyle(bgStyle, texts);
    }
}
