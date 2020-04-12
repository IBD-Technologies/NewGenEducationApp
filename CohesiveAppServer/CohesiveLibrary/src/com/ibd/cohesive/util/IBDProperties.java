/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author DELL
 */
public class IBDProperties {

    public Properties g_props = new Properties();

    public void loadProperties(String prop_FileName) throws FileNotFoundException, IOException {
        //g_props.load(new FileInputStream(System.getenv("ibdenv").concat("\\" + prop_FileName)));
        InputStream propertyStream=null;
        try
        {  
        propertyStream=this.getClass().getClassLoader().getResourceAsStream(prop_FileName);
      
        g_props.load(propertyStream);
        
        }
    finally{
            if(propertyStream!=null) 
              propertyStream.close();
    }
        
        
    }

    public String getProperty(String p_propertyName) {
        return getProperty(p_propertyName, null);
    }

    public String getProperty(String p_propertyName,
            String p_defaultValue) {
        String l_propertyValue = null;
        l_propertyValue = g_props.getProperty(p_propertyName);
        if (l_propertyValue != null && l_propertyValue.length() != 0) {
            return l_propertyValue;
        }
        return p_defaultValue;
    }

    public void setProperty(String p_propertyName, String p_propertyValue) {
        g_props.setProperty(p_propertyName, p_propertyValue);
    }

    public void clearProperties()
            throws IOException {
        g_props.clear();

    }

}
