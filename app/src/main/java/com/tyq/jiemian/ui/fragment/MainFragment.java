package com.tyq.jiemian.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.tyq.jiemian.R;
import com.tyq.jiemian.adapter.MainAdapter;
import com.tyq.jiemian.bean.PopularNews;
import com.tyq.jiemian.databinding.FragmentMainBinding;
import com.tyq.jiemian.presenter.HomePresenter;
import com.tyq.jiemian.ui.activity.SearchActivity;
import com.tyq.jiemian.ui.callback.HomeInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {

    private FragmentMainBinding dataBinding;
    private MainAdapter adapter;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false);
        adapter = new MainAdapter(getActivity());
        dataBinding.recyclerView.setAdapter(adapter);
        initListener();
        loadBanner();
        return dataBinding.getRoot();
    }

    private void initListener() {
        dataBinding.floating.setOnClickListener(this);
    }

    private void loadBanner() {
        List<Integer> sliderImage = new ArrayList<>();
        sliderImage.add(R.mipmap.main_back1);
        sliderImage.add(R.mipmap.main_back2);
        sliderImage.add(R.mipmap.main_back3);
        sliderImage.add(R.mipmap.main_back4);
        sliderImage.add(R.mipmap.main_back5);
        dataBinding.convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView();
            }
        }, sliderImage)
        .startTurning(3000);
        Class cls = null;
        try {
            cls = Class.forName("com.ToxicBakery.viewpager.transforms."+ZoomOutTranformer.class.getSimpleName());
            ABaseTransformer transformer= (ABaseTransformer )cls.newInstance();
            dataBinding.convenientBanner.getViewPager().setPageTransformer(true,transformer);
            dataBinding.convenientBanner.setScrollDuration(1500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dataBinding.convenientBanner.startTurning(5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        dataBinding.convenientBanner.stopTurning();
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }


    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }

    }

}
