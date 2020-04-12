/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.report.preprocessor;

import com.ibd.businessViews.IAppDataSet;
import com.ibd.businessViews.IBatchDataset;
import com.ibd.businessViews.IClassDataSet;
import com.ibd.businessViews.IClassDataSetBusiness;
import com.ibd.businessViews.IInstituteDataSet;
import com.ibd.businessViews.IInstituteDataSetBusiness;
import com.ibd.businessViews.IStudentDataSet;
import com.ibd.businessViews.IStudentDataSetBusiness;
import com.ibd.businessViews.ITeacherDataSet;
import com.ibd.businessViews.ITeacherDataSetBusiness;
import com.ibd.businessViews.IUserDataSet;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import javax.ejb.Local;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
@Local
public interface IPreProcessor {
    public boolean preProcessing(String token,String userID,String instituteID,String service)throws DBValidationException,BSValidationException,DBProcessingException,BSProcessingException;
    public  IAppDataSet getAppDataset()throws NamingException;
    public  IStudentDataSet getStudentDataset()throws NamingException;
    public  ITeacherDataSet getTeacherDataset()throws NamingException;
    public  IClassDataSet getClassDataset()throws NamingException;
    public  IInstituteDataSet getInstituteDataset()throws NamingException;
    public  IUserDataSet getUserDataset()throws NamingException;
    public  IClassDataSetBusiness getClassDataSetBusiness() throws NamingException;
    public  IStudentDataSetBusiness getStudentDataSetBusiness() throws NamingException;
    public  ITeacherDataSetBusiness getTeacherDataSetBusiness() throws NamingException;
    public  IBatchDataset getBatchDataset()throws NamingException;
    public  IInstituteDataSetBusiness getInstituteDataSetBusiness() throws NamingException;
}
