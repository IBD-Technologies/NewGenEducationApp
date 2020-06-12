/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var resToken;
var callRequestTokenResponse;
var request;
var apiCallRequired = false;
var apiError = false;

var searchDataModel = {
    searchFilter: "",
    searchResults: []
};
         
        async function setRequestToken (response, serviceName) {
            if (JSON.parse(await sessionStorage.getItem('Rst')) != null) {
                var tempRst = JSON.parse(await sessionStorage.getItem('Rst'));
            } else {
                var tempRst = [];
            }
            var serviceExist = false;
            if (tempRst != null && tempRst.lenghth != 0) {
                for (let item of tempRst) {
                    if (item.service == serviceName) {
                        serviceExist = true;

                        if (response.status == 'success') {
                            item.value = response.resourceToken;
                        } else {
                            item.value = null;
                        }
                        break;
                    }

                }
            }
            if (!serviceExist) {
                if (response.status == 'success') {
                    var serviceObj = {service: serviceName, value: response.resourceToken};
                } else {
                    var serviceObj = {service: serviceName, value: null};

                }
                if (tempRst != null && tempRst.lenghth != 0)
                    tempRst.push(serviceObj);
                else {
                    tempRst = [];
                    tempRst.push(serviceObj);
                }
            }

            await sessionStorage.setItem('Rst', JSON.stringify(tempRst));
        }

async function getRequestToken (serviceName) {

    var tempRst = JSON.parse(await sessionStorage.getItem('Rst'));

    var serviceExist = false;
    if (tempRst != null) {
        for (let item of tempRst) {
            if (item.service == serviceName) {
                resToken = item.value;
                serviceExist = true;
                break;
            }

        }
    }
    if (!serviceExist)
        resToken = null;

};

async function callRequestToken(global, service) {
      var request = {
        source:'NewGenEducationWeb', 
        userID: global.userID,
        instituteID: global.instituteID,
        service: service,
        token: window.parent.uhtuliak
    };

    await sessionStorage.setItem(service, '');

    console.log(request, 'resource Token request');
    
       await $.ajax({
      url:getURL("App", 'ResourceToken'),
      type: 'PUT',
      cache:false,
      data : JSON.stringify(request),
      processData: false,
      contentType: 'application/json',
      dataType: 'json'}).done(function(response){
                callRequestTokenResponse = response;
               }).fail(function(jqXHR, textStatus, errorThrown)
                       { 
                      callRequestTokenResponse = {
                      status: 'error',
                      error: [{
                            errorCode: jqXHR.status,
                            errorMessage: jqXHR.responseText
                        }]
                        };
                    });
    
     sessionStorage.setItem(service, '');
    
     setRequestToken(callRequestTokenResponse, service);

}

