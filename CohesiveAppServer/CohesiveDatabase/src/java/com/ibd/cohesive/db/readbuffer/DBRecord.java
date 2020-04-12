/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.readbuffer;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class DBRecord {
    long position;

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public char getOperation() {
        return operation;
    }

    public void setOperation(char operation) {
        this.operation = operation;
    }

    public ArrayList<String> getRecord() {
        return record;
    }

    public void setRecord(ArrayList<String> record) {
        this.record = record;
    }
    char operation;
    ArrayList<String> record;
    
    public DBRecord(long position,ArrayList<String> record){
        this.position=position;
        this.record=record;
    }
    public DBRecord(long position,ArrayList<String> record,char operation){
        this.position=position;
        this.record=record;
        this.operation=operation;
    }
    
    
    public DBRecord(ArrayList<String> record){
        this.record=record;
    }
}
