import us.codecraft.webmagic.Spider;

/**
 * Created by likole on 8/5/17.
 */
public class test {

    public static void main(String[] args) {


        /*
         * 酒店数据
         */

        Spider.create(new HotelSpider()).addUrl("https://www.dianping.com/huhehaote/hotel/").addPipeline(new HotelPipeline("res")).thread(5000).run();


        /*
         * 景点数据
         */
        Spider spider = Spider.create(new FeatureSpotSpider());
        spider.addUrl("http://you.ctrip.com/sight/beijing1/s0-p1.html");
        spider.addPipeline(new FeatureSpotPipeline("res")).thread(5000).run();
    }
}
