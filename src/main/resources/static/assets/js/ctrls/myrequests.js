cmpe
		.controller(
				'myreqCtrl',
				function($scope, $rootScope, $http, $window, $state, $stateParams) {
					$scope.requests = [];
					var tempArr = [];
					$scope.getRequests = function() {
						$http
								.get("/data/userinbox")
								.success(
										function(data) {
											console.log(data);
											var objs = data.controllerResponse.responseObject;
											for (var i = 0; i < objs.length; i++) {
												console.log(objs[i])
												$scope.requests.push(objs[i].request);
											}
											console.log($scope.requests);
										});
					};
					$scope.getRequests();

					$scope.req = null;
					$scope.getReqDetails = function(id) {
						$scope.req = $scope.requests[id];
						$scope.getWorkflowByReqId(id);
						$scope.plotChartById(id);
					}

					$scope.getWorkflowByReqId = function(id) {
						console.log($scope.req);
						if ($scope.req.requestJson
								&& $scope.req.requestJson != "") {
							$scope.chartViewModel = new flowchart.ChartViewModel(
									JSON.parse($scope.req.requestJson));

							// Set the Color of a node
							$scope.dragSelectionRect = {
								x : 0,
								y : 0,
								width : 1000,
								height : 1000,
							};

							$scope.chartViewModel
									.applySelectionRect($scope.dragSelectionRect);
						}
					}

					$scope.plotChartById = function(id){
						$http.get("/data/requestcompletepercentchart/"+ $scope.req.id).success(function(data) {
						  	var arrOutput = [['Progress', 'No. of Nodes']];
						  	var arrOutputTemp = [['Progress', 'No. of Nodes'],['PENDING',2],['PENDING_ACTION',1]];
								var objs;
								console.log(data);
								objs = data.controllerResponse;
								console.log(objs);
								for (var key in objs){
									var arr = [key, Number(objs[key])];								
									arrOutput.push(arr);
								}
								var dataNew = google.visualization.arrayToDataTable(arrOutput);
						      var options = {
								          title: 'Request Progress',
								          pieHole: 0.4,
								          pieSliceTextStyle: {
								              color: 'black',
								            },
								          slices: {
								              0: { color: 'red' },
								              1: { color: 'green' }
								            }
								        };
						      var chart = new google.visualization.PieChart(document.getElementById('piechart'));
						      chart.draw(dataNew, options);
							});

					}
					var requestId = $stateParams.requestID;

					$scope.request = [];
					$scope.getRequest = function() {
						$http
								.get("/data/request/" + $scope.req)
								.success(
										function(data) {
											if (data.controllerResponse.responseObject) {
												var objs = data.controllerResponse.responseObject;
												$scope.request = objs;
												console.log('here object');
												console.log($scope.request);
												$scope.chartViewModel = new flowchart.ChartViewModel(
														JSON
																.parse($scope.request[0].request.requestJson));
											}
										});
					};
					$scope.getRequest();

					$scope.load = function() {
						console.log("Request JSON");
						console.log($scope.request.requestJson);
						$scope.chartViewModel = new flowchart.ChartViewModel(
								JSON.parse($scope.request.requestJson));
					}

				});