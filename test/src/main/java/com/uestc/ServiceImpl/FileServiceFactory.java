package com.uestc.ServiceImpl;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/**
 * Created by lenovo on 2018/4/13.
 */
public class FileServiceFactory implements ObjectFactory {
    public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable<?, ?> env) throws Exception {
        if (obj instanceof Reference) {
            FileServiceImpl files = new FileServiceImpl();
            return files;
        }
        return null;
    }
}
