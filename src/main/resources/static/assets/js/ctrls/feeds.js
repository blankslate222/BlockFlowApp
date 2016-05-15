cmpe
.controller('feedCtrl', function($scope, $state, $rootScope, $http, $cookieStore) {
	
	$scope.feedList = [];
	debugger;
	var clientid = $cookieStore.get('client').id;
	console.log(clientid);
	$scope.getFeed = function() {
		console.log("inside get feed");
		$http.get("data/chainfeed/" + clientid).success(function(data) {
			var objs = data.controllerResponse.responseObject;
			for (var i = 0; i < objs.length; i++) {
				$scope.feedList.push(objs[i]);
				//$scope.feedList.push(objs[i]);
			}
			console.log(objs);
			console.log("Feed List");
			console.log($scope.feedList);
			console.log($scope.feedList[0].transactionid);
		});
	};
	$scope.getFeed();

	$scope.getadminfeed = function() {
		$state.go('root.adminfeed');
	};
	
	$scope.t={};
	$scope.load = function(t) {

		$http.get("/data/transactionData/"+t).success(function(data) {
				console.log(data.controllerResponse.responseObject);
				$scope.t=data.controllerResponse.responseObject;
		});
	};
	
	
});

cmpe.controller('adminFeedCtrl', function($scope, $state, $rootScope, $http, $cookieStore) {
      
	$scope.feedList = [];

	$scope.getFeed = function() {
		
		console.log("inside get feed");
		$http.get("data/adminfeed").success(function(data) {
			console.log(data);
			var obj = data.controllerResponse;
		
			for (var key in obj) {
				console.log(key + " -- " + obj[key])
				var feedobj = {};
				var txnarr = obj[key];
				feedobj.client = key;
				feedobj.transactions = [];
				for (var i = 0; i < txnarr.length; i++) {
					feedobj.transactions.push(txnarr[i].transactionid);
				}
				//debugger;
				$scope.feedList.push(feedobj);
				
				//$scope.feedList.push(objs[i]);
			}
			console.log($scope.feedList);
			
			
		    google.charts.setOnLoadCallback(drawChart);
		    function drawChart() {
		    	
		      var arrOutput = [['Client', 'Number of Transactions']];	
		      console.log("******"+$scope.feedList.length);
		     
		      for (var i = 0; i < $scope.feedList.length; i++) {
		    	  var arr = [$scope.feedList[i].client, $scope.feedList[i].transactions.length];								
		    	  arrOutput.push(arr);
				}
		      
		      console.log(arrOutput);
		      
		      var data = google.visualization.arrayToDataTable(arrOutput);
		    	
//		      var data = google.visualization.arrayToDataTable([
//		        ['Client', 'Number of Transactions'],
//		        ['Bank of America', 10],
//		        ['Chase', 20],
//		        ['SJSU', 5],
//		        ['Bank of America', 10],
//		        ['Chase', 20],
//		        ['SJSU', 5],
//		        ['Bank of America', 10],
//		        ['Chase', 20],
//		        ['SJSU', 5],
//		      ]);

		      var options = {
		        chart: {
		          title: '',
		          subtitle: 'Number of blockchain transactions per each client',
		        }
		      };

		      var chart = new google.charts.Bar(document.getElementById('columnchart_material'));

		      chart.draw(data, options);
		    }
			
			
		});
	};
	$scope.getFeed();
	
	$scope.toggleChart = function(chart_id){
        
	      var div = document.getElementById(chart_id);
	      if (div.style.display !== 'none') {
	          div.style.display = 'none';
	      }
	      else {
	          div.style.display = 'block';
	      }

	    };
	    
	$scope.testing = function(){
        
	    };

	
	$scope.t={};
	$scope.load = function(t) {

		$http.get("/data/transactionData/"+t).success(function(data) {
				console.log(data.controllerResponse.responseObject);
				$scope.t=data.controllerResponse.responseObject;
		});
	};
	
	$scope.check = function() {
		$http.get("/admin/request/validate/"+$scope.requestID).success(function(data) {
			console.log(data);
				console.log(data.controllerResponse.isValid);
				var valid = data.controllerResponse.isValid;
				if (valid == "true") {
					$scope.validityMsg = "All transactions logged on the Blockchain for request with ID = "+ $scope.requestID + " are valid";
				} else {
					$scope.validityMsg = "Request ID = "+ $scope.requestID + " does not have valid transactions logged on the Blockchain";
				}
		});
	};
	
	$scope.getTransactionDetail = function() {
		console.log("going to get blockchain host!");
	};
	
	
});

cmpe.controller('deptFeedCtrl', function($scope, $state, $rootScope, $http, $cookieStore) {
	
	$scope.feedList = [];
	$scope.getFeed = function() {
		
		console.log("inside dept feed");
		$http.get("data/deptfeed").success(function(data) {
			console.log(data);
			var obj = data.controllerResponse.deptfeed;
			var dept = data.controllerResponse.department;
			$scope.department_name = dept.name;
			for (var feed in obj) {
				$scope.feedList.push(obj[feed]);
			}
		
			console.log("Feed List");
			console.log($scope.feedList);

		});
	};
	
	$scope.t={};
	$scope.load = function(t) {

		$http.get("/data/transactionData/"+t).success(function(data) {
				console.log(data.controllerResponse.responseObject);
				$scope.t=data.controllerResponse.responseObject;
		});
	};
	$scope.getFeed();
	
	
});