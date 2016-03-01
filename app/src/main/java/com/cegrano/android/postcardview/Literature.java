package com.cegrano.android.postcardview;

/**
 * Created by cegrano on 16/2/29.
 * 文学赏析item实体
 */
public class Literature {
    public static int sid = 0;
    int id;
    String title;
    String author;
    String publish_time;
    String content;

    public Literature() {
        sid++;
        if (sid > 10)
            sid = 0;
        id = sid;
        title = "天空海阔" + id;
        author = "林子祥";
        publish_time = "1977-04-20";
        content = id + "远远当天有这小岛数个\n" +
                "艇里的歌透过清风远播\n" +
                "遥遥星光正似岸上灯火\n" +
                "总不少也总不多\n" +
                "季节匆匆转眼过\n" +
                "我记忆中听过基督爱我\n" +
                "店铺之中也有中西百货\n" +
                "前来居港移民逐渐增多\n" +
                "一家七口的居所\n" +
                "遍布山边挤满过\n" +
                "工方资方争执多\n" +
                "天星加斗令竟起了祸\n" +
                "怕见少壮变暴徒\n" +
                "你我拚过右与左\n" +
                "千七点股灾的风波ICAC的庆贺\n" +
                "这里那里有难民\n" +
                "炒金焉知福与祸\n" +
                "到了今天看这小岛数个\n" +
                "繁荣地位世界当中远播\n" +
                "原来好比渔船逐浪翻波\n" +
                "不尽千百尺巨浪趁风击浆总会过\n" +
                "九七掀起风波多\n" +
                "中英双方多唱和\n" +
                "这里那里国籍人\n" +
                "来来回回没结果\n" +
                "增加选举可不可\n" +
                "炒楼通宵街里坐\n" +
                "餸贵菜贵也奈何\n" +
                "真枪假枪怎么躲\n" +
                "再看今天寸寸光阴已过\n" +
                "这个港口有你始终有我\n" +
                "原来多少浮沉历尽风波\n" +
                "当中一切笑共泪\n" +
                "与你一起拥有过\n" +
                "任潮浪打来让我共你襄助\n" +
                "明日的风波也势必将它冲破\n" +
                "任潮浪打来让我共你襄助\n" +
                "明日的风波也要它好比当初\n" +
                "一起天空海阔里渡过";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
