var cmpe = angular.module('cmpe', ['ui.router', 'flowChart', 'ngCookies']);

cmpe.config(function($stateProvider, $urlRouterProvider){

	$urlRouterProvider.otherwise("/");

	$stateProvider
		.state('root',{
			url: '',
			views: {
				'container@': {
					templateUrl: 'views/login.html',
					controller: 'loginCtrl'
				}
			}
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
		.state('root.client', {
			url: '/client',
			views: {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/client.html',
					controller : 'clientCtrl'
				}
			}
		})
		.state('root.client.id', {
			url: '/:clientID',
			views: {
				'container@' : {
					templateUrl : 'views/clientDetails.html',
					controller : 'clientDetailsCtrl'
				}
			}
		})
		.state('root.client.id.dept', {
			url: '/dept/:deptId',
			views: {
				'container@' : {
					templateUrl : 'views/clientDeptDetails.html',
					controller : 'clientDeptDetailsCtrl'
				}
			}
		})
		.state('root.client.id.dept.group', {
			url: '/group/:grpId',
			views: {
				'container@' : {
					templateUrl : 'views/clientDeptGrpDetails.html',
					controller : 'clientDeptGrpDetailsCtrl'
				}
			}
		})
		.state('root.workflowcrete', {
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
		.state('root.workflow', {
			url : '',
			views : {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/workflowdisplay.html',
					controller : 'workViewCtrl'
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
		.state('root.deptss', {
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