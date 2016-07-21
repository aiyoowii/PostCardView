package com.cegrano.android.test;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cegrano.android.postcardview.R;


/**
 * Created by cegrano on 16/3/6.
 * 歌词卡片背景选择
 */
public class CardBgSelectorAdapter extends RecyclerView.Adapter {

    //推荐背景
    public static int[] mBgRes = {R.drawable.bg_audio_test_01, R.drawable.bg_audio_test_02, R.drawable.bg_audio_test_03, R.drawable.bg_audio_test_04};
    private int select = -1;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setSelect(int select) {
        this.select = select;
        notifyItemChanged(select);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return -1;
        else if (position == select)
            return 1;
        else
            return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == -1) {
            view = View.inflate(parent.getContext(), R.layout.item_audio_card_bg_album, null);
        } else if (viewType == 0) {
            view = View.inflate(parent.getContext(), R.layout.item_audio_card_bg_norm, null);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_audio_card_bg_seleted, null);
        }
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.iv_bg);
        if (position > 0)
            imageView.setImageResource(mBgRes[position - 1]);
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
        return mBgRes.length + 1;
    }


    public interface ItemClickListener {
        void onClick(int position);
    }
}
