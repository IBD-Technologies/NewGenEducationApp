/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.core.metadata;

/**
 *
 * @author IBD Technologies
 */
public class DBColumn {

    private int i_ColumnID;
    private String i_ColumnName;
    private int i_ColumnLength;
    private String i_ColumnDataType;
    private String i_ColumnLevelRelationship;

    public int getI_ColumnID() {
        return i_ColumnID;
    }

    public String getI_ColumnName() {
        return i_ColumnName;
    }

    public int getI_ColumnLength() {
        return i_ColumnLength;
    }
    
    public String getI_ColumnDataType() {
        return i_ColumnDataType;
    }
    
    public String getI_ColumnLevelRelationship(){
        return i_ColumnLevelRelationship;
    }

    public void setI_ColumnID(int i_ColumnID) {
        this.i_ColumnID = i_ColumnID;
    }

    public void setI_ColumnName(String i_ColumnName) {
        this.i_ColumnName = i_ColumnName;
    }

    public void setI_ColumnLength(int i_ColumnLength) {
        this.i_ColumnLength = i_ColumnLength;
    }

    public void setI_ColumnDataType(String i_ColumnDataType) {
        this.i_ColumnDataType = i_ColumnDataType;
    }

    public void setI_ColumnLevelRelationship(String i_ColumnLevelRelationship){
        this.i_ColumnLevelRelationship=i_ColumnLevelRelationship;
    }
    
    }
    
    
    
    



