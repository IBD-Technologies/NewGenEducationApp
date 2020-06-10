/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var SearchPosition;

var SearchConfig = {
    fieldID: '',
    headings: [],
    apiResultCols: [],
    resultreduceFields: [],
    searchService: ''
};
var searchText;
function clearSearchResult() {
    parentStateChange({
        searchVisible: false
    });
    /*SearchConfig = {
        fieldID: '',
        headings: [],
        apiResultCols: [],
        resultreduceFields: [],
        searchService: ''
    };*/
    searchDataModel = {
        searchFilter: "",
        searchResults: []
    };
    $("#searchResultHeader").empty();
    $("#searchResultBody").empty();
    $("#searchWrapper").empty();
    $("#searchWrapper").remove();
    
    

}



async function launchSuggestion() {
    var serviceName = '';

    if (searchText != '')
    {
        if (searchDataModel.searchResults.length != 0) {
            showResult();
            parentStateChange({
                searchVisible: true,
                searchIsLoading: false
            });
        } else {
            var apiObject = {
                serviceName: SearchConfig.searchService,
                serviceType: 'Institute',
                datamodel: searchDataModel,
                operation: 'View',
                businessEntity: [{"entityName": "filter", "entityValue": ""}],
                audit: {}
            };
            
            
            if (searchText.length ==1)
            {
              clearSearchResult();
              buildSearch();   
              parentStateChange({

                searchVisible: true,
                searchIsLoading:false
            });   
            await callApi(apiObject, null).catch(function(e){apiError=false});
            if (apiError)
                return;
            }
            
            showResult();

           
        }
    } else {
        clearSearchResult();
        parentStateChange({
            seachVisible: false
        });
    }

}


function showResult() {

    var searchResultBodyTemplate='';

    $("#searchResultBody").empty();
    
    var filterData = [];
      var i = 0;
    var list = searchDataModel.searchResults;
    if (list.length != 0) {
      if (searchText != '') {
        for (let rowData of list) {
          for (let colkey of SearchConfig.apiResultCols) {
              if (colkey==SearchConfig.fieldID)
              {   
                if (rowData[colkey].toLowerCase().startsWith(searchText.toLowerCase())) {
                       filterData[i] = rowData;
                         i++;
                         break;
                     }         
              
            }
          }
        }
      }
      else {
        filterData = list;
      }
  }
  
  if (filterData.length>0)
  {   
    for (var i = 0; i < filterData.length; i++) {
         searchResultBodyTemplate = searchResultBodyTemplate+'<div id="SearchResult_' + i + '" class="row searchRow searchResultRow" >\n';
         var cols=Math.floor(12/SearchConfig.apiResultCols.length);
         for (var j = 0; j < SearchConfig.apiResultCols.length; j++) {
            searchResultBodyTemplate = searchResultBodyTemplate +
                   
                   '<div class="col-sm-'+cols+' col-md-'+cols+' col-lg-'+cols+' col-xs-'+cols+' ">\n' +
                    filterData[i][SearchConfig.apiResultCols[j]] + '\n' +
                    '</div>\n';

        
        }
        searchResultBodyTemplate = searchResultBodyTemplate+'</div>';
    }
    if(searchResultBodyTemplate!=''){
     $("#searchResultBody").append(searchResultBodyTemplate);
       $('.searchResultRow').click(function()
        {     
          hideSearchResult(this.id,filterData);
           }       
     );  
     
       parentStateChange({
                searchVisible: true
            });
    }
    else
    {
         parentStateChange({
                searchVisible: false
            });
    }
    }  
}

function hideSearchResult(id,filterData) {
    console.log(id);
    var searchId = id.split("_")[0];
    var index = id.split("_")[1];
    for (var i = 0; i < filterData.length; i++) {
        if (i == index) {
            for (var k = 0; k < SearchConfig.resultreduceFields.split('~').length; k++)
            {
                $("#" + SearchConfig.resultreduceFields.split('~')[k]).val(filterData[i][SearchConfig.apiResultCols[k]]);
            }
           // console.log(filterData[i].studentClass);
        }
    }

    clearSearchResult();
}

function buildSearch()
{

    var searchDiv = '<div id="searchWrapper" class="card searchResult" ng-show=searchVisible>\n' +
            '<div class="header" id="searchResultHeader">\n' +
              '<span id="searchClose">x</span>\n'+
            '</div>\n' +
            '<div class="body" id="searchResultBody">\n' +
              
              "<div class=\"spinner-center\" ng-show=searchIsLoading>"+
	         "<div class=\"preloader pl-size-xs\">"+
                      "<div class=\"spinner-layer pl-pink\">"+
                           "<div class=\"circle-clipper left\">"+
                                 "<div class=\"circle\"></div>"+
                                    "</div>"+
                       "<div class=\"circle-clipper right\">"+
                        "<div class=\"circle\"></div>"+
                                          "</div>"+
                                  "</div>"+
                              " </div>"+
	                     "</div>"+
                       "</div>"+
                     "</div>"+  
                  '</div>\n' +
                  
             '</div>\n';

    getSubScreenScope().addDynamicElement(SearchConfig.fieldID,'After',searchDiv);
    var resultHeadings='';
    resultHeadings=resultHeadings+'<div class="row searchRow closeBox">\n';
                                  
    var cols=Math.floor(12/SearchConfig.headings.length);
    for (var i = 0; i < SearchConfig.headings.length; i++)
    {
        resultHeadings = resultHeadings +
                '<div class="col-sm-'+cols+' col-md-'+cols+' col-lg-'+cols+' col-xs-'+cols+'">\n' +
                '<b>' + SearchConfig.headings[i] + '</b>\n' +
                '</div>\n';

    }
     resultHeadings=resultHeadings+'</div>\n';
     getSubScreenScope().addDynamicElement('searchClose','After',resultHeadings);
   
$(document).mouseup(function(e) 
{
    var container = $('#searchWrapper');
    if(e.target.id=='searchClose')
    {
        clearSearchResult();
    }
    // if the target of the click isn't the container nor a descendant of the container
    if (!container.is(e.target) && container.has(e.target).length === 0) 
    {
         clearSearchResult();
    }
});

}

  