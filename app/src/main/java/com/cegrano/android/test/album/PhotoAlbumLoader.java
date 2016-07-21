package com.cegrano.android.test.album;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cegrano
 * 相册加载
 */
public class PhotoAlbumLoader extends AsyncTaskLoader<List<Album>> {
    static final int _displayName = 0;
    static final int _id = 3;
    static final int _bucketId = 4;
    static final int _bucketName = 5;
    static final int _path = 6;
    static final int _date = 7;
    private static final String[] IMAGES = {
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.LATITUDE,
            MediaStore.Images.Media.LONGITUDE,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_TAKEN
    };

    public PhotoAlbumLoader(Context context) {
        super(context);
    }

    @Override
    public List<Album> loadInBackground() {
        List<Album> albumList = new ArrayList<Album>();
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGES, null, null, MediaStore.Images.Media.DATE_TAKEN + " desc");
        Map<String, Album> albumMap = new HashMap<String, Album>();
        Album albumPre;
        while (cursor.moveToNext()) {
            String dir_id;
            if (albumMap.containsKey(dir_id = cursor.getString(_bucketId))) {
                albumPre = albumMap.get(dir_id);
                albumPre.setCount(albumPre.getCount() + 1);
                albumPre.getPhotoList().add(new AlbumPhoto(cursor.getInt(_id), cursor.getString(_path), cursor.getString(_date), albumPre.getName()));
            } else {
                albumPre = new Album();
                albumPre.setName(cursor.getString(_bucketName));
                albumPre.setFirstPhoto(cursor.getInt(_id));
                albumPre.setCount(1);
                albumPre.getPhotoList().add(new AlbumPhoto(cursor.getInt(_id), cursor.getString(_path), cursor.getString(_date), albumPre.getName()));
                albumMap.put(dir_id, albumPre);
            }
        }
        cursor.close();
        for (Album album : albumMap.values()) {
            albumList.add(album);
        }
        return albumList;
    }
}
