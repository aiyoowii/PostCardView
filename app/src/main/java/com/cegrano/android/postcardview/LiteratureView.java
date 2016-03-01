package com.cegrano.android.postcardview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cegrano on 16/2/24.
 * 相关诗歌散文
 */
public class LiteratureView extends FrameLayout{
    private static final int DEFAULT_DIVIDER_HEIGHT = 24;
    private static final int PRE = -1,CUR=0,NEXT=1;
    private View mHeader,mFooter;
    ViewGroup mMiddle;
    List<Literature> literatureList = new ArrayList<>();
    View preView,currentView,nextView,tempView;
    int curPos;
    boolean viewIsChanging;
    private int mDividerHeight = DEFAULT_DIVIDER_HEIGHT;
    private int mMidHeight;
    private int mFullHeight;
    private int mHalfHeight;
    private int mHeaderHeight;

    public LiteratureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        validateViews();
    }

    private void validateViews(){
        post(new Runnable() {
            @Override
            public void run() {
                validateViewsImps();
            }
        });
    }

    private void validateViewsImps() {
        mFullHeight = getHeight();
        mHeaderHeight = mHeader.getMeasuredHeight();
        mHalfHeight = mFullHeight - mHeaderHeight - mDividerHeight;
        mMidHeight = mHalfHeight - mHeaderHeight - mDividerHeight;
        notifyDateChange();
    }

    public void setLiteratureList(List<Literature> literatureList) {
        this.literatureList.clear();
        this.literatureList.addAll(literatureList);
    }

    private void onScrollUp(){
        mFooter.setVisibility(INVISIBLE);
        ViewPropertyAnimator curViewAnim = currentView.animate();
        curViewAnim.translationYBy(-mMidHeight - mDividerHeight);
        curViewAnim.setDuration(1000);
        ViewPropertyAnimator nextViewAnim = nextView.animate();
        nextViewAnim.translationYBy(-mMidHeight - mDividerHeight);
        nextViewAnim.setDuration(1000);
        curViewAnim.start();
        nextViewAnim.start();
        nextViewAnim.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                curPos++;
                tempView = preView;
                preView = currentView;
                currentView = nextView;
                nextView = tempView;
                setHeader(PRE,curPos - 1);
                resetContentView(nextView,NEXT,curPos+1);
                viewIsChanging = false;
            }
        });
    }

    private void onScrollDown(){
        mHeader.setVisibility(INVISIBLE);
        ViewPropertyAnimator curViewAnim = currentView.animate();
        curViewAnim.translationYBy(mMidHeight + mDividerHeight);
        curViewAnim.setDuration(1000);
        ViewPropertyAnimator nextViewAnim = preView.animate();
        nextViewAnim.translationYBy(mMidHeight + mDividerHeight);
        nextViewAnim.setDuration(1000);
        curViewAnim.start();
        nextViewAnim.start();
        nextViewAnim.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                curPos--;
                tempView = preView;
                preView = currentView;
                currentView = nextView;
                nextView = tempView;
                setHeader(NEXT,curPos + 1);
                resetContentView(preView,PRE,curPos-1);
                viewIsChanging = false;
            }
        });
    }

    private void resetContentView(View v,int index,int pos){
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.height = getViewHeight(pos);
        v.setLayoutParams(params);
        v.setY(getViewY(index, pos));
        setContent(v, pos);
        setHeader(index,pos);
    }

    private void setHeader(int index, int pos) {
        switch (index){
            case PRE:
                if (pos<0)
                    mHeader.setVisibility(INVISIBLE);
                else
                    mHeader.setVisibility(VISIBLE);
                setContent(mHeader,pos);
                break;
            case NEXT:
                if (pos>literatureList.size()-1)
                    mFooter.setVisibility(INVISIBLE);
                else
                    mFooter.setVisibility(VISIBLE);
                setContent(mFooter,pos);
                break;
        }
    }

    private void setContent(View v, int pos) {
        if (pos < 0 || pos >= literatureList.size())
            return;
        TextView title = (TextView) v.findViewById(R.id.tv_title);
        TextView author = (TextView) v.findViewById(R.id.tv_author);
        TextView content = (TextView) v.findViewById(R.id.tv_content);
        title.setText(literatureList.get(pos).getTitle());
        author.setText(literatureList.get(pos).getPublish_time() + "作者:" + literatureList.get(pos).getAuthor());
        if (content!=null)
        content.setText(literatureList.get(pos).getContent());
    }

    //全尺寸
    private void setViewFull(View v){
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.height = (int) (mMidHeight + mDividerHeight * 2 + mFooter.getHeight() * 2);
        v.setLayoutParams(params);
    }

    //半全尺寸
    private void setViewHalfFull(View v){
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.height = (int) (mMidHeight + mDividerHeight + mFooter.getHeight());
        v.setLayoutParams(params);
    }

    //正常尺寸
    private void setViewCenter(View v){
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.height = (int) (mMidHeight);
        v.setLayoutParams(params);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     *
     */
    private void initView() {
        initHeader();
        initMiddle();
        initFooter();

        addView(mMiddle, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.TOP));
        addView(mHeader, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.TOP));
        addView(mFooter, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM));
    }


    private void initFooter() {
        mFooter = View.inflate(getContext(), R.layout.literature_h, null);
        mFooter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewIsChanging) {
                    viewIsChanging = true;
                    onScrollUp();
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            viewIsChanging = false;
                        }
                    });
                }
            }
        });
    }

    private void initMiddle() {
//        mMiddle = View.inflate(getContext(),R.layout.literature_m,null);
        mMiddle = new FrameLayout(getContext());
        preView = View.inflate(getContext(),R.layout.literature_m,null);
        currentView = View.inflate(getContext(),R.layout.literature_m,null);
        nextView = View.inflate(getContext(),R.layout.literature_m,null);
        mMiddle.addView(preView);
        mMiddle.addView(currentView);
        mMiddle.addView(nextView);
    }

    private int getViewY(int index,int pos){
        int y = 0;
        switch (index){
            case PRE:
                y = -getViewHeight(pos) + getViewY(CUR,pos + 1) - mDividerHeight;
                break;
            case NEXT:
                y = getViewHeight(pos - 1) + getViewY(CUR,pos - 1) + mDividerHeight;
                break;
            case CUR:
                if (pos == 0){
                    y = 0;
                } else {
                    y = mHeaderHeight + mDividerHeight;
                }
                break;
        }
        return y;
    }

    private int getViewHeight(int pos){
        if (literatureList.size()<2){
            return mFullHeight;
        } else if (pos == 0 || pos == literatureList.size()-1) {
            return mHalfHeight;
        } else {
            return mMidHeight;
        }
    }

    private void initHeader() {
        mHeader = View.inflate(getContext(),R.layout.literature_h,null);
        mHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewIsChanging){
                    viewIsChanging = true;
                    onScrollDown();
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            viewIsChanging = false;
                        }
                    });
                }
            }
        });
    }

    /**
     * 设置中间区域背景
     * @param background 传bitmap
     */
    public void setBackground(Bitmap background){

    }

    /**
     * 某些api需要17以上
     * @param context RendScript需要的context
     * @param bitmap 原图
     * @return 高斯模糊处理过的bitmap
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blurBitmap(Context context,Bitmap bitmap){

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(context);

        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur
        blurScript.setRadius(25.f);

        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        //recycle the original bitmap
        bitmap.recycle();

        //After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;


    }


    public void notifyDateChange() {
        if (curPos>=literatureList.size())
            curPos = literatureList.size() - 1;
        resetContentView(currentView,CUR,curPos);
        resetContentView(preView,PRE,curPos-1);
        resetContentView(nextView,NEXT,curPos+1);
    }
}
