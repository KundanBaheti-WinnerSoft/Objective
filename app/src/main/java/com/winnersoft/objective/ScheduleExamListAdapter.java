package com.winnersoft.objective;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleExamListAdapter extends RecyclerView.Adapter<ScheduleExamListAdapter.MyViewHolder> {
    private ArrayList<HashMap<String, String>> arraymap;
    private Context context;
    private ItemClickListener clickListener;

    public ScheduleExamListAdapter(Context context, ArrayList<HashMap<String, String>> productname) {
        arraymap = productname;
        this.context = context;
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    @Override
    public ScheduleExamListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scheduleexam_list_adapter, parent, false);
        return new ScheduleExamListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvCourse.setText(arraymap.get(position).get("courseName"));
        holder.tvBatch.setText(arraymap.get(position).get("batch"));
        holder.tvNoOfQues.setText(arraymap.get(position).get("noOfQuestions"));
        holder.tvTotalMark.setText(arraymap.get(position).get("totalMarks"));
        holder.tvStartDate.setText(arraymap.get(position).get("dateOfExam"));
        holder.tvDay.setText(arraymap.get(position).get("day"));
        holder.tvStartTime.setText(arraymap.get(position).get("sTime"));
        holder.tvEndTime.setText(arraymap.get(position).get("eTime"));

        ScheduleExamDetails scheduleExamDetails=new ScheduleExamDetails();
        scheduleExamDetails.setCoursename(arraymap.get(position).get("courseName"));
        scheduleExamDetails.setBatch(arraymap.get(position).get("batch"));
        scheduleExamDetails.setNoofquestions(arraymap.get(position).get("noOfQuestions"));
        scheduleExamDetails.setTotalmarks(arraymap.get(position).get("totalMarks"));
        scheduleExamDetails.setDateofexam(arraymap.get(position).get("dateOfExam"));
        scheduleExamDetails.setDay(arraymap.get(position).get("day"));
        scheduleExamDetails.setStarttime(arraymap.get(position).get("sTime"));
        scheduleExamDetails.setEndtime(arraymap.get(position).get("eTime"));
        scheduleExamDetails.setId(arraymap.get(position).get("id"));


        holder.llProfileCard.setTag(scheduleExamDetails);


    }

    @Override
    public int getItemCount() {
        return arraymap.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvCourse, tvBatch,tvNoOfQues,tvTotalMark,tvStartDate,tvDay,tvStartTime,tvEndTime;
        LinearLayout llProfileCard;
        CardView cardview1;
        public MyViewHolder(View view) {
            super(view);

            llProfileCard=view.findViewById(R.id.ll_profile_card);
            tvCourse = view.findViewById(R.id.tv_course);
            tvBatch = view.findViewById(R.id.tv_batch);
            tvNoOfQues = view.findViewById(R.id.tv_no_of_questions);
            tvTotalMark = view.findViewById(R.id.tv_total_marks);
            tvStartDate = view.findViewById(R.id.tv_start_date);
            tvDay = view.findViewById(R.id.tv_day);
            tvStartTime = view.findViewById(R.id.tv_start_time);
            tvEndTime = view.findViewById(R.id.tv_end_date);
            cardview1=view.findViewById(R.id.cardview1);
            llProfileCard.setOnClickListener(this);
            //   tvReqStatus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }

}
