    $(document).ready(function() {
      $("#upload-file-input").on("change", uploadFile);
    });
    
    /**
     * Upload the file sending it via Ajax at the Spring Boot server.
     */
    var token = '';
    
    function uploadFile() {
      $.ajax({
        url: "http://localhost:8090/file/uploadFile",
        type: "POST",
        data: new FormData($("#upload-file-form")[0]),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        crossDomain : true,
        success: function (result) {
	          $("#upload-file-message").text("File succesfully uploaded");
	    },
	    error: function (error) {
	          $("#upload-file-message").text(
	              "File not uploaded (perhaps it's too much big)");
	    }
      });
    } // function uploadFile
   