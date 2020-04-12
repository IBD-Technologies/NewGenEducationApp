/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibd.cohesive.util.debugger;

/**
 *
 * @author IBD Technologies
 */


import com.ibd.cohesive.util.IBDProperties;
import java.util.Calendar;
import java.util.Date;
import java.io.*;

/**
 <PRE>
 * Logger utility class used for logging messages.

 * It can be operated in three modes :-
 * 1. IF.LOGGER.ENABLED=Y
 *    Logging to a file, whose size is controlled by the IF.LOGGER.FILE_SIZE
 *    property [default being 1 MB].
 * 2. IF.LOGGER.ENABLED=N
 *    Printing on the console.
 * 3. IF.LOGGER.ENABLED=I
 *    Neither logging to a file nor printing on the console.

 * <B>Note:</B>
 * a. The exceptions are always logged to a file regardless of the utility's mode
 *    of operation.
 * b. IF.LOGGER.ENABLED and IF.LOGGER.FILE_SIZE mentioned above are the properties
 *    as defined in the properties file.

 * For defining the Datetime string representations use the following
 * <B>Time Format Syntax:</B>

 *  Symbol   Meaning                 Presentation        Example
 *  ------   -------                 ------------        -------
 *  G        era designator          (Text)              AD
 *  y        year                    (Number)            1996
 *  M        month in year           (Text & Number)     July & 07
 *  d        day in month            (Number)            10
 *  h        hour in am/pm (1~12)    (Number)            12
 *  H        hour in day (0~23)      (Number)            0
 *  m        minute in hour          (Number)            30
 *  s        second in minute        (Number)            55
 *  S        millisecond             (Number)            978
 *  E        day in week             (Text)              Tuesday
 *  D        day in year             (Number)            189
 *  F        day of week in month    (Number)            2 (2nd Wed in July)
 *  w        week in year            (Number)            27
 *  W        week in month           (Number)            2
 *  a        am/pm marker            (Text)              PM
 *  k        hour in day (1~24)      (Number)            24
 *  K        hour in am/pm (0~11)    (Number)            0
 *  z        time zone               (Text)              Pacific Standard Time
 *  '        escape for text         (Delimiter)
 *  ''       single quote            (Literal)           '
 </PRE>
 */

public class CohesiveLogger
{

    //===================== CONSTANTS ========================================

    // Constant for Newline.
    private static String NEWLINE = "\n";
    //"\n";

    // The mode of Logger to log the messages in file.
    public static String WRITE_TO_FILE ;

    // The mode of Logger not to log the messages in file but print on console.
   // public static String DONT_WRITE_TO_FILE = "Y";

    // The mode of Logger neither to log the messages in file nor to print on
    // console.
   // public static String IGNORE = "I";

	// The mode of Logger to log only incoming and outgoing messages.
  //  private static final String ONLY_IN_OUT_MSG = "M";//BUG1205


    //=======================  Logger Default Values  ========================

    // The default log file path [User's home directory].
    
    public static String LOG_FOLDER_SEPERATOR;
    public static String LOG_FILE_PATH;
    //public static String FOLDER_DELIMITER;   
    // The default log file name.
    private String LOGGER_DEFAULT_LOGFILE_NAME;

    // The default date format which is appended to the log messages.
    public static String LOG_CONETENT_DATE_DISPLAY_FORMAT;
       

    // The default date format which is appended to the file name.
    public static String LOG_FILE_DATE_DISPLAY_FORMAT;
        

    // The default size of the StringBuffer wherein the log messages are stored.
    //private static final long IF_DEFAULT_BUFFER_SIZE = 8192;
    // 8 * 1024 = 8192 ie. 8KB
    public static  int LOG_BUFFER_SIZE;
    // The default size of the Log File.
    public static int LOG_FILE_SIZE;
    // 1024 * 1024 = 1048576 ie. 1MB

    //===================== CLASS VARIABLES ==================================

    // The identifier used to have a unique file name.
    private String g_identifier = "SYS";

