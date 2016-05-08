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
				$scope.depts.push(data.controllerResponse.responseObject);
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
		$log, $timeout, $rootScope, $http, $cookieStore ) {
	$scope.clientId = $stateParams.clientId;
	console.log('$scope.clientId'+$scope.clientId);
	console.log('$cookieStore.clientId'+$cookieStore.get('client').id);
	$scope.clientId = $cookieStore.get('client').id;
	$scope.deptId = $stateParams.deptId;
	$scope.groups=[];
	$scope.dept = {};
	$scope.getDept = function() {
		$http.get("/data/department/" + $stateParams.deptId).success(
			function(data) {
				if (data.controllerResponse.responseObject) {
					var objs = data.controllerResponse.responseObject;
					$scope.dept = objs;
				}
			});
	};
	$scope.getDept();
	$scope.getGroups = function() {
		$http.get("/data/groupsbydept/" + $stateParams.deptId).success(
		function(data) {
			if (data.controllerResponse.responseObject) {
				
				var objs = data.controllerResponse.responseObject;
				console.log(objs);
				for (var i = 0; i < objs.length; i++) {
					 $scope.groups.push(objs[i]);
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
			console.log(data);
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
	$scope.groupId = $stateParams.grpId;

	$scope.grp = {};
	$scope.getGrp = function() {
		$http.get("/data/group/" + $stateParams.grpId).success(
			function(data) {
				if (data.controllerResponse.responseObject) {
					var objs = data.controllerResponse.responseObject;
					$scope.grp = objs;
				}
			});
	};
	$scope.getGrp();
	
	$scope.users = [];
	$scope.getUsersForGrp = function() {
		$http.get("/data/usersbygroup/" + $stateParams.grpId).success(
				function(data) {
					if (data.controllerResponse.responseObject) {
						
						var objs = data.controllerResponse.responseObject;
						console.log("Objects:"+objs);
						for (var i = 0; i < objs.length; i++) {
							console.log("App User:");
							console.log(objs[i].appUser);
							 $scope.users.push(objs[i].appUser);
						}
					}
				});
	};
	$scope.getUsersForGrp();

	$scope.getAllUsers = function() {
	};

	
	$scope.addUserToGrp = function() {
		
		var paramMap = {
			groupId : $scope.groupId,
			userEmail : $scope.email
		}
		$http.post("admin/group/adduser",paramMap).success(function(data) {
			console.log("Managed User data:");
			console.log(data);
			$scope.users.push(data.controllerResponse.responseObject.appUser);
		});

	};
});
