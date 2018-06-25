$(function(){
	$("#pin").keyup(function(){
		toPwd(this);
	});
	$("#confirpin").keyup(function(){
		toPwd(this);
	});
	$("#updatePwd").click(function(){
		var $oldPwd = $("#pwd").val();
		var $pwd = $("#password").val();
		var $pwd2 = $("#password1").val();
		var exponent = $("#exponent").val();
		var modulus = $("#module").val();
		//密码验证
		//验证-1:必须为8~20位字母与数字组合
		var pwd_count_ret = /^[a-zA-Z0-9]{8,20}$/;
		//验证-2:密码必须是8~20位数字与字母组合
		var pwd_ret = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/;
		
		 if(pwd_count_ret.test($oldPwd) && pwd_count_ret.test($pwd) && pwd_count_ret.test($pwd2)){
	      }else{
	    	  mui.toast('请输入字母或者数字8-20位密码',{duration:'1', type:'div' });
	          return;
	      }
		 if(pwd_ret.test($oldPwd) && pwd_ret.test($pwd) && pwd_ret.test($pwd2)){
	      }else{
	    	  mui.toast('请输入字母或者数字8-20位密码',{duration:'1', type:'div'});
	          return;
	      }
	 	  if($pwd!=$pwd2){
	 		  mui.toast('两次密码输入不一致',{duration:'1', type:'div'});
	 		  return;
	 	  }
	 	  
	 	 //前段加密
		 var publicKey = RSAUtils.getKeyPair(exponent, '', modulus);
		 var oldPwd = RSAUtils.encryptedString(publicKey, $oldPwd);
		 var pwd = RSAUtils.encryptedString(publicKey, $pwd);
		 
		 $.post(
				"/member/updatepwd.htm",
				{oldPwd:oldPwd,password:pwd}
				,function(result){
					if(result.ret == "success"){
						mui.toast('密码修改成功',{ duration:'1', type:'div' });
						window.location="/login/index.htm";
					}else{
						mui.toast(result.msg,{ duration:'1', type:'div' });
					}	
			});
			
	});
	
	$("#sendcode").click(function(){
		var count = 60;
		var countdown = setInterval(CountDown, 1000);
		
		function CountDown() {
			$("#sendcode").attr("disabled", true);
			$("#sendcode").text(count+"秒后重新发送");
			if (count == 0) {
				$("#sendcode").removeAttr("disabled");
				$("#sendcode").text("发送验证码");
				clearInterval(countdown);
			}
			count--;
		}
		var val = this;
		$.post("/member/sendauthcode.do",function(data){
				if(data.ret == "success"){
					mui.toast("发送成功",{ duration:'1000', type:'div' })
					CountDown();
				}else{
					mui.toast(data.msg,{ duration:'1000', type:'div' });
				}
			
			});	
		
	});
	
	
	$("#updatePayPwd").click(function(){
		var $authcode = $("#authcode").val();
		var $pwd = $("#pin").val();
		var exponent = $("#exponent").val();
		var modulus = $("#module").val();
		var t = $("#t").val();
		
		var ret2=/^\d{4}$/;
		if(ret2.test($authcode)){
		}else{
			mui.toast('请输入4位数字验证码',{ duration:'1000', type:'div' });
			return;
		}
		var ret =/^\d{6}$/;
		 if(ret.test($pwd)){
	      }else{
	    	mui.toast('请输入6位数字积分支付密码',{ duration:'1000', type:'div' });
	        return;
	      }
          	      
		 //前段加密
		 var publicKey = RSAUtils.getKeyPair(exponent, '', modulus);
		var pwd = RSAUtils.encryptedString(publicKey, $pwd);
		
		$.post(
				"/member/updatepaypwd.htm",
				{authcode:$authcode,pin:pwd}
				,function(result){
					if(result.ret == "success"){
						if(t == 'no'){
							mui.toast('积分支付密码修改成功',{ duration:'1000', type:'div' });
							window.location="/jfmall/index.htm";
						}else{
							mui.toast('积分支付密码修改成功',{ duration:'1000', type:'div' });
							var temp = document.createElement("form");        
						    temp.action = "/cashier/topay.htm";        
						    temp.method = "post";        
						    temp.style.display = "none";        
						    var opt = document.createElement("input");        
						    opt.name = "t";        
						    opt.value = t;        
						    temp.appendChild(opt);        
						    document.body.appendChild(temp);        
						    temp.submit();  
						}
					}else{
						mui.toast(result.msg,{ duration:'1000', type:'div' });
					}	
			});
		
	});
	
	//设置支付密码
	$("#addPayPwd").click(function(){
		var $authcode = $("#authcode").val();
		var $pwd = $("#pin").val();
		var $confirpin = $("#confirpin").val();
		var $t = $("#t").val();
		var exponent = $("#exponent").val();
		var modulus = $("#module").val();
		var ret =/^\d{6}$/;
		
		 if(ret.test($pwd)){
	      }else{
	    	mui.toast('请输入6位数字积分支付密码',{ duration:'1000', type:'div' });
	        return;
	      }
	 	 var ret2=/^\d{4}$/;
	 	 if(ret2.test($authcode)){
	 	 }else{
	 		mui.toast('请输入4位数字验证码',{ duration:'1000', type:'div' });
	 		return;
	 	 }
	 	 if($confirpin != $pwd){
	 		 mui.toast('两次支付密码输入不一致',{ duration:'1', type:'div' });
	 		 return;
	 	 }
	 	 
		 //前段加密
		 var publicKey = RSAUtils.getKeyPair(exponent, '', modulus);
		var pwd = RSAUtils.encryptedString(publicKey, $pwd);
		
		$.post(
				"/member/updatepaypwd.htm",
				{authcode:$authcode,pin:pwd}
				,function(result){
					if(result.ret == "success"){
						mui.toast('积分支付密码设置成功',{ duration:'1000', type:'div' });
						var temp = document.createElement("form");        
					    temp.action = "/cashier/topay.htm";        
					    temp.method = "post";        
					    temp.style.display = "none";        
					    var opt = document.createElement("input");        
					    opt.name = "t";        
					    opt.value = $t;        
					    temp.appendChild(opt);        
					    document.body.appendChild(temp);        
					    temp.submit();  
					}else{
						mui.toast(result.msg,{ duration:'1000', type:'div' });
					}	
			});
		
	});
	
});

