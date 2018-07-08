$(document).ready(function(){
	$("select").change(function(){
		 $( "#fileForm" ).empty();
		 $("#upload-file-message").empty();
		 var selectedValue = $(this).val();
	     if(selectedValue =='UPLOAD'){
	    	 $("#fileForm").append("<p><input type=\"file\" name=\"file\" size=\"60\" /></p>");
	    	 $("#fileForm").append("<p><input id=\"filePath\" name=\"filePath\" type=\"text\" placeholder=\"Please Enter the file path to save\"/>");
	    	 // $("#fileForm").attr("action","http://localhost:8090/upload");
	    	 //$("#fileForm").attr("method", "post");
	    	 $("#fileForm").attr("enctype","multipart/form-data");
	    	 $("#fileForm").append("<input id=\"uploadFile\" type=\"submit\" value=\"Submit\"/>"); 
	     }else if(selectedValue =='READ'){
	    	 $("#fileForm").append("<p><input id=\"fileName\" type=\"text\" size=\"60\" placeholder=\"Please Enter the file name to download\"/></p>");
	    	 $("#fileForm").append("<input id=\"readFile\" type=\"button\" value=\"Submit\"/>");
	     }else if(selectedValue =='LIST'){
	    	 $("#fileForm").append("<p><input id=\"filepath\" type=\"text\" size=\"60\" placeholder=\"Please Enter the folder name\"/></p>");
	    	 $("#fileForm").append("<input id=\"FilesList\" type=\"button\" value=\"Submit\"/>");
	     }else if(selectedValue =='DELETE'){
	    	 $("#fileForm").append("<p><input id=\"fileName\" type=\"text\" size=\"60\" placeholder=\"Please Enter the file name to delete\"/></p>");
	    	 $("#fileForm").append("<input id=\"delFile\" type=\"button\" value=\"Submit\"/>"); 
	     }
	    
	});
	$(document).on('click', '#uploadFile', function(){
		clearResponse();
		uploadFile();
		
	}); 
	
	$(document).on('click', '#FilesList', function(){
		clearResponse();
		fileNames($("#filepath").val());
		
	});
	$(document).on('click', '#delFile', function(){
		clearResponse();
		fileDelete($("#fileName").val());
	});
	$(document).on('click', '#readFile', function(){
		clearResponse();
		fileDownload($("#fileName").val());
	});
	function clearResponse(){
		$("#upload-file-message").empty();
	}
	
	function uploadFile() {
		$("#fileForm").on('submit',(function(e) {
			  e.preventDefault();
			  $.ajax({
			         url: "./file/upload",
			         type: "POST",
			         data:  new FormData(this),
			         contentType: false,
			         cache: false,
			         processData:false,
			         beforeSend : function()  {
			        	 $("#err").fadeOut();
			         },
			         success: function(data){
			        	 if(data=='invalid')   {
			        		 $("#err").html("Invalid File !").fadeIn();
			        	 } else {
			        		 $("#preview").html(data).fadeIn();
			        		 $("#fileForm")[0].reset(); 
			        	 }
			         },
			         error: function(error) {
			        	 if(error.status = "200"){
			        		 $("#upload-file-message").html(" Uploaded Successfully !!");
				        	 $("#fileForm")[0].reset(); 
			        	 }
			        	 $("#err").html(error).fadeIn();
			         }          
			    });
			 }));
	}  
	function fileNames(folderPath) {
		 $.ajax({
	        url: "./fileservice/file/list?folderPath="+folderPath,
	        type: "GET",
	        headers: {          
			    Accept: "application/json;", 
			    "Content-Type": "application/json; charset=utf-8",
			    'Access-Control-Allow-Origin': '*'
			},
	        success: function (result) {
	        	var fileNamesList ="";
	        	if(result != null && result.length >0){
	        		$.each( result[0], function( index, value ){
		        		fileNamesList += "<br>"+ value;
		        	});
	        	}else{
	        		fileNamesList =" There are no fiels "
	        	}
	        	$("#upload-file-message").html(fileNamesList);
	        },
	        error: function (error) {
	          $("#upload-file-message").html(error.responseJSON.error +"<br>"+error.responseJSON.exception+"<br>"+error.responseJSON.message
	        );
	        }
	      });
	}
	function fileDelete(fileName) {
		 $.ajax({
	        url: "./fileservice/file?fileName="+fileName,
	        type: "DELETE",
	        headers: {          
			    Accept: "application/json;", 
			    "Content-Type": "application/json; charset=utf-8",
			    'Access-Control-Allow-Origin': '*'
			},
	        success: function (result) {
	        	if(error.status = "200")
	        	$("#upload-file-message").html(" Deleted Successfully !!");
	        },
	        error: function (error) {
	        	if(error.status = "404")
	        		$("#upload-file-message").html("File Not found");
	        	if(error.status = "200")
		        	$("#upload-file-message").html(" Deleted Successfully !!");
	        }
	      });
	} 
	function fileDownload(fileName) {
	    $.ajax({
	        url: 'http://localhost:8762/fileservice/file?fileName='+fileName,
	        method: 'GET',
	        headers: {          
			    Accept: "application/json;", 
			    "Content-Type": "application/json; charset=utf-8",
			    'Access-Control-Allow-Origin': '*'
			},
	        xhrFields: {
	            responseType: 'blob'
	        },
	        success: function (data) {
	            var a = document.createElement('a');
	            var url = window.URL.createObjectURL(data);
	            a.href = url;
	            a.download = 'myfile.pdf';
	            a.click();
	            window.URL.revokeObjectURL(url);
	        },
	        error: function (error) {
	        	if(error.status == "404")
	        		$("#upload-file-message").html("File Not found");
	        	 
	        }
	    });
	}
	
});