async function framRequest(apiObject) {
    var globalData = JSON.parse(await sessionStorage.getItem('GLOBAL'));

    await getRequestToken(apiObject.serviceName);
    if (resToken == null || resToken == '') {
        await callRequestToken(globalData, apiObject.serviceName).catch(function(){});
        await getRequestToken(apiObject.serviceName);
    }

    if (resToken == null) {
        request = null;
    } else {
        var header = {
            msgID: "",
            source: 'NewGenEducationWeb',
            service: apiObject.serviceName,
            operation: apiObject.operation,
            businessEntity: apiObject.businessEntity,
            status: "",
            key: "",
            instituteID: globalData.instituteID,
            userID: globalData.userID,
            token: resToken

        };
        var request1 = {
            header: header,
            body: apiObject.datamodel,
            error: null,
            audit: apiObject.audit
        };
        request = request1;
    }
}


 async function callApi (apiObject, responseDispatch) {
    if (apiObject.serviceName.includes('SearchService')) {
        parentStateChange({
            searchIsLoading: true
        });
    } else {
       window.parent.fn_show_spinner();;
    }
    apiError = false;
    callRequestTokenResponse = null;
    request = null;
    var request1;
     await framRequest(apiObject).then(res => {
      request1 = request;
    });
    
    console.log(request, "api call request");
    if (request != null) {
       
      await $.ajax({
      url:getURL(apiObject.serviceType, apiObject.serviceName),
      type: 'PUT',
      cache:false,
      data : JSON.stringify(request),
      processData: false,
      contentType: 'application/json',
      dataType: 'json'
    }).done( function(response){
           console.log(response, "response of api");
                        if (response.header.status == 'success') {
                            if (apiObject.serviceName == "DashBoardService") {
                                 responseDispatch(response);
                            } else {
                                if (apiObject.serviceName.includes('SearchService')) {
                                    searchDataModel = response.body;
                                     parentStateChange({
                                        searchIsLoading: false
                                    });

                                } else if (apiObject.serviceName.includes('Summary')) {

                                    var responsefilter = request.body.filter;
                                    var responseResult = response.body.SummaryResult;
                                    var response =
                                            {
                                                filter: responsefilter,
                                                SummaryResult: responseResult
                                            };
                                    parentStateChange({
                                        summaryDataModel: response
                                    });
                                     window.parent.fn_hide_parentspinner();
                                    
                                } else {
                                     successHandler(response, parentStateChange);
                                  
                                }
                            }
                        } else {
                            apiError = true;
                             window.parent.fn_hide_parentspinner();
                             fn_show_backend_exception(response.error);
                          
                        } 
                 
           return true;      
                 
    })
   .fail(function(jqXHR, textStatus, errorThrown)
       {
         try{
          apiError=true;
          console.log(jqXHR, "error response of api");
                   var error1= [{
                            errorCode: jqXHR.status,
                            errorMessage: jqXHR.responseText
                        }];
               fn_show_backend_exception(error1);
               return false;
           }
         catch(e)
         {
             return;
           }
        
   }).complete(function(e){});
  }
  else
  {
      if (callRequestTokenResponse.status=='error')
         fn_show_backend_exception(callRequestTokenResponse.error);
      else 
         fn_show_backend_exception({errorCode:'REQUEST',errorMessage:'Request building is failed'}); 
     }   
  
  }      
    
async function getServiceToken (serviceName) {
    var tempRst = JSON.parse(await sessionStorage.getItem('Rst'));
    if (tempRst != null) {
        for (let item of tempRst) {
            if (item.service == serviceName) {
                return item.value;
                break;
            }

        }
    }
    return null;
}



function successHandler (response) {
    switch (bottomTabClick) {
        case 'Save':
            if (response.data.header.operation == 'Create') {
                parentStateChange({
                    currentStep: 0,
                    currentOperation: 'Default'
                });
                 window.parent.fn_hide_parentspinner();
                
                fn_Show_Exception_With_Param('FE-VAL-012','');

            } else if (response.data.header.operation == 'Modify') {
                parentStateChange({
                    currentStep: 0,
                    currentOperation: 'Default'
                });
                  window.parent.fn_hide_parentspinner();
                 fn_Show_Exception_With_Param('FE-VAL-013','');
               }

            break;
        case 'Delete':
            parentStateChange({
                currentStep: 0,
                currentOperation: 'Default'
            });
              window.parent.fn_hide_parentspinner();
            fn_Show_Exception_With_Param('FE-VAL-014','');
            break;
        case 'Reject':
            parentStateChange({
                currentStep: 0,
                currentOperation: 'Default'
            });
              window.parent.fn_hide_parentspinner();
            fn_Show_Exception_With_Param('FE-VAL-017','');
            
            break;
        case 'Auth':
            parentStateChange({
                currentStep: 0,
                currentOperation: 'Default'
            });
              window.parent.fn_hide_parentspinner();
              fn_Show_Exception_With_Param('FE-VAL-015','');
            
            break;
        default:
            
            parentStateChange({
                dataModel: response.data.body,
                auditDataModel: response.data.audit
            });
           window.parent.fn_hide_parentspinner();
            break;

    }



};



