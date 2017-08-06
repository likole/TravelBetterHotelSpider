import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by likole on 8/5/17.
 */
public class HotelPipeline extends FilePersistentBase implements Pipeline {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public HotelPipeline(String path) {
        this.setPath(path);
    }

    public void process(ResultItems resultItems, Task task) {
        String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;

        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(this.getFile(path + resultItems.get("title")) + ".json"));
            BufferedWriter bufferedWriter = new BufferedWriter(printWriter);
            String info = resultItems.get("info");
            bufferedWriter.write(info, 0, info.length());
            bufferedWriter.close();

            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Hotel (title, score, address) VALUES (?,?,?)");
            preparedStatement.setString(1, resultItems.get("title"));
            preparedStatement.setString(2, resultItems.get("score"));
            preparedStatement.setString(3, resultItems.get("address"));
            preparedStatement.execute();
            preparedStatement.close();
        } catch (IOException var5) {
            this.logger.warn("write file error", var5);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}