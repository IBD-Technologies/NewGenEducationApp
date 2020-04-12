/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.web.Gateway.util;

import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author IBD Technologies
 */
public class WebUtility {
    
private Debug dbg;

    public Debug getDbg() {
        return dbg;
    }

    public void setDbg(Debug dbg) {
        this.dbg = dbg;
    }
    
 public Cookie getCookie(String name,HttpServletRequest request){
     dbg("Inside Get Cookie");
     dbg("Cookie Name:"+name);
  Cookie[] cookies=request.getCookies();
  if (cookies != null) {
    for (int i=0; i < cookies.length; i++) {
      Cookie c=cookies[i];
      if (c.getName().equals(name)) {
        return c;
      }
    }
  }
  return null;
}   
private JsonObject getJsonObject(HttpServletRequest request)throws BSProcessingException{
        
        //try(InputStream is = new ByteArrayInputStream(request.getBytes(Charset.forName("UTF-8")));
        try(InputStream is = request.getInputStream();
            JsonReader jsonReader = Json.createReader(is);)
        {  
        JsonObject jsonObject = jsonReader.readObject();
        
        return  jsonObject;  
        
        }catch(IOException ex){
             
            throw new BSProcessingException("IOException"+ex.toString());
        }
        } 
public JsonObject getJsonObject(String p_request)throws BSProcessingException{
        
        try(InputStream is = new ByteArrayInputStream(p_request.getBytes(Charset.forName("UTF-8")));
            JsonReader jsonReader = Json.createReader(is);)    
                
                {
        
        
        JsonObject jsonObject = jsonReader.readObject();
        
        return  jsonObject;  
        
        }catch(IOException ex){
             
            throw new BSProcessingException("IOException"+ex.toString());
        }
        }
       
 
private void dbg(String p_value){
        dbg.dbg(p_value);
    }
     private void dbg(Exception ex){
        dbg.exceptionDbg(ex);
    }    
    
}
