package com.assocaition.management.module.pay.dao;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.assocaition.management.module.pay.entity.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "202100011766370719980109";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key ="DJHMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCjEiy8pSOakgcRutZfKqz3mp3R4La/D2STiyorQ8/jCrcmPxUYaUFjLcWekGiZrYnZW9sX9enGY7pv6Sj4iUALNg5Vq+kvXKXBa/zyeZwEQZHHjbtSExha3CfDd2Fq6lGrv0JX0/2H3fj/uBliY6B8v6goCUn0VE7VXVYhTsYkVLXxzDY3aVP7loW/fIsMloUDjSihU+/KH06LrcXpw4/TF4NB11DYvtEkZcfV08zm89qo4Q12Cs44tMkuSp1+LexUZ3exlFLrqWhg/iowX0gMASRp0y8y1VmH8Mhyxi7DgwfjY3CAKh3zEMTzsSxX/2GM+aRMzap5RTRc9LN8UD+xAgMBAAECggEACe2TV7DeKAz37TZo0sZwXWrGbP63YY54Wxs/5/5q67G9UNDKYgAWgQlKXMsB80lDeT5EaoMIRpHPyfxyCB+jDIuv97tNqRqE5O/EKKcKvp+XVoTkgKy08OMycI4WGkJF0qUETwCTEPCMtlTpBrp2DOJGRJm8LOntGsRh+AkpuxBsB7l+MrNH15q8ztgXzkCqVhkSrpzEErVGR07EKDCRzSSdWOai3Q82mkWNZmr6frnt+U7NAZyPOwmCYmCM4NXgCpnKVGcasALUOmm0ieId76gfiPgXRZ7zPQOtzUQ9kd+A4ZXwwzVECyN2urGMQqcIxRK62U2rL/vgwdBdsQpV+QKBgQDv/PYOqcCEPT5YPwzwGSIL1QVqYLHv9nzS60+CIYtICUNG2kpyuNk7aSH4zmhnPW4i9GHdYKAEbaGJ7A5U2ymxP0cSkn/K9HN+pxTjntV78B4XVb0Nk9kttQk6Bp1YlX8WIJ3r9eUUIZIw3awIfs/CdqASqnOQ0rownZS/oCizWwKBgQCt83SiuprrBOI5dl5/NibNLtMlUHOhrEg1yFZX3fzeU79Jx+uVYuAWuhmcuqjN4KxMF8R1/fZY5ZcR1yftsl3e6ut3llOYUbUxHwhEq4ua+gGkrv5gY24Qn0K9dKA/6Kc/lOiG546dO6W5i6wnCyXn93Tn/fCWyR8Mnxgf6X+C4wKBgBE8huOot4YH7AlkheZct8BHzXrFdSI5/t8bPXuoMxu6ZpWZKg2xTxDgTStUj9USaW2jRcAjp3qJ8Tc5uBHKJ+VuGQmxcfI+umjXUT3D86tSJKciZlCqVMfen8oGHZj2K8mIEC+ZWpmmi5xs32VAzmU2JesRSPmW2P6S4tMciq/PAoGBAIjrRVZFLWVp3as/OZhGBnqgrcAEOE/8bIYw342gBMb3Am1PuGZIgnxBFaHg5rfObbAjURdSxBIq/SMkyJ/3Mp4hKSBjKf+N6OH2PCrZIDTnLl3zA/TYrqzUvtKjNvO269ZHHlP5da7bYVHP17yPsNGKXPGyVypIunDT1UhhIWXjAoGBAL0yDlKmIqJKfqwRfnCWyEVxTmWIXb74+erDovlposGMsYOeNGvr6mO3ewrycTNDgVqPNXIPHweTAxG3AtdKUUL7T9IwMJzohodTzUf5dn4jSNXJEIgV1CHF4xsNfm2LQnEErF04/QYAwKjynXmtDuO3VMO8SlrwZDIA/qW9IYAZ";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "DJHMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA15B4I64hO4GwHKaHTyDV/lHsrnLrHM9kTQm49Lk0SnaqqYS/T9KNy313aGiDF0NFA85Thuf4M0YiYjhszrObLXMAvHjJRjui9T/TDohsUyJwHeAcb4PiSluFbFClRsUc6KIgrXLuT/U2wtMla671nafvuDWa6dh3H7ZFYq6CmaeE0hZay9IziLDNbqrzCO4pI8bP+p/t+M8c5MDDcTyOPXBB+4BK5LINpl0IUtiJ3GuEa2er4CpExkkTn5/Tt6L7xZ3SXO3UzalsBk8ceC1SqnAMYlbBvRImSEBuTGAYd4tR/IBf9uOStO96R/QuxYqp/uu7sndDJRqRREpZUWdAJwIDAQAB";
            // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url = "http://tejidtmllr.bjhttp.cn/payed/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url = "http://127.0.0.1:7080/fassociation/paySuccess.do";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    // 自动关单时间
    private String timeout = "15m";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        // 30分钟内不付款就会自动关单
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\"" + timeout + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        return result;
    }
}
