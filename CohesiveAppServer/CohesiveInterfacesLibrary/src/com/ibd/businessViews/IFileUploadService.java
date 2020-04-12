/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.exceptions.BSValidationException;
import com.ibd.cohesive.util.exceptions.DBProcessingException;
import com.ibd.cohesive.util.exceptions.DBValidationException;
import java.io.IOException;
import javax.json.JsonObject;

/**
 *
 * @author IBD Technologies
 */
public interface IFileUploadService {
    public String EJBprocessing(String request);
    public String processing(String token,String useriD,String ServiceName,String Institute,String fileName)throws IOException,BSProcessingException,BSValidationException,DBValidationException,DBProcessingException ;

}
