/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.web.Gateway.ImageUpload;

import com.ibd.businessViews.IFileUploadService;
import com.ibd.businessViews.ITokenValidationService;
import com.ibd.cohesive.util.JWEInput;
import com.ibd.cohesive.util.exceptions.BSProcessingException;
import com.ibd.cohesive.util.session.CohesiveSession;
import com.ibd.cohesive.web.Gateway.util.CohesiveBeans;
import com.ibd.cohesive.web.Gateway.util.WebDI.DependencyInjection;
import com.ibd.cohesive.web.Gateway.util.WebUtility;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 * REST Web Service
 *
 * @author IBD Technologies
 */
public class ImageUploadResource {

    private String image;
    private CohesiveSession session;
    private DependencyInjection inject;
    private WebUtility webutil;

private static final String UPLOAD_FOLDER = "/img";
    /**
     * Creates a new instance of ImageUploadResource
     */
    private ImageUploadResource(String image) throws NamingException {
        this.image = image;
        session = new CohesiveSession("gateway.properties");
        inject = new DependencyInjection();
        webutil=new WebUtility();
    }

    /**
     * Get instance of the ImageUploadResource
     */
    public static ImageUploadResource getInstance(String image) throws NamingException {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of ImageUploadResource class.
        return new ImageUploadResource(image);
    }

