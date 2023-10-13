package com.wlwq.common.utils;

import com.aliyuncs.utils.Base64Helper;

import javax.net.ssl.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Locale;

/**
 * 苹果IAP内购验证工具类
 */
public class IosVerifyUtil {
	private static class TrustAnyTrustManager implements X509TrustManager{

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return new X509Certificate[] {};
		}
	}
	
	private static class TrustAnyHostnameVerifier implements HostnameVerifier{

		@Override
		public boolean verify(String arg0, SSLSession arg1) {
			// TODO Auto-generated method stub
			return true;
		}
	}
	
	private static final String url_sandbox = "https://sandbox.itunes.apple.com/verifyReceipt";  
    private static final String url_verify = "https://buy.itunes.apple.com/verifyReceipt";
    
    /** 
     * 苹果服务器验证 
     *  
     * @param receipt 账单要验证的地址  
     * @url 
     * @return null 或返回结果 沙盒 
     *  
     */ 
    public static String buyAppVerify(String receipt,int type) {  
    	//环境判断 线上/开发环境用不同的请求链接  
    	String url = "";
    	if(type==0){
    		url = url_sandbox; //沙盒测试
    	}else{
    		url = url_verify; //线上测试
    	}
        try {  
            SSLContext sc = SSLContext.getInstance("SSL");  
            sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());  
            URL console = new URL(url);  
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();  
            conn.setSSLSocketFactory(sc.getSocketFactory());  
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("content-type", "text/json");  
            conn.setRequestProperty("Proxy-Connection", "Keep-Alive");  
            conn.setDoInput(true);  
            conn.setDoOutput(true);  
            BufferedOutputStream hurlBufOus = new BufferedOutputStream(conn.getOutputStream());  
  
            String str = String.format(Locale.CHINA, "{\"receipt-data\":\"" + receipt + "\"}");//拼成固定的格式传给平台
            hurlBufOus.write(str.getBytes());  
            hurlBufOus.flush();  
  
            InputStream is = conn.getInputStream();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));  
            String line = null;  
            StringBuffer sb = new StringBuffer();  
            while ((line = reader.readLine()) != null) {  
                sb.append(line);  
            }
            return sb.toString();  
        } catch (Exception ex) {  
        	System.out.println("苹果服务器异常");
            ex.printStackTrace();  
        }  
        return null;
    }
    
    /** 
     * 用BASE64加密 
     *  
     * @param str 
     * @return 
     */  
    @SuppressWarnings("static-access")
	public static String getBASE64(String str) {  
        byte[] b = str.getBytes();  
        String s = null;  
        if (b != null) {  
            s = new Base64Helper().encode(b);  
        }  
        return s;  
    }  
}
