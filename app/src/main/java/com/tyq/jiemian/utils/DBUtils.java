package com.tyq.jiemian.utils;

import android.content.Context;

import com.tyq.greendao.Collect;
import com.tyq.greendao.CollectDao;
import com.tyq.greendao.DaoSession;
import com.tyq.greendao.HaveRead;
import com.tyq.greendao.HaveReadDao;
import com.tyq.jiemian.JMApplication;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by tyq on 2016/6/2.
 */
public class DBUtils {

    private static DBUtils instance;
    private HaveReadDao haveReadDao;
    private CollectDao collectDao;

    public static DBUtils getInstance(Context context) {
        if (instance == null) {
            instance = new DBUtils();
            DaoSession daoSession = JMApplication.getDaoSession(context);
            instance.haveReadDao = daoSession.getHaveReadDao();
            instance.collectDao = daoSession.getCollectDao();
        }
        return instance;
    }

    //添加数据（已读新闻）
    public void addNewsToHaveRead(HaveRead haveRead) {
        haveReadDao.insert(haveRead);
    }

    public List<HaveRead> getHaveReadList() {
        QueryBuilder<HaveRead> qb = haveReadDao.queryBuilder();
        return qb.list();
    }

    //插入收藏数据
    public void addNewsToCollect(Collect collect) {
        collectDao.insert(collect);
    }

    public List<Collect> getCollectList() {
        QueryBuilder<Collect> qb = collectDao.queryBuilder();
        return qb.list();
    }

    public void delectNews(Collect collect){
        collectDao.delete(collect);
    }
}
