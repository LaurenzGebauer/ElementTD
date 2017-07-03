package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;


/**
 * Created by Laurenz on 15.11.2015.
 */

public class Spiel_Screen extends Stage implements Screen {
    private Skin elements;

    //MVC
    private Model model;
    private Controller controller;
    private ArrowTower arrowTower;

   // private TowerSprite towersprite;
    private Sprite sprite;
    private Sprite banana;
    //Screen Elemente
    private KeyBack_Menu_Screen game;
    private Stage stage;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    //Animation
    private static final int FRAME_COLS = 13;
    private static final int FRAME_ROWS = 21;
    //Movement
    private MoveToAction ac;
    //Animation
    private Animation walkAnimation;
    private Array<Animation> walkAnimations;
    private Animation deadAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    private SpriteBatch spriteBatch;
    private SpriteBatch npcSpriteBatch;
    private TextureRegion currentFrame;
    private float stateTime;
    private Enemy enemy;
    private Array<Enemy> enemys = new Array<Enemy>();
    private Iterator<Enemy> enemyIterator;
    private int angle;
    //Shaperenderer
    private ShapeRenderer sr;
    //Gui- Elemente
    private Label label, gold, life,goldstand,lifestand;
    private int goldzahl;
    private int lifezahl;
    private Image sourceImage;
    //Actual User Touchpoints
    private Vector2 vec;
    private Vector3 touchPoint;
    //Collision Detection und Movement Controll
    private Array<Rectangle> tiled_tower_fields, tiled_npc_fields, towerrange;
    private TextureAtlas menu, towermenuicons;

    // Rounds
    private int round = 1;
    private int health = 1;
    private int goldReward = 1;
    private int enemyCounter = 0;

    private int enemyLoopCounter = 0;

    // Time
    double lastSpawn = TimeUtils.nanoTime();
    double spawnFreq = TimeUtils.millisToNanos(3000);

    // Towers
    Array<ArrowTower> arrowTowers = new Array<ArrowTower>();
    private TextureAtlas ta;
    // Tower fireRate
    private float fireDelay;

    public TextureAtlas getTowermenuicons() {
        return towermenuicons;
    }
    public void setTowermenuicons(TextureAtlas towermenuicons) {
        this.towermenuicons = towermenuicons;
    }

