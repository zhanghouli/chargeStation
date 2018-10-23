package com.jopool.chargingStation.www.base.pay.alipayNew.config;

public class AlipayConfig {
    // 商户appid
    public static String APPID             = "2017111509943327";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY   = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCSlccOwr8eukCjRs8EamhWXHgrtXRW5/xoGlJmO+404l0tAhJp4+ZyY6MGttGJqrHTeT6WuBgrRIVCYEZ/E/mYjFsz3sF4kFo1qId6zRSJjw6dyL3H1cokoI9rErANZ2MkCsDdClk63GkuZCQi8n0+j57mPEZzJtiyTxvhVQaV04rdksdGq07v40PYhb7XViagh3JqaHxxSmeyeUN5O57jfNvqwNiv0n8Bs1CaqsCvp7+VE6tHV6VtR70IXbl5/mp7/ynELRgmrVwIU5oKqelfZjfmnkGudeDgdifHE18tI3VRnpKLgPXYYwS0NvrazKMrd5EmJPma47/2AMn8K0j5AgMBAAECggEBAIHL7FIusasUw02Q0piXXEI0aRhS+WxGySKpRBNQt/HOCzFiZ0mqSpE5ls7ILSyYoBOghYJSCtytNM58j4/Q8S9aARwh//Cw6aAf1a8wTyIrMYcXjKyZqv/ZyITILGoCBTCR8QsJMc/EAcLZW4IydCJkbBB0iPFIEZN6JzbU4hrht3ufwftowhzC7DkxY2kLDlx4YXYqyOb4uY35I7JGqjnM9EPtCUZC/jyJeX9GAZgIP+ui+49ECulQXFj5bazoDA2eohZgdAE8AYl53X6hKC41IjcRfKwPH4w/e+GIIdbz2d+7RgW3Rx07xmh2WbCTqHyH9F4t4ptSiMNqnb777XECgYEA2/XpfB8FgdN3FOKUpZBADXFFRhavjXSwj0BGAWPDxBw2Lc7Zi3+y7LRq8FwoOuEAaa0iIc/Ut5oc3FfYGNwHuwgYD9wx0HRXWG08jPh8H9tTZ4jX/eAU18pfGRuB8HCSkhw719i3ih6ZHh/uQoBDRM5HWlrpClIYavmx28egyqUCgYEAqpoupOjBRfkmCqv2t1wMIrThytu+VlWhOTd3vQG1MGFZXd2SsZdHdj9KecP0G+EzNGo30I94wWVO7e+Di/hVRWhghdhal4OcKm3BoM7LtEOxDf5AJShTH6Gpz9wvvDtdfr1swxEiAfERoLf8o80VF3rxB40dluH3rgRzPYQKeMUCgYAeHNBWGbkbSOPojAxljhX2wScGrVxjNTJmqhnrXskSM/qG0uadXmIUXa62MSegx+TSks01a8pnSEXjNHN1E8pLJJSGt1Jqg0Lh7DJ56hq0EcadSfKn4uF3mVDjkwx66i5mqk7XAeg+lGnJ4f0wRXbwGH1UNVXp/8sv+fcLa4zrsQKBgGvRKS1bU9ovDVWtPCzBZYIBorkbivzw/QU2AtxWWfYLb/kccPr/SlB2y1CNAJcmCcBUaJXMNlqy9eD1f8NT3EATKP0tsIMUz/sYhkhkqORx61GWn19cTrws5S7STvC86+ePO09EYqOrgKJ75JR4pZ3DOtS04EBc6rT1Zwc3jY6pAoGBAMcy9ixKwnk78xhHM+z7QQNjeYTmF0NDnYJv781DysLyh0IfODUJsjpy86aOZUTYdlfR7kBRvpMaJHiAs0bjTsgWnAloykWWF4q9BAk+sGRMI6Zz9wOFawIU6PGtzUgeMRDfvKWMviAzawel7Cz3FMCWCmsTWKQlZnbtaF1lRuyg";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url        = "http://api.h1d.com.cn/api/common/pay/doAlipay.htm";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url        = "";
    // 请求网关地址
    public static String URL               = "https://openapi.alipay.com/gateway.do";
    // 编码
    public static String CHARSET           = "UTF-8";
    // 返回格式
    public static String FORMAT            = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlDRtyBwzhpdcB1/0WwsVlJxhSKnoGadPbUW7NDiF3osFDFUcVfdP8JmVUiHGd9yQ3lSVZjHw+HdtMwpzE7FgwyStlE5UVc9tnCO2AnGwTfpWyYxDNolDN23ErXPisS6qSoti9z49bCfUvzfrheoJBQuZUPEDYbZ7TPuoMPqTaK9u2Lo3cRiVWwQYIhvOa+9TdugaFH8IffSSluPIjSJoEyqkXpdC1e8ddH23hObW6H6aF3OTgd1Z9r4xtGJGm6G5RDt7izy29KkNTF8ZsGgfE9Te0WTvo4hkr7y741PkOtGa16CDx4fDWSguNJq31eDpcaehIE+LEDtW5qFBNWiUAQIDAQAB";
    // 日志记录目录
    public static String log_path          = "/log";
    // RSA2
    public static String SIGNTYPE          = "RSA2";
}
