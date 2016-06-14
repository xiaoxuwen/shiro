package com.github.zhangkaitao.shiro.chapter5.hash;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.BlowfishCipherService;
import org.apache.shiro.crypto.DefaultBlockCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Test;

import java.security.Key;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-27
 * <p>Version: 1.0
 */
public class CodecAndCryptoTest {


    /**
     * 可以进行base64 编码/解码操作
     */
    @Test
    public void testBase64() {
        String str = "hello";
        String base64Encoded = Base64.encodeToString(str.getBytes());
        System.out.println("base64加密：" + base64Encoded);
        String str2 = Base64.decodeToString(base64Encoded);
        System.out.println("base64解密：" + str2);
        //assertThat(str2, is(str));
        assertEquals(str, str2);
    }


    /**
     * 可以进行16进制字符串编码/解码操作
     */
    @Test
    public void testHex() {
        String str = "hello";
        String base64Encoded = Hex.encodeToString(str.getBytes());
        System.out.println("16进制64加密：" + base64Encoded);
        String str2 = new String(Hex.decode(base64Encoded.getBytes()));
        System.out.println("16进制64解密：" + str2);
        assertThat(str2, is(str));
    }

    @Test
    public void testCodecSupport() {
        String str = "hello";
        str.toCharArray();
        byte[] bytes = CodecSupport.toBytes(str, "utf-8");
        String str2 = CodecSupport.toString(bytes, "utf-8");
        assertEquals(str, str2);
    }


    @Test
    public void testRandom() {
        //生成随机数
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed("123".getBytes());
        System.out.println(randomNumberGenerator.nextBytes().toHex());
    }


    @Test
    public void testMd5() {
        String str = "hello";
        String salt = "123";
        String md5 = new Md5Hash(str, salt).toString();//还可以转换为 toBase64()/toHex()
        System.out.println(md5);
    }

    @Test
    public void testSha1() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha1Hash(str, salt).toString();
        System.out.println(sha1);

    }

    @Test
    public void testSha256() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha256Hash(str, salt).toString();
        System.out.println(sha1);

    }

    @Test
    public void testSha384() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha384Hash(str, salt).toString();
        System.out.println(sha1);
    }

    @Test
    public void testSha512() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha512Hash(str, salt).toString();
        System.out.println(sha1);
    }

    @Test
    public void testSimpleHash() {
        String str = "hello";
        String salt = "123";
        //MessageDigest
        String simpleHash = new SimpleHash("SHA-1", str, salt).toString();
        System.out.println(simpleHash);
    }


    /**
     * 1、首先创建一个DefaultHashService，默认使用SHA-512 算法；
     * 2、可以通过hashAlgorithmName属性修改算法；
     * 3、可以通过privateSalt设置一个私盐，其在散列时自动与用户传入的公盐混合产生一个新盐；
     * 4、可以通过generatePublicSalt属性在用户没有传入公盐的情况下是否生成公盐；
     * 5、可以设置randomNumberGenerator用于生成公盐；
     * 6、可以设置hashIterations属性来修改默认加密迭代次数；
     * 7、需要构建一个HashRequest，传入算法、数据、公盐、迭代次数。
     */
    @Test
    public void testHashService() {
        DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
        hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
        hashService.setHashIterations(1); //生成Hash值的迭代次数

        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))
                .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();
        String hex = hashService.computeHash(request).toHex();
        System.out.println(hex);
    }


    /**
     * AES 算法实现
     */
    @Test
    public void testAesCipherService() {
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);//设置key长度

        //生成key
        Key key = aesCipherService.generateNewKey();

        String text = "hello";

        //加密
        String encrptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String text2 = new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

        assertEquals(text, text2);
    }

    /**
     * Blowfish 算法实现
     */
    @Test
    public void testBlowfishCipherService() {
        BlowfishCipherService blowfishCipherService = new BlowfishCipherService();
        blowfishCipherService.setKeySize(128);

        //生成key
        Key key = blowfishCipherService.generateNewKey();

        String text = "hello";

        //加密
        String encrptText = blowfishCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String text2 = new String(blowfishCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

        assertEquals(text, text2);
    }

    @Test
    public void testDefaultBlockCipherService() throws Exception {
        //对称加密，使用Java的JCA（javax.crypto.Cipher）加密API，常见的如 'AES', 'Blowfish'
        DefaultBlockCipherService cipherService = new DefaultBlockCipherService("AES");
        cipherService.setKeySize(128);

        //生成key
        Key key = cipherService.generateNewKey();

        String text = "hello";

        //加密
        String encrptText = cipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String text2 = new String(cipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

        assertEquals(text, text2);
    }


    //加密/解密相关知识可参考snowolf的博客 http://snowolf.iteye.com/category/68576


}