//支付页面
function topay(t){
	var temp = document.createElement("form");        
    temp.action = "/cashier/topay.htm";        
    temp.method = "post";        
    temp.style.display = "none";        
    var opt = document.createElement("input");        
    opt.name = "t";        
    opt.value = t;        
    temp.appendChild(opt);        
    document.body.appendChild(temp);        
    temp.submit();  
}

//订单详情页面
function torderdetail(trace,goodstype){
	var temp = document.createElement("form");        
    temp.action = "/order/detail.htm";        
    temp.method = "post";        
    temp.style.display = "none";        
    var opt = document.createElement("input");        
    opt.name = "trace";        
    opt.value = trace;     
    var opt1 = document.createElement("input");   
    opt1.name = "goodstype";        
    opt1.value = goodstype; 
    temp.appendChild(opt);    
    temp.appendChild(opt1); 
    document.body.appendChild(temp);        
    temp.submit();  
}

//物流详情页
function todelivery(expressid, expressno, expressname, centerpic, trace){
	var temp = document.createElement("form");        
    temp.action = "/order/delivery.htm";        
    temp.method = "post";        
    temp.style.display = "none";        
    var opt = document.createElement("input");   
    var opt1 = document.createElement("input"); 
    var opt2 = document.createElement("input"); 
    var opt3 = document.createElement("input"); 
    var opt4 = document.createElement("input"); 
    opt.name = "expressid";        
    opt.value = expressid; 
    opt1.name = "expressno";        
    opt1.value = expressno;  
    opt2.name = "expressname";        
    opt2.value = expressname;  
    opt3.name = "centerpic";        
    opt3.value = centerpic;  
    opt4.name = "trace";        
    opt4.value = trace;  
    temp.appendChild(opt);   
    temp.appendChild(opt1);  
    temp.appendChild(opt2);  
    temp.appendChild(opt3); 
    temp.appendChild(opt4);
    document.body.appendChild(temp);        
    temp.submit();  
}

function toPwd(object){
	var style = window.getComputedStyle(object);
    if(style.webkitTextSecurity){
        //do nothing
    }else{
    	object.setAttribute("type","password");
    }
}