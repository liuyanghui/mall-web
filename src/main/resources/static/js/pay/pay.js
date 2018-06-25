var i = 0;
function confirmPay(){
	if($("#hasPin").val()!="true"){
		tourl("/member/addpwd.htm");
		return;
	}
	if(i==0){
		var trace = $("#t").val();
		//请求确认支付,成功则弹出积分支付密码窗口
		$.post(
			"/cashier/confirmpay.htm",
			{t:trace}
			,function(result){
				if(result.ret == "0000" || result.ret == "00000020"){
					i++;
					$("#paypwddiv").show();
					//$("#password1").focus();  
				}else{
					mui.toast(result.msg,{ duration:'1', type:'div' });
				}	
		});
	}else{
		$("#paypwddiv").show();
//		$("#password1").focus();
	}
}

function wxpay(data, trace){
	WeixinJSBridge.invoke(
			'getBrandWCPayRequest', {
	           "appId": data.appId, //公众号名称，由商户传入     
	           "timeStamp": data.timeStamp,   //时间戳，自1970年以来的秒数     
	           "nonceStr": data.nonceStr, //随机串     
	           "package": data.package,     
	           "signType": data.signType, //微信签名方式
	           "paySign": data.paySign //微信签名 
	       },
	       function(res){
	    	   // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
	    	   window.location.href="/web/pay/wx/"+trace+".htm?result="+res.err_msg;
	       }
	   );
}
function tourl(url){
	var temp = document.createElement("form");        
    temp.action = url;        
    temp.method = "post";        
    temp.style.display = "none";        
    var opt = document.createElement("input");        
    opt.name = "t";
    opt.value = $("#t").val();
    temp.appendChild(opt);
    document.body.appendChild(temp);
    temp.submit();
}