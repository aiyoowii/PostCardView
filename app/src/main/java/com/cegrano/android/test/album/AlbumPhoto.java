package com.cegrano.android.test.album;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by cegrano
 */
public class AlbumPhoto implements Parcelable, Serializable, Comparable<AlbumPhoto> {
    public static final Creator<AlbumPhoto> CREATOR = new Creator<AlbumPhoto>() {

        @Override
        public AlbumPhoto createFromParcel(Parcel source) {
            return new AlbumPhoto(source.readInt(), source.readString(), source.readString(), source.readString(), source.readInt());
        }

        @Override
        public AlbumPhoto[] newArray(int size) {
            return new AlbumPhoto[size];
        }
    };
    int id;
    String path;
    String date;
    String album;
    int select;

    public AlbumPhoto() {
    }

    public AlbumPhoto(int id, String path, String date, String album) {
        this.id = id;
        this.path = path;
        this.date = date;
        this.album = album;
    }

    public AlbumPhoto(int id, String path, String date, String album, int select) {
        this.id = id;
        this.path = path;
        this.date = date;
        this.album = album;
        this.select = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public boolean isSelect() {
        return select != 0;
    }

    public void setSelect(boolean select) {
        this.select = select ? 1 : 0;
    }

    @Override
    public int compareTo(AlbumPhoto another) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(path);
        dest.writeString(date);
        dest.writeString(album);
        dest.writeInt(select);
    }

}
