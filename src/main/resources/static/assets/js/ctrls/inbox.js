cmpe.controller('inboxCtrl', function($scope, $rootScope, $http){
	$scope.requests=[];
	$scope.getRequests = function(){
		var reqs=[{
			name:"Req1",
			id:1,
			desc:"SSN update",
			username:"Nikhil"
		},{
			name:"Req2",
			id:2,
			desc:"Transript update",
			username:"Chris"
		},{
			name:"Req1",
			id:1,
			desc:"SSN update",
			username:"Nikhil"
		},{
			name:"Req2",
			id:2,
			desc:"Transript update",
			username:"Chris"
		},{
			name:"Req1",
			id:1,
			desc:"SSN update",
			username:"Nikhil"
		},{
			name:"Req2",
			id:2,
			desc:"Transript update",
			username:"Chris"
		},{
			name:"Req1",
			id:1,
			desc:"SSN update",
			username:"Nikhil"
		},{
			name:"Req2",
			id:2,
			desc:"Transript update",
			username:"Chris"
		},{
			name:"Req1",
			id:1,
			desc:"SSN update",
			username:"Nikhil"
		},{
			name:"Req2",
			id:2,
			desc:"Transript update",
			username:"Chris"
		}];
		$scope.requests=reqs;
	}
	$scope.getRequests();
	$scope.req=null;
	$scope.getReqDetails = function(id){
		$scope.getWorkflowByReqId(id);
		$scope.req=$scope.requests[id];
	}
	
	$scope.getWorkflowByReqId = function(id){
		var temp={
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

			};
		$scope.chartViewModel = new flowchart.ChartViewModel(temp);

		//Set the Color of a node
		$scope.dragSelectionRect = {
	            x: 0,
	            y: 0,
	            width: 800,
	            height: 800,
	        };
		 $scope.chartViewModel.applySelectionRect($scope.dragSelectionRect);
	}
	
	
});
