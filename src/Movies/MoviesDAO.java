package Movies;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Conexao.Conexao;

public class MoviesDAO {

    public void register(Movies m, JComboBox cmbStreaming) throws SQLException {

        Connection conexao = Conexao.getConnection();
        String sql = "INSERT INTO movies (titulo, ano, diretor, genero, id_streaming) VALUES (?,?,?,?,?)";

        try (PreparedStatement smt = conexao.prepareStatement(sql)){

            smt.setString(1, m.getTitulo());
            smt.setString(2, m.getAno());
            smt.setString(3, m.getDiretor());
            smt.setString(4,m.getGenero());

            // Obtém o nome do streaming selecionado
            String selectedStreaming = (String) cmbStreaming.getSelectedItem();

            int selectedStreamingId = getIdStreaming(selectedStreaming);
            System.out.println(selectedStreamingId);

            // Configura o id_streaming na PreparedStatement
            smt.setInt(5, getIdStreaming(selectedStreaming));

            smt.executeUpdate();
            smt.close();
            conexao.close();

            JOptionPane.showMessageDialog(null, "Registro salvo com sucesso!");

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage());
        }

    }

    public void update(Movies m, JComboBox cmbStreaming) throws SQLException {

        Connection conexao = Conexao.getConnection();
        String sql = "UPDATE movies SET titulo = ?, ano = ?, diretor = ?, genero = ?, id_streaming = ? WHERE id_movie = ?";

        try (PreparedStatement smt = conexao.prepareStatement(sql)){

            smt.setString(1, m.getTitulo());
            smt.setString(2, m.getAno());
            smt.setString(3, m.getDiretor());
            smt.setString(4,m.getGenero());
            smt.setInt(6, m.getIdMovie());

            // Obtém o nome do streaming selecionado
            String selectedStreaming = (String) cmbStreaming.getSelectedItem();

            int selectedStreamingId = getIdStreaming(selectedStreaming);
            System.out.println(selectedStreamingId);

            // Configura o id_streaming na PreparedStatement
            smt.setInt(5, getIdStreaming(selectedStreaming));

            smt.executeUpdate();
            smt.close();
            conexao.close();

            JOptionPane.showMessageDialog(null, "Registro atualizado com sucesso!");


        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar");
        }

    }

    public void delete(Movies m) throws SQLException {

        Connection conexao = Conexao.getConnection();
        String sql = "DELETE FROM movies WHERE id_movie = ?";
        int opcao = JOptionPane.showConfirmDialog(null, "Deseja excluir o filme: " + m.getTitulo() + "?", "Excluir", JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_NO_OPTION){
            try (PreparedStatement smt = conexao.prepareStatement(sql)){

                smt.setInt(1, m.getIdMovie());

                smt.executeUpdate();
                conexao.close();

                JOptionPane.showMessageDialog(null, "Registro excluído com sucesso");

            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir");
            }
        }
    }

    public void show(JTable tbMovies) throws SQLException{

        Connection conexao = Conexao.getConnection();
        String sql =    "SELECT a.id_movie, a.titulo, a.ano, a.diretor, a.genero, b.nome_streaming "
                        +"FROM movies AS a "
                        +"INNER JOIN streaming AS b ON b.id_streaming = a.id_streaming";
        DefaultTableModel tblModel = (DefaultTableModel) tbMovies.getModel();

        tblModel.setRowCount(0);

        try (Statement st = conexao.createStatement()){

            ResultSet results = st.executeQuery(sql);
            ResultSetMetaData rsmd = results.getMetaData();

            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];

            for (int i=0; i<cols;i++) colName[i] = rsmd.getColumnName(i+1);
            tblModel.setColumnIdentifiers(colName);

            while (results.next()){
                String id_movie = String.valueOf(results.getInt("id_movie"));
                String titulo = results.getString("titulo");
                String ano = String.valueOf(results.getInt("ano"));
                String diretor = results.getString("diretor");
                String genero = results.getString("genero");
                String nome_streaming = results.getString("nome_streaming");

                String tbData[] = {id_movie,titulo,ano,diretor,genero, nome_streaming};

                tblModel.addRow(tbData);
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Erro ao listar filmes");
        }
        conexao.close();
    }

    public void search(JTable tbMovies, JTextField txtPesquisa, String columnName) throws SQLException{

        Connection conexao = Conexao.getConnection();
        String sql = "SELECT * FROM movies WHERE " + columnName + " LIKE '%" + txtPesquisa.getText() + "%'";

        DefaultTableModel tblModel = (DefaultTableModel) tbMovies.getModel();
        tblModel.setRowCount(0);

        try (Statement st = conexao.createStatement()){

            ResultSet results = st.executeQuery(sql);
            ResultSetMetaData rsmd = results.getMetaData();

            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];

            for (int i=0; i<cols;i++) colName[i] = rsmd.getColumnName(i+1);
            tblModel.setColumnIdentifiers(colName);

            while (results.next()) {
                String id_movie = String.valueOf(results.getInt("id_movie"));
                String titulo = results.getString("titulo");
                String ano = String.valueOf(results.getInt("ano"));
                String diretor = results.getString("diretor");
                String genero = results.getString("genero");

                String tbData[] = {id_movie,titulo,ano,diretor,genero};

                tblModel.addRow(tbData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conexao.close();
    }

    private int getIdStreaming(String nomeStreaming) throws SQLException {
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement smt = conexao.prepareStatement("SELECT id_streaming FROM streaming WHERE nome_streaming = ?")) {

            smt.setString(1, nomeStreaming);

            try (ResultSet resultSet = smt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_streaming");
                } else {
                    // Handle caso não encontre o streaming
                    return -1; // ou lance uma exceção, dependendo do seu caso
                }
            }
        }
    }

    public List<String> getNomeStreaming(){
        List<String> streamingNames = new ArrayList<>();

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement statement = conexao.prepareStatement("SELECT nome_streaming FROM streaming");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                streamingNames.add(resultSet.getString("nome_streaming"));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao obter nomes de streaming: " + ex.getMessage());
        }

        return streamingNames;
    }
}
