package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

//	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
//	public static String app_id = "2016102900776453";
//	
//	// 商户私钥，您的PKCS8格式RSA2私钥
//    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0C9Jb9jt1eegdNTSgmGJ3vGmXCOyyWgxkrhtey0UiYLx195fyKY+lin9Zzl/HFgOTFe5nNO0gukDyPQVPE9eCaWVTuJ5rTaPSmUtfa3eUYfnpWNfg/cojrWnHKUPgQkWzYbhsV1XIMepxfoxo9y2KZJLKaAkAX0Yofmgnr9Hwfymp2yBNFFnR4D565URKz+slln2hd7MtQ6X5nWuKEwz5nc1XDQfN4/NqTKvmUXfAFfGhxFE72A6Kv+KW+J3BVblPjt83vS0MsodVLhnWsL/umgr/8M4AETMLoVRGXe5zhYghN55cuUOw5Tx51if+w2vfkPLsGvlGINKZAo5Wm9o7AgMBAAECggEBAIWxIYrX1F7vHsRRdbqqbLOsXFuibOAirYE1J23EUpmsa0TNjS5sXhX2OYpeDBCw8AaQCm7ohgv/QaqeVQrbGPPlfzAjrr7O8A65dZZtHmY4OnJjXinbSfYkFp+krJohm9APh/oq/6+gIQ4Vgf2PTWmuNLHHfqegF2CKPJz7mVCSXW5h31J6pP6AOSpl3npKIlQl768aPn1i1vKz69VxOba+Zd7QUPa5i249Lqc2Yek73/cn0vakUrd3eLS3K08rFd5ybR82xvijgfPBDNMgdrtnMjRrvhedX9kZU+hk5+A0WQQxSCiGIELup4Xre8CrLceNPhdZPKinhFLBoOaEtYECgYEA15w7SUoPk503GuHawsFCepTcNEXCIQDgc+C44L+SnUkWzK9PMwkR3PIfbfe1MG4MElNnsXJH5N+ZvPQsHn7zv7NsoJ3QxMz0B492XRbBoZJFVVuEsO5QwS0lIh4fAU98GezW/T/IScwLDgMnyx7Io6CaH8Z2DtpH+pmr1EDqxEkCgYEA1cYVmrlRfz7GjtC5UgXoslo6E7dRK4f/mdk0LHj1GVUTwD0RHhXHLTI6pCHXx7jnm/R4bk3Wpavv5jFMLmx5xLOdiIj8KAf4GWJ3eKKSxoKN9QCj54Ur2OZkDh+yyJWkjCp5uxydGP0kBuFOsiiJ9jrdtnaeSZB7DaSoTzs+YmMCgYEAz9lyZcKZpYnnd2jHBPxdY+JhLZsZ+8rmlY7Law4C/tFYw1U1GlPh/ocCuXYUG23bAUeaLLuohwDvKfKRHYzw2ZrUWzoH6dBSZ39rcW3gmCtz30dscUwsWS12IJ+87/x5/FwPHPAB2vJyrb1o/Szd6q+GJJ+ja8oNnY3K0cELENECgYBGertOusCSyjieh1RCJa6VdsflDjBVwM3VhFX+P5bXVkRfwgJtv9JeJUxOJtZEg8LgmRZHj6QwFOLQKvqp55ux7WIHzNoNvKrge6VWAYuQpSR2XVKslyxgo3zjZzIDjcqlZ6+gsAtfXL5PAmN2u4TYyImmia8gI+FeGyZekPK9bwKBgGtpxJPatAW79xFtoRGWfvo+doAJ0E/1QomEnTNVUie0/sVy+atCE/LOObX5birhJwlE/cXtakXKaRgiYm+L38SWqHKq6tK4oPVyEC6VOBLsq+Crf4nvn6br/yPDfy8N28J+5RWtm3bOgdZsXGre3juSYO4YV68n3abI+OjKyTxB";
//	
//	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
//    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAytsoyQ7HLDF7c57r3lHqJ35WJXLhbnduo0x+eLYA+zCyw61B2owVpF5h4bHUqsQwW+z1TcDhBxNLVE+fAJswwQMcBQzaacdAUr5F0o1kyBkAiFVaf+F4/LkrTRmvbVEQoqRcTFpx3BlId4o8HOvG+pv1V/HuKgeZl7lfuKtOfXuWZshsYmZseWpZsJrO8MEXId7C38lzmjvLnvfmRs77urqp+yBDiiRpcBBSKNyVIl2IEqzlCjKELBEW9Ru4R4bN2tfeoM1mQO1XNpIGMxMQ6qv8gzrkGGdn2P4A+aBYgU/8HjNIO4A+JWLo6yNGr5q4NYIGn0kInkXhp0+XLatV9QIDAQAB";


	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016102900777340";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDBfothJvmL4txPgtU9lePt8QZJ9ug9+l6POzwlC81nBh+FUpAFEdWD267vZOV/pBeLuQ/RTmMYStYY4T59LhC7KrurUsM/cdxuqDhx8RCGbTWRMQLPldhsbcMFfgnO0I0PdsJ/NcMru3I6MSVRLxIrWmsmmXWedTd0Zwgy3II2u3bC8vMLvSuP/Ggok3ooHSwwxJBseb8IrlHvyghX8aVV+Rf/ZjoBOnaGialAM/QjO42YleLr0RHtCDgWs0iK73EEkAptO/AyC5h54bZ/zju5KxhH2wKHsJRDC5+MzqWGIravRwUn0hWJi2Japuqw33STaXbWN+bTBBfmVXusCisTAgMBAAECggEAFH7xCKN2P4kSSSHUsKdV83hacC9FPnts1MMzhcEQSt9tK1QpiT/wW/1DNHx9it242MYLlOI2wPX8Y7pIQj5E626FXopvwCKxdM9A22DrnTGoQO7uG0sGF8YwqD5Hj66ron2z8IGjYNiEKwHVsBnKVOlI/Fe5C8Xiz+OyDKEq8d0OPlweeRy272d6IX2Klelw3prSngSIzvDP/uLr58lBBs2uZw8AxGqNXyQUWVX9lMrJFiQe9l2GIC1MILZnjOcm1YpT0KW5F9TMK4tT4hoBQ2jNB24/yC3n8EheGABsVOtT3DMIL4DZmSBy4GNYL/lE7v4SltRcK8/vsRFmzrIL8QKBgQD1+9FL/3csc0dAPByv5ojTWEtWAnA0TU+OtcjzAf7M/L3Bw7X3Cjm/5e8SRND3tuqLnllhy9yTxinq/aaSKQEZ9U5FozCBAuKGgldDuLyekU7f1urRXXAJE5nFc5rkY++yS9xRJ8rz1GbSr3AfjkkeFXlLcN7B8EdKYoPl0g8fXQKBgQDJX5FJCjz/LneWqp0jqofHsjTjDvt/imlZQNRb90eO9hSSULr2WxysiM00LFjGL2HT/p33aILR36SwJiiCCvL/C6yDs8m6pqVYdpBLyGaHMtjiqCIBSG3TA8Vmu8+CsDAvjUaybnkdW2HspEXkT3Mfrt+p/4hiKzvTTfAfxI99LwKBgQDV3b83KEHhL8k/HASFGxbTSe0WNsB/eMg29+5i+dUjPItvME3BH+7d3XhildppxWtfQKnN0YVSJfTEDw0H/waqHxGSkjPVt0BkWmasxq2X4BaHn0BCD88c1SHY/o4pDIqWpkZDeSmHnaBYhZgil38S3PC8msHSNKqHDehHpL7pMQKBgQC5s+USW1u25A4qpno1SuzeTswQ3F831dejFHMBwH+AKhT8fXB9AbbmsV2zkHXphgaMbpEUeI+zpa+FNhtDGX/Pjd9wsRsGgumWM/iLumo6obx7ARwi3L1SzHZ9cBNEbOBy6bDZmaEdXolmpnNBwbbj7+uZJOqLxVztV0FqumSsxwKBgAH37O1dBAClM3290xD1ZE60d2QkWpoJqQwWMLpyFDNmge53lWbI7x9QUwpcxKqwmz5RwwgRPjeo6hLyy/MTzgey61Av7Td7je7rvu+NoaAw1Q/2tOmdHoRb+uztWVKOEJ01Al4d8TANgenLHaeFYE34weHI1QLOYe/Sr0JArzfL";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwX6LYSb5i+LcT4LVPZXj7fEGSfboPfpejzs8JQvNZwYfhVKQBRHVg9uu72Tlf6QXi7kP0U5jGErWGOE+fS4Quyq7q1LDP3Hcbqg4cfEQhm01kTECz5XYbG3DBX4JztCND3bCfzXDK7tyOjElUS8SK1prJpl1nnU3dGcIMtyCNrt2wvLzC70rj/xoKJN6KB0sMMSQbHm/CK5R78oIV/GlVfkX/2Y6ATp2hompQDP0IzuNmJXi69ER7Qg4FrNIiu9xBJAKbTvwMguYeeG2f847uSsYR9sCh7CUQwufjM6lhiK2r0cFJ9IViYtiWqbqsN90k2l21jfm0wQX5lV7rAorEwIDAQAB";

	
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/A83-ZM-S2/notify_url.jsp";
//http://localhost:8080/A83-ZM-S2/index.jsp
	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/A83-ZM-S2/07-大麦商城/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关   https://openapi.alipaydev.com/gateway.do
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

