package mx.itesm.mx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Objeto {
    //Sprite que representa graficamente el objeto
    protected Sprite sprite;
    //consructor
    public Objeto(Texture texture, float x, float y){
        sprite=new Sprite(texture);
        sprite.setPosition(x,y);
    }
}
