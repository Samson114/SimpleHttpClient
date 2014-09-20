package ONE;

import java.io.IOException;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
/** *//**
 * �ύ������ʾ
 * �ó������ӵ�һ�����ڲ�ѯ�ֻ����������ص�ҳ��
 * �Ա��ѯ�����1330227���ڵ�ʡ���Լ�����
 * @author Liudong
 */

public class SimpleHttpClient {
    public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient();
//        client.getHostConfiguration().setHost("www.imobile.com.cn", 80, "http");
        client.getHostConfiguration().setHost("http://jw.djtu.edu.cn/academic/j_acegi_security_check", 80, "http");
        
        HttpMethod method = getPostMethod();//ʹ��POST��ʽ�ύ����
     client.executeMethod(method);
       //��ӡ���������ص�״̬
     System.out.println(method.getStatusLine());
       //��ӡ���ҳ��
    String response =   new String(method.getResponseBodyAsString().getBytes("8859_1"));
       //��ӡ���ص���Ϣ
     System.out.println(response);
        method.releaseConnection();
    }
    /** *//**
     * ʹ��GET��ʽ�ύ����
   * @return
     */
    private static HttpMethod getGetMethod(){
        return new GetMethod("/simcard.php?simcard=1330227");
    }
    /** *//**
     * ʹ��POST��ʽ�ύ����
   * @return
     */
    private static HttpMethod getPostMethod(){
        PostMethod post = new PostMethod("/simcard.php");
        NameValuePair simcard = new NameValuePair("simcard","1330227");
        
        post.setRequestBody(new NameValuePair[] { simcard});
        return post;
    }
} 
