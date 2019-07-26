package com.hetao.demo.wechatpay;

import com.hetao.demo.entity.WechatPayRecord;
import com.hetao.demo.service.WechatPayRecordService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Api(value = "PC微信接口")
@RestController
@RequestMapping("/sprint2019/pc-wx")
public class PayController {


    @Resource
    private WechatPayRecordService wechatPayRecordService;




    @RequestMapping(value = "/wxPay", method = RequestMethod.POST)
    public  Map<String, Object> wxPay(HttpServletRequest request, String openId, String phoneNum, String totalFee, String productType, Integer dayOrder, Integer orderType) {
        try {
            //生成的随机字符串
            String nonce_str = getRandomStringByLength(32);
            //商品名称
            String goodsName = "30天冲刺活动";

            //订单编号
            String orderNo = getOrderNo();

            //获取客户端的ip地址
            String spbill_create_ip = getIpAddr(request);
            //组装参数，用户生成统一下单接口的签名
            Map<String, String> packageParams = new HashMap<>();
            packageParams.put("appid", WechatConfig.appid);
            packageParams.put("mch_id", WechatConfig.mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", goodsName);
            packageParams.put("out_trade_no", orderNo + "");//商户订单号,自己的订单ID
            packageParams.put("total_fee", Double.parseDouble(totalFee)*100 + "");//支付金额，这边需要转成字符串类型，否则后面的签名会失败,单位 分
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", WechatConfig.notify_url);//支付成功后的回调地址
            packageParams.put("trade_type", WechatConfig.TRADETYPE);//支付方式
            packageParams.put("openid", openId + "");//用户的openID，自己获取

            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, WechatConfig.key, "utf-8").toUpperCase();

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + WechatConfig.appid + "</appid>"
                    + "<body><![CDATA[" + goodsName + "]]></body>"
                    + "<mch_id>" + WechatConfig.mch_id + "</mch_id>"
                    + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + WechatConfig.notify_url + "</notify_url>"
                    + "<openid>" + openId + "</openid>"
                    + "<out_trade_no>" + orderNo + "</out_trade_no>"
                    + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + totalFee + "</total_fee>"
                    + "<trade_type>" + WechatConfig.TRADETYPE + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";

            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(WechatConfig.pay_url, "POST", xml);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码
            String result_code = (String) map.get("result_code");//返回状态码

            Map<String, Object> responseMap = new HashMap<String, Object>();//返回给小程序端需要的参数
            if (return_code == "SUCCESS" && return_code.equals(result_code)) {
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                responseMap.put("nonceStr", nonce_str);
                responseMap.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                responseMap.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                //拼接签名需要的参数
                String stringSignTemp = "appId=" + WechatConfig.appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, WechatConfig.key, "utf-8").toUpperCase();
                responseMap.put("paySign", paySign);

                //生成订单入库
                WechatPayRecord wechatPayRecord = new WechatPayRecord();
                wechatPayRecord.setOrderNo(orderNo);
                wechatPayRecord.setPhoneNum(phoneNum);
                wechatPayRecord.setProductType(productType);
                wechatPayRecord.setDayOrder(dayOrder);
                wechatPayRecord.setCreateTime(new Date());
                wechatPayRecord.setStatus(1);
                wechatPayRecord.setOrderType(orderType);
                wechatPayRecord.setPrice(Double.parseDouble(totalFee));
                this.wechatPayRecordService.insertOrder(wechatPayRecord);
            }
            responseMap.put("appid", WechatConfig.appid);

            return responseMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //这里是支付回调接口，微信支付成功后会自动调用
    @RequestMapping(value = "/wxNotify", method = RequestMethod.POST)
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        Map map = PayUtil.doXMLParse(notityXml);

        String resXml = "";

        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equals(returnCode)) {
            //验证签名是否正确
            Map<String, String> validParams = PayUtil.paraFilter(map);  //回调验签时需要去除sign和空值参数
            String prestr = PayUtil.createLinkString(validParams);
            //根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
            if (PayUtil.verify(prestr, (String) map.get("sign"), WechatConfig.key, "utf-8")) {
                /**此处添加自己的业务逻辑代码start**/
                String orderNo = (String) map.get("out_trade_no");
                double totalFee = ((double)map.get("total_fee"))/100;
                //根据orderNo和totalFee更新订单状态
                this.wechatPayRecordService.updateOrderStatus(orderNo,totalFee);
                /**此处添加自己的业务逻辑代码end**/
                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }

        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    /*  订单查询函数 。有待细化
    @ResponseBody
        @RequestMapping("orderQuery")
        public OrderQueryResult orderQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
            OrderQueryResult orderQueryResult = null;
            OrderQueryParams orderQueryParams = new OrderQueryParams();
            orderQueryParams.setAppid(WechatConfig.APP_ID);
            orderQueryParams.setMch_id(WechatConfig.MCH_ID);
            orderQueryParams.setNonce_str(PayUtil.createNonceStr());
            orderQueryParams.setTransaction_id(""); //二者选其一，推荐transaction_id
            //orderQueryParams.setOut_trade_no("");
            //请求的xml
            String orderQueryXml = wechatPayService.abstractPayToXml(orderQueryParams);//签名合并到service
            // 返回<![CDATA[SUCCESS]]>格式的XML
            String orderQueryResultXmL = HttpReqUtil.HttpsDefaultExecute(HttpReqUtil.POST_METHOD,WechatConfig.ORDER_QUERY_URL, null, orderQueryXml);
            // 进行签名校验
            if (SignatureUtil.checkIsSignValidFromWeiXin(orderQueryResultXmL)) {
                orderQueryResult = XmlUtil.getObjectFromXML(orderQueryResultXmL, OrderQueryResult.class);
            }
            return orderQueryResult;
        }
    */

    // 获取订单id 规则：当前日期_uuid(17位，总共32位) 支付宝64位。
    public String getOrderNo(){
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+"_"+getRandomStringByLength(16);
    }

    //获取随机字符串
    private String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //获取IP
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    private void createOrder(){

    }



}

