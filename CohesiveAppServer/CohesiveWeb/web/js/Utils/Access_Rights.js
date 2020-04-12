/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 function fn_check_access_rights(menuName,OperationName)
 {
      for(i=0;i<access.length;i++)
	  {
         if (access[i].Menu ==menuName)
           { 
	         for(operations in access[i].Operation) 
              {
                 if (operations==OperationName)
                   return access[i].Operation[operations];
                				 
                }      			  
            }
 
 }
 return false;
 }