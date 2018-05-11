package com.uestc.plugins;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by xyf on 2018/1/24.
 */
public class PluginsClassLoader {

    private URLClassLoader classLoader;

    public PluginsClassLoader(File[] files) {
        this.classLoader = createClassLoader(files);
    }

    private URLClassLoader createClassLoader(final File[] files) {

        URL[] elements = new URL[files.length];
        for(int i = 0; i < files.length; i++){
            File file = files[i];
            if (null != file && file.canRead() && file.isFile()){
                try {
                    URL element = file.toURI().normalize().toURL();
                    elements[i] = element;
                }catch (MalformedURLException e){

                }
            }
        }
        return URLClassLoader.newInstance(elements);
    }

    public Class<?> loadClass(String className) throws ClassNotFoundException{
        return classLoader.loadClass(className);
    }

    public URLClassLoader getClassLoader(){
        return classLoader;
    }
}
