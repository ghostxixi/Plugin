package com.uestc.Context;

import com.sun.jndi.fscontext.FSContextFactory;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * Created by xyf on 2018/1/25.
 */
public class PluginContextFactory extends FSContextFactory {

    public PluginContextFactory() {
    }

    public static Context createContext(String var0, Hashtable var1) throws NamingException {
        String var2 = FSContextFactory.getFileNameFromURLString(var0);
        return new PluginContext(var2, var1);
    }

    protected Context createContextAux(String var1, Hashtable var2) throws NamingException {
        return createContext(var1, var2);
    }
}
