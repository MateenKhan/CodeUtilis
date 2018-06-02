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
<body onload="greetUser()">
	<h2 id='url_user_name'></h2>
	<div class="grid-x">
		<div class="small-6 cell">
				<h2 class="header-label" id="result"> Code Generate </h2>
		</div>
		<div class="small-6 cell">
				<textarea id="uiFields" placeholder="input json with fields"></textarea>
		</div>
	</div>

	<div class="grid-x">
		<h1>Sample input:</h1>
		<p>{"name":"Discounts","fields":[{"name":"id","type":"String"},{"name":"name","type":"String"},{"name":"description","type":"String"},{"name":"type","type":"String"}]}</p>
		<div class="small-12 cell angular-files-table-div">
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
				
				<tr>
					<td> <button class="button" id="code" onclick="downloadCode()">Generate Code</button> </td>
				</tr>
			</table>
		</div>
		
	</div>

</body>
</html>
