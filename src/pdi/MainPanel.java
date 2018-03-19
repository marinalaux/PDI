package pdi;

import commons.Image;
import commons.ImageStatistics;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import processes.GeometricTransformations;

/**
 * Painel principal da janela
 * 
 * @author Marina
 */
public class MainPanel extends JPanel {

    /** Imagem original */
    private Image originalImage;
    /** Estatísticas da imagem original */
    private ImageStatistics originalImageStatistics;
    /** Imagem modificada */
    private Image modifiedImage;
    /** Painel direito */
    private final JPanel rightPanel;
    /** Painel para criação do histograma */
    private JFXPanel fxPanel;
    
    /**
     * Construtor
     */
    public MainPanel() {
        super();
        setLayout(new GridLayout(1, 2));
        add(new ImageView());
        rightPanel = new ImageView();
        add(rightPanel);
    }
    
    /**
     * Atualiza a exibição da imagem original
     * 
     * @param original 
     */
    public void updateOriginalImage(Image original) {
        originalImage = original;
        originalImageStatistics = new ImageStatistics(originalImage);
        originalImageStatistics.computeAll();
        removeAll();
        ImageView originalImagePanel = new ImageView("Imagem original");
        originalImagePanel.updateImage(original);
        add(originalImagePanel);
        rightPanel.removeAll();
        rightPanel.setBorder(null);
        add(rightPanel);
        revalidate();
        repaint();        
    }
    
    /**
     * Exibe estatísticas da imagem original
     * 
     * @param statistics 
     */
    public void showOriginalImageStatistics(ImageStatistics statistics) {
        rightPanel.removeAll();
        rightPanel.setBorder(BorderFactory.createTitledBorder("Estatísticas da imagem original"));
        rightPanel.add(new ImageStatisticsView("",
                statistics.computeMedia(), 
                statistics.computeMediana(), 
                statistics.computeModa(), 
                statistics.computeVariancia()));
        revalidate();
        repaint();
    }
    
