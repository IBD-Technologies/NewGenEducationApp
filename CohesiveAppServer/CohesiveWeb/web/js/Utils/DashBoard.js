/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function fndashBoardmasterCalls(childScope)
{
	var msgheader={msgID:"",
            service:"DashBoardService",
            operation:"View",
			instituteID:window.Institute.ID,
			userID:window.User.Id,
                        key:"",
                        token:$('#nokotser').val(),
                        source:"CohesiveFrontEnd",
            businessEntity:[],
            status:""};
        if(TEST=="YES"){
        
           var URL="https://cohesivetest.ibdtechnologies.com/CohesiveGateway/Institute/DashBoardService";
        
       }else{
           
           var URL="https://cohesive.ibdtechnologies.com/CohesiveGateway/Institute/DashBoardService";
       }
        
        
         var request={header:msgheader,
                     body:{
                            teacherAttendance:"",
                            studentAttendance:"",
                            teacherWorkingDays:"",
                            teacherLeaveDays:"",
                            classOfTheTeacher:"",
                            totalFee:"",
                            collectedAmount:"",
                            pendingAmount:"",
                            overDueAmount:"",
                            classOverDueAmount:"",
                            totalTeachers:"",
                            totalStudents:""
                         
                         },
                     audit:{},
                     error:{}
                        };
                       
       $.ajax({
      //url: $('#imgUpld').attr('action'),
      
//      if()
      
//      url:"https://cohesive.ibdtechnologies.com/CohesiveGateway/Institute/DashBoardService",
       url:URL,
        
        
        type: 'PUT',
      //enctype:'application/json',
      cache:true,
      data : JSON.stringify(request),
      processData: false,
      contentType: "application/json;charset=utf-8",
      success: function(data){
        //console.log('form submitted.');
        //if(xhr.status == '200')
        //{  //$("#logo").attr("src","/img/uploads/"+xhr.responseText);
             //window.parent.fn_hide_parentspinner();
             if(data.header.status=="success")
             {
               // var res=xhr.split("~"); 
              
                  // src.fileName= res[2];  
                   //src.uploadID= res[1]; 
                   fnPostDashBoardResponse(data,childScope);
                    /*var fn = window["fn"+MenuName+"postSelectBoxMaster"];
                     if (typeof fn === "function") 
                     {  
                         fn();
                     }*/ 
                   //fnPostImageUpload(id,res[2],res[1]);
              } 
              else 
              {
                  $("#dashBoardAlert").text("There is Error in DashBoard Processing !..");
//                  //var res=xhr.split("~");
//                  
//                  for(i=0;i<data.error.length;i++)
//	             {
// 	               if(data.error[i].errorCode=='BS_VAL_101')
//                       {
//                         var error= [{
//			errorCode: "",
//			errorMessage: ""
//		           }];
//	                    error[0].errorCode=data.error[i].errorCode;
//                            error[0].errorMessage=data.error[i].errorMessage;
// 
//                        window.parent.fnMainPagewithError(error); 
//                        window.parent.$("#frame").attr('src', '');
//                      return;
//                    } 
//                      
//                     }
//                   window.parent.$("#frame").attr('src', '');
//                   window.parent.fnMainPagewithError(data.error);
                   //fn_show_backend_exception(data.error);

                }
                  
                             
              //}    
                  
          return true; 
          },
      error:function(xhr)
      {
          $("#dashBoardAlert").text("There is Error in DashBoard Processing !..")
//          window.parent.fn_hide_parentspinner();
//          var error= [{
//			errorCode: "",
//			errorMessage: ""
//		           }];
//	                    error[0].errorCode=xhr.status;
//                            error[0].errorMessage=xhr.responseText;
//                     
//          //fn_show_backend_exception(error);
//         window.parent.$("#frame").attr('src', '');
//         window.parent.fnMainPagewithError(error);
//                   
//          return false;
      }
    });
    
	return 'success'; 
    	
}


