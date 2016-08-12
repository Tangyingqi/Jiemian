package com.tyq.jiemian.ui.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tyq.greendao.Collect;
import com.tyq.jiemian.R;
import com.tyq.jiemian.adapter.CollectAdapter;
import com.tyq.jiemian.databinding.FragmentCollectBinding;
import com.tyq.jiemian.utils.DBUtils;

import java.util.List;

public class CollectFragment extends BaseFragment {

    private FragmentCollectBinding dataBinding;
    private CollectAdapter adapter;

    public CollectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_collect, container, false);
        adapter = new CollectAdapter(getActivity());
        dataBinding.recyclerView.setAdapter(adapter);
        return dataBinding.getRoot();
    }

    public void onTabSelected() {
        List<Collect> collects = DBUtils.getInstance(getActivity()).getCollectList();
        if (collects.size() > 0) {
            adapter.addAll(collects);
        }
    }

}
