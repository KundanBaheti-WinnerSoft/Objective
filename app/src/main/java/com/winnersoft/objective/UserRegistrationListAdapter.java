package com.winnersoft.objective;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class UserRegistrationListAdapter extends RecyclerView.Adapter<UserRegistrationListAdapter.MyViewHolder> {
    private ArrayList<HashMap<String, String>> arraymap;
    private Context context;
    private ItemClickListener clickListener;


    public UserRegistrationListAdapter(Context context, ArrayList<HashMap<String, String>> productname) {
        arraymap = productname;
        this.context = context;
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public UserRegistrationListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_reg_list_adapter, parent, false);
        return new UserRegistrationListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserRegistrationListAdapter.MyViewHolder holder, final int position) {

        Urllink urllink = new Urllink();

//        String imageUrl = urllink.downloadProfilePic + arraymap.get(position).get("image");

//        Picasso.with(context).load(imageUrl).into(holder.ivProfilePic);

        holder.tvName.setText(arraymap.get(position).get("firstname")+" "+arraymap.get(position).get("lastname"));
        holder.tvNumber.setText(arraymap.get(position).get("mobileno"));

        ProfileDetails profileDetails = new ProfileDetails();
        profileDetails.setId(arraymap.get(position).get("id"));
        profileDetails.setRole(arraymap.get(position).get("role"));
//        profileDetails.setUserId(arraymap.get(position).get("studentId"));
        profileDetails.setFirstname(arraymap.get(position).get("firstname"));
        profileDetails.setMiddlename(arraymap.get(position).get("middlename"));
        profileDetails.setLastname(arraymap.get(position).get("lastname"));
        profileDetails.setMotherName(arraymap.get(position).get("mothername"));
        profileDetails.setBirthdate(arraymap.get(position).get("birthdate"));
        profileDetails.setAdmissionDate(arraymap.get(position).get("admissiondate"));
        profileDetails.setGender(arraymap.get(position).get("gender"));
        profileDetails.setEmailid(arraymap.get(position).get("emailid"));
        profileDetails.setMobileno(arraymap.get(position).get("mobileno"));
        profileDetails.setBloodGroup(arraymap.get(position).get("bloodGroup"));
        profileDetails.setEmergencyMobile(arraymap.get(position).get("emergencymobileno"));
        profileDetails.setQualification(arraymap.get(position).get("qualification"));
        profileDetails.setCountry(arraymap.get(position).get("country"));
        profileDetails.setState(arraymap.get(position).get("state"));
        profileDetails.setCity(arraymap.get(position).get("city"));
        profileDetails.setAddress(arraymap.get(position).get("address"));
//        profileDetails.setLoginName(arraymap.get(position).get("loginName"));
        profileDetails.setActiveDeactive(Boolean.valueOf(arraymap.get(position).get("activeDeactive")));

//        holder.llProfileCard.setTag(profileDetails);
        //   holder.tvAdminStatus.setTag(lostReq);
    }

    @Override
    public int getItemCount() {
        return arraymap.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvName, tvNumber;
        LinearLayout llAdmin;
        ImageView ivProfilePic;

        public MyViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tv_name);
            tvNumber = view.findViewById(R.id.tv_number);
            llAdmin = view.findViewById(R.id.ll_admin);
            ivProfilePic = view.findViewById(R.id.iv_profile);

            llAdmin.setOnClickListener(this);
            //   tvReqStatus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }

}
