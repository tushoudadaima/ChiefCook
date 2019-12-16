package pachong.search.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import pachong.search.entity.Meterial;
import pachong.search.entity.Methods;
import  pachong.search.entity.Show;

public class Content {
	/**
	 * 爬取食品材料
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public List<Meterial> findMeterial(String name) throws IOException{
		List<Meterial> list = new ArrayList<>();
		String url = "https://www.douguo.com/search/recipe/"+name;
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByClass("cook-list");
        Elements li = elements.select("li");
        String url2 = li.get(0).select("a").attr("href");
        String lastUrl = "https://www.douguo.com"+url2;
        Document document2 = Jsoup.connect(lastUrl).get();
        Elements elements2 = document2.select("table");
        Elements table = elements2.select("td");
        Elements vname = document2.getElementsByClass("scname");
        Elements count = document2.getElementsByClass("right scnum");
        for(int i=0;i<vname.size();i++) {
        	Meterial meterial = new Meterial();
        	meterial.setVname(vname.get(i).text());
        	if(count.get(i).text().equals("")) {
        		meterial.setCount("适量");
        	}else {
				meterial.setCount(count.get(i).text());
			}
        	list.add(meterial);
        }
        return list;
	}
	
	/**
	 * 爬取做法步骤
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public List<Methods> findMethods(String name) throws IOException{
		List<Methods> list = new ArrayList<Methods>();
		String url = "https://www.douguo.com/search/recipe/"+name;
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByClass("cook-list");
        Elements li = elements.select("li");
        String url2 = li.get(0).select("a").attr("href");
        String lastUrl = "https://www.douguo.com"+url2;
        Document document2 = Jsoup.connect(lastUrl).get();
        Elements elements2 = document2.getElementsByClass("step");
        Elements table = elements2.select("div .stepinfo");
        Elements tables = document2.getElementsByClass("stepcont clearfix");
        for(int i=0;i<table.size();i++) {
        	String iString = tables.get(i).select("img").attr("src");
        	String cString = table.get(i).text();
        	list.add(new Methods(iString,cString));
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
	public List<Show> findList(String gname) throws IOException{
		Random random = new Random();
		int a= random.nextInt(10);
		String[] array = {"0","20","40","60","80","100","120","140","160","180"};
		List<Show> list = new ArrayList<Show>();
		String url = "https://www.douguo.com/search/recipe/"+gname+"/0/"+array[a];
		Document document = Jsoup.connect(url).get();
		Elements elements = document.getElementsByClass("cook-list");
        Elements li = elements.select("li");
        Random random2 = new Random();
        int j = random2.nextInt(14);
        for(int i=j;i<j+6;i++) {
        	Show show = new Show();
        	String url2 = li.get(i).select("a").attr("href");
        	String imgUrl = "https://www.douguo.com"+url2;
        	Document document2 = Jsoup.connect(imgUrl).get();
        	Elements elements2 = document2.getElementsByClass("wb100");
        	Elements div = document.getElementsByClass("cook-info");
        	String name = div.get(i).getElementsByClass("cookname text-lips ").text();
        	String description = div.get(i).select("p").text();
        	String imgUrl2 = elements2.get(0).attr("src");
        	show.setName(name);
        	show.setDescription(description);
        	show.setImgUrl(imgUrl2);
        	list.add(show);
        }
        System.out.println("好了");
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
	
	public String wordDown(String name) throws IOException {
		String url = "https://www.douguo.com/search/recipe/"+name;
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByClass("cook-list");
        Elements li = elements.select("li");
        String url2 = li.get(0).select("a").attr("href");
        String lastUrl = "https://www.douguo.com"+url2;
        Document document2 = Jsoup.connect(lastUrl).get();
        String word = document2.getElementsByClass("intro").get(0).text();
		return word;
	}
	
	
	public String findVideos(String name) throws IOException{
//		String url = "https://haokan.baidu.com/videoui/page/pc/search?query=a";
//        Document document = Jsoup.connect(url).get();
//        Elements elements = document.getElementsByClass("list-container videolist clearfix");
//        String aaa=elements.get(0).attr("href");
//        String url2 = elements.attr("class");
		List<String> abc=new ArrayList<>();
		abc.add("https://haokan.baidu.com/v?vid=9970259205291125340");
		abc.add("https://haokan.baidu.com/v?vid=6717878311264858082");
		abc.add("https://haokan.baidu.com/v?vid=11722132264680992903");
		abc.add("https://haokan.baidu.com/v?vid=4513957554359102937");
		abc.add("https://haokan.baidu.com/v?vid=243235490585027154");
		abc.add("https://haokan.baidu.com/v?vid=6305955569337390080");
		abc.add("https://haokan.baidu.com/v?vid=9588690204719678953");
		abc.add("https://haokan.baidu.com/v?vid=8291911799773708323");
		abc.add("https://haokan.baidu.com/v?vid=18412591728194063446");
		abc.add("https://haokan.baidu.com/v?vid=3811045689884498854");

		String rrr = "";
		for(int i=0;i<abc.size();i++) {
			String lastUrl=abc.get(i);
			Document document2 = Jsoup.connect(lastUrl).get();
	        Elements elements2 = document2.getElementsByClass("video");
	        String url3=elements2.attr("src");
	        rrr+=url3+"\n";
		}
 
        return rrr;
        
	}
}
