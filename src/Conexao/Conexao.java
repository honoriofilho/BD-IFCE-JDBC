package Conexao;

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
            createTableMovies(conexao);
            createTableWatchlist(conexao);
            createTableStreaming(conexao);
        } catch (ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, "Driver de Banco de Dados não encontrado: " + ex.getMessage());
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados " + ex.getMessage());
        }
        return conexao;
    }

    private static void createTableMovies(Connection conexao) {
        if (conexao != null) {
            try (Statement statement = conexao.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS movies ("
                        + "id_movie INT AUTO_INCREMENT PRIMARY KEY ,"
                        + "titulo VARCHAR(255) NOT NULL,"
                        + "ano INT(4) NOT NULL,"
                        + "diretor VARCHAR(255) NOT NULL,"
                        + "genero VARCHAR(255) NOT NULL,"
                        + "id_streaming INT,"
                        + "FOREIGN KEY (id_streaming) REFERENCES streaming(id_streaming)"
                        + ")";
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao criar tabela: " + ex.getMessage());
            }
        }
    }

    private static void createTableWatchlist(Connection conexao) {
        if (conexao != null) {
            try (Statement statement = conexao.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS watchlist ("
                        + "id_watchlist INT AUTO_INCREMENT PRIMARY KEY,"
                        + "nome_watchlist VARCHAR(255) NOT NULL"
                        + ")";
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao criar tabela Watchlist: " + ex.getMessage());
            }
        }
    }

    private static void createTableStreaming(Connection conexao) {
        if (conexao != null) {
            try (Statement statement = conexao.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS streaming ("
                        + "id_streaming INT AUTO_INCREMENT PRIMARY KEY,"
                        + "nome_streaming VARCHAR(255) NOT NULL"
                        + ")";
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao criar tabela Streaming: " + ex.getMessage());
            }
        }
    }
}
