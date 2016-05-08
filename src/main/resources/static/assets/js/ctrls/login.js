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
						$http.post(
								"/login",
								'username=' + user.username + '&password='
										+ user.password, {
									headers : {'Content-Type' : 'application/x-www-form-urlencoded'}
						})
						.success(function(data) {
							console.log(data.user.role);
							if (data.user.email) {
								$cookieStore.put('user', data.user);
								$cookieStore.put('client', data.client);
								debugger;
								//console.log($cookieStore.get('user'));
								if(data.user.role='ENDUSER')
									$state.go('root.request');
							} else
								$scope.status = data.error;
						})
						.error(function(err) {
							//$scope.status = data.error;
							$scope.status ="Invalid Username or Password";
						});
					};
				});
