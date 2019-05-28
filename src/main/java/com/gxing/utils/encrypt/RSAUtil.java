package com.gxing.utils.encrypt;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

/**
 * @name: RSAUtil
 * @desc: RSA 加解密工具类
 * @author: gxing
 * @date: 2019-05-27 13:57
 **/
public class RSAUtil {

    public static String RSA_ALGORITHM = "RSA";

    /*模数*/
    public static String MODULUS = "20699446869743660585652600085521142545885549299416180356907337174876844447072851354250340617493097256523250986258369631674290919786602738236384133455968402973562638944860676845409357141185806086950114923982834902384649504957498323265843637180622659201493542601941446721829110515477834357151021180476489881513326425639917146332982689679883343585484994645969500826856802047685314244182936770833959865758239998395155286496069770257369754869757955696448390476954823903148723590343746389325432308943023017334156517775392772516273846119705893987401809697466747196964354573931365227853281877988902285243625430107129364655677";
    /*公钥指数*/
    public static String PUBLIC_EXPONENT = "65537";
    /*私钥指数*/
    public static String PRIVATE_EXPONENT = "7752382980878864596713964159163776855650408281645027188615447624354977294557320849139792948355403392913364900097833019659817669807317457466958337361745951920077131588287613305012018722712446563074150034471499804370526909977292160833729527970434764638305979876476689652979164710047811836005185084376694754745220717141834721991812378949309261576714952224857443411317481019886585010389914550136482822295477704937979041224302405587417950544740345790835101577186170938604199681751873488184019865981053862812821750153385594250488559776169734914538021236458801817027632992427894939524367338782478952238328781398876966625473";

    /**
     * 公钥加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data) throws Exception {
        RSAPublicKey publicKey = RSAUtil.getPublicKey(MODULUS, PUBLIC_EXPONENT);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 模长
        int key_len = publicKey.getModulus().bitLength() / 8;
        // 加密数据长度 <= 模长-11
        String[] datas = splitString(data, key_len - 11);
        String mi = "";
        // 如果明文长度大于模长-11则要分组加密
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes()));
        }
        return mi;
    }

    /**
     * 私钥解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data) throws Exception {
        RSAPrivateKey privateKey = RSAUtil.getPrivateKey(MODULUS, PRIVATE_EXPONENT);
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 模长
        int key_len = privateKey.getModulus().bitLength() / 8;
        byte[] bytes = data.getBytes();
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        // 如果密文长度大于模长则要分组解密
        String ming = "";
        byte[][] arrays = splitArray(bcd, key_len);
        for (byte[] arr : arrays) {
            ming += new String(cipher.doFinal(arr));
        }
        return ming;
    }


    /**
     * 生成公钥和私钥
     * @throws NoSuchAlgorithmException
     */
    public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        map.put("public", publicKey);
        map.put("private", privateKey);
        return map;
    }

    /**
     * 使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用模和指数生成RSA私钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 模长
        int key_len = publicKey.getModulus().bitLength() / 8;
        // 加密数据长度 <= 模长-11
        String[] datas = splitString(data, key_len - 11);
        String mi = "";
        // 如果明文长度大于模长-11则要分组加密
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes()));
        }
        return mi;
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 模长
        int key_len = privateKey.getModulus().bitLength() / 8;
        byte[] bytes = data.getBytes();
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        // 如果密文长度大于模长则要分组解密
        String ming = "";
        byte[][] arrays = splitArray(bcd, key_len);
        for (byte[] arr : arrays) {
            ming += new String(cipher.doFinal(arr));
        }
        return ming;
    }

    /**
     * ASCII码转BCD码
     */
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    public static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }

    /**
     * BCD转字符串
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 拆分字符串
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * 拆分数组
     */
    public static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    public static void main(String[] args) throws Exception {
		/*HashMap<String, Object> map = RSAUtil.getKeys();
        //生成公钥和私钥
        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");

        //模
        String MODULUS = publicKey.getModulus().toString();
        System.err.println("MODULUS:" + MODULUS);

        //公钥指数
        String PUBLIC_EXPONENT = publicKey.getPublicExponent().toString();
        System.err.println("PUBLIC_EXPONENT:" + PUBLIC_EXPONENT);

        //私钥指数
        String PRIVATE_EXPONENT = privateKey.getPrivateExponent().toString();
        System.err.println("PRIVATE_EXPONENT:" + PRIVATE_EXPONENT);

        //明文
        String ming = "Hello World";
        //使用模和指数生成公钥和私钥
        RSAPublicKey pubKey = RSAUtil.getPublicKey(MODULUS, PUBLIC_EXPONENT);
        RSAPrivateKey priKey = RSAUtil.getPrivateKey(MODULUS, PRIVATE_EXPONENT);

        //加密后的密文
        String mi = RSAUtil.encryptByPublicKey(ming, pubKey);
        System.err.println("密文："+mi);
        //解密后的明文
        ming = RSAUtil.decryptByPrivateKey(mi, priKey);
        System.err.println(ming);
        String jiami = encryptByPublicKey("Hello World");
        System.out.println(jiami);
        System.out.println(decryptByPrivateKey("334FF888C5CC290374DFC1271F932D203008ADF1754D7399ABFC9D3A1E09B3A299EC12C2A84A87B413B3FD6F5887E84D0007243F2AC3D413FD949FE4C1EBBD233D90C533605B06402B1C323BF675A5126E22C319C58E9890AAFFC6C2CB5D0362DF8C68509E55205001A74497F17029C361398FCE7B9A4DA85423DEF92B38EC5B6B8F1B4BCAEA2F84B8F31B5E061C9B321D837011898380B64A6889AF0153008A199666A1F541D48E37FB3AF72FCCAD06287996FF96FE801D848B6C7A051C03220421E810A8B5A66B37CAC0F38DA400DE6FB86E87718215613E6F3748AE4F2E86C5CDC60A5DF01218933656E18DBF2D6689AA3E58334651323E659BB3F922C228"));

        System.out.println(MODULUS.length());
        */
    }
}