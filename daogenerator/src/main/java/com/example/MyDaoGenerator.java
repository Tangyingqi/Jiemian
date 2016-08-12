package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {
    public static void main(String[] args)throws Exception{
        //两个参数分别代表：数据库版本号，自动代码生成所在的包名
        Schema schema = new Schema(1,"com.tyq.greendao");

        addData(schema);

        //使用DaoGenerator的generateAll方法自动生成代码，会放入第二个参数的路径中
        new DaoGenerator().generateAll(schema,"/Android Sample/Jiemian/app/src/main/java-gen");

    }

    private static void addData(Schema schema){
        //一个实体类就代表一张表，此处表名为HaveRead
        Entity haveReadNews = schema.addEntity("HaveRead");

        //生成表的字段
        haveReadNews.addIdProperty();
        haveReadNews.addStringProperty("title");
        haveReadNews.addStringProperty("imageUrl");
        haveReadNews.addStringProperty("url");

        Entity collectNews = schema.addEntity("Collect");

        collectNews.addIdProperty().autoincrement();
        collectNews.addStringProperty("title");
        collectNews.addStringProperty("imageUrl");
        collectNews.addStringProperty("url");
        collectNews.addDateProperty("time");
    }
}
