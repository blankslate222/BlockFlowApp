cmpe.controller('headCtrl', function($scope, $rootScope, $http, $location, $state,
		$cookieStore) {
	$scope.tasks = [ {
		task : 'School Transcript',
		percent : 10
	}, {
		task : 'SSN Update',
		percent : 20
	}, {
		task : 'Class Schedule',
		percent : 30
	}, {
		task : 'School Payment',
		percent : 40
	}, ];

	$scope.getClass = function(path) {
		return ($location.path().substr(0) === path) ? 'active' : '';
	}
	//debugger;
	$scope.user = $cookieStore.get('user');
	$scope.superadmin = ($scope.user.id == 1);
	console.log($scope.superadmin);
	console.log($scope.user.role);
	$scope.admin = false;
	$scope.staff = false;
	$scope.manager = false;
	for(var rol in $scope.user.role) {
		if ($scope.user.role[rol] == "ADMIN") {
			console.log("setting staff");
			$scope.admin = true;
		}
		if ($scope.user.role[rol] == "STAFF") {
			console.log("setting staff");
			$scope.staff = true;
		} 
		if ($scope.user.role[rol] == "MANAGER") {
			console.log("setting manager");
			$scope.manager = true;
		}
	}
	
	$scope.role = $scope.user.role;
	
	$scope.checkAuth = function() {
		if($location.path().substr(0) == '/client' && $scope.user.role=="ENDUSER"){
				$state.go('root.login');
		}
		if($location.path().substr(0) == '/client' && $scope.user.role=="ENDUSER")
				$state.go('root.login');
	}
	//$scope.checkAuth();
	
	$scope.logout = function() {
		//console.log("in logout");
		$http.post("/logout").success(function(data) {
			console.log("log out success");
		});
		$scope.user=null;
		$cookieStore.remove('user');
		$cookieStore.remove('client');
		$state.go('root.login');
	}
	
});
