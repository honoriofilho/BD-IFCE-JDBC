import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class Movies extends MoviesForm{
    private int idMovie;
    private String titulo;
    private String ano;
    private String diretor;
    private String genero;

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    protected void btnSalvarClick(ActionEvent ev) throws SQLException {
        Movies m = new Movies();
        m.setTitulo(txtTitulo.getText());
        m.setAno(txtAno.getText());
        m.setDiretor(txtDiretor.getText());
        m.setGenero(txtGenero.getText());

        System.out.println(m.getTitulo());
        System.out.println(m.getGenero());
        System.out.println(m.getDiretor());
        System.out.println(m.getAno());

        MoviesDAO moviesDAO = new MoviesDAO();
        moviesDAO.register(m);

        txtID.setText("");
        txtTitulo.setText("");
        txtAno.setText("");
        txtDiretor.setText("");
        txtGenero.setText("");

        moviesDAO.show(tbMovies);
    }

    @Override
    protected void btnFecharClick(ActionEvent ev) {
        this.setVisible(false);
        this.dispose();
    }

    @Override
    protected void btnListarClick(ActionEvent ev) throws SQLException {
        MoviesDAO moviesDAO = new MoviesDAO();
        moviesDAO.show(tbMovies);
    }

    @Override
    protected void btnPesquisarClick(ActionEvent ev) throws SQLException {
        MoviesDAO moviesDAO = new MoviesDAO();
        String columnName;
        if (rdbAno.isSelected()) {
            columnName = "ano";
        } else if (rdbDiretor.isSelected()){
            columnName = "diretor";
        } else if (rdbGenero.isSelected()) {
            columnName = "genero";
        } else {
            columnName = "titulo";
        }

        moviesDAO.search(tbMovies, txtPesquisa, columnName);
    }

    protected void btnExcluirClick(ActionEvent ev) throws SQLException{

        int opcao = tbMovies.getSelectedRow();

        if (opcao >= 0){
            Movies m = new Movies();
            m.setIdMovie(Integer.parseInt(tbMovies.getValueAt(opcao,0).toString()));
            m.setTitulo(tbMovies.getValueAt(opcao, 1).toString());
            m.setAno(tbMovies.getValueAt(opcao, 2).toString());
            m.setDiretor(tbMovies.getValueAt(opcao, 3).toString());
            m.setGenero(tbMovies.getValueAt(opcao, 4).toString());

            MoviesDAO moviesDAO = new MoviesDAO();
            moviesDAO.delete(m);
            moviesDAO.show(tbMovies);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma Linha");
        }
    }

    protected void btnEditarClick(ActionEvent ev) throws SQLException{
        int opcao = tbMovies.getSelectedRow();

        if (opcao >= 0) {
            txtID.setText(tbMovies.getValueAt(opcao, 0).toString());
            txtTitulo.setText(tbMovies.getValueAt(opcao, 1).toString());
            txtAno.setText(tbMovies.getValueAt(opcao, 2).toString());
            txtDiretor.setText(tbMovies.getValueAt(opcao, 3).toString());
            txtGenero.setText(tbMovies.getValueAt(opcao, 4).toString());

            btnEditar.setVisible(false);
            btnSalvar.setVisible(false);
            btnAtualizar.setVisible(true);

        }  else {
            JOptionPane.showMessageDialog(null, "Selecione uma Linha");
        }
    }

    protected void btnAtualizarClick(ActionEvent ev) throws SQLException{

        Movies m = new Movies();
        m.setIdMovie(Integer.parseInt(txtID.getText()));
        m.setTitulo(txtTitulo.getText());
        m.setAno(txtAno.getText());
        m.setDiretor(txtDiretor.getText());
        m.setGenero(txtGenero.getText());


        MoviesDAO moviesDAO = new MoviesDAO();
        moviesDAO.update(m);

        txtID.setText("");
        txtTitulo.setText("");
        txtAno.setText("");
        txtDiretor.setText("");
        txtGenero.setText("");

        moviesDAO.show(tbMovies);

        btnEditar.setVisible(true);
        btnSalvar.setVisible(true);
        btnAtualizar.setVisible(false);
    }
}
