cmpe.controller('loginCtrl', function($scope, $stateParams, $state, $log, $timeout,
		$rootScope, $http) {
	
	$scope.user={};

	$scope.doLogin = function() {
		var user={
				username: $scope.user.email,
				password: $scope.user.password
		}
		//$state.go('root.dash');
		//$scope.status = "Invalid Password";
		$http.post("/login",user).success(function(data){
			console.log(data);
			if(data.email){
				//$state.go('root.dash');
			}				
			else
				$scope.status = data.error;
		}).error(function(err){
			$scope.status = data.error;
			console.log(err);
		})
	};
});
