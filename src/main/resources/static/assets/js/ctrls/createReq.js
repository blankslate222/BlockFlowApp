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
											$scope.userReqs.push(objs[i].request);
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
					$scope.nodes=[];
					$scope.getRequest = function() {
						$http.get("/data/request/"+ requestId).success(function(data) {
							console.log(data);
							if (data.controllerResponse.responseObject) {
								var objs = data.controllerResponse.responseObject;
								debugger;
								$scope.request = objs.request;
								console.log("Request Details");
								console.log($scope.nodes);
								console.log($scope.request.id);
								
								$scope.request.created = getFormattedDate($scope.request.created);
								$scope.request.modified = getFormattedDate($scope.request.modified);
								
								
								var obj=JSON.parse($scope.request.requestJson);
								console.log("JSON:"+$scope.request.requestJson);
								for(var level=0;level<objs.nodeList.length;level++){
									if(objs.nodeList[level].status == "PENDING_ACTION"){
										$scope.request.assignedDept = objs.nodeList[level].name;
									}
									console.log("obj.nodes[level].status:"+obj.nodes[level].status);
									console.log("obj.nodes[level].name:"+obj.nodes[level].name);
									obj.nodes[level].name=obj.nodes[level].name + " ("+objs.nodeList[level].status+")";
									objs.nodeList[level].created = getFormattedDate(objs.nodeList[level].created)
									objs.nodeList[level].modified = getFormattedDate(objs.nodeList[level].modified)
								}
								$scope.nodes = objs.nodeList;
								$scope.chartViewModel = new flowchart.ChartViewModel(
										obj);
							}
						});
					};
					$scope.getRequest();
					
				      google.charts.setOnLoadCallback(drawChart);

				      function drawChart() {

					$http.get("/data/requestcompletepercentchart/"+ requestId).success(function(data) {
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
						          backgroundColor: '#F2F2F2',
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
				      
				      //drawChart();
				    function getFormattedDate(timeInMillis){
						var date = new Date(timeInMillis);
						var hours = date.getHours();
						var minutes = "0" + date.getMinutes();
						var seconds = "0" + date.getSeconds();
						var day = "0" + date.getDate();
						var month = "0" + date.getMonth();
						var year = date.getFullYear();
						var formattedTime = month.substr(-2) + '/' + day.substr(-2) + '/' + year + ' ' + hours + ':' + minutes.substr(-2) + ':' + seconds.substr(-2);
				    	
				    	return formattedTime;
				    }
				    
					$scope.load = function() {
						console.log("Request JSON");
						console.log($scope.request.requestJson);
						$scope.chartViewModel = new flowchart.ChartViewModel(
								JSON.parse($scope.request.requestJson));
						  google.charts.load('current', {'packages':['corechart']});
						  google.charts.setOnLoadCallback(drawChart);
					}
					
					
				} ]);

