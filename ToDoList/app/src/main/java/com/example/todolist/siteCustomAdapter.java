package com.example.todolist;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class siteCustomAdapter extends RecyclerView.Adapter<siteCustomAdapter.ViewHolder> {
    private ArrayList<SiteInform> siteInforms;
    private Context mContext;
    private DBHelper1 mDBHelper;

    public siteCustomAdapter(ArrayList<SiteInform> siteInforms, Context mContext) {
        this.siteInforms = siteInforms;
        this.mContext = mContext;
        mDBHelper = new DBHelper1(mContext);
    }

    @NonNull
    @Override
    public siteCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.site_list,parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull siteCustomAdapter.ViewHolder holder, int position) {
        holder.tv_siteName.setText(siteInforms.get(position).getSiteName());
        holder.tv_siteID.setText(siteInforms.get(position).getSiteID());
        holder.tv_sitePass.setText(siteInforms.get(position).getSitePass());
    }

    @Override
    public int getItemCount() {
        return siteInforms.size();
    }

    public void setData(ArrayList<SiteInform> newData) {
        siteInforms = newData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_siteName;
        private TextView tv_siteID;
        private TextView tv_sitePass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_siteName = itemView.findViewById(R.id.tv_siteName);
            tv_siteID = itemView.findViewById(R.id.tv_siteID);
            tv_sitePass = itemView.findViewById(R.id.tv_sitePass);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int num = getAdapterPosition();
                    SiteInform siteInform = siteInforms.get(num);

                    String[] strChoiceItems = {"수정", "삭제"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("원하는 작업을 선택하세요.");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which == 0){
                                //수정

                                Dialog dialog1 = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                                dialog1.setContentView(R.layout.dialog_siteinfrom);
                                EditText et_siteName = dialog1.findViewById(R.id.editTextText);
                                EditText et_siteID = dialog1.findViewById(R.id.editTextText2);
                                EditText et_sitePass = dialog1.findViewById(R.id.editTextText3);

                                Button btn_ok = dialog1.findViewById(R.id.button);

                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String siteName = et_siteName.getText().toString();
                                        String siteID = et_siteID.getText().toString();
                                        String sitePass = et_sitePass.getText().toString();

                                        String beforeSiteName = siteInform.getSiteName().toString();
                                        String beforeSiteID = siteInform.getSiteID().toString();

                                        mDBHelper.updateSite(siteName, siteID, sitePass, beforeSiteName, beforeSiteID);
                                        siteInform.setSiteName(siteName);
                                        siteInform.setSiteID(siteID);
                                        siteInform.setSitePass(sitePass);

                                        notifyItemChanged(num, siteInform);
                                        dialog1.dismiss();
                                        Toast.makeText(mContext, "수정 완료", Toast.LENGTH_SHORT).show();
                                    }
                                });                                dialog1.show();

                            }
                            else if (which == 1){
                                //삭제
                                String beforeSiteName = siteInform.getSiteName().toString();
                                String beforeSiteID = siteInform.getSiteID().toString();

                                mDBHelper.deletesiteInform(beforeSiteName, beforeSiteID);
                                siteInforms.remove(num);
                                notifyItemRemoved(num);
                                Toast.makeText(mContext,"목록 제거", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.show();
                }
            });
        }

        // 데이터 갱신을 위한 setData 메소드 추가

    }

    public void addItem(SiteInform _item){
        siteInforms.add(0,_item);
        notifyItemInserted(0);
    }


}