    /**
     * Retrieves representation of an instance of com.ibd.cohesive.web.Filter.ImageUploadResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ImageUploadResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
public Response uploadFile(MultipartFormDataInput input) throws IOException, NamingException, BSProcessingException {
String response =null;
FileChannel fc=null;
  try
 {   
     session.createGatewaySessionObject();
   dbg("Inside uploadFile:"+session.getI_session_identifier());
   
   if (input == null || input.getParts() == null || input.getParts().isEmpty()) {
        throw new IllegalArgumentException("Multipart request is empty");
    }
   
    //CohesiveBeans bean =inject.getCohesiveBean(session);
    //IFileUploadService service =inject.getFileUploadService();
    String token=getFormValue("token",input);
    String userid=getFormValue("user",input);
    String instid=getFormValue("institute",input);
    String serviceName=getFormValue("service",input);
    
    dbg("token-->"+token);
        dbg("useriD-->"+userid);
        dbg("ServiceName-->"+serviceName);
        dbg("Institute-->"+instid);
        
    
    
 
    Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
    List<InputPart> inputParts =null;
    if(uploadForm.get("profileImg") !=null)
     inputParts = uploadForm.get("profileImg");
    else if(uploadForm.get("StudentprofileImg") !=null)
      inputParts = uploadForm.get("StudentprofileImg");
    else if(uploadForm.get("TeacherprofileImg") !=null)
      inputParts = uploadForm.get("TeacherprofileImg");
    else if(uploadForm.get("familyprofileImg") !=null)
      inputParts = uploadForm.get("familyprofileImg");
    else if(uploadForm.get("ContactprofileImg") !=null)
      inputParts = uploadForm.get("ContactprofileImg");
    else if(uploadForm.get("circularImg") !=null)
      inputParts = uploadForm.get("circularImg");
    else if(uploadForm.get("InstituteAssignmentFile") !=null)
      inputParts = uploadForm.get("InstituteAssignmentFile");
    InputStream inputStream =null;
    String fileName =null;
    byte[] bytes =null;
    
    for (InputPart inputPart : inputParts) {

try {
    MultivaluedMap<String, String> header = inputPart.getHeaders();

    fileName = getFileName(header);

    dbg("fileName --> "+fileName);
    inputStream = inputPart.getBody(InputStream.class, null);

     bytes= IOUtils.toByteArray(inputStream);
       
     inputStream.close();
}
catch (Exception e) {

    dbg(e);
    throw new RuntimeException(e);
}
finally
{
 if(inputStream!=null)
   inputStream.close();
}

    }
 
    
    if(bytes.length > Integer.parseInt(session.getCohesiveproperties().getProperty("MAX_UPLOAD_SIZE")))
    return Response.status(200).entity(
       "error~BS-VAL-104~File size is more, please contact system admin").build();
        
    boolean comeOutLoop = false;
            int iteratonCount = 0;
            while (comeOutLoop == false) {
                try {
                    iteratonCount = iteratonCount + 1;
                    IFileUploadService fileUpld= inject.getFileUploadService();
  response= fileUpld.processing(token,userid,serviceName,instid,fileName);
                  
  
  comeOutLoop = true;
                } catch (EJBException ex) {

                    if (iteratonCount <= 10) {

                        inject = null;
                        Thread.sleep(3000);
                        inject = new DependencyInjection();

                    } else {
                        throw ex;
                    }
                }
//                catch (Exception ex) {
//
//                    if (iteratonCount <= 10) {
//                        inject = null;
//                        Thread.sleep(3000);
//                        inject = new DependencyInjection();
//
//                    } else {
//                        throw ex;
//                    }
//                }

            }
  
 if(!response.contains("success"))
  return Response.status(200).entity(
        "error~"+response).build();
  
 else
 {
  String folderDelimiter=session.getCohesiveproperties().getProperty("FOLDER_DELIMITER");
  String uploadPath =null;
  
  //if (serviceName.equals("GeneralLevelConfiguration"))
  uploadPath="images" +folderDelimiter+session.getI_session_identifier().toString();  
  
  
  dbg("uploadPath -->"+uploadPath);
  Path dirPath = Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath);
              
  if (!Files.exists(dirPath))
     Files.createDirectory(dirPath);
   Path filePath=Paths.get(session.getCohesiveproperties().getProperty("UPLOAD_HOME_PATH") + uploadPath+folderDelimiter+fileName);
  if (Files.exists(filePath))
    Files.delete(filePath);
  Files.createFile(filePath);
             
  
  //using PosixFilePermission to set file permissions 755
       Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
       //add owners permission
       perms.add(PosixFilePermission.OWNER_READ);
       perms.add(PosixFilePermission.OWNER_WRITE);
       //perms.add(PosixFilePermission.OWNER_EXECUTE);
       //add group permissions
       //perms.add(PosixFilePermission.GROUP_READ);
       //perms.add(PosixFilePermission.GROUP_EXECUTE);
       //add others permissions
       //perms.add(PosixFilePermission.OTHERS_READ);
       //perms.add(PosixFilePermission.OTHERS_EXECUTE);
       
       Files.setPosixFilePermissions(filePath, perms);
  
  fc = FileChannel.open(filePath, WRITE);
  
  fc.write(ByteBuffer.wrap(bytes));

     response="success~"+session.getI_session_identifier().toString()+"~"+fileName;
 }  
     
 
   } 
  catch (Exception e) {

    dbg(e);
    throw new RuntimeException(e); 
//e.printStackTrace();

}
  finally
{
    session.clearSessionObject();
    if (fc !=null &&fc.isOpen())
      fc.close();
    //session.clearSessionObject();
}
 
return Response.status(200).entity(
        response).build();
  

}



private String getFileName(MultivaluedMap<String, String> header) {

String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

 

for (String filename : contentDisposition) {

 

if ((filename.trim().startsWith("filename"))) {

 

String[] name = filename.split("=");

 

String finalFileName = name[1].trim().replace("\"", "");

return finalFileName;

}

}

return "unknown";

}

// Utility method

private void writeFile(byte[] content, String filename) throws IOException {
File file = new File(filename);
dbg("filename inside writeFile-->"+filename);
if (!file.exists()) {
file.createNewFile();
}
else
{
file.delete();
file.createNewFile();
}
FileOutputStream fop = new FileOutputStream(file);
try
{   

fop.write(content);

fop.flush();
fop.close();

//System.out.println("Written: " + filename);
}
finally{
    fop.close();
}

}
    
private  String getFormValue(String fieldName,MultipartFormDataInput input) throws IOException 
{
    dbg("Inside getFormVale fieldName-->"+fieldName);
 Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

 List<InputPart> inputParts = uploadForm.get(fieldName);
String fieldValue=null;
for (InputPart inputPart : inputParts) {

  fieldValue= inputPart.getBody(String.class, null);

 dbg("Inside getFormVale fieldValue-->"+fieldValue);
}            
 return fieldValue;
 
}
    
    /**
     * DELETE method for resource ImageUploadResource
     */
    @DELETE
    public void delete() 
{
    throw new UnsupportedOperationException();
    }
private void dbg(String p_value){
        session.getDebug().dbg(p_value);
    }
     private void dbg(Exception ex){
        session.getDebug().exceptionDbg(ex);
    }    
    
    
}
