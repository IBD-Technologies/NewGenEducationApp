/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.scheduler;

import com.ibd.cohesive.db.readbuffer.DBRecord;
import java.util.Map;

/**
 *
 * @author DELL
 */
public class WorkDispatch {
    String fileName;
    Map<String,Map<String,DBRecord>>fileMap;

    public Map<String, Map<String, DBRecord>> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<String, Map<String, DBRecord>> fileMap) {
        this.fileMap = fileMap;
    }
    String result;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
    public WorkDispatch(String fileName,String result,Map<String,Map<String,DBRecord>>fileMap){
        this.fileName = fileName;
        this.result = result;
        this.fileMap=fileMap;
    }
    
    public WorkDispatch(String fileName,String result){
        this.fileName = fileName;
        this.result = result;
    }


    
    
    
}
