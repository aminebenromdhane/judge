package com.orion.judgesc.script;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.URLStreamHandler;
import java.util.List;
import java.util.Map;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.silvertunnel_ng.netlib.adapter.url.NetlibURLStreamHandlerFactory;
import org.silvertunnel_ng.netlib.api.NetFactory;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetLayerIDs;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.util.HttpUtil;

//glype
public class ProxyType1 extends Proxy{

	public static final String[] PROXY_URL_ARRAY = new String[]{"https://london-s01-i15-traffic.cyberghostvpn.com",
																"https://us.proxy.cyberghostvpn.com"};
	
	private String url;
	private String method;
	private String dataToSend;
	private String cookie;
	private HttpsURLConnection connection;
	private InputStream content;
	private boolean first;
	private int id;
	
	public ProxyType1(int id){
		this.id=id;
		this.first = true;
		this.url = "";
		this.cookie = "";
		this.connection = null;
		this.content = null;
		this.method = "GET";
		this.dataToSend = "";

	}
	
	private void beforeConnection() throws IOException{
		/*if(first){
			URL base = new URL(PROXY_URL_ARRAY[id]+"/");
			HttpURLConnection urlConnection = (HttpURLConnection) base.openConnection();
			urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
			List<String> cookies = urlConnection.getHeaderFields().get("Set-Cookie");
			cookie += cookies.get(0).replace("path=/", "");
		}*/
		
		
		/*URL base = new URL("https://webproxy.vpnbook.com/includes/process.php?action=update");
		HttpURLConnection urlConnection = (HttpURLConnection) base.openConnection();
		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
		urlConnection.setRequestMethod("POST");
		urlConnection.setDoOutput(true); 
		urlConnection.setFixedLengthStreamingMode(this.url.getBytes().length);
		urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
		out.print("u="+URLEncoder.encode(this.url,"UTF-8"));
	    out.close();
		List<String> cookies = urlConnection.getHeaderFields().get("Set-Cookie");
		System.out.println(urlConnection.getHeaderFields());
		cookie += cookies.get(0).replace("path=/", "");*/
		/*if(first){
			Map<String, String> cookies = Jsoup.connect(PROXY_URL_ARRAY[id]+"/").
			timeout(0).execute().cookies();
			for(String key : cookies.keySet()){
				cookie += key+"="+cookies.get(key)+";";
			}
		}*/
		
	}
	
	private void connection(){
		try{
			this.beforeConnection();	
			
			
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			        return null;
			      }

			      public void checkClientTrusted(X509Certificate[] certs, String authType) {
			      }

			      public void checkServerTrusted(X509Certificate[] certs, String authType) {
			      }
			    } };

			    SSLContext sc = SSLContext.getInstance("SSL");
			    sc.init(null, trustAllCerts, new java.security.SecureRandom());
			    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			
			
			
			connection = (HttpsURLConnection) (new URL(PROXY_URL_ARRAY[id]+"/go/browse.php?u="+URLEncoder.encode(url,"US-ASCII")+"&b=1")).openConnection(); 
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
			connection.addRequestProperty("Cookie", cookie);
			connection.setRequestMethod(this.method);
			connection.setHostnameVerifier(new Verifier());
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		
	}
	/*
	private void connection(){
		    
	        try {   
	        	URL context = null;
	        	URL urls = new URL(context, url, handler);
	        	connection = (HttpURLConnection) urls.openConnection();
	        	connection.setRequestMethod(this.method);
	        } catch (IOException e) {
				e.printStackTrace();
			}
	}
	*/
	public void connect(){
		try {
			this.connection();
			if(!this.dataToSend.equals("")){
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			    connection.setRequestProperty("Content-Length", ""+this.dataToSend.length());
			 
				PrintWriter out = new PrintWriter(connection.getOutputStream());
				out.print(this.dataToSend);
			    out.close();

			}
			if(first){
				System.out.println(connection);
				List<String> cookies = connection.getHeaderFields().get("Set-Cookie");
				if(cookies != null){
					for(String aCookie : cookies){
						cookie += aCookie.replace(" path=/;", "");
					}
				}
				first = false;
			}
			
			if(connection != null){
				content = connection.getInputStream();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void afterConnection() throws IOException{
		
		URL base2 = new URL(PROXY_URL_ARRAY[id]+"/includes/process.php?action=sslagree");
		HttpURLConnection urlConnection2 = (HttpURLConnection) base2.openConnection();
		urlConnection2.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
		urlConnection2.addRequestProperty("Cookie", cookie);
		urlConnection2.setConnectTimeout(50000);
		content = urlConnection2.getInputStream();
		List<String> cookies = urlConnection2.getHeaderFields().get("Set-Cookie");
		if(cookies != null){
			for(String aCookie : cookies){
				cookie += aCookie.replace("path=/", "");
			}
		}
		System.out.println(id);
		System.out.println(cookie);
		first = false;
	}
	
	
	public InputStream getContent(){
		return content;
	}
	public void usePost(){
		this.method = "POST";
	}
	public void setDataToSend(String dataToSend){
		this.dataToSend = dataToSend;
	}
	public void setUrl(String url){
		this.url = url;
		this.connection = null;
		this.content = null;
		this.method = "GET";
		this.dataToSend = "";

	}
	public class Verifier implements HostnameVerifier {

	    public boolean verify(String arg0, SSLSession arg1) {
	            return true;   // mark everything as verified
	    }
	}
}
