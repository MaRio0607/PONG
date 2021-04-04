package mx.itesm.mx;
/*
Creado por mario
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaJuego implements Screen {
    private final Pong pong;
// tamaño del mundo
    public static final float ANCHO = 1200;
    public static final float ALTO = 800;
    //camara
    private OrthographicCamera camara;
    private Viewport vista;
    //Administrador de trazos sobre la pantalla
    private SpriteBatch batch;
    //Texturas
    private Texture texturaBarraHorizontal;
    private Texture texturaRaqueta;
    private Texture texturaCuadro;
    //Objeto
    private Pelota pelota;


    public PantallaJuego(Pong pong) {
        this.pong=pong;
    }
//esta metodo se ejecuta la mostrarse la vista en la pantalla
    @Override
    public void show() {
        //Creae la camara
        camara=new OrthographicCamera(ANCHO, ALTO);
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();

        vista=new StretchViewport(ANCHO,ALTO,camara);
        batch = new SpriteBatch();

        //Cargar todas las texturas
        cargarTextura();
        //crear todos los objetos
        crearObjetos();
    }

    private void crearObjetos() {
        pelota=new Pelota(texturaCuadro,ANCHO/2,ALTO/2);
    }

    private void cargarTextura() {
        texturaBarraHorizontal=new Texture("barraHorizontal.png");
        texturaRaqueta=new Texture("raqueta.png");
        texturaCuadro= new Texture("cuadrito.png");
    }

    //Esta metodoo dibuja en la pantalla
    @Override
    public void render(float delta) {
        //actualizar los objetos en la pantalla
        actualzarObjetos();
        //borrar pantalla
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        // aqui se dibijan los elementos del juego
        batch.draw(texturaBarraHorizontal,0,0);
        batch.draw(texturaBarraHorizontal,0,ALTO-texturaBarraHorizontal.getHeight());
        //linea central
        for (float y=0; y<ALTO;y+=2*texturaCuadro.getHeight()){
            batch.draw(texturaCuadro,ANCHO/2,y);
        }
        //Dibuja las Raquetas
        batch.draw(texturaRaqueta,0,ALTO/2);
        batch.draw(texturaRaqueta, ANCHO-texturaRaqueta.getWidth(),ALTO/2);
        //Dibuja la pelotas
        pelota.render(batch);
        batch.end();
    }

    private void actualzarObjetos() {
        pelota.mover();
    }

    //se ejecuta ciando la ventana cambia de tamaño
    @Override
    public void resize(int width, int height) {
    vista.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
