package com.hetao.demo.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * @description: 微信小程序手机号解密
 * @author: chenliang
 * @create: 2019-07-18 18:23
 **/
public class WXBizDataCryptUtil {


    public static JSONObject decryptData(String encryptedData, String iv, String sessionKey) throws Exception {
        byte[] dataByte = Base64.decodeBase64(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decodeBase64(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decodeBase64(iv);

            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            keyByte = completToBase(keyByte,base);
            ivByte = completToBase(ivByte,base);
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        return null;
    }

    //补全数组位数
    public static byte[] completToBase(byte[] bytes,int base){
        byte[] temp = new byte[]{};
        if (bytes.length % base != 0) {
            int groups = bytes.length / base + (bytes.length % base != 0 ? 1 : 0);
            temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(bytes, 0, temp, 0, bytes.length);
        }else{
            temp = bytes;
        }
        return temp;
    }
}
