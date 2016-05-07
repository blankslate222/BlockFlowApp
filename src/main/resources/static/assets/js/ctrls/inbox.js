cmpe.controller('inboxCtrl', function($scope, $rootScope, $http, $window) {
	$scope.requests = [];
	var tempArr = [];
	$scope.getRequests = function() {
		$http.get("/data/userinbox").success(function(data) {
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
		$scope.getWorkflowByReqId(id);
		$scope.req = $scope.requests[id];
	}

	$scope.getWorkflowByReqId = function(id) {
		var temp = {
			"workflowname" : "workflow2",
			"nodes" : [

			{
				"name" : "Grp 1",
				"id" : 0,
				"x" : 36,
				"y" : 28,
				"inputConnectors" : [ {
					"name" : "DeptUser1"
				} ],
				"outputConnectors" : [ {
					"name" : "DeptUser1"
				} ]
			}, {
				"name" : "Grp 2",
				"id" : 1,
				"x" : 335,
				"y" : 174,
				"inputConnectors" : [ {
					"name" : "DeptUser2"
				} ],
				"outputConnectors" : [ {
					"name" : "DeptUser2"
				} ]
			}, {
				"name" : "Grp 3",
				"id" : 10,
				"x" : 635,
				"y" : 30,
				"inputConnectors" : [ {
					"name" : "DeptUser3"
				} ],
				"outputConnectors" : [ {
					"name" : "DeptUser3"
				} ]
			}, {
				"name" : "Dept4",
				"id" : 11,
				"x" : 923,
				"y" : 164,
				"inputConnectors" : [ {
					"name" : "DeptUser4"
				} ],
				"outputConnectors" : [ {
					"name" : "DeptUser4"
				} ]
			}

			],
			"connections" : [

			{
				"source" : {
					"nodeID" : 0,
					"connectorIndex" : 0
				},
				"dest" : {
					"nodeID" : 1,
					"connectorIndex" : 0
				}
			}, {
				"source" : {
					"nodeID" : 1,
					"connectorIndex" : 0
				},
				"dest" : {
					"nodeID" : 10,
					"connectorIndex" : 0
				}
			}, {
				"source" : {
					"nodeID" : 10,
					"connectorIndex" : 0
				},
				"dest" : {
					"nodeID" : 11,
					"connectorIndex" : 0
				}
			}

			]

		};
		$scope.chartViewModel = new flowchart.ChartViewModel(temp);

		// Set the Color of a node
		$scope.dragSelectionRect = {
			x : 0,
			y : 0,
			width : 800,
			height : 800,
		};

		$scope.chartViewModel.applySelectionRect($scope.dragSelectionRect);
	}

	$scope.doActionOnRequest = function(req_id, nodes, status) {

		console.log('going to change request status');
		console.log(req_id);
		console.log(nodes);
		console.log(status);

		var req_node_id = 0;

		for (var i = 0; i < nodes.length; i++) {
			if (nodes[i].status === "PENDING_ACTION") {
				console.log("Got status PENDING_ACTION on node: "
								+ nodes[i].id);
				req_node_id = nodes[i].id;
				break;
			}
		}

		var paramMap = {
			"requestid" : req_id,
			"requestnodeid" : req_node_id,
			"status" : status
		};

		console.log(paramMap);

		$http.post("staff/request/update", paramMap).success(function(data) {
			console.log(data);
			$window.location.href = '/#/inbox';
		});

	}

});
