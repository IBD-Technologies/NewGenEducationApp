/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.businessViews;

import javax.json.JsonObject;

/**
 *
 * @author DELL
 */
public interface ITeacherSummaryService {
    public JsonObject EJBprocessing(JsonObject p_request,String service) ;
}
