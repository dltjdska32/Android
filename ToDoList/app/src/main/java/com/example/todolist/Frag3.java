package com.example.todolist;

import static java.lang.Math.log;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Frag3 extends Fragment {
    private View view;
    private DBHelper1 mDBhelper;
    private ArrayList<SiteInform> siteInforms;
    private FloatingActionButton btn_write;
    private RecyclerView rv_site;
    private siteCustomAdapter Adapter;
    Log log;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container, false);
        log.d("d112", "oncreate");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setInit();
    }


    private void setInit() {
        mDBhelper = new DBHelper1(view.getContext());

        rv_site = view.findViewById(R.id.rv_list);
        btn_write = view.findViewById(R.id.btn_write);
        siteInforms = new ArrayList<>();

        rv_site.setHasFixedSize(true);
        Adapter = new siteCustomAdapter(siteInforms, view.getContext());
        rv_site.setAdapter(Adapter);

        loadRecentDB();
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(v.getContext(), android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_siteinfrom);
                EditText et_siteName = dialog.findViewById(R.id.editTextText);
                EditText et_siteID = dialog.findViewById(R.id.editTextText2);
                EditText et_sitePass = dialog.findViewById(R.id.editTextText3);

                Button btn_ok = dialog.findViewById(R.id.button);

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDBhelper.insertMange(et_siteName.getText().toString(), et_siteID.getText().toString(), et_sitePass.getText().toString());

                        SiteInform item = new SiteInform();
                        item.setSiteName(et_siteName.getText().toString());
                        item.setSiteID(et_siteID.getText().toString());
                        item.setSitePass(et_sitePass.getText().toString());

                        Adapter.addItem(item);

                        rv_site.smoothScrollToPosition(0);

                        dialog.dismiss();
                        Toast.makeText(view.getContext(), "사이트 관리에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });
    }
    private void loadRecentDB() {
        siteInforms = mDBhelper.getsiteInformList();
        if (Adapter == null) {
            Adapter = new siteCustomAdapter(siteInforms, view.getContext());
            rv_site.setHasFixedSize(true);
            rv_site.setAdapter(Adapter);
        } else {
            Adapter.setData(siteInforms); // 이미 어댑터가 있으면 데이터를 갱신합니다.
            Adapter.notifyDataSetChanged();
        }
    }

}
