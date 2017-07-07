package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

import java.util.Iterator;


/**
 * Created by Laurenz on 15.11.2015.
 */

public class Spiel_Screen extends Stage implements Screen {

    private Skin elements;

    //MVC
    private Model model;
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
    private Array<MoveToAction> actionArray;
    //Animation
    private Animation walkAnimation;
    private Array<Animation> walkAnimations;
    private Animation deadAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    private SpriteBatch spriteBatch;
    private SpriteBatch npcSpriteBatch;
    private float stateTime;
    private Enemy enemy;
    private Array<Enemy> enemys = new Array<Enemy>();
    private Iterator<Enemy> enemyIterator;
    //Gui- Elemente
    private Label gold, life,goldstand,lifestand;
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
    private int enemycount = 5;
    private int health = 5;
    private int goldReward = 0;

    private static final float TIME_CONSTANT = 5.0f;

    // Towers
    Array<Tower> towers = new Array<Tower>();
    private TextureAtlas ta;
    // Enemy spawn delay
    private float spawnDelay;
    //private TextureAtlas towermenuicons;
    private TextureRegion tablem;
    private Skin uiskin;
    private Array<ImageButton> towermenubutton;
    private ImageButton.ImageButtonStyle tb, tb1, tb2, tb3, tb4, tb5, tb6, tb7, tb8;
    private Table table;
    final Table table2 = new Table();
    private Viewport viewport;
    private int mapPixelHeight, mapPixelWidth;
    private Array<ImageButton.ImageButtonStyle> towermenuButtonSkin;
    private  ParticleEffect effect;
    private  int showparticle;
    public Spiel_Screen(KeyBack_Menu_Screen g) {
        //MVC Objects
        this.game = g;
        model = new Model(this);

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

        //label = new Label("NPC", skin);
        gold = new Label("Gold  :", skin);
        life = new Label("Life  :", skin);
        goldzahl=30;
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



        menu = new TextureAtlas("menu_bg.txt");
        //Tower_Menü Hintergrundbild
        tablem = menu.findRegion("menu_bg");

        towermenuicons = new TextureAtlas("elements.txt");
        uiskin = new Skin(towermenuicons);
        towermenuButtonSkin = new Array<ImageButton.ImageButtonStyle>();

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
        towermenuButtonSkin.add(tb);
        tb2.up  = uiskin.getDrawable("cannon");
        towermenuButtonSkin.add(tb2);
        tb3.up = uiskin.getDrawable("fire");
        towermenuButtonSkin.add(tb3);
        tb4.up = uiskin.getDrawable("water");
        towermenuButtonSkin.add(tb4);
        tb5.up = uiskin.getDrawable("darkness");
        towermenuButtonSkin.add(tb5);
        tb6.up = uiskin.getDrawable("light");
        towermenuButtonSkin.add(tb6);
        tb7.up = uiskin.getDrawable("nature");
        towermenuButtonSkin.add(tb7);
        tb8.up = uiskin.getDrawable("earth");
        towermenuButtonSkin.add(tb8);

        towermenubutton = new Array<ImageButton>();

            //Tower_Buttons werden der Tabelle hinzugfügt
          for(int i = 0; i< towermenuicons.getRegions().size; i++){
                    ImageButton item1Button = new ImageButton(towermenuButtonSkin.get(i));
                    towermenubutton.add(item1Button);
                    towermenubutton.get(i).setName(""+i);
                    towermenubutton.get(i).addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {

                         model.setMode(1);
                         model.setTowerNumberClicked= actor.getName();
                         model.changeTowerNumbertoName(model.setTowerNumberClicked);
                         model.setTowerplacementobserver(1);
                 }});
                 //Tower Menu für die Tower Icons wird hergerichtet
                 table2.add(towermenubutton.get(i)).pad(30);
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

