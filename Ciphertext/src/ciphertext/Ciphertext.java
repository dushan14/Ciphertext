package ciphertext;

import static com.sun.javafx.PlatformUtil.isLinux;
import static com.sun.javafx.PlatformUtil.isWindows;

/*
 * @author dushan
 */
public class Ciphertext {

    public static void main(String[] args) {

        if (isLinux()) {
            setUiTheme("Nimbus");
        } else if (isWindows()) {
            setUiTheme("Windows");
        }
        
        new UI().show();
    }

    private static void setUiTheme(String theme) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (theme.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

}
