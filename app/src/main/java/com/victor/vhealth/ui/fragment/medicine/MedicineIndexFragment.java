package com.victor.vhealth.ui.fragment.medicine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.victor.vhealth.R;
import com.victor.vhealth.base.ContentBaseFragment;
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
import com.victor.vhealth.ui.activity.DetailActivity;
import com.victor.vhealth.ui.activity.MapActivity;
import com.victor.vhealth.ui.activity.MedicineActivity;
import com.victor.vhealth.util.BitmapHelper;
import com.victor.vhealth.util.UIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MedicineIndexFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

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
    @ViewInject(R.id.lv_hospital)
    private GridView mGvHospital;
    @ViewInject(R.id.gv_pharmacy)
    private GridView mGvPharmacy;
    @ViewInject(R.id.gv_company)
    private GridView mGvCompany;
    @ViewInject(R.id.srf_medicine_index_refresh)
    private SwipeRefreshLayout mRefreshLayout;

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
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    private void initData() {
        initData(MedicineType.DRUG);
        initData(MedicineType.DISEASE);
        initData(MedicineType.HOSPITAL);
        initData(MedicineType.PHARMACY);
        initData(MedicineType.COMPANY);
    }

    private void initListener() {
        mTvDrugInfoMore.setOnClickListener(this);
        mTvDiseaseInfoMore.setOnClickListener(this);
        mTvPharmacyInfoMore.setOnClickListener(this);
        mTvHospitalInfoMore.setOnClickListener(this);
        mTvCompanyInfoMore.setOnClickListener(this);

        mGvDrug.setOnItemClickListener(this);
        mGvDisease.setOnItemClickListener(this);
        mGvHospital.setOnItemClickListener(this);
        mGvPharmacy.setOnItemClickListener(this);
        mGvCompany.setOnItemClickListener(this);
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
                            if (mDrugInfos != null && mDrugInfos.size() > 0) {
                                for (int i =0; i< GRIDVIEW_ITEM_COUNT; i++) {
                                    MedicineInfo medicineInfo = new MedicineInfo();
                                    medicineInfo.name = mDrugInfos.get(i).name;
                                    medicineInfo.img = mDrugInfos.get(i).img;
                                    medicineInfo.id = mDrugInfos.get(i).id;
                                    medicineInfos.add(medicineInfo);
                                }
                                UIUtils.runningOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mGvDrug.setAdapter(new MedicineIndexAdapter(medicineInfos));
                                    }
                                });
                            }
                            break;
                        case DISEASE:
                            DiseaseProtocol diseaseProtocol = new DiseaseProtocol(
                                    Constant.URL.MEDICINE_DISEASE_LIST, -1);
                            mDiseaseInfos = diseaseProtocol.loadData(1);
                            if (mDiseaseInfos != null && mDiseaseInfos.size() > 0) {
                                for (int i =0; i< GRIDVIEW_ITEM_COUNT; i++) {
                                    MedicineInfo medicineInfo = new MedicineInfo();
                                    medicineInfo.name = mDiseaseInfos.get(i).name;
                                    medicineInfo.img = mDiseaseInfos.get(i).img;
                                    medicineInfo.id = mDiseaseInfos.get(i).id;
                                    medicineInfos.add(medicineInfo);
                                }
                                UIUtils.runningOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mGvDisease.setAdapter(new MedicineIndexAdapter(medicineInfos));
                                    }
                                });
                            }
                            break;
                        case HOSPITAL:
                            HospitalProtocol hospitalProtocol = new HospitalProtocol(1,
                                    Constant.URL.MEDICINE_HOSPTIAL);
                            mHospitalInfos = hospitalProtocol.loadData(1);
                            if (mHospitalInfos != null && mHospitalInfos.size() > 0) {
                                for (int i =0; i< GRIDVIEW_ITEM_COUNT; i++) {
                                    MedicineInfo medicineInfo = new MedicineInfo();
                                    medicineInfo.name = mHospitalInfos.get(i).name;
                                    medicineInfo.img = mHospitalInfos.get(i).img;
                                    medicineInfo.id = mHospitalInfos.get(i).id;
                                    medicineInfos.add(medicineInfo);
                                }
                                UIUtils.runningOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mGvHospital.setAdapter(new MedicineIndexAdapter(medicineInfos));
                                    }
                                });
                            }
                            break;
                        case PHARMACY:
                            PharmacyProtocol pharmacyProtocol = new PharmacyProtocol(1,
                                    Constant.URL.MEDICINE_PHARMACY);
                            mPharmacyInfos = pharmacyProtocol.loadData(1);
                            if (mPharmacyInfos != null && mPharmacyInfos.size() > 0) {
                                for (int i =0; i< GRIDVIEW_ITEM_COUNT; i++) {
                                    MedicineInfo medicineInfo = new MedicineInfo();
                                    medicineInfo.name = mPharmacyInfos.get(i).name;
                                    medicineInfo.img = mPharmacyInfos.get(i).img;
                                    medicineInfo.id = mPharmacyInfos.get(i).id;
                                    medicineInfos.add(medicineInfo);
                                }
                                UIUtils.runningOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mGvPharmacy.setAdapter(new MedicineIndexAdapter(medicineInfos));
                                    }
                                });
                            }
                            break;
                        case COMPANY:
                            CompanyProtocol companyProtocol = new CompanyProtocol(1,
                                    Constant.URL.MEDICINE_COMPANY);
                            mCompanyInfos = companyProtocol.loadData(1);
                            if (mCompanyInfos != null && mCompanyInfos.size() > 0) {
                                for (int i =0; i< GRIDVIEW_ITEM_COUNT; i++) {
                                    MedicineInfo medicineInfo = new MedicineInfo();
                                    medicineInfo.name = mCompanyInfos.get(i).name;
                                    medicineInfo.img = mCompanyInfos.get(i).img;
                                    medicineInfo.id = mCompanyInfos.get(i).id;
                                    medicineInfos.add(medicineInfo);
                                }
                                UIUtils.runningOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mGvCompany.setAdapter(new MedicineIndexAdapter(medicineInfos));
                                    }
                                });
                            }
                            break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();

                } catch (HttpException e) {
                    e.printStackTrace();
                } finally {
                    UIUtils.runningOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            mRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(UIUtils.getContext(), MedicineActivity.class);;
        switch (v.getId()) {
            case R.id.tv_drug_info_more:
                intent.putExtra(MedicineActivity.MEDICINE_CALSSIFY_KEY, Constant.URL.MEDICINE_DRUG);
                startActivity(intent);
                break;
            case R.id.tv_disease_info_more:
                intent.putExtra(MedicineActivity.MEDICINE_CALSSIFY_KEY, Constant.URL.MEDICINE_DISEASE);
                startActivity(intent);
                break;
            case R.id.tv_hospital_info_more:
                intent.putExtra(MedicineActivity.MEDICINE_CALSSIFY_KEY, Constant.URL.MEDICINE_HOSPTIAL);
                startActivity(intent);
                break;
            case R.id.tv_pharmacy_info_more:
                intent.putExtra(MedicineActivity.MEDICINE_CALSSIFY_KEY, Constant.URL.MEDICINE_PHARMACY);
                startActivity(intent);
                break;
            case R.id.tv_company_info_more:
                intent.putExtra(MedicineActivity.MEDICINE_CALSSIFY_KEY, Constant.URL.MEDICINE_COMPANY);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(UIUtils.getContext(), DetailActivity.class);
        Intent intentToMap = new Intent(UIUtils.getContext(), MapActivity.class);
        switch (parent.getId()) {
            case R.id.gv_drug:
                int idd = (int) mDrugInfos.get(position).id;
                intent.putExtra(ContentBaseFragment.DATA_ID, idd);
                // 放入url里面的关键词，用于区分是药物、医院信息等……
                intent.putExtra(DetailActivity.CLASSIFY_KEY, Constant.URL.MEDICINE_DRUG);
                startActivity(intent);
                break;
            case R.id.gv_disease:
                int diseaseId = (int) mDiseaseInfos.get(position).id;
                intent.putExtra(ContentBaseFragment.DATA_ID, diseaseId);
                intent.putExtra(DetailActivity.CLASSIFY_KEY, Constant.URL.MEDICINE_DISEASE);
                startActivity(intent);
                break;
            case R.id.lv_hospital:
                HospitalInfo info = mHospitalInfos.get(position);
                int hospitalId = (int) info.id;
                intentToMap.putExtra(MapActivity.X_COORDINATE, info.x);
                intentToMap.putExtra(MapActivity.Y_COORDINATE, info.y);
                intentToMap.putExtra(MapActivity.HOSPITAL_NAME, info.name);
                intentToMap.putExtra(ContentBaseFragment.DATA_ID, hospitalId);
                intentToMap.putExtra(MapActivity.MAP_CLASSIFY_KEY, Constant.URL.MEDICINE_HOSPTIAL);
                startActivity(intentToMap);
                break;
            case R.id.gv_pharmacy:
                PharmacyInfo pharmacyInfo = mPharmacyInfos.get(position);
                int pharmacyId = (int) pharmacyInfo.id;
                intentToMap.putExtra(MapActivity.X_COORDINATE, pharmacyInfo.x);
                intentToMap.putExtra(MapActivity.Y_COORDINATE, pharmacyInfo.y);
                intentToMap.putExtra(MapActivity.HOSPITAL_NAME, pharmacyInfo.name);
                intentToMap.putExtra(ContentBaseFragment.DATA_ID, pharmacyId);
                intentToMap.putExtra(MapActivity.MAP_CLASSIFY_KEY, Constant.URL.MEDICINE_PHARMACY);
                startActivity(intentToMap);
                break;
            case R.id.gv_company:
                CompanyInfo companyInfo = mCompanyInfos.get(position);
                int companyId = (int) companyInfo.id;
                intentToMap.putExtra(MapActivity.X_COORDINATE, companyInfo.x);
                intentToMap.putExtra(MapActivity.Y_COORDINATE, companyInfo.y);
                intentToMap.putExtra(MapActivity.HOSPITAL_NAME, companyInfo.name);
                intentToMap.putExtra(ContentBaseFragment.DATA_ID, companyId);
                intentToMap.putExtra(MapActivity.MAP_CLASSIFY_KEY, Constant.URL.MEDICINE_COMPANY);
                startActivity(intentToMap);
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
