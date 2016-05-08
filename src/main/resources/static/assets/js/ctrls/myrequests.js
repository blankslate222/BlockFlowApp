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
											var objs = data.controllerResponse.responseObject.requestlist;
											for (var i = 0; i < objs.length; i++) {
												console.log(objs[i])
												$scope.requests.push(objs[i]);
											}
											console.log($scope.requests);
										});
					};
					$scope.getRequests();

					$scope.req = null;
					$scope.getReqDetails = function(id) {
						$scope.req = $scope.requests[id];
						$scope.getWorkflowByReqId(id);
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