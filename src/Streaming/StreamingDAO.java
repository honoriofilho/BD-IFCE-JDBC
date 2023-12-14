package Streaming;

import Conexao.Conexao;
import Watchlist.WatchList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class StreamingDAO {
    public void register(Streaming s) throws SQLException {

        Connection conexao = Conexao.getConnection();
        String sql = "INSERT INTO streaming (nome_streaming) VALUES (?)";

        try (PreparedStatement smt = conexao.prepareStatement(sql)){

            smt.setString(1, s.getNomeStreaming());

            smt.executeUpdate();
            smt.close();
            conexao.close();

            JOptionPane.showMessageDialog(null, "Registro salvo com sucesso!");

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage());
        }
    }

    public void show(JTable tbStreaming) throws SQLException{

        Connection conexao = Conexao.getConnection();
        String sql = "SELECT * FROM streaming";
        DefaultTableModel tblModel = (DefaultTableModel) tbStreaming.getModel();

        tblModel.setRowCount(0);

        try (Statement st = conexao.createStatement()){

            ResultSet results = st.executeQuery(sql);
            ResultSetMetaData rsmd = results.getMetaData();

            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];

            for (int i=0; i<cols;i++) colName[i] = rsmd.getColumnName(i+1);
            tblModel.setColumnIdentifiers(colName);

            while (results.next()){
                String id_streaming = String.valueOf(results.getInt("id_streaming"));
                String nome_streaming = results.getString("nome_streaming");

                String tbData[] = {id_streaming, nome_streaming};

                tblModel.addRow(tbData);
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Erro ao listar filmes: " + ex.getMessage());
        }
        conexao.close();
    }

    public void update(Streaming s) throws SQLException {

        Connection conexao = Conexao.getConnection();
        String sql = "UPDATE streaming SET nome_streaming = ? WHERE id_streaming = ?";

        try (PreparedStatement smt = conexao.prepareStatement(sql)){

            smt.setString(1, s.getNomeStreaming());
            smt.setInt(2, s.getIdStreaming());

            smt.executeUpdate();
            smt.close();
            conexao.close();

            JOptionPane.showMessageDialog(null, "Registro atualizado com sucesso!");


        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex.getMessage());
        }
    }

    public void search(JTable tbStreaming, JTextField txtPesquisa) throws SQLException{

        Connection conexao = Conexao.getConnection();
        String sql = "SELECT * FROM streaming WHERE nome_streaming LIKE '%" + txtPesquisa.getText() + "%'";

        DefaultTableModel tblModel = (DefaultTableModel) tbStreaming.getModel();
        tblModel.setRowCount(0);

        try (Statement st = conexao.createStatement()){

            ResultSet results = st.executeQuery(sql);
            ResultSetMetaData rsmd = results.getMetaData();

            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];

            for (int i=0; i<cols;i++) colName[i] = rsmd.getColumnName(i+1);
            tblModel.setColumnIdentifiers(colName);

            while (results.next()) {
                String id_streaming = String.valueOf(results.getInt("id_streaming"));
                String nome_streaming = results.getString("nome_streaming");

                String tbData[] = {id_streaming, nome_streaming};

                tblModel.addRow(tbData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conexao.close();
    }

    public void delete(Streaming s) throws SQLException {

        Connection conexao = Conexao.getConnection();
        String sql = "DELETE FROM streaming WHERE id_streaming = ?";
        int opcao = JOptionPane.showConfirmDialog(null, "Deseja excluir o streaming: " + s.getNomeStreaming() + "?", "Excluir", JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_NO_OPTION){
            try (PreparedStatement smt = conexao.prepareStatement(sql)){

                smt.setInt(1, s.getIdStreaming());

                smt.executeUpdate();
                smt.close();
                conexao.close();

                JOptionPane.showMessageDialog(null, "Registro excluído com sucesso");

            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex.getMessage());
            }
        }
    }
}