    // The identifier used to check the Logging Paramater in Parent Parameter file
    private String g_log_required="";
    // A String variable used to store the dynamically generated file name.
    private String fileName = null;

    // The StringBuffer variable used to store and queue the log messages.
    private StringBuffer loggedMessage = null;

    // The String variable used to store the log messages.
    private String g_outputMsg = null;

    // The String variable representing the Class calling the utility to log.
    private String g_className = "";

    // The String variable representing the method name.
    private String g_methodName = "";

    // A String variable used to store the file name as indicated in the
    // Property file
    private String FILE_NAME = "";

    
    // A String variable representing the size of the StringBuffer wherein the
    // log messages are stored as indicated in the Properties file
    private int BUFFER_SIZE = 0;

    // A String variable representing the size of the log file as indicated in
    // the Properties file
    private int FILE_SIZE = 0;

    // A RandomAccessFile instance used to find the size of the file.
    private RandomAccessFile ifLogFile = null;

    // A PrintWriter instance used to write to the file.
    private PrintWriter ifLogger = null;

    // A IFProperties instance used to load the Properties file.
//    private IFProperties g_IFProp = null;

    // A String variable representing the mode of the Logger.
    private String IS_LOGGER_ENABLED = null;

    // A String variable representing the delimiter for the Logger.
    private String LOGGER_DELIMITER = " --> ";

    // A String variable used to indicate the Datetime format to be appended to
    // the log messages.
    private String DATE_DISPLAY_FORMAT;

    // A SimpleDateFormat instance.
    private java.text.SimpleDateFormat g_sdf;

    // A String variable used to indicate the Datetime format to be appended to
    // the log file name.
    private String FILE_DATE_DISPLAY_FORMAT;

    // A SimpleDateFormat instance.
    private java.text.SimpleDateFormat g_fsdf;

    // A Throwable instance.
    private Throwable wrappedThrowable = null;
    private String FILE_EXTENSION;

    //============================  CONSTRUCTOR  =============================

    /**
     <PRE>
     * Receives two arguments one for IFProperties representing the properties
     * file and another a String variable representing an indicator to be
     * appended to the file name to make it unique.

     * It stores these two arguments in the memory.

     * It calls initializePropValues() method to retrieve and store the Logger
     * specific property values.

     * It calls initializeIFLogger() method to create the log file.

     * It calls initializeLogMessage() method to initialize the StringBuffer
     * for storing the log messages.
     </PRE>

     * @param p_IFProp        the IFProperties instance.<BR>
     * @param p_identifier    a String variable representing the <BR>

     * @exception Exception   thrown when some exception is thrown by
     *                        the called methods <BR>
     */
    public CohesiveLogger(String p_identifier, String p_log_required,String module,String sessionId,boolean iserror)
        throws Exception
    {
        //Store IFProperties instance in the memory.
        //g_IFProp = p_IFProp;

        //If p_identifier is not null, then store it in g_identifier.
        if (p_identifier != null)
        {
            g_identifier = p_identifier;
        }

        if (p_log_required != null)
        {
            g_log_required = p_log_required;
        }
        
        if(g_log_required.equals("Y"))
         {
        //Retrieve and store the Logger specific property values.
        initializePropValues(module,sessionId,iserror);

        //Create the log file.
        initializeIFLogger();

        //Initialize the StringBuffer variable for storing the log messages.
        initializeLogMessage();

        FILE_SIZE -= BUFFER_SIZE;
    }
   }
    //------------------------------------------------------------------------

    /**
     <PRE>
     * Receives two arguments one for IFProperties representing the properties
     * file and another a String variable representing an indicator to be
     * appended to the file name to make it unique.

     * It stores these two arguments in the memory.

     * It calls initializePropValues() method to retrieve and store the Logger
     * specific property values.

     * It calls initializeIFLogger() method to create the log file.

     * It calls initializeLogMessage() method to initialize the StringBuffer
     * for storing the log messages.

     * It initializes the BUFFER_SIZE to the buffer size as received as argument.
     </PRE>

     * @param p_IFProp        the IFProperties instance.<BR>
     * @param p_identifier    a String variable representing the <BR>

     * @exception Exception   thrown when some exception is thrown by
     *                        the called methods <BR>
     */

