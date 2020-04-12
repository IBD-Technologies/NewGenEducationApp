/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.db.util;

/**
 *
 * @author IBD Technologies
 */
import com.ibd.cohesive.util.debugger.Debug;
import com.ibd.cohesive.util.session.CohesiveSession;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import java.util.Vector;
 
public class SFTPUtill {
    
    ChannelSftp c =null;
    Channel channel =null;
    Session session =null;
    Debug debug=null;
    JSch jsch =null;
    String user =null;
    String host =null;
    String privateKey =null;
    int port;
    
    public SFTPUtill(CohesiveSession cosession) throws Exception {
       // ChannelSftp c =null;
        //Channel channel =null;
        //Session session =null;
       try
       {
        cosession.createSessionObject();
        
         this.debug=cosession.getDebug();
            jsch = new JSch();
 
            this.user =cosession.getCohesiveproperties().getProperty("SFTPUSER") ;
            this.host = cosession.getCohesiveproperties().getProperty("STANDBYIP");
            this.port = Integer.parseInt(cosession.getCohesiveproperties().getProperty("STANDBYPORT"));
//To connect by key            
            this.privateKey = cosession.getCohesiveproperties().getProperty("STANDBYKEYPATH") ;
            jsch.addIdentity(privateKey);
 
            
            session = jsch.getSession(user, host, port);
//            this.debug=cosession.getDebug();
             java.util.Properties config = new java.util.Properties();
             config.put("StrictHostKeyChecking", "no");
             session.setConfig(config);
             session.connect();
//             channel = session.openChannel("sftp");
//            channel.setInputStream(System.in);
//            channel.setOutputStream(System.out);
//            channel.connect();
//            c = (ChannelSftp) channel;
            
         dbg("identity added ");
        }
        catch (Exception e) {
            dbg(e);
          throw e;  
        }
       finally{
           cosession.clearSessionObject();
       }        
               
    }   
  public void doSftpTransfer(CohesiveSession cosession,String sourceFileName,String destFilePath)throws JSchException,SftpException
  {       
           try {  
//            session = jsch.getSession(user, host, port);
            this.debug=cosession.getDebug();
// To Connect by pwd starts           
            //session.setPassword("H0ru5%~"); 
            
            dbg("SFTP session created.");
 
            // disabling StrictHostKeyChecking may help to make connection but makes it insecure
            // see http://stackoverflow.com/questions/30178936/jsch-sftp-security-with-session-setconfigstricthostkeychecking-no
//            // 
//            java.util.Properties config = new java.util.Properties();
//             config.put("StrictHostKeyChecking", "no");
//             session.setConfig(config);
// To Connect by pwd ends

//            try

//            session.connect();
//            dbg("session connected.....");
// 
            channel = session.openChannel("sftp");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect();
            dbg("shell channel connected....");
 
             c = (ChannelSftp) channel;
 
           // String fileName = "C:\\Raj\\work\\js\\samplesftp.txt";
            //c.put(sourceFileName, "/home/oracle/raj");
            c.put(sourceFileName,destFilePath);
           
           //c c.exit();
            dbg("SFTP done");
//           }catch(JSchException e){
//               
//               throw e;
        } catch (Exception e) {
            dbg(e);
            throw e;
        }
    finally{
          c.exit();
        dbg("sftp Channel exited.");
        channel.disconnect();
        dbg("Channel disconnected.");
//        session.disconnect();
//        dbg("Host Session disconnected.");  
        
        }
    
    }
    
public Long CheckFileAvailability(String destFilePath) throws JSchException
{
    try{
     session = jsch.getSession(user, host, port);
      dbg("session created.");
      java.util.Properties config = new java.util.Properties();
             config.put("StrictHostKeyChecking", "no");
             session.setConfig(config);
// To Connect by pwd ends
            session.connect();
            dbg("session connected.....");
 
            channel = session.openChannel("sftp");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect();
            c = (ChannelSftp) channel;
            Vector res = null;
            SftpATTRS attrs = null;
            res = c.ls(destFilePath);
            if(res==null||res.isEmpty())
            return 0L;    
        dbg("SFTP done");
             attrs = c.lstat(destFilePath);
        return attrs.getSize();
           // return true;
        
 
        } catch (Exception e) {
            dbg(e);
            return 0L;
        }
    finally{
          c.exit();
        dbg("sftp Channel exited.");
//        channel.disconnect();
//        dbg("Channel disconnected.");
//        session.disconnect();
//        dbg("Host Session disconnected.");  
        
        }
         
            
}       

public void destroySession(){
    
    
    
    channel.disconnect();
//        dbg("Channel disconnected.");
        session.disconnect();
//        dbg("Host Session disconnected.");  
    
    
}



public void destroyAndCreateSession()throws JSchException{
    
    try{
        
        try{
             session.disconnect();
        }catch(Exception ex){
            
        }
        
        
        jsch=null;
        jsch = new JSch();
        jsch.addIdentity(privateKey);
        
        session = jsch.getSession(user, host, port);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
    
    
    }catch(Exception ex){
        dbg(ex);
        throw ex;
    }
    
    
}








public void dbg(String p_Value) {

        debug.dbg(p_Value);

    }

    public void dbg(Exception ex) {

        debug.exceptionDbg(ex);

    }


    
}
