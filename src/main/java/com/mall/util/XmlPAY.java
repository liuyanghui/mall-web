package com.mall.util;




/**
 * @author yinshu 省级手机支付服务平台标签库
 */
public class XmlPAY  {
	/*
	 * 商户中文名称
	 */
	public static String MERCNM = new String("MERCNM");
	/*
	 * 商户简称
	 */
	public static String MERCAB = new String("MERCAB");
	/*
	 * 商户返奖账户
	 */
	public static String MERAC = new String("MERAC");
	/*
	 * 商户返奖账户余额
	 */
	public static String MERBAL = new String("MERBAL");
	
	/*
	 * 文件处理类型
	 */
	public static String FILETYP = new String("FILETYP");

	/*
	 * 签名原始串
	 */
	public static String SIGNSTR = new String("SIGNSTR");
	/*
	 * 小额支付密码
	 */
	public static String PASS_WD = new String("PASSWD");
	/*
	 * 小额支付退货金额
	 */
	public static String RECV_AMT = new String("RECVAMT");
	
	/*
	 * 账务日期
	 */
	public static final String PAYDAY = new String("PAYDAY");
	/*
	 * 文件路径
	 */
	public static final String FILEDIR = new String("FILEDIR");
	/*
	 * 支付返回通知url
	 */
	public static final String CALLBACKURL = new String("CALLBACKURL");
	
