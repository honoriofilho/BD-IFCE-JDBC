package Watchlist;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class WatchList extends WatchListForm {

    private int idWatchList;
    private String nomeWatchList;

    public int getIdWatchList() {
        return idWatchList;
    }

    public void setIdWatchList(int idWatchList) {
        this.idWatchList = idWatchList;
    }

    public String getNomeWatchList() {
        return nomeWatchList;
    }

    public void setNomeWatchList(String nomeWatchList) {
        this.nomeWatchList = nomeWatchList;
    }

    @Override
    protected void btnSalvarClick(ActionEvent ev) throws SQLException {
        WatchList w = new WatchList();
        w.setNomeWatchList(txtNomeWL.getText());

        WatchListDAO watchListDAO = new WatchListDAO();
        watchListDAO.register(w);

        txtWL.setText("");
        txtNomeWL.setText("");

        //moviesDAO.show(tbMovies);
    }

    @Override
    protected void btnFecharClick(ActionEvent ev) {
        this.setVisible(false);
        this.dispose();
    }

    @Override
    protected void btnListarClick(ActionEvent ev) throws SQLException {
        WatchListDAO watchListDAO = new WatchListDAO();
        watchListDAO.show(tbWL);
    }

    protected void btnEditarClick(ActionEvent ev) throws SQLException{
        int opcao = tbWL.getSelectedRow();

        if (opcao >= 0) {
            txtWL.setText(tbWL.getValueAt(opcao, 0).toString());
            txtNomeWL.setText(tbWL.getValueAt(opcao, 1).toString());

            btnEditar.setVisible(false);
            btnSalvar.setVisible(false);
            btnAtualizar.setVisible(true);

        }  else {
            JOptionPane.showMessageDialog(null, "Selecione uma Linha");
        }
    }

    protected void btnAtualizarClick(ActionEvent ev) throws SQLException{

        WatchList w = new WatchList();
        w.setIdWatchList(Integer.parseInt(txtWL.getText()));
        w.setNomeWatchList(txtNomeWL.getText());

        WatchListDAO watchListDAO = new WatchListDAO();
        watchListDAO.update(w);

        txtWL.setText("");
        txtNomeWL.setText("");

        watchListDAO.show(tbWL);

        btnEditar.setVisible(true);
        btnSalvar.setVisible(true);
        btnAtualizar.setVisible(false);
    }

    @Override
    protected void btnPesquisarClick(ActionEvent ev) throws SQLException {
        WatchListDAO watchListDAO = new WatchListDAO();

        watchListDAO.search(tbWL, txtPesquisa);
    }
    protected void btnExcluirClick(ActionEvent ev) throws SQLException{

        int opcao = tbWL.getSelectedRow();

        if (opcao >= 0){
            WatchList w = new WatchList();
            w.setIdWatchList(Integer.parseInt(tbWL.getValueAt(opcao,0).toString()));
            w.setNomeWatchList(tbWL.getValueAt(opcao, 1).toString());

            WatchListDAO watchListDAO = new WatchListDAO();
            watchListDAO.delete(w);
            watchListDAO.show(tbWL);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma Linha");
        }
    }
}
