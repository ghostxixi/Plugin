package com.uestc;


import com.uestc.Service.DBService;
import com.uestc.Service.FileService;
import com.uestc.Service.LogService;


/**
 * Created by xyf on 2018/1/24.
 */
public class Client {

    public static void main(String[] args){
        Container container = Container.getContainer();
        container.init();
        try{
            DBService db = (DBService)container.lookup("com.uestc.Service.DBService");
            FileService file = (FileService)container.lookup("com.uestc.Service.FileService");
            LogService log = (LogService)container.lookup("com.uestc.Service.LogService");
            db.getConnection();
            file.getFile();
            log.getLog();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
