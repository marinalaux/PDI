package processes;

import commons.Image;
import commons.ImageObject;
import filters.BrightnessContrastFilter;
import filters.ClosureFilter;
import filters.DilationFilter;
import filters.ErosionFilter;
import filters.GaussFilter;
import filters.MarrAndHildrethFilter;
import filters.OpeningFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Botânica
 */
public class Botany {
    
    /** Imagem da folha */
    private Image image;
    /** Lista de objetos da imagem */
    private List<ImageObject> objects;

    /**
     * Construtor
     * 
     * @param image 
     */
    public Botany(Image image) {
        this.image = new Image(image.getHeight(), image.getWidth());
        this.image.setPixels(image.getPixels());
        this.objects = new ArrayList<>();
    }
    
    /**
     * Aplica processos para extração das manchas de ferrugem
     * 
     * @return Imagem
     */
    public Image applyProcessesOld() {
        image = new GaussFilter().apply(image);
        image = BrightnessContrastFilter.applyContrast(image, 1.8);
        image = BrightnessContrastFilter.applyBrightness(image, -20);
        image = new DilationFilter().apply(image);
        image = new GaussFilter().apply(image);
        image = new DilationFilter().apply(image);
        image = new DilationFilter().apply(image);
        image = new GaussFilter().apply(image);
        image = new MarrAndHildrethFilter().apply(new GaussFilter().apply(image), 80);
        image = new GaussFilter().apply(image);
        image = new ErosionFilter().apply(image);
        image = new ErosionFilter().apply(image);
//        image = new GaussFilter().apply(image);
//        image = new ErosionFilter().apply(image);
//        image = new GaussFilter().apply(image);
//        image = BrightnessContrastFilter.applyContrast(image, 3.0);
        image = new OpeningFilter().apply(image);
//        image = new GaussFilter().apply(image);
        image = new DilationFilter().apply(image);
        image = new OpeningFilter().apply(image);
//        image = new DilationFilter().apply(image);
//        image = BrightnessContrastFilter.applyContrast(image, 3.0);
//        image = new DilationFilter().apply(image);
//        image = new DilationFilter().apply(image);
//        image = new DilationFilter().apply(image);


//        BinaryLabeling binary = new BinaryLabeling(image, 0, 255, 155);
//        binary.apply();
//        image = binary.getLabeledImage();
//        objects = binary.getObjects();
        
        return image;
    }
    
    /**
     * Aplica processos para extração das manchas de ferrugem
     * 
     * @return Imagem modificada
     */
    public Image applyProcesses() {
        // Elimina detalhes desnecessários para análise da folha
        image = new GaussFilter().apply(image);
        image = new GaussFilter().apply(image);
        image = BrightnessContrastFilter.applyContrast(image, 2.0);
        image = BrightnessContrastFilter.applyBrightness(image, -30);
        // Destaca pixels mais escuros da imagem
        image = Threshold.apply(image, 100, 0);
        // Expande os objetos escuros (ferrugem) identificados
        image = new DilationFilter().apply(image);
        image = new GaussFilter().apply(image);
        image = new DilationFilter().apply(image);
        image = new GaussFilter().apply(image);
        image = new ClosureFilter().apply(image);
        image = new GaussFilter().apply(image);
        // Destaca bordas das ferrugens identificadas
        image = new MarrAndHildrethFilter().apply(new GaussFilter().apply(image), 80);
        // Elimina ruídos que possam ter surgido na imagem pós filtro passa-alta
        image = new GaussFilter().apply(image);
        image = new GaussFilter().apply(image);
        image = new ErosionFilter().apply(image);
        image = new GaussFilter().apply(image);
        // Elimina objetos/riscos identificados erroneamente
        image = Threshold.apply(image, 100, 0);
        // Sobressalta objetos identificados
        image = new OpeningFilter().apply(image);
        image = new ErosionFilter().apply(image);
        image = new DilationFilter().apply(image);
        image = new OpeningFilter().apply(image);
        // Binariza objetos
        image = Threshold.apply(image, 100, 0);
        image = BrightnessContrastFilter.applyContrast(image, 5.0);
        // Elimina objetos que não serão analisados
        image = new GaussFilter().apply(image);
        image = new ErosionFilter().apply(image);
        image = new OpeningFilter().apply(image);
        image = new ErosionFilter().apply(image);
        image = new ErosionFilter().apply(image);
        image = new DilationFilter().apply(image);
        image = new OpeningFilter().apply(image);
        image = new ErosionFilter().apply(image);
        image = new ErosionFilter().apply(image);
        image = new OpeningFilter().apply(image);
        // Expande objetos restantes
        image = Threshold.apply(image, 50, 0);
        image = new DilationFilter().apply(image);
        image = new DilationFilter().apply(image);
        image = new DilationFilter().apply(image);
        image = new DilationFilter().apply(image);
        image = new GaussFilter().apply(image);
        // Binariza cor dos objetos identificados
        image = Binarization.apply(image, 100);
        // Inverte as cores da imagem para melhor identificação visual dos objetos
        image = Transformations.reversePixelsValue(image);
        // Separa objetos identificados
        BinaryLabeling binary = new BinaryLabeling(image, 255, 0);
        binary.apply();
        image = binary.getLabeledImage();
        objects = binary.getObjects();
        
        return image;
    }
    
    /**
     * Retorna qual o melhor tratamento para a folha
     * 
     * @return Tratamento
     */
    public String getTreatment() {
        if (objects.size() > 3) {
            return "GRAVE (" + objects.size() + "): A folha deve ser pulverizada com Aerosol anti-ferrugem botânica!";
        }
        int objsMaior20 = 0;
        for (ImageObject obj : objects) {
            if (obj.getQtdPixels() > 20) {
                objsMaior20++;
            }
        }
        if (objsMaior20 == 1) {
            return "INICIAL (" + objects.size() + "): A folha deve ser tratada com chá de alho!";
        }
        if (objsMaior20 > 1) {
            return "MÉDIA (" + objects.size() + "): A folha deve ser tratada com pó de calcário dissolvido em 1 litro de água!";
        }
        return "Folha sem contaminação!";
    }
    
}
