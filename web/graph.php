<!DOCTYPE HTML>
<html>
    <head>
	<script src="js/canvasjs.min.js"></script>
	<script type="text/javascript">
		
		function displayStats(pId) 
		{
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
					var min = parseInt(response.values[0]);
					var moy = 0;
					var max = parseInt(response.values[0]);

					for (var i = 0; i < 100; i++) 
					{
						y = parseInt(response.values[i]);
						moy += y;
						
						if (y < min)
						{
							min = y;
						}
						
						if (y > max)
						{
							max = y;
						}					
						var dateTime = new Date();
						dateTime.setDate(dateTime.getHours()-7*24);
						dateTime.setSeconds(dateTime.getSeconds()+i*5);
						
						chart.options.data[0].dataPoints.push({
							x: dateTime,
							y: y
						});
						
						chart.render();
					}
					moy = moy/100;
					document.getElementById("minValue").innerHTML = min;
					document.getElementById("moyValue").innerHTML = moy;
					document.getElementById("maxValue").innerHTML = max;
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
        <div><p>Min : <span id="minValue"></span> W</p><p>Moy : <span id="moyValue"></span> W</p><p>Max : <span id="maxValue"></span> W</p></div>
</body>

</html>