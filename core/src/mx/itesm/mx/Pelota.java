package mx.itesm.mx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pelota extends Objeto {
    //Desplaxa cada fotograma
    private float DX=4;
    private float DY=4;

    public Pelota(Texture texture, float x, float y) {
        super(texture, x, y);
    }
    //dibuja el objeto
    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }
    //actualiza la poscision del sprite
    public void mover(Raqueta raqueta){
        float xp=sprite.getX();
        float yp=sprite.getY();
        //prueba limites de la dereche-izquierda
        if (xp>=PantallaJuego.ANCHO-sprite.getWidth() ){
            DX=-DX;
        }
        //Prueba colisiones con la raqueta del juegador
        float xr= raqueta.sprite.getX();
        float yr=raqueta.sprite.getY();
        if (xp>=xr && xp==xr+raqueta.sprite.getWidth() &&
                yp>=yr && yp<=(yr+raqueta.sprite.getHeight()-sprite.getHeight() )){
            DX=-DX;
        }

        //prueba limites de la Arriba-Abajo
        if (yp>=PantallaJuego.ALTO-sprite.getHeight() || yp<=0){
            DY=-DY;
        }
        //Mueve la pelota a la nueva poscicion
        sprite.setPosition(xp+DX,yp+DY);
    }
}
