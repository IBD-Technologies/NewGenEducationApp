/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataSet.dataModels.batch;

import com.ibd.cohesive.util.exceptions.DBProcessingException;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class BATCH_STATUS_HISTORY {
    String INSTITUTE_ID;
    String BATCH_NAME;
    String BUSINESS_DATE;
    String START_TIME;
    String END_TIME;
    String EOD_STATUS;
    String NO_OF_SUCCESS;
    String NO_FAILURES;
    String ERROR;
    String SEQUENCE_NO;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getBATCH_NAME() {
        return BATCH_NAME;
    }

    public void setBATCH_NAME(String BATCH_NAME) {
        this.BATCH_NAME = BATCH_NAME;
    }

    public String getBUSINESS_DATE() {
        return BUSINESS_DATE;
    }

    public void setBUSINESS_DATE(String BUSINESS_DATE) {
        this.BUSINESS_DATE = BUSINESS_DATE;
    }

    public String getSTART_TIME() {
        return START_TIME;
    }

    public void setSTART_TIME(String START_TIME) {
        this.START_TIME = START_TIME;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public String getEOD_STATUS() {
        return EOD_STATUS;
    }

    public void setEOD_STATUS(String EOD_STATUS) {
        this.EOD_STATUS = EOD_STATUS;
    }

    public String getNO_OF_SUCCESS() {
        return NO_OF_SUCCESS;
    }

    public void setNO_OF_SUCCESS(String NO_OF_SUCCESS) {
        this.NO_OF_SUCCESS = NO_OF_SUCCESS;
    }

    public String getNO_FAILURES() {
        return NO_FAILURES;
    }

    public void setNO_FAILURES(String NO_FAILURES) {
        this.NO_FAILURES = NO_FAILURES;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public String getSEQUENCE_NO() {
        return SEQUENCE_NO;
    }

    public void setSEQUENCE_NO(String SEQUENCE_NO) {
        this.SEQUENCE_NO = SEQUENCE_NO;
    }
    
     
}
