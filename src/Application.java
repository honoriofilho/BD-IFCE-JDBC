import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Movies movie = new Movies();
            movie.setVisible(true);
        });
    }
}