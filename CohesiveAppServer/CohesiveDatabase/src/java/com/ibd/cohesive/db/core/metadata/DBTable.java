/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.core.metadata;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author IBD Technologies
 */
public class DBTable {

    private String i_TableName;
    private int i_Tableid;
    private String i_Pkey;
    private Map<String, DBColumn> i_ColumnCollection = new HashMap<>();
    private String i_Relationship;
    private String i_file_names_for_the_change;
    private String i_online_change;

    public String getI_TableName() {
        return i_TableName;
    }

    public int getI_Tableid() {
        return i_Tableid;
    }

    public String getI_Pkey() {
        return i_Pkey;
    }
    

    public Map<String, DBColumn> getI_ColumnCollection() {
        return i_ColumnCollection;
    }

    public String getI_Relationship() {
        return i_Relationship;
    }

    public String getI_file_names_for_the_change() {
        return i_file_names_for_the_change;
    }

    public String getI_online_change() {
        return i_online_change;
    }

    
   public void setI_TableName(String i_TableName) {
        this.i_TableName = i_TableName;
    }

    public void setI_Tableid(int i_Tableid) {
        this.i_Tableid = i_Tableid;
    }

    public void setI_Pkey(String i_Pkey) {
        this.i_Pkey = i_Pkey;
    }
    

    public void setI_ColumnCollection(Map<String, DBColumn> i_ColumnCollection) {
        this.i_ColumnCollection = i_ColumnCollection;
    }

    public void setI_Relationship(String i_Relationship) {
        this.i_Relationship = i_Relationship;
    }

    public void setI_file_names_for_the_change(String i_file_names_for_the_change) {
        this.i_file_names_for_the_change = i_file_names_for_the_change;
    }

    public void setI_online_change(String i_online_change) {
        this.i_online_change = i_online_change;
    }

    
   

    
}
