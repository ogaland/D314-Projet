<!DOCTYPE HTML>
<html>
    <head>
	<script src="js/canvasjs.min.js"></script>
	<script type="text/javascript">
		
	
		function displayStats(pId) 
		{
			var dataPointsA = [];
			var query = "getStats";
			
			var chart = new CanvasJS.Chart("chartContainer",
				{
					zoomEnabled: true,
					animationEnabled: true,
					title:{
						text: "Statistiques" 
					},
					axisX :{
						labelAngle: -30
					},
					axisY :{
						minimum: 0,
						maximum: 1000
					},
					data: [ 
							{
							  type: "line",
							  dataPoints: []
							}
						]
				});
				
			
			
					
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200)
				{
					var response = JSON.parse(this.responseText);

					for (var i = 0; i < 100; i++) 
					{
						y = parseInt(response.values[i]);
						
						dateTime = new Date(new Date().setDate(new Date().getDate()-7));
						
						dateTime.setSeconds(dateTime.getSeconds()+i*5);
						
						chart.options.data[0].dataPoints.push({
							x: dateTime,
							y: y
						});
						
						chart.render();
					}			
				}						
			};
			xmlhttp.open("GET", "inc/ajax.inc.php?query=" + query + "&id=" + pId, true);
			xmlhttp.send();
			
		}
			 
	</script>

	<title>Consommation Electrique</title>
    </head>

<body onpageshow="displayStats(<?php if($_GET['id'] != '') { echo $_GET['id']; } ?>)">
	<div id="chartContainer" style="height: 400px; width: 100%;">
	</div>
        min = 10W, moyenne = 50W, max = 100W
</body>

</html>