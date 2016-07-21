package com.cegrano.android.test.album;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cegrano
 */
public class Album implements Serializable {
    String name;
    long count;
    int firstPhoto;
    List<AlbumPhoto> photoList = new ArrayList<AlbumPhoto>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int xgetFirstPhoto() {
        return firstPhoto;
    }

    public void setFirstPhoto(int firstPhoto) {
        this.firstPhoto = firstPhoto;
    }

    public List<AlbumPhoto> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<AlbumPhoto> photoList) {
        this.photoList = photoList;
    }

    @Override
    public String toString() {
        return name;
    }
}
