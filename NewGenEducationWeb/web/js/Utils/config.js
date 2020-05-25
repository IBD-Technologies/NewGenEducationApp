var Exception=[{error_code:"FE-VAL-001",error_type:"E",error_message:"Please enter value for $1"},
				{error_code:"FE-VAL-002",error_type:"E",error_message:"$1 is Invalid, Please change it"},
				{error_code:"FE-VAL-003",error_type:"E",error_message:"Invalid password"},
				{error_code:"FE-VAL-004",error_type:"E",error_message:"User already exists"},
				{error_code:"FE-VAL-005",error_type:"E",error_message:"Password is too weak"},
                {error_code:"FE-VAL-006",error_type:"E",error_message:"Sorry You do not have access for this operation"},
				{error_code:"FE-VAL-007",error_type:"E",error_message:"Record is not yet Authorised,Maker only can do Modification"},
				{error_code:"FE-VAL-008",error_type:"E",error_message:"Record is Deleted/Removed, can not be Modified"},
				{error_code:"FE-VAL-009",error_type:"E",error_message:"Record is not yet Authorised,Maker only can do Deletion"},
				{error_code:"FE-VAL-010",error_type:"E",error_message:"Record is already Deleted/Removed"},
				{error_code:"FE-VAL-011",error_type:"E",error_message:"Record is already Authorised/Rejected"},
				{error_code:"FE-VAL-012",error_type:"I",error_message:"Record is Successfully Created"},
			    {error_code:"FE-VAL-013",error_type:"I",error_message:"Record is Successfully Modified"},
				{error_code:"FE-VAL-014",error_type:"I",error_message:"Record is Successfully Deleted"},
			    {error_code:"FE-VAL-015",error_type:"I",error_message:"Record is Successfully Authorised"},
				{error_code:"FE-VAL-016",error_type:"I",error_message:"Record is Successfully Retrieved"},
				{error_code:"FE-VAL-017",error_type:"I",error_message:"Record is Successfully Rejected"},
				{error_code:"FE-VAL-019",error_type:"C",error_message:"Do you want to really Reject?"},
				{error_code:"FE-VAL-018",error_type:"C",error_message:"Do you want to really Delete?"},
				{error_code:"FE-VAL-020",error_type:"E",error_message:"Maker can not Authorise"},
				{error_code:"FE-VAL-021",error_type:"C",error_message:"Do you want to really Authorise?"},
				{error_code:"FE-VAL-022",error_type:"E",error_message:"From Date is greater than To Date"},
				{error_code:"FE-VAL-023",error_type:"E",error_message:"To Date is less than From Date"},
				{error_code:"FE-VAL-024",error_type:"E",error_message:"Cannot apply leave for past Date"},
				{error_code:"FE-VAL-025",error_type:"C",error_message:"From Date/To date  is past  Is it Ok?"},
				{error_code:"FE-VAL-026",error_type:"E",error_message:"Maker Cannot Reject"},
				{error_code:"FE-VAL-027",error_type:"E",error_message:"Please select Only one Record for Detail View"},
				{error_code:"FE-VAL-028",error_type:"E",error_message:"Please enter atleast one filter value "},
				{error_code:"FE-VAL-029",error_type:"E",error_message:"Please select atleast one record for Detail View "},
				{error_code:"FE-VAL-030",error_type:"E",error_message:"Record is submitted"},
				{error_code:"FE-VAL-031",error_type:"I",error_message:"Institute is Changed Successfully"},
				{error_code:"FE-VAL-032",error_type:"I",error_message:"Report is Generated Successfully"},
				{error_code:"FE-VAL-033",error_type:"C",error_message:"Do you want to really enroll your children to this activity?"},
                                {error_code:"FE-VAL-034",error_type:"I",error_message:"Your Children is successfully Enrolled"},
                                {error_code:"FE-VAL-035",error_type:"E",error_message:"Only youtube video supported"},
                                {error_code:"FE-VAL-036",error_type:"C",error_message:"Do you want to really sign your children's progress card"},
                                {error_code:"FE-VAL-037",error_type:"I",error_message:"Progress card is successfully signed"},
                                {error_code:"FE-VAL-038",error_type:"I",error_message:"Please Enter Student ID or Name"}
                   
];
				
var MenuDetail=[{menuName:"TeacherTimeTable",angularControl:"TeacherTimeTableCtrl"},
				{menuName:"StudentTimeTable",angularControl:"StudentTimeTableCtrl"}];

var MVWPAGESIZE = 4;				
				
var ENTITY={GenralLevelConfigutations:'Institute'};

var TEST="YES";