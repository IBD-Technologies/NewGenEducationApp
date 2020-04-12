/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.RestGateway.util;

import com.ibd.businessViews.IArchApplyStatusUpdate;
import com.ibd.businessViews.IArchShippingStatusUpdate;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author DELL
 */
public class DependencyInjection {
    
    private InitialContext contxt;
     
    
    public DependencyInjection() throws NamingException  {
        
        contxt = new InitialContext();
        
       }
    
    public IArchApplyStatusUpdate getIArchApplyStatusUpdate() throws NamingException{
         IArchApplyStatusUpdate archApply=(IArchApplyStatusUpdate)
                 contxt.lookup("java:app/CohesiveDatabase/ArchApplyStatusUpdate!com.ibd.businessViews.IArchApplyStatusUpdate");
         return archApply;
     }
     public IArchShippingStatusUpdate getIArchShippingStatusUpdate() throws NamingException{
         IArchShippingStatusUpdate archShipping=(IArchShippingStatusUpdate)
                 contxt.lookup("java:app/CohesiveDatabase/ArchShippingStatusUpdate!com.ibd.businessViews.IArchShippingStatusUpdate");
         return archShipping;
     }
    
}
