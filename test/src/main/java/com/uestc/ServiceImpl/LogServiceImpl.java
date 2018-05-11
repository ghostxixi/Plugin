package com.uestc.ServiceImpl;

import com.uestc.Service.LogService;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import java.util.logging.Logger;

/**
 * Created by lenovo on 2018/4/13.
 */
public class LogServiceImpl implements LogService,Referenceable {
    public Logger getLog() {
        System.out.println("********************you get a log***********");
        return null;
    }

    public Reference getReference() throws NamingException {
        Reference ref = new Reference(getClass().getName(),
                LogServiceFactory.class.getName(), null);
        return ref;
    }
}
