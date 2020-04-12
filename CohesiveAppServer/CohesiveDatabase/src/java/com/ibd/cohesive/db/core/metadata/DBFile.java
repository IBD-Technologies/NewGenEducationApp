/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template DBFile, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.core.metadata;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author IBD Technologies
 */
public class DBFile {

    private int i_Fileid;
    private String i_FileType;
    private boolean i_index_required;
    private boolean i_online_indexing_required;
    private Map<String, DBTable> i_TableCollection = new HashMap();

    public int getI_Fileid() {
        return i_Fileid;
    }

    public String getI_FileType() {
        return i_FileType;
    }

    public Map<String, DBTable> getI_TableCollection() {
        return i_TableCollection;
    }

    public boolean isI_index_required() {
        return i_index_required;
    }

    public boolean isI_online_indexing_required() {
        return i_online_indexing_required;
    }
    
    

    public void setI_Fileid(int i_Fileid) {
        this.i_Fileid = i_Fileid;
    }

    public void setI_FileType(String i_FileType) {
        this.i_FileType = i_FileType;
    }

    public void setI_TableCollection(Map<String, DBTable> i_TableCollection) {
        this.i_TableCollection = i_TableCollection;
    }

    public void setI_index_required(boolean i_index_required) {
        this.i_index_required = i_index_required;
    }

    public void setI_online_indexing_required(boolean i_online_indexing_required) {
        this.i_online_indexing_required = i_online_indexing_required;
    }
    
    

} 

