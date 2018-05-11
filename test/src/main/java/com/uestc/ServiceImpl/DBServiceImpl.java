package com.uestc.ServiceImpl;

import com.uestc.Service.DBService;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;


/**
 * Created by xyf on 2018/1/24.
 */
public class DBServiceImpl implements DBService,Referenceable{

    public Reference getReference() throws NamingException {
        Reference ref = new Reference(getClass().getName(),
                DBserviceFactory.class.getName(), null);
        return ref;
    }

    public void getConnection(){

        System.out.println("-----------------you get an connection--------------");
    }
}
