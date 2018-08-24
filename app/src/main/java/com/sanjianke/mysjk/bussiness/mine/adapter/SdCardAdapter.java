package com.sanjianke.mysjk.bussiness.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.mylibrary.utils.Tool;
import com.dd.mylibrary.wedget.CircleImageView;
import com.sanjianke.mysjk.R;
import com.sanjianke.mysjk.bean.ClassificationBean;

import java.io.File;
import java.util.List;

/**
 * ***********************************************************
 * author: dd
 * 得分页面的展示信息，1、2、3三个状态
 * *************************************************************
 */
public class SdCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context mContext;
    public LayoutInflater mInflater;
    public List<File> mDatas;
    //有几种类型
    private static final int TYPE_FIRST = 0;
    private static final int TYPE_SECOND = 1;
    private static final int TYPE_THIRD = 2;

    public SdCardAdapter(Context context, List<File> datas) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    //返回当前位置的类型
    @Override
    public int getItemViewType(int position) {

        /**
         * 比赛状态：1--结束，2--未开赛，3，进行中
         */
        String type = 2 + "";
        if ("0".equals(type)) {
            return TYPE_FIRST;
        } else if ("1".equals(type)) {
            return TYPE_SECOND;
        } else {
            return TYPE_THIRD;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_FIRST:
                return new SpecialHolder(mInflater.inflate(R.layout.item_kind_first, parent, false));
            case TYPE_SECOND:
                return new NormalHolder(mInflater.inflate(R.layout.item_kind_second, parent, false));
            case TYPE_THIRD:
                return new BigImgHolder(mInflater.inflate(R.layout.adater_file, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            //今天
            case TYPE_FIRST:
                SpecialHolder specialHolder = (SpecialHolder) holder;
                specialHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Tool.isFastDoubleClick()) {
                            if (mListener != null) {
                                mListener.onItemClick(position, holder.itemView);
                            }
                        }
                    }
                });

//                Glide.with(mContext).load(R.drawable.min).into(((SpecialHolder) holder).mTxt_point);


                break;

            //明天
            case TYPE_SECOND:

                NormalHolder normalHolder = (NormalHolder) holder;
                normalHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Tool.isFastDoubleClick()) {
                            if (mListener != null) {
                                mListener.onItemClick(position, holder.itemView);
                            }
                        }
                    }
                });

                break;

            //昨天
            case TYPE_THIRD:
                BigImgHolder bigImgHolder = (BigImgHolder) holder;
                bigImgHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Tool.isFastDoubleClick()) {
                            if (mListener != null) {
                                mListener.onItemClick(position, holder.itemView);
                            }
                        }
                    }
                });

                File file = mDatas.get(position);
                bigImgHolder.mTxt_fileName.setText(file.getName());

                if (file.isDirectory()){
                    bigImgHolder.mImg_file.setImageResource(R.mipmap.file);
                }else {
                    bigImgHolder.mImg_file.setImageResource(R.mipmap.file_2);
                }


                break;
        }
    }


    class SpecialHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImg_left, mImg_right;
        private ImageView mTxt_point;
        private TextView mTxt_left, mTxt_postion, mTxt_win, mTxt_draw, mTxt_loss, mTxt_beginTime, mTxt_right, mTxt_showPass, mTxtOnLine, mTxt_guess, mTxt_finish;

        public SpecialHolder(View itemView) {
            super(itemView);

        }
    }

    class NormalHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImg_left, mImg_right;
        private TextView mTxt_left, mTxt_postion, mTxt_win, mTxt_draw, mTxt_loss, mTxt_beginTime, mTxt_right, mTxt_showPass;
        private LinearLayout ll_odds;


        public NormalHolder(View itemView) {
            super(itemView);

        }
    }

    class BigImgHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImg_left, mImg_right;
        private TextView mTxt_fileName;
        private ImageView mImg_file;
        public BigImgHolder(View itemView) {
            super(itemView);
            mTxt_fileName = itemView.findViewById(R.id.mTxt_fileName);
            mImg_file = itemView.findViewById(R.id.mImg_file);
//            mImg_left = (CircleImageView) itemView.findViewById(R.id.mImg_left);
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
