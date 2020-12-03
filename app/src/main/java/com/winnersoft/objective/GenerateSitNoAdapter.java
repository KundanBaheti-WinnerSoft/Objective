package com.winnersoft.objective;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class GenerateSitNoAdapter extends RecyclerView.Adapter<GenerateSitNoAdapter.MyViewHolder> {
    private ArrayList<HashMap<String, String>> arraymap;
    private Context context;
    private ItemClickListener clickListener;

    public GenerateSitNoAdapter(Context context, ArrayList<HashMap<String, String>> productname) {
        arraymap = productname;
        this.context = context;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public GenerateSitNoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.generatesitno_adapter, parent, false);
        return new GenerateSitNoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final GenerateSitNoAdapter.MyViewHolder holder, int position) {
        holder.cvGenerateSitNo.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));

        holder.tvBatch.setText(arraymap.get(position).get("batch"));
        holder.tvCourse.setText(arraymap.get(position).get("courseName"));
        holder.tvDateOfExam.setText(arraymap.get(position).get("dateOfExam"));
        holder.tvStartTime.setText(arraymap.get(position).get("sTime"));
        holder.tvEndTime.setText(arraymap.get(position).get("eTime"));
        holder.tvNoOfQue.setText(arraymap.get(position).get("noOfQuestions"));


        if (arraymap.get(position).get("seatnumber_allocation").equals("not_allocated")) {
//            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.white));
            holder.tvStatus.setBackground(context.getResources().getDrawable(R.drawable.startblue_roundback));
            holder.tvStatus.setText(context.getResources().getString(R.string.generateSitNo));

        } else if (arraymap.get(position).get("seatnumber_allocation").equals("allocated")) {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.white));
            holder.tvStatus.setBackground(context.getResources().getDrawable(R.drawable.grey_roundback));
            holder.tvStatus.setText(context.getResources().getString(R.string.sitNoGenerated));
        }

        GenerateSitNoDetail generateSitNoDetail = new GenerateSitNoDetail();
        generateSitNoDetail.setId(arraymap.get(position).get("id"));
        generateSitNoDetail.setCoursename(arraymap.get(position).get("courseName"));
        generateSitNoDetail.setBatch(arraymap.get(position).get("batch"));
        generateSitNoDetail.setNoofquestions(arraymap.get(position).get("noOfQuestions"));
        generateSitNoDetail.setTotalmarks(arraymap.get(position).get("totalMarks"));
        generateSitNoDetail.setDateofexam(arraymap.get(position).get("dateOfExam"));
        generateSitNoDetail.setDay(arraymap.get(position).get("day"));
        generateSitNoDetail.setStarttime(arraymap.get(position).get("sTime"));
        generateSitNoDetail.setEndtime(arraymap.get(position).get("eTime"));
        generateSitNoDetail.setSeatnumber_allocation(arraymap.get(position).get("seatnumber_allocation"));

        holder.tvStatus.setTag(generateSitNoDetail);
//        holder.cvGenerateSitNo.setTag(generateSitNoDetail);


    }

    @Override
    public int getItemCount() {
        return arraymap.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvBatch, tvStatus, tvCourse, tvDateOfExam, tvStartTime, tvEndTime, tvNoOfQue;
        CardView cvGenerateSitNo;

        public MyViewHolder(View view) {
            super(view);

            tvBatch = view.findViewById(R.id.tv_batch);
            tvStatus = view.findViewById(R.id.tv_status);
            tvCourse = view.findViewById(R.id.tv_course);
            tvDateOfExam = view.findViewById(R.id.tv_dateofexam);
            tvStartTime = view.findViewById(R.id.tv_start_time);
            tvEndTime = view.findViewById(R.id.tv_endtime);
            tvNoOfQue = view.findViewById(R.id.tv_no_of_questions);
            cvGenerateSitNo = view.findViewById(R.id.cv_generatesitno);

            tvStatus.setOnClickListener(this);
//            cvGenerateSitNo.setOnClickListener(this);
            //   tvReqStatus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }

}
