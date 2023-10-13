package com.wlwq.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @ClassName RsaUtils
 * @Description jsencrypt加解密  http://web.chacuo.net/netrsakeypair 生成秘钥
 * @Date 2021/10/13 15:24
 * @Author Rick Jen
 */
public class RsaUtils {

    // Rsa 私钥
    public static String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANoBvrt9vXxJ6BzF48TvAGq/CJb2pV1n4PqYyrVheqcsyGwlkkekGLVVZJ/Y2rCemXpLjghrEgSzgJr0G8jB51ZRlt8YDaHbUuMipsDqIJCYy/HO6V6kDHb0hgvOtH4fK9YTNvLMeVOGQoBllCr4dx+ji+o/uVIEhIHLYc/f5MUtAgMBAAECgYBH/PWHohDVHu6XubzpxeZEWYqzlwSqIGhJN/UQCf1fgMk59Pol0Akcr/MHWat1URnrKVHgc8LCbKfWAy+9jlIeMc+2zOz0NBlngZ/ibElpqLIIoQPmkVOC82+UfmW47b/p6It2MICWlaCEu3YWQbEE/TNMxK6cXSKjdvvfsnYDIQJBAO+VaH6gNPJfnGudBpigAHsObRpIV0CnDKIqgSj/rIOz/QQexop72vIYt/0m343fttQlHjjLWhuj2ENQWDQ9PYkCQQDo8dpicoKy9y9Bx8dB+N5awo3KPC0lIBFvrSdH1mYJeLowzcfr1tjU/7mfFLUjaSL3f/JXu1B6uOJ7koer9yWFAkEAiWGXReT/92uz+lFUkvhMzkN7dHK+9afUqrjMz4Gsnuj60P4Ewyw6ZFCgUF2WG3iJEiUmq1KQHQFa5V6Q9YOYYQJBALL4noDk2eYGZBh2FXFqc+vnbmjiswzDBqJ2tqdK4jqEtwioEeHwv8dmcob2sfTy4hqmAtvE3qBrTBcFDZfh2sECQQCGu1qjcVPjky/F1cRSBpCaz7p8t51H2whMLZoQP+LzV4mHpEZXAc26/wDsMcXjuI84A8kYvL7NtevrVJ9yFSOB";

    // Rsa 公钥
    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDaAb67fb18SegcxePE7wBqvwiW" +
            "9qVdZ+D6mMq1YXqnLMhsJZJHpBi1VWSf2Nqwnpl6S44IaxIEs4Ca9BvIwedWUZbf" +
            "GA2h21LjIqbA6iCQmMvxzulepAx29IYLzrR+HyvWEzbyzHlThkKAZZQq+Hcfo4vq" +
            "P7lSBISBy2HP3+TFLQIDAQAB";

    public static void main(String[] args) {
        try {
            // 公钥加密
//            System.out.println(encryptByPublicKey(publicKey,"123456"));
            // 私钥解密
            System.out.println(decryptByPrivateKey(privateKey, "hytDY9Rbsx5/6gEkAm9QFyIWL6RTlo2a7LazOjKD81KXST2Ej8qjhP9PZjhHbkzdDfQUFfS6ojw4qzP8sblt4caMHeaKGCxYbbM6vDhiFh/V1boDUxaClNeRt0U8KrBsI0a5e5nBuuTsVvzwZTR1LK94CdMk75Ovg3VFHLUQCPY="));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 私钥解密
     *
     * @param text 待解密的文本
     * @return 解密后的文本
     */
    public static String decryptByPrivateKey(String text) throws Exception {
        return decryptByPrivateKey(privateKey, text);
    }

    /**
     * 公钥解密
     *
     * @param publicKeyString 公钥
     * @param text            待解密的信息
     * @return 解密后的文本
     */
    public static String decryptByPublicKey(String publicKeyString, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * 私钥加密
     *
     * @param privateKeyString 私钥
     * @param text             待加密的信息
     * @return 加密后的文本
     */
    public static String encryptByPrivateKey(String privateKeyString, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * 私钥解密
     *
     * @param privateKeyString 私钥
     * @param text             待解密的文本
     * @return 解密后的文本
     */
    public static String decryptByPrivateKey(String privateKeyString, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * 公钥加密
     *
     * @param publicKeyString 公钥
     * @param text            待加密的文本
     * @return 加密后的文本
     */
    public static String encryptByPublicKey(String publicKeyString, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * 构建RSA密钥对
     *
     * @return 生成后的公私钥信息
     */
    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
        String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        return new RsaKeyPair(publicKeyString, privateKeyString);
    }

    /**
     * RSA密钥对对象
     */
    public static class RsaKeyPair {
        private final String publicKey;
        private final String privateKey;

        public RsaKeyPair(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }
    }
}
