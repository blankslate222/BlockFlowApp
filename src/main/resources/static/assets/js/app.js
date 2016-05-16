var cmpe = angular.module('cmpe', ['ui.router', 'flowChart', 'ngCookies', 'angularSpinners']);

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
		.state('root.blockchain', {
			url: '/block',
			views: {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/blockChainFeed.html',
					controller : 'feedCtrl'
				}
			}
		})
		.state('root.adminfeed', {
			url: '/adminfeed',
			views: {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/adminFeed.html',
					controller : 'adminFeedCtrl'
				}
			}
		})
		.state('root.deptfeed', {
			url: '/deptfeed',
			views: {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/deptFeed.html',
					controller : 'deptFeedCtrl'
				}
			}
		})
		.state('root.request', {
			url: '/request',
			views: {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/createReq.html',
					controller : 'createReqCtrl'
				}
			}
		})
		.state('root.request.id', {
			url: '/:requestID',
			views: {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/requestdetails.html',
					controller : 'requestDetailCtrl'
				}
			}
		})
		.state('root.inbox', {
			url: '/inbox',
			views: {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/inbox.html',
					controller : 'inboxCtrl'
				}
			}
		})
		.state('root.myrequests', {
			url: '/myrequests',
			views: {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/myrequests.html',
					controller : 'myreqCtrl'
				}
			}
		})
		.state('root.charts',{
			url: '/charts',
			views: {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@': {
					templateUrl: 'views/testCharts.html',
					controller: ''
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
		.state('root.workflowcrete', {
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
		.state('root.userDash', {
			url : '/userDash',
			views : {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/clientDash.html',
					controller : 'userDashCtrl'
				}
			}
		})
		.state('root.adminDash', {
			url : '/adminDash',
			views : {
				'header@': {
					templateUrl: 'views/header.html',
					controller: 'headCtrl'
				},
				'container@' : {
					templateUrl : 'views/dashboard.html',
					controller : 'adminDashCtrl'
				}
			}
		})
});