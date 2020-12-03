package com.winnersoft.objective;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.MyViewHolder> {
    private ArrayList<HashMap<String, String>> arraymap;
    private Context context;
    private ItemClickListener clickListener;

    public CourseListAdapter(Context context, ArrayList<HashMap<String, String>> productname) {
        arraymap = productname;
        this.context = context;
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public CourseListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.courselist_adapter, parent, false);
        return new CourseListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.cardview1.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation));
        String Role = arraymap.get(position).get("userTypeName");
        holder.tvCourseCode.setText(arraymap.get(position).get("courseCode"));
        holder.tvCourseName.setText(arraymap.get(position).get("courseName"));
        holder.tvCourseDesc.setText(arraymap.get(position).get("courseDesc"));
        holder.tvCourseDuration.setText(arraymap.get(position).get("duration"));
        holder.tvCourseFees.setText(arraymap.get(position).get("fees"));
//        if (Role.equalsIgnoreCase("admin")) {
//            holder.tvCourseCode.setText(arraymap.get(position).get("mobile"));
//            holder.tvCourseName.setText(arraymap.get(position).get("email"));
//            holder.tvCourseDesc.setText(arraymap.get(position).get("admissionDate"));
//            holder.tvCourseDuration.setText(arraymap.get(position).get("admissionDate"));
//            holder.tvCourseFees.setText(arraymap.get(position).get("admissionDate"));
//
//        } else if (Role.equalsIgnoreCase("Exam Controller")) {
//            holder.tvCourseCode.setText(arraymap.get(position).get("mobile"));
//            holder.tvCourseName.setText(arraymap.get(position).get("email"));
//            holder.tvCourseDesc.setText(arraymap.get(position).get("admissionDate"));
//            holder.tvCourseDuration.setText(arraymap.get(position).get("admissionDate"));
//            holder.tvCourseFees.setText(arraymap.get(position).get("admissionDate"));
//
//
//        } else if (Role.equalsIgnoreCase("student")) {
//            holder.tvCourseCode.setText(arraymap.get(position).get("mobile"));
//            holder.tvCourseName.setText(arraymap.get(position).get("email"));
//            holder.tvCourseDesc.setText(arraymap.get(position).get("admissionDate"));
//            holder.tvCourseDuration.setText(arraymap.get(position).get("admissionDate"));
//            holder.tvCourseFees.setText(arraymap.get(position).get("admissionDate"));
//
//        }

        CourseDetails courseDetails=new CourseDetails();
        courseDetails.setCoursecode(arraymap.get(position).get("courseCode"));
        courseDetails.setCoursename(arraymap.get(position).get("courseName"));
        courseDetails.setCoursedesc(arraymap.get(position).get("courseDesc"));
        courseDetails.setCourseduration(arraymap.get(position).get("duration"));
        courseDetails.setCoursefees(arraymap.get(position).get("fees"));
        courseDetails.setId(arraymap.get(position).get("id"));
//        courseDetails.setCoursefees(Double.valueOf(arraymap.get(position).get("fees")));

//        ProfileDetails profileDetails = new ProfileDetails();
//        profileDetails.setId(arraymap.get(position).get("id"));
//        profileDetails.setRole(arraymap.get(position).get("userTypeName"));
////        profileDetails.setUserId(arraymap.get(position).get("studentId"));
//        profileDetails.setFirstname(arraymap.get(position).get("first_name"));
//        profileDetails.setMiddlename(arraymap.get(position).get("middle_name"));
//        profileDetails.setLastname(arraymap.get(position).get("last_name"));
//        profileDetails.setMotherName(arraymap.get(position).get("m_name"));
//        profileDetails.setBirthdate(arraymap.get(position).get("birthdate"));
        holder.llProfileCard.setTag(courseDetails);

    }

    @Override
    public int getItemCount() {
        return arraymap.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvCourseCode, tvCourseName,tvCourseDesc,tvCourseDuration,tvCourseFees;
        LinearLayout llProfileCard;
        CardView cardview1;
        public MyViewHolder(View view) {
            super(view);

            llProfileCard=view.findViewById(R.id.ll_profile_card);
            tvCourseCode = view.findViewById(R.id.tv_course_code);
            tvCourseName = view.findViewById(R.id.tv_course_name);
            tvCourseDesc = view.findViewById(R.id.tv_course_desc);
            tvCourseDuration = view.findViewById(R.id.tv_course_duration);
            tvCourseFees = view.findViewById(R.id.tv_course_fees);
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
