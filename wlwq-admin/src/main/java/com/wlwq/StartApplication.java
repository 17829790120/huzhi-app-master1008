package com.wlwq;

import org.apache.shiro.codec.Base64;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 启动程序
 *
 * @author wlwq
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
/**
 * 打开redis缓存服务
 */
@EnableCaching
public class StartApplication implements CommandLineRunner {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(StartApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ 你就幸福吧你！   ლ(´ڡ`ლ)ﾞ  \n 方法都给你了！ ");
    }


    @Autowired
    private StringEncryptor codeSheepEncryptorBean;

    //TODO 如果没有自行配置

    /**
     * 数据库密码
     */
    @Value("${spring.datasource.druid.master.password}")
    private String mysqlPwd;


    @Override
    public void run(String... args) throws Exception {

        // 加密
        String mysqlEncryptedPwd = encrypt(mysqlPwd);

        // 打印加密前后的结果对比
        System.out.println("MySQL原始明文密码为：" + mysqlPwd);
//        System.out.println("Redis原始明文密码为：" + redisPwd);
//        System.out.println("MpSecret原始明文密码为：" + mpSecret);
//        System.out.println("MiniAppSecret原始明文密码为：" + miniAppSecret);
//        System.out.println("====================================");
//        System.out.println("MySQL原始明文密码加密后的结果为：" + mysqlEncryptedPwd);
//        System.out.println("Redis原始明文密码加密后的结果为：" + redisEncryptedPwd);
//        System.out.println("MpSecret原始明文密码加密后的结果为：" + mpSecretEncryptedPwd);
//        System.out.println("MiniAppSecret原始明文密码加密后的结果为：" + miniAppEncryptedPwd);


        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecretKey secretKey = keygen.generateKey();
        System.out.println("后端记住我 生成的密钥：" + Base64.encodeToString(secretKey.getEncoded()));
    }

    /**
     * 加密
     */
    private String encrypt(String originPassword) {
        return codeSheepEncryptorBean.encrypt(originPassword);
    }

    /**
     * 解密
     *
     * @param encryptedPassword
     * @return
     */
    private String decrypt(String encryptedPassword) {
        return codeSheepEncryptorBean.decrypt(encryptedPassword);
    }
}