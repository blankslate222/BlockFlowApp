cmpe.controller('deptReqCtrl', function($scope, $rootScope, $http){
	$scope.tasks = 
			[{task: 'School Transcript', percent:10},
			 {task: 'SSN Update', percent:20},
			 {task: 'Class Schedule', percent:30},
			 {task: 'School Payment', percent:40},
			 ];
});
