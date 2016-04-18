var cmpe = angular.module('cmpe', ['ui.router','flowChart', ]);

cmpe.config(function($stateProvider, $urlRouterProvider){

	$urlRouterProvider.otherwise("/");

	$stateProvider
		.state('root',{
			url: '',
			controller : 'baseCtrl'
			//abstract: true,
		})
		.state('root.login',{
			url: '/login',
			views: {
				'container@': {
					templateUrl: 'views/login.html',
					controller: 'loginCtrl'
				}
			}
		})
		.state('root.base', {
			url: '/',
			views: {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/workflow.html',
					controller : 'workCtrl'
				}
			}
		})
		.state('root.register', {
			url: '/register',
			views: {
				'container@' : {
					templateUrl : 'views/register.html',
					controller : 'registerCtrl'
				}
			}
		})
		.state('root.workflow', {
			url : '/workflow',
			views : {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/workflow.html',
					controller : 'workCtrl'
				}
			}
		})
		.state('root.dash', {
			url : '/dashboard',
			views : {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/dashboard.html',
					controller : 'dashCtrl'
				}
			}
		})
		.state('root.dept', {
			url : '/dept',
			views : {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/dept.html',
					controller : 'deptCtrl'
				}
			}
		})
		.state('root.deptReq', {
			url: '/deptReq',
			views: {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/deptRequest.html',
					controller : 'deptReqCtrl'
				}
			}
		})
		.state('root.clientDash', {
			url : '/clientDash',
			views : {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/clientDash.html',
					controller : 'clientDashCtrl'
				}
			}
		})
});