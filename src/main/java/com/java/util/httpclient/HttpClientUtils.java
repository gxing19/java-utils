package com.java.util.httpclient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.java.util.file.FileSuffix;
import com.java.util.https.MyX509TrustManager;

/**
 * http,https连接和请求
 * @author Rocky
 *
 */
@SuppressWarnings("deprecation")
public class HttpClientUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
			.build();

	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	/**
	 * 发送 get 请求（HTTPS），K-V形式
	 * 
	 * @param url
	 * @param params
	 * @return jsonString
	 */
	public static String doGetSSL(String url, Map<String, Object> params) {
		String queryString = toQueryString(params);
		CloseableHttpClient httpClient = createSSLClientDefault();
		HttpGet httpGet = new HttpGet();
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		String result = null;
		try {
			httpGet.setURI(new URI(url + "?" + queryString.toString()));
			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				logger.info("HttpGet方式请求失败！状态码:" + statusCode);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			result = EntityUtils.toString(entity, "utf-8");
			logger.info("HttpGet方式请求成功！返回结果：{}", result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 发送 post 请求（HTTPS），K-V形式
	 * 
	 * @param url
	 * @param json
	 * @return jsonString
	 */
	public static String doPostSSL(String url, Object json) {
		CloseableHttpClient httpClient = createSSLClientDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		String result = null;
		try {
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				logger.info("HttpGet方式请求失败！状态码:" + statusCode);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			result = EntityUtils.toString(entity, "utf-8");
			logger.info("HttpGet方式请求成功！返回结果：{}", result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	// httpURLConnection post请求方法
	public static String httpsRequest(String requestUrl, String reqBody) {
		String UTF8 = "UTF-8";
		OutputStream outputStream = null;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		StringBuffer stringBuffer = null;
		String resp;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setConnectTimeout(10 * 1000);
			httpURLConnection.setReadTimeout(10 * 1000);
			httpURLConnection.connect();
			outputStream = httpURLConnection.getOutputStream();
			outputStream.write(reqBody.getBytes(UTF8));

			inputStream = httpURLConnection.getInputStream();
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
			stringBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			resp = stringBuffer.toString();
			return resp;
		} catch (Exception e) {
			System.out.println("https请求异常：{}" + e.getMessage());
		} finally {
			if (stringBuffer != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 发送 get 请求（HTTPS） 获取下载流
	 * 
	 * @param url
	 * @param params
	 * @return byte[]
	 */
	public static byte[] doGetSSLDown(String url, Map<String, Object> params) {
		String queryString = toQueryString(params);
		return doGetSSLDown(url + "?" + queryString);
	}

	public static byte[] doGetSSLDown(String url) {
		CloseableHttpClient httpClient = createSSLClientDefault();
		HttpGet httpGet = new HttpGet();
		httpGet.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		byte[] by = null;
		try {
			httpGet.setURI(new URI(url));
			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			by = inputSteam2Btye(entity.getContent());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return by;
	}

	/***
	 * 拼接URL参数
	 * 
	 * @param data
	 * @return
	 */
	public static String toQueryString(Map<?, ?> data) {
		StringBuffer queryString = new StringBuffer();
		for (Entry<?, ?> pair : data.entrySet()) {
			queryString.append(pair.getKey() + "=");
			queryString.append(pair.getValue() + "&");
		}
		if (queryString.length() > 0) {
			queryString.deleteCharAt(queryString.length() - 1);
		}
		return queryString.toString();
	}

	/**
	 * URL编码
	 * 
	 * @param source
	 * @param encode
	 * @return
	 */
	public static String urlEncode(String source, String encode) {
		String result = source;
		try {
			result = URLEncoder.encode(source, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 流转字节数组
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] inputSteam2Btye(InputStream input) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			IOUtils.copy(input, bos);
			IOUtils.closeQuietly(input);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return bos.toByteArray();
	}

	public static String downloadImage(String imagepath, String savePath, String fileName) {
		URL url = null;
		HttpURLConnection conn = null;
		// 获取连接
		try {
			url = new URL(imagepath);
			if ("https".equals(url.getProtocol())) {
				SSLContext context = null;
				try {
					context = SSLContext.getInstance("SSL", "SunJSSE");
					context.init(new KeyManager[0], new TrustManager[] { new MyX509TrustManager() },
							new java.security.SecureRandom());
				} catch (Exception e) {
					throw new IOException(e);
				}
				HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
				connHttps.setSSLSocketFactory(context.getSocketFactory());
				connHttps.setHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String arg0, SSLSession arg1) {
						return true;
					}
				});
				conn = connHttps;
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				String contentType = conn.getContentType();
				String suffix = FileSuffix.getSuffix(contentType);
				InputStream inputStream = conn.getInputStream();
				FileOutputStream output = null;
				// 保存文件
				if (suffix != null) {
					String filePath = savePath + fileName + "." + suffix;
					try {
						// String fileName = savePath+suffix;
						File file = new File(filePath);
						output = new FileOutputStream(file);
						int len = 0;
						byte[] array = new byte[1024];
						while ((len = inputStream.read(array)) != -1) {
							output.write(array, 0, len);
						}
						output.flush();
						output.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (inputStream != null) {
							try {
								inputStream.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if (output != null) {
							try {
								output.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					return filePath;
				} else {
					// 返回的不是图片类型
					String result = "";
					if (inputStream != null) {
						result = IOUtils.toString(inputStream, CharEncoding.UTF_8);
					}
					logger.error("下载二维码失败原因：" + result);
				}
			} else {
				logger.error(conn.getResponseCode() + "," + conn.getResponseMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
