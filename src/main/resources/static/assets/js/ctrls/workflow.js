cmpe.factory('prompt', function() {
	return prompt;
})
cmpe.controller('workCtrl',[
						'$scope',
						'$http',
						'prompt',
						'$cookieStore',
						function AppCtrl($scope, $http, prompt, $cookieStore) {

							var deleteKeyCode = 46;

							var ctrlKeyCode = 17;

							var ctrlDown = false;

							var aKeyCode = 65;

							var escKeyCode = 27;

							var nextNodeID = 10;

							var chartDataModel = {

								"nodes" : [
								/*
								 * { "name" : "Dept 1", "id" : 0, "x" : 36, "y" :
								 * 28, "inputConnectors" : [ { "name" :
								 * "DeptUser1" } ], "outputConnectors" : [ {
								 * "name" : "DeptUser1" } ] }, { "name" : "Dept
								 * 2", "id" : 1, "x" : 335, "y" : 174,
								 * "inputConnectors" : [ { "name" : "DeptUser2" } ],
								 * "outputConnectors" : [ { "name" : "DeptUser2" } ] }, {
								 * "name" : "Dept 3", "id" : 10, "x" : 635, "y" :
								 * 30, "inputConnectors" : [ { "name" :
								 * "DeptUser3" } ], "outputConnectors" : [ {
								 * "name" : "DeptUser3" } ] }, { "name" : "Dept
								 * 4", "id" : 11, "x" : 923, "y" : 164,
								 * "inputConnectors" : [ { "name" : "DeptUser4" } ],
								 * "outputConnectors" : [ { "name" : "DeptUser4" } ] }
								 */
								],
								"connections" : [
								/*
								 * { "source" : { "nodeID" : 0, "connectorIndex" :
								 * 0 }, "dest" : { "nodeID" : 1,
								 * "connectorIndex" : 0 } }, { "source" : {
								 * "nodeID" : 1, "connectorIndex" : 0 }, "dest" : {
								 * "nodeID" : 10, "connectorIndex" : 0 } }, {
								 * "source" : { "nodeID" : 10, "connectorIndex" :
								 * 0 }, "dest" : { "nodeID" : 11,
								 * "connectorIndex" : 0 } }
								 */
								]

							};

							//
							// Event handler for key-down on the flowchart.
							//
							$scope.keyDown = function(evt) {

								if (evt.keyCode === ctrlKeyCode) {

									ctrlDown = true;
									evt.stopPropagation();
									evt.preventDefault();
								}
							};

							//
							// Event handler for key-up on the flowchart.
							//
							$scope.keyUp = function(evt) {

								if (evt.keyCode === deleteKeyCode) {
									//
									// Delete key.
									//
									$scope.chartViewModel.deleteSelected();
								}

								if (evt.keyCode == aKeyCode && ctrlDown) {
									// 
									// Ctrl + A
									//
									$scope.chartViewModel.selectAll();
								}

								if (evt.keyCode == escKeyCode) {
									// Escape.
									$scope.chartViewModel.deselectAll();
								}

								if (evt.keyCode === ctrlKeyCode) {
									ctrlDown = false;

									evt.stopPropagation();
									evt.preventDefault();
								}
							};
							$scope.depts=[];
							$scope.getDepts = function() {
								$http.get("/data/deptbyclient/"+$cookieStore.get('client').id).success(function(data) {
									var objs = data.controllerResponse.responseObject;
									for (var i = 0; i < objs.length; i++) {
										console.log(objs[i]);
										$scope.depts.push(objs[i]);
									}
									console.log($scope.depts);
								});
							}
							$scope.getDepts();

							
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
//
							// Add a new node to the chart.
							//
							$scope.addNewNode = function() {
								var selectedDepartment = JSON.parse($scope.selectedDept);
								console.log("Selected department id"+selectedDepartment.id);
								
								var newNodeDataModel = {
									name : selectedDepartment.name,
									id : nextNodeID++,
									deptId : selectedDepartment.id,
									x : 0,
									y : 0,
									inputConnectors : [ {
										name :  ""
									} ],
									outputConnectors : [ {
										name : ""
									} ]
								};

								$scope.chartViewModel.addNode(newNodeDataModel);
							};

							//
							// Add an input connector to selected nodes.
							//
							$scope.addNewInputConnector = function() {
								var connectorName = prompt(
										"Enter a connector name:",
										"New connector");
								if (!connectorName) {
									return;
								}

								var selectedNodes = $scope.chartViewModel
										.getSelectedNodes();
								for (var i = 0; i < selectedNodes.length; ++i) {
									var node = selectedNodes[i];
									node.addInputConnector({
										name : connectorName,
									});
								}
							};
							$scope.addNewOutputConnector = function() {
								var connectorName = prompt(
										"Enter a connector name:",
										"New connector");
								if (!connectorName) {
									return;
								}

								var selectedNodes = $scope.chartViewModel
										.getSelectedNodes();
								for (var i = 0; i < selectedNodes.length; ++i) {
									var node = selectedNodes[i];
									node.addOutputConnector({
										name : connectorName,
									});
								}
							};
							$scope.deleteSelected = function() {
								$scope.chartViewModel.deleteSelected();
							};
							function topologicalsort(edges) {
								return toposort(uniqueNodes(edges), edges)
							}
							function toposort(nodes, edges) {
								var cursor = nodes.length, sorted = new Array(
										cursor), visited = {}, i = cursor

								while (i--) {
									if (!visited[i])
										visit(nodes[i], i, [])
								}

								return sorted

								function visit(node, i, predecessors) {
									if (predecessors.indexOf(node) >= 0) {
										throw new Error('Cyclic dependency: '
												+ JSON.stringify(node))
									}

									if (visited[i])
										return;
									visited[i] = true

									// outgoing edges
									var outgoing = edges.filter(function(edge) {
										return edge[0] === node
									})
									if (i = outgoing.length) {
										var preds = predecessors.concat(node)
										do {
											var child = outgoing[--i][1]
											visit(child, nodes.indexOf(child),
													preds)
										} while (i)
									}

									sorted[--cursor] = node
								}
							}

							function uniqueNodes(arr) {
								var res = []
								for (var i = 0, len = arr.length; i < len; i++) {
									var edge = arr[i]
									if (res.indexOf(edge[0]) < 0)
										res.push(edge[0])
									if (res.indexOf(edge[1]) < 0)
										res.push(edge[1])
								}
								return res
							}

							function getByValue(arr, value) {

								for (var i = 0, iLen = arr.length; i < iLen; i++) {

									if (arr[i].id == value)
										return arr[i];
								}
							}

							$scope.createWorkflow = function() {
								var connlist = [];
								connlist = chartDataModel.connections;
								var graph = [];
								for (var i = 0; i < connlist.length; i++) {
									var edge = [];
									edge.push(connlist[i].dest.nodeID);
									edge.push(connlist[i].source.nodeID);
									graph.push(edge);
								}
								// var client = {
								// //name : $scope.client.name,
								// id : 6,
								// name : "Infy",
								// address : "India"
								// }
								// console.log(toposort(graph));
								// console.log(toposort(graph).reverse());
								var workflow = {};
								workflow.name = $scope.workflowname;
								var flowclient = {};
								flowclient.id = $cookieStore.get('client').id;
								flowclient.name = $cookieStore.get('client').name;
								flowclient.address = $cookieStore.get('client').address;
								flowclient.isActive = true;
								
								workflow.client = flowclient;
								workflow.nodes = [];
								workflow.workflowJson = JSON.stringify(chartDataModel);
								// debugger;
								// var uniquenodes = uniqueNodes(graph);
								var nodeseq = topologicalsort(graph).reverse();
								// [ 0, 1, 10, 11 ]
								var nodelist = chartDataModel.nodes;
								for (var i = 0; i < nodeseq.length; i++) {
									var uiNode = getByValue(nodelist,
											nodeseq[i])
									uiNode.level = i + 1;
									var workflownode = {};
									workflownode.name = uiNode.name;
									workflownode.level = uiNode.level;
									if (i == 0)
										workflownode.status = "PENDING_ACTION";
									else
										workflownode.status = "PENDING";
									workflownode.department_id = uiNode.deptId ;
									workflow.nodes.push(workflownode);
								}

								console.log(workflow);
								// send post request
								$http
										.post("/admin/workflow/create",
												workflow)
										.success(
												function(data) {
													console.log("after workflow create : \n");
													console.log(chartDataModel);
													console.log("after workflow create 1: \n");
													console.log(data.controllerResponse.responseObject.workflowJson);
													console.log("after workflow create 2: \n");
													console.log(JSON.stringify(data));
													$scope.workflows.push(data.controllerResponse.responseObject);
													$scope.chartViewModel = new flowchart.ChartViewModel(
															JSON.parse(data.controllerResponse.responseObject.workflowJson));
												}).error(function(err) {
											// $scope.status = data.error;
											console.log("Error: \n" + err);
										});
							}
							$scope.chartViewModel = new flowchart.ChartViewModel(
									chartDataModel);
						} ]);

cmpe.controller('workViewCtrl', [ '$scope', '$http', 'prompt', '$cookieStore',
		function($scope, $http, prompt, $cookieStore) {

		} ]);