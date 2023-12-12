import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.lang.StringBuilder;
import java.sql.Statement;

public class Conexao {
    private static final String jdbc = "com.mysql.cj.jdbc.Driver";
    private static final String usuario = "root";
    private static final String senha = "123456";
    private static final String url = "jdbc:mysql://localhost/letterboxd";

    public static Connection getConnection() throws SQLException {

        Connection conexao = null;

        try{
            Class.forName(jdbc);
            conexao = DriverManager.getConnection(url,usuario,senha);
            createTable(conexao);
        } catch (ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, "Driver de Banco de Dados não encontrado: " + ex.getMessage());
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados " + ex.getMessage());
        }
        return conexao;
    }

    private static void createTable(Connection conexao) {
        if (conexao != null) {
            try (Statement statement = conexao.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS movies ("
                        + "id_movie INT AUTO_INCREMENT PRIMARY KEY,"
                        + "titulo VARCHAR(255),"
                        + "ano INT,"
                        + "diretor VARCHAR(255),"
                        + "genero VARCHAR(255)"
                        + ")";
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao criar tabela: " + ex.getMessage());
            }
        }
    }
}
