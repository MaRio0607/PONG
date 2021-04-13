package mx.itesm.mx;
/*
Creado por mario
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
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
    //Raqueta
    private Raqueta raquetaCPU;
    private Raqueta raquetaPLAY;
    //Marcador
    private int puntosJugador=0;
    private int puntosMaquina=0;
    private Texto texto;    //Muestra los valores en la pantalla
    //Estado del juego
    private Estado estado= Estado.JUGANDO;

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
        //indica quien escucha y atiende eventos
        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearObjetos() {
        pelota=new Pelota(texturaCuadro,ANCHO/2,ALTO/2);
        raquetaCPU=new Raqueta(texturaRaqueta, ANCHO-texturaRaqueta.getWidth(),ALTO/2);
        raquetaPLAY=new Raqueta(texturaRaqueta,0,ALTO/2);
        //onjeto que dibuja texto
        texto=new Texto();
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

        //prueba si pierde el jugador
        if (estado==Estado.JUGANDO && pelota.sprite.getX()<2){
            puntosMaquina++;
            if (puntosMaquina>=5){
                //perdio el jugador
                estado=Estado.PIERDE;
            }
            //Reinicia la pelota
            pelota.sprite.setPosition(ANCHO/2, ALTO/2);
        }

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
        //batch.draw(texturaRaqueta,0,ALTO/2);
        //batch.draw(texturaRaqueta, ANCHO-texturaRaqueta.getWidth(),ALTO/2);
        raquetaPLAY.dibujar(batch);
        raquetaCPU.dibujar(batch);
        //Dibuja la pelotas
        pelota.render(batch);
        //Dibuja el marcador
        texto.mostrarMensaje(batch,Integer.toString(puntosJugador),ANCHO/2-ANCHO/6,3*ALTO/4);
        texto.mostrarMensaje(batch,Integer.toString(puntosMaquina),ANCHO/2+ANCHO/6,3*ALTO/4);

        //pierde
        if(estado==Estado.PIERDE){
            texto.mostrarMensaje(batch,"Lo siento, Perdiste...",ANCHO/2,ALTO/2);
            texto.mostrarMensaje(batch,"Tap Para Continuar...",3*ANCHO/4,ALTO/4);

        }
        batch.end();
    }

    private void actualzarObjetos() {
        if (estado==Estado.JUGANDO) {
            pelota.mover(raquetaPLAY);
            raquetaCPU.seguirPelota(pelota);
        }
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

    //Esta clase sirvve para crear un objeta que atienda las instruciones de touch
    class ProcesadorEntrada implements InputProcessor{

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if(estado==Estado.PIERDE){
                puntosJugador=0;
                puntosMaquina=0;
                pelota.sprite.setPosition(ANCHO/2,ALTO/2);
                estado=Estado.JUGANDO;
            }
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            Vector3 v = new Vector3(screenX,screenY,0);
            camara.unproject(v);
            raquetaPLAY.sprite.setY(v.y);
            return true;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }
    }
}
