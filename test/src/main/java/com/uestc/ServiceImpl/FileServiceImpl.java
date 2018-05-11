package com.uestc.ServiceImpl;

import com.uestc.Service.FileService;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import java.io.File;

/**
 * Created by lenovo on 2018/4/13.
 */
public class FileServiceImpl implements FileService,Referenceable {
    public File getFile() {
        System.out.println("==========you get a file==========");
        return null;
    }

    public Reference getReference() throws NamingException {
        Reference ref = new Reference(getClass().getName(),
                FileServiceFactory.class.getName(), null);
        return ref;
    }
}
