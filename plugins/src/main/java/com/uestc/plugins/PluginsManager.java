package com.uestc.plugins;


import com.uestc.Container;
import com.uestc.Service.DBService;
import com.uestc.util.XmlParser;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by xyf on 2018/1/24.
 */
public class PluginsManager {

    private static PluginsManager mgr = null;
    private PluginsClassLoader pluginsClassLoader = null;
    private ArrayList<ArrayList<String>> clazzs = null;
    private String[] filters = null;

    //防止new PluginsManager实例
    private PluginsManager(){
    }

    public PluginsClassLoader getPluginsClassLoader(){
        return pluginsClassLoader;
    }

    public static PluginsManager getMgr(){
        if(mgr == null){
           mgr = new PluginsManager();
        }
        if(mgr.pluginsClassLoader == null){
            mgr.init();
        }
        return mgr;
    }

    private void init(){
        System.out.println("PluginsManager init");
        ArrayList<Plugin> list = XmlParser.parse();
        if(list != null){
            File[] files = new File[list.size()];
            filters = new String[list.size()];
            for(int i = 0; i < files.length; i++){
                files[i] = new File(list.get(i).getJar());
                filters[i] = list.get(i).getFilter();
            }
            pluginsClassLoader = new PluginsClassLoader(files);
            parserJar(files,filters);
        }
    }

    private void parserJar(File[] files, String[] filters){

        clazzs = new ArrayList<ArrayList<String>>();
        try{
            for(int i = 0; i < files.length; i++) {
                JarFile jarFile = new JarFile(files[i]);
                Enumeration<JarEntry> jarEntries = jarFile.entries();
                ArrayList<String> clazz = new ArrayList<String>();
                while (jarEntries.hasMoreElements()) {
                    JarEntry jarEntry = jarEntries.nextElement();
                    if (!jarEntry.isDirectory()) {
                        String jarEntryName = jarEntry.getName();
                        String name = jarEntryName.replace("/", ".");
                        String clazzName = name.substring(0, name.lastIndexOf("."));
                        if(clazzName.contains(filters[i])){
                            clazz.add(clazzName);
                        }
                    }
                }
                clazzs.add(clazz);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public HashMap<String,Object> getPlugins(){
        HashMap<String,Object> map = new HashMap<String, Object>();
        try{
             for(int i = 0; i < clazzs.size(); i++) {
                 for(int j = 0; j < clazzs.get(i).size(); j++){
                     Class<?> clazz = pluginsClassLoader.loadClass(clazzs.get(i).get(j));
                     Class<?>[] interfaces = clazz.getInterfaces();
                     if (interfaces.length > 0) {
                         for (int k = 0; k < interfaces.length; k++) {
                             if(interfaces[k].getName().contains(filters[i])){
                                 Object obj = clazz.newInstance();
                                 map.put(interfaces[k].getName(), obj);
                             }
                         }
                     }
                 }
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args){
        getMgr();
        try{
            //从配置文件Properties.properties中读取配置
            InputStream in = Container.class.getClassLoader().getResourceAsStream("Properties.properties");
            Properties props = new Properties();
            props.load(in);
            // 初始化JNDI提供者。
            Hashtable<Object,Object> env = new Hashtable<Object,Object>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, props.getProperty("INITIAL_CONTEXT_FACTORY"));
            // fscontext的初始目录
            env.put(Context.PROVIDER_URL, props.getProperty("PROVIDER_URL"));
            Context a = new InitialContext(env);
            Class<?> db = mgr.pluginsClassLoader.loadClass("com.uestc.ServiceImpl.DBServiceImpl");
            a.rebind("DBService.xxx",db.newInstance());
            DBService s = (DBService)a.lookup("DBService.xxx");
            s.getConnection();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
