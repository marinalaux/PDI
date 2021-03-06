package pdi;

import processes.Transformations;
import commons.ImageStatistics;
import commons.Image;
import com.sun.glass.events.KeyEvent;
import commons.ImageObject;
import commons.Mediana;
import commons.Moda;
import filters.AverageFilter;
import filters.ClosureFilter;
import filters.DilationFilter;
import filters.ErosionFilter;
import filters.GaussFilter;
import filters.GenericFilter;
import filters.KirschFilter;
import filters.MarrAndHildrethFilter;
import filters.OpeningFilter;
import filters.RobertsFilter;
import filters.RobinsonFilter;
import filters.SobelFilter;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
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
import processes.BinaryLabeling;
import processes.HoltProcess;

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
        menuBar.add(createMenuFiltros());
        menuBar.add(createMenuMorfologia());
        menuBar.add(createMenuAfinamento());
        menuBar.add(createMenuCaracteristicas());
        menuBar.add(createMenuBotanica());
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
     * Cria menu de primeiro nível para filtros
     * 
     * @return JMenu
     */
    private JMenu createMenuFiltros() {
        JMenu menuFiltros = new JMenu("Filtros");
        menuFiltros.setMnemonic(KeyEvent.VK_F);
        menuFiltros.add(createMenuFiltrosBrilho());
        menuFiltros.add(createMenuFiltrosContraste());
        menuFiltros.add(createMenuFiltrosPassaBaixa());
        menuFiltros.add(createMenuFiltrosPassaAlta());
        return menuFiltros;
    }
    
    /**
     * Cria menu de primeiro nível para morfologia matemática
     * 
     * @return JMenu
     */
    private JMenu createMenuMorfologia() {
        JMenu menuMorfologia = new JMenu("Morfologia matemática");
        menuMorfologia.setMnemonic(KeyEvent.VK_M);
        menuMorfologia.add(createMenuMorfologiaDilatacao());
        menuMorfologia.add(createMenuMorfologiaErosao());
        menuMorfologia.add(createMenuMorfologiaAbertura());
        menuMorfologia.add(createMenuMorfologiaFechamento());
        return menuMorfologia;
    }
    
    /**
     * Cria menu de primeiro nível para afinamento
     * 
     * @return JMenu
     */
    private JMenu createMenuAfinamento() {
        JMenu menuAfinamento = new JMenu("Afinamento");
        menuAfinamento.setMnemonic(KeyEvent.VK_N);
        menuAfinamento.add(createMenuAfinamentoHolt());
        return menuAfinamento;
    }
    
    /**
     * Cria menu de primeiro nível para extração de características
     * 
     * @return JMenu
     */
    private JMenu createMenuCaracteristicas() {
        JMenu menuCaracteristicas = new JMenu("Extração de características");
        menuCaracteristicas.setMnemonic(KeyEvent.VK_E);
        menuCaracteristicas.add(createMenuCaracteristicasQuadrado());
        menuCaracteristicas.add(createMenuCaracteristicasCirculos());
        return menuCaracteristicas;
    }
    
    /**
     * Cria menu de primeiro nível para características botânicas
     * 
     * @return JMenu
     */
    private JMenu createMenuBotanica() {
        JMenu menuBotanica = new JMenu("Botânica");
        menuBotanica.setMnemonic(KeyEvent.VK_B);
        menuBotanica.add(createMenuBotanicaFolha());
        return menuBotanica;
    }

    /**
     * Cria submenu para abrir a imagem
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuArquivoAbrir() {
        JMenuItem arquivoAbrir = new JMenuItem("Abrir imagem");
        arquivoAbrir.setMnemonic(KeyEvent.VK_A);
        arquivoAbrir.addActionListener((ActionEvent e) -> {
            openImage();
            mainPanel.updateOriginalImage(originalImage);
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
        arquivoAbrirEspecial.addSeparator();
        arquivoAbrirEspecial.add(createArquivoAbrirEye());
        arquivoAbrirEspecial.add(createArquivoAbrirLandscape());
        arquivoAbrirEspecial.addSeparator();
        arquivoAbrirEspecial.add(createArquivoAbrirExemploMorfologia());
        arquivoAbrirEspecial.addSeparator();
        arquivoAbrirEspecial.add(createArquivoAbrirExemploCaracteristicasQuadrado());
        arquivoAbrirEspecial.add(createArquivoAbrirExemploCaracteristicasCirculos());
        arquivoAbrirEspecial.addSeparator();
        arquivoAbrirEspecial.add(createArquivoAbrirExemploFolha1());
        arquivoAbrirEspecial.add(createArquivoAbrirExemploFolha2());
        arquivoAbrirEspecial.add(createArquivoAbrirExemploFolha3());
        arquivoAbrirEspecial.add(createArquivoAbrirExemploFolha4());
        return arquivoAbrirEspecial;
    }
    
    /**
     * Cria submenu para abrir imagem da Lena
     * 
     * @return JMenuItem
     */
    private JMenuItem createArquivoAbrirLena() {
        JMenuItem abrirLena = new JMenuItem("Lena");
        abrirLena.addActionListener((ActionEvent e) -> {
            loadImage(PDI.class.getResourceAsStream("/res/lena.png"));
            mainPanel.updateOriginalImage(originalImage);
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
        abrirEye.addActionListener((ActionEvent e) -> {
            loadImage(PDI.class.getResourceAsStream("/res/eye.png"));
            mainPanel.updateOriginalImage(originalImage);
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
        abrirLandscape.addActionListener((ActionEvent e) -> {
            loadImage(PDI.class.getResourceAsStream("/res/landscape.png"));
            mainPanel.updateOriginalImage(originalImage);
        });
        return abrirLandscape;
    }
    
    /**
     * Cria submenu para abrir imagem para execução da morfologia
     * 
     * @return JMenuItem
     */
    private JMenuItem createArquivoAbrirExemploMorfologia() {
        JMenuItem abrirExemploMorfologia = new JMenuItem("Exemplo morfologia");
        abrirExemploMorfologia.addActionListener(((e) -> {
            loadImage(PDI.class.getResourceAsStream("/res/risco_branco.png"));
            mainPanel.updateOriginalImage(originalImage);
        }));
        return abrirExemploMorfologia;
    }
    
    /**
     * Cria submenu para abrir imagem para execução da extração de características de quadrado
     * 
     * @return JMenuItem
     */
    private JMenuItem createArquivoAbrirExemploCaracteristicasQuadrado() {
        JMenuItem abrirExemploQuadrado = new JMenuItem("Exemplo extração de características: Quadrado");
        abrirExemploQuadrado.addActionListener((ActionEvent e) -> {
            loadImage(PDI.class.getResourceAsStream("/res/quadrado.png"));
            mainPanel.updateOriginalImage(originalImage);
        });
        return abrirExemploQuadrado;
    }
    
    /**
     * Cria submenu para abrir imagem para execução da extração de características de círculos
     * 
     * @return JMenuItem
     */
    private JMenuItem createArquivoAbrirExemploCaracteristicasCirculos() {
        JMenuItem abrirExemploCirculos = new JMenuItem("Exemplo extração de características: Círculos");
        abrirExemploCirculos.addActionListener((ActionEvent e) -> {
            loadImage(PDI.class.getResourceAsStream("/res/circulos.png"));
            mainPanel.updateOriginalImage(originalImage);
        });
        return abrirExemploCirculos;
    }
    
    /**
     * Cria submenu para abrir imagem da folha 1
     * 
     * @return JMenuItem
     */
    private JMenuItem createArquivoAbrirExemploFolha1() {
        JMenuItem abrirExemploFolha1 = new JMenuItem("Exemplo botânica: Folha 1");
        abrirExemploFolha1.addActionListener((ActionEvent e) -> {
            loadImage(PDI.class.getResourceAsStream("/res/folha_1.PNG"));
            mainPanel.updateOriginalImage(originalImage);
        });
        return abrirExemploFolha1;
    }

    /**
     * Cria submenu para abrir imagem da folha 2
     * 
     * @return JMenuItem
     */
    private JMenuItem createArquivoAbrirExemploFolha2() {
        JMenuItem abrirExemploFolha2 = new JMenuItem("Exemplo botânica: Folha 2");
        abrirExemploFolha2.addActionListener((ActionEvent e) -> {
            loadImage(PDI.class.getResourceAsStream("/res/folha_2.PNG"));
            mainPanel.updateOriginalImage(originalImage);
        });
        return abrirExemploFolha2;
    }

    /**
     * Cria submenu para abrir imagem da folha 3
     * 
     * @return JMenuItem
     */
    private JMenuItem createArquivoAbrirExemploFolha3() {
        JMenuItem abrirExemploFolha3 = new JMenuItem("Exemplo botânica: Folha 3");
        abrirExemploFolha3.addActionListener((ActionEvent e) -> {
            loadImage(PDI.class.getResourceAsStream("/res/folha_3.PNG"));
            mainPanel.updateOriginalImage(originalImage);
        });
        return abrirExemploFolha3;
    }

    /**
     * Cria submenu para abrir imagem da folha 4
     * 
     * @return JMenuItem
     */
    private JMenuItem createArquivoAbrirExemploFolha4() {
        JMenuItem abrirExemploFolha4 = new JMenuItem("Exemplo botânica: Folha 4");
        abrirExemploFolha4.addActionListener((ActionEvent e) -> {
            loadImage(PDI.class.getResourceAsStream("/res/folha_4.PNG"));
            mainPanel.updateOriginalImage(originalImage);
        });
        return abrirExemploFolha4;
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
        arquivoSalvarOriginal.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                File original = new File(System.getProperty("user.home") + "\\Desktop\\ImagemOriginal.png");
                try {
                    ImageIO.write(originalImage.toBufferedImage(), "png", original);
                } catch (IOException ex) {
                    ex.printStackTrace();
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
        arquivoSalvarTransformada.addActionListener((ActionEvent e) -> {
            if (mainPanel.getModifiedImage() != null) {
                File modified = new File(System.getProperty("user.home") + "\\Desktop\\ImagemModificada.png");
                try {
                    ImageIO.write(mainPanel.getModifiedImage().toBufferedImage(), "png", modified);
                } catch (IOException ex) {
                    ex.printStackTrace();
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
        arquivoSair.addActionListener((ActionEvent e) -> {
            dispose();
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
        estatisticaCalculos.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showOriginalImageStatistics(originalImageStatistics);
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
        estatisticaHistograma.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showOriginalImageHistogram(originalImageStatistics);                
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
        transformacoesAcimaMedia.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.setModifiedImage(Transformations.aboveAvgToWhite(originalImage, originalImageStatistics));
                mainPanel.showModifiedImageInformations();
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
        transformacoesAbaixoMediana.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.setModifiedImage(Transformations.belowMedianToBlack(originalImage, originalImageStatistics));
                mainPanel.showModifiedImageInformations();
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
        transformacoesModa.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.setModifiedImage(Transformations.aboveModeToWhiteOthersBlack(originalImage, originalImageStatistics));
                mainPanel.showModifiedImageInformations();
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
        transformacoesInverter.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.setModifiedImage(Transformations.reversePixelsValue(originalImage));
                mainPanel.showModifiedImageInformations();
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
        transformacoesEspelhar.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showMirrorPanel();
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
        transformacoesTransladar.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showTransferPanel();
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
        transformacoesRedimensionar.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showResizePanel();
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
        transformacoesRotacionar.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showRotationPanel();
            }
        });
        return transformacoesRotacionar;
    }
    
    /**
     * Cria submenu para aplicação de brilho
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuFiltrosBrilho() {
        JMenuItem filtrosBrilho = new JMenuItem("Brilho");
        filtrosBrilho.setMnemonic(KeyEvent.VK_B);
        filtrosBrilho.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showBrightnessPanel();
            }
        });
        return filtrosBrilho;
    }
    
    /**
     * Cria submenu para aplicação de contraste
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuFiltrosContraste() {
        JMenuItem filtroContraste = new JMenuItem("Contraste");
        filtroContraste.setMnemonic(KeyEvent.VK_C);
        filtroContraste.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showContrastPanel();
            }
        });
        return filtroContraste;
    }
    
    /**
     * Cria submenu para aplicação de filtros passa-baixas
     * 
     * @return JMenu
     */
    private JMenu createMenuFiltrosPassaBaixa() {
        JMenu filtroPassaBaixa = new JMenu("Filtros Passa-Baixas");
        filtroPassaBaixa.setMnemonic(KeyEvent.VK_P);
        filtroPassaBaixa.add(createMenuFiltrosMedia());
        filtroPassaBaixa.add(createMenuFiltrosModa());
        filtroPassaBaixa.add(createMenuFiltrosMediana());
        filtroPassaBaixa.add(createMenuFiltrosGauss());
        return filtroPassaBaixa;
    }
    
    /**
     * Cria submenu para aplicação de filtro média
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuFiltrosMedia() {
        JMenuItem filtroMedia = new JMenuItem("Média");
        filtroMedia.setMnemonic(KeyEvent.VK_M);
        filtroMedia.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.setModifiedImage(new AverageFilter().apply(originalImage));
                mainPanel.showModifiedImageInformations();
            }
        });
        return filtroMedia;
    }
    
    /**
     * Cria submenu para aplicação de filtro moda
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuFiltrosModa() {
        JMenuItem filtroModa = new JMenuItem("Moda");
        filtroModa.setMnemonic(KeyEvent.VK_O);
        filtroModa.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.setModifiedImage(new GenericFilter().apply(originalImage, new Moda()));
                mainPanel.showModifiedImageInformations();
            }
        });
        return filtroModa;
    }
    
    /**
     * Cria submenu para aplicação de filtro mediana
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuFiltrosMediana() {
        JMenuItem filtroMediana = new JMenuItem("Mediana");
        filtroMediana.setMnemonic(KeyEvent.VK_E);
        filtroMediana.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.setModifiedImage(new GenericFilter().apply(originalImage, new Mediana()));
                mainPanel.showModifiedImageInformations();
            }
        });
        return filtroMediana;
    }
    
    /**
     * Cria submenu para aplicação de filtro Gauss
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuFiltrosGauss() {
        JMenuItem filtroGauss = new JMenuItem("Gauss");
        filtroGauss.setMnemonic(KeyEvent.VK_G);
        filtroGauss.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.setModifiedImage(new GaussFilter().apply(originalImage));
                mainPanel.showModifiedImageInformations();
            }
        });
        return filtroGauss;
    }
    
    /**
     * Cria submenu para aplicação de filtros passa-altas
     * 
     * @return JMenu
     */
    private JMenu createMenuFiltrosPassaAlta() {
        JMenu filtroPassaAlta = new JMenu("Filtros Passa-Altas");
        filtroPassaAlta.setMnemonic(KeyEvent.VK_A);
        filtroPassaAlta.add(createMenuFiltrosRoberts());
        filtroPassaAlta.add(createMenuFiltrosSobel());
        filtroPassaAlta.add(createMenuFiltrosKirsch());
        filtroPassaAlta.add(createMenuFiltrosRobinson());
        filtroPassaAlta.add(createMenuFiltrosMarrAndHildreth());
        return filtroPassaAlta;
    }
    
    /**
     * Cria submenu para aplicação de filtro de Roberts
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuFiltrosRoberts() {
        JMenuItem filtroRoberts = new JMenuItem("Roberts");
        filtroRoberts.setMnemonic(KeyEvent.VK_R);
        filtroRoberts.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showThresholdingPanel(new RobertsFilter());
            }
        });
        return filtroRoberts;
    }
    
    /**
     * Cria submenu para aplicação de filtro de Sobel
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuFiltrosSobel() {
        JMenuItem filtroSobel = new JMenuItem("Sobel");
        filtroSobel.setMnemonic(KeyEvent.VK_S);
        filtroSobel.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showThresholdingPanel(new SobelFilter());
            }
        });
        return filtroSobel;
    }
    
    /**
     * Cria submenu para aplicação de filtro de Kirsch
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuFiltrosKirsch() {
        JMenuItem filtroKirsch = new JMenuItem("Kirsch");
        filtroKirsch.setMnemonic(KeyEvent.VK_K);
        filtroKirsch.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showThresholdingPanel(new KirschFilter());
            }
        });
        return filtroKirsch;
    }
    
    /**
     * Cria submenu para aplicação de filtro de Robinson
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuFiltrosRobinson() {
        JMenuItem filtroRobinson = new JMenuItem("Robinson");
        filtroRobinson.setMnemonic(KeyEvent.VK_B);
        filtroRobinson.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showThresholdingPanel(new RobinsonFilter());
            }
        });
        return filtroRobinson;
    }
    
    /**
     * Cria submenu para aplicação de filtro de Marr e Hildreth
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuFiltrosMarrAndHildreth() {
        JMenuItem filtroMarrAndHildreth = new JMenuItem("Marr and Hildreth");
        filtroMarrAndHildreth.setMnemonic(KeyEvent.VK_M);
        filtroMarrAndHildreth.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showThresholdingPanel(new MarrAndHildrethFilter());
            }
        });
        return filtroMarrAndHildreth;
    }
    
    /**
     * Cria submenu para dilatação da imagem
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuMorfologiaDilatacao() {
        JMenuItem morfologiaDilatacao = new JMenuItem("Dilatação");
        morfologiaDilatacao.setMnemonic(KeyEvent.VK_D);
        morfologiaDilatacao.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.setModifiedImage(new DilationFilter().apply(originalImage));
                mainPanel.showModifiedImageInformations();
            }
        });
        return morfologiaDilatacao;
    }
    
    /**
     * Cria submenu para erosão da imagem
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuMorfologiaErosao() {
        JMenuItem morfologiaErosao = new JMenuItem("Erosão");
        morfologiaErosao.setMnemonic(KeyEvent.VK_E);
        morfologiaErosao.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.setModifiedImage(new ErosionFilter().apply(originalImage));
                mainPanel.showModifiedImageInformations();
            }
        });
        return morfologiaErosao;
    }
    
    /**
     * Cria submenu para abertura da imagem
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuMorfologiaAbertura() {
        JMenuItem morfologiaAbertura = new JMenuItem("Abertura");
        morfologiaAbertura.setMnemonic(KeyEvent.VK_A);
        morfologiaAbertura.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.setModifiedImage(new OpeningFilter().apply(originalImage));
                mainPanel.showModifiedImageInformations();
            }
        });
        return morfologiaAbertura;
    }
    
    /**
     * Cria submenu para fechamento da imagem
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuMorfologiaFechamento() {
        JMenuItem morfologiaFechamento = new JMenuItem("Fechamento");
        morfologiaFechamento.setMnemonic(KeyEvent.VK_F);
        morfologiaFechamento.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.setModifiedImage(new ClosureFilter().apply(originalImage));
                mainPanel.showModifiedImageInformations();
            }
        });
        return morfologiaFechamento;
    }
    
    /**
     * Cria submenu para afinamento de Holt
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuAfinamentoHolt() {
        JMenuItem afinamentoHolt = new JMenuItem("Holt");
        afinamentoHolt.setMnemonic(KeyEvent.VK_H);
        afinamentoHolt.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.setModifiedImage(new HoltProcess().apply(originalImage));
                mainPanel.showModifiedImageInformations();
            }
        });
        return afinamentoHolt;
    }
    
    /**
     * Cria submenu para extração de características de um quadrado
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuCaracteristicasQuadrado() {
        JMenuItem caracteristicasQuadrado = new JMenuItem("Características quadrado");
        caracteristicasQuadrado.setMnemonic(KeyEvent.VK_Q);
        caracteristicasQuadrado.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                ImageObject quadrado = new ImageObject();
                for (int x = 0; x < originalImage.getWidth(); x++) {
                    for (int y = 0; y < originalImage.getHeight(); y++) {
                        int pixelValue = originalImage.getPixels()[x][y];
                        if (pixelValue == 0) {
                            quadrado.setArea(quadrado.getArea() + 1);
                            if (x == 0 || y == 0 || x == originalImage.getWidth() - 1
                                    || y == originalImage.getHeight() - 1
                                    || originalImage.getPixels()[x][y - 1] == 255
                                    || originalImage.getPixels()[x - 1][y] == 255
                                    || originalImage.getPixels()[x + 1][y] == 255
                                    || originalImage.getPixels()[x][y + 1] == 255) {
                                // Se for canto deve contar 2x como parte do perímetro
                                quadrado.setPerimetro(quadrado.getPerimetro() + 1);
                            }
                        }
                    }
                }
                System.out.println("Perímetro: " + quadrado.getPerimetro());
                System.out.println("Área: " + quadrado.getArea());

                BinaryLabeling binary = new BinaryLabeling(originalImage, 255, 0);
                binary.apply();
                mainPanel.setModifiedImage(binary.getLabeledImage());
                mainPanel.showModifiedImageInformations();
            }
        });
        return caracteristicasQuadrado;
    }
    
    /**
     * Cria submenu para extração de características de círculos
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuCaracteristicasCirculos() {
        JMenuItem caracteristicasCirculos = new JMenuItem("Características círculos");
        caracteristicasCirculos.setMnemonic(KeyEvent.VK_C);
        caracteristicasCirculos.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {

                BinaryLabeling binary = new BinaryLabeling(originalImage, 255, 0);
                binary.apply();
                mainPanel.setModifiedImage(binary.getLabeledImage());
                mainPanel.showModifiedImageInformations();
                
            }
        });
        return caracteristicasCirculos;
    }
    
    /**
     * Cria submenu para extração de características de folhas
     * 
     * @return JMenuItem
     */
    private JMenuItem createMenuBotanicaFolha() {
        JMenuItem menuBotanicaFolhas = new JMenuItem("Folhas");
        menuBotanicaFolhas.addActionListener((ActionEvent e) -> {
            if (originalImage != null) {
                mainPanel.showBotanyImageInformations();
            }
        });
        return menuBotanicaFolhas;
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
