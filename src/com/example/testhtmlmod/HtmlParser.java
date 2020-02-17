package com.example.testhtmlmod;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;

public class HtmlParser extends AsyncTask<Void, Void, String> {

	private String mUrl;
	private WebView webView;
	private static final String TAG = "HtmlParser";
	private Context mContext;
	public static String Js2JavaInterfaceName = "JsUseJava";
	public List<String> imgUrls = new ArrayList<String>();

	public HtmlParser(WebView webView, String url, Context context) {
		this.webView = webView;
		mUrl = url;
		mContext = context;
	}

	@Override
	protected String doInBackground(Void... params) {

		Document doc = null;
		try {
			doc = Jsoup.parse(new URL(mUrl), 15000);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		if (doc == null)
			return null;

		// 删除javascript代码
		Elements es = doc.select("script");
		if (es != null) {
			es.remove();
		}
		// 去除超链接
		Elements hrefs = doc.getElementsByTag("a");
		for (Element href : hrefs) {
			href.removeAttr("href");
		}

		// 去除多余的内容
		Element content = doc.getElementById("news_content");
		content.getElementsByClass("digbox").remove();
		content.getElementsByTag("a").remove();
		String htmlText = content.html();

		return htmlText;
	}

	protected String handleDocument(Document doc) {
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		webView.loadDataWithBaseURL(null, result, "text/html", "utf-8", null);
		super.onPostExecute(result);
	}

}
