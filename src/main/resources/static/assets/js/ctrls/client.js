cmpe.controller('clientCtrl', function($scope, $stateParams, $state, $log,
		$timeout, $rootScope, $http) {

	$scope.clients = [];

	$scope.doClientCreate = function() {
		var client = {
			name : $scope.client.name,
			address : $scope.client.address
		}
		$http.post("/admin/client/create", client).success(function(data) {
			if (data.controllerResponse.responseObject) {
				$scope.clients.push(data.controllerResponse.responseObject);
				console.log(data.controllerResponse.responseObject);
			} else {
			}
		}).error(function(err) {
			// $scope.status = data.error;
			console.log("Error");
		});
	};

	$scope.getClients = function() {
		$http.get("/data/clients").success(function(data) {
			var objs = data.controllerResponse.responseObject;
			for (var i = 0; i < objs.length; i++) {
				$scope.clients.push(objs[i]);
			}
			console.log($scope.clients);
		});
	};
	$scope.getClients();
});

cmpe.controller('clientDetailsCtrl', function($scope, $stateParams, $state,
		$log, $timeout, $rootScope, $http) {
	$scope.client = {};
	$scope.getClient = function() {
		$http.get("/data/client/" + $stateParams.clientID).success(
				function(data) {
					if (data.controllerResponse.responseObject) {
						var objs = data.controllerResponse.responseObject;
						$scope.client = objs;
						console.log($scope.client);
					}
					// console.log(data.controllerResponse.responseObject);
				});
	};
	$scope.getClient();
	
	$scope.depts = [];
	$scope.getDepts = function() {
		  $http.get("/data/deptbyclient/" +
		  $stateParams.clientID).success(function(data){
			  if(data.controllerResponse.responseObject){ 
				  var objs=data.controllerResponse.responseObject; 
				  for(var i=0;i<objs.length;i++){
					  $scope.depts.push(objs[i]); 
				  } 
				  console.log($scope.depts); 
			  }
		  });
	}
	$scope.getDepts();
	
	$scope.doDeptCreate = function() {
		var dept = {
			name : $scope.dept.name,
			client : $scope.client
		}
		$http.post("/admin/department/create", dept, {
			headers : {'Content-Type' : 'application/json'}
		}).success(function(data) {
			if (data.controllerResponse.responseObject) {
				$scope.client.clientDepartments.push(data.controllerResponse.responseObject);
				console.log(data.controllerResponse.responseObject);
			} else {
			}
		}).error(function(err) {
			// $scope.status = data.error;
			console.log("Error:" + JSON.stringify(err));
		});
	};
});

cmpe.controller('clientDeptDetailsCtrl', function($scope, $stateParams, $state,
		$log, $timeout, $rootScope, $http) {
	$scope.clientId = $stateParams.clientId;
	$scope.deptId = $stateParams.deptId;
	$scope.dept = {};
	$scope.getDept = function() {
		$http.get("/data/department/" + $stateParams.deptId).success(
			function(data) {
				if (data.controllerResponse.responseObject) {
					var objs = data.controllerResponse.responseObject;
					$scope.dept = objs;
				}
				if(!$scope.dept.groups){
					$scope.groups=$scope.dept.groups;
				}
			});
	};
	$scope.getDept();
	$scope.groups=[];
	$scope.getGroups = function() {
		$http.get("/data/groupsbydept/" + $stateParams.deptId).success(
		function(data) {
			if (data.controllerResponse.responseObject) {
				
				var objs = data.controllerResponse.responseObject;
				console.log(objs);
				for (var i = 0; i < objs.length; i++) {
					$scope.groups = objs[i].name;
				}
			}
		});
	};
	$scope.getGroups();
	$scope.doGrpCreate = function() {
		var grp = {
			name : $scope.name,
			department: $scope.dept
		}
		
		$http.post("/admin/group/create", grp, {
			headers : {'Content-Type' : 'application/json'}
		}).success(function(data) {
			//console.log(data);
			if (data.controllerResponse.responseObject) {
				console.log(data.controllerResponse.responseObject);
				$scope.groups.push(data.controllerResponse.responseObject);
				
			} else {
			}
		}).error(function(err) {
			// $scope.status = data.error;
			console.log("Error:" + JSON.stringify(err));
		});
	}
});

cmpe.controller('clientDeptGrpDetailsCtrl', function($scope, $stateParams,
		$state, $log, $timeout, $rootScope, $http) {
	$scope.clientId = $stateParams.clientID;
	$scope.deptID = $stateParams.deptID;
	$scope.grp = {};
	$scope.getGrp = function() {
		/*
		 * $http.get("/data/dept/" +
		 * $stateParams.deptID).success(function(data){
		 * if(data.controllerResponse.responseObject){ var
		 * objs=data.controllerResponse.responseObject; $scope.client=objs;
		 * console.log($scope.client); }
		 * //console.log(data.controllerResponse.responseObject); });
		 */
		var g = {
			id : 1,
			name : "Grp1"
		}
		$scope.grp = g;
	};
	$scope.getDept();
	$scope.users = [];
	$scope.getUsersForGrp = function() {
		/*
		 * $http.get("/data/dept/" +
		 * $stateParams.deptID).success(function(data){
		 * if(data.controllerResponse.responseObject){ var
		 * objs=data.controllerResponse.responseObject; for(var i=0;i<objs.length;i++){
		 * $scope.groups.push(objs[i]); } console.log($scope.groups); }
		 * //console.log(data.controllerResponse.responseObject); });
		 */
	};
	$scope.getUsersForGrp();

	$scope.getAllUsers = function() {
		/*
		 * $http.get("/data/dept/" +
		 * $stateParams.deptID).success(function(data){
		 * if(data.controllerResponse.responseObject){ var
		 * objs=data.controllerResponse.responseObject; for(var i=0;i<objs.length;i++){
		 * $scope.groups.push(objs[i]); } console.log($scope.groups); }
		 * //console.log(data.controllerResponse.responseObject); });
		 */
	};

	$scope.addUserToGrp = function() {
		var grp = {
			id : $scope.clientId,
			name : $scope.name
		}
		$scope.groups.push(grp);
		/*
		 * $http.post("/admin/dept/create",dept).success(function(data){
		 * if(data.controllerResponse.responseObject){
		 * $scope.depts.push(data.controllerResponse.responseObject); } else{ }
		 * }).error(function(err){ //$scope.status = data.error;
		 * console.log("Error"); });
		 */
	};
});
