cmpe.controller(
				'createReqCtrl',
				[
						'$scope',
						'$http',
						'prompt',
						'$cookieStore',
						'$window',
						'spinnerService',	
						function AppCtrl($scope, $http, prompt, $cookieStore, $window, spinnerService) {
							$scope.workflowsList=[];
							$scope.clients=[];
							$scope.loading=false;
							
							$scope.getClients = function() {
								$scope.loading=true;
								$http.get("/data/clients").success(function(data) {
									
									var objs = data.controllerResponse.responseObject;
									for (var i = 0; i < objs.length; i++) {
										$scope.clients.push(objs[i]);
									}
									console.log($scope.clients);
								})
								.finally(function () {
									$scope.loading=false;
							    });
							};
							$scope.getClients();
							
							$scope.getWorkflows = function() {
								$scope.loading=true;
								$http.get("/data/workflowrequests").success(function(data) {
									console.log(data);
									var objs = data.controllerResponse.responseObject;
									for (var i = 0; i < objs.length; i++) {
										$scope.workflowsList.push(objs[i]);
									}
								})
								.finally(function () {
									$scope.loading=false;
							    });
							};
							$scope.getWorkflows();
							
							$scope.getWorkflowByClient = function() {
								$scope.workflows=[];
								console.log($scope.workflowsList);
								for (var i = 0; i < $scope.workflowsList.length; i++) {
									if($scope.workflowsList[i].client== $scope.clients[$scope.selectedClient].id )
										$scope.workflows.push($scope.workflowsList[i]);
									if($scope.workflowsList[i].client.id && $scope.workflowsList[i].client.id== $scope.clients[$scope.selectedClient].id )
										$scope.workflows.push($scope.workflowsList[i]);
								}
							}
							
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
								if(!$scope.selectedworkflow){
									alert("Select a Request");
									return ;
								}
								console.log('creating a request here');
								var clientid = $scope.workflows[$scope.selectedworkflow].client.id;
								var clientid2 = $scope.workflows[$scope.selectedworkflow].client;
								var clientval = undefined;
								
								console.log(clientid);
								console.log(clientid2);
								
								if (clientid != undefined) {
									clientval = clientid;
								} else if (clientid2 != undefined) {
									clientval = clientid2;
								}
								console.log(clientval);
								debugger;
								var paramMap = {
										   "workflowid" : $scope.workflows[$scope.selectedworkflow].id, 
										   "clientid" :  clientval,
										   "description" : $scope.desc
										}
								
								console.log("params map \n");
								console.log(paramMap);
								$http.post("user/request/create",paramMap).success(function(data) {

									/*
									 * var objs =
									 * data.controllerResponse.responseObject;
									 * for (var i = 0; i < objs.length; i++) {
									 * $scope.workflows.push(objs[i]); }
									 * console.log($scope.workflows);
									 */
								
									console.log("Request data:"+data.controllerResponse.responseObject.id);
									console.log(data);
									$window.location.href = '/#/request/'+data.controllerResponse.responseObject.id;
								});
								
							};
							
							$scope.getReqs = function(){
								console.log('>>> a request here');
								
								$http.get("/data/userinbox").success(function(data) {
										$scope.userReqs=[];
										var objs = data.controllerResponse.responseObject.requestlist;
										for (var i = 0; i < objs.length; i++) {
											$scope.userReqs.push(objs[i]);
										}
										console.log($scope.userReqs);
									});
							
							};
							$scope.getReqs();
							
							
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
								console.log($scope.request);
								var obj=JSON.parse($scope.request[0].request.requestJson);
								for(var i=0;i<$scope.request.length;i++){
									obj.nodes[$scope.request[i].level-1].name=obj.nodes[$scope.request[i].level-1].name + " ("+$scope.request[i].status+")";
								}
								$scope.chartViewModel = new flowchart.ChartViewModel(
										obj);
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
