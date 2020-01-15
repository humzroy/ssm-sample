package com.zhen.util;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 * Author：wuhengzhen
 * Date：2018-10-24
 * Time：17:50
 */
public class RSAUtil {
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 获取公钥的key
	 */
	public static final String PUBLIC_KEY = "RSAPublicKey";

	/**
	 * 获取私钥的key
	 */
	public static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * RSA最大加密明文大小
	 */
	public static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	public static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 字节数据转字符串专用集合
	 */
	private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * <p>
	 * 生成密钥对(公钥和私钥)
	 * </p>
	 *
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 从文件中输入流中加载密钥
	 *
	 * @param path 密钥输入流
	 * @throws Exception 加载密钥时产生的异常
	 */
	public static String loadKeyByFile(String path) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new Exception("密钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("密钥输入流为空");
		}
	}

	/**
	 * 根据字符串获取私钥
	 *
	 * @param privateKeyStr 私钥字符串
	 * @return
	 */
	public static PrivateKey getPrivateKeyByStr(String privateKeyStr) throws Exception {
		byte[] keyBytes = Base64Util.decryptBASE64(privateKeyStr);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		return privateKey;
	}

	/**
	 * 根据字符串获取公钥
	 *
	 * @param publicKeyStr 公钥字符串
	 * @return
	 */
	public static PublicKey getPublickeyByStr(String publicKeyStr) throws Exception {
		byte[] keyBytes = Base64Util.decryptBASE64(publicKeyStr);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * <p>
	 * 根据keyMap获取私钥
	 * </p>
	 *
	 * @param keyMap 密钥对Map
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return Base64Util.encryptBASE64(key.getEncoded());
	}

	/**
	 * 根据私钥获取私钥字符串
	 *
	 * @param privateKey 私钥PrivateKey
	 * @return
	 */
	public static String getPrivateKey(PrivateKey privateKey) throws Exception {
		return Base64Util.encryptBASE64(privateKey.getEncoded());
	}

	/**
	 * <p>
	 * 根据keyMap获取公钥
	 * </p>
	 *
	 * @param keyMap 密钥对Map
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return Base64Util.encryptBASE64(key.getEncoded());
	}

	/**
	 * 根据公钥获取公钥字符串
	 *
	 * @param publicKey
	 * @return
	 */
	public static String getPublicKey(PublicKey publicKey) throws Exception {
		return Base64Util.encryptBASE64(publicKey.getEncoded());
	}

	/**
	 * 根据私钥字符串加签
	 *
	 * @param privateKeyStr 私钥字符串(BASE64编码)
	 * @param data          需要加签的数据
	 * @return
	 */
	public static String sign(String privateKeyStr, String data) throws Exception {
		PrivateKey privateKey = getPrivateKeyByStr(privateKeyStr);
		return sign(privateKey, data);
	}

	/**
	 * 根据私钥加签
	 *
	 * @param privateKey
	 * @param data
	 * @return
	 */
	public static String sign(PrivateKey privateKey, String data) throws Exception {
		// 加签算法，加签算法有很多种，这里选择 MD5WithRSA
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateKey);
		signature.update(data.getBytes());
		byte[] signByte = signature.sign();
		return Base64Util.encryptBASE64(signByte);
	}

	/**
	 * <p>
	 * 根据公钥字符串校验数字签名
	 * </p>
	 *
	 * @param data      已加密数据
	 * @param publicKey 公钥(BASE64编码)
	 * @param sign      数字签名
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(String data, String publicKey, String sign) throws Exception {
		PublicKey publicK = getPublickeyByStr(publicKey);
		return verify(publicK, sign, data);
	}

	/**
	 * 根据公钥验签
	 *
	 * @param publicKey 公钥
	 * @param sign      数字签名
	 * @param data      已加密数据
	 * @return
	 */
	public static boolean verify(PublicKey publicKey, String sign, String data) throws Exception {
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicKey);
		signature.update(data.getBytes());
		return signature.verify(Base64Util.decryptBASE64(sign));
	}

	/**
	 * 私钥解密
	 *
	 * @param encryptedData 已加密数据
	 * @param privateKey    私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
		byte[] keyBytes = Base64Util.decryptBASE64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		return decrypt(encryptedData, cipher);
	}

	/**
	 * <p>
	 * 公钥解密
	 * </p>
	 *
	 * @param encryptedData 已加密数据
	 * @param publicKey     公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
		byte[] keyBytes = Base64Util.decryptBASE64(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		return decrypt(encryptedData, cipher);
	}

	/**
	 * <p>
	 * 公钥加密
	 * </p>
	 *
	 * @param data      源数据
	 * @param publicKey 公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = Base64Util.decryptBASE64(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		return encrypt(data, cipher);
	}

	/**
	 * 私钥字符串加密
	 *
	 * @param data       源数据
	 * @param privateKey 私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
		PrivateKey privateK = getPrivateKeyByStr(privateKey);
		return encryptByPrivateKey(data, privateK);
	}

	/**
	 * 私钥加密
	 *
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return encrypt(data, cipher);
	}

	/**
	 * 分块加密
	 *
	 * @param data
	 * @param cipher
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Cipher cipher) throws Exception {
		return dealWithData(data, cipher, MAX_ENCRYPT_BLOCK);
	}

	/**
	 * 分块解密
	 *
	 * @param encryptedData
	 * @param cipher
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] encryptedData, Cipher cipher) throws Exception {
		return dealWithData(encryptedData, cipher, MAX_DECRYPT_BLOCK);
	}

	/**
	 * 分块处理数据
	 *
	 * @param data
	 * @param cipher
	 * @param block
	 * @return
	 * @throws Exception
	 */
	public static byte[] dealWithData(byte[] data, Cipher cipher, int block) throws Exception {
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > block) {
				cache = cipher.doFinal(data, offSet, block);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * block;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 字节数据转十六进制字符串
	 *
	 * @param data 输入数据
	 * @return 十六进制内容
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i < data.length - 1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * main method
	 *
	 * @param args
	 */
	public static void main(String[] args) {
        // OpenSSL 生成的RSA秘钥对
        String defaultPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4BDPESj4eihcHVoekuFXefa4R\n" +
                "0JDDwJ7L2adQKomHKMv/jCj29hD5Z7niuSCNY7TR0bEt+2UdAx7LZ/Y6TCkHCiJp\n" +
                "y0u2SZv577w7LsodGdIv8jZ3WYtjnZk/MwhtnXn1qaPOs0Vnqexd3VoxZ/nu8kJ5\n" +
                "ubyciD4RRDXUZ62t4wIDAQAB";

        String defaultPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALgEM8RKPh6KFwdW\n" +
                "h6S4Vd59rhHQkMPAnsvZp1AqiYcoy/+MKPb2EPlnueK5II1jtNHRsS37ZR0DHstn\n" +
                "9jpMKQcKImnLS7ZJm/nvvDsuyh0Z0i/yNndZi2OdmT8zCG2defWpo86zRWep7F3d\n" +
                "WjFn+e7yQnm5vJyIPhFENdRnra3jAgMBAAECgYEAo3xQRZc0Q0uqFAHjdwuydn4w\n" +
                "VXF3/AI40qEnzchM8UDkpMBwcKDDCeIGuxCAhD1OG49yG4kbF0B7bnmJv9eEJlQM\n" +
                "zrbYl028orI4pguIyNMn1p159V2l0Do4ZuVsF1bjX/4jxFDiI4jwLBIBZDfdjjdo\n" +
                "lY+ih7BicGAarbXfNtECQQDgsGFnpDGGMREdA6dWeWqIz560Q64CohUwoJnG/7v9\n" +
                "8r51xVG2daXFUGV9mphJOKGGFlrJFRAe9cJrHn3Ml83JAkEA0ajbUeGkjyHDcVTu\n" +
                "38yZsuJL2w8RJhN1Wq626/qjdhuXxBDGrss6FzVMLe+OhvsQ/5QGUT75nicUGc+W\n" +
                "ubNESwJAbt86QV0pPvFpY2rRIP+qzTW+N2+KJyx5zlQBbcv1pPsqdozWpHVrRnFE\n" +
                "k3U8niz+r17Kj50AJnbY5+jU5Kcn2QJAd0w4t6fIRjV1C0jDv46hKlt/xD8Xh+3s\n" +
                "idP+e9obJvpeag8Nrqou/MOz/DNii0XTD0qAKbzGtooP8vvfDP2HbwJBAM78SoPW\n" +
                "vCEc+OP8cP4jgK4hqBTIj+6+B+SFBdPIovKsYkGZOMEzS4kaIsGltMOBnM9AhCzh\n" +
                "FvdHl0v57GwfCxk=";
		//测试字符串
		String dataStr = "Test String wuhengzhen";

		try {
			//加密
			byte[] cipher = RSAUtil.encryptByPublicKey(dataStr.getBytes(), defaultPublicKey);
			//解密
			byte[] plainText = RSAUtil.decryptByPrivateKey(cipher, defaultPrivateKey);
			System.out.println("密文长度:" + cipher.length);
			System.out.println(RSAUtil.byteArrayToString(cipher));
			System.out.println("明文长度:" + plainText.length);
			System.out.println(RSAUtil.byteArrayToString(plainText));
			System.out.println(new String(plainText));

			String data = "Signature类是一个引擎类，提供加密的数字签名算法，例如DSA或RSAwithMD5。加密安全签名算法采用任意大小的输入和私钥，并生成一个相对较短（通常是固定大小）的字节串——签名。RSA最大解密密文大小为128个字节，RSA最大加密明文大小为117个字节，测试加密解密字符串";
			Map keyPair = genKeyPair();
			String privateKeyStr = getPrivateKey(keyPair);
			String publicKeyStr = getPublicKey(keyPair);

			byte[] encryptData = encryptByPrivateKey(data.getBytes(), privateKeyStr);
			String encryptStr = Base64Util.encryptBASE64(encryptData);
			System.out.println("加密后的字符串为：" + encryptStr);

			byte[] decryptData = Base64Util.decryptBASE64(encryptStr);
			byte[] decrypt = decryptByPublicKey(decryptData, publicKeyStr);
			System.out.println("解密后的字符串为：" + new String(decrypt));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