	public static final String MERINFO = new String("MERINFO");
	/*
	 * 协议标识
	 */
	public static String MSGPRO = new String("MSGPRO");
	/*
	 * xpm协议名称
	 */
	public static String SF = new String("SF");
	/*
	 * xpm协议版本
	 */
	public static String XPM_VERSION = new String("0001");
	/*
	 * 请求
	 */
	public static String XPM_REQ = new String("rq");
	/*
	 * 响应
	 */
	public static String XPM_RES = new String("rp");
	/*
	 * 请求
	 */
	public static String XPM_BUSI = new String("00");
	/*
	 * 响应
	 */
	public static String XPM_MAN = new String("30");
	/*
	 * 版本号
	 */
	public static String VER = new String("VER");
	/*
	 * 省平台标识
	 */
	public static String REQSYS = new String("REQSYS");
	/*
	 * 请求响应标志 请求(rq) 响应(rp)
	 */
	public static String SCH = new String("SCH");
	/*
	 * 报文属性 业务报文(00) 管理类报文(30)
	 */
	public static String MSGATR = new String("MSGATR");
	/*
	 * 报文安全标志,待定...
	 */
	public static String SECTAG = new String("SAFEFLG");
	/*
	 * 报文MAC
	 */
	public static String MAC = new String("MAC");
	/*
	 * 报文类型
	 */
	public static String MCODE = new String("MCODE");
	/*
	 * 系统跟踪号
	 */
	public static String MID = new String("MID");
	/*
	 * 交易日期
	 */
	public static String DATE = new String("DATE");
	/*
	 * 交易时间
	 */
	public static String TIME = new String("TIME");
	/*
	 * 返回码
	 */
	public static String RCODE = new String("RCODE");
	/*
	 * 返回描述
	 */
	public static String DESC = new String("DESC");
	/*
	 * 手机号
	 */
	public static String MOBILEID = new String("MOBILEID");
	/*
	 * 协议号
	 */
	public static String UID = new String("UID");
	/*
	 * 金融机构代码
	 */
	public static String BANK_ID = new String("BANKID");
	/*
	 * 货币类型
	 */
	public static String CTYPE = new String("CTYPE");
	/*
	 * 交易金额
	 */
	public static String AMOUT = new String("AMOUT");
	/*
	 * 清算日期
	 */
	public static String BCDATE = new String("BCDATE");
	/*
	 * 原始交易信息
	 */
	public static String OPINF = new String("OPINF");
	/*
	 * 原交易流水
	 */
	public static String OMID = new String("OMID");
	/*
	 * 原交易货币类型
	 */
	public static String OCTYPE = new String("OCTYPE");
	/*
	 * 原交易金额
	 */
	public static String OAMT = new String("OAMT");
	/*
	 * 原交易日期时间
	 */
	public static String ODATE = new String("ODATE");
	/*
	 * 原交易清算日期
	 */
	public static String OBCDATE = new String("OBCDATE");
	/*
	 * 借/贷记
	 */
	public static String BAL_SIGN = new String("BALSIGN");
	/*
	 * 余额
	 */
	public static String BANLANCE = new String("BANLANCE");
	/*
	 * 别名
	 */
	public static String ALIAS = new String("ALIAS");
	/*
	 * 机构类型
	 */
	public static String AGENTYPE = new String("AGENTYPE");
	/*
	 * 机构代码
	 */
	public static String AGENID = new String("AGENID");
	/*
	 * 短信校验码
	 */
	public static String CID = new String("CID");
	/*
	 * 用户信息
	 */
	public static String USER = new String("USER");
	/*
	 * 银行处理日期
	 */
	public static String UDATE = new String("UDATE");
	/*
	 * 银行卡类型
	 */
	public static String CARD_TYPE = new String("CARDTYPE");
	/*
	 * 银行卡后六位
	 */
	public static String CARDTYCARNOPE = new String("CARNO");
	/*
	 * 客户姓名
	 */
	public static String NAME = new String("NAME");
	/*
	 * 证件类型
	 */
	public static String CERT_TYPE = new String("CERTTYPE");
	/*
	 * 证件号码
	 */
	public static String CERTNO = new String("CERTNO");
	/*
	 * 渠道类型,modified by niulei
	 */
	public static String CHANNELTYPE = new String("CHANNELTYPE");
	/*
	 * 渠道ID,modified by niulei
	 */
	public static String CHANNELID = new String("CHANNELID");
	/*
	 * 商品信息
	 */
	public static String GOODSINF = new String("GOODSINF");
	/*
	 * 商户订单号
	 */
	public static String ORDER_ID = new String("ORDERID");
	/*
	 * 原商户订单号
	 */
	public static String OORDERID = new String("OORDERID");
	/*
	 * 手机号码或别名
	 */
	public static String USERID = new String("USERID");
	/*
	 * 协议类别
	 */
	public static String AGRTYP = new String("AGRTYP");
	/*
	 * 协议编号
	 */
	public static String AGRID = new String("AGRID");
	/*
	 * 冻结编号
	 */
	public static String FROZENNO = new String("FROZENNO");
	/*
	 * 商户请求号
	 */
	public static String REQUESTID = new String("REQUESTID");
	/*
	 * 是否允许评论
	 */
	public static String ALLOWNOTE = new String("ALLOWNOTE");
	/*
	 * 推荐用户进行确认的方式
	 */
	public static String AUTHORIZEMODE = new String("AUTHORIZEMODE");
	/*
	 * 币种
	 */
	public static String CURRENCY = new String("CURRENCY");
	/*
	 * 送货标志
	 */
	public static String DELIVERFLAG = new String("DELIVERFLAG");
	/*
	 * 发票标志
	 */
	public static String INVOICEFLAG = new String("INVOICEFLAG");
	/*
	 * 订单日期
	 */
	public static String ORDER_DATE = new String("ORDERDATE");
	/*
	 * 有效期数量
	 */
	public static String PERIOD = new String("PERIOD");
	/*
	 * 产品描述
	 */
	public static String PRODUCTDESC = new String("PRODUCTDESC");
	/*
	 * 产品编号
	 */
	public static String PRODUCTID = new String("PRODUCTID");
	/*
	 * 产品名称
	 */
	public static String PRODUCTNAME = new String("PRODUCTNAME");
	/*
	 * 交易类型
	 */
	public static String TXNTYP = new String("TXNTYP");
	/*
	 * 交易方式
	 */
	public static String TXNMOD = new String("TXNMOD");
	/*
	 * 预授权编号
	 */
	public static String AUTHORIZENO = new String("AUTHORIZENO");
	/*
	 * 交易发起方式1：用户或系统发起2：商户主动发起
	 */
	public static String TXNTCH = new String("TXNTCH");
	/*
	 * 下订类型N：正常下订 F：找回订单
	 */
	public static String ORDERTYPE = new String("ORDERTYPE");
	/*
	 * 免验密标志Y：免验密 N: 需要验密码
	 */
	public static String CHKPSWFLG = new String("CHKPSWFLG");
	/*
	 * 支付密码
	 */
	public static String PAYPSW = new String("PAYPSW");
	/*
	 * 退款金额类型 901：可提现金额 902：不可提现金额 3位
	 */
	public static String CNYTYP = new String("CNYTYP");
	/*
	 * 银行卡后六位
	 */
	public static String CARNO = new String("CARNO");
	/*
	 * 商户号
	 */
	public static String MER_ID = new String("MERID");
	/*
	 * 协议类别
	 */
	public static String BUSTYP = new String("BUSTYP");
	/*
	 * 通知手机号
	 */
	public static String NOTICEMOB = new String("NOTICEMOB");
	/*
	 * 通知EMAIL
	 */
	public static String NOTICEEMAIL = new String("NOTICEEMAIL");
	/*
	 * 消费确认金额
	 */
	public static String CONAMT = new String("CONAMT");
	/*
	 * 生效状态
	 */
	public static String STATUS = new String("STATUS");
	/*
	 * 备注
	 */
	public static String REMARK = new String("REMARK");
	/*
	 * 生效标志
	 */
	public static String VALIDFLAG = new String("VALIDFLAG");
	/*
	 * 生效日期
	 */
	public static String EFFDATE = new String("EFFDATE");
	/*
	 * 失效日期
	 */
	public static String EXPDATE = new String("EXPDATE");
	/*
	 * 交易代码
	 */
	public static String TRANSCODE = new String("TRANSCODE");
	/*
	 * 错误代码
	 */
	public static String ERRCODE = new String("ERRCODE");
	/*
	 * 进程标识
	 */
	public static String TPID = new String("TPID");
	/*
	 * 参数名称
	 */
	public static String TPNAME = new String("TPNAME");
	/*
	 * CA
	 */
	public static String CA = new String("CA");
	/*
	 * 有效期单位
	 */
	public static String PERIODUNIT = new String("PERIODUNIT");
	/*
	 * 完成标志
	 */
	public static String COMPFLAG = new String("COMPFLAG");
	/*
	 * 扣款编号
	 */
	public static String MUSRID = new String("MUSRID");
	/*
	 * 回调url
	 */
	public static String CALLBACK = new String("CALLBACK");
	/*
	 * 单笔限额
	 */
	public static String PERLIMIT = new String("PERLIMIT");
	/*
	 * 日限额
	 */
	public static String DAYLIMIT = new String("DAYLIMIT");
	/*
	 * 月限额
	 */
	public static String MONTHLIMIT = new String("MONTHLIMIT");
	/*
	 * 提示文字
	 */
	public static String TIPMSG = new String("TIPMSG");
	/*
	 * 用户确认标志
	 */
	public static String CONFIRMFLG = new String("CONFIRMFLG");
	/*
	 * 返奖文件
	 */
	public static String FILE_NAME = new String("FILENAME");
	/*
	 * 返奖文件记录总数
	 */
	public static String RECSUM = new String("RECSUM");
	/*
	 * 中心批次号
	 */
	public static String CENTERREQUESTID = new String("CENTERREQUESTID");
	/*
	 * 令牌
	 */
	public static String TOKEN = new String("TOKEN");
	/*
	 * 下订日期
	 */
	public static String ORDDATE = new String("ORDDATE");
	/*
	 * 下订时间
	 */
	public static String ORDTIME = new String("ORDTIME");
	/*
	 * 消费金额
	 */
	public static String ORDAMT = new String("ORDAMT");
	/*
	 * 订单状态
	 */
	public static String ORDSTS = new String("ORDSTS");
	/*
	 * 支付类型
	 */
	public static String PAYTYP = new String("PAYTYP");
	/*
	 * 订单失效日期
	 */
	public static String ORDEXPDAT = new String("ORDEXPDAT");
	/*
	 * 订单失效时间
	 */
	public static String ORDEXPTIM = new String("ORDEXPTIM");
	/*
	 * 订单最后记账时间
	 */
	public static String ORDACTDT = new String("ORDACTDT");
	/*
	 * 拒付原因
	 */
	public static String RJTRSN = new String("RJTRSN");
	/*
	 * 省代码
	 */
	public static String PROV_CODE = new String("PROVCODE");

