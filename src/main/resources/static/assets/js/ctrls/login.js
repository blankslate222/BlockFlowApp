cmpe
		.controller(
				'loginCtrl',
				function($scope, $stateParams, $state, $log, $timeout,
						$rootScope, $http, $cookieStore) {
					
					$scope.user = {};

					$scope.doLogin = function() {
						console.log("in login");
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
									headers : {'Content-Type' : 'application/x-www-form-urlencoded'}
						})
						.success(function(data) {
							console.log(data);
							if (data.user.email) {
								$cookieStore.put('user', data.user);
								$cookieStore.put('client', data.client);
								//console.log($cookieStore.get('user'));
								$state.go('root.dash');
							} else
								$scope.status = data.error;
						})
						.error(function(err) {
							//$scope.status = data.error;
							console.log(err);
						});
					};
				});
