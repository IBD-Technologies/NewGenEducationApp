/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.app.business.util;

import com.ibd.businessViews.BusinessEJBTemplate;
import com.ibd.businessViews.businessUtils.ExistingAudit;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import javax.json.JsonObject;

/**
 *
 * @author IBD Technologies
 */
public class BusinessEJB<T extends BusinessEJBTemplate> {
    private T t;

    public void set(T t) { this.t = t; }
    public T get() { return t; }
    
    public void create() throws DBProcessingException, DBValidationException, BSProcessingException, BSValidationException{ t.create();}
    public void authUpdate() throws DBProcessingException, DBValidationException, BSProcessingException, BSValidationException{ t.authUpdate();}
    public void delete() throws DBProcessingException, DBValidationException, BSProcessingException, BSValidationException{ t.delete();}
    public void fullUpdate() throws DBProcessingException, DBValidationException, BSProcessingException, BSValidationException{ t.fullUpdate();}
    public void view() throws DBProcessingException, DBValidationException, BSProcessingException, BSValidationException{ t.view();}
    public JsonObject buildJsonResFromBO() throws DBProcessingException, DBValidationException, BSProcessingException, BSValidationException{ return t.buildJsonResFromBO();}
    public ExistingAudit getExistingAudit()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{return t.getExistingAudit();}
    public void relationshipProcessing()throws BSProcessingException,DBValidationException,DBProcessingException,BSValidationException{t.relationshipProcessing();};
//    public void createDefault()throws DBValidationException,DBProcessingException,BSProcessingException,BSValidationException{t.createDefault();};
}
