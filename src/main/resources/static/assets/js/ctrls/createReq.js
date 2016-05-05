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
									console.log("Request data:");
									console.log(data);
									$window.location.href = '/#/requestdetail';
								});
								
							};
						} ]);


cmpe.controller(
		'requestDetailCtrl',
		[
				'$scope',
				'$http',
				'prompt',
				'$cookieStore',
				function AppCtrl($scope, $http, prompt, $cookieStore) {
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
						$scope.chartViewModel = new flowchart.ChartViewModel(
								JSON.parse($scope.workflows[$scope.selectedworkflow].workflowJson));
					}
					
					
				} ]);
