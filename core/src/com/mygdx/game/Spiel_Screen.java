package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by Laurenz on 15.11.2015.
 */

public class Spiel_Screen extends Stage implements Screen {
    //MVC
    private Model model;
    private Controller controller;
    private ArrowTower arrowTower;
    //Screen Elemente
    private KeyBack_Menu_Screen game;
    private Stage stage;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    //Animation
    private static final int FRAME_COLS = 2;
    private static final int FRAME_ROWS = 4;
    //Movement
    private MoveToAction ac;
    //Animation
    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    private SpriteBatch spriteBatch;
    private SpriteBatch npcSpriteBatch;
    private TextureRegion currentFrame;
    private float stateTime;
    private Enemy enemy;
    private int angle;
    //Shaperenderer
    private ShapeRenderer sr;
    //Gui- Elemente
    private Label label,gold,life;
    private Image sourceImage;
    //Actual User Touchpoints
    private Vector2 vec;
    private Vector3 touchPoint;
    //Collision Detection und Movement Controll
    private Array<Rectangle> tiled_tower_fields, tiled_npc_fields;
    private TextureAtlas menu,menuicons;
    private TextureRegion tablem;
    private Skin uiskin;
    private Array<ImageButton> testbutton;
    private ImageButton.ImageButtonStyle tb, tb1, tb2, tb3, tb4, tb5, tb6, tb7, tb8;
    private Table table;
    final Table table2 = new Table();
    private Viewport viewport;
    private int mapPixelHeight,mapPixelWidth;

    public Array<ImageButton> getTestbutton() {
        return testbutton;
    }


    private Array<ImageButton.ImageButtonStyle> testbuttonstyle;

