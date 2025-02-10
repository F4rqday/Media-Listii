import java.sql.*;

public class DatabaseManager {
    private static final String DB_NAME = "media_db";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Aibn2007";

    private Connection connection;

    public DatabaseManager() {
        createDatabaseIfNotExists();
        connectToDatabase();
        createTableIfNotExists();
    }

    private void createDatabaseIfNotExists() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT 1 FROM pg_database WHERE datname = '" + DB_NAME + "'";
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.next()) {
                stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
                System.out.println(" Database created: " + DB_NAME);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + DB_NAME, USER, PASSWORD);
            System.out.println(" Connected to PostgreSQL: " + DB_NAME);
        } catch (SQLException e) {
            System.out.println(" Connection failed!");
            e.printStackTrace();
        }
    }

    private void createTableIfNotExists() {
        String sql = """
                CREATE TABLE IF NOT EXISTS media (
                    id SERIAL PRIMARY KEY,
                    title VARCHAR(255) UNIQUE NOT NULL,
                    status VARCHAR(50) NOT NULL,
                    rating DOUBLE PRECISION DEFAULT 0,
                    type VARCHAR(50) NOT NULL
                );
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMedia(String title, String status, double rating, String type) {
        String sql = "INSERT INTO media (title, status, rating, type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, status);
            stmt.setDouble(3, rating);
            stmt.setString(4, type);
            stmt.executeUpdate();
            System.out.println(" Media added to database: " + title);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMediaStatus(String title, String newStatus) {
        String sql = "UPDATE media SET status = ? WHERE title = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setString(2, title);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(" Media status updated: " + title + " → " + newStatus);
            } else {
                System.out.println(" Media not found: " + title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMedia(String title) {
        String sql = "DELETE FROM media WHERE title = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println(" Media deleted: " + title);
            } else {
                System.out.println(" Media not found: " + title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayMedia() {
        String sql = "SELECT * FROM media";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println(" Media List:");
            while (rs.next()) {
                System.out.println(rs.getString("type") + ": " +
                        rs.getString("title") + " [" +
                        rs.getString("status") + "] Rating: " +
                        rs.getDouble("rating"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sortMediaByTitle() {
        String sql = "SELECT * FROM media ORDER BY title ASC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println(" Sorted by title:");
            while (rs.next()) {
                System.out.println(rs.getString("type") + ": " +
                        rs.getString("title") + " [" +
                        rs.getString("status") + "] Rating: " +
                        rs.getDouble("rating"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sortMediaByRating() {
        String sql = "SELECT * FROM media ORDER BY rating DESC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println(" Sorted by rating:");
            while (rs.next()) {
                System.out.println(rs.getString("type") + ": " +
                        rs.getString("title") + " [" +
                        rs.getString("status") + "] Rating: " +
                        rs.getDouble("rating"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println(" Connection to PostgreSQL closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
