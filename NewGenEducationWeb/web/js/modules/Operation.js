/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var bottomTabClick = 'Next';
var skipStep = false;
async function nextStepEventHandler($scope) {
    skipStep = false;
    var globalData = JSON.parse(await sessionStorage.getItem('GLOBAL'));
    apiCallRequired = false;
    var operation = selectedOperation;
    var apiObject;
    var numberOfSteps = getNumberOfSteps(operation);

    var emptyDataModel = Object.assign({}, $scope.emptyDataModel);
    var emptyAuditDataModel = Object.assign({}, $scope.emptyAuditDataModel);
    var emptySummaryDataModel = Object.assign({}, $scope.emptySummaryDataModel);


    if (operation !== null) {
        switch (operation) {
            case 'create':
                switch ($scope.currentStep) {
                    case 1:
                        await getServiceToken($scope.serviceName).then(
                                async (values) => {
                            if (values == null) {
                                await callRequestToken(globalData, $scope.serviceName).catch(function (e) {
                                    apiError = true;
                                });

                            }

                        });
                        if(apiError)
                         return false;   
                        parentStateChange({
                            dataModel: emptyDataModel,
                            auditDataModel: emptyAuditDataModel,
                            editable: true
                        });
                        break;
                    default:
                        if(!DefaultStep('create', $scope))
                          return false;
                        break;
                }

                break;
            case 'query':
                switch ($scope.currentStep) {
                    case 1:
                        await getServiceToken($scope.serviceName).then(
                                async (values) => {
                            if (values == null) {
                                await callRequestToken(globalData, $scope.serviceName).catch(function (e) {
                                    apiError = true;
                                });

                            }

                        });
                        if(apiError)
                         return false;    
                        parentStateChange({
                            dataModel: emptyDataModel,
                            auditDataModel: emptyAuditDataModel,
                            summaryDataModel: emptySummaryDataModel,
                            currentOperation: 'query',
                            editable: false
                        });
                        break;
                    case 2:
                        if(!QueryStep1('query', $scope, emptyDataModel, emptyAuditDataModel))
                        return false;
                        break;

                    case 3:
                        if(!QueryStep2('query', $scope, emptyDataModel, emptyAuditDataModel))
                         return false;
                        break;

                }

                break;
            case 'modification':
                switch ($scope.currentStep) {
                    case 1:
                        await getServiceToken($scope.serviceName).then(
                                async (values) => {
                            if (values == null) {
                                await callRequestToken(globalData, $scope.serviceName).catch(function (e) {
                                    apiError = true;
                                });

                            }

                        });
                        if (apiError)
                          return false;  
                        parentStateChange({
                            dataModel: emptyDataModel,
                            auditDataModel: emptyAuditDataModel,
                            summaryDataModel: emptySummaryDataModel,
                            editable: true
                        });
                        break;
                    case 2:
                        if(!QueryStep1('modification', $scope, emptyDataModel, emptyAuditDataModel))
                         return false;   
                        break;

                    case 3:
                        if(!QueryStep2('modification', $scope, emptyDataModel, emptyAuditDataModel))
                          return false;
                        break;
                    default:
                        if(!DefaultStep('modification', $scope))
                          return false;  
                        break;
                }


                break;
            case 'deletion':
                switch ($scope.currentStep) {
                    case 1:
                        
                        await getServiceToken($scope.serviceName).then(
                                async (values) => {
                            if (values == null) {
                                await callRequestToken(globalData, $scope.serviceName).catch(function (e) {
                                    apiError = true;
                                });

                            }

                        });
                        if(apiError)
                        return false;    
                        parentStateChange({
                            dataModel: emptyDataModel,
                            auditDataModel: emptyAuditDataModel,
                            summaryDataModel: emptySummaryDataModel,
                            editable: false
                        });
                        break;
                    case 2:
                        if(!QueryStep1('deletion', $scope, emptyDataModel, emptyAuditDataModel))
                        return false;
                        break;

                    case 3:
                        if(!QueryStep2('deletion', $scope, emptyDataModel, emptyAuditDataModel))
                         return false;
                        break;
                    default:
                        if(!DefaultStep('deletion', $scope))
                          return false;  
                        break;
                }

                break;
            case 'authorization':
                switch ($scope.currentStep) {
                    case 1:
                       
                        await getServiceToken($scope.serviceName).then(
                                async (values) => {
                            if (values == null) {
                                await callRequestToken(globalData, $scope.serviceName).catch(function (e) {
                                    apiError = true;
                                });

                            }

                        });
                        if(apiError)
                          return false;   
                        parentStateChange({
                            dataModel: emptyDataModel,
                            auditDataModel: emptyAuditDataModel,
                            summaryDataModel: emptySummaryDataModel,
                            editable: false
                        });
                        break;
                    case 2:
                        if(!QueryStep1('authorization', $scope, emptyDataModel, emptyAuditDataModel))
                            return false;
                        break;

                    case 3:
                        if(!QueryStep2('authorization', $scope, emptyDataModel, emptyAuditDataModel))
                            return false;

                        break;
                    default:
                        if(!DefaultStep('authorization', $scope))
                         return false;   
                        break;
                }
                break;
        }
    }
return true;
}



