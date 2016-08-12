package com.tyq.jiemian.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by tyq on 2016/5/15.
 */
public class BaseFragment extends Fragment {

    private loadMoreListener mLoadMoreListener;

    public interface loadMoreListener{
        void onLoadMore();
    }

    public void showToast(int msgResId) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), getString(msgResId), Toast.LENGTH_SHORT).show();
        }
    }

    public void showToast(String msg) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }


}
