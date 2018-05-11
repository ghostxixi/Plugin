package com.uestc.ServiceImpl;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/**
 * Created by xyf on 2018/1/24.
 */
public class DBserviceFactory implements ObjectFactory{
    public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable<?, ?> env) throws Exception {
        if (obj instanceof Reference) {
            DBServiceImpl db = new DBServiceImpl();
            return db;
        }
        return null;
    }
}
