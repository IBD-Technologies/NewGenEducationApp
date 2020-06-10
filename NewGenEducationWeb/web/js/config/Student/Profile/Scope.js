/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function initScope()
{
    var $scope = angular.element(document.getElementById('SubScreenCtrl')).scope();

    $scope.familyDetailSelectedIndex= 1;
    $scope.familyEmptyrecord= {
            memberName: "",
            memberID: "",
            memberRelationship: "",
            memberOccupation: "",
            memberEmailID: "",
            memberContactNo: "",
            notificationRequired: false,
            language: "",
            memberImgPath: "/images/ic_profile.jpg"
        };
                
        $scope.summaryDataModel= {
            filter: {
                studentName: "",
                studentID: "",
                class: "",
                authStat: "",
                recordStat: ""
            },
            SummaryResult: [
                {
                    studentID: "",
                    studentName: "",
                    class: "",
                    authStat: "",
                    recordStat: ""
                }
            ]
        };

        $scope.emptySummaryDataModel= {
            filter: {
                studentName: "",
                studentID: "",
                class: "",
                authStat: "",
                recordStat: ""
            },
            SummaryResult: [
                {
                    studentID: "",
                    studentName: "",
                    class: "",
                    authStat: "",
                    recordStat: ""
                }
            ]
        };
        
     $scope.dataModel= {
            studentName: "",
            studentID: "",
            profileImgPath: "",
            note: "",
            general: {
                class: "",
                gender: "",
                dob: "",
                bloodGroup: "",
                nationalID: "",
                address: {
                    addressLine1: "",
                    addressLine2: "",
                    addressLine3: "",
                    addressLine4: "",
                    addressLine5: ""
                }
            },
            emergency:
                    {
                        existingMedicalDetails: ""
                    },
            family: [
                {
                    memberName: "",
                    memberID: "",
                    memberRelationship: "",
                    memberOccupation: "",
                    memberEmailID: "",
                    memberContactNo: "",
                    notificationRequired: false,
                    language: "",
                    memberImgPath: ''
                }
            ],
            audit:
                    {
                        MakerID: "",
                        AuthStat: "",
                        MakerRemarks: "",
                        CheckerRemarks: "",
                        MakerDtStamp: "",
                        CheckerDtStamp: "",
                        CheckerID: "",
                        Version: "",
                        RecordStat: "Open"
                    },

        };

        $scope.emptyDataModel= {
            studentName: "",
            studentID: "",
            profileImgPath: "",
            note: "",
            general: {
                class: "",
                gender: "",
                dob: "",
                bloodGroup: "",
                nationalID: "",
                address: {
                    addressLine1: "",
                    addressLine2: "",
                    addressLine3: "",
                    addressLine4: "",
                    addressLine5: ""
                }
            },
            emergency:
                    {
                        existingMedicalDetails: ""
                    },
            family: [
                {
                    memberName: "",
                    memberID: "",
                    memberRelationship: "",
                    memberOccupation: "",
                    memberEmailID: "",
                    memberContactNo: "",
                    notificationRequired: false,
                    language: "",
                    memberImgPath: ''
                }
            ],
        };
        
       $scope.primaryKeyCols=['studentID'];
       $scope.serviceName= 'StudentProfile';
       $scope.serviceType= 'Student';
       $scope.summaryService= 'StudentProfileSummary';
       $scope.summaryServiceType= 'StudentSummaryEntity';
       

}
function createMandatoryCheck($scope) {
  switch ($scope.currentStep) {
    case 2:
      if ($scope.dataModel.studentName == '' || $scope.dataModel.studentName == null) {
         fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
        return false;
      }
      if ($scope.dataModel.studentID == '' || $scope.dataModel.studentID == null) {
        fn_Show_Exception_With_Param('FE-VAL-001',  ['Student ID'] );
        return false;
      }
      if ($scope.dataModel.general.class == '' || $scope.dataModel.general.class == null) {
        fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
        return false;
      }
      if ($scope.dataModel.general.gender == '' || $scope.dataModel.general.gender == null) {
        fn_Show_Exception_With_Param('FE-VAL-001', ['Gender']);
        return false;
      }
      break;
    case 3:
      if ($scope.dataModel.family == null || $scope.dataModel.family.length == 0) {
        fn_Show_Exception_With_Param('FE-VAL-001', ['Family Member Details'] );
       return false;
      }

      for (var i = 0; i < $scope.dataModel.family.length; i++) {
        if ($scope.dataModel.family[i].memberName == '' || $scope.dataModel.family[i].memberName == null) {

          fn_Show_Exception_With_Param( 'FE-VAL-001', ["Family Member Name In Family Tab " + "record " + (i + 1)]);
          return false;
        }
        if ($scope.dataModel.family[i].memberRelationship == '' || $scope.dataModel.family[i].memberRelationship == null); {

         fn_Show_Exception_With_Param( 'FE-VAL-001',["Family Member Relationship in Family Tab " + "record " + (i + 1)]);
          return false;
        }
        if ($scope.dataModel.family[i].memberContactNo == '' || $scope.dataModel.family[i].memberContactNo == null) {
          fn_Show_Exception_With_Param('FE-VAL-001', ["Family Member Contact Number in Family Tab " + "record " + (i + 1)]);
          return false;
        }
      }
  }
  return true;
}
function modificationMandatoryCheck($scope) {
  
    switch ($scope.currentStep) {
    case 2:
      if ($scope.dataModel.studentName == '' || $scope.dataModel.studentName == null) {
         fn_Show_Exception_With_Param('FE-VAL-001', ['Student Name']);
        return false;
      }
      if ($scope.dataModel.studentID == '' || $scope.dataModel.studentID == null) {
        fn_Show_Exception_With_Param('FE-VAL-001',  ['Student ID'] );
        return false;
      }
      if ($scope.dataModel.general.class == '' || $scope.dataModel.general.class == null) {
        fn_Show_Exception_With_Param('FE-VAL-001', ['Class']);
        return false;
      }
      if ($scope.dataModel.general.gender == '' || $scope.dataModel.general.gender == null) {
        fn_Show_Exception_With_Param('FE-VAL-001', ['Gender']);
        return false;
      }
      break;
    case 3:
      if ($scope.dataModel.family == null || $scope.dataModel.family.length == 0) {
        fn_Show_Exception_With_Param('FE-VAL-001', ['Family Member Details'] );
       return false;
      }

      for (var i = 0; i < $scope.dataModel.family.length; i++) {
        if ($scope.dataModel.family[i].memberName == '' || $scope.dataModel.family[i].memberName == null) {

          fn_Show_Exception_With_Param( 'FE-VAL-001', ["Family Member Name In Family Tab " + "record " + (i + 1)]);
          return false;
        }
        if ($scope.dataModel.family[i].memberRelationship == '' || $scope.dataModel.family[i].memberRelationship == null); {

         fn_Show_Exception_With_Param( 'FE-VAL-001',["Family Member Relationship in Family Tab " + "record " + (i + 1)]);
          return false;
        }
        if ($scope.dataModel.family[i].memberContactNo == '' || $scope.dataModel.family[i].memberContactNo == null) {
          fn_Show_Exception_With_Param('FE-VAL-001', ["Family Member Contact Number in Family Tab " + "record " + (i + 1)]);
          return false;
        }
      }
  }
  return true;
}

function queryMandatoryCheck($scope)
{
    switch ($scope.currentStep) {
    case 2:
      if (($scope.summaryDataModel.filter.studentName == '' || $scope.summaryDataModel.filter.studentName == null)
        && ($scope.summaryDataModel.filter.studentID == '' || $scope.summaryDataModel.filter.studentID == null)
        && ($scope.summaryDataModel.filter.class == '' || $scope.summaryDataModel.filter.class == null)
        && ($scope.summaryDataModel.filter.authStat == '' || $scope.summaryDataModel.filter.authStat == null)
      ) {
          fn_Show_Exception_With_Param('FE-VAL-028','');
        return false;
      }
      break;
  }
  return true;
    
}
