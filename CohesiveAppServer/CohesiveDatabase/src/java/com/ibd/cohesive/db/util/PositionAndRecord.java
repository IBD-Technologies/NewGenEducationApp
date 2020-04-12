/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DELL
 */
public class PositionAndRecord {

    long i_position;
    ArrayList<String> i_records = new ArrayList<>();
    Map<String,String> i_position_of_filtered_records=new HashMap();
 

    public long getI_position() {
        return i_position;
    }

    public ArrayList<String> getI_records() {
        return i_records;
    }

    public void setI_position(long i_position) {
        this.i_position = i_position;
    }

    public void setI_records(ArrayList<String> i_records) {
        this.i_records = i_records;
    }

   /* public Map<String, String> getI_columnId_and_position() {
        return i_columnId_and_position;
    }

    public void setI_columnId_and_position(Map<String, String> i_columnId_and_position) {
        this.i_columnId_and_position = i_columnId_and_position;
    }*/

    public Map<String, String> getI_position_of_filtered_records() {
        return i_position_of_filtered_records;
    }

    public void setI_position_of_filtered_records(Map<String, String> i_position_of_filtered_records) {
        this.i_position_of_filtered_records = i_position_of_filtered_records;
    }
    

}

