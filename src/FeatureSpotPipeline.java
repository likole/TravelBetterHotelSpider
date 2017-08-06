import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by likole on 8/5/17.
 */
public class FeatureSpotPipeline extends FilePersistentBase implements Pipeline {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public FeatureSpotPipeline(String path) {
        this.setPath(path);
    }

    public void process(ResultItems resultItems, Task task) {
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO  FeatureSpot (title, score, address, `time`, price, description, tip) VALUES (?,?,?,?,?,?,?)");
            preparedStatement.setString(1, resultItems.get("title"));
            preparedStatement.setString(2, resultItems.get("score"));
            preparedStatement.setString(3, resultItems.get("address"));
            preparedStatement.setString(4, resultItems.get("time"));
            preparedStatement.setString(5, resultItems.get("price"));
            preparedStatement.setString(6, resultItems.get("description"));
            preparedStatement.setString(7, resultItems.get("tip"));
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}