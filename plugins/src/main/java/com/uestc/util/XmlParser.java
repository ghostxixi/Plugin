package com.uestc.util;

import com.uestc.plugins.Plugin;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

/**
 * Created by xyf on 2018/1/24.
 */
public class XmlParser {

    private static DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    private final static String filePath = "plugins.xml";
    private final static String plugin = "plugin";
    private final static String name = "name";
    private final static String jar = "jar";
    private final static String filter = "filter";

    private XmlParser(){}

    public static ArrayList<Plugin> parse() {
        Document document = null;
        ArrayList<Plugin> plugins = null;
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            document = builder.parse(XmlParser.class.getClassLoader().getResourceAsStream(filePath));
            Element rootElement = document.getDocumentElement();
            NodeList nodeList = rootElement.getElementsByTagName(plugin);
            if(nodeList != null)
            {
                plugins = new ArrayList<Plugin>();
                for (int i = 0 ; i < nodeList.getLength(); i++)
                {
                    Element element = (Element)nodeList.item(i);
                    NodeList node = element.getChildNodes();
                    Plugin plugin = parserPlugin(node);
                    if(plugin != null){
                        plugins.add(plugin);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return plugins;
    }

    private static Plugin parserPlugin(NodeList node) {
        Plugin plugin = null;
        if (node != null) {
            plugin = new Plugin();
            for (int j = 0; j < node.getLength(); j++) {
                if (node.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    if (node.item(j).getNodeName().equals(name)) {
                        plugin.setName(node.item(j).getFirstChild().getNodeValue());
                    } else if (node.item(j).getNodeName().equals(jar)) {
                        plugin.setJar(node.item(j).getFirstChild().getNodeValue());
                    } else if (node.item(j).getNodeName().equals(filter)) {
                        plugin.setFilter(node.item(j).getFirstChild().getNodeValue());
                    }
                }
            }
        }

        return plugin;
    }

}
