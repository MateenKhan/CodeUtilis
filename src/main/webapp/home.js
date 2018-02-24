var serviceUrl = window.location.protocol + "//" + window.location.host + ""+ window.location.pathname;
var tablesUrl =  serviceUrl+ "service/data/tables"; 
$(function() {
//	loadTables();
});

function loadTables(){
	$("#loadingTableMsg").show(200);
	$.ajax({
		type : "GET",
		url : tablesUrl,
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			console.log(data);
			$("#loadingTableMsg").hide(200);
			$("#tags").autocomplete({
				source : data,
				select : function(a, b) {
					$("#result").text($("#database").val()+" : "+b.item.value);
					$("#table").text(b.item.value);
					$("#pk_div").show(200);
				}
			});
		},
		error : function(data) {
			showError(data)
		}
	});
}

function downloadCode() {
		url = serviceUrl;
		url += "service/data/code";
		var json={
				table : $("#table").text(),
				pk : $("#pk").val(),
				database : $("#database").val(),
				java : $("#java-check").is(':checked'),
				angular4 : $("#angular-check").is(':checked'),
				pojo : $("#pojo").is(':checked'),
				dao : $("#dao").is(':checked'),
				daoImpl : $("#daoImpl").is(':checked'),
				querys : $("#querys").is(':checked'),
				controller : $("#controller").is(':checked'),
				controllerImpl : $("#controllerImpl").is(':checked'),
				component : $("#component").is(':checked'),
				form : $("#form").is(':checked'),
				model : $("#model").is(':checked'),
				service : $("#service").is(':checked'),
				html : $("#html").is(':checked'),
				uiFields : $("#uiFields").val(),
				pk : $("#pk").val()
			};
		
		var xhr = new XMLHttpRequest();
        xhr.open('POST', url, true);
        xhr.responseType = "blob";
        xhr.onload = function (event) {
            var data = xhr.response;
            if (data) {
                    var zipAsDataUri = 'data:application/zip,' + data;
                    $('body').append('<a id="zipdata" ></a>');
                    var blob = new Blob([data], { type: "application/zip" });
                    if (navigator.msSaveBlob) { 
                        navigator.msSaveBlob(blob, "post.zip")
                    } else {                        
                        var url=window.URL.createObjectURL(blob);
                        $('#zipdata').attr('href',url);
                        $('#zipdata').attr('download', "get.zip");                        
                        $("#zipdata")[0].click();                                                
                    }
                }
                
        };
        xhr.send(JSON.stringify(json));
	}

	function showError(text){
		alert(text);
	}
	
	function getNewTables() {
		$.ajax({
			type : "GET",
			url : window.location.protocol + "//" + window.location.host + ""
					+ window.location.pathname + "service/data/tables",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				console.log(data);
				return data;
			},
			error : function(data) {
				showError(data)
			}
		});
	}