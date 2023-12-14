package Watchlist;

import Conexao.Conexao;
import Watchlist.WatchList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class WatchListDAO {

    public void register(WatchList w) throws SQLException {

        Connection conexao = Conexao.getConnection();
        String sql = "INSERT INTO watchlist (nome_watchlist) VALUES (?)";

        try (PreparedStatement smt = conexao.prepareStatement(sql)){

            smt.setString(1, w.getNomeWatchList());

            smt.executeUpdate();
            smt.close();
            conexao.close();

            JOptionPane.showMessageDialog(null, "Registro salvo com sucesso!");

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage());
        }
    }

    public void show(JTable tbWL) throws SQLException{

        Connection conexao = Conexao.getConnection();
        String sql = "SELECT * FROM watchlist";
        DefaultTableModel tblModel = (DefaultTableModel) tbWL.getModel();

        tblModel.setRowCount(0);

        try (Statement st = conexao.createStatement()){

            ResultSet results = st.executeQuery(sql);
            ResultSetMetaData rsmd = results.getMetaData();

            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];

            for (int i=0; i<cols;i++) colName[i] = rsmd.getColumnName(i+1);
            tblModel.setColumnIdentifiers(colName);

            while (results.next()){
                String id_watchlist = String.valueOf(results.getInt("id_watchlist"));
                String nome_watchlist = results.getString("nome_watchlist");

                String tbData[] = {id_watchlist, nome_watchlist};

                tblModel.addRow(tbData);
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Erro ao listar filmes: " + ex.getMessage());
        }
        conexao.close();
    }

    public void update(WatchList w) throws SQLException {

        Connection conexao = Conexao.getConnection();
        String sql = "UPDATE watchlist SET nome_watchlist = ? WHERE id_watchlist = ?";

        try (PreparedStatement smt = conexao.prepareStatement(sql)){

            smt.setString(1, w.getNomeWatchList());
            smt.setInt(2, w.getIdWatchList());

            smt.executeUpdate();
            smt.close();
            conexao.close();

            JOptionPane.showMessageDialog(null, "Registro atualizado com sucesso!");


        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex.getMessage());
        }
    }

    public void search(JTable tbWL, JTextField txtPesquisa) throws SQLException{

        Connection conexao = Conexao.getConnection();
        String sql = "SELECT * FROM watchlist WHERE nome_watchlist LIKE '%" + txtPesquisa.getText() + "%'";

        DefaultTableModel tblModel = (DefaultTableModel) tbWL.getModel();
        tblModel.setRowCount(0);

        try (Statement st = conexao.createStatement()){

            ResultSet results = st.executeQuery(sql);
            ResultSetMetaData rsmd = results.getMetaData();

            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];

            for (int i=0; i<cols;i++) colName[i] = rsmd.getColumnName(i+1);
            tblModel.setColumnIdentifiers(colName);

            while (results.next()) {
                String id_watchlist = String.valueOf(results.getInt("id_watchlist"));
                String nome_watchlist = results.getString("nome_watchlist");

                String tbData[] = {id_watchlist, nome_watchlist};

                tblModel.addRow(tbData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conexao.close();
    }

    public void delete(WatchList w) throws SQLException {

        Connection conexao = Conexao.getConnection();
        String sql = "DELETE FROM watchlist WHERE id_watchlist = ?";
        int opcao = JOptionPane.showConfirmDialog(null, "Deseja excluir o filme: " + w.getNomeWatchList() + "?", "Excluir", JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_NO_OPTION){
            try (PreparedStatement smt = conexao.prepareStatement(sql)){

                smt.setInt(1, w.getIdWatchList());

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
