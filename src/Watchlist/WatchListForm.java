package Watchlist;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public abstract class WatchListForm extends JFrame {

    private static final int TAM = 15;

    //Painel
    protected JPanel pnlForm;
    protected JPanel pnlRodape;
    protected JPanel pnlCenter;
    protected JPanel pnlTable;
    protected JPanel pnlSearch;
    protected JPanel pnlNorth;

    //Imagem
    protected Icon imgPesquisa;

    //Tabela
    protected JTable tbWL;

    //Campo de Pesquisa
    protected JLabel lblPesquisa;
    protected JTextField txtPesquisa;

    //ID Filme
    protected JLabel lblWL;
    protected JTextField txtWL;

    // Titulo do Filme
    protected JLabel lblNomeWL;
    protected JTextField txtNomeWL;


    //Botão de Salvar
    protected JButton btnSalvar;

    //Botão de Salvar
    protected JButton btnListar;

    //Botão de Cancelar
    protected JButton btnFechar;

    //Botão de Pesquisa
    protected JButton btnPesquisa;

    //Botão de Excluir
    protected JButton btnExcluir;

    //Botão de Atualizar
    protected JButton btnAtualizar;

    //Botão de Editar
    protected JButton btnEditar;


    Image imgBackground = new ImageIcon("background.jpg").getImage();

    public void paint(Graphics g) {
        /* Desenha a imagem de fundo */
        g.drawImage(imgBackground, 0, 0, getWidth(), getHeight(), this);

        // Chama o método paint padrão da superclasse para garantir o comportamento correto
        super.paint(g);
    }
    public WatchListForm(){
        this.inicializar();
        this.eventos();
    }

    public void inicializar(){
        this.setTitle("WatchList de Filmes");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setResizable(false);


        this.getContentPane().add(getPnLCenter(), BorderLayout.CENTER);
        this.getContentPane().add(getPnlRodape(), BorderLayout.PAGE_END);
        this.getContentPane().add(getPnlNorth(), BorderLayout.NORTH);

        //this.pack();
        this.setSize(942,626);

    }

    protected abstract void btnSalvarClick(ActionEvent ev) throws SQLException;
    protected abstract void btnListarClick(ActionEvent ev) throws SQLException;
    protected abstract void btnExcluirClick(ActionEvent ev) throws SQLException;
    protected abstract void btnEditarClick(ActionEvent ev) throws SQLException;
    protected abstract void btnPesquisarClick(ActionEvent ev) throws SQLException;
    protected abstract void btnAtualizarClick(ActionEvent ev) throws SQLException;
    protected abstract void btnFecharClick(ActionEvent ev);


    private void eventos(){
        btnFechar.addActionListener(this::btnFecharClick);
        btnSalvar.addActionListener(ev -> {
            try {
                btnSalvarClick(ev);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        btnListar.addActionListener(ev1 -> {
            try {
                btnListarClick(ev1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        btnAtualizar.addActionListener(ev -> {
            try {
                btnAtualizarClick(ev);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        btnEditar.addActionListener(ev -> {
            try {
                btnEditarClick(ev);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        btnPesquisa.addActionListener(ev -> {
            try {
                btnPesquisarClick(ev);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        btnExcluir.addActionListener(ev -> {
            try {
                btnExcluirClick(ev);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public JPanel getPnLCenter(){
        if (pnlCenter == null) {
            pnlCenter = new JPanel(new GridLayout(1, 2));

            pnlCenter.add(getPnlForm());
            pnlCenter.add(getPnlTable());
        }
        return pnlCenter;
    }

    public JPanel getPnlNorth(){
        if (pnlNorth == null){
            pnlNorth = new JPanel(new GridLayout(1, 1));

            pnlNorth.setBorder(new EmptyBorder(25, 5, 25, 5));

            //pnlNorth.add(getPnlRadioButton());
            pnlNorth.add(getPnlSearch());
        }
        return pnlNorth;
    }

    public JPanel getPnlSearch(){
        if (pnlSearch == null) {
            pnlSearch = new JPanel(new FlowLayout());

            imgPesquisa = new ImageIcon("search.png");

            lblPesquisa = new JLabel("Busca");
            txtPesquisa = new JTextField(TAM);
            txtPesquisa.setPreferredSize(new Dimension(200, 40));
            btnPesquisa = new JButton(imgPesquisa);

            //pnlSearch.setBorder(new EmptyBorder(5, 5, 5, 5));

            pnlSearch.add(lblPesquisa);
            pnlSearch.add(txtPesquisa);
            pnlSearch.add(btnPesquisa);

        }
        return pnlSearch;
    }

    public JPanel getPnlTable(){
        if (pnlTable == null){
            pnlTable = new JPanel(new FlowLayout());

            tbWL = new JTable();
            pnlTable.add(new JScrollPane(tbWL));
        }
        return pnlTable;
    }

    public JPanel getPnlForm(){
        if (pnlForm == null){
            pnlForm = new JPanel(new GridLayout(5, 2, 10, 10));

            lblWL = new JLabel("ID Watchlist");
            txtWL = new JTextField(TAM);
            txtWL.setEditable(false);

            lblNomeWL = new JLabel("Nome");
            txtNomeWL = new JTextField(TAM);

            pnlForm.setBorder(new EmptyBorder(4, 30, 200, 20));

            pnlForm.add(lblWL);
            pnlForm.add(txtWL);

            pnlForm.add(lblNomeWL);
            pnlForm.add(txtNomeWL);
        }
        return pnlForm;
    }

    public JPanel getPnlRodape(){
        if (pnlRodape == null){
            pnlRodape = new JPanel(new FlowLayout(FlowLayout.CENTER,50, 15));

            btnSalvar = new JButton("Salvar Registro");
            btnFechar = new JButton("Voltar ao Início");
            btnListar = new JButton("WatchLists");
            btnExcluir = new JButton("Excluir Registro");
            btnAtualizar = new JButton("Atualizar Registro");
            btnEditar = new JButton("Editar Registro");

            btnAtualizar.setVisible(false);

            pnlRodape.add(btnSalvar);
            pnlRodape.add(btnAtualizar);
            pnlRodape.add(btnEditar);
            pnlRodape.add(btnListar);
            pnlRodape.add(btnExcluir);
            pnlRodape.add(btnFechar);
        }
        return pnlRodape;
    }


}
