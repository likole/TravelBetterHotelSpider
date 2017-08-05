import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by likole on 8/5/17.
 */
public class DBUtil {

    private static String driver="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://localhost:3306/TravelBetter?useUnicode=true&characterEncoding=utf8";
    private static String username="root";
    private static String password="7117c5d8d2635b69";
    private static Connection connection=null;

    static {
        try {
            Class.forName(driver);
            connection= DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static void add(String title,String address,String score) throws SQLException {
        PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO Hotel (title, score, address) VALUES (?,?,?)");
        preparedStatement.setString(1,title);
        preparedStatement.setString(2,score);
        preparedStatement.setString(3,address);
        preparedStatement.execute();
        preparedStatement.close();
    }
}
