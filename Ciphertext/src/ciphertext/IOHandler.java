package ciphertext;

import static com.sun.javafx.PlatformUtil.isLinux;
import static com.sun.javafx.PlatformUtil.isWindows;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * @author dushan
 */
enum OutType {

    encrypted, decrypted

}

public class IOHandler {

    private static final DateFormat sdf = new SimpleDateFormat("HHmmss");

    public static File getFile(JFrame form) {

        File selectedFile = null;

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(form);

        if (result == JFileChooser.APPROVE_OPTION) {

            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());

        }

        return selectedFile;

    }

    public static void writeIntoFile(String text, File sourceFile, OutType type) {

        String name = outPutFileName(sourceFile, type);

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(name), "utf-8"))) {

            writer.write(text);

            openFile(name);

        } catch (IOException ex) {
            Logger.getLogger(IOHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getTxtFromFile(File file) throws IOException {

        byte[] encoded = Files.readAllBytes(file.toPath());
        return new String(encoded, "utf-8");

    }

    private static String outPutFileName(File inputFile, OutType type) {

        Date date = new Date();

        String fileName = inputFile.getName();
        int pos = fileName.lastIndexOf(".");
        if (pos > 0) {
            fileName = fileName.substring(0, pos);
        }

        String modifier = type == OutType.encrypted ? "_encrypted" : "_decrypted";
        String name = inputFile.getParentFile().toString() + "/" + fileName + modifier + sdf.format(date) + ".txt";

        return name;

    }

    private static void openFile(String filePath) {

        String command;
        if (isLinux()) {
            command = "xdg-open " + filePath;
        } else if (isWindows()) {
            command = "cmd /C start " + filePath;
        } else {
            return;
        }

        try {
            Runtime.getRuntime().exec(command);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
