package util;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @Author:L1ANN
 * @Description: 生成Token的工具类
 * @Date:Created in 17:44 2017/11/6
 * @Modified By:
 */
public class TokenProccessor {
    /*
     *单例设计模式(保证类的对象在内存中只有一个）
     * 1.把类的构造函数私有
     * 2.自己创建一个类的对象
     * 3.对外提供一个公共的方法，返回类的对象
     */

    private TokenProccessor(){}

    private static final TokenProccessor instance = new TokenProccessor();

    /**
     * 返回类的对象
     * @return
     */
    public static TokenProccessor getInstance(){
        return instance;
    }

    /**
     * 生成Token
     * @return
     */
    public String makeToken(){
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999))+"";
        //数据指纹 128位长  16个字节 md5
        try{
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] = md.digest(token.getBytes());
            //base64编码——任意二进制编码明文字符
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(md5);
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
}