function fnPostDashBoardResponse(data,childScope){
    
    
    if(typeof data.body.teacherAttendance!='undefined'){
    
        childScope.teacherAttendance=data.body.teacherAttendance;
    }
     
    if(typeof data.body.studentAttendance!='undefined'){
        childScope.studentAttendance=data.body.studentAttendance;
    }   
        
    if(typeof data.body.teacherWorkingDays!='undefined'){    
        childScope.teacherWorkingDays=data.body.teacherWorkingDays;
        
    }
    
    if(typeof data.body.teacherLeaveDays!='undefined'){   
        childScope.teacherLeaveDays=data.body.teacherLeaveDays;
        
    }
    if(typeof data.body.classOfTheTeacher!='undefined'){   
        childScope.classOfTheTeacher=data.body.classOfTheTeacher;
        
    }
//        childScope.totalFee=data.body.totalFee;
//        childScope.collectedAmount=data.body.collectedAmount;
//        childScope.pendingAmount=data.body.pendingAmount;
//        childScope.overDueAmount=data.body.overDueAmount;
//        childScope.classOverDueAmount=data.body.classOverDueAmount;
        
//        $('#totalFee').val(data.body.totalFee);
//        $('#totalFee').formatCurrency({ colorize:true,region: 'en-IN' })
//	childScope.totalFee=$('#totalFee').val();
//        
//        $('#collectedAmount').val(data.body.collectedAmount);
//        $('#collectedAmount').formatCurrency({ colorize:true,region: 'en-IN' })
//	childScope.collectedAmount=$('#collectedAmount').val();
//        
//        $('#pendingAmount').val(data.body.pendingAmount);
//        $('#pendingAmount').formatCurrency({ colorize:true,region: 'en-IN' })
//	childScope.pendingAmount=$('#pendingAmount').val();
//        
//        $('#overDueAmount').val(data.body.overDueAmount);
//        $('#overDueAmount').formatCurrency({ colorize:true,region: 'en-IN' })
//	childScope.overDueAmount=$('#overDueAmount').val();
//        
//        $('#classOverDueAmount').val(data.body.classOverDueAmount);
//        $('#classOverDueAmount').formatCurrency({ colorize:true,region: 'en-IN' })
//	childScope.classOverDueAmount=$('#classOverDueAmount').val();
        
        if(typeof data.body.pendingQueueMaster!='undefined'){   
        
            childScope.pendingQueueMaster=data.body.pendingQueueMaster;
        }
        
        
        childScope.pendingQueueMasterTable = fnConvertmvw('pendingQueueMasterTable',data.body.pendingQueueMaster);
		childScope.pendingQueueMasterCurPage = 1;
		childScope.pendingQueueMasterShowObject = childScope.fnMvwGetCurPageTable('pendingQueueMaster');
        
        
        
        if(typeof data.body.smsLimit!='undefined'){
            
            childScope.smsLimit=data.body.smsLimit;
        
        }
        
        if(typeof data.body.currentSMSBalance!='undefined'){
        
           childScope.currentSMSBalance=data.body.currentSMSBalance;
        
        }
        
        if(typeof data.body.totalTeachers!='undefined'){
            
            childScope.totalTeachers=data.body.totalTeachers;
            
        }
        
        if(typeof data.body.totalStudents!='undefined'){
        
           childScope.totalStudents=data.body.totalStudents;
        
        }
        
        if(typeof data.body.institutFeeDetails!='undefined'){
        
           childScope.institutFeeDetails=data.body.institutFeeDetails;
        
        }
        
        if(typeof data.body.institutFeeDetails!='undefined'){
        
         if(data.body.institutFeeDetails.length!=0){
                
		  childScope.instituteFeeDetailsTable = fnConvertmvw('institutFeeDetails',data.body.institutFeeDetails);
                }else{
                    
                  childScope.instituteFeeDetailsTable=null;  
                }
             
		childScope.instituteFeeDetailscurIndex = 0;
		if (childScope.instituteFeeDetailsTable != null) {
			childScope.instituteFeeDetailsRecord = childScope.instituteFeeDetailsTable[childScope.instituteFeeDetailscurIndex];
			childScope.instituteFeeDetailsShow = true;
		}
        
    }
        
        if(typeof data.body.classFeeDetails!=='undefined'){
        
            childScope.classFeeDetails=data.body.classFeeDetails;
        }
        
        
        childScope.dashBoardProcessDone=true;
        
        childScope.$apply();
        $('.currency').formatCurrency({ colorize:true,region: 'en-IN' });
        //$('.currency').trigger('input');
        //$('.currency').trigger('change');
       // var $scope = angular.element(document.getElementById('MainCtrl')).scope();
        //$scope.$apply();
           
        
}

function fnConvertmvw(tableName,responseObject)
{
	switch(tableName)
	{
		case 'pendingQueueMasterTable':
		   
			var pendingQueueMasterTable = new Array();
			 responseObject.forEach(fnConvert1);
			 
			 
			 function fnConvert1(value,index,array){
				     pendingQueueMasterTable[index] = new Object();
					 pendingQueueMasterTable[index].idx=index;
					 pendingQueueMasterTable[index].service=value.service;
					 pendingQueueMasterTable[index].operation=value.operation;
					 pendingQueueMasterTable[index].count=value.count;
					}
			return pendingQueueMasterTable;
			break ;
                        
                        case 'institutFeeDetails':
		   
			var instituteFeeDetailsTable = new Array();
			 responseObject.forEach(fnConvert2);
			 
			 
			 function fnConvert2(value,index,array){
                             
				     instituteFeeDetailsTable[index] = new Object();
					 instituteFeeDetailsTable[index].idx=index;
					 instituteFeeDetailsTable[index].feeType=value.feeType;
                    
                             $('#dummyAmount').val(value.totalFee);
    $('#dummyAmount').formatCurrency({ colorize:true,region: 'en-IN' })
   instituteFeeDetailsTable[index].totalFee=$('#dummyAmount').val();
                             //instituteFeeDetailsTable[index].totalFee=value.totalFee;
   $('#dummyAmount').val(value.collectedAmount);
    $('#dummyAmount').formatCurrency({ colorize:true,region: 'en-IN' })
   instituteFeeDetailsTable[index].collectedAmount=$('#dummyAmount').val();
					 //instituteFeeDetailsTable[index].collectedAmount=value.collectedAmount;
                       $('#dummyAmount').val(value.pendingAmount);
    $('#dummyAmount').formatCurrency({ colorize:true,region: 'en-IN' })
   instituteFeeDetailsTable[index].pendingAmount=$('#dummyAmount').val();                   
					// instituteFeeDetailsTable[index].pendingAmount=value.pendingAmount;
                       $('#dummyAmount').val(value.overDueAmount);
    $('#dummyAmount').formatCurrency({ colorize:true,region: 'en-IN' })
   instituteFeeDetailsTable[index].overDueAmount=$('#dummyAmount').val();                   
	                            
					 //instituteFeeDetailsTable[index].overDueAmount=value.overDueAmount;
					}
			return instituteFeeDetailsTable;
			break ;
                        
                        
			}
			}
                        
                        
             