        walkSheet = new Texture(Gdx.files.internal("skeleton.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[9 * 1];
        walkAnimations = new Array<Animation>();
        for (int i = 8; i < 12; i++){
            for (int j = 0; j < 9; j++) {
                walkFrames[j] = tmp[i][j];
            }
            walkAnimation = new Animation(0.1f, walkFrames);
            walkAnimations.add(walkAnimation);
            walkFrames = new TextureRegion[9 * 1];
        }


        walkFrames = new TextureRegion[6 * 1];
        for (int i = 0; i < 6; i++) {
            walkFrames[i] = tmp[FRAME_ROWS-1][i];
        }
        deadAnimation = new Animation(0.15f, walkFrames);
        walkAnimations.add(deadAnimation);
        actionArray = new Array<MoveToAction>();
        changeRound();
        //enemy = Enemy.createEnemy(walkAnimations, health, goldReward);

        // stateTime = 0f;
         //NPC startPosition
        //enemy.setPosition(tiled_npc_fields.get(0).x, tiled_npc_fields.get(0).y);
        //MovetoAction wird aufgerufen und sagt wie sich das NPC bewegen soll
        /*ac = new MoveToAction();
        ac.setPosition(tiled_npc_fields.get(1).x, tiled_npc_fields.get(1).y);
        ac.setDuration(3);
        enemy.addAction(ac);*/
        //Partikel
        //TextureAtlas particleAtlas; //<-load some atlas with your particle assets in
        /*effect = new ParticleEffect();
        effect.load(Gdx.files.internal("particle/fire_particles"), Gdx.files.internal("particle"));
        effect.start();*/



        //Actors werden auf der Stage platziert
        // stage.addActor(enemy);
        stage.addActor(table);

    }

    public void changeRound() {
        enemys = new Array<Enemy>();
        actionArray = new Array<MoveToAction>();
        spawnDelay = 0.0f;
        switch (round) {
            /*case 1:
                health = 10;
                goldReward = 5;
                break;
            case 2:
                health = 15;
                goldReward = 9;
                break;
            case 3:
                health = 20;
                goldReward = 14;
                break;
            case 4:
                health = 25;
                goldReward = 19;
                break;
            case 5:
                health = 30;
                goldReward = 24;
                break;*/
            case 20: game.setScreen(new WonScreen(game));
                break;
            default:
                health += 5;
                goldReward += 3;
                //game.setScreen(new WonScreen(game));
                break;
        }
        enemys = createEnemys(health, goldReward);
        for (int i = 0; i < enemys.size; i++) {
            stage.addActor(enemys.get(i));
        }
        // enemyIterator = enemys.iterator();
    }

    public Array<Enemy> createEnemys(int health, int goldReward) {
        Array<Enemy> newEnemys = new Array<Enemy>();
        for (int i = 0; i < enemycount; i++) {
            Enemy singleEnemy = Enemy.createEnemy(this.walkAnimations, health, goldReward);
            singleEnemy.setPosition(tiled_npc_fields.get(0).x, tiled_npc_fields.get(0).y);
            newEnemys.add(singleEnemy);

            MoveToAction singleAc = new MoveToAction();
            singleAc.setPosition(tiled_npc_fields.get(1).x, tiled_npc_fields.get(1).y);
            singleAc.setDuration(model.calcDurationOfMovement(singleEnemy.getX(), singleEnemy.getY(), singleAc.getX(), singleAc.getY()));
            actionArray.add(singleAc);
        }
        return newEnemys;
    }

    @Override
    public void render(float delta) {
        //Prepare Screen
        Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Actual Position of the Touchpoints
        Vector3 actualTouchpos = new Vector3(  Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(actualTouchpos);
        camera.update();
        //sr.setProjectionMatrix(camera.combined);
        renderer.setView(camera);
        //Animation Bewegung
        for (int i = 0; i < enemys.size; i++) {
            enemys.get(i).stateTime += Gdx.graphics.getDeltaTime();           // #15
        }
        //currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);  // #16
        renderer.render();
        stage.act(delta);
        stage.draw();

        float scaleFactor = 2f;

        /* works ... for single enemy
        if (enemy.aliveHasChanged) {
            stateTime = 0f;
            goldzahl += enemy.getGoldReward();
            goldstand.setText("" + goldzahl);
            enemy.aliveHasChanged = false;
        } */

        spriteBatch.begin();

        //Updating and Drawing the particle effect
        //Delta being the time to progress the particle effect by, usually you pass in Gdx.graphics.getDeltaTime();
        for (int i = 0; i < towers.size; i++) {
            if(towers.get(i).showParticles){
                towers.get(i).particleEffect.start();
                towers.get(i).particleEffect.draw(spriteBatch, delta);
                towers.get(i).particleEffect.update(delta);
                // towers.get(i).showParticles = false;
            }
        }
        for (int i = 0; i < enemys.size; i++) {
            if (enemys.get(i).aliveHasChanged) {
                enemys.get(i).stateTime = 0f;
                goldzahl += enemys.get(i).getGoldReward();
                goldstand.setText("" + goldzahl);
                enemys.get(i).aliveHasChanged = false;
            }
            spawnDelay -= delta;
            if (spawnDelay <= 0 && !enemys.get(i).hasStarted) {
                enemys.get(i).addAction(actionArray.get(i));
                spawnDelay += (float)enemys.size - 1.0f;
                enemys.get(i).hasStarted = true;
            }

            if (enemys.get(i).getActions() != null && enemys.get(i).isAlive) {
                model.npc_route_running(actionArray.get(i), enemys.get(i), tiled_npc_fields);
            }

            if (enemys.get(i).getX() == tiled_npc_fields.get(tiled_npc_fields.size-1).getX() && enemys.get(i).getY() == tiled_npc_fields.get(tiled_npc_fields.size-1).getY() && enemys.get(i).isAlive) {
                enemys.get(i).isAlive = false;
                enemys.get(i).clear();
                lifezahl -= 1;
            }
            lifestand.setText("" + lifezahl);
            if (lifezahl <= 0) {
                game.setScreen(new GameOver(game));
            }

            if (!enemys.get(i).isAlive && enemys.get(i).animatedNpc.isAnimationFinished(enemys.get(i).stateTime)) {
                enemys.get(i).setVisible(false);
                enemys.get(i).remove();
            }

            if(enemys.get(i).isVisible()){
                enemys.get(i).draw(spriteBatch, delta);
            }
        }

        /* works ... for single enemy
        model.npc_route_running(ac, enemy, tiled_npc_fields);
        if(enemy.isVisible()){
            enemy.draw(spriteBatch, delta, stateTime);
        }

        if (!enemy.isAlive && enemy.animatedNpc.isAnimationFinished(stateTime)) {
            enemy.setVisible(false);
        }*/


         //Freie Flächen werden gezeichnet
         if(model.getMode() == model.DRAW_OPEN_FIELDS){
             if(model.getTowerCost()<=goldzahl) {
                 model.drawEmptyFields(this, ta, spriteBatch, tiled_tower_fields);
             }
         }

        //Draws selected Tower on Field
        if(model.getTowerplacementobserver()== model.towerinvalid) {
            for (int i = 0; i < tiled_tower_fields.size; i++) {
                if (tiled_tower_fields.get(i).contains(actualTouchpos.x, actualTouchpos.y)) {
                    //Freie Flächen werden nicht mehr angezeigt
                    model.setMode(0);
                    if(model.getTowerCost()<=goldzahl){
                        //Tower erstellen
                        Tower tower = Tower.createTower(model.getTowerTypeFromName(model.towerNameClicked),
                                model.getTowerSprite(model.towerNameClicked),
                                tiled_tower_fields.get(i).getX(),
                                tiled_tower_fields.get(i).getY(),
                                model.towerRange(tiled_tower_fields.get(i).getX(),
                                    tiled_tower_fields.get(i).getY(),
                                    tiled_tower_fields.get(i).getWidth() * 3,
                                    tiled_tower_fields.get(i).getHeight() * 3));

                        //Goldstand ändert sich
                        goldzahl=goldzahl-tower.getCost();
                        goldstand.setText(""+goldzahl);
                        //Tower zu Array hinzufügen
                        towers.add(tower);
                        //Fläche wo tower platziert wurde, wird ungültigt
                        tiled_tower_fields.removeIndex(i);
                        //Tower aus Menü muss nach den Platzieren erneut ausgewählt werden
                        model.setTowerplacementobserver(0);
                    }
                }
            }
        }

        for (int i = 0; i < towers.size; i++) {
            towers.get(i).getSprite().draw(spriteBatch);
        }
        spriteBatch.end();

        for(int i = 0; i < towers.size; i++){
            for (int j = 0; j < enemys.size; j++) {
                if(towers.get(i).getRange().contains(enemys.get(j).getX(), enemys.get(j).getY()) && enemys.get(j).isAlive){
                    towers.get(i).showParticles = true;
                    towers.get(i).fireDelay -= delta;
                    if (towers.get(i).fireDelay <= 0) {
                        System.out.println("Tower " + towers.get(i).type.toString() + " hit enemy with " + towers.get(i).getDamage() + " damage");
                        enemys.get(j).reduceHealthBy(towers.get(i).getDamage());
                        towers.get(i).fireDelay += towers.get(i).getNextShotDelay() * TIME_CONSTANT;
                        towers.get(i).particleEffect.reset();
                    }
                }
                else {
                    if (towers.get(i).fireDelay >= 0) {
                        towers.get(i).fireDelay -= delta;
                    }
                    else {
                        towers.get(i).fireDelay = 0f;
                    }
                    // towers.get(i).particleEffect.reset();
                    //if (towers.get(i).particleEffect.isComplete()){
                        towers.get(i).showParticles = false;
                    //}
                }
            }
        }

        if (model.checkIfAllEnemysDead(enemys)) {
            round += 1;
            enemycount += 1;
            changeRound();
        }


        /* works ... for single enemy
        for(int i = 0; i < towers.size; i++){
                //sr.begin(ShapeRenderer.ShapeType.Filled);
                //sr.setColor(Color.GREEN);
                //sr.rect(towerrange.get(i).x,towerrange.get(i).y , towerrange.get(i).width,towerrange.get(i).height);
                //sr.end();
            if(towers.get(i).getRange().contains(enemy.getX(),enemy.getY()) && enemy.isAlive){
                towers.get(i).spawnDelay -= delta;
                if (towers.get(i).spawnDelay <= 0) {
                    System.out.println("Tower " + towers.get(i).type.toString() + " hit enemy with " + towers.get(i).getDamage() + " damage");
                    enemy.reduceHealthBy(towers.get(i).getDamage());
                    towers.get(i).spawnDelay += towers.get(i).getNextShotDelay();
                    //Setting the position of the ParticleEffect
                    towers.get(i).particleEffect.setPosition(towers.get(i).getPositionx()+50, towers.get(i).getPositiony()+20);
                }
                towers.get(i).showParticles = true;
            }
            else {
                if (towers.get(i).spawnDelay >= 0) {
                    towers.get(i).spawnDelay -= delta;
                }
                else {
                    towers.get(i).spawnDelay = 0f;
                }
                towers.get(i).showParticles = false;
            }
        } */



        //Back to Menu Screen
        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
                game.setScreen(new Menu_Screen(game));
        }
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
    public void setTowermenubutton(Array<ImageButton> towermenubutton) {
        this.towermenubutton = towermenubutton;
    }
    public Array<ImageButton.ImageButtonStyle> getTowermenuButtonSkin() {
        return towermenuButtonSkin;
    }
    public void setTowermenuButtonSkin(Array<ImageButton.ImageButtonStyle> towermenuButtonSkin) {
        this.towermenuButtonSkin = towermenuButtonSkin;
    }
    public float getStateTime() {
        return stateTime;
    }
    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
    public Skin getUiskin() {
        return uiskin;
    }
    public Array<ImageButton> getTowermenubutton() {
        return towermenubutton;
    }
     public TextureAtlas getTowermenuicons() {
        return towermenuicons;
    }
    public void setTowermenuicons(TextureAtlas towermenuicons) {
        this.towermenuicons = towermenuicons;
    }
}