    //============================  METHODS  =================================

    //===================== START INITIALIZE METHODS  ========================

    /**
     <PRE>
     * Retrieves and stores the Logger specific property values in the memory
     * as in the Properties file.
     </PRE>

     * @return  void
     */
    private void initializePropValues(String module,String sessionId,boolean isError)
    {
        
        IS_LOGGER_ENABLED =g_log_required;
           
       if(isError)
      {   
        FILE_NAME =LOG_FILE_PATH+"err"+LOG_FOLDER_SEPERATOR;
        FILE_NAME =
                FILE_NAME
                + g_identifier
                +"_"
                + module
                + "_"
                +sessionId
                +"_";        
        FILE_EXTENSION=".err";              
                }
       else
       {   
          FILE_NAME =LOG_FILE_PATH+"dbg"+LOG_FOLDER_SEPERATOR;
        FILE_NAME =
                FILE_NAME
                + g_identifier
                +"_"
                + module
                +"_"
                +sessionId
                +"_";
                        
                //+ "_";
        FILE_EXTENSION= ".dbg";            
                } 
       
        BUFFER_SIZE =LOG_BUFFER_SIZE;
        FILE_SIZE =LOG_FILE_SIZE;
            
        DATE_DISPLAY_FORMAT =
                LOG_CONETENT_DATE_DISPLAY_FORMAT;

        g_sdf = new java.text.SimpleDateFormat(DATE_DISPLAY_FORMAT);

        FILE_DATE_DISPLAY_FORMAT =
                LOG_FILE_DATE_DISPLAY_FORMAT;

        g_fsdf = new java.text.SimpleDateFormat(FILE_DATE_DISPLAY_FORMAT);

    }

    //------------------------------------------------------------------------

    /**
     <PRE>
     * Creates a log file.
     </PRE>

     * @exception Exception   thrown when there is some exception.

     * @return  void
     */
    private void initializeIFLogger() throws Exception
    {
        try
        {
            if (WRITE_TO_FILE.equalsIgnoreCase(IS_LOGGER_ENABLED))
            {
                if (ifLogger == null)
                {
                    if (fileName == null)
                    {
                        StringBuffer l_sb = new StringBuffer(FILE_NAME);
                        
                        l_sb.append(
                            g_fsdf
                                .format(Calendar.getInstance().getTime())
                                .toUpperCase());
                        l_sb.append(FILE_EXTENSION);
                        fileName = l_sb.toString();
                        fileName = fileName.replace(' ','_');
                    }

                    ifLogger = new PrintWriter(new FileWriter(fileName, true));

                    //The following instance of RandomAccessFile is created to check
                    //the size of the file
                    ifLogFile = new RandomAccessFile(fileName, "r");
                }
            }



        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        }
    }

    //------------------------------------------------------------------------

    /**
     <PRE>
     * Initializes StringBuffer variable for storing the log messages.
     </PRE>
     *
     * @return  void
     */
    private void initializeLogMessage()
    {
        loggedMessage = new StringBuffer();
    }

    //===================== END INITIALIZE METHODS  ==========================

    //===================== START FLUSHING METHODS  ==========================

