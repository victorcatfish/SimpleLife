package com.victor.vhealth.ui.fragment.medicine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.domain.CompanyInfo;
import com.victor.vhealth.domain.DiseaseInfo;
import com.victor.vhealth.domain.DrugInfo;
import com.victor.vhealth.domain.HospitalInfo;
import com.victor.vhealth.domain.MedicineInfo;
import com.victor.vhealth.domain.PharmacyInfo;
import com.victor.vhealth.factory.ThreadPoolFactory;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.protocol.CompanyProtocol;
import com.victor.vhealth.protocol.DiseaseProtocol;
import com.victor.vhealth.protocol.DrugProtocol;
import com.victor.vhealth.protocol.HospitalProtocol;
import com.victor.vhealth.protocol.PharmacyProtocol;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MedicineIndexFragment extends Fragment implements View.OnClickListener {

    public static final int GRIDVIEW_ITEM_COUNT = 8;

    @ViewInject(R.id.tv_drug_info_more)
    private TextView mTvDrugInfoMore;
    @ViewInject(R.id.tv_disease_info_more)
    private TextView mTvDiseaseInfoMore;
    @ViewInject(R.id.tv_hospital_info_more)
    private TextView mTvHospitalInfoMore;
    @ViewInject(R.id.tv_pharmacy_info_more)
    private TextView mTvPharmacyInfoMore;
    @ViewInject(R.id.tv_company_info_more)
    private TextView mTvCompanyInfoMore;

    @ViewInject(R.id.gv_drug)
    private GridView mGvDrug;
    @ViewInject(R.id.gv_disease)
    private GridView mGvDisease;
    @ViewInject(R.id.gv_hospital)
    private GridView mGvHospital;
    @ViewInject(R.id.gv_pharmacy)
    private GridView mGvPharmacy;
    @ViewInject(R.id.gv_company)
    private GridView mGvCompany;
    private List<DrugInfo> mDrugInfos;
    private List<DiseaseInfo> mDiseaseInfos;
    private List<HospitalInfo> mHospitalInfos;
    private List<PharmacyInfo> mPharmacyInfos;
    private List<CompanyInfo> mCompanyInfos;
    private MedicineType mCurType;


    enum MedicineType {
        DRUG,
        DISEASE,
        HOSPITAL,
        PHARMACY,
        COMPANY
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicine_index, container, false);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(MedicineType.DRUG);
        initData(MedicineType.DISEASE);
        initData(MedicineType.HOSPITAL);
        initData(MedicineType.PHARMACY);
        initData(MedicineType.COMPANY);
        initListener();
    }

    private void initListener() {
        mTvDrugInfoMore.setOnClickListener(this);
        mTvDiseaseInfoMore.setOnClickListener(this);
        mTvPharmacyInfoMore.setOnClickListener(this);
        mTvHospitalInfoMore.setOnClickListener(this);
        mTvCompanyInfoMore.setOnClickListener(this);
    }

    private void initData(final MedicineType type) {

        ThreadPoolFactory.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<MedicineInfo> medicineInfos = new ArrayList<>();
                    switch (type) {
                        case DRUG:
                            DrugProtocol drugProtocol = new DrugProtocol(1,
                                    Constant.URL.MEDICINE_DRUG);
                            mDrugInfos = drugProtocol.loadData(1);
                            for (int i =0; i< GRIDVIEW_ITEM_COUNT; i++) {
                                MedicineInfo medicineInfo = new MedicineInfo();
                                medicineInfo.name = mDrugInfos.get(i).name;
                                medicineInfo.img = mDrugInfos.get(i).img;
                                medicineInfos.add(medicineInfo);
                            }
                            UIUtils.runningOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    mGvDrug.setAdapter(new MedicineIndexAdapter(medicineInfos));
                                }
                            });
                            break;
                        case DISEASE:
                            DiseaseProtocol diseaseProtocol = new DiseaseProtocol(
                                    Constant.URL.MEDICINE_DISEASE);
                            mDiseaseInfos = diseaseProtocol.loadData(1);
                            for (int i =0; i< GRIDVIEW_ITEM_COUNT; i++) {
                                MedicineInfo medicineInfo = new MedicineInfo();
                                medicineInfo.name = mDiseaseInfos.get(i).name;
                                medicineInfo.img = mDiseaseInfos.get(i).img;
                                medicineInfos.add(medicineInfo);
                            }
                            UIUtils.runningOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    mGvDisease.setAdapter(new MedicineIndexAdapter(medicineInfos));
                                }
                            });
                            break;
                        case HOSPITAL:
                            HospitalProtocol hospitalProtocol = new HospitalProtocol(1,
                                    Constant.URL.MEDICINE_HOSPTIAL);
                            mHospitalInfos = hospitalProtocol.loadData(1);
                            for (int i =0; i< GRIDVIEW_ITEM_COUNT; i++) {
                                MedicineInfo medicineInfo = new MedicineInfo();
                                medicineInfo.name = mHospitalInfos.get(i).name;
                                medicineInfo.img = mHospitalInfos.get(i).img;
                                medicineInfos.add(medicineInfo);
                            }
                            UIUtils.runningOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    mGvHospital.setAdapter(new MedicineIndexAdapter(medicineInfos));
                                }
                            });
                            break;
                        case PHARMACY:
                            PharmacyProtocol pharmacyProtocol = new PharmacyProtocol(1,
                                    Constant.URL.MEDICINE_PHARMACY);
                            mPharmacyInfos = pharmacyProtocol.loadData(1);
                            for (int i =0; i< GRIDVIEW_ITEM_COUNT; i++) {
                                MedicineInfo medicineInfo = new MedicineInfo();
                                medicineInfo.name = mPharmacyInfos.get(i).name;
                                medicineInfo.img = mPharmacyInfos.get(i).img;
                                medicineInfos.add(medicineInfo);
                            }
                            UIUtils.runningOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    mGvPharmacy.setAdapter(new MedicineIndexAdapter(medicineInfos));
                                }
                            });
                            break;
                        case COMPANY:
                            CompanyProtocol companyProtocol = new CompanyProtocol(1,
                                    Constant.URL.MEDICINE_COMPANY);
                            mCompanyInfos = companyProtocol.loadData(1);
                            for (int i =0; i< GRIDVIEW_ITEM_COUNT; i++) {
                                MedicineInfo medicineInfo = new MedicineInfo();
                                medicineInfo.name = mCompanyInfos.get(i).name;
                                medicineInfo.img = mCompanyInfos.get(i).img;
                                medicineInfos.add(medicineInfo);
                            }
                            UIUtils.runningOnUIThread(new Runnable() {
                                @Override
                                public void run() {
                                    mGvCompany.setAdapter(new MedicineIndexAdapter(medicineInfos));
                                }
                            });
                            break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (HttpException e) {
                    e.printStackTrace();
                }


            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_drug_info_more:
                Toast.makeText(getContext(), "更多药品", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_disease_info_more:
                Toast.makeText(getContext(), "更多疾病", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_hospital_info_more:
                Toast.makeText(getContext(), "更多医院", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_pharmacy_info_more:
                Toast.makeText(getContext(), "更多药店", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_company_info_more:
                Toast.makeText(getContext(), "更多药企", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    class MedicineIndexAdapter extends BaseAdapter {

        private List<MedicineInfo> mDatas;

        public MedicineIndexAdapter(List<MedicineInfo> datas) {
            mDatas = datas;
        }

        @Override
        public int getCount() {
            return GRIDVIEW_ITEM_COUNT;
        }

        @Override
        public MedicineInfo getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getContext(), R.layout.item_medicine_index, null);
            ImageView ivImg = (ImageView) view.findViewById(R.id.iv_medicine_img);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_medicine_title);

            BitmapHelper.display(ivImg, Constant.URL.IMG_BASE + mDatas.get(position).img);
            tvTitle.setText(mDatas.get(position).name);

            return view;
        }
    }
}