    public Spiel_Screen(KeyBack_Menu_Screen g) {
    //MVC Objects
        this.game = g;
        model = new Model(this);
        arrowTower = new ArrowTower();
        final Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.getFont("default-font").getData().setScale(3, 3);
     //Tiled Map wird geladen und dessen Informartion geholten
        tiledMap = new TmxMapLoader().load("map_new.tmx");
        MapProperties prop = tiledMap.getProperties();
    //Dimension(Anzahl) der Tiles
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
     //Größe eines Tiles
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);
    //Auflösung wird Berrechnet Tiles * Anzahl der Tiles z.B 1980/1080
         mapPixelWidth = mapWidth * tilePixelWidth;
         mapPixelHeight = mapHeight * tilePixelHeight;
     // Tiledmap wird gerendert (Sonst kann nicht angezeigt werden)
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
    //Camera
        stage = new Stage(new StretchViewport(mapPixelWidth+360, mapPixelHeight)); //+360 Wegen Tabellengröße Width7
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, stage.getWidth(), stage.getHeight());
        camera.update();
    //Gui Elements

        tiled_npc_fields = new Array<Rectangle>();
        tiled_npc_fields = model.getTiledObjects(tiledMap, tiled_npc_fields,"position");

        tiled_tower_fields = new Array<Rectangle>();
        tiled_tower_fields = model.getTiledObjects(tiledMap, tiled_tower_fields,"towers");

        touchPoint = new Vector3();

        skin.add("hero", new Texture("Hero.png"));

     //sourceImage.setPosition(-20,-20);

        //label = new Label("NPC", skin);
        gold= new Label("Gold  :", skin);
        life= new Label("Life  :", skin);


    //Tower_Menü
        table = new Table();
        table.defaults().height(stage.getHeight());
        table.setFillParent(true);
        table2.add(gold).pad(15);
        table2.add(life).pad(15);
        table2.row();

        sr = new ShapeRenderer();

        menu = new TextureAtlas("menu.pack");
        //Tower_Menü Hintergrundbild
        tablem = menu.findRegion("wood");

        menuicons = new TextureAtlas("menuicons.pack");
        uiskin = new Skin(menuicons);
        testbuttonstyle= new Array<ImageButton.ImageButtonStyle>();

        //Tower_Menu_Buttons werden Deklariert

        tb = new ImageButton.ImageButtonStyle();
        tb1 = new ImageButton.ImageButtonStyle();
        tb2 = new ImageButton.ImageButtonStyle();
        tb3 = new ImageButton.ImageButtonStyle();
        tb4 = new ImageButton.ImageButtonStyle();
        tb5 = new ImageButton.ImageButtonStyle();
        tb6 = new ImageButton.ImageButtonStyle();
        tb7 = new ImageButton.ImageButtonStyle();
        tb8 = new ImageButton.ImageButtonStyle();
        //Bilder werden den Tower_Buttons hinzugefügt
        tb.up  = uiskin.getDrawable("arrow");
        testbuttonstyle.add(tb);
        tb2.up  = uiskin.getDrawable("cannon");
        testbuttonstyle.add(tb2);
        tb3.up  = uiskin.getDrawable("fire");
        testbuttonstyle.add(tb3);
        tb4.up  = uiskin.getDrawable("water");
        testbuttonstyle.add(tb4);
        tb5.up  = uiskin.getDrawable("darkness");
        testbuttonstyle.add(tb5);
        tb6.up  = uiskin.getDrawable("light");
        testbuttonstyle.add(tb6);
        tb7.up  = uiskin.getDrawable("nature");
        testbuttonstyle.add(tb7);
        tb8.up  = uiskin.getDrawable("earth");
        testbuttonstyle.add(tb8);

        testbutton = new Array<ImageButton>();

        Rectangle rec= new Rectangle();

         //Tower_Buttons werden der Tabelle hinzugfügt
          for(int i=0;i<menuicons.getRegions().size;i++){
                ImageButton item1Button = new ImageButton(testbuttonstyle.get(i));
                testbutton.add(item1Button);
                testbutton.get(i).setName(""+i);
                testbutton.get(i).addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                         System.out.println("sadas");
                         model.setMode(1);
                         model.setTowerNumberClicked= actor.getName();
                         model.changeTowerNumbertoName(model.setTowerNumberClicked);
                 }});



          table2.add(testbutton.get(i)).pad(30);
              if(i==1){
                  table2.row();
              }
              if(i==3){
                  table2.row();
              }
              if(i==5){
                  table2.row();
              }
              if(i==7){
                  table2.row();
              }
          }


        table2.setBackground(new TextureRegionDrawable(new TextureRegion(tablem)));
        table2.setHeight(stage.getHeight());
        table.add(table2);
        table.right();


    //Animation
        spriteBatch = new SpriteBatch();
        npcSpriteBatch = new SpriteBatch();
        walkSheet = new Texture(Gdx.files.internal("runningcat.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.2f, walkFrames);      // #11
        stateTime = 0f;                         // #13
        enemy = Enemy.createEnemy(walkAnimation);

        //NPC startPosition
        //label.setPosition(tiled_npc_fields.get(0).x, tiled_npc_fields.get(0).y);
        enemy.setPosition(tiled_npc_fields.get(0).x, tiled_npc_fields.get(0).y);
        //MovetoAction wird aufgerufen und sagt wie sich das NPC bewegen soll
        ac = new MoveToAction();
        ac.setPosition(tiled_npc_fields.get(1).x, tiled_npc_fields.get(1).y);
        ac.setDuration(3);
        //label.addAction(ac);
        enemy.addAction(ac);

    //Listeners und Stage platzierungen
        stage.addActor(arrowTower);
        //stage.addActor(label);
        stage.addActor(enemy);
        stage.addActor(table);
     }

    public static Vector2 getStageLocation(Actor actor) {
        return actor.localToStageCoordinates(new Vector2(0, 0));
    }


    private NinePatch getNinePatch(String fname) {

        // Get the image
        final Texture t = new Texture(Gdx.files.internal(fname));

        // create a new texture region, otherwise black pixels will show up too, we are simply cropping the image
        // last 4 numbers respresent the length of how much each corner can draw,
        // for example if your image is 50px and you set the numbers 50, your whole image will be drawn in each corner
        // so what number should be good?, well a little less than half would be nice
        return new NinePatch(new TextureRegion(t, 1, 1 , t.getWidth() - 2, t.getHeight() - 2), 10, 10, 10, 10);
    }

    @Override
    public void render(float delta) {
        //Prepare Screen
        Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        //sr.setProjectionMatrix(camera.combined);

        renderer.setView(camera);

        //Animation Bewegung
        stateTime += Gdx.graphics.getDeltaTime();           // #15
        //currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);  // #16

        currentFrame = (TextureRegion) enemy.animatedNpc.getKeyFrame(stateTime, true);

        //System.out.println("X   "+sourceImage.getX()+"Y"+ sourceImage.getY());
        renderer.render();
        stage.act(delta);
        stage.draw();//



        vec = getStageLocation(testbutton.get(2));

        float scaleFactor = 0.5f;

        angle = 0;
        if (enemy.dir == Enemy.Dir.LEFT) {
            angle = 180;
            currentFrame.flip(false, true);
        } else if (enemy.dir == Enemy.Dir.RIGHT) {
            angle = 0;
        } else if (enemy.dir == Enemy.Dir.UP) {
            angle = 270;
        } else if (enemy.dir == Enemy.Dir.DOWN) {
            angle = 90;
        }

        npcSpriteBatch.begin();
        model.npc_route_running(ac, enemy, tiled_npc_fields);
        npcSpriteBatch.draw(currentFrame,
                            enemy.getX(),
                            enemy.getY(),
                            currentFrame.getRegionWidth() * scaleFactor,
                            currentFrame.getRegionHeight() * scaleFactor);

        enemy.draw(npcSpriteBatch, delta);


        npcSpriteBatch.end();

        currentFrame.flip(false,false);



        /*
        spriteBatch.begin();
        model.npc_route_running(ac, label, tiled_npc_fields);
        //npc_route_running(label, tiled_npc_fields);
        spriteBatch.draw(currentFrame, 0 , 0);

        spriteBatch.end();
        */

        spriteBatch.begin();
        arrowTower.draw(spriteBatch,Gdx.input.getX(), Gdx.input.getY());
        if(model.getMode() == model.DRAW_OPEN_FIELDS){
            model.drawEmptyFields(this,sr,spriteBatch, tiled_tower_fields);
        }
        spriteBatch.end();

        /**
         * Back to the first Screen
         */
        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            game.setScreen(new Menu_Screen(game));
        }

    }

    //Getter und Setter Methoden
    public Array<Rectangle> getTiled_npc_fields() {
        return tiled_npc_fields;
    }
    public void setTiled_npc_fields(Array<Rectangle> tiled_npc_fields) {
        this.tiled_npc_fields = tiled_npc_fields;
    }
    public Array<Rectangle> getTiled_tower_fields() {
        return tiled_tower_fields;
    }
    public void setTiled_tower_fields(Array<Rectangle> tiled_tower_fields) {
        this.tiled_tower_fields = tiled_tower_fields;
    }
    public MoveToAction getAc() {
        return ac;
    }
    public void setAc(MoveToAction ac) {
        this.ac = ac;
    }
    public OrthographicCamera getCamera() {
        return camera;
    }
    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }
    public Image getSourceImage() {
        return sourceImage;
    }
    public void setSourceImage(Image sourceImage) {
        this.sourceImage = sourceImage;
    }
    public Vector3 getTouchPoint() {
        return touchPoint;
    }
    public void setTouchPoint(Vector3 touchPoint) {
        this.touchPoint = touchPoint;
    }
    public void setTestbutton(Array<ImageButton> testbutton) {
        this.testbutton = testbutton;
    }
    public Array<ImageButton.ImageButtonStyle> getTestbuttonstyle() {
        return testbuttonstyle;
    }
    public void setTestbuttonstyle(Array<ImageButton.ImageButtonStyle> testbuttonstyle) {
        this.testbuttonstyle = testbuttonstyle;
    }

    @Override
    public void show() {

    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Skin getUiskin() {
        return uiskin;
    }
}
