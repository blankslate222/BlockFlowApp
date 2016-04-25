cmpe
		.controller(
				'loginCtrl',
				function($scope, $stateParams, $state, $log, $timeout,
						$rootScope, $http) {

					$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
					$scope.user = {};

					$scope.doLogin = function() {
						var user = {
							username : $scope.user.email,
							password : $scope.user.password
						}
	
						console.log(user.username);
						console.log(user.password);

						$http.post(
								"/login",
								'username=' + user.username + '&password='
										+ user.password, {
						})
						.success(function(data) {
							console.log(data);
							if (data.email) {
								$state.go('root.dash');
								$window.location.href = '/index.html'
							} else
								$scope.status = data.error;
						})
						.error(function(err) {
							//$scope.status = data.error;
							console.log(err);
						});
					};
				});