async function QueryStep1(operation, $scope, emptyDataModel, emptyAuditDataModel) {
    if (mandatoryCheck(operation, $scope.currentStep)) {
        var businessEntity = [];
        var i = 0;
        for (let value of $scope.primaryKeyCols) {
            businessEntity[i] =
                    {
                        entityName: value,
                        entityValue: $scope.summaryDataModel.filter[value]
                    };
            i++;
        }

        var editable;
        switch (operation) {
            case 'query':
                editable = false;
                break;
            case 'modification':
                editable = true;
                break;
            case 'deletion':
                editable = false;
                break;
            case 'authorization':
                editable = false;
                break;
        }

        var apiObject = {
            serviceName: $scope.summaryService,
            serviceType: $scope.summaryServiceType,
            datamodel: $scope.summaryDataModel,
            operation: 'View',
            businessEntity: businessEntity,
            audit: $scope.auditDataModel
        };

        apiCallRequired = true;
        if (beforeApicall(operation, $scope.currentStep, apiObject)) {
            if (apiCallRequired) {
                $scope.summaryDataModel.SummaryResult = [];
                parentStateChange({
                    summaryDataModel: $scope.summaryDataModel
                });
                await callApi(apiObject, null, $scope.parentStateChange).catch(function (e) {
                                    apiError = true;
                                });;

                if (apiError) {
                    return false;
                }
                if (afterApicall) {

                }
            }
            Operation.bottomTabClick = 'Next';
            
            if ($scope.summaryDataModel.SummaryResult.length == 1) {
                parentStateChange({
                    dataModel: emptyDataModel,
                    auditDataModel: emptyAuditDataModel
                });
                for (let value of $scope.primaryKeyCols) {
                    $scope.dataModel[value] = $scope.summaryDataModel.SummaryResult[0][value];
                }
                parentStateChange({
                    dataModel: $scope.dataModel,
                    editable: editable
                });
                skipStep = true;
                
            } else {
                for (let value of $scope.primaryKeyCols) {
                    $scope.dataModel[value] = '';
                }



                parentStateChange({
                    dataModel: $scope.dataModel,
                    editable: editable
                });

            }
        }

    }
return true;    
}



