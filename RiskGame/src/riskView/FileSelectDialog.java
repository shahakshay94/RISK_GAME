package riskView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * File Select Dialog class to select the map file
 *
 * @author prashantp95
 */
public class FileSelectDialog extends JFileChooser {

    /**
     * This constructor allows to select the map file and load the file location
     */
    public FileSelectDialog() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Map FILES", "map");
        setFileFilter(filter);
        setDialogTitle("Select a map file");
        setCurrentDirectory(new File(System.getProperty("user.home")));
    }
}