    /**
     <PRE>
     * Logs to a specified file.
     * Should always be called from synchMethod() method for synchronization
     * of file writing.
     </PRE>

     * @exception Exception   thrown when there is some exception.

     * @return  void
     */
    private void flushLogMessage() throws Exception
    {
        try
        {
            //If log file size exceeds the maximum size specified, then close
            //it.
            if (ifLogFile != null && ifLogFile.length() >= FILE_SIZE)
            {
                ifLogger.close();
                ifLogger = null;

                ifLogFile.close();
                ifLogFile = null;

                fileName = null;
            }

            //If log file object is null then create a new one.
            if (ifLogger == null || ifLogFile == null)
            {
                initializeIFLogger();
            }

            //If StringBuffer variable used to store the log messages is null
            //then initialize it.
            /*if(loggedMessage == null)
            {
                initializeLogMessage();
            }*/

            //Write the log messages to the log file.
            if (WRITE_TO_FILE.equalsIgnoreCase(IS_LOGGER_ENABLED))
            {
                ifLogger.write(loggedMessage.toString());
                ifLogger.println();

                //Flush the PrintWriter instance used to write to the log file. //VIDU: luk afterwards
                ifLogger.flush();
            }



            //Empty the StringBuffer variable used to store the log messages.
            loggedMessage = null;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        }
    }

    //------------------------------------------------------------------------

    /**
     * Called when the calling class exits.
     * All the log messages are logged.
     *
     * @return  void
     */
    public void flushLogger()
    {
        synchMethod(1, null);
    }

    //===================== END FLUSHING METHODS  ============================

    //===================== START println METHODS  ===========================

