import us.codecraft.webmagic.*;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by likole on 8/4/17.
 */
public class MySpider implements PageProcessor {

    private Site site=Site.me().setSleepTime(1000).setRetryTimes(3).setCharset("utf-8").setRetrySleepTime(3000);

    @Override
    public void process(Page page) {
        if(page.getUrl().regex("https://www\\.dianping\\.com/huhehaote/hotel/\\w*").match()){
            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"page\"]").links().all());
            page.addTargetRequests(page.getHtml().xpath("//h2[@class=\"hotel-name\"]").links().all(),1);
            page.setSkip(true);
        }else if(page.getUrl().regex("https://www\\.dianping\\.com/shop/[0-9]*").match()){
            page.putField("title",page.getHtml().xpath("//*[@class=\"main-detail-left\"]//h1/text()").toString());
            page.putField("address",page.getHtml().xpath("//span[@class=\"hotel-address\"]/text()").toString());
            page.putField("score",page.getHtml().xpath("//span[@class=\"score\"]/text()").toString());
            String info=page.getHtml().xpath("//script[4]/html()").toString();
            info=info.substring(27);
            info=info.substring(0,info.length()-1);
            page.putField("info",info);
        }else{
            page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new MySpider()).addUrl("https://www.dianping.com/huhehaote/hotel/").addPipeline(new MyPipeline("res")).thread(5).run();
    }


}