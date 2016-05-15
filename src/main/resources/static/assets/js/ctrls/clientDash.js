cmpe.controller('clientDashCtrl', function($scope, $rootScope, $http) {

	google.charts.load("current", {
		packages : [ "corechart",'gantt' ]
	});
	google.charts.setOnLoadCallback(drawChart);
	
    function drawChart() {
      var data = google.visualization.arrayToDataTable([
        ['Status', 'Percentage'],
        ['Completed',     2],
        ['Pending',      3]
      ]);

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

                         
                         ['CCDept', 'Credit Card Dept', '', null, null, toMilliseconds(10), 100, null],
                         ['SDept', 'Savings Dept', '', null, null, toMilliseconds(10), 100, 'CCDept'],
                         ['LDept', 'Loan Dept', '', null, null, toMilliseconds(10), 0, 'SDept'],
                         ['CDept', 'Checking Dept', '', null, null, toMilliseconds(10), 0, 'LDept'],
                         ['FDept', ' Dept', '', null, null, toMilliseconds(10), 0, 'CDept'],


      ]);

      var options = {
        height: 400,
        gantt: {
          defaultStartDateMillis: new Date(2015, 3, 28)
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
      
        
        ['CCDept', 'Credit Card Dept', '', null, null, toMilliseconds(10), 100, null],
        ['SDept', 'Savings Dept', '', null, null, toMilliseconds(10), 0, 'CCDept'],
        ['LDept', 'Loan Dept', '', null, null, toMilliseconds(10), 0, 'SDept'],


      ]);

      var options = {
        height: 400,
        gantt: {
          defaultStartDateMillis: new Date(2015, 3, 28)
        }
      };

      var chart = new google.visualization.Gantt(document.getElementById('chart_div4'));

      chart.draw(otherData, options);
    }
    
    
    
});

cmpe.controller('adminDashCtrl', function($scope, $rootScope, $http) {

	google.charts.load('current', {
		packages : [ 'corechart', 'bar', 'sankey' ]
	});
	google.charts.setOnLoadCallback(drawDualX);

	function drawDualX() {
		var data = google.visualization.arrayToDataTable([
				[ 'Status', 'Accepted', 'Rejected', 'Pending', {
					role : 'annotation'
				} ], [ 'CC Dept', 10, 24, 20, '' ],
				[ 'Loan Dept', 16, 22, 23, '' ],
				[ 'Savings Dept', 28, 19, 29, '' ] ]);

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
