package processes;

import commons.Image;
import commons.ImageObject;
import filters.BrightnessContrastFilter;
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
    public Image applyProcesses() {
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
        image = new GaussFilter().apply(image);
        image = new ErosionFilter().apply(image);
        image = new GaussFilter().apply(image);
        image = BrightnessContrastFilter.applyContrast(image, 3.0);
        image = new OpeningFilter().apply(image);
        image = new GaussFilter().apply(image);
        image = new DilationFilter().apply(image);
        image = new DilationFilter().apply(image);
        image = BrightnessContrastFilter.applyContrast(image, 3.0);
        image = new DilationFilter().apply(image);
        image = new DilationFilter().apply(image);
        image = new DilationFilter().apply(image);
        
//        BinaryLabeling binary = new BinaryLabeling(image, 0, 255, 155);
//        binary.apply();
//        image = binary.getLabeledImage();
//        objects = binary.getObjects();
        
        System.out.println("Qtd obj: " + objects.size());
        
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
            return "INICIAL: A folha deve ser tratada com chá de alho!";
        }
        if (objsMaior20 > 1) {
            return "MÉDIA: A folha deve ser tratada com pó de calcário dissolvido em 1 litro de água!";
        }
        return "Folha sem contaminação!";
    }
    
}