    /**
     * Overloaded println method logs the string representing the object p_obj.
     *
     * @param   p_className      a String variable representing the
     *                           Class Name which calls this method
     * @param   p_methodName     a String variable representing the method name
     * @param   p_obj            an Object variable
     * @return  void
     */
    public void println(String p_className, String p_methodName, Object p_obj)
    {
        //If Logging mode is I i.e. IGNORE; then return.
        if ("N".equalsIgnoreCase(IS_LOGGER_ENABLED))
        {
            return; // return void
        }

        if (p_obj != null)
        {
            println(p_className, p_methodName, p_obj.toString());
        }
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded println method logs the string representing the byte p_value.
     *
     * @param   p_className      a String variable representing the
     *                           Class Name which calls this method
     * @param   p_methodName     a String variable representing the method name
     * @param   p_value          a byte variable
     * @return  void
     */
    public void println(String p_className, String p_methodName, byte p_value)
    {
        //If Logging mode is I i.e. IGNORE; then return.
        if ("N".equalsIgnoreCase(IS_LOGGER_ENABLED))
        {
            return; // return void
        }

        println(p_className, p_methodName, p_value + "");
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded println method logs the string representing the byte array
     * p_value.
     *
     * @param   p_className      a String variable representing the
     *                           Class Name which calls this method
     * @param   p_methodName     a String variable representing the method name
     * @param   p_value          a byte[] variable
     * @return  void
     */
    public void println(
        String p_className,
        String p_methodName,
        byte[] p_value)
    {
        //If Logging mode is I i.e. IGNORE; then return.
        if ("N".equalsIgnoreCase(IS_LOGGER_ENABLED))
        {
            return; // return void
        }

        println(p_className, p_methodName, new String(p_value));
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded println method logs the string representing the int p_value.
     *
     * @param   p_className      a String variable representing the
     *                           Class Name which calls this method
     * @param   p_methodName     a String variable representing the method name
     * @param   p_value          an int variable
     * @return  void
     */
    public void println(String p_className, String p_methodName, int p_value)
    {
        //If Logging mode is I i.e. IGNORE; then return.
        if ("N".equalsIgnoreCase(IS_LOGGER_ENABLED))
        {
            return; // return void
        }

        println(p_className, p_methodName, p_value + "");
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded println method logs the string representing the char p_char.
     *
     * @param   p_className      a String variable representing the
     *                           Class Name which calls this method
     * @param   p_methodName     a String variable representing the method name
     * @param   p_c              a character variable
     * @return  void
     */
    public void println(String p_className, String p_methodName, char p_char)
    {
        //If Logging mode is I i.e. IGNORE; then return.
        if ("N".equalsIgnoreCase(IS_LOGGER_ENABLED))
        {
            return; // return void
        }

        println(p_className, p_methodName, p_char + "");
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded println method logs the string representing the short p_value.
     *
     * @param   p_className      a String variable representing the
     *                           Class Name which calls this method
     * @param   p_methodName     a String variable representing the method name
     * @param   p_value          a short variable
     * @return  void
     */
    public void println(String p_className, String p_methodName, short p_value)
    {
        //If Logging mode is I i.e. IGNORE; then return.
        if ("N".equalsIgnoreCase(IS_LOGGER_ENABLED))
        {
            return; // return void
        }

        println(p_className, p_methodName, p_value + "");
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded println method logs the string representing the long p_value.
     *
     * @param   p_className      a String variable representing the
     *                           Class Name which calls this method
     * @param   p_methodName     a String variable representing the method name
     * @param   p_value          a long variable
     * @return  void
     */
    public void println(String p_className, String p_methodName, long p_value)
    {
        //If Logging mode is I i.e. IGNORE; then return.
        if ("N".equalsIgnoreCase(IS_LOGGER_ENABLED))
        {
            return; // return void
        }

        println(p_className, p_methodName, p_value + "");
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded println method logs the string representing the float p_value.
     *
     * @param   p_className      a String variable representing the
     *                           Class Name which calls this method
     * @param   p_methodName     a String variable representing the method name
     * @param   p_value          a float variable
     * @return  void
     */
    public void println(String p_className, String p_methodName, float p_value)
    {
        //If Logging mode is I i.e. IGNORE; then return.
        if ("N".equalsIgnoreCase(IS_LOGGER_ENABLED))
        {
            return; // return void
        }

        println(p_className, p_methodName, p_value + "");
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded println method logs the string representing the double p_value.
     *
     * @param   p_className      a String variable representing the
     *                           Class Name which calls this method
     * @param   p_methodName     a String variable representing the method name
     * @param   p_value          a double variable
     * @return  void
     */
    public void println(
        String p_className,
        String p_methodName,
        double p_value)
    {
        //If Logging mode is I i.e. IGNORE; then return.
        if ("N".equalsIgnoreCase(IS_LOGGER_ENABLED))
        {
            return; // return void
        }

        println(p_className, p_methodName, p_value + "");
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded println method logs the string representing the boolean p_value.
     *
     * @param   p_className      a String variable representing the
     *                           Class Name which calls this method
     * @param   p_methodName     a String variable representing the method name
     * @param   p_value          a boolean variable
     * @return  void
     */
    public void println(
        String p_className,
        String p_methodName,
        boolean p_value)
    {
        //If Logging mode is I i.e. IGNORE; then return.
        if ("N".equalsIgnoreCase(IS_LOGGER_ENABLED))
        {
            return; // return void
        }

        println(p_className, p_methodName, p_value + "");
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded println method logs the string p_output.
     *
     * @param   p_className      a String variable representing the
     *                           Class Name which calls this method
     * @param   p_methodName     a String variable representing the method name
     * @param   p_output         a String variable
     * @return  void
     */
    public void println(
        String p_className,
        String p_methodName,
        String p_output)
    {
        //If Logging mode is I i.e. IGNORE; then return.
        if ("N".equalsIgnoreCase(IS_LOGGER_ENABLED))
        {
            return; // return void
        }

        g_className = p_className;
        g_methodName = p_methodName;
        g_outputMsg = p_output;
       // System.out.println(g_outputMsg);
        synchMethod(0, null);
    }

public void println(
        String p_className,
        String p_methodName,
        String p_output,
		String p_inout)
    {
        //If Logging mode is I i.e. IGNORE; then return.
        if ("N".equalsIgnoreCase(IS_LOGGER_ENABLED))
        {
            return; // return void
        }

        g_className = p_className;
        g_methodName = p_methodName;
        g_outputMsg = p_output;
        synchMethod(0, null);
    }


    //------------------------------------------------------------------------

    /**
     * Overloaded println method to enter NEWLINE.
     *
     * @return  void
     */
    public void println()
    {
        println("", "", "");
    }

    //===================== END println METHODS  ============================

    //===================== START printStackTrace METHODS  ==================

    /**
     * Overloaded printStackTrace method
     *
     * @param   p_className      a String variable representing the
     *                           Class Name which calls this method
     * @param   p_methodName     a String variable representing the method name
     * @param   p_throwable      a variable of Throwable class type
     * @return  void
     */
    public void printStackTrace(
        String p_className,
        String p_methodName,
        Throwable p_throwable)
    {
        String l_isLogEnabled = IS_LOGGER_ENABLED;

        if (!(WRITE_TO_FILE.equalsIgnoreCase(l_isLogEnabled)))
        {
            IS_LOGGER_ENABLED = WRITE_TO_FILE;
        }

        g_className = p_className;
        g_methodName = p_methodName;
        //System.out.println("raj i am inside printStackTrace1");
        synchMethod(2, p_throwable);

        IS_LOGGER_ENABLED = l_isLogEnabled;
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded printStackTrace method
     *
     * @return  void
     */
    private void printStackTrace()
    {
        printStackTrace("");
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded printStackTrace method
     *
     * @param   p_str    a String variable
     * @return  void
     */
    private void printStackTrace(String p_str)
    {
    	//System.out.println("raj i am inside printStackTrace2");
    	if (wrappedThrowable != null)
        {
            System.out.println();
            if (!("".equals(p_str)) && p_str != null)
            {
                System.out.println(p_str);
            }
            wrappedThrowable.printStackTrace();
            System.out.println();
        }
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded printStackTrace method
     *
     * @param   p_pw        a variable of PrintWriter class type
     * @return  void
     */
    private void printStackTrace(PrintWriter p_pw)
    {
        printStackTrace(p_pw, "");
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded printStackTrace method
     *
     * @param   p_pw        a variable of PrintWriter class type
     * @param   p_str       a String variable
     * @return  void
     */
    private void printStackTrace(PrintWriter p_pw, String p_str)
    {
    	//System.out.println("raj i am inside printStackTrace3");
    	if (wrappedThrowable != null)
        {
            if (!("".equals(p_str)) && p_str != null)
            {
                p_pw.write(p_str + NEWLINE);
                p_pw.println();
            }
            wrappedThrowable.printStackTrace(p_pw);
            p_pw.println();
            p_pw.flush();
        }
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded printStackTrace method
     *
     * @param   p_ps        a variable of PrintStream class type
     * @return  void
     */
    private void printStackTrace(PrintStream p_ps)
    {
        printStackTrace(p_ps, "");
    }

    //------------------------------------------------------------------------

    /**
     * Overloaded printStackTrace method
     *
     * @param   p_ps        a variable of PrintStream class type
     * @param   p_str       a String variable
     * @return  void
     */
    private void printStackTrace(PrintStream p_ps, String p_str)
    {
    	//System.out.println("raj i am inside printStackTrace4");
        if (wrappedThrowable != null)
        {
            if (!("".equals(p_str)) && p_str != null)
            {
                p_ps.println(p_str + NEWLINE);
                p_ps.println();
            }
            wrappedThrowable.printStackTrace(p_ps);
            p_ps.println();
            p_ps.flush();
        }
    }

    //------------------------------------------------------------------------

    /**
     * Access the original exception.
     *
     * @return Throwable that was initially thrown
     */
    private Throwable getWrappedThrowable()
    {
        return wrappedThrowable;
    }

    //------------------------------------------------------------------------

    /**
     * Reset the original exception.
     *
     * @param p_t Throwable to be wrapped
     */
    private void setWrappedThrowable(Throwable p_t)
    {
        wrappedThrowable = p_t;
    }

    //===================== END printStackTrace METHODS  ====================

    //===================== START synchMethod  ==============================

    /**
     * It ensures that at one specific time only one method will access the file.
     *
     * @param   p_priority     0 - println()
     *                         1 - flushLogger()
     *                         2 - printStackTrace()
     * @param   p_throwable    A variable of Throwable class type
     *
     * @return  void
     */
    private synchronized void synchMethod(
        int p_priority,
        Throwable p_throwable)
    {
        StringBuffer l_printStatement = new StringBuffer("");
        if (!("".equals(g_outputMsg)
            && "".equals(g_className)
            && "".equals(g_methodName)))
        {
            l_printStatement
                .append(g_className)
                .append(" [")
                .append(g_methodName)
                .append("] ")
                .append(
                    g_sdf
                        .format(Calendar.getInstance().getTime())
                        .toUpperCase())
                .append(LOGGER_DELIMITER);
        }

        try
        {
            if (p_priority == 0)
            {
                l_printStatement.append(g_outputMsg);

				
if (WRITE_TO_FILE.equalsIgnoreCase(IS_LOGGER_ENABLED))
				//BUG1205 end
                {
                    if (loggedMessage == null)
                    {
                    	//System.out.println("Raj i am here : 2");
                        initializeLogMessage();
                    }
                    //System.out.println("Raj i am here : 3");
                    loggedMessage.append(l_printStatement.toString());

                    //Append new line to the log message only if the BUFFER_SIZE
                    //is more than 0.
                    if (BUFFER_SIZE != 0)
                    {
                    	//System.out.println("Raj i am here : 4");
                    	loggedMessage.append(NEWLINE);
                    }

                    //Flush the StringBuffer once its size equals or exceeds
                    //the specified BUFFER_SIZE.
                    if (loggedMessage != null
                        && loggedMessage.length() >= BUFFER_SIZE)
                    {
                        flushLogMessage();
                    }

                    return; // return void
                }

                //If Logging mode is other than Y or I then print the log
                //messages on the console.
               // System.out.println(l_printStatement.toString());
            }
            else if (p_priority == 1)
            {
                //l_printStatement.append(g_outputMsg);
                //loggedMessage.append(l_printStatement.toString());

                if (loggedMessage != null)
                {
                    flushLogMessage();
                }

                if (ifLogger != null)
                {
                    //Flush the PrintWriter instance used to write to the log file.
                    ifLogger.flush();

                    //Close the PrintWriter instance used to write to the log file.
                    ifLogger.close();

                    ifLogger = null;
                }

                if (ifLogFile != null)
                {
                    ifLogFile.close();
                    ifLogFile = null;
                }
            }
            else if (p_priority == 2)
            {
                //l_printStatement.append(EXCEPTION_STRING);

                if (loggedMessage != null && loggedMessage.length() > 0)
                {
                    flushLogMessage();
                }

                if (ifLogger == null)
                {
                    initializeIFLogger();
                }

                setWrappedThrowable(p_throwable);
                printStackTrace(ifLogger, l_printStatement.toString());

                //If Logging mode is I i.e. IGNORE; then return.
                /*if(IGNORE.equalsIgnoreCase(IS_LOGGER_ENABLED))
                {
                    // return void
                    return;
                }*/

                //If Logging mode is other than I then print the log messages
                //on the console.
//                printStackTrace(l_printStatement.toString());
            }
        }
        catch (Exception ie)
        {
            ie.printStackTrace();
            //throw ie;  //VIDU : Check it latter [V.IMP]
        }
    }

    //===================== END synchMethod =================================

    //===================== START getDefLogPath =============================

    public static String getDefLogPath()
    {
        return LOG_FILE_PATH;
    }
    
public void closeLogger()
    {
       try
        {
            if (ifLogger != null)
            {   
                ifLogger.close();
                ifLogger = null;
            }  
            
            if(ifLogFile !=null)
            {   
                ifLogFile.close();
                ifLogFile = null;
            }
                fileName = null;
                this.loggedMessage =null;
        }      
     catch (Exception ex)
        {
            ex.printStackTrace();
            //throw ex;
        }           
    }

    
    
    
    //===================== END getDefLogPath ===============================
}
