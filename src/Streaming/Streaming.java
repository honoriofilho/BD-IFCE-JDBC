package Streaming;

import Watchlist.WatchList;
import Watchlist.WatchListDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class Streaming extends StreamingForm{
    private int idStreaming;
    private String nomeStreaming;
    public int getIdStreaming() {
        return idStreaming;
    }

    public void setIdStreaming(int idStreaming) {
        this.idStreaming = idStreaming;
    }

    public String getNomeStreaming() {
        return nomeStreaming;
    }

    public void setNomeStreaming(String nomeStreaming) {
        this.nomeStreaming = nomeStreaming;
    }


    @Override
    protected void btnSalvarClick(ActionEvent ev) throws SQLException {
        Streaming s = new Streaming();
        s.setNomeStreaming(txtNome.getText());

        StreamingDAO streamingDAO = new StreamingDAO();
        streamingDAO.register(s);

        txtID.setText("");
        txtNome.setText("");

        //moviesDAO.show(tbMovies);
    }

    @Override
    protected void btnFecharClick(ActionEvent ev) {
        this.setVisible(false);
        this.dispose();
    }

    @Override
    protected void btnListarClick(ActionEvent ev) throws SQLException {
        StreamingDAO streamingDAO = new StreamingDAO();
        streamingDAO.show(tbStreaming);
    }

    protected void btnEditarClick(ActionEvent ev) throws SQLException{
        int opcao = tbStreaming.getSelectedRow();

        if (opcao >= 0) {
            txtID.setText(tbStreaming.getValueAt(opcao, 0).toString());
            txtNome.setText(tbStreaming.getValueAt(opcao, 1).toString());

            btnEditar.setVisible(false);
            btnSalvar.setVisible(false);
            btnAtualizar.setVisible(true);

        }  else {
            JOptionPane.showMessageDialog(null, "Selecione uma Linha");
        }
    }

    protected void btnAtualizarClick(ActionEvent ev) throws SQLException{

        Streaming s = new Streaming();
        s.setIdStreaming(Integer.parseInt(txtID.getText()));
        s.setNomeStreaming(txtNome.getText());

        StreamingDAO streamingDAO = new StreamingDAO();
        streamingDAO.update(s);

        txtID.setText("");
        txtNome.setText("");

        streamingDAO.show(tbStreaming);

        btnEditar.setVisible(true);
        btnSalvar.setVisible(true);
        btnAtualizar.setVisible(false);
    }

    @Override
    protected void btnPesquisarClick(ActionEvent ev) throws SQLException {
        StreamingDAO streamingDAO = new StreamingDAO();

        streamingDAO.search(tbStreaming, txtPesquisa);
    }
    protected void btnExcluirClick(ActionEvent ev) throws SQLException{

        int opcao = tbStreaming.getSelectedRow();

        if (opcao >= 0){
            Streaming s = new Streaming();
            s.setIdStreaming(Integer.parseInt(tbStreaming.getValueAt(opcao,0).toString()));
            s.setNomeStreaming(tbStreaming.getValueAt(opcao, 1).toString());

            StreamingDAO streamingDAO = new StreamingDAO();
            streamingDAO.delete(s);
            streamingDAO.show(tbStreaming);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma Linha");
        }
    }
}
