cmpe.factory('prompt', function () {

	/* Uncomment the following to test that the prompt service is working as expected.
	return function () {
		return "Test!";
	}
	*/

	// Return the browsers prompt function.
	return prompt;
})
cmpe.controller('workCtrl', ['$scope', 'prompt', function AppCtrl ($scope, prompt) {

	//
	// Code for the delete key.
	//
	var deleteKeyCode = 46;

	//
	// Code for control key.
	//
	var ctrlKeyCode = 17;

	//
	// Set to true when the ctrl key is down.
	//
	var ctrlDown = false;

	//
	// Code for A key.
	//
	var aKeyCode = 65;

	//
	// Code for esc key.
	//
	var escKeyCode = 27;

	//
	// Selects the next node id.
	//
	var nextNodeID = 10;

	//
	// Setup the data-model for the chart.
	//
	var chartDataModel = {

			
			    "nodes": [
			        {
			            "name": "Dept 1",
			            "id": 0,
			            "x": 36,
			            "y": 28,
			            "inputConnectors": [
			                {
			                    "name": "DeptUser1"
			                }
			            ],
			            "outputConnectors": [
			                {
			                    "name": "DeptUser1"
			                }
			            ]
			        },
			        {
			            "name": "Dept 2",
			            "id": 1,
			            "x": 335,
			            "y": 174,
			            "inputConnectors": [
			                {
			                    "name": "DeptUser2"
			                }
			            ],
			            "outputConnectors": [
			                {
			                    "name": "DeptUser2"
			                }
			            ]
			        },
			        {
			            "name": "Dept 3",
			            "id": 10,
			            "x": 635,
			            "y": 30,
			            "inputConnectors": [
			                {
			                    "name": "DeptUser3"
			                }
			            ],
			            "outputConnectors": [
			                {
			                    "name": "DeptUser3"
			                }
			            ]
			        },
			        {
			            "name": "Dept 4",
			            "id": 11,
			            "x": 923,
			            "y": 164,
			            "inputConnectors": [
			                {
			                    "name": "DeptUser4"
			                }
			            ],
			            "outputConnectors": [
			                {
			                    "name": "DeptUser4"
			                }
			            ]
			        }
			    ],
			    "connections": [
			        {
			            "source": {
			                "nodeID": 0,
			                "connectorIndex": 0
			            },
			            "dest": {
			                "nodeID": 1,
			                "connectorIndex": 0
			            }
			        },
			        {
			            "source": {
			                "nodeID": 1,
			                "connectorIndex": 0
			            },
			            "dest": {
			                "nodeID": 10,
			                "connectorIndex": 0
			            }
			        },
			        {
			            "source": {
			                "nodeID": 10,
			                "connectorIndex": 0
			            },
			            "dest": {
			                "nodeID": 11,
			                "connectorIndex": 0
			            }
			        }
			    ]
			
	};

	//
	// Event handler for key-down on the flowchart.
	//
	$scope.keyDown = function (evt) {

		if (evt.keyCode === ctrlKeyCode) {

			ctrlDown = true;
			evt.stopPropagation();
			evt.preventDefault();
		}
	};

	//
	// Event handler for key-up on the flowchart.
	//
	$scope.keyUp = function (evt) {

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

	//
	// Add a new node to the chart.
	//
	$scope.addNewNode = function () {
		debugger;
		var nodeName = document.getElementById("email").value;//prompt("Enter a node name:", "New node");
		if (!nodeName) {
			return;
		}

		//
		// Template for a new node.
		//
		var newNodeDataModel = {
			name: nodeName,
			id: nextNodeID++,
			x: 0,
			y: 0,
			inputConnectors: [
				{
					name: "X"
				}
			],
			outputConnectors: [ 
				{
					name: "1"
				}
			],
		};

		$scope.chartViewModel.addNode(newNodeDataModel);
	};

	//
	// Add an input connector to selected nodes.
	//
	$scope.addNewInputConnector = function () {
		var connectorName = prompt("Enter a connector name:", "New connector");
		if (!connectorName) {
			return;
		}

		var selectedNodes = $scope.chartViewModel.getSelectedNodes();
		for (var i = 0; i < selectedNodes.length; ++i) {
			var node = selectedNodes[i];
			node.addInputConnector({
				name: connectorName,
			});
		}
	};
	$scope.addNewOutputConnector = function () {
		var connectorName = prompt("Enter a connector name:", "New connector");
		if (!connectorName) {
			return;
		}

		var selectedNodes = $scope.chartViewModel.getSelectedNodes();
		for (var i = 0; i < selectedNodes.length; ++i) {
			var node = selectedNodes[i];
			node.addOutputConnector({
				name: connectorName,
			});
		}
	};
	$scope.deleteSelected = function () {
		$scope.chartViewModel.deleteSelected();
	};
	$scope.chartViewModel = new flowchart.ChartViewModel(chartDataModel);
}])
;