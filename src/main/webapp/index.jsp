<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Code Utils</title>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.4.3/css/foundation.min.css">
<link rel="stylesheet"
	href="./home.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.4.3/js/foundation.min.js"></script>
<script src="./home.js"></script>
</head>
<body>
	<div class="grid-x">
		<div class="small-6 cell">
				<h1 class="header-label" id="result"> Auto Code Generate </h1>
		</div>
		<div class="small-6 cell">
				<textarea id="uiFields" placeholder="input json with fields"></textarea>
		</div>
	</div>

	<div class="grid-x">
		<div class="small-4 cell">
			<table>
				<tr>
					<td>
						<div class="ui-widget">
							<label for="tags">Table: </label> 
						</div>
					</td>
					<td>
						<input id="tags">
						<input id="table" class="hidden">
						<span id="loadingTableMsg">loading tables...</span>
					</td>
				</tr>
				<tr>
					<td><label>Database: </label></td><td><input id="database" type="text" value="qount" readonly></td>
				</tr>
				<tr>
					<td><label id="pk_div">pk: </label></td><td><input type="text" id="pk" value="id" readonly/></td>
				</tr>
			</table>
			<button class="button" id="loadNewTables" onclick="loadTables()">Load New Tables</button>
			<button class="button" id="code" onclick="downloadCode()">Generate Code</button>
		</div>
	
		<div class="small-4 cell">
			<table class="java-files-table">
				<tr><tr><th><label class='file-header'>Java Files:</label></th><th><input id="java-check" type="checkbox" checked></th></tr>
				<tr>
					<td><label class='file'>Pojo:</label></td><td><input id="pojo" type="checkbox" checked></td>
				</tr>
				<tr>
					<td><label class='file'>Dao:</label></td><td><input id="dao" type="checkbox" checked></td>
				</tr>
				<tr>
					<td><label class='file'>DaoImpl:</label></td><td><input id="daoImpl" type="checkbox" checked></td>
				</tr>
				<tr>
					<td><label class='file'>Querys:</label></td><td><input id="querys" type="checkbox" checked></td>
				</tr>
				<tr>
					<td><label class='file'>Controller:</label></td><td><input id="controller" type="checkbox" checked></td>
				</tr>
				<tr>
					<td><label class='file'>ControllerImpl:</label></td><td><input id="controllerImpl" type="checkbox"
						checked>
					</td>
				</tr>
			</table>
		</div>
		<div class="small-4 cell angular-files-table-div">
			<table class="angular-files-table">
				<tr><tr><th><label class='file-header'>Angular 4 Files:</label></th><th><input id="angular-check" type="checkbox" checked></th></tr>
				<tr>
					<td><label class='file'>Component:</label></td><td><input id="component" type="checkbox" checked></td>
				</tr>
				<tr>
					<td><label class='file'>Form:</label></td><td><input id="form" type="checkbox" checked></td>
				</tr>
				<tr>
					<td><label class='file'>Model:</label></td><td><input id="model" type="checkbox" checked></td>
				</tr>
				<tr>
					<td><label class='file'>Service:</label></td><td><input id="service" type="checkbox" checked></td>
				</tr>
				<tr>
					<td><label class='file'>Html:</label></td><td><input id="html" type="checkbox" checked></td>
				</tr>
			</table>
		</div>
		
	</div>

</body>
</html>