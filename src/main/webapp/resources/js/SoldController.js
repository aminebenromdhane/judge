function SoldController($scope,$http) {
	
	$scope.isrun = false;
	$scope.work = false;
	$scope.remain = "";
	
	$scope.refreshAll = function(){
		$http.get('/zd/sold/isrun').success(function(res){
			$scope.isrun = (res == "true");
		});
		$http.get('/zd/sold/stat').success(function(res){
			$scope.work = (res == "true");
		});
		$http.get('/zd/sold/remain').success(function(res){
			$scope.remain = res;
		});
	};

	$scope.refresh = function(){
		$scope.refreshAll();
	};
	$scope.start = function(){
		$http.get('/zd/sold/start').success(function(res){});
	};
	$scope.stop = function(){
		$http.get('/zd/sold/stop').success(function(res){});
	};
	setInterval($scope.refresh,1000);
}