// Mengimpor kelas JDBC untuk koneksi dan eksekusi query SQL.
import java.sql.*;

public class Database {
    // Menyimpan koneksi aktif ke database.
    private Connection connection;
    // Menjalankan perintah SQL melalui koneksi tersebut.
    private Statement statement;

    // Constructor untuk menghubungkan Java dengan MySQL
    public Database() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/db_people",
                    "root",
                    ""
            );

            statement = connection.createStatement();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Digunakan untuk mengambil data (SELECT)
    public ResultSet selectQuery(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Digunakan untuk INSERT, UPDATE, dan DELETE
    public int insertUpdateDeleteQuery(String sql) {
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Getter untuk Statement
    public Statement getStatement() {
        return statement;
    }
}
