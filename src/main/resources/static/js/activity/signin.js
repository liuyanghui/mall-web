function doSignin(){
	var signinDays = $("#signinDays").val();
	var points = $("#points").val();
	mui.ajax('/activity/dosignin.htm',{
		data:{},
		dataType:'json', //服务器返回json格式数据
		type:'post', //HTTP请求类型
		success:function(data){
			var ret = data.ret;
			var msg = data.msg;
			if ("0000"==ret){
				setSigninArea(signinDays);
				mui.toast('签到成功，送'+points+'积分',{ duration:'long', type:'div' });
				// 增加签到次数，修改签到样式
			}else if("0007"==ret){
				setSigninArea(signinDays);
				mui.toast('签到成功，送'+points+'积分',{ duration:'long', type:'div' });
			    setPrize();
			}else{
				// 签到失败，仅提示失败原因.
				mui.toast('签到失败，'+msg, { duration:'long', type:'div' });
			}
		}
	});
}

function setSigninArea(signinDays){
	$(".red18").text(signinDays*1+1);
	$("#signin").html('<div class="signin"><a href="#" onclick="return false;">已签到</a></div>');
	$("#signin_area").html('');
	$("#signed_area").css('display', 'block');
}

function setPrize() {
	initPrize();
	$("#yprize").show();
	$('html').css({"height":"100%","overflow":"hidden"});
	$('body').css({"height":"100%","overflow":"hidden"});
}
function initPrize() {
	$("#signin_banner").html('<div class="signin_banner"><img src="../../images/mall/signin_05.png" ></div>');
	$("#signin_banner").click(function() {
		$("#yprize").show();
	});
}
function checkSignStatus() {
	if ($('.signed:visible').length == 7) {
		initPrize();
	}
}
