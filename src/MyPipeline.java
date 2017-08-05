import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by likole on 8/5/17.
 */
public class MyPipeline extends FilePersistentBase implements Pipeline {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public MyPipeline(String path) {
        this.setPath(path);
    }

    public void process(ResultItems resultItems, Task task) {
        String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;

        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(this.getFile(path + resultItems.get("title")) + ".json"));
            BufferedWriter bufferedWriter=new BufferedWriter(printWriter);
            String info=resultItems.get("info");
            bufferedWriter.write(info,0,info.length());
            bufferedWriter.close();
            DBUtil.add(resultItems.get("title"),resultItems.get("address"),resultItems.get("score"));
        } catch (IOException var5) {
            this.logger.warn("write file error", var5);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}