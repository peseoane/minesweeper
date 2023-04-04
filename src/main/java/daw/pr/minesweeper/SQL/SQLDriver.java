package daw.pr.minesweeper.SQL;

import java.sql.*;
import java.util.ArrayList;

public class SQLDriver {

    private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";

    public SQLDriver() {}

    public ArrayList<String[]> ejecutarQuery(String query) {
        ArrayList<String[]> result = new ArrayList<>();

        try (
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)
        ) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();

            while (rs.next()) {
                String[] row = new String[numColumns];
                for (int i = 1; i <= numColumns; i++) {
                    row[i - 1] = rs.getString(i);
                }
                result.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public String ejecutarActualizacion(String query) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
