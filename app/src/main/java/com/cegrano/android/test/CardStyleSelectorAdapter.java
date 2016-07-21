package com.cegrano.android.test;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cegrano.android.postcardview.PostCardHelper;
import com.cegrano.android.postcardview.PostCardView;
import com.cegrano.android.postcardview.R;

import java.util.List;

/**
 * Created by cegrano on 16/3/6.
 * 歌词卡片模版样式选择
 */
public class CardStyleSelectorAdapter extends RecyclerView.Adapter {
    private int select;
    private ItemClickListener itemClickListener;

    private List<String> words;
    private Drawable bgDrawable;
    private Drawable bgSelectDrawable;

    public void setWords(List<String> words, Drawable bgDrawable) {
        this.words = words;
        if (words == null || words.isEmpty())
            this.words = null;
        this.bgDrawable = bgDrawable;
        this.bgSelectDrawable = new BitmapDrawable(((BitmapDrawable) bgDrawable).getBitmap());
    }


    // TODO: 16/3/6 传入当前背景和词

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setSelect(int select) {
        this.select = select;
        notifyItemChanged(select);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == select)
            return 1;
        else
            return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = View.inflate(parent.getContext(), R.layout.item_audio_card_style_norm, null);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_audio_card_style_seleted, null);
        }
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PostCardView postCardView = (PostCardView) holder.itemView.findViewById(R.id.post_card);
        postCardView.setCardStyle(PostCardHelper.getTemper(position, words));
        if (getItemViewType(position) == 0)
            postCardView.setBackgroundDrawable(bgDrawable);
        else
            postCardView.setBackgroundDrawable(bgSelectDrawable);
        // TODO: 16/3/6 set background
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pre = select;
                select = position;
                notifyItemChanged(pre);
                notifyItemChanged(select);
                if (itemClickListener != null) {
                    itemClickListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }


    public interface ItemClickListener {
        void onClick(int position);
    }
}
