/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.waitmonitor;

/**
 *
 * @author DELL
 */
public class DBWait {
    String fileName;
    String tableName;
    String primaryKey;
    String methodName;
    String className;
    String sessionID;
    long waitTime;
    String timeStamp;
    int dbReadBufferSize;
    int dbTempSegmentSize;
    int dbWriteBufferSize;
    int dbWaitBufferSize;
    boolean isSessionCompleted;

    public boolean isIsSessionCompleted() {
        return isSessionCompleted;
    }

    public void setIsSessionCompleted(boolean isSessionCompleted) {
        this.isSessionCompleted = isSessionCompleted;
    }

    public int getDbReadBufferSize() {
        return dbReadBufferSize;
    }

    public void setDbReadBufferSize(int dbReadBufferSize) {
        this.dbReadBufferSize = dbReadBufferSize;
    }

    public int getDbTempSegmentSize() {
        return dbTempSegmentSize;
    }

    public void setDbTempSegmentSize(int dbTempSegmentSize) {
        this.dbTempSegmentSize = dbTempSegmentSize;
    }

    public int getDbWriteBufferSize() {
        return dbWriteBufferSize;
    }

    public void setDbWriteBufferSize(int dbWriteBufferSize) {
        this.dbWriteBufferSize = dbWriteBufferSize;
    }

    public int getDbWaitBufferSize() {
        return dbWaitBufferSize;
    }

    public void setDbWaitBufferSize(int dbWaitBufferSize) {
        this.dbWaitBufferSize = dbWaitBufferSize;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
    
//    public DBWait(String fileName,String tableName,String primaryLey,String methodName,String className,String sessionID,long waitTime){
//        this.fileName=
//        
//    }

    public DBWait(String fileName, String tableName, String primaryKey, String methodName, String className, String sessionID, long waitTime,String timeStamp,int dbReadBufferSize,int dbTempSegmentSize,int dbWriteBufferSize ,int dbWaitBufferSize,boolean isSessionCompleted) {
        this.fileName = fileName;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.methodName = methodName;
        this.className = className;
        this.sessionID = sessionID;
        this.waitTime = waitTime;
        this.timeStamp=timeStamp;
        this.dbReadBufferSize=dbReadBufferSize;
        this.dbTempSegmentSize=dbTempSegmentSize;
        this.dbWriteBufferSize=dbWriteBufferSize;
        this.dbWaitBufferSize=dbWaitBufferSize;
        this.isSessionCompleted=isSessionCompleted;
    }
    
    
    
    
    public DBWait(String fileName, String tableName, String primaryKey, String methodName, String className, String sessionID, long waitTime,String timeStamp) {
        this.fileName = fileName;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.methodName = methodName;
        this.className = className;
        this.sessionID = sessionID;
        this.waitTime = waitTime;
        this.timeStamp=timeStamp;
    }
    
}
