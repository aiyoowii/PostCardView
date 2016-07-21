package com.cegrano.android.test;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cegrano.android.postcardview.PostCardView;
import com.cegrano.android.postcardview.R;
import com.cegrano.android.test.album.Album;
import com.cegrano.android.test.album.AlbumPhoto;
import com.cegrano.android.test.album.PhotoAlbumLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CardBackSelectActivity extends Activity implements LoaderManager.LoaderCallbacks<List<Album>>, View.OnClickListener {

    private RelativeLayout lyTitle;
    private ImageView ivBack;
    private TextView tvCancel;
    private TextView tvTitle;
    private TextView tvRightAction;
    private GridView gridView;

    private AlbumAdapter mAdapter;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CardBackSelectActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_card_back_select);
        initView();

        ivBack.setVisibility(View.GONE);
        tvCancel.setVisibility(View.VISIBLE);
        tvTitle.setText("相机胶卷");
        tvRightAction.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_audio_camera_blue, 0, 0, 0);
        tvRightAction.setText("拍照");
        tvRightAction.setVisibility(View.VISIBLE);
        mAdapter = new AlbumAdapter();
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri intent = (Uri.parse("file://" + ((AlbumPhoto) mAdapter.getItem(position)).getPath()));
                startActivityForResult(ImageCropHelper.crop(intent, Uri.fromFile(new File(PostCardView.newPath())), 1080, 1560), 100);
            }
        });
        getLoaderManager().initLoader(0, null, this).forceLoad();
    }

    private void initView() {
        lyTitle = (RelativeLayout) findViewById(R.id.ly_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRightAction = (TextView) findViewById(R.id.tv_right_action);
        gridView = (GridView) findViewById(R.id.gridView);

        tvCancel.setOnClickListener(this);
        tvRightAction.setOnClickListener(this);
    }

    @Override
    public Loader<List<Album>> onCreateLoader(int id, Bundle args) {
        return new PhotoAlbumLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Album>> loader, List<Album> data) {
        List<AlbumPhoto> list = new ArrayList<AlbumPhoto>();
        if (!data.isEmpty()) {
            for (Album album : data) {
                list.addAll(album.getPhotoList());
            }
        }
        try {
            Collections.sort(list, new Comparator<AlbumPhoto>() {
                @Override
                public int compare(AlbumPhoto lhs, AlbumPhoto rhs) {
                    return rhs.getDate().compareTo(lhs.getDate());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter.addAllData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Album>> loader) {

    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_cancel) {
            setResult(RESULT_CANCELED);
            finish();

        } else if (i == R.id.tv_right_action) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//调用照相机
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(PostCardView.newPhotoPath())));
            this.startActivityForResult(intent, 101);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    if (null == data || null == data.getData()) {
                        LogUtil.e(data + "---" + null);
                        data = new Intent();
                        data.setData(Uri.fromFile(new File(PostCardView.newPath())));
                    }
                    LogUtil.e(data);
                    setResult(RESULT_OK, data);
                    finish();
//                    PostCardView.imageInsert(CardBackSelectActivity.this, data.getData().getPath());
                    break;
                case 101:
                    Uri from = null;
                    if (null == data || null == data.getData())
                        from = Uri.fromFile(new File(PostCardView.newPhotoPath()));
                    else
                        from = data.getData();

                    startActivityForResult(ImageCropHelper.crop(from, Uri.fromFile(new File(PostCardView.newPath())), 1080, 1560), 100);
                    break;
            }
        }
    }

    class AlbumAdapter extends android.widget.BaseAdapter {
        List<AlbumPhoto> mData = new ArrayList<AlbumPhoto>();

        public void addAllData(List<AlbumPhoto> list) {
            mData.clear();
            mData.addAll(list);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = View.inflate(parent.getContext(), R.layout.item_audio_album_photo, null);

            ImageView iv = (ImageView) convertView.findViewById(R.id.iv_photo);
//            View v = convertView.findViewById(R.id.v_select);
//            ImageLoaderUtil.showImgNormal(iv, "file://" + mData.get(position).getPath());
            iv.setImageURI(Uri.fromFile(new File(mData.get(position).getPath())));
            return convertView;
        }
    }
//
//    class AlbumAdapter extends SimpleBaseAdapter<AlbumPhoto> {
//
//        public AlbumAdapter(Context context) {
//            super(context);
//        }
//
//        @Override
//        public int getItemResource(int viewType) {
//            return R.layout.item_audio_album_photo;
//        }
//
//        @Override
//        public View getItemView(int position, View convertView, ViewHolder holder) {
//            ImageView iv = holder.getView(R.id.iv_photo);
//            View v = holder.getView(R.id.v_select);
//            ImageLoaderUtil.showImgNormal(iv, "file://" + getItem(position).getPath());
//
//            return convertView;
//        }
//    }
}
