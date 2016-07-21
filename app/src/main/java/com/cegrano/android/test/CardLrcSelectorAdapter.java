package com.cegrano.android.test;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cegrano.android.postcardview.PostCardHelper;
import com.cegrano.android.postcardview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cegrano on 16/3/6.
 * 歌词选择
 */
public class CardLrcSelectorAdapter extends RecyclerView.Adapter {
    public List<Integer> mSelectLrc = new ArrayList<Integer>();
    public List<String> mAllLrc = new ArrayList<String>();
    private LrcChangeListener changeListener;

    public void setLrc(List<String> list) {
        mAllLrc.clear();
        mAllLrc.addAll(list);
        notifyDataSetChanged();
    }

    public void setSelectLrc(List<Integer> list) {
        mSelectLrc.clear();
        mSelectLrc.addAll(list);
        notifyDataSetChanged();
    }

    public void setChangeListener(LrcChangeListener changeListener) {
        this.changeListener = changeListener;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mSelectLrc.contains((Integer) position))
            return 1;
        else
            return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0)
            view = View.inflate(parent.getContext(), R.layout.item_audio_card_lrc_norm, null);
        else
            view = View.inflate(parent.getContext(), R.layout.item_audio_card_lrc_select, null);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TextView text = (TextView) holder.itemView.findViewById(R.id.tv_lrc);
        final String lrcString = mAllLrc.get(position);
        text.setText(lrcString);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectLrc.contains((Integer) position)) {
                    mSelectLrc.remove((Integer) position);
                } else {
                    if (mSelectLrc.size() >= PostCardHelper.getTempLimit()) {
                        if (changeListener != null)
                            changeListener.onLimit();
                        return;
                    }
                    mSelectLrc.add((Integer) position);
                }
                notifyDataSetChanged();
                if (changeListener != null)
                    changeListener.onChange(mSelectLrc);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAllLrc.size();
    }


    public interface LrcChangeListener {
        void onChange(List<Integer> stringList);

        void onLimit();
    }
}
