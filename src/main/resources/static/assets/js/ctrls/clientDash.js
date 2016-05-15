cmpe.controller('clientDashCtrl', function($scope, $rootScope, $http) {

	google.charts.setOnLoadCallback(drawChart);
	
    function drawChart() {
    	var arrOutputTemp=
    	[
         ['Status', 'Percentage'],
         ['Completed',     2],
         ['Pending',      3]
       ]
    	
      var data = google.visualization.arrayToDataTable(arrOutputTemp);

      var options = {
        title: 'Request Status',
        pieHole: 0.4,
      };

      var chart = new google.visualization.PieChart(document.getElementById('chart_div1'));
      chart.draw(data, options);
      drawChart2();
    }

    function toMilliseconds(minutes) {
      return minutes * 60 * 1000;
    }

    function drawChart2() {

      var otherData = new google.visualization.DataTable();
      otherData.addColumn('string', 'Task ID');
      otherData.addColumn('string', 'Task Name');
      otherData.addColumn('string', 'Resource');
      otherData.addColumn('date', 'Start');
      otherData.addColumn('date', 'End');
      otherData.addColumn('number', 'Duration');
      otherData.addColumn('number', 'Percent Complete');
      otherData.addColumn('string', 'Dependencies');

      otherData.addRows([

                         
                         ['CCDept', 'Credit Card Dept', '1', null, null, toMilliseconds(10), 0, null],
                         ['SDept', 'Savings Dept', '1', null, null, toMilliseconds(10), 0, 'CCDept'],
                         ['LDept', 'Loan Dept', '2', null, null, toMilliseconds(10), 0, 'SDept'],
                         ['CDept', 'Checking Dept', '2', null, null, toMilliseconds(10), 0, 'LDept'],
                         ['FDept', ' Dept', '2', null, null, toMilliseconds(10), 0, 'CDept'],


      ]);

      var options = {
        height: 300,
        gantt: {
        }
      };

      var chart = new google.visualization.Gantt(document.getElementById('chart_div2'));

      chart.draw(otherData, options);
      drawChart3();
    }
    
    

    function drawChart3() {
      var data = google.visualization.arrayToDataTable([
        ['Status', 'Percentage'],
        ['Completed',     1],
        ['Pending',      2]
      ]);

      var options = {
        title: 'Request Status',
        pieHole: 0.4,
      };

      var chart = new google.visualization.PieChart(document.getElementById('chart_div3'));
      chart.draw(data, options);
      drawChart4();
    }

    function toMilliseconds(minutes) {
      return minutes * 60 * 1000;
    }

    function drawChart4() {

      var otherData = new google.visualization.DataTable();
      otherData.addColumn('string', 'Task ID');
      otherData.addColumn('string', 'Task Name');
      otherData.addColumn('string', 'Resource');
      otherData.addColumn('date', 'Start');
      otherData.addColumn('date', 'End');
      otherData.addColumn('number', 'Duration');
      otherData.addColumn('number', 'Percent Complete');
      otherData.addColumn('string', 'Dependencies');

      otherData.addRows([
      
        
        ['CCDept', 'Credit Card Dept', '1', null, null, toMilliseconds(10), 0, null],
        ['SDept', 'Savings Dept', '2', null, null, toMilliseconds(10), 0, 'CCDept'],
        ['LDept', 'Loan Dept', '2', null, null, toMilliseconds(10), 0, 'SDept'],


      ]);

      var options = {
        height: 300,
        gantt: {
          defaultStartDateMillis: new Date(2015, 3, 28)
        }
      };

      var chart = new google.visualization.Gantt(document.getElementById('chart_div4'));

      chart.draw(otherData, options);
    }
    
});

cmpe.controller('adminDashCtrl', function($scope, $rootScope, $http, $cookieStore) {

	google.charts.setOnLoadCallback(drawDualX);

	function drawDualX() {
		/*  var arrOutputTemp=[ [ 'Status', 'Accepted', 'Rejected', 'Pending', {
		  role : 'annotation' } ], [ 'CC Dept', 10, 24, 20, '' ], [ 'Loan Dept', 16, 22, 23, '' ], [ 'Savings Dept', 28, 19, 29, '' ] ];
		 console.log(arrOutputTemp);*/
		
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
		var data = google.visualization.arrayToDataTable([
				[ 'Dept', 'Completed', 'Pending' ], 
				[ 'CC Dept', 1000, 400 ],
				[ 'Loan Dept', 660, 1120 ],
				[ 'Savings Dept', 1030, 540 ] ]);

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
		drawChart3();
	}
	
	 function drawChart3() {
	        var data = new google.visualization.DataTable();
	        data.addColumn('string', 'From');
	        data.addColumn('string', 'To');
	        data.addColumn('number', 'Weight');
	        data.addRows([
	          [ 'Loan Dept', 'Request 1', 5 ],
	          [ 'Loan Dept', 'Request 2', 5 ],
	          [ 'Loan Dept', 'Request 3', 5 ],
	          [ 'Loan Dept', 'Request 4', 5 ],
	          [ 'Savings Dept', 'Request 4', 7 ],
	          [ 'Savings Dept', 'Request 2', 7 ],
	          [ 'CC Dept', 'Request 1', 6 ],
	          [ 'CC Dept', 'Request 2', 6 ],
	          [ 'CC Dept', 'Request 3', 6 ],
	        ]);

	        // Sets chart options.
	        var options = {
	          width: 600,
	        };

	        // Instantiates and draws our chart, passing in some options.
	        var chart = new google.visualization.Sankey(document.getElementById('chart_div3'));
	        chart.draw(data, options);
	        drawChart4();
	    }
	 
	 	function drawChart4() {
	        var data = new google.visualization.arrayToDataTable([
	          ['Day', 'Requests'],
	          ["5/10", 20],
	          ["5/11", 15],
	          ["5/12", 12],
	          ["5/13", 10],
	          ['5/14', 3]
	        ]);

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
	      };

});

cmpe.controller('superadminDashCtrl', function($scope, $rootScope, $http) {

});
