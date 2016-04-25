cmpe.controller('clientCtrl', function($scope, $stateParams, $state, $log, $timeout,
		$rootScope, $http) {
	
	$scope.clients=[];
	
	$scope.doClientCreate = function() {
		var client={
				name: $scope.client.name,
				address: $scope.client.address
		}
		$http.post("/admin/client/create",client).success(function(data){
			if(data.controllerResponse.responseObject){
				$scope.clients.push(data.controllerResponse.responseObject);
			}				
			else{
			}
		}).error(function(err){
			//$scope.status = data.error;
			console.log("Error");
		});
	};
	
	$scope.getClients = function() {
		$http.get("/data/clients").success(function(data){
			var objs=data.controllerResponse.responseObject;
			for(var i=0;i<objs.length;i++){
				$scope.clients.push(objs[i]);
			}
			console.log($scope.clients);
		});
	};
	$scope.getClients();
});

cmpe.controller('clientDetailsCtrl', function($scope, $stateParams, $state, $log, $timeout,
		$rootScope, $http) {
	$scope.client={};
	$scope.getClient = function() {
		$http.get("/data/client/" + $stateParams.clientID).success(function(data){
			if(data.controllerResponse.responseObject){
				var objs=data.controllerResponse.responseObject;
				$scope.client=objs;
				console.log($scope.client);
			}
			//console.log(data.controllerResponse.responseObject);
		});
	};
	$scope.getClient();
	$scope.depts=[];
	$scope.getDepts = function() {
		/*$http.get("/data/dept/" + $stateParams.clientID).success(function(data){
			if(data.controllerResponse.responseObject){
				var objs=data.controllerResponse.responseObject;
				for(var i=0;i<objs.length;i++){
					$scope.depts.push(objs[i]);
				}
				console.log($scope.depts);
			}
			//console.log(data.controllerResponse.responseObject);
		});*/
	};
	$scope.getDepts();
	
	$scope.doDeptCreate = function() {
		var dept={
				id:1,
				name: $scope.name
		}
		$scope.depts.push(dept);
		/*$http.post("/admin/dept/create",dept).success(function(data){
			if(data.controllerResponse.responseObject){
				$scope.depts.push(data.controllerResponse.responseObject);
			}				
			else{
			}
		}).error(function(err){
			//$scope.status = data.error;
			console.log("Error");
		});*/
	};
});


cmpe.controller('clientDeptDetailsCtrl', function($scope, $stateParams, $state, $log, $timeout,
		$rootScope, $http) {
	$scope.clientId=$stateParams.clientID;
	$scope.dept={};
	$scope.getDept = function() {
		/*
		$http.get("/data/dept/" + $stateParams.deptID).success(function(data){
			if(data.controllerResponse.responseObject){
				var objs=data.controllerResponse.responseObject;
				$scope.client=objs;
				console.log($scope.client);
			}
			//console.log(data.controllerResponse.responseObject);
		});*/
		var d={
				id:1,
				name: "Dept1",
				address:"India"
		}
		$scope.dept=d;
	};
	$scope.getDept();
	$scope.groups=[];
	$scope.getGroups = function() {
		/*$http.get("/data/dept/" + $stateParams.deptID).success(function(data){
			if(data.controllerResponse.responseObject){
				var objs=data.controllerResponse.responseObject;
				for(var i=0;i<objs.length;i++){
					$scope.groups.push(objs[i]);
				}
				console.log($scope.groups);
			}
			//console.log(data.controllerResponse.responseObject);
		});*/
	};
	$scope.getGroups();
	
	$scope.doGrpCreate = function() {
		var grp={
				id: $scope.clientId,
				name: $scope.name
		}
		$scope.groups.push(grp);
		/*$http.post("/admin/dept/create",dept).success(function(data){
			if(data.controllerResponse.responseObject){
				$scope.depts.push(data.controllerResponse.responseObject);
			}				
			else{
			}
		}).error(function(err){
			//$scope.status = data.error;
			console.log("Error");
		});*/
	};
});


cmpe.controller('clientDeptGrpDetailsCtrl', function($scope, $stateParams, $state, $log, $timeout,
		$rootScope, $http) {
	$scope.clientId=$stateParams.clientID;
	$scope.deptID=$stateParams.deptID;
	$scope.grp={};
	$scope.getGrp = function() {
		/*
		$http.get("/data/dept/" + $stateParams.deptID).success(function(data){
			if(data.controllerResponse.responseObject){
				var objs=data.controllerResponse.responseObject;
				$scope.client=objs;
				console.log($scope.client);
			}
			//console.log(data.controllerResponse.responseObject);
		});*/
		var g={
				id:1,
				name: "Grp1"
		}
		$scope.grp=g;
	};
	$scope.getDept();
	$scope.users=[];
	$scope.getUsersForGrp = function() {
		/*$http.get("/data/dept/" + $stateParams.deptID).success(function(data){
			if(data.controllerResponse.responseObject){
				var objs=data.controllerResponse.responseObject;
				for(var i=0;i<objs.length;i++){
					$scope.groups.push(objs[i]);
				}
				console.log($scope.groups);
			}
			//console.log(data.controllerResponse.responseObject);
		});*/
	};
	$scope.getUsersForGrp();
	
	$scope.getAllUsers = function() {
		/*$http.get("/data/dept/" + $stateParams.deptID).success(function(data){
			if(data.controllerResponse.responseObject){
				var objs=data.controllerResponse.responseObject;
				for(var i=0;i<objs.length;i++){
					$scope.groups.push(objs[i]);
				}
				console.log($scope.groups);
			}
			//console.log(data.controllerResponse.responseObject);
		});*/
	};
	
	$scope.addUserToGrp = function() {
		var grp={
				id: $scope.clientId,
				name: $scope.name
		}
		$scope.groups.push(grp);
		/*$http.post("/admin/dept/create",dept).success(function(data){
			if(data.controllerResponse.responseObject){
				$scope.depts.push(data.controllerResponse.responseObject);
			}				
			else{
			}
		}).error(function(err){
			//$scope.status = data.error;
			console.log("Error");
		});*/
	};
});