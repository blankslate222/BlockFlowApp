cmpe.controller('userDashCtrl', function($scope, $rootScope, $http, $cookieStore) {

	google.charts.setOnLoadCallback(load);
	
	function load() {
		$http.get("/data/userdashboardchart/"+$cookieStore.get('user').id).success(function(data) {
			
			var objs= data.controllerResponse.responseObject;
			for(var i=0;i<objs.length;i++){
				for(var key in objs[i]){
					if(objs[i][key][0].statuscounts.length==0)
						continue;
					var divRow = document.createElement('div');
					divRow.className = 'row mt';
					
					var div1 = document.createElement('div');
					div1.className = 'col-lg-6';
					var h4 = document.createElement('h4');
					h4.innerText= "Request-" + key + " Summary";
					div1.appendChild(h4);
					var divchart_pie = document.createElement('div');
					divchart_pie.id = "chart_pie"+i;
					divchart_pie.style = 'width:600px; height: 300px;';
					div1.appendChild(divchart_pie);
					divRow.appendChild(div1);
					
					var div2 = document.createElement('div');
					div2.className = 'col-lg-6';
					var h4 = document.createElement('h4');
					h4.innerText= "Request Details";
					div2.appendChild(h4);
					var divchart_flow = document.createElement('div');
					divchart_flow.id = "chart_flow"+i;
					divchart_flow.style = 'width:600px; height: 300px;';
					div2.appendChild(divchart_flow);
					divRow.appendChild(div2);
					
					var block=document.getElementById("block");
					block.appendChild(divRow);
					
					drawChart(objs[i][key],i);
				}
			}
		});
	}
	
    function drawChart(obj,id) {
    	var comp=0,pend=0;
    	for(var i=0;i<obj[0].statuscounts.length;i++){
    		for(var key in obj[0].statuscounts[i]){
    			if(key==="PENDING" || key==="PENDING_ACTION")
    				pend+=obj[0].statuscounts[i][key];
    			else
    				comp+=obj[0].statuscounts[i][key];
    		}
    	}
    	
    	var arrOutputTemp=
    	[
         ['Status', 'Percentage'],
         ['Completed',     comp],
         ['Pending',      pend]
       ]
    	
      var data = google.visualization.arrayToDataTable(arrOutputTemp);

      var options = {
        title: 'Request Status',
        pieHole: 0.4,
      };

      var chart = new google.visualization.PieChart(document.getElementById("chart_pie"+id));
      chart.draw(data, options);
      drawChart2(obj,id);
    }

    function toMilliseconds(minutes) {
      return minutes * 60 * 1000;
    }

    function drawChart2(obj,id) {

      var otherData = new google.visualization.DataTable();
      otherData.addColumn('string', 'Task ID');
      otherData.addColumn('string', 'Task Name');
      otherData.addColumn('string', 'Resource');
      otherData.addColumn('date', 'Start');
      otherData.addColumn('date', 'End');
      otherData.addColumn('number', 'Duration');
      otherData.addColumn('number', 'Percent Complete');
      otherData.addColumn('string', 'Dependencies');
/*
      otherData.addRows([
                 
                         ['CCDept', 'Credit Card Dept', '1', null, null, toMilliseconds(10), 0, null],
                         ['SDept', 'Savings Dept', '1', null, null, toMilliseconds(10), 0, 'CCDept'],
                         ['LDept', 'Loan Dept', '2', null, null, toMilliseconds(10), 0, 'SDept'],
                         ['CDept', 'Checking Dept', '2', null, null, toMilliseconds(10), 0, 'LDept'],
                         ['FDept', ' Dept', '2', null, null, toMilliseconds(10), 0, 'CDept'],

      ]);*/
      var arrOutput=[];
      var status;
      debugger;
      for(var i=0;i<obj[1].nodedetails.length;i++){
    	  if(obj[1].nodedetails[i]["status"]==="PENDING_ACTION" || obj[1].nodedetails[i]["status"]==="PENDING")
    		  status='1';
    	  else
    		  status='2';
    	  if(obj[1].nodedetails[i]["level"]==1)
    		  arrOutput.push([obj[1].nodedetails[i]["level"]+"", obj[1].nodedetails[i]["deptName"], status, null, null, toMilliseconds(10), 0, null]);
    	  else
    		  arrOutput.push([obj[1].nodedetails[i]["level"]+"", obj[1].nodedetails[i]["deptName"], status, null, null, toMilliseconds(10), 0, (Number(obj[1].nodedetails[i]["level"])-1)+""]);
      }
      otherData.addRows(arrOutput);
      var options = {
        height: 300,
        gantt: {
        }
      };

      var chart = new google.visualization.Gantt(document.getElementById("chart_flow"+id));
      chart.draw(otherData, options);
    }
});

