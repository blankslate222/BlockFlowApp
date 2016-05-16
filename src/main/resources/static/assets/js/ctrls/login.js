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
								console.log(data.user);
								var userrole = data.user.role;
								var role = '';
								for(var key in userrole) {
									if (userrole[key] == 'ENDUSER' && role != 'ADMIN' && role != 'MANAGER' && role != 'STAFF') {
										role = 'ENDUSER';
									}
									if (userrole[key] == 'MANAGER' || userrole[key] == 'STAFF' && role != 'ADMIN') {
										role = 'MANAGER';
									}
									if (userrole[key] == 'ADMIN') {
										role = 'ADMIN';
										break;
									}									
								}
								if (data.user.id == 1)
									$state.go('root.adminfeed');
								else if(role == 'ENDUSER')
									$state.go('root.userDash');
								else if (role == 'ADMIN')
									$state.go('root.adminDash');
								else 
									$state.go('root.userDash');
							} else
								$scope.status = data.error;
						})
						.error(function(err) {
							//$scope.status = data.error;
							$scope.status ="Invalid Username or Password";
						});
					};
				});
