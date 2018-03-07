package pdi;

import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Processamento Digital de Imagens
 * 
 * @author Marina
 */
public class PDI extends JFrame {
    /** Imagem original */
    private Image originalImage;
    /** Estatísticas da imagem original */
    private ImageStatistics originalImageStatistics;
    /** Imagem transformada */
    private Image modifiedImage;
    /** Estatísticas da imagem transformada */
    private ImageStatistics modifiedImageStatistics;
    /** Painel esquerdo da janela */
    private JPanel leftPanel;
    /** Painel direito da janela */
    private JPanel rightPanel;
    /** Label onde será exibida a imagem original */
    private JLabel orignalImageLabel;
    /** Label onde será exibida a imagem modificada */
    private JLabel modifiedImageLabel;
    /** Painel para criação do histograma */
    private JFXPanel fxPanel;
    
    public static void main(String[] args) {
        PDI imageManipulation = new PDI();
    }

    public PDI() {
        Platform.setImplicitExit(false);
        initGui();
    }

    /**
     * Inicializa a interface
     */
    private void initGui() {
        setTitle("Processamento Digital de Imagens");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setJMenuBar(createMenu());
        getContentPane().add(createGrid());
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    /**
     * Cria menus
     * 
     * @return JMenuBar
     */
    private JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createMenuArquivo());
        menuBar.add(createMenuEstatistica());
        return menuBar;
    }

    /**
     * Cria menu de primeiro nível para operações com arquivo
     * 
     * @return JMenu
     */
    private JMenu createMenuArquivo() {
        JMenu menuArquivo = new JMenu("Arquivo");
        menuArquivo.setMnemonic(KeyEvent.VK_A);
        menuArquivo.add(createMenuArquivoAbrir());
        menuArquivo.add(createMenuArquivoAbrirEspecial());
        menuArquivo.add(createMenuArquivoSalvarOriginal());
        menuArquivo.add(createMenuArquivoSalvarTransformada());
        menuArquivo.add(createMenuArquivoSairPrograma());
        return menuArquivo;
    }

    /**
     * Cria submenu para abrir a imagem
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuArquivoAbrir() {
        JMenuItem arquivoAbrir = new JMenuItem("Abrir imagem");
        arquivoAbrir.setMnemonic(KeyEvent.VK_A);
        arquivoAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openImage();
                refreshOriginalImagePanel();
            }
        });
        return arquivoAbrir;
    }
    
    /**
     * Cria submenu para abrir imagens específicas
     * 
     * @return JMenu
     */
    private JMenu createMenuArquivoAbrirEspecial() {
        JMenu arquivoAbrirEspecial = new JMenu("Abrir especial");
        arquivoAbrirEspecial.setMnemonic(KeyEvent.VK_E);
        arquivoAbrirEspecial.add(createArquivoAbrirLena());
        arquivoAbrirEspecial.add(createArquivoAbrirEye());
        arquivoAbrirEspecial.add(createArquivoAbrirLandscape());
        return arquivoAbrirEspecial;
    }
    
    /**
     * Cria submenu para abrir imagem da Lena
     * 
     * @return JMenuItem
     */
    private JMenuItem createArquivoAbrirLena() {
        JMenuItem abrirLena = new JMenuItem("Lena");
        abrirLena.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage(PDI.class.getResourceAsStream("/res/lena.png"));
                refreshOriginalImagePanel();
            }
        });
        return abrirLena;
    }
    
    /**
     * Cria submenu para abrir imagem de olho
     * 
     * @return JMenuItem
     */
    private JMenuItem createArquivoAbrirEye() {
        JMenuItem abrirEye = new JMenuItem("Olho");
        abrirEye.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage(PDI.class.getResourceAsStream("/res/eye.png"));
                refreshOriginalImagePanel();
            }
        });
        return abrirEye;
    }
    
    /**
     * Cria submenu para abrir imagem de paisagem
     * 
     * @return JMenuItem
     */
    private JMenuItem createArquivoAbrirLandscape() {
        JMenuItem abrirLandscape = new JMenuItem("Paisagem");
        abrirLandscape.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage(PDI.class.getResourceAsStream("/res/landscape.png"));
                refreshOriginalImagePanel();
            }
        });
        return abrirLandscape;
    }

    /**
     * Cria submenu para salvar a imagem orignal
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuArquivoSalvarOriginal() {
        JMenuItem arquivoSalvarOriginal = new JMenuItem("Salvar imagem original");
        arquivoSalvarOriginal.setMnemonic(KeyEvent.VK_O);
        arquivoSalvarOriginal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (originalImage != null) {
                    File original = new File(System.getProperty("user.home") + "\\Desktop\\ImagemOriginal.png");
                    try {
                        ImageIO.write(originalImage.toBufferedImage(), "png", original);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        return arquivoSalvarOriginal;
    }

    /**
     * Cria submenu para salvar a imagem transformada
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuArquivoSalvarTransformada() {
        JMenuItem arquivoSalvarTransformada = new JMenuItem("Salvar imagem transformada");
        arquivoSalvarTransformada.setMnemonic(KeyEvent.VK_T);
        arquivoSalvarTransformada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modifiedImage != null) {
                    File modified = new File(System.getProperty("user.home") + "\\Desktop\\ImagemModificada.png");
                    try {
                        ImageIO.write(modifiedImage.toBufferedImage(), "png", modified);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        return arquivoSalvarTransformada;
    }

    /**
     * Cria submenu para sair do programa
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuArquivoSairPrograma() {
        JMenuItem arquivoSair = new JMenuItem("Sair do programa");
        arquivoSair.setMnemonic(KeyEvent.VK_S);
        arquivoSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        return arquivoSair;
    }
    
    /**
     * Cria menu de primeiro nível para estatística em imagens
     * 
     * @return JMenu
     */
    private JMenu createMenuEstatistica() {
        JMenu menuEstatistica = new JMenu("Estatística em imagens");
        menuEstatistica.setMnemonic(KeyEvent.VK_E);
        menuEstatistica.add(createMenuEstatisticaCalculos());
        menuEstatistica.add(createMenuEstatisticaHistograma());
        menuEstatistica.add(createMenuEstatisticaTransformacoes());
        return menuEstatistica;
    }

    /**
     * Cria submenu para cálculos estatísticos da imagem
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuEstatisticaCalculos() {
        JMenuItem estatisticaCalculos = new JMenuItem("Cálculos estatísticos");
        estatisticaCalculos.setMnemonic(KeyEvent.VK_C);
        estatisticaCalculos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPanel.setLayout(new FlowLayout());
                rightPanel.removeAll();
                rightPanel.setBorder(BorderFactory.createTitledBorder("Estatísticas da imagem original"));
                rightPanel.add(showImageStatistics(originalImageStatistics));
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        });
        return estatisticaCalculos;
    }

    /**
     * Cria submenu para histograma da imagem
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuEstatisticaHistograma() {
        JMenuItem estatisticaHistograma = new JMenuItem("Histograma");
        estatisticaHistograma.setMnemonic(KeyEvent.VK_H);
        estatisticaHistograma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPanel.setLayout(new BorderLayout());
                rightPanel.removeAll();
                rightPanel.setBorder(BorderFactory.createTitledBorder("Histograma da imagem original"));
                rightPanel.add(createHistograma());
                rightPanel.revalidate();
                rightPanel.repaint();
                
            }
        });
        return estatisticaHistograma;
    }

    /**
     * Cria submenu para transformações na imagem
     * 
     * @return JMenu
     */
    private JMenu createMenuEstatisticaTransformacoes() {
        JMenu estatisticaTransformacoes = new JMenu("Transformações");
        estatisticaTransformacoes.setMnemonic(KeyEvent.VK_T);
        estatisticaTransformacoes.add(createTransformacoesAcimaMedia());
        estatisticaTransformacoes.add(createTransformacoesAbaixoMediana());
        estatisticaTransformacoes.add(createTransformacoesModa());
        estatisticaTransformacoes.add(createTransformacoesInvertePixels());
        return estatisticaTransformacoes;
    }
    
    /**
     * Cria submenu para transformar pixels acima da média em branco
     * 
     * @return JMenuItem
     */
    private JMenuItem createTransformacoesAcimaMedia() {
        JMenuItem transformacoesAcimaMedia = new JMenuItem("Pixels acima da média para branco");
        transformacoesAcimaMedia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifiedImage = new Image(originalImage.getHeight(), originalImage.getWidth());
                modifiedImage = Transformations.aboveAvgToWhite(originalImage, originalImageStatistics);
                modifiedImageStatistics = new ImageStatistics(modifiedImage);
                modifiedImageStatistics.computeAll();
                refreshModifiedImagePanel();
            }
        });
        return transformacoesAcimaMedia;
    }
    
    /**
     * Cria submenu para transformar pixels abaixo da mediana para preto
     * 
     * @return JMenuItem
     */
    private JMenuItem createTransformacoesAbaixoMediana() {
        JMenuItem transformacoesAbaixoMediana = new JMenuItem("Pixels abaixo da mediana para preto");
        transformacoesAbaixoMediana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifiedImage = new Image(originalImage.getHeight(), originalImage.getWidth());
                modifiedImage = Transformations.belowMedianToBlack(originalImage, originalImageStatistics);
                modifiedImageStatistics = new ImageStatistics(modifiedImage);
                modifiedImageStatistics.computeAll();
                refreshModifiedImagePanel();
            }
        });
        return transformacoesAbaixoMediana;
    }
    
    /**
     * Cria submenu para transformar pixels acima da moda para branco e demais para preto
     * 
     * @return JMenuItem
     */
    private JMenuItem createTransformacoesModa() {
        JMenuItem transformacoesModa = new JMenuItem("Pixels acima da moda para branco e demais para preto");
        transformacoesModa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifiedImage = new Image(originalImage.getHeight(), originalImage.getWidth());
                modifiedImage = Transformations.aboveModeToWhiteOthersBlack(originalImage, originalImageStatistics);
                modifiedImageStatistics = new ImageStatistics(modifiedImage);
                modifiedImageStatistics.computeAll();
                refreshModifiedImagePanel();
            }
        });
        return transformacoesModa;
    }
    
    /**
     * Cria submenu para inverter o valor dos pixels
     * 
     * @return JMenuItem
     */
    private JMenuItem createTransformacoesInvertePixels() {
        JMenuItem transformacoesInverter = new JMenuItem("Inverter valor dos pixels");
        transformacoesInverter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifiedImage = new Image(originalImage.getHeight(), originalImage.getWidth());
                modifiedImage = Transformations.reversePixelsValue(originalImage);
                modifiedImageStatistics = new ImageStatistics(modifiedImage);
                modifiedImageStatistics.computeAll();
                refreshModifiedImagePanel();
            }
        });
        return transformacoesInverter;
    }

    /**
     * Cria área de informações da janela
     * 
     * @return JComponent
     */
    private JComponent createGrid() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(createLeftPanel());
        panel.add(createRightPanel());
        return panel;
    }
    
    /**
     * Cria painel da esquerda da área de informações
     * 
     * @return JComponent
     */
    private JComponent createLeftPanel() {
        leftPanel = new JPanel();
        orignalImageLabel = new JLabel();
        leftPanel.add(orignalImageLabel);
        return leftPanel;
    }
    
    /**
     * Cria painel da direita da área de informações
     * 
     * @return JComponent
     */
    private JComponent createRightPanel() {
        rightPanel = new JPanel(new BorderLayout());
        modifiedImageLabel = new JLabel();
        rightPanel.add(modifiedImageLabel);
        return rightPanel;
    }
    
    /**
     * Abre a imagem selecionada
     */
    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                loadImage(new FileInputStream(fileChooser.getSelectedFile()));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Carrega a imagem para manipulação
     */
    private void loadImage(InputStream file) {
        try {
            BufferedImage imageFile = ImageIO.read(file);
            int height = imageFile.getHeight();
            int width = imageFile.getWidth();
            int[][] image = new int[width][height];
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    image[i][j] = rgbToGrayScale(imageFile.getRGB(i, j));
                }
            }
            originalImage = new Image(height, width);
            originalImage.setPixels(image);
            originalImageStatistics = new ImageStatistics(originalImage);
            originalImageStatistics.computeAll();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Converte RGB para escala de cinza
     * 
     * @param rgb
     * @return int
     */
    private int rgbToGrayScale(int rgb) {
        int r = (rgb&0x00ff0000)>>16;
        int g = (rgb&0x0000ff00)>>8;
        int b = (rgb&0x000000ff);
        return (r + g + b) / 3;
    }

    /**
     * Atualiza o painel com a imagem original
     */
    private void refreshOriginalImagePanel() {
        if (originalImage != null) {
            leftPanel.setBorder(BorderFactory.createTitledBorder("Imagem original"));
            orignalImageLabel.setIcon(new ImageIcon(originalImage.toBufferedImage()));
            leftPanel.revalidate();
            leftPanel.repaint();
            rightPanel.removeAll();
            rightPanel.setBorder(null);
            rightPanel.revalidate();
            rightPanel.repaint();
        }
    }

    /**
     * Atualiza o painel com a imagem modificada
     */
    private void refreshModifiedImagePanel() {
        rightPanel.setLayout(new BorderLayout());
        rightPanel.removeAll();
        rightPanel.setBorder(BorderFactory.createTitledBorder("Imagem transformada"));
        rightPanel.add(showModifiedImage(), BorderLayout.CENTER);
        rightPanel.add(createModifiedImageStatisticsPanel(), BorderLayout.SOUTH);
        rightPanel.revalidate();
        rightPanel.repaint();
    }
    
    /**
     * Exibe a imagem modificada
     * 
     * @return JComponent
     */
    private JComponent showModifiedImage() {
        JPanel modifiedImagePanel = new JPanel();
        modifiedImagePanel.setLayout(new FlowLayout());
        modifiedImageLabel.setIcon(new ImageIcon(modifiedImage.toBufferedImage()));
        modifiedImagePanel.add(modifiedImageLabel);
        return modifiedImagePanel;
    }
    
    /**
     * Cria painel para exibir as estatísticas da imagem modificada
     * 
     * @return JComponent
     */
    private JComponent createModifiedImageStatisticsPanel() {
        JPanel modifiedImagePanel = new JPanel();
        modifiedImagePanel.setLayout(new FlowLayout());
        modifiedImagePanel.add(showImageStatistics(modifiedImageStatistics));
        return modifiedImagePanel;
    }
    
    /**
     * Exibe estatísticas da imagem
     * 
     * @return JComponent
     */
    private JComponent showImageStatistics(ImageStatistics statistics) {
        JPanel statisticsPanel = new JPanel();
        statisticsPanel.setLayout(new GridLayout(4, 2, 20, 0));
        statisticsPanel.add(new JLabel("Média:"));
        statisticsPanel.add(new JLabel(String.valueOf(statistics.computeMedia())));
        statisticsPanel.add(new JLabel("Mediana:"));
        statisticsPanel.add(new JLabel(String.valueOf(statistics.computeMediana())));
        statisticsPanel.add(new JLabel("Moda:"));
        statisticsPanel.add(new JLabel(String.valueOf(statistics.computeModa())));
        statisticsPanel.add(new JLabel("Variância:"));
        statisticsPanel.add(new JLabel(String.valueOf(statistics.computeVariancia())));
        return statisticsPanel;
    }
    
    /**
     * Exibe histograma da imagem
     * 
     * @return JComponent
     */
    private JComponent createHistograma() {
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
                serie.getData().add(new XYChart.Data(String.valueOf(i), originalImageStatistics.loadHistograma()[i]));
            }
            chart.getData().add(serie);
            root.setCenter(chart);
            fxPanel.setScene(scene);
            SwingUtilities.invokeLater(() -> {
                rightPanel.revalidate();
                rightPanel.repaint();
            });
        });
        return fxPanel;
    }
    
}
