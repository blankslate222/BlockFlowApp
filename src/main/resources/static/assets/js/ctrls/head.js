cmpe.controller('headCtrl', function($scope, $rootScope, $http, $location){
	$scope.tasks = 
			[{task: 'School Transcript', percent:10},
			 {task: 'SSN Update', percent:20},
			 {task: 'Class Schedule', percent:30},
			 {task: 'School Payment', percent:40},
			 ];
	
	$scope.getClass = function (path) {
			console.log(path);
		  return ($location.path().substr(0) === path) ? 'active' : '';
	}
});

