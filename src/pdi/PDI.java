package pdi;

import processes.Transformations;
import commons.ImageStatistics;
import commons.Image;
import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javafx.application.Platform;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

/**
 * Processamento Digital de Imagens
 * 
 * @author Marina
 */
public class PDI extends JFrame {
    
    /** Painel principal */
    private MainPanel mainPanel;
    /** Imagem original */
    private Image originalImage;
    /** Estatísticas da imagem original */
    private ImageStatistics originalImageStatistics;
    
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
        mainPanel = new MainPanel();
        getContentPane().add(mainPanel);
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
        menuBar.add(createMenuTransformacoesGeometricas());
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
     * Cria menu de primeiro nível para transformações geométricas
     * 
     * @return JMenu
     */
    private JMenu createMenuTransformacoesGeometricas() {
        JMenu menuTransformacoes = new JMenu("Transformações geométricas");
        menuTransformacoes.setMnemonic(KeyEvent.VK_T);
        menuTransformacoes.add(createMenuTransformacoesEspelhar());
        menuTransformacoes.add(createMenuTransformacoesTransladar());
        menuTransformacoes.add(createMenuTransformacoesRedimensionar());
        menuTransformacoes.add(createMenuTransformacoesRotacionar());
        return menuTransformacoes;
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
                mainPanel.updateOriginalImage(originalImage);
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
                mainPanel.updateOriginalImage(originalImage);
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
                mainPanel.updateOriginalImage(originalImage);
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
                mainPanel.updateOriginalImage(originalImage);
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
        arquivoSalvarOriginal.setToolTipText("Salva a imagem original na área de trabalho do computador");
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
        arquivoSalvarTransformada.setToolTipText("Salva a imagem transformada na área de trabalho do computador");
        arquivoSalvarTransformada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainPanel.getModifiedImage() != null) {
                    File modified = new File(System.getProperty("user.home") + "\\Desktop\\ImagemModificada.png");
                    try {
                        ImageIO.write(mainPanel.getModifiedImage().toBufferedImage(), "png", modified);
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
                if (originalImage != null) {
                    mainPanel.showOriginalImageStatistics(originalImageStatistics);
                }
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
                if (originalImage != null) {
                    mainPanel.showOriginalImageHistogram(originalImageStatistics);
                }                
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
        transformacoesAcimaMedia.setToolTipText("Transforma pixels com valor acima da média para branco");
        transformacoesAcimaMedia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (originalImage != null) {
                    mainPanel.setModifiedImage(Transformations.aboveAvgToWhite(originalImage, originalImageStatistics));
                    mainPanel.showModifiedImageInformations();
                }
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
        transformacoesAbaixoMediana.setToolTipText("Transforma pixels com valor abaixo da mediana para preto");
        transformacoesAbaixoMediana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (originalImage != null) {
                    mainPanel.setModifiedImage(Transformations.belowMedianToBlack(originalImage, originalImageStatistics));
                    mainPanel.showModifiedImageInformations();
                }
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
        transformacoesModa.setToolTipText("Transforma pixels com valor acima da moda para branco e pixels com valor igual ou abaixo da moda para preto");
        transformacoesModa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (originalImage != null) {
                    mainPanel.setModifiedImage(Transformations.aboveModeToWhiteOthersBlack(originalImage, originalImageStatistics));
                    mainPanel.showModifiedImageInformations();
                }
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
        transformacoesInverter.setToolTipText("Inverte valor dos pixels de forma que os pixels escuros fiquem claros");
        transformacoesInverter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (originalImage != null) {
                    mainPanel.setModifiedImage(Transformations.reversePixelsValue(originalImage));
                    mainPanel.showModifiedImageInformations();
                }
            }
        });
        return transformacoesInverter;
    }

    /**
     * Cria submenu para espelhar a imagem
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuTransformacoesEspelhar() {
        JMenuItem transformacoesEspelhar = new JMenuItem("Espelhar");
        transformacoesEspelhar.setMnemonic(KeyEvent.VK_E);
        transformacoesEspelhar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (originalImage != null) {
                    mainPanel.showMirrorPanel();
                }
            }
        });
        return transformacoesEspelhar;
    }
    
    /**
     * Cria submenu para transladar a imagem
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuTransformacoesTransladar() {
        JMenuItem transformacoesTransladar = new JMenuItem("Transladar");
        transformacoesTransladar.setMnemonic(KeyEvent.VK_T);
        transformacoesTransladar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (originalImage != null) {
                    mainPanel.showTransferPanel();
                }
            }
        });
        return transformacoesTransladar;
    }
    
    /**
     * Cria submenu para redimensionar a imagem
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuTransformacoesRedimensionar() {
        JMenuItem transformacoesRedimensionar = new JMenuItem("Redimensionar");
        transformacoesRedimensionar.setMnemonic(KeyEvent.VK_R);
        transformacoesRedimensionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (originalImage != null) {
                    
                }
            }
        });
        return transformacoesRedimensionar;
    }
    
    /**
     * Cria submenu para rotacionar a imagem
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuTransformacoesRotacionar() {
        JMenuItem transformacoesRotacionar = new JMenuItem("Rotacionar");
        transformacoesRotacionar.setMnemonic(KeyEvent.VK_O);
        transformacoesRotacionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (originalImage != null) {
                    
                }
            }
        });
        return transformacoesRotacionar;
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
    
}
