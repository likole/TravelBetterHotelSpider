import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by likole on 8/5/17.
 */
public class FeatureSpotSpider implements PageProcessor {

    Site site = Site.me().setSleepTime(1000).setRetryTimes(3).setRetrySleepTime(1000).setCharset("utf-8");


    @Override
    public void process(Page page) {
        if (page.getUrl().regex("http://you\\.ctrip\\.com/sight/beijing1/s[0-9]*-p[0-9]*.html\\.*").match()) {
            page.setSkip(true);
//            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"pager_v1\"]").links().all());
            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"rdetailbox\"]//dt").links().all(), 1);
        } else if (page.getUrl().regex("http://you\\.ctrip\\.com/sight/beijing1/[0-9]+.html").match()) {
            page.putField("title", page.getHtml().xpath("//h1/text()").toString());
            page.putField("score", page.getHtml().xpath("//ul[@class=\"detailtop_r_info\"]//span[@class=\"score\"]/b/text()").toString());
            page.putField("address", page.getHtml().xpath("//p[@class=\"s_sight_addr\"]/text()").toString().substring(3));
            page.putField("time", page.getHtml().xpath("//dl[@class=\"s_sight_in_list\"][1]/dd/text()").toString());
            page.putField("price", page.getHtml().xpath("//dl[@class=\"s_sight_in_list\"][2]/dd/text()").toString());
            page.putField("description", StringUtils.join(page.getHtml().xpath("//div[@class=\"detailcon detailbox_dashed\"]//div[@class=\"toggle_l\"]//p/html()").all(), "<br/>"));
            page.putField("tip", StringUtils.join(page.getHtml().xpath("//div[@class=\"detailcon detailbox_dashed\"][2]//div[@class=\"toggle_l\"]//p/html()").all(), "<br/>"));
        } else {
            page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