    //private TextureAtlas towermenuicons;
    private TextureRegion tablem;
    private Skin uiskin;
    private Array<ImageButton> testbutton;
    private ImageButton.ImageButtonStyle tb, tb1, tb2, tb3, tb4, tb5, tb6, tb7, tb8;
    private Table table;
    final Table table2 = new Table();
    private Viewport viewport;
    private int mapPixelHeight, mapPixelWidth;


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
        stage = new Stage(new StretchViewport(mapPixelWidth + 360, mapPixelHeight)); //+360 Wegen Tabellengröße Width7
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, stage.getWidth(), stage.getHeight());
        camera.update();
    //Gui Elements

        tiled_npc_fields = new Array<Rectangle>();
        tiled_npc_fields = model.getTiledObjects(tiledMap, tiled_npc_fields, "position");

        tiled_tower_fields = new Array<Rectangle>();
        tiled_tower_fields = model.getTiledObjects(tiledMap, tiled_tower_fields, "towers");

        towerrange = new Array<Rectangle>();

        touchPoint = new Vector3();

        skin.add("hero", new Texture("Hero.png"));

     //sourceImage.setPosition(-20,-20);

        //label = new Label("NPC", skin);
        gold = new Label("Gold  :", skin);
        life = new Label("Life  :", skin);
        goldzahl=100;
        lifezahl=5;
        goldstand = new Label(""+goldzahl, skin);
        lifestand = new Label(""+lifezahl, skin);

    //Tower_Menü
        table = new Table();
        table.defaults().height(stage.getHeight());
        table.setFillParent(true);
        table2.add(gold).pad(15);
        table2.add(life).pad(15);
        table2.row();
        table2.add(goldstand).pad(15);
        table2.add(lifestand).pad(15);
        table2.row();

        sr = new ShapeRenderer();

        menu = new TextureAtlas("menu_bg.txt");
        //Tower_Menü Hintergrundbild
        tablem = menu.findRegion("menu_bg");

        towermenuicons = new TextureAtlas("elements.txt");
        uiskin = new Skin(towermenuicons);
        testbuttonstyle = new Array<ImageButton.ImageButtonStyle>();

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
        tb3.up = uiskin.getDrawable("fire");
        testbuttonstyle.add(tb3);
        tb4.up = uiskin.getDrawable("water");
        testbuttonstyle.add(tb4);
        tb5.up = uiskin.getDrawable("darkness");
        testbuttonstyle.add(tb5);
        tb6.up = uiskin.getDrawable("light");
        testbuttonstyle.add(tb6);
        tb7.up = uiskin.getDrawable("nature");
        testbuttonstyle.add(tb7);
        tb8.up = uiskin.getDrawable("earth");
        testbuttonstyle.add(tb8);

        testbutton = new Array<ImageButton>();



         //Tower_Buttons werden der Tabelle hinzugfügt
          for(int i = 0; i< towermenuicons.getRegions().size; i++){
                ImageButton item1Button = new ImageButton(testbuttonstyle.get(i));
                testbutton.add(item1Button);
                testbutton.get(i).setName(""+i);
                testbutton.get(i).addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {

                         model.setMode(1);
                         model.setTowerNumberClicked= actor.getName();
                         model.changeTowerNumbertoName(model.setTowerNumberClicked);
                         model.setTowerplacementobserver(1);
                 }});



            table2.add(testbutton.get(i)).pad(30);
            if (i == 1) {
                table2.row();
            }
            if (i == 3) {
                table2.row();
            }
            if (i == 5) {
                table2.row();
            }
            if (i == 7) {
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
        /*walkSheet = new Texture(Gdx.files.internal("runningcat.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }*/
        walkSheet = new Texture(Gdx.files.internal("skeleton.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[9 * 1];
        walkAnimations = new Array<Animation>();
        for (int i = 8; i < 12; i++){
            for (int j = 0; j < 9; j++) {
                walkFrames[j] = tmp[i][j];
            }
            walkAnimation = new Animation(0.2f, walkFrames);
            walkAnimations.add(walkAnimation);
            walkFrames = new TextureRegion[9 * 1];
        }



        walkFrames = new TextureRegion[6 * 1];
        for (int i = 0; i < 6; i++) {
            walkFrames[i] = tmp[FRAME_ROWS-1][i];
        }
        deadAnimation = new Animation(0.2f, walkFrames);
        walkAnimations.add(deadAnimation);
        changeRound();
        enemy = Enemy.createEnemy(walkAnimations, health, goldReward);

        //walkAnimation = new Animation(0.2f, walkFrames);      // #11
        stateTime = 0f;                         // #13



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
        //stage.addActor(arrowTower);
    //Listeners und Stage platzierungen
        //stage.addActor(arrowTower);
        //stage.addActor(label);

        stage.addActor(enemy);
        stage.addActor(table);

        /*for (int i = 0; i < enemys.size; i++) {
            stage.addActor(enemys.get(i));
        }*/
    }

    public void changeRound() {
        switch (round) {
            case 1: // enemys = createEnemys(10, 5);
                health = 10;
                goldReward = 5;
                break;
            case 2: // enemys = createEnemys(15, 9);
                health = 15;
                goldReward = 9;
                break;
            case 3: // enemys = createEnemys(20, 14);
                health = 20;
                goldReward = 14;
                break;
            case 4: // enemys = createEnemys(25, 19);
                health = 20;
                goldReward = 19;
                break;
            case 5: // enemys = createEnemys(30, 24);
                health = 30;
                goldReward = 24;
                break;
            default: // enemys = createEnemys(1, 1);
                health = 1;
                goldReward = 1;
                break;
        }
        //enemys = createEnemys(health, goldReward);
        //enemyIterator = enemys.iterator();
    }

    public Array<Enemy> createEnemys(int health, int goldReward) {
        Array<Enemy> newEnemys = new Array<Enemy>();
        for (int i = 0; i < 10; i++) {
            Enemy singleEnemy = Enemy.createEnemy(this.walkAnimations, health, goldReward);
            singleEnemy.setPosition(tiled_npc_fields.get(0).x, tiled_npc_fields.get(0).y);
            singleEnemy.addAction(ac);
            newEnemys.add(singleEnemy);
        }
        return newEnemys;
    }

    public static Vector2 getStageLocation(Actor actor) {
        return actor.localToStageCoordinates(new Vector2(0, 0));
    }



    @Override
    public void render(float delta) {
        //Prepare Screen
        Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Vector3 mousePos = new Vector3(  Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);
        camera.update();

        //sr.setProjectionMatrix(camera.combined);

        renderer.setView(camera);

        //Animation Bewegung
        stateTime += Gdx.graphics.getDeltaTime();           // #15
        //currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);  // #16


        //System.out.println("X   "+sourceImage.getX()+"Y"+ sourceImage.getY());
        renderer.render();
        stage.act(delta);
        stage.draw();

        vec = getStageLocation(testbutton.get(2));

        float scaleFactor = 2f;

        npcSpriteBatch.begin();
        model.npc_route_running(ac, enemy, tiled_npc_fields);
        if(enemy.isAlive){
            enemy.draw(npcSpriteBatch, delta, stateTime);
        }
        else {
            while (!enemy.animatedNpc.isAnimationFinished(stateTime)){
                enemy.draw(npcSpriteBatch, delta, stateTime);
            }
        }

        /* does not work ... only one enemy is spawned and flashes while moving
        if(TimeUtils.nanoTime() - lastSpawn > spawnFreq && enemyIterator.hasNext()) {
            enemyIterator.next().setPosition(tiled_npc_fields.get(0).x, tiled_npc_fields.get(0).y);
            //enemyIterator.next().draw(npcSpriteBatch, delta, stateTime);
        }
        model.npc_route_running(ac, enemys.get(enemyLoopCounter), tiled_npc_fields);
        enemys.get(enemyLoopCounter).draw(npcSpriteBatch, delta, stateTime);

        enemyLoopCounter++;

        if (enemyLoopCounter >= enemys.size) {
            enemyLoopCounter = 0;
        }

        lastSpawn = TimeUtils.nanoTime();
        */

        /* does not work ... only one enemy is spawned and moves normally
        if((TimeUtils.nanoTime() - lastSpawn > spawnFreq) && enemyCounter <= 10) {
            //for (int i = 0; i < enemys.size; i++) {
            Enemy newEnemy = new Enemy(walkAnimations, health, goldReward);
            //stage.addActor(newEnemy);
            newEnemy.setPosition(tiled_npc_fields.get(0).x, tiled_npc_fields.get(0).y);
            newEnemy.addAction(ac);
            enemys.add(newEnemy);
            enemyCounter++;
            lastSpawn = TimeUtils.nanoTime();
        }

        if(enemys.size != 0){
            for (int i = 0; i < enemys.size; i++) {
                currentFrame = (TextureRegion) enemys.get(i).animatedNpc.getKeyFrame(stateTime, true);

                model.npc_route_running(ac, enemys.get(i), tiled_npc_fields);

                npcSpriteBatch.draw(currentFrame,
                        enemys.get(i).getX(),
                        enemys.get(i).getY(),
                        currentFrame.getRegionWidth() * scaleFactor,
                        currentFrame.getRegionHeight() * scaleFactor);
            }
        }*/
        npcSpriteBatch.end();

         /*
        spriteBatch.begin();
        model.npc_route_running(ac ,label, tiled_npc_fields);
        //npc_route_running(label, tiled_npc_fields);
        spriteBatch.draw(currentFrame, 0 , 0);

        spriteBatch.end();
        */

        spriteBatch.begin();



//        towersprite = new TowerSprite(sprite, (int) mousePos.x,(int) mousePos.y);
//        towersprite.getSprite().setTexture(texturearrow);
//        towersprite.getSprite().draw(spriteBatch);

         if(model.getMode() == model.DRAW_OPEN_FIELDS){
            model.drawEmptyFields(this,ta,spriteBatch, tiled_tower_fields);

        }
        spriteBatch.end();

        spriteBatch.begin();
        if(model.getTowerplacementobserver()== model.towerinvalid) {
            for (int i = 0; i < tiled_tower_fields.size; i++) {
                if (tiled_tower_fields.get(i).contains(mousePos.x, mousePos.y)) {

                    model.setMode(0);
                    if(model.towercost<=goldzahl){
                    arrowTowers.add(new ArrowTower(model.getTowerSprite(model.towerNameClicked),
                            tiled_tower_fields.get(i).getX(),
                            tiled_tower_fields.get(i).getY(),
                            model.towerRange(tiled_tower_fields.get(i).getX(),tiled_tower_fields.get(i).getY())));

                    //towerrange.add(model.towerRange(tiled_tower_fields.get(i).getX(),tiled_tower_fields.get(i).getY()));

                    tiled_tower_fields.removeIndex(i);

                    // model.drawSprite(model.towerNameClicked,tiled_tower_fields.get(i).getX(),tiled_tower_fields.get(i).getY());
                      //arrowTower.draw(spriteBatch,mousePos.x, mousePos.y);

                   // model.drawTower(tiled_tower_fields, i);
                    model.setTowerplacementobserver(0);
                    goldzahl=goldzahl-model.towercost;
                    goldstand.setText(""+goldzahl);
                }
                }
            }
         }

        for (int i = 0; i < arrowTowers.size; i++) {
            arrowTowers.get(i).getSprite().draw(spriteBatch);
        }

        spriteBatch.end();

        for(int i = 0; i < arrowTowers.size; i++){
//            sr.begin(ShapeRenderer.ShapeType.Filled);
//            sr.setColor(Color.GREEN);
//            sr.rect(towerrange.get(i).x,towerrange.get(i).y , towerrange.get(i).width,towerrange.get(i).height);
//
//            sr.end();

            // TODO: Enemy enemy wurde durch Array<Enemy> enemys ersetzt
            if(arrowTowers.get(i).getRange().contains(enemy.getX(),enemy.getY()) && enemy.isAlive){
                fireDelay -= delta;
                if (fireDelay <= 0) {
                    enemy.reduceHealthBy(arrowTowers.get(i).getDamage());
                    System.out.println("LaurenzBOSSlifE");
                    fireDelay += 1.0f;
                }
            }

        }


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
    public float getStateTime() {
        return stateTime;
    }
    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
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
