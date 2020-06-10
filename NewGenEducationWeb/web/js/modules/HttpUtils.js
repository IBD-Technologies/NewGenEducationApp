/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var BASE_URL = 'https://cohesivetest.ibdtechnologies.com/CohesiveGateway/';
var FILE_URL = 'https://cohesivetest.ibdtechnologies.com';

// export const BASE_URL = 'http://jagdamshah.com/api/';


  function getURL(serviceType,serviceName) {
    return BASE_URL + serviceType + '/' + serviceName;
  }
  
  function FILE_URL(){
    return FILE_URL;
  }
  
 function DEFAULT_IMAGE_FILE_PATH(){
    return require("/images/ic_profile.jpg");
  }


