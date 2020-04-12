/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.web.Gateway.util;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author IBD Technologies
 */
@javax.ws.rs.ApplicationPath("CohesiveGateway")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.ibd.cohesive.web.Gateway.App.AppResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.App.AppsResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.ClassEntity.ClassEntitiesResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.ClassEntity.ClassEntityResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.ClassSummary.ClassSummaryEntitiesResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.ClassSummary.ClassSummaryEntityResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.ImageUpload.ImageUploadResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.ImageUpload.ImageUploadsResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Institute.InstituteResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Institute.InstitutesResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.InstituteSummary.InstituteSummaryEntitiesResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.InstituteSummary.InstituteSummaryEntityResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Report.ClassReport.ClassReportResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Report.ClassReport.ClassReportsResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Report.InstituteReport.InstituteReportResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Report.InstituteReport.InstituteReportsResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Report.StudentReport.StudentReportResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Report.StudentReport.StudentReportsResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Report.TableReport.TableReportResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Report.TableReport.TableReportsResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Report.TeacherReport.TeacherReportResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Report.TeacherReport.TeacherReportsResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Student.StudentResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Student.StudentsResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.StudentSummary.StudentSummaryEntitiesResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.StudentSummary.StudentSummaryEntityResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Teacher.TeacherResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.Teacher.TeachersResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.TeacherSummary.TeacherSummaryEntitiesResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.TeacherSummary.TeacherSummaryEntityResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.User.UserResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.User.UsersResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.UserSummary.UserSummaryEntitiesResource.class);
        resources.add(com.ibd.cohesive.web.Gateway.UserSummary.UserSummaryEntityResource.class);
    }
    
}
