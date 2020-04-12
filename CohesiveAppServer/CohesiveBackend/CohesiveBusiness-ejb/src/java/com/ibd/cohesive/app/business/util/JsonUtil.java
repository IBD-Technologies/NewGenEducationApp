/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.util;

import com.ibd.cohesive.util.exceptions.BSProcessingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author IBD Technologies
 */
public class JsonUtil {
    
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
   
    
    
}
