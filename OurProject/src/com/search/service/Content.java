package com.search.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.search.entity.Show;

public class Content {
	/**
	 * 爬取食品材料
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public List<String> findMeterial(String name) throws IOException{
		List<String> list = new ArrayList<String>();
		String url = "https://www.douguo.com/search/recipe/"+name;
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByClass("cook-list");
        Elements li = elements.select("li");
        String url2 = li.get(0).select("a").attr("href");
        String lastUrl = "https://www.douguo.com"+url2;
        Document document2 = Jsoup.connect(lastUrl).get();
        Elements elements2 = document2.select("table");
        Elements table = elements2.select("td");
        for(int i=0;i<table.size();i++) {
        	String cString = table.get(i).text();
        	list.add(cString);
        }
        return list;
        
	}
	
	/**
	 * 爬取做法步骤
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public List<String> findMethods(String name) throws IOException{
		List<String> list = new ArrayList<String>();
		String url = "https://www.douguo.com/search/recipe/"+name;
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByClass("cook-list");
        Elements li = elements.select("li");
        String url2 = li.get(0).select("a").attr("href");
        String lastUrl = "https://www.douguo.com"+url2;
        Document document2 = Jsoup.connect(lastUrl).get();
        Elements elements2 = document2.getElementsByClass("step");
        Elements element = elements2.select("h2");
        System.out.println(element.text());
        Elements table = elements2.select("div .stepinfo");
        for(int i=0;i<table.size();i++) {
        	String cString = table.get(i).text();
        	list.add(cString);
//        	System.out.println(cString+"\n");
        }
		return list;
	}
	
	/**
	 * 爬取详情页图片
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public String imgDown(String name) throws IOException {
        String url = "https://www.douguo.com/search/recipe/"+name;
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByClass("cook-list");
        Elements li = elements.select("li");
        String url2 = li.get(0).select("a").attr("href");
        String lastUrl = "https://www.douguo.com"+url2;
        Document document2 = Jsoup.connect(lastUrl).get();
        Element element2 = document2.getElementById("banner");
        Elements element = element2.select("a");
        String str = element.get(0).select("img").attr("src");
		return str;
	}
	
	/**
	 * 爬取到首页列表
	 * @return
	 * @throws IOException
	 */
	public List<Show> findList() throws IOException{
		List<Show> list = new ArrayList<Show>();
		String url = "https://www.douguo.com/search/recipe/减肥餐";
		Document document = Jsoup.connect(url).get();
		Elements elements = document.getElementsByClass("cook-list");
        Elements li = elements.select("li");
        for(int i=0;i<li.size();i++) {
        	Show show = new Show();
        	String url2 = li.get(i).select("a").attr("href");
        	String imgUrl = "https://www.douguo.com"+url2;
        	Elements div = document.getElementsByClass("cook-info");
        	String name = div.get(i).getElementsByClass("cookname text-lips ").text();
        	String description = div.get(i).select("p").text();
        	show.setName(name);
        	show.setDescription(description);
        	show.setImgUrl(imgUrl);
        	list.add(show);
        }
		return list;
	}
	
	/**
	 * 根据做法，分类爬取列表
	 * @param name
	 * @return
	 * @throws IOException 
	 */
	public List<Show> findListByName(String gname) throws IOException{
		Random random = new Random();
		int a= random.nextInt(10);
		String[] array = {"0","20","40","60","80","100","120","140","160","180"};
		List<Show> list = new ArrayList<Show>();
		String url = "https://www.douguo.com/search/recipe/"+gname+"/0/"+array[a];
		Document document = Jsoup.connect(url).get();
		Elements elements = document.getElementsByClass("cook-list");
        Elements li = elements.select("li");
        for(int i=0;i<li.size();i++) {
        	Show show = new Show();
        	String url2 = li.get(i).select("a").attr("href");
        	String imgUrl = "https://www.douguo.com"+url2;
        	Document document2 = Jsoup.connect(imgUrl).get();
        	Elements elements2 = document2.getElementsByClass("wb100");
//        	Elements e2 = elements2.select("img");
        	Elements div = document.getElementsByClass("cook-info");
        	String name = div.get(i).getElementsByClass("cookname text-lips ").text();
        	String description = div.get(i).select("p").text();
        	String imgUrl2 = elements2.get(0).attr("src");
        	show.setName(name);
        	show.setDescription(description);
        	show.setImgUrl(imgUrl2);
        	list.add(show);
        }
		return list;
	}
}
