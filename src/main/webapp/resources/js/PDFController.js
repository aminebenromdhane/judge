function PDFController($scope,$http) {
	
	$scope.countpdf = 0;
	$scope.failpdf = 0;
	$scope.existpdf = 0;
	$scope.waitingpdf = 0;
	$scope.threadNumber = 1;
	$scope.loading = false;
	
	$scope.refreshCount = function(){
		$http.get('/judge/verificator/countpdf').success(function(res){
	  		$scope.countpdf = res;
		});		
	};

	$scope.refreshWaiting = function(){
		$http.get('/judge/verificator/countwaiting').success(function(res){
	  		$scope.waitingpdf = res;
		});		
	};
	
	$scope.refreshFail = function(){
		$http.get('/judge/verificator/countfail').success(function(res){
	  		$scope.failpdf = res;
		});		
	};

	$scope.refreshExist = function(){
		$http.get('/judge/verificator/countexist').success(function(res){
	  		$scope.existpdf = res;
		});		
	};
	$scope.refresh = function(){
		if($scope.waitingpdf != 0 && !$scope.loading){
			$scope.loading = true;
		}
		$scope.refreshCount();
		$scope.refreshFail();
		$scope.refreshExist();
		$scope.refreshWaiting();
	};
	$scope.restart = function(){
		$scope.loading = true;
		$http.get('/judge/downloadpdf/start?numberThread='+$scope.threadNumber).success(function(res){
			$scope.loading = false;
			console.log("finish");
		});
	};
	setInterval($scope.refresh,1000);
}