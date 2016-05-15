cmpe.controller('inboxCtrl', function($scope, $rootScope, $http, $window) {
	$scope.requests = [];
	var tempArr = [];
	$scope.getRequests = function() {
		$http.get("/data/staffinbox").success(function(data) {
			console.log(data);
			debugger;
			var objs = data.controllerResponse.responseObject;
			for (var i = 0; i < objs.length; i++) {
				console.log(objs[i])
				var req = {};
				req.request = objs[i].request;
				req.nodes = objs[i].nodes;
				$scope.requests.push(req);
			}
			console.log($scope.requests);
		});
	};
	$scope.getRequests();

	$scope.req = null;
	$scope.getReqDetails = function(id) {
		debugger;
		$scope.req = $scope.requests[id].request;
		$scope.req.nodes = $scope.requests[id].nodes;
		$scope.getWorkflowByReqId(id);
	}

	$scope.getWorkflowByReqId = function(id) {
		$scope.req.workflow=JSON.parse($scope.req.requestJson);
		console.log($scope.req);
		if($scope.req.requestJson && $scope.req.requestJson!=""){
			$scope.chartViewModel = new flowchart.ChartViewModel(JSON.parse($scope.req.requestJson));
	
			// Set the Color of a node
			$scope.dragSelectionRect = {
				x : 0,
				y : 0,
				width : 1000,
				height : 1000,
			};
	
			$scope.chartViewModel.applySelectionRect($scope.dragSelectionRect);
		}
	}

	$scope.doActionOnRequest = function(req_id, nodes, status) {
debugger;
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