    /**
     * Exibe histograma da imagem original
     * 
     * @param statistics 
     */
    public void showOriginalImageHistogram(ImageStatistics statistics) {
        rightPanel.removeAll();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Histograma da imagem original"));
        // Deve instanciar apenas uma vez o painel do JavaFX
        if (fxPanel == null) {
            fxPanel = new JFXPanel();
        }
        Platform.runLater(() -> {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root);
            BarChart<String,Number> chart = new BarChart<>(new CategoryAxis(), new NumberAxis());
            chart.setVerticalGridLinesVisible(false);
            chart.setLegendVisible(false);
            chart.getStylesheets().add("/pdi/pdi.css");
            XYChart.Series serie = new XYChart.Series();
            for (int i = 0; i < 256; i++) {
                serie.getData().add(new XYChart.Data(String.valueOf(i), statistics.loadHistograma()[i]));
            }
            chart.getData().add(serie);
            root.setCenter(chart);
            fxPanel.setScene(scene);
            SwingUtilities.invokeLater(() -> {
                rightPanel.revalidate();
                rightPanel.repaint();
            });
        });
        rightPanel.add(fxPanel);
        revalidate();
        repaint();
    }
    
    /**
     * Atualiza a exibição da imagem modificada
     */
    public void showModifiedImageInformations() {
        ImageStatistics modifiedImageStatistics = new ImageStatistics(modifiedImage);
        modifiedImageStatistics.computeAll();
        resetModifiedImagePanel();
        ImageView modifiedImagePanel = new ImageView();
        modifiedImagePanel.updateImage(modifiedImage);
        rightPanel.add(modifiedImagePanel, BorderLayout.CENTER);
        rightPanel.add(new ImageStatisticsView("Cálculos estatísticos",
                modifiedImageStatistics.computeMedia(),
                modifiedImageStatistics.computeMediana(),
                modifiedImageStatistics.computeModa(),
                modifiedImageStatistics.computeVariancia()),
                BorderLayout.SOUTH);
        revalidate();
        repaint();
    }
    
    /**
     * Exibe painel de espelhamento da imagem
     */
    public void showMirrorPanel() {
        resetModifiedImagePanel();
        ImageView imageMirrorPanel = new ImageView();
        JPanel mirroringInformationPanel = new JPanel(new BorderLayout());
        mirroringInformationPanel.add(new MirrorParametersView((MirrorParametersBean params) -> {
            modifiedImage = GeometricTransformations.transforma(originalImage, params.getMatrix());
            ImageStatistics modifiedImageStatistics = new ImageStatistics(modifiedImage);
            modifiedImageStatistics.computeAll();
            imageMirrorPanel.updateImage(modifiedImage);
            mirroringInformationPanel.add(new ImageStatisticsView("Cálculos estatísticos", 
                    modifiedImageStatistics.computeMedia(), 
                    modifiedImageStatistics.computeMediana(), 
                    modifiedImageStatistics.computeModa(), 
                    modifiedImageStatistics.computeVariancia()), BorderLayout.NORTH);
            rightPanel.revalidate();
            rightPanel.repaint();
        }), BorderLayout.CENTER);
        rightPanel.add(imageMirrorPanel, BorderLayout.CENTER);
        rightPanel.add(mirroringInformationPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }
    
    /**
     * Exibe painel para transladar a imagem
     */
    public void showTransferPanel() {
        resetModifiedImagePanel();
        ImageView imageTransferPanel = new ImageView();
        JPanel transferInformationPanel = new JPanel(new BorderLayout());
        transferInformationPanel.add(new TransferParametersView((TransferParametersBean params) -> {
            modifiedImage = GeometricTransformations.transforma(originalImage, params.getMatrix());
            ImageStatistics modifiedImageStatistics = new ImageStatistics(modifiedImage);
            modifiedImageStatistics.computeAll();
            imageTransferPanel.updateImage(modifiedImage);
            transferInformationPanel.add(new ImageStatisticsView("Cálculos estatísticos", 
                    modifiedImageStatistics.computeMedia(), 
                    modifiedImageStatistics.computeMediana(), 
                    modifiedImageStatistics.computeModa(), 
                    modifiedImageStatistics.computeVariancia()), BorderLayout.NORTH);
            rightPanel.revalidate();
            rightPanel.repaint();
        }), BorderLayout.CENTER);
        rightPanel.add(imageTransferPanel, BorderLayout.CENTER);
        rightPanel.add(transferInformationPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }
    
    /**
     * Exibe painel para redimensionar a imagem
     */
    public void showResizePanel() {
        resetModifiedImagePanel();
        ImageView imageResizePanel = new ImageView();
        JPanel resizeInformationPanel = new JPanel(new BorderLayout());
        resizeInformationPanel.add(new ResizeParametersView((ResizeParametersBean params) -> {
            modifiedImage = GeometricTransformations.transforma(originalImage, params.getMatrix());
            ImageStatistics modifiedImageStatistics = new ImageStatistics(modifiedImage);
            modifiedImageStatistics.computeAll();
            imageResizePanel.updateImage(modifiedImage);
            resizeInformationPanel.add(new ImageStatisticsView("Cálculos estatísticos", 
                    modifiedImageStatistics.computeMedia(), 
                    modifiedImageStatistics.computeMediana(), 
                    modifiedImageStatistics.computeModa(), 
                    modifiedImageStatistics.computeVariancia()), BorderLayout.NORTH);
            rightPanel.revalidate();
            rightPanel.repaint();
        }), BorderLayout.CENTER);
        rightPanel.add(imageResizePanel, BorderLayout.CENTER);
        rightPanel.add(resizeInformationPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    /**
     * Retorna a imagem modificada
     * 
     * @return Imagem modificada
     */
    public Image getModifiedImage() {
        return modifiedImage;
    }

    /**
     * Cria uma nova imagem modificada
     * 
     * @param modifiedImage 
     */
    public void setModifiedImage(Image modifiedImage) {
        this.modifiedImage = new Image(modifiedImage.getHeight(), modifiedImage.getWidth());
        this.modifiedImage.setPixels(modifiedImage.getPixels());
    }
    
    /**
     * Inicializa painel de exibição da imagem transformada
     */
    private void resetModifiedImagePanel() {
        rightPanel.removeAll();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Imagem transformada"));
    }
    
}
