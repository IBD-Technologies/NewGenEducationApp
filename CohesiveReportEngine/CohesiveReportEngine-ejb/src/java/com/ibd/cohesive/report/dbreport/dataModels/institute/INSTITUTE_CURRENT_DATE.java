/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.dbreport.dataModels.institute;

import com.ibd.cohesive.report.businessreport.dataModels.teacher.SubstituteAvailabilityInOtherClass;
import java.util.ArrayList;

/**
 *
 * @author ibdtech
 */
public class INSTITUTE_CURRENT_DATE {
    
    String INSTITUTE_ID;
    String CURRENT_DATE;

    public String getINSTITUTE_ID() {
        return INSTITUTE_ID;
    }

    public void setINSTITUTE_ID(String INSTITUTE_ID) {
        this.INSTITUTE_ID = INSTITUTE_ID;
    }

    public String getCURRENT_DATE() {
        return CURRENT_DATE;
    }

    public void setCURRENT_DATE(String CURRENT_DATE) {
        this.CURRENT_DATE = CURRENT_DATE;
    }
    
    public ArrayList<INSTITUTE_CURRENT_DATE>convertStringToArrayList(String result){
        
          ArrayList<INSTITUTE_CURRENT_DATE> INSTITUTE_CURRENT_DATEList=new ArrayList();
          
          
          String[] records=result.split("#");
          
          for(int i=0;i<records.length;i++){
              
              String record=records[i];

              
              INSTITUTE_CURRENT_DATE appStatus=new INSTITUTE_CURRENT_DATE();
              
              appStatus.setCURRENT_DATE(record.split("~")[0]);
              appStatus.setINSTITUTE_ID(record.split("~")[1]);
              
              
              
              
           INSTITUTE_CURRENT_DATEList.add(appStatus);
          }
          
        return INSTITUTE_CURRENT_DATEList;
           
      
}
    
}
