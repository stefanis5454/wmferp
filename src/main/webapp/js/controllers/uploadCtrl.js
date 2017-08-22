(function(app) {
	app
			.controller(
					'uploadCtrl',
					function($scope, $http, $location, cfpLoadingBar) {

						$scope.upload = function() {
							// var fileInput =
							// document.getElementById("inputFile");
							console.log($scope.uploadFile);
							// var file = fileInput.files[0];
							var file = $scope.uploadFile;
							// prevent cancel button trigger change
							// event
							if (file) {
								var data = new FormData(); // create a
								// FormData
								// to store
								// key/value
								data.append('file', file);

								var req = {
									method : 'POST',
									url : 'api/UploadExcelFile',
									headers : {
										'Content-Type' : undefined
									},
									data : data
								};

								console.log(req)

								$http(req)
										.then(
												function(result) {
													console.log(result);
													if (result.data.trim() == 'Success') {
														swal("文件数据已成功插入数据库！",
																'', 'success');
													} else {
														swal(
																"文件上传或数据插入出现问题，请重试！",
																'', 'warning')
													}
												},
												function(error) {
													console.log(error);
													swal("文件上传或数据插入出现问题，请重试！",
															'', 'warning')
												});

							} else {
								swal("请先选择文件！", '', 'warning')
							}

						}

						$scope.download = function() {
							$http
									.post("DownloadExcelFile", {

									}, {
										responseType : "blob"
									})
									.then(
											function(data) {
												var blob = new Blob(
														[ data ],
														{
															type : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
														});
												var fileName = "WMF产品数据备份_"
														+ $scope.$parent.ledgerDate
																.Format("yyyy-MM-dd");
												var a = document
														.createElement("a");
												document.body.appendChild(a);
												a.download = fileName;
												a.href = URL
														.createObjectURL(blob);
												a.click();
											})
						}

					})
})(app);