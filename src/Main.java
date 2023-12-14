import Movies.Movies;
import Streaming.Streaming;
import Streaming.StreamingDAO;
import Watchlist.WatchList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends MainForm {
    @Override
    protected void btnMoviesClick(ActionEvent ev){
        SwingUtilities.invokeLater(() -> {
            // Oculta a JFrame principal
            setVisible(false);

            // Abre a nova JFrame (Movies.Movies)
            Movies movie = new Movies();

            // Define um listener para tratar o fechamento da nova JFrame
            movie.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // Torna a JFrame principal visível novamente quando a JFrame Movies.Movies for fechada
                    setVisible(true);
                }
            });
            movie.setVisible(true);
        });;
    }

    protected void btnWatchListClick(ActionEvent ev){
        SwingUtilities.invokeLater(() -> {
            // Oculta a JFrame principal
            setVisible(false);

            // Abre a nova JFrame (Watchlist.WatchList)
            WatchList watchList = new WatchList();

            watchList.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // Torna a JFrame principal visível novamente quando a nova JFrame for fechada
                    setVisible(true);
                }
            });
            watchList.setVisible(true);
        });
    }

    @Override
    protected void btnStreamingClick(ActionEvent ev){
        SwingUtilities.invokeLater(() -> {
            // Oculta a JFrame principal
            setVisible(false);

            // Abre a nova JFrame (Streaming)
            Streaming streaming = new Streaming();

            // Define um listener para tratar o fechamento da nova JFrame
            streaming.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // Torna a JFrame principal visível novamente quando a JFrame Movies.Movies for fechada
                    setVisible(true);
                }
            });
            streaming.setVisible(true);
        });;
    }
}
