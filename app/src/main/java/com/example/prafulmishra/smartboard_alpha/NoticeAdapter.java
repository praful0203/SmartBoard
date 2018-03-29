package com.example.prafulmishra.smartboard_alpha;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private Context context;
    private List<DataGson> data = new ArrayList<>();
    public NoticeAdapter(Context context, List<DataGson> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list,parent,false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoticeViewHolder holder, int position) {

        DataGson noticedata = data.get(position);
        holder.lblId.setText(noticedata.getNoticeId());
        holder.lblNotice.setText(noticedata.getNotice());
        holder.lblTime.setText(noticedata.getTime());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {
        TextView lblId, lblNotice, lblTime;
        public NoticeViewHolder(View itemView) {
            super(itemView);
        lblId = itemView.findViewById(R.id.lblId);
        lblNotice = itemView.findViewById(R.id.lblNotice);
        lblTime = itemView.findViewById(R.id.lblTime);
        }
    }
}
