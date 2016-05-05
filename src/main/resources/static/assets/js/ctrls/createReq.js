cmpe.controller(
				'createReqCtrl',
				[
						'$scope',
						'$http',
						'prompt',
						'$cookieStore',
						'$window',
						function AppCtrl($scope, $http, prompt, $cookieStore, $window) {
							$scope.workflows=[];
							$scope.getWorkflows = function() {
								$http.get("/data/workflows").success(function(data) {
									var objs = data.controllerResponse.responseObject;
									for (var i = 0; i < objs.length; i++) {
										$scope.workflows.push(objs[i]);
									}
									console.log($scope.workflows);
								});
							};
							$scope.getWorkflows();
		
							$scope.load = function() {
								console.log("selected workflow");
								console.log($scope.selectedworkflow);
								console.log("selected workflow 2");
								console.log($scope.workflows[$scope.selectedworkflow])
								console.log("selected workflow 3");
								console.log($scope.workflows[$scope.selectedworkflow].workflowJson)
								$scope.chartViewModel = new flowchart.ChartViewModel(
										JSON.parse($scope.workflows[$scope.selectedworkflow].workflowJson));
							}
							
							$scope.createReq = function(){
								console.log('creating a request here');
								var paramMap = {
										   "workflowid" : "40", 
										   "clientid" : "2" ,
										   "description" : "My New Description"
										}
								$http.post("user/request/create", paramMap).success(function(data) {
									/*var objs = data.controllerResponse.responseObject;
									for (var i = 0; i < objs.length; i++) {
										$scope.workflows.push(objs[i]);
									}
									console.log($scope.workflows);*/
									console.log("Request data:"+data.controllerResponse.responseObject.id);
									console.log(data);
									$window.location.href = '/#/request/'+data.controllerResponse.responseObject.id;
								});
								
							};
						} ]);


cmpe.controller(
		'requestDetailCtrl',
		[
				'$scope',
				'$http',
				'prompt',
				'$cookieStore','$stateParams',
				function AppCtrl($scope, $http, prompt, $cookieStore, $stateParams) {
					var requestId = $stateParams.requestID;

					$scope.request=[];
					$scope.getRequest = function() {
						$http.get("/data/request/"+ requestId).success(function(data) {
							if (data.controllerResponse.responseObject) {
								var objs = data.controllerResponse.responseObject;
								$scope.request = objs;
								console.log('here object');
								console.log($scope.request);
								$scope.chartViewModel = new flowchart.ChartViewModel(
										JSON.parse($scope.request.requestJson));
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
					
					
				} ]);
