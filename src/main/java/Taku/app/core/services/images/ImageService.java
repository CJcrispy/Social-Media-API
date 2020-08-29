package Taku.app.core.services.images;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

@Service
public class ImageService {


    public boolean isImage(String image_path){
        Image image = new ImageIcon(image_path).getImage();
        if(image.getWidth(null) == -1){
            return false;
        }
        else{
            return true;
        }
    }
}
