package cn.goat.oms.uitls;

import cn.goat.oms.config.Constant;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
* @Description:AES加密
* @author: heyz
* @Date: 2021/7/29 16:25
*/
public class AESUtil {

    public static String encodingPsw(String password){
        byte[] encoded = SecureUtil.generateKey(Constant.AES).getEncoded();
        SymmetricCrypto symmetricCrypto = new SymmetricCrypto(SymmetricAlgorithm.AES,encoded);
        //加密为16进制表示
        String encryptHex = symmetricCrypto.encryptHex(password);
        return encryptHex;
    }

    public static String decodingPsw(String password){
        byte[] encoded = SecureUtil.generateKey(Constant.AES).getEncoded();
        SymmetricCrypto symmetricCrypto = new SymmetricCrypto(SymmetricAlgorithm.AES,encoded);
        //解密为字符串
        String decryptStr = symmetricCrypto.decryptStr(password);
        return decryptStr;
    }

}
