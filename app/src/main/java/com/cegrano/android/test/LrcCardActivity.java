package com.cegrano.android.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cegrano.android.postcardview.DensityUtil;
import com.cegrano.android.postcardview.PostCardHelper;
import com.cegrano.android.postcardview.PostCardView;
import com.cegrano.android.postcardview.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LrcCardActivity extends Activity implements ViewTreeObserver.OnPreDrawListener, View.OnClickListener {

    public static final String LRC = "lrc";
    public static final String ARG_REVEAL_START_LOCATION = "start_location";
    public static final String CUR_LRC_POS = "current_lrc_position";
    PopupWindow mPopAlert;
    private LinearLayout llRoot;
    private RelativeLayout lyTitle;
    private ImageView ivBack;
    private TextView tvCancel;
    private TextView tvTitle;
    private TextView tvRightAction;
    private PostCardView postCard;
    private ImageView ivLrc;
    private ImageView ivBg;
    private ImageView ivStyle;
    private ImageView ivShare;
    private RecyclerView listSelector;
    private ArrayList<String> mLrc = new ArrayList<String>();
    private List<Integer> mSelectLrc = new ArrayList<Integer>();
    private int temperPos = 0;
    private List<String> words = new ArrayList<String>();
    private int bgPos = 1;
    private int[] mStartingLocation;
    private int mCurrentPos = 0;

    public static float hypo(float a, float b) {
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    public static void start(Context context, ArrayList<String> lrc) {
        Intent intent = new Intent(context, LrcCardActivity.class);
        intent.putStringArrayListExtra(LRC, lrc);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == savedInstanceState) {
            mStartingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
        }
        setContentView(R.layout.activity_audio_lrc_card);

        initView();

        getViewTreeObserver().addOnPreDrawListener(this);
    }

    protected ViewTreeObserver getViewTreeObserver() {
        return llRoot.getViewTreeObserver();
    }

    private void initView() {

        llRoot = (LinearLayout) findViewById(R.id.ll_root);
        lyTitle = (RelativeLayout) findViewById(R.id.ly_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRightAction = (TextView) findViewById(R.id.tv_right_action);
        postCard = (PostCardView) findViewById(R.id.post_card);
        ivLrc = (ImageView) findViewById(R.id.iv_lrc);
        ivBg = (ImageView) findViewById(R.id.iv_bg);
        ivStyle = (ImageView) findViewById(R.id.iv_style);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        listSelector = (RecyclerView) findViewById(R.id.list_selector);

        tvTitle.setText("歌词卡片");
        tvRightAction.setText("保存");
        tvRightAction.setVisibility(View.VISIBLE);

        //读取歌词及长按的歌词句子角标, 设置默认文字
        mLrc = getIntent().getStringArrayListExtra(LRC);
        mCurrentPos = getIntent().getIntExtra(CUR_LRC_POS, 0);
        words.add(mLrc.get(mCurrentPos));
        mSelectLrc.add(mCurrentPos);

        postCard.setCardStyle(PostCardHelper.getTemper(temperPos, words));

        postCard.setBackgroundResource(CardBgSelectorAdapter.mBgRes[bgPos - 1]);

        ivBack.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvRightAction.setOnClickListener(this);
        ivLrc.setOnClickListener(this);
        ivBg.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        postCard.setOnClickListener(this);
        ivStyle.setOnClickListener(this);
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_back) {
            finish();

        } else if (i == R.id.tv_cancel) {
            hideChangeStyle();

        } else if (i == R.id.tv_right_action) {
            if (listSelector.getVisibility() == View.VISIBLE)
                hideChangeStyle();
            else
                save();

        } else if (i == R.id.iv_lrc) {
            selectLrc();
            onShowChangeStyle("更改歌词");

        } else if (i == R.id.iv_bg) {
            selectBg();
            onShowChangeStyle("更换背景图");

        } else if (i == R.id.iv_style) {
            selectStyle();
            onShowChangeStyle("更换主题");

        } else if (i == R.id.iv_share) {

        } else if (i == R.id.post_card) {
            hideChangeStyle();

        }
    }

    private void onShowChangeStyle(String title) {
        tvRightAction.setText("完成");
        tvTitle.setText(title);
        ivBack.setVisibility(View.GONE);
        tvCancel.setVisibility(View.VISIBLE);
        listSelector.setVisibility(View.VISIBLE);
    }

    private void hideChangeStyle() {
        listSelector.setVisibility(View.GONE);
        listSelector.setAdapter(new EmptyAdapter());
        tvRightAction.setText("保存");
        tvTitle.setText("歌词卡片");
        ivBack.setVisibility(View.VISIBLE);
        tvCancel.setVisibility(View.GONE);
    }

    private void selectLrc() {
        CardLrcSelectorAdapter selectorAdapter = new CardLrcSelectorAdapter();
        listSelector.setLayoutManager(new LinearLayoutManager(this));
        listSelector.setAdapter(selectorAdapter);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listSelector.getLayoutParams();
        params.height = DensityUtil.dip2px(this, 220);
        listSelector.setLayoutParams(params);
//        listSelector.setVisibility(View.VISIBLE);
        selectorAdapter.setLrc(mLrc);
        selectorAdapter.setSelectLrc(mSelectLrc);
        selectorAdapter.setChangeListener(new CardLrcSelectorAdapter.LrcChangeListener() {
            @Override
            public void onChange(List<Integer> stringList) {
                mSelectLrc = stringList;
                words.clear();
                for (Integer i : mSelectLrc)
                    words.add(mLrc.get(i));
                postCard.setCardStyle(PostCardHelper.getTemper(temperPos, words));
            }

            @Override
            public void onLimit() {
                showAlert(R.layout.layout_audio_card_limit_tip);
            }
        });
    }

    private void showAlert(int viewRes) {

        if (mPopAlert != null)
            mPopAlert.dismiss();
        View v = View.inflate(LrcCardActivity.this, viewRes, null);
        mPopAlert = new PopupWindow(v, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        mPopAlert.setOutsideTouchable(true);
        mPopAlert.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        mPopAlert.setBackgroundDrawable(new BitmapDrawable());

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mPopAlert.showAtLocation(llRoot, Gravity.CENTER, 0, 0);
    }

    private void selectBg() {
        CardBgSelectorAdapter selectorAdapter = new CardBgSelectorAdapter();
        selectorAdapter.setSelect(bgPos);
        listSelector.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        listSelector.setAdapter(selectorAdapter);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listSelector.getLayoutParams();
        params.height = DensityUtil.dip2px(this, 100);
        listSelector.setLayoutParams(params);
//        listSelector.setVisibility(View.VISIBLE);
        selectorAdapter.setItemClickListener(new CardBgSelectorAdapter.ItemClickListener() {
            @Override
            public void onClick(int position) {
                if (position == 0) {
                    startActivityForResult(CardBackSelectActivity.newIntent(LrcCardActivity.this), 100);
                } else {
                    postCard.setBackgroundResource(CardBgSelectorAdapter.mBgRes[position - 1]);
                }
                bgPos = position;
            }
        });
    }

    private void selectStyle() {
        CardStyleSelectorAdapter selectorAdapter = new CardStyleSelectorAdapter();
        // 得用back的bitmap重新生产，不然会改编back的打小
        selectorAdapter.setWords(words, new BitmapDrawable(((BitmapDrawable) postCard.getBackground()).getBitmap()));
        selectorAdapter.setSelect(temperPos);
        listSelector.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        listSelector.setAdapter(selectorAdapter);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listSelector.getLayoutParams();
        params.height = DensityUtil.dip2px(this, 100);
        listSelector.setLayoutParams(params);
//        listSelector.setVisibility(View.VISIBLE);
        selectorAdapter.setItemClickListener(new CardStyleSelectorAdapter.ItemClickListener() {
            @Override
            public void onClick(int position) {
                temperPos = position;
                postCard.setCardStyle(PostCardHelper.getTemper(position, words));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    postCard.setBackgroundDrawable(new BitmapDrawable(data.getData().getPath()));
                    hideChangeStyle();
                    break;
            }
        }
    }

    private void save() {
        tvRightAction.setEnabled(false);
        try {
            postCard.saveViewToFile();
//            ToastUtil.showShort("保存成功");
            showAlert(R.layout.layout_audio_card_save_tip);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT);
        }
        tvRightAction.setEnabled(true);
    }

    @Override
    public boolean onPreDraw() {
        getViewTreeObserver().removeOnPreDrawListener(this);
        return true;
    }
}
