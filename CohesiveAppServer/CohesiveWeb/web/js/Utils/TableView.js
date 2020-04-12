/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var app= angular.module('TableView',[]);

//app.controller('TableSingleViewControl',fnTableSingleView);


app.service('TableViewCallService', function($compile,$timeout) {
	
    this.addSvwRowClick=function(emptyTableRec,svwObject)
	{
       if(svwObject.tableObject==null || svwObject.tableObject.length==0){
				svwObject.tableShow =true;  
				emptyTableRec.idx =0;
				svwObject.curIndex =0 ;
			    svwObject.tableObject[0]=emptyTableRec;
			   } 
			   else{
				     emptyTableRec.idx =svwObject.tableObject.length;   
				     svwObject.curIndex = svwObject.tableObject.length;
				     	svwObject.tableObject.push(emptyTableRec);

						}
			  

	};

    this.deleteSvwRowClick=function(svwObject)
		{	
		  if (svwObject.tableObject != null && svwObject.tableObject.length!=0)
		  {svwObject.tableObject.splice(svwObject.curIndex,1);
	        
			if(svwObject.curIndex !=0)
			{	
			svwObject.curIndex = svwObject.curIndex -1;
			svwObject.tableObject[svwObject.curIndex].idx=svwObject.curIndex;
			}
			else
			{
			  if(svwObject.tableObject!= null && svwObject.tableObject.length !=0)	
			svwObject.tableObject[svwObject.curIndex].idx=svwObject.curIndex;	
			}				
			
		  }  
	if(svwObject.tableObject==null || svwObject.tableObject.length==0)	  
	    svwObject.tableShow =false;
     
	};

 	this.forwardSvwClick=function(svwObject)
		{	
         svwObject.curIndex = svwObject.curIndex+1;
		 if (svwObject.tableObject.length == svwObject.curIndex || svwObject.curIndex > svwObject.tableObject.length)
		 {
			 svwObject.curIndex=svwObject.tableObject.length-1;
		 }			 
	};

	
 	this.backwardSvwClick=function(svwObject)
		{	
 
         svwObject.curIndex = svwObject.curIndex-1;
		 if ( svwObject.curIndex < 0)
		 {
			 svwObject.curIndex=0;
		 }         
 
	};
	
	this.SvwGetCurrentPage=function(svwObject)
	{
			if (svwObject.tableObject==null ||svwObject.tableObject.length==0)
			 return svwObject.curIndex;
		    else
			return svwObject.curIndex+1;
		 
	};
	
	this.SvwGetTotalPage=function(svwObject)
	{
		if (svwObject.tableObject==null ||svwObject.tableObject.length==0)
			return 0;
		else return svwObject.tableObject.length;
	};
	
	
	
this.addMvwRowClick=function(emptyTableRec,mvwObject)
	{
       if(mvwObject.tableObject==null || mvwObject.tableObject.length==0){
				//svwObject.tableShow =true;  
				emptyTableRec.idx =0;
				mvwObject.curPage =1 ;
			    mvwObject.tableObject[0]=emptyTableRec;
			   } 
			   else{
				     emptyTableRec.idx =mvwObject.tableObject.length;   
				    // mvwObject.curPage = mvwObject.tableObject.length/MVWPAGESIZE;
				     	mvwObject.tableObject.push(emptyTableRec);

						}
						
		    if (mvwObject.tableObject.length<= MVWPAGESIZE)
			{	
              mvwObject.screenShowObject=	mvwObject.tableObject;
		      mvwObject.curPage=1;
			}
            else
			{
				if((mvwObject.tableObject.length % MVWPAGESIZE) !=0)
				{	
				  mvwObject.screenShowObject= mvwObject.tableObject.slice((mvwObject.tableObject.length-(mvwObject.tableObject.length % MVWPAGESIZE)));
				  mvwObject.curPage =parseInt((mvwObject.tableObject.length-(mvwObject.tableObject.length % MVWPAGESIZE))/MVWPAGESIZE)+1;
				  
				}
				else
				{					
				  mvwObject.screenShowObject = mvwObject.tableObject.slice(mvwObject.tableObject.length-MVWPAGESIZE);	
				  mvwObject.curPage=parseInt((mvwObject.tableObject.length)/MVWPAGESIZE);
				}
			     
			}
		  


		  
		   
	};

    this.deleteMvwRowClick=function(mvwObject)
		{	
		
		
		  if (mvwObject.tableObject != null && mvwObject.tableObject!=0)
		  { var temp= mvwObject.tableObject.slice();
			 mvwObject.tableObject.forEach(fnDelete);
			 mvwObject.tableObject =temp;
			 function fnDelete(value,index,array){
				 				 
				if(value.checkBox ==true){
					 try{
					temp.forEach(function(value,index,array){
						                      

						                     if(value.checkBox ==true) 
											 { 
						                       temp.splice(index,1); 
											   if (index <temp.length) 
											    temp[index].idx=index;
										   
											   throw "done";
											 }
											   }
										   
										   );
			
				 }	
				 catch(e){ {if(e!="done") throw e; }}
				 
				}
				 
			 }
			 
		  
			 var CurPageEndIndex =mvwObject.curPage*MVWPAGESIZE;
			 var CurPageStartIndex = CurPageEndIndex-MVWPAGESIZE;
			 
			 if(mvwObject.tableObject.length<=MVWPAGESIZE)
			 {	 
			  mvwObject.curPage=1;
			  mvwObject.screenShowObject=mvwObject.tableObject;
			 }
			 else if(mvwObject.tableObject.length>=CurPageEndIndex ||(mvwObject.tableObject.length<=CurPageEndIndex -1))
			 {
				 if(CurPageEndIndex>mvwObject.tableObject.length)
				  mvwObject.screenShowObject=mvwObject.tableObject.slice(CurPageStartIndex);
			     else  
				   mvwObject.screenShowObject=mvwObject.tableObject.slice(CurPageStartIndex,CurPageEndIndex);
			 }
			 else{
				 mvwObject.curPage=mvwObject.curPage-1;
				 var CurPageEndIndex =mvwObject.curPage*MVWPAGESIZE;
			     var CurPageStartIndex = CurPageEndIndex-MVWPAGESIZE;
			     if(CurPageEndIndex>mvwObject.tableObject.length)
				  mvwObject.screenShowObject=mvwObject.tableObject.slice(CurPageStartIndex);
			     else  
				   mvwObject.screenShowObject=mvwObject.tableObject.slice(CurPageStartIndex,CurPageEndIndex);
			 }
			 
		  }	 
			
	};

 	this.forwardMvwClick=function(mvwObject)
		{	
         mvwObject.curPage = mvwObject.curPage+1;
		 var totalPage;
		 if(mvwObject.tableObject.length<=MVWPAGESIZE)
			 totalPage=1;
		 else if((mvwObject.tableObject.length%MVWPAGESIZE)>0)
			  totalPage =parseInt((mvwObject.tableObject.length/MVWPAGESIZE))+1;
		 else
		      totalPage =parseInt(mvwObject.tableObject.length/MVWPAGESIZE); 
		 
		if(mvwObject.curPage>=totalPage)
			mvwObject.curPage= totalPage; 
		
		var CurPageEndIndex =mvwObject.curPage*MVWPAGESIZE;
	    var CurPageStartIndex = CurPageEndIndex-MVWPAGESIZE;
	     if(CurPageEndIndex>mvwObject.tableObject.length)
				  mvwObject.screenShowObject=mvwObject.tableObject.slice(CurPageStartIndex);
         else  
				  mvwObject.screenShowObject=mvwObject.tableObject.slice(CurPageStartIndex,CurPageEndIndex);
			 
 			 
	};

	
 	this.backwardMvwClick=function(mvwObject)
		{	
 
         mvwObject.curPage = mvwObject.curPage-1;
		 if (mvwObject.curPage ==0)
		 {
			 mvwObject.curPage=1;
		 }         
        var CurPageEndIndex =mvwObject.curPage*MVWPAGESIZE;
	    var CurPageStartIndex = CurPageEndIndex-MVWPAGESIZE;
	     if(CurPageEndIndex>mvwObject.tableObject.length)
				  mvwObject.screenShowObject=mvwObject.tableObject.slice(CurPageStartIndex);
         else  
				  mvwObject.screenShowObject=mvwObject.tableObject.slice(CurPageStartIndex,CurPageEndIndex);
		      
   
 
	};	
	this.MvwGetTotalPage=function(mvwObject)
	{
		var totalPage;
		 if (mvwObject.tableObject==null || mvwObject.tableObject.length==0)
			 totalPage=0;
		 else if(mvwObject.tableObject.length<=MVWPAGESIZE)
			 totalPage=1;
		 else if((mvwObject.tableObject.length%MVWPAGESIZE)>0)
			  totalPage =parseInt((mvwObject.tableObject.length/MVWPAGESIZE))+1;
		 else
		      totalPage =parseInt(mvwObject.tableObject.length/MVWPAGESIZE); 
		 return totalPage;
	};
	
	this.MvwGetCurPage=function(mvwObject)
	{
		 if(mvwObject.tableObject==null ||mvwObject.tableObject.length==0)
		  return 0;
	     else 
		 return mvwObject.curPage;
	};
	
	this.MvwGetCurPageTable=function(curPage,tableObject)
	{
		var CurPageEndIndex =curPage*MVWPAGESIZE;
	    var CurPageStartIndex = CurPageEndIndex-MVWPAGESIZE;
	     if(CurPageEndIndex>tableObject.length)
				 return tableObject.slice(CurPageStartIndex);
         else  
				  return tableObject.slice(CurPageStartIndex,CurPageEndIndex);
		
	};
	
	
	
   	});