	/*
	 * 工商注册号
	 */
	public static String ICREGNO = new String("ICREGNO");
	/*
	 * 商户查询类型
	 */
	public static String QRYTYP = new String("QRYTYP");
	/*
	 * 银行卡号
	 */
	public static String CARDNO = new String("CARDNO");
	/*
	 * 性别
	 */
	public static String GENDER = new String("GENDER");
	/*
	 * 邮箱地址
	 */
	public static String EMAIL = new String("EMAIL");
	/*
	 * 电话
	 */
	public static String PHONE = new String("PHONE");
	/*
	 * 邮政编码
	 */
	public static String ZIPCODE = new String("ZIPCODE");
	/*
	 * 通讯地址
	 */
	public static String ADDRESS = new String("ADDRESS");
	/*
	 * 允许绑定卡种
	 */
	public static String CARDCLASS = new String("CARDCLASS");
	/*
	 * 是否自动充值
	 */
	public static String DEALTYPE = new String("DEALTYPE");
	/*
	 * 自动充值阀值
	 */
	public static String AUTOAREA = new String("AUTOAREA");
	/*
	 * 自动充值金额
	 */
	public static String AUTOAMOUNT = new String("AUTOAMOUNT");
	/*
	 * 交易日期时间
	 */
	public static String DATE_TIME = new String("DATETIME");
	/*
	 * 备注
	 */
	public static String ME_MO = new String("MEMO");
	/*
	 * 直付卡通协议号
	 */
	public static String SIGNNO = new String("SIGNNO");
	/*
	 * 错误代码
	 */
	public static String ERRORCODE = new String("ERRORCODE");
	/*
	 * 错误信息
	 */
	public static String ERRORMESSAGE = new String("ERRORMESSAGE");
	/*
	 * 记录条数
	 */
	public static String RECNUM = new String("RECNUM");
	
	/*
	 * 操作员ID
	 */
	public static String MER_ADM_NO = new String("MER_ADM_NO");
	/*
	 * 操作员手机号
	 */
	public static String MER_ADM_MBL = new String("MER_ADM_MBL");
	
	/*
	 * 签名
	 */
	public static String SIGN = new String("SIGN");
	
	
	public static String REC = new String("REC");//直付通协议签约查询，循环报文KEY
	public static String BANKNO = new String("BANKNO"); //银行编号
	public static String CANDATE = new String("CANDATE"); //解约日期
	public static String STS = new String("STS"); //签约状态
	
	public static String ACSTATUS = new String("ACSTATUS");
	
	
	
	
	
}



