package com.sanjianke.mysjk.bussiness.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sanjianke.mysjk.R;
import com.sanjianke.mysjk.bean.MusicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选页面列表适配器
 * 显示首字母和包含的比赛信息
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MusicBean> mDatas = new ArrayList<>();

    public MusicAdapter(Context context, List<MusicBean> datas) {
        this.mInflater = LayoutInflater.from(context);
        mDatas = datas;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_music, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MusicBean musicBean = mDatas.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position, holder.itemView);
                }
                setSelect(position);
                notifyDataSetChanged();
            }
        });

        if (musicBean.isSelect()) {
            holder.itemView.setBackgroundColor(Color.parseColor("#50ff0000"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        holder.mTxt_musicName.setText((position+1)+". "+musicBean.getName());
        holder.mTxt_musicSize.setText(musicBean.getTime());
    }

    //选中条目
    public void setSelect(int pos) {
        if (mDatas != null) {
            for (int i = 0; i < mDatas.size(); i++) {
                if (pos == i) {
                    mDatas.get(i).setSelect(true);
                } else {
                    mDatas.get(i).setSelect(false);
                }
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxt_musicName, mTxt_musicSize;
        private CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTxt_musicName = (TextView) itemView.findViewById(R.id.mTxt_musicName);
            mTxt_musicSize = (TextView) itemView.findViewById(R.id.mTxt_musicSize);
            checkBox = (CheckBox) itemView.findViewById(R.id.mCheck);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, View view);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
