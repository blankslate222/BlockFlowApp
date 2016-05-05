cmpe
		.controller(
				'createReqCtrl',
				[
						'$scope',
						'$http',
						'prompt',
						'$cookieStore',
						function AppCtrl($scope, $http, prompt, $cookieStore) {

							var workflows = [];							
							$scope.getWorkflows = function(){
								var temp=[{
									"workflowname" : "workflow1",
									"nodes" : [
									
									  { "name" : "Dept 1", "id" : 0, "x" : 36, "y" :
									  28, "inputConnectors" : [ { "name" :
									  "DeptUser1" } ], "outputConnectors" : [ {
									  "name" : "DeptUser1" } ] }, { "name" : "Dept2", "id" : 1, "x" : 335, "y" : 174,
									  "inputConnectors" : [ { "name" : "DeptUser2" } ],
									  "outputConnectors" : [ { "name" : "DeptUser2" } ] }, {
									  "name" : "Dept 3", "id" : 10, "x" : 635, "y" :
									  30, "inputConnectors" : [ { "name" :
									  "DeptUser3" } ], "outputConnectors" : [ {
									  "name" : "DeptUser3" } ] }, { "name" : "Dept4", "id" : 11, "x" : 923, "y" : 164,
									  "inputConnectors" : [ { "name" : "DeptUser4" } ],
									  "outputConnectors" : [ { "name" : "DeptUser4" } ] }
									 
									],
									"connections" : [
									
									  { "source" : { "nodeID" : 0, "connectorIndex" :
									  0 }, "dest" : { "nodeID" : 1,
									  "connectorIndex" : 0 } }, { "source" : {
									  "nodeID" : 1, "connectorIndex" : 0 }, "dest" : {
									  "nodeID" : 10, "connectorIndex" : 0 } }, {
									  "source" : { "nodeID" : 10, "connectorIndex" :
									  0 }, "dest" : { "nodeID" : 11,
									  "connectorIndex" : 0 } }
									 
									]

								},{
									"workflowname" : "workflow2",
										"nodes" : [
										
										  { "name" : "Grp 1", "id" : 0, "x" : 36, "y" :
										  28, "inputConnectors" : [ { "name" :
										  "DeptUser1" } ], "outputConnectors" : [ {
										  "name" : "DeptUser1" } ] }, { "name" : "Grp 2", "id" : 1, "x" : 335, "y" : 174,
										  "inputConnectors" : [ { "name" : "DeptUser2" } ],
										  "outputConnectors" : [ { "name" : "DeptUser2" } ] }, {
										  "name" : "Grp 3", "id" : 10, "x" : 635, "y" :
										  30, "inputConnectors" : [ { "name" :
										  "DeptUser3" } ], "outputConnectors" : [ {
										  "name" : "DeptUser3" } ] }, { "name" : "Dept4", "id" : 11, "x" : 923, "y" : 164,
										  "inputConnectors" : [ { "name" : "DeptUser4" } ],
										  "outputConnectors" : [ { "name" : "DeptUser4" } ] }
										 
										],
										"connections" : [
										
										  { "source" : { "nodeID" : 0, "connectorIndex" :
										  0 }, "dest" : { "nodeID" : 1,
										  "connectorIndex" : 0 } }, { "source" : {
										  "nodeID" : 1, "connectorIndex" : 0 }, "dest" : {
										  "nodeID" : 10, "connectorIndex" : 0 } }, {
										  "source" : { "nodeID" : 10, "connectorIndex" :
										  0 }, "dest" : { "nodeID" : 11,
										  "connectorIndex" : 0 } }
										 
										]

									}];
								
								$scope.workflows=temp;
							}
							$scope.getWorkflows();
							
							$scope.load = function() {
								console.log($scope.selectedworkflow);
								$scope.chartViewModel = new flowchart.ChartViewModel(
										$scope.workflows[$scope.selectedworkflow]);
							}
							
							
						} ]);
