package com.winnersoft.objective;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ExamControllerAdapter extends RecyclerView.Adapter<ExamControllerAdapter.MyViewHolder> {
    private ArrayList<HashMap<String, String>> arraymap;
    private Context context;
    private ItemClickListener clickListener;

    public ExamControllerAdapter(Context context, ArrayList<HashMap<String, String>> productname) {
        arraymap = productname;
        this.context = context;
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ExamControllerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.examcontroller_adapter, parent, false);
        return new ExamControllerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ExamControllerAdapter.MyViewHolder holder, final int position) {

        Urllink urllink = new Urllink();

//        String imageUrl = urllink.downloadProfilePic + arraymap.get(position).get("image");

//        Picasso.with(context).load(imageUrl).into(holder.ivProfilePic);

        holder.cardview1.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));
        String Role = arraymap.get(position).get("userTypeName");
        if (Role.equalsIgnoreCase("SuperAdmin")) {
            holder.tvName.setText(arraymap.get(position).get("first_name") + " " + arraymap.get(position).get("middle_name") + " " + arraymap.get(position).get("last_name"));
            holder.tvMobNo.setText( arraymap.get(position).get("mobile"));
            holder.tvEmailId.setText( arraymap.get(position).get("email"));
            holder.tvAdmissionDate.setText( arraymap.get(position).get("admissionDate"));

        }else if (Role.equalsIgnoreCase("Exam Controller")){
            holder.tvName.setText(arraymap.get(position).get("first_name") + " " + arraymap.get(position).get("middle_name") + " " + arraymap.get(position).get("last_name"));
            holder.tvMobNo.setText( arraymap.get(position).get("mobile"));
            holder.tvEmailId.setText(arraymap.get(position).get("email"));
            holder.tvAdmissionDate.setText(arraymap.get(position).get("admissionDate"));

        }else if (Role.equalsIgnoreCase("student")){
            holder.tvName.setText(arraymap.get(position).get("first_name") + " " + arraymap.get(position).get("middle_name") + " " + arraymap.get(position).get("last_name"));
            holder.tvMobNo.setText( arraymap.get(position).get("mobile"));
            holder.tvEmailId.setText( arraymap.get(position).get("email"));
            holder.tvAdmissionDate.setText( arraymap.get(position).get("admissionDate"));

        }
//        else if (Role.equalsIgnoreCase("student")){
//            holder.tvName.setText(arraymap.get(position).get("first_name") + " " + arraymap.get(position).get("middle_name") + " " + arraymap.get(position).get("last_name"));
//            holder.tvMobNo.setText(context.getResources().getString(R.string.mobileNoColan) + arraymap.get(position).get("mobile"));
//            holder.tvEmailId.setText(context.getResources().getString(R.string.emailIdColan) + arraymap.get(position).get("email"));
//            holder.tvAdmissionDate.setText(context.getResources().getString(R.string.admissionDateColan) + arraymap.get(position).get("admissionDate"));
//
//        }
        ProfileDetails profileDetails = new ProfileDetails();
        profileDetails.setId(arraymap.get(position).get("id"));
        profileDetails.setRole(arraymap.get(position).get("userTypeName"));
//        profileDetails.setUserId(arraymap.get(position).get("studentId"));
        profileDetails.setFirstname(arraymap.get(position).get("first_name"));
        profileDetails.setMiddlename(arraymap.get(position).get("middle_name"));
        profileDetails.setLastname(arraymap.get(position).get("last_name"));
        profileDetails.setMotherName(arraymap.get(position).get("m_name"));
        profileDetails.setBirthdate(arraymap.get(position).get("birthdate"));
        profileDetails.setAdmissionDate(arraymap.get(position).get("admissionDate"));
        profileDetails.setGender(arraymap.get(position).get("gender"));
        profileDetails.setEmailid(arraymap.get(position).get("email"));
        profileDetails.setMobileno(arraymap.get(position).get("mobile"));
        profileDetails.setBloodGroup(arraymap.get(position).get("bloodGroup"));
        profileDetails.setEmergencyMobile(arraymap.get(position).get("emergencyMobNo"));
        profileDetails.setQualification(arraymap.get(position).get("highestQualification"));
        profileDetails.setCountry(arraymap.get(position).get("nationality"));
        profileDetails.setState(arraymap.get(position).get("state"));
        profileDetails.setCity(arraymap.get(position).get("city"));
        profileDetails.setAddress(arraymap.get(position).get("address"));
//        profileDetails.setLoginName(arraymap.get(position).get("loginName"));
        profileDetails.setActiveDeactive(Boolean.valueOf(arraymap.get(position).get("softDelete")));

        holder.llProfileCard.setTag(profileDetails);
        //   holder.tvAdminStatus.setTag(lostReq);
    }

    @Override
    public int getItemCount() {
        return arraymap.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvName, tvMobNo,tvEmailId,tvAdmissionDate;
        LinearLayout llProfileCard;
        CardView cardview1;
        public MyViewHolder(View view) {
            super(view);

            llProfileCard=view.findViewById(R.id.ll_profile_card);
            tvName = view.findViewById(R.id.tv_name);
            tvMobNo = view.findViewById(R.id.tv_mobile_no);
            tvEmailId = view.findViewById(R.id.tv_email_id);
            tvAdmissionDate = view.findViewById(R.id.tv_admission_date);
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
