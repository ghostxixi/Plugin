package com.uestc;

import com.uestc.plugins.PluginsManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by xyf on 2018/1/24.
 */
public class Container {

    private static Container container = null;
    private Context ctx;

    //防止new Container实例
    private Container(){}

    public static Container getContainer(){
        if(container == null){
            container = new Container();
        }
        return container;
    }

    public void init(){
        try {
            //从配置文件Properties.properties中读取配置
            InputStream in = Container.class.getClassLoader().getResourceAsStream("Properties.properties");
            Properties props = new Properties();
            props.load(in);
            // 初始化JNDI提供者。
            Hashtable<Object,Object> env = new Hashtable<Object,Object>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, props.getProperty("INITIAL_CONTEXT_FACTORY"));
            // fscontext的初始目录
            env.put(Context.PROVIDER_URL, props.getProperty("PROVIDER_URL"));
            ctx = new InitialContext(env);
            bindPlugins();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void bindPlugins() throws NamingException{
        PluginsManager pluginsManager = PluginsManager.getMgr();
        HashMap<String,Object> plugins = pluginsManager.getPlugins();
        Set<String> set = plugins.keySet();
        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            ctx.rebind(key, plugins.get(key));
        }
    }

    public Object lookup(String clazz) throws NamingException{
        return ctx.lookup(clazz);
    }

}
