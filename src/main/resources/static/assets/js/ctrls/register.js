cmpe.controller('registerCtrl', function($scope, $rootScope, $http, $window){
	
	$scope.requests=[];
	
	$scope.doSignUp = function(){
		console.log('creating signup request');
		var paramMap = {
				"name": $scope.user.name,
				"email": $scope.user.email,
				"passwordHash": $scope.user.password
			}
		$http.post("/signup", paramMap).success(function(data) {
			console.log(data);
			$window.location.href = '/#/login';
		});
		
	};
	
});
