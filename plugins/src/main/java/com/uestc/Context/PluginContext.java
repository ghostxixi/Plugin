package com.uestc.Context;

import com.sun.jndi.fscontext.RefFSContext;
import com.uestc.plugins.PluginsManager;

import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Created by xyf on 2018/1/25.
 */
public class PluginContext extends RefFSContext {

    public PluginContext(File var1, Hashtable var2) throws NamingException {
        super(var1, var2);
    }

    public PluginContext(String var1, Hashtable var2) throws NamingException {
        super(var1, var2);
    }

    private Properties getBindings(Name var1) throws NamingException {
        File var2 = null;

        try {
            var2 = this.getBindingsFile(var1, false);
        } catch (NameNotFoundException var6) {
            return new Properties();
        }

        try {
            if(!var2.canRead()) {
                return new Properties();
            }
        } catch (SecurityException var7) {
            throw this.generateNamingException(var7);
        }

        try {
            FileInputStream var3 = new FileInputStream(var2);
            Properties var4 = new Properties();
            var4.load(var3);
            var3.close();
            return var4;
        } catch (IOException var5) {
            throw this.generateNamingException(var5);
        }
    }

    private File getBindingsFile(Name var1, boolean var2) throws NamingException {
        Name var3 = (Name)var1.clone();
        var3.add(".bindings");

        try {
            return (File)super.lookup(var3);
        } catch (NameNotFoundException var5) {
            if(var2) {
                return this.getFile(var3);
            } else {
                throw var5;
            }
        } catch (ClassCastException var6) {
            throw new NameAlreadyBoundException("an object is already bound where the bindings should be stored");
        }
    }

    @Override
    public Object lookup(Name var1) throws NamingException {
        try {
            String var2 = var1.get(var1.size() - 1);
            Name var3 = var1.getPrefix(var1.size() - 1);
            Properties var4 = this.getBindings(var3);
            String var5 = var4.getProperty(var2 + "/" + "ClassName");
            Class<?> clazz = PluginsManager.getMgr().getPluginsClassLoader().loadClass(var5);
            return clazz.newInstance();
            } catch (Exception var3) {
                throw this.generateNamingException(var3, var1.toString());
            }
    }
}
