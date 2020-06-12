/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function getNumberOfSteps(operation) {

    switch (operation)
    {
        case 'create':
            return CreateConfig.endStep;
            break;
        case 'query':
            return CreateConfig.endStep;
            break;

        case 'authorization':
            return CreateConfig.endStep;
            break;
        case 'modification':
            return ModificationConfig.endStep;
            break;

        case 'deletion':
            return DeletionConfig.endStep;
            break;
        case 'Default':
            return defaultConfig.endStep;
            break;

    }
}

async function fnDefaultandValidateAudit($scope) {
    var globalData = JSON.parse(await AsyncStorage.getItem('GLOBAL'));
    const{auditDataModel} = $scope;
    switch ($scope.currentOperation) {
        case 'Query':
            //return true;
            break;
        case 'Create':
            //if (auditDataModel ==null) 
            //auditDataModel = new Object();
            auditDataModel.MakerID = globalData.userID;
            auditDataModel.AuthStat = 'Unauthorised';
            auditDataModel.MakerRemarks = '';
            auditDataModel.CheckerRemarks = '';
            auditDataModel.MakerDtStamp = '';
            auditDataModel.CheckerDtStamp = '';
            auditDataModel.CheckerID = '';
            auditDataModel.Version = "1";
            auditDataModel.RecordStat = 'Open';
            return true;
            break;

        case 'Modification':
            if (auditDataModel.RecordStat != 'Open') {
                fn_Show_Exception_With_Param('FE-VAL-008', '');
                return false;
            }

            if (auditDataModel.AuthStat == 'Unauthorised') {
                if (auditDataModel.MakerID != globalData.userID) {
                    fn_Show_Exception_With_Param('FE-VAL-007', '');
                    return false;
                } else if (auditDataModel.RecordStat == 'Open') {
                    //auditDataModel.Version=audit.Version;
                    //auditDataModel.MakerID=audit.MakerID;
                    auditDataModel.AuthStat = 'Unauthorised';
                    if ($scope.remarks != null && $scope.remarks != '') {
                        auditDataModel.MakerRemarks = $scope.remarks;
                    }

                    auditDataModel.CheckerRemarks = '';
                    auditDataModel.MakerDtStamp = '';
                    auditDataModel.CheckerDtStamp = '';
                    auditDataModel.CheckerID = '';

                    //audit.MakerRemarks=MakerRemarks;
                    auditDataModel.RecordStat = 'Open';
                    return true;
                }

            } else {
                auditDataModel.Version = (parseInt(auditDataModel.Version) + 1).toString();//Integration change
                auditDataModel.MakerID = globalData.userID;
                auditDataModel.AuthStat = 'Unauthorised';
                if ($scope.remarks != null && $scope.remarks != '') {
                    auditDataModel.MakerRemarks = $scope.remarks;
                }
                // auditDataModel.MakerRemarks = '';
                auditDataModel.CheckerRemarks = '';
                auditDataModel.MakerDtStamp = '';
                auditDataModel.CheckerDtStamp = '';
                auditDataModel.CheckerID = '';

                //audit.MakerRemarks=MakerRemarks;
                auditDataModel.RecordStat = 'Open';
                return true;
            }

            break;
        case 'Deletion':
            if (auditDataModel.RecordStat == 'Deleted') {
                // fn_Show_Exception('FE-VAL-008');
                fn_Show_Exception_With_Param('FE-VAL-008', '');
                return false;
            }

            if (auditDataModel.AuthStat == 'Unauthorised') {
                if (auditDataModel.MakerID != globalData.userID) {
                    // fn_Show_Exception('FE-VAL-009');
                    fn_Show_Exception_With_Param('FE-VAL-009', '');
                    return false;
                } else {
                    //auditDataModel.Version=audit.Version;
                    //auditDataModel.MakerID=audit.MakerID;
                    previousAuditScope = Object.assign({}, auditDataModel);

                    auditDataModel.AuthStat = 'Unauthorised';
                    //auditDataModel.MakerRemarks='';
                    auditDataModel.CheckerRemarks = '';
                    //auditDataModel.MakerDtStamp='';
                    auditDataModel.CheckerDtStamp = '';
                    auditDataModel.CheckerID = '';

                    //audit.MakerRemarks=MakerRemarks;
                    auditDataModel.RecordStat = 'Deleted';
                    if ($scope.remarks != null && $scope.remarks != '') {
                        auditDataModel.MakerRemarks = $scope.remarks;
                    }

                    return true;
                }

            } else {
                // previousAuditScope = Object.assign({}, auditDataModel);
                auditDataModel.Version = (parseInt(auditDataModel.Version) + 1).toString();
                auditDataModel.MakerID = globalData.userID;
                auditDataModel.AuthStat = 'Unauthorised';
                //audit.MakerRemarks=MakerRemarks;
                auditDataModel.RecordStat = 'Deleted';
                if ($scope.remarks != null && $scope.remarks != '') {
                    auditDataModel.MakerRemarks = $scope.remarks;
                }
                auditDataModel.CheckerRemarks = '';
                auditDataModel.MakerDtStamp = '';
                auditDataModel.CheckerDtStamp = '';
                auditDataModel.CheckerID = '';

                return true;
            }

            break;

        case 'Authorisation':
            /* if (audit.RecordStat=='D')
             { 		 
             fn_Show_Exception('FE-VAL-008');
             return false;
             } */

            if (auditDataModel.AuthStat != 'Unauthorised') {
                // fn_Show_Exception('FE-VAL-011');
                fn_Show_Exception_With_Param('FE-VAL-011', '');
                return false;
            }
            if (auditDataModel.MakerID == globalData.userID) {
                // fn_Show_Exception('FE-VAL-020');
                fn_Show_Exception_With_Param('FE-VAL-020', '');
                return false;
            }
            previousAuditScope = Object.assign({}, auditDataModel);
            auditDataModel.CheckerID = globalData.userID;
            //auditDataModel.MakerRemarks='';
            //auditDataModel.MakerRemarks=dialogRemarks;
            if ($scope.remarks != null && $scope.remarks != '') {
                auditDataModel.CheckerRemarks = $scope.remarks;
            }
            // auditDataModel.CheckerRemarks = dialogRemarks;
            //auditDataModel.MakerDtStamp='';
            auditDataModel.CheckerDtStamp = '';
            //auditDataModel.CheckerID='';

            auditDataModel.AuthStat = 'Authorised';
            break;
        case 'Reject':

            if (auditDataModel.AuthStat != 'Unauthorised') {
                // fn_Show_Exception('FE-VAL-011');
                fn_Show_Exception_With_Param('FE-VAL-011', '');
                return false;
            }
            if (auditDataModel.MakerID == globalData.userID) {
                fn_Show_Exception_With_Param('FE-VAL-026', '');
                // fn_Show_Exception('FE-VAL-026');
                return false;
            }
            // previousAuditScope = Object.assign({}, auditDataModel);
            auditDataModel.CheckerID = globalData.userID;
            auditDataModel.AuthStat = 'Rejected';
            //auditDataModel.MakerRemarks='';
            if ($scope.remarks != null && $scope.remarks != '') {
                auditDataModel.CheckerRemarks = $scope.remarks;
            }
            // auditDataModel.CheckerRemarks = dialogRemarks;
            //auditDataModel.MakerDtStamp='';
            auditDataModel.CheckerDtStamp = '';
            //auditDataModel.CheckerID='';

            break;


    }
    //$scope1.$apply();		  
    return true;

}
