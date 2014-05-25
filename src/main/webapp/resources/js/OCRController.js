function OCRController($scope,$http) {
	
	$scope.goodocr = 0;
	$scope.failocr = 0;
	$scope.existocr = 0;
	$scope.threadNumber = 1;
	$scope.loading = false;
	
	refreshCount = function(){
		$http.get('/judge/verificator/goodocr').success(function(res){
	  		$scope.goodocr = res;
		});		
	};
	
	refreshFail = function(){
		$http.get('/judge/verificator/failocr').success(function(res){
	  		$scope.failocr = res;
		});		
	};

	refreshExist = function(){
		$http.get('/judge/verificator/existocr').success(function(res){
	  		$scope.existocr = res;
		});		
	};
	refresh = function(){
		if($scope.goodocr != 0 && !$scope.loading){
			$scope.loading = true;
		}
		refreshCount();
		refreshFail();
		refreshExist();
		refreshWaiting();
	};
	$scope.restart = function(){
		$scope.loading = true;
		$http.get('/judge/ocrpdf/start?numberThread='+$scope.threadNumber).success(function(res){
			$scope.loading = false;
			console.log("finish");
		});
	};
	setInterval(refresh,1000);
}