async function QueryStep2(operation, $scope, emptyDataModel, emptyAuditDataModel) {
    var businessEntity = [];
    var i = 0;

    for (let value of $scope.primaryKeyCols) {
        if ($scope.dataModel[value] == '') {
            fn_Show_Exception_With_Param('FE-VAL-029', '');
            return false;
        }
        businessEntity[i] =
                {
                    entityName: value,
                    entityValue: $scope.dataModel[value]
                };
        i++;
    }

    var apiOperation = '';
    var editable;
    switch (operation) {
        case 'query':
            editable = false;
            break;
        case 'modification':
            editable = true;
            break;
        case 'deletion':
            editable = false;
            break;
        case 'authorization':
            editable = false;
            break;
    }


    var apiObject = {
        serviceName: $scope.serviceName,
        serviceType: $scope.serviceType,
        datamodel: $scope.dataModel,
        operation: 'View',
        businessEntity: businessEntity,
        audit: $scope.auditDataModel
    };
    apiCallRequired = true;


    if (beforeApicall(operation, $scope.currentStep, apiObject)) {
        if (apiCallRequired) {
            parentStateChange({
                dataModel: emptyDataModel,
                auditDataModel: emptyAuditDataModel
            });

            await callApi(apiObject, null, parentStateChange).catch(function (e) {
                apiError = true;
            });

            if (apiError) {
                return false;
            }
            if (afterApicall) {

            }
        }
        bottomTabClick = 'Next';

        parentStateChange({
            editable: editable
        });
    }
return true;
}


async function DefaultStep(operation, $scope) {
    var apiOperation = '';
    var editable;
    switch (operation) {
        case 'create':
            editable = true;
            apiOperation = 'create';
            break;
        case 'modification':
            editable = true;
            apiOperation = 'Modify';
            break;
        case 'deletion':
            editable = false;
            apiOperation = 'Delete';
            break;
        case 'authorization':
            editable = false;
            if (bottomTabClick == 'Auth') {
                apiOperation = 'Auth';
            } else {
                apiOperation = 'Reject';
            }

            break;

    }

    if ($scope.currentStep != getNumberOfSteps(operation) - 1) {
        if (mandatoryCheck(operation, $scope.currentStep)) {
            var businessEntity = [];
            var i = 0;
            for (let value of $scope.primaryKeyCols) {
                businessEntity[i] =
                        {
                            entityName: value,
                            entityValue: $scope.dataModel[value]
                        };
                i++;
            }

            var apiObject = {
                serviceName: $scope.serviceName,
                serviceType: $scope.serviceType,
                datamodel: $scope.dataModel,
                operation: apiOperation,
                businessEntity: businessEntity,
                audit: $scope.auditDataModel
            };
            if (beforeApicall(operation, $scope.currentStep, apiObject)) {
                if (apiCallRequired) {

                    if (fnDefaultandValidateAudit($scope))
                        callApi(apiObject, null, parentStateChange).catch(function (e) {
                            apiError = true;
                        });
                    else
                        return false;

                    if (apiError) {
                        return false;
                    }
                    if (afterApicall) {

                    }

                }


                bottomTabClick = 'Next';
                parentStateChange({
                    currentStep: $scope.currentStep + 1,
                    currentOperation: operation,
                    editable: editable
                });

            }
        }
    } else {
        if (bottomTabClick == 'Save' || bottomTabClick == 'Delete' || bottomTabClick == 'Auth' || bottomTabClick == 'Reject') {
            if (mandatoryCheck(operation, $scope.currentStep)) {
                var businessEntity = [];
                var i = 0;
                for (let value of $scope.primaryKeyCols) {
                    businessEntity[i] =
                            {
                                entityName: value,
                                entityValue: $scope.dataModel[value]
                            };
                    i++;
                }
                var apiObject = {
                    serviceName: $scope.serviceName,
                    serviceType: $scope.serviceType,
                    datamodel: $scope.dataModel,
                    operation: apiOperation,
                    businessEntity: businessEntity,
                    audit: $scope.auditDataModel
                };
                apiCallRequired = true;
                if (beforeApicall(operation, $scope.currentStep, apiObject)) {
                    if (apiCallRequired) {

                        if (fnDefaultandValidateAudit($scope))
                            callApi(apiObject, null, parentStateChange).catch(function (e) {
                                apiError = true;
                            });
                        if (apiError) {
                            return false;
                        }
                        if (afterApicall) {

                        }

                    }
                }
            }
        }
    }
return true;
}
