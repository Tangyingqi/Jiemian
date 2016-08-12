package com.tyq.jiemian.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tyq.greendao.HaveRead;
import com.tyq.jiemian.R;
import com.tyq.jiemian.bean.User;
import com.tyq.jiemian.databinding.ActivityMainBinding;
import com.tyq.jiemian.ui.fragment.CollectFragment;
import com.tyq.jiemian.ui.fragment.EntertainmentFragment;
import com.tyq.jiemian.ui.fragment.MainFragment;
import com.tyq.jiemian.ui.fragment.TechnologicalFragment;
import com.tyq.jiemian.ui.fragment.ZhiHuFragment;
import com.tyq.jiemian.utils.DBUtils;
import com.tyq.jiemian.utils.UserProxy;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView photo_iv;
    private TextView login_text;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActivityMainBinding dataBinding;
    private EntertainmentFragment entertainmentFragment;
    private TechnologicalFragment technologicalFragment;
    private ZhiHuFragment zhiHuFragment;
    private MainFragment mainFragment;
    private CollectFragment collectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
        switchFragment(0);

    }

    public void switchFragment(int fragmentIndex) {
        entertainmentFragment = (EntertainmentFragment) getSupportFragmentManager().findFragmentById(R.id.entertainment);
        technologicalFragment = (TechnologicalFragment) getSupportFragmentManager().findFragmentById(R.id.technological);
        zhiHuFragment = (ZhiHuFragment) getSupportFragmentManager().findFragmentById(R.id.zhihu);
        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        collectFragment = (CollectFragment) getSupportFragmentManager().findFragmentById(R.id.collect);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(mainFragment);
        fragmentTransaction.hide(entertainmentFragment);
        fragmentTransaction.hide(technologicalFragment);
        fragmentTransaction.hide(zhiHuFragment);
        fragmentTransaction.hide(collectFragment);

        switch (fragmentIndex) {
            default:
                fragmentTransaction.show(mainFragment);
                break;
            case 1:
                fragmentTransaction.show(technologicalFragment);
                technologicalFragment.onTabSelected();
                break;
            case 2:
                fragmentTransaction.show(entertainmentFragment);
                entertainmentFragment.onTabSelected();
                break;
            case 3:
                fragmentTransaction.show(zhiHuFragment);
                zhiHuFragment.onTabSelected();
                break;
            case 4:
                fragmentTransaction.show(collectFragment);
                collectFragment.onTabSelected();
                break;
        }
        fragmentTransaction.commit();
    }


    private void initView() {

        setSupportActionBar(dataBinding.toolbar);
        dataBinding.toolbar.setNavigationIcon(R.drawable.menu);

        mDrawerToggle = new ActionBarDrawerToggle(this, dataBinding.drawLayout, dataBinding.toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        dataBinding.drawLayout.addDrawerListener(mDrawerToggle);

        setupDrawerContent();
    }

    private void setupDrawerContent() {
        View header = LayoutInflater.from(this).inflate(R.layout.navigation_header, null);
        photo_iv = (ImageView) header.findViewById(R.id.photo_iv);
        photo_iv.setOnClickListener(v -> {

            if (!UserProxy.isLogin(MainActivity.this)) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, PersonalActivity.class));
            }

        });
        login_text = (TextView) header.findViewById(R.id.login_tv);

        dataBinding.navigationView.addHeaderView(header);
        dataBinding.navigationView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.nav_home:
                    switchFragment(0);
                    dataBinding.toolbar.setTitle("首页");
                    break;
                case R.id.nav_technological:
                    switchFragment(1);
                    dataBinding.toolbar.setTitle("科技");
                    break;
                case R.id.nav_entertainment:
                    dataBinding.toolbar.setTitle("娱乐");
                    switchFragment(2);
                    break;
                case R.id.nav_settings:
                    startActivity(new Intent(MainActivity.this,SettingActivity.class));
                    break;
                case R.id.nav_personal:
                    startActivity(new Intent(MainActivity.this, PersonalActivity.class));
                    break;
                case R.id.nav_robot:
                    startActivity(new Intent(MainActivity.this,TRClientActivity.class));
                case R.id.nav_zhihu:
                    switchFragment(3);
                    dataBinding.toolbar.setTitle("知乎日报");
                    break;
                case R.id.nav_collect:
                    switchFragment(4);
                    dataBinding.toolbar.setTitle("我的收藏");
                    break;
            }
            item.setChecked(true);
            dataBinding.drawLayout.closeDrawers();
            return true;

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserProxy.isLogin(this)) {
            User user = UserProxy.getCurrentUser(this);
            if (null != user) {
                login_text.setText(user.getUsername());
            }
        }else{
            login_text.setText(R.string.null_login);
        }
    }
}
