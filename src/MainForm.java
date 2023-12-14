import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public abstract class MainForm extends JFrame {

    //Painel Principal
    protected JPanel pnlMain;

    //Botões da Tela Inicial
    protected JButton btnMovies;
    protected JButton btnWatchList;
    protected JButton btnStreaming;

    public MainForm(){
        this.inicializar();
        this.eventos();
    }

    private void inicializar(){
        this.setTitle("Watchlist.WatchList de Filmes");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.setResizable(false);

        this.getContentPane().add(getPnlMain(),BorderLayout.CENTER);
        this.setSize(942,626);
    }

    protected abstract void btnMoviesClick(ActionEvent ev);
    protected abstract void btnWatchListClick(ActionEvent ev);
    protected abstract void btnStreamingClick(ActionEvent ev);

    private void eventos(){
        btnMovies.addActionListener(this::btnMoviesClick);
        btnWatchList.addActionListener(this::btnWatchListClick);
        btnStreaming.addActionListener(this::btnStreamingClick);
    }

    public JPanel getPnlMain(){
        if (pnlMain == null){
            pnlMain = new JPanel(new GridLayout(2,1));

            btnMovies = new JButton("Filmes");
            btnWatchList = new JButton("WatchList");
            btnStreaming = new JButton("Streaming");

            pnlMain.add(btnWatchList);
            pnlMain.add(btnStreaming);
            pnlMain.add(btnMovies);
        }
        return pnlMain;
    }

}
