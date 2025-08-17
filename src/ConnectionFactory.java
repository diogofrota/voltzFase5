import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {
    private static final String URL  = "xxx";
    private static final String USER = "xxx";
    private static final String PASS = "xxx";

    private ConnectionFactory() {}

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter conexão com o Oracle", e);
        }
    }
}


