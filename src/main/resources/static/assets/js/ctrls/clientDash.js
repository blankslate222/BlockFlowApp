cmpe.controller('clientDashCtrl', function($scope, $rootScope, $http) {

	google.charts.load("current", {
		packages : [ "corechart" ]
	});
	google.charts.setOnLoadCallback(drawChart);
	function drawChart() {
		var data = google.visualization.arrayToDataTable([
				[ 'Status', 'Count' ], [ 'Accepted', 10 ], [ 'Rejected', 22 ],
				[ 'Pending', 12 ] ]);

		var options = {
			title : 'Status Pie',
			is3D : true,
		};

		var chart = new google.visualization.PieChart(document
				.getElementById('piechart_3d'));
		chart.draw(data, options);
	}
});

cmpe.controller('adminDashCtrl', function($scope, $rootScope, $http) {

	google.charts.load('current', {
		packages : [ 'corechart', 'bar' ]
	});
	google.charts.setOnLoadCallback(drawDualX);

	function drawDualX() {
		var data = google.visualization.arrayToDataTable([
				[ 'Genre', 'Fantasy & Sci Fi', 'Romance', 'Mystery/Crime',
						'General', 'Western', 'Literature', {
							role : 'annotation'
						} ], [ '2010', 10, 24, 20, 32, 18, 5, '' ],
				[ '2020', 16, 22, 23, 30, 16, 9, '' ],
				[ '2030', 28, 19, 29, 30, 12, 13, '' ] ]);

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
				.getElementById('chart_div'));
		material.draw(data, options);
	}

	google.charts.load("current2", {
		packages : [ "corechart" ]
	});
	
	google.charts.setOnLoadCallback(drawChart2);
	function drawChart2() {
		var data = google.visualization.arrayToDataTable([
				[ 'Quarks', 'Leptons', 'Gauge Bosons', 'Scalar Bosons' ],
				[ 2 / 3, -1, 0, 0 ], [ 2 / 3, -1, 0, null ],
				[ 2 / 3, -1, 0, null ], [ -1 / 3, 0, 1, null ],
				[ -1 / 3, 0, -1, null ], [ -1 / 3, 0, null, null ],
				[ -1 / 3, 0, null, null ] ]);

		var options = {
			title : 'Charges of subatomic particles',
			legend : {
				position : 'top',
				maxLines : 2
			},
			colors : [ '#5C3292', '#1A8763', '#871B47', '#999999' ],
			interpolateNulls : false,
		};
		
		var chart = new google.visualization.Histogram(document
				.getElementById('chart_div2'));
		chart.draw(data, options);
	}

});

cmpe.controller('superadminDashCtrl', function($scope, $rootScope, $http) {

});
