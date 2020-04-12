/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.readbuffer;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author DELL
 */
public class DBFileContent {
    String fileNameKey;
    //Map<String,Map<String, Map<String,ArrayList<String>>>> fileMap;
    ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>> fileMap; //Key is TableName and Value is Map of (Key - Primary key,Values - DB record )

    public ConcurrentHashMap<String, ConcurrentHashMap<String, DBRecord>> getFileMap() {
        return fileMap;
    }
    DBFileContent pre;
    DBFileContent next;
    
    public DBFileContent(String fileNameKey, ConcurrentHashMap<String, ConcurrentHashMap<String,DBRecord>> fileMap){
        this.fileNameKey=fileNameKey;
        this.fileMap=fileMap;
    }
}