cmpe.controller('adminDashCtrl', function($scope, $rootScope, $http, $cookieStore) {

	google.charts.setOnLoadCallback(drawDualX);

	function drawDualX() {
		/*
		 * var arrOutputTemp=[ [ 'Status', 'Accepted', 'Rejected', 'Pending', {
		 * role : 'annotation' } ], [ 'CC Dept', 10, 24, 20, '' ], [ 'Loan
		 * Dept', 16, 22, 23, '' ], [ 'Savings Dept', 28, 19, 29, '' ] ];
		 * console.log(arrOutputTemp);
		 */
		
		$http.get("/data/requeststatusbydeptchart/"+$cookieStore.get('client').id).success(function(data) {
	    	var arrOutput = [[ 'Status', 'Approved', 'Rejected', 'Pending', {
				role : 'annotation'
			}]];
			var objs;
			objs = data.controllerResponse;
			console.log(objs);
			var app=0,rej=0,pend=0;
			var prevName="";
			var keys=Object.keys(objs);
			keys.sort();
			for(var i=0;i<keys.length;i++){
				
				var DeptAndStat=keys[i].split("|||");
				var currName=DeptAndStat[0];
				var currStatus=DeptAndStat[1];
				if(prevName!=currName){
					if(prevName!=""){
						var arr=[];
						arr.push(prevName);arr.push(app);arr.push(rej);arr.push(pend);arr.push('');
						arrOutput.push(arr);
					}
					prevName=currName;
					app=0;rej=0;pend=0;
				}
				
				if(currStatus==="APPROVED")
					app=Number(objs[keys[i]]);
				if(currStatus==="REJECTED")
					rej=Number(objs[keys[i]]);
				if(currStatus==="PENDING")
					pend=Number(objs[keys[i]]);
			}
			
			var arr=[];
			arr.push(prevName);arr.push(app);arr.push(rej);arr.push(pend);arr.push('');
			arrOutput.push(arr);
			var data = google.visualization.arrayToDataTable(arrOutput);
	
			var options = {
				width : 600,
				height : 400,
				legend : {
					position : 'top',
					maxLines : 3
				},
				bar : {
					groupWidth : '75%'
				},
				isStacked : true
			};
			var material = new google.charts.Bar(document
					.getElementById('chart_div1'));
			material.draw(data, options);
		});
		
		drawChart2();
	}
	
	function drawChart2() {
		/*
		 * var data = google.visualization.arrayToDataTable([ [ 'Dept',
		 * 'Completed', 'Pending' ], [ 'CC Dept', 1000, 400 ], [ 'Loan Dept',
		 * 660, 1120 ], [ 'Savings Dept', 1030, 540 ] ]); ]
		 */
		
		$http.get("/data/requeststatusbydeptchart/"+$cookieStore.get('client').id).success(function(data) {
	    	var arrOutput = [[ 'Dept Name', 'Completed', 'Pending']];
			var objs= data.controllerResponse;
			var comp=0,pend=0;
			var prevName="";
			var keys=Object.keys(objs);
			keys.sort();
			for(var i=0;i<keys.length;i++){
				
				var DeptAndStat=keys[i].split("|||");
				var currName=DeptAndStat[0];
				var currStatus=DeptAndStat[1];
				if(prevName!=currName){
					if(prevName!=""){
						var arr=[];
						arr.push(prevName);arr.push(comp);arr.push(pend);
						arrOutput.push(arr);
					}
					prevName=currName;
					comp=0;pend=0;
				}
				
				if(currStatus==="PENDING")
					pend=Number(objs[keys[i]]);
				else 
					comp+=Number(objs[keys[i]]);
			}
			
			var arr=[];
			arr.push(prevName);arr.push(comp);arr.push(pend);;
			arrOutput.push(arr);
			var data = google.visualization.arrayToDataTable(arrOutput);
			
			var options = {
				title : 'Department Performance',
				curveType : 'function',
				legend : {
					position : 'bottom'
				}
			};

			var chart = new google.visualization.LineChart(document
					.getElementById('chart_div2'));

			chart.draw(data, options);
			
		});
		
		drawChart3();
	}
	
	 function drawChart3() {
		 $http.get("/data/requestdeptchart/"+$cookieStore.get('client').id).success(function(data) {
	        	
		    	var arrOutputTemp = new google.visualization.DataTable();
		    	arrOutputTemp.addColumn('string', 'From');
		    	arrOutputTemp.addColumn('string', 'To');
		    	arrOutputTemp.addColumn('number', 'Weight');
		    	/*arrOutputTemp.addRows([
		          [ 'Loan Dept', 'Request 1', 5 ],
		          [ 'Loan Dept', 'Request 2', 5 ],
		          [ 'Loan Dept', 'Request 3', 5 ],
		          [ 'Loan Dept', 'Request 4', 5 ],
		          [ 'Savings Dept', 'Request 4', 7 ],
		          [ 'Savings Dept', 'Request 2', 7 ],
		          [ 'CC Dept', 'Request 1', 6 ],
		          [ 'CC Dept', 'Request 2', 6 ],
		          [ 'CC Dept', 'Request 3', 6 ],
		        ]);*/
				var objs= data.controllerResponse.responseObject;
				var arrOutput=[];
				for(var i=0;i<objs.length;i++){
					var obj=objs[i];
					for(var key in obj){
						var arr=[];
						arr.push(key);
						arr.push(""+obj[key]);
						arr.push(Number(5));
						arrOutput.push(arr);
					}
				}
				arrOutputTemp.addRows(arrOutput);
				 var options = {
				          width: 600,
				        };

		        var chart = new google.visualization.Sankey(document.getElementById('chart_div3'));
		        chart.draw(arrOutputTemp, options);
				
			});
	        drawChart4();
	    }
	 
	 	function drawChart4() {
	        /*
			 * var data = new google.visualization.arrayToDataTable([ ['Day',
			 * 'Requests'], ["5/10", 20], ["5/11", 15], ["5/12", 12], ["5/13",
			 * 10], ['5/14', 3] ]);
			 */
	        $http.get("/data/deptrequestbydaychart/"+$cookieStore.get('client').id).success(function(data) {
		    	var arrOutput = [[ 'Day', 'Requests']];
				var objs= data.controllerResponse;
				var keys=Object.keys(objs);
				keys.sort();
				for(var i=0;i<keys.length;i++){
					var arr=[];
					arr.push(keys[i]);
					arr.push(Number(objs[keys[i]]));
					arrOutput.push(arr);
				}
				var data = google.visualization.arrayToDataTable(arrOutput);
				
		        var options = {
		          width: 600,
		          legend: { position: 'none' },
		          chart: { },
		          bars: 'horizontal', // Required for Material Bar Charts.
		          axes: {
		            x: {
		              0: { side: 'top', label: 'Requests'} // Top x-axis.
		            }
		          },
		          bar: { groupWidth: "90%" }
		        };

		        var chart = new google.charts.Bar(document.getElementById('chart_div4'));
		        chart.draw(data, options);
				
			});
	      };

});

cmpe.controller('superadminDashCtrl', function($scope, $rootScope, $http) {

});
