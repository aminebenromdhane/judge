function OCRController($scope,$http) {
	
	$scope.goodocr = 0;
	$scope.failocr = 0;
	$scope.existocr = 0;
	$scope.threadNumber = 1;
	$scope.loading = false;
	
	$scope.refreshCount = function(){
		$http.get('/judge/verificator/goodocr').success(function(res){
	  		$scope.goodocr = res;
		});		
	};
	
	$scope.refreshFail = function(){
		$http.get('/judge/verificator/failocr').success(function(res){
	  		$scope.failocr = res;
		});		
	};

	$scope.refreshExist = function(){
		$http.get('/judge/verificator/existocr').success(function(res){
	  		$scope.existocr = res;
		});		
	};
	$scope.refresh = function(){
		if($scope.goodocr != 0 && !$scope.loading){
			$scope.loading = true;
		}
		$scope.refreshCount();
		$scope.refreshFail();
		$scope.refreshExist();
	};
	$scope.restart = function(){
		$scope.loading = true;
		$http.get('/judge/ocrpdf/start?numberThread='+$scope.threadNumber).success(function(res){
			$scope.loading = false;
			console.log("finish");
		});
	};
	setInterval($scope.refresh,1000);
}