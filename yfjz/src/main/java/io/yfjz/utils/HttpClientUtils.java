package io.yfjz.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA. User: Administrator Date: 14-4-25 Time: 上午1:41 To
 * change this template use File | Settings | File Templates.
 */
public class HttpClientUtils {

	private final static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
	 * POST方法请求 参数为键值对
	 *
	 * @param map
	 * @param httpUrl
	 */
	public synchronized static String postMethod(Map<String, String> map, String httpUrl) {
		String result = null;
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(httpUrl);
		postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		postMethod.setRequestHeader("accept", "application/json");
        List<NameValuePair> list=  mapToList(map);
		postMethod.setRequestBody(list.toArray(new NameValuePair[0]));
		try {
			int httpStatus = client.executeMethod(postMethod);
			if (httpStatus == HttpStatus.SC_OK) {
				byte[] bytes = postMethod.getResponseBody();
				result = new String(bytes, "UTF-8");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}

	/**
	 * POST方法请求 参数为字符串
	 *
	 * @param queryString
	 * @param httpUrl
	 */
	public synchronized static String postMethod(String queryString, String httpUrl) {
		String result = null;
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(httpUrl);
		postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		postMethod.setRequestHeader("accept", "application/json");
		postMethod.setRequestBody(queryString);
		try {
			int httpStatus = client.executeMethod(postMethod);
			if (httpStatus == HttpStatus.SC_OK) {
				byte[] bytes = postMethod.getResponseBody();
				result = new String(bytes, "UTF-8");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
    /**
     * POST方法请求 参数为字符串
     *
     * @param queryString
     * @param httpUrl
     */
    public synchronized static String postMethod(String queryString, String httpUrl,String fileName) {
        String result = null;
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(httpUrl);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        postMethod.setRequestHeader("accept", "application/json");
        postMethod.setRequestBody(queryString);
        try {
            int httpStatus = client.executeMethod(postMethod);
            if (httpStatus == HttpStatus.SC_OK) {
                byte[] bytes = postMethod.getResponseBody();
                FileOutputStream os = new FileOutputStream(new File(fileName));
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }finally {
            postMethod.releaseConnection();
        }
        return result;
    }

    /**
     * 此方法值针对腾讯云通讯接口使用
     * 普通的http【post，get】请求请调用HttpClientUtils中的postMethod的方法
     * POST方法请求 发送的请求格式为普通文本格式，key=value与{"key":"value"}混合提交方式
     * 混合提交
     * @param queryString
     * @param httpUrl
     */
    public synchronized static String postMethodHybridommit(String queryString, String httpUrl) {
        String result = null;
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(httpUrl);
        postMethod.setRequestHeader("Content-Type","text/plain;charset=UTF-8");//发送的请求格式为普通文本格式，key=value与{"key":"value"}混合提交方式
        postMethod.setRequestHeader("accept", "application/json");//接受服务器返回的格式
        postMethod.setRequestBody(queryString);
        try {
            int httpStatus = client.executeMethod(postMethod);
            if (httpStatus == HttpStatus.SC_OK) {
                byte[] bytes = postMethod.getResponseBody();
                result = new String(bytes, "UTF-8");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }

	/**
	 * 模拟表单上传文件和数据
	 *
	 * @param params
	 * @param url
	 * @param fileurl
	 * @return
	 * @throws Exception
	 */
	public synchronized static int postMultipartMethod(Map<String, String> params,
			String url, String fileurl) throws Exception {
		MultipartPostMethod filePost = new MultipartPostMethod(url);
		if (!"".equals(fileurl)) {
			FilePart file = new FilePart("file", new File(fileurl));
			//file.setContentType("image/*");   //此处修改上传文件类型
            file.setContentType("application/msword");   //上传word文档类型
			filePost.addPart(file);
		}
		Set<String> sets = params.keySet();
		for (String str : sets) {
			StringPart stringPart = new StringPart(str, params.get(str));
			stringPart.setCharSet("UTF-8");
			filePost.addPart(stringPart);
		}
		HttpClient client = new HttpClient();
		int result = 0;
		try {
			result = client.executeMethod(filePost);
		} catch (IOException e) {
			e.printStackTrace();

		}
		return result;
	}

	/**
	 * 模拟表单上传文件和数据
	 *
	 * @param url
	 * @param fileurl
	 * @return
	 * @throws Exception
	 */
	public synchronized static int postMultipartMethod(String url, String fileurl)
			throws Exception {
		MultipartPostMethod filePost = new MultipartPostMethod(url);
		if (!"".equals(fileurl)) {
			FilePart file = new FilePart("file", new File(fileurl));
			file.setContentType("image/*");
			filePost.addPart(file);
		}
		HttpClient client = new HttpClient();
		int result = 0;
		try {
			result = client.executeMethod(filePost);
		} catch (IOException e) {
			e.printStackTrace(); // To change body of catch statement use File |
		}
        return result;
	}



	/**
	 * 测试GET方法请求
	 */
	public synchronized static String httpGet(String url) {
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		String result = null;
		try {
			int httpStatus = client.executeMethod(getMethod);
			logger.info("HttpStatus code{}", httpStatus);
			if (httpStatus != HttpStatus.SC_OK) {
				logger.info("地址{}返回错误。", getMethod.getURI());
				System.out.println("get请求错误！");
			} else {
				byte[] bytes = getMethod.getResponseBody();
				result = new String(bytes, "utf-8");
				logger.info("getMethod:"+url+"--"+ result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return result;
	}

    public synchronized static List<NameValuePair>  mapToList(Map<String, String> map){
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (Map.Entry entry : entrySet) {
            NameValuePair nameValuePair = new NameValuePair((String) entry.getKey(), (String) entry.getValue());
            list.add(nameValuePair);
        }
        return list;
    }

    /**
     * 单独文件上传，没有业务功能模块
     * @param url servlet访问路径
     * @param file 需要上传的文件
     * @return
     */
    public static String upload(String url,File file) {
        String respBody = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);
            FileBody fileBody = new FileBody(file);
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", fileBody).build();
            httppost.setEntity(reqEntity);
            CloseableHttpResponse response = httpClient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    respBody = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));// 打印响应内容
                }
                EntityUtils.consume(resEntity);// 销毁
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            respBody = new R().error("连接文件服务器网络异常").toString();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return respBody;
    }
    /**
     * 文件上传，按照业务功能模块 创建文件夹的方式
     * @param url servlet访问路径
     * @param file 需要上传的文件
     * @param moudelCode 功能模块编号
     * @return
     */
    public static String upload(String url,File file,String moudelCode) {
        String respBody=null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);
            FileBody fileBody = new FileBody(file);
            StringBody comment = new StringBody(moudelCode, ContentType.TEXT_PLAIN);
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", fileBody).addPart("moudelCode", comment).build();
            httppost.setEntity(reqEntity);
            CloseableHttpResponse response = httpClient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    respBody = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));// 打印响应内容
                }
                EntityUtils.consume(resEntity);// 销毁
            } finally {
                response.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
            respBody = new R().error("连接文件服务器网络异常").toString();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                respBody = new R().error("关闭连接异常").toString();
            }
        }
        return respBody;
    }

    /**
     * 执行远程服务器文件删除
     * @param url
     * @param filePath 文件远程服务器地址
     * @return
     */
    public static String deleteFromServer(String url,String filePath) {
        //org.apache.http.NameValuePair 4.4.6 版本
        List<org.apache.http.NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("fUrl", filePath));//防止恶意猜猜参数名
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String message = null;
        UrlEncodedFormEntity uefEntity;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);
            uefEntity = new UrlEncodedFormEntity(param, "UTF-8");
            httppost.setEntity(uefEntity);
            response = httpClient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    message = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));// 打印响应内容
                }
                EntityUtils.consume(resEntity);// 销毁
            } finally {
                response.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
            message = "删除文件异常";
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return message;
    }
    /**
     * 执行远程服务器word转html
     * @param url
     * @return
     */
    public static String word2htmlFromServer(String url,String filePath) {
        String respBody=null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);
            StringBody comment = new StringBody(filePath, ContentType.TEXT_PLAIN);
            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("relativePath", comment).build();
            httppost.setEntity(reqEntity);
            CloseableHttpResponse response = httpClient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    respBody = EntityUtils.toString(resEntity, Charset.forName("UTF-8"));// 打印响应内容
                }
                EntityUtils.consume(resEntity);// 销毁
            } finally {
                response.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
            respBody = new R().error("连接文件服务器网络异常").toString();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
                 respBody = new R().error("关闭连接异常").toString();
            }
        }
        return respBody;
    }
    /**
     * POST方法请求 参数为字符串
     *
     * @param queryString
     * @param httpUrl
     */
    public synchronized static String postMethodJson(String queryString, String httpUrl) {
        String result = null;
        HttpClient client = new HttpClient();
        PostMethod postMethod = new PostMethod(httpUrl);
        postMethod.setRequestHeader("accept", "application/json");
        postMethod.setRequestHeader("Content-Type", "application/json");//发送json字符串
        postMethod.setRequestBody(queryString);
        try {
            int httpStatus = client.executeMethod(postMethod);
            if (httpStatus == HttpStatus.SC_OK) {
                byte[] bytes = postMethod.getResponseBody();
                result = new String(bytes, "UTF-8");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            postMethod.releaseConnection();
        }
        return result;
    }

}
