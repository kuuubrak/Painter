package GUI;

import DataModel.Circle;
import Engine.FileManager.ImageManager;
import Engine.ImageRecreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by tomasz on 13.05.2014.
 */
public class MainFrame extends JFrame implements GUIComponent {

    private ImagePanel targetImagePanel;
    private ImagePanel currentImagePanel;
    private BufferedImage targetImage = null;
    private BufferedImage currentImage = null;
    private JPanel imagePanelsContainer;
    private JPanel buttonsContainer;
    private JButton clearButton;
    private JButton runButton;
    private JButton loadButton;

    private ImageManager loader;

    public static void main (String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    createMainFrame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainFrame(String title) throws HeadlessException {
        super(title);
        loader = new ImageManager();
        loadTargetImage();
        loadCurrentImage();
        setSize(targetImage.getWidth() *2, targetImage.getHeight() + GuiConstants.mainFrameHeightModifier);
        centerMainFrame();
        createImagePanels();
        createButtons();
        }

    private static void createMainFrame() {
        MainFrame mainFrame = new MainFrame(GuiConstants.mainFrameTitle);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }


    private void centerMainFrame () {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    private void createImagePanels() {
        Dimension panelDimension = new Dimension(this.getWidth(), this.getHeight()/2);
        Dimension targetDimension = new Dimension(targetImage.getWidth(), targetImage.getHeight());
        targetImagePanel = new ImagePanel(targetDimension, targetImage);
        currentImagePanel = new ImagePanel(targetDimension, currentImage);
        createImagePanelsContainer();
    }

    private void createImagePanelsContainer () {
        imagePanelsContainer = new JPanel();
        imagePanelsContainer.setLayout(new BoxLayout(imagePanelsContainer, BoxLayout.LINE_AXIS));
        imagePanelsContainer.add(currentImagePanel);
        imagePanelsContainer.add(targetImagePanel);
        add(imagePanelsContainer, BorderLayout.CENTER);
    }

    private void loadTargetImage() {
        targetImage = loader.loadImage(ImageManager.examplePath);
    }

    private void loadCurrentImage() {
        Dimension dim = new Dimension(targetImage.getWidth(), targetImage.getHeight());
        currentImage = loader.intializeStartingImage(dim);
    }

    private void createButtons () {
        clearButton = new JButton(GuiConstants.clearButtonTitle);
        runButton = new JButton(GuiConstants.runButtonTitle);
        loadButton = new JButton(GuiConstants.loadButtonTitle);

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageRecreator recreator = new ImageRecreator(MainFrame.this);
                recreator.recreateImage(targetImage);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageManager imageManager = new ImageManager();
                Dimension currentDimension = new Dimension(targetImage.getWidth(), targetImage.getHeight());
                currentImage = imageManager.intializeStartingImage(currentDimension);
                currentImagePanel.setImage(currentImage);
                currentImagePanel.repaint();
            }
        });

        loadButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(MainFrame.this);

                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();

                    remove(imagePanelsContainer);

                    targetImage = loader.loadImage(file.getPath());
                    Dimension targetDimension = new Dimension(targetImage.getWidth(), targetImage.getHeight());
                    targetImagePanel = new ImagePanel(targetDimension, targetImage);
                    targetImagePanel.repaint();

                    currentImage = loader.intializeStartingImage(targetDimension);
                    currentImagePanel.setImage(currentImage);
                    currentImagePanel.repaint();

                    setSize(targetImage.getWidth() *2, targetImage.getHeight() + GuiConstants.mainFrameHeightModifier);
                    centerMainFrame();
                    createImagePanels();
                }
            }
        });

        createButtonContainer();
    }

    private void createButtonContainer () {
        buttonsContainer = new JPanel();
        buttonsContainer.add(clearButton);
        buttonsContainer.add(runButton);
        buttonsContainer.add(loadButton);
        add(buttonsContainer, BorderLayout.SOUTH);
    }

    public void updateCurrentImage(BufferedImage currentImage) {
        this.currentImage = currentImage;
        currentImagePanel.setImage(currentImage);
        //currentImagePanel.repaint();
        //TODO Trzeba dobrze ogarnąć wyświetlanie w Javie, tzn. jakiej metody tu użyć
        update(getGraphics());
        //currentImagePanel.update(getGraphics());
    }
}
