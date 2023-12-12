import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class MoviesDAO {

    public void register(Movies m) throws SQLException {

        Connection conexao = Conexao.getConnection();
        String sql = "INSERT INTO movies (titulo, ano, diretor, genero) VALUES (?,?,?,?)";

        try (PreparedStatement smt = conexao.prepareStatement(sql)){

            smt.setString(1, m.getTitulo());
            smt.setString(2, m.getAno());
            smt.setString(3, m.getDiretor());
            smt.setString(4,m.getGenero());

            smt.executeUpdate();
            smt.close();
            conexao.close();

            JOptionPane.showMessageDialog(null, "Registro salvo com sucesso!");

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage());
        }

    }

    public void update(Movies m) throws SQLException {

        Connection conexao = Conexao.getConnection();
        String sql = "UPDATE movies SET titulo = ?, ano = ?, diretor = ?, genero = ? WHERE id_movie = ?";

        try (PreparedStatement smt = conexao.prepareStatement(sql)){

            smt.setString(1, m.getTitulo());
            smt.setString(2, m.getAno());
            smt.setString(3, m.getDiretor());
            smt.setString(4,m.getGenero());
            smt.setInt(5, m.getIdMovie());

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
                smt.close();
                conexao.close();

                JOptionPane.showMessageDialog(null, "Registro excluído com sucesso");

            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir");
            }
        }
    }

    public void show(JTable tbMovies) throws SQLException{

        Connection conexao = Conexao.getConnection();
        String sql = "SELECT * FROM movies";
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

                String tbData[] = {id_movie,titulo,ano,diretor,genero};

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
}
