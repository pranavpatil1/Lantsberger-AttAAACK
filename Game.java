import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.transform.Translate;
import javafx.scene.transform.Affine;

import java.util.Scanner;
import java.io.FileInputStream;

/**
 * class Game runs the different parts of the game
 * @author  Pranav Patil, Keerthana Sundar, Jane Wang, Aruna Sudarshan
 */
public class Game extends Application
 
{
    // details about window / canvas
    private final int WIDTH = 900;
    private final int HEIGHT = 600;
    private final String TITLE = "Lantsberger AttAAAACK";
    
    // an enum for each of the game states
    private static enum GameState
    {
        MENU, LEVEL_SELECT, WORLD_SELECT, GAME, ABOUT, QUESTION, WIN_SCREEN;
    }
    // stores the game state
    private GameState state = GameState.MENU;
    private QuestionList list;
    
    // camera coordinates (added to positions!)
    private double camX;
    private double camY;
    
    // world/level that has been selected in menu
    private int myWorld;
    private int myLevel;
    
    //starting position
    private int myXstart;
    private int myYstart;
    
    //Question viewed and answer chosen
    private Question displayed;
    private String chosenOne;

    //background image
    private Image background; 
    // if the game world has been initialized or needs to be
    // true at the first frame of the beginning of a level
    private boolean isInit;
    // if a question is being answered
    // if true the game stops! frozen in time :/
    private boolean isQuestion;
     
    //arsernal for weapon
    private boolean isSelected0 = true;
    private boolean isSelected1 = false;
    private boolean isSelected2 = false;
    
    // milliseconds since the last shot
    private int lastShot = 0;

    // all the sprites to be displayed
    private List<Sprite> mySprites;
    private List<Fighter> myFighters;
    private List<Enemy> myEnemies;
    private List<Block> myBlocks;
    private List<QBlock> myQBlocks;
    private List<Weapon> myWeapons;
    private Player myPlayer;
    
    private PhysicsBox myPhysics;
    
    // controls the enemies
    private EnemyAI enemyAI;
    
    // map for the level - all maps done by Aruna
    private String[][][] myMap = {
        {
            { 
                "              ?                 ",
                "            xxxx                ",
                "                 xx             ",
                "          x             !  xxxxx",
                "  0  !                 xxx      ",
                " xxx xx xxxx x     xxxx         "
            },
            {
                "                                                                  ",
                "                                   ?                              ",
                "                               !  xxx        x xx                 ",                      
                "                              xxx     x    x                      ",
                "                                 xx     xx      xxxx              ",
                "                          x         xxx             xxx           ",
                "          !  xx         !    xx   !      xxx                      ",
                "         xxx           xxx      xxx          x     !              ",
                "       x         ?  x                          xxxxx              ",
                " 0  xx    ?     xxx                  xx       xxx                 ",
                "xxx      xx        xx                               xxxx          ",
              
        
        
    



            },
            {  
                    "                                                                                                  ",
                    "                      ?                                                                           ",
                    "0    xx         !   xxxx                                                                          ",
                    "xxx        ?   xxx                                           !!                                   ",
                    "   x      xxx      xx                                        xxxxx               x                ",
                    "      !                 !               !    ?                    xx                              ",
                    "     xxx      xx       xxx              xx  xxx             xx       ?        xx   !              ",
                    "                  !       xxx     ?  xx             x     x          xxx  xxx    xxxx      xx     ",
                    "                xxxx             xxx          xxxx     xx               xx            x  x    xx  ",
                    "                              xx                    xxx                                xx         ",
                    "                                                                                                  "





            }
        }, 
        {
            {
               
                "              ?                 ",
                "            xxxx                ",
                "                 xx             ",
                "          x             !  xxxxx",
                "  0  !                 xxx      ",
                " xxx xx xxxx x     xxxx         "
            },
            {
                "                                  ?                               ",
                "                                 xxx                              ",
                "                               !                                  ",                      
                "                              xxx                                 ",
                "                                 xx     xx                        ",
                "                          x         xxx                           ",
                "          !  xx         !    xx   !      xxx                      ",
                "         xxx           xxx      xxx          x     !              ",
                "       x         ?  x             xx             xxxxx            ",
                " 0  xx    ?     xxx                  xx       xxx                 ",
                "xxx      xx        xx                               xxxx          ",
              




            },
            {
                    "                                                                                                  ",
                    "                      ?                                                                           ",
                    "0    xx         !   xxxx                                                                          ",
                    "xxx        ?   xxx                                           !!                                   ",
                    "   x      xxx      xx                                        xxxxx               x                ",
                    "      !                 !               !    ?                    xx                              ",
                    "     xxx      xx       xxx              xx  xxx             xx       ?        xx   !              ",
                    "                  !       xxx     ?  xx             x     x          xxx  xxx    xxxx      xx     ",
                    "                xxxx             xxx          xxxx     xx               xx            x  x    xx  ",
                    "                              xx                    xxx                                xx         ",
                    "                                                                                                  "

    

            }
        },
        {
            {
                
                "              ?                 ",
                "            xxxx                ",
                "                 xx             ",
                "          x             !  xxxxx",
                "  0  !                 xxx      ",
                " xxx xx xxxx x     xxxx         "
            },
            {
                "                                  ?                               ",
                "                                 xxx                              ",
                "                               !                                  ",                      
                "                              xxx                                 ",
                "                                 xx     xx                        ",
                "                          x         xxx                           ",
                "          !  xx         !    xx   !      xxx                      ",
                "         xxx           xxx      xxx          x     !              ",
                "       x         ?  x             xx             xxxxx            ",
                " 0  xx    ?     xxx                  xx       xxx                 ",
                "xxx      xx        xx                               xxxx          ",
              


            },
            {
                    "                                                                                                  ",
                    "                      ?                                                                           ",
                    "0    xx         !   xxxx                                                                          ",
                    "xxx        ?   xxx                                           !!                                   ",
                    "   x      xxx      xx                                        xxxxx               x                ",
                    "      !                 !               !    ?                    xx                              ",
                    "     xxx      xx       xxx              xx  xxx             xx       ?        xx   !              ",
                    "                  !       xxx     ?  xx             x     x          xxx  xxx    xxxx      xx     ",
                    "                xxxx             xxx          xxxx     xx               xx            x  x    xx  ",
                    "                              xx                    xxx                                xx         ",
                    "                                                                                                  "




            }
        }
    };
    
    /**
     * constructor Game loads images and sprites to game
     * initializes all of the instance ArrayLists
     */
    public Game()
    {
        mySprites = new ArrayList<Sprite>();
        myFighters = new ArrayList<Fighter>();
        myEnemies = new ArrayList<Enemy>();
        myBlocks = new ArrayList<Block>();
        myQBlocks = new ArrayList<QBlock>();
        myWeapons = new ArrayList<Weapon>();
        myPhysics = new PhysicsBox(myFighters, myBlocks);
        enemyAI = new EnemyAI(myEnemies, myBlocks, myPlayer);
        isInit = true;
        list = new QuestionList();
        chosenOne = null;
    }    
    /**
     * provides the starter codes for the game
     * initializes Graphics Context, Scene; 
     * calls methods that set up the screen
     * Pranav
     */
    @Override
    public void start(Stage stage) 
    {
        // sets window title
        stage.setTitle(TITLE);
        // cannot resize (stuck at game size)
        stage.setResizable(false);
        
        // group to hold everything
        Group root = new Group();
        // scene to hold canvas
        Scene scene = new Scene(root);
        // add scene to stage
        stage.setScene(scene);
        
        // creates canvas and adds it to the root
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        // creates graphic context to work with (on canvas)
        GraphicsContext gc = canvas.getGraphicsContext2D();

        ArrayList<String> input = new ArrayList<String>();
        //read Questions
        readQs(list);
        
        // Sound.loop("background.wav", 1);

        // creates an input system such that any keys that are currently held are in the input array
        // https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
        handleKeyInput(input, scene); 
        handleMouseInput(scene, stage);
        // goes through the game loop (with all necessary data)
        gameLoop (gc, input);

        stage.show();
    }
    /**
     * method handleKeyInput adds any keyboard input on the scene to an 
     * List<String> but only when the key is held
     * @param   input   list to store key input on keyPressed
     * @param   scene   scene to listen to
     * Pranav
     */
    private void handleKeyInput(List<String> input, Scene scene)
    {
        // creates an input system such that any keys that are currently held are in the input array
        // code from:
        // https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
        scene.setOnKeyPressed(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    
                    // remap WASD to arrow keys
                    if (code.equals("W"))
                        code = "UP";
                    if (code.equals("A"))
                        code = "LEFT";
                    if (code.equals("S"))
                        code = "DOWN";
                    if (code.equals("D"))
                        code = "RIGHT";

                    // add string version of keycode on mouse down
                    if ( !input.contains(code) )
                        input.add( code );
                }
            });
        scene.setOnKeyReleased(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    // removes instance of key from list on release
                    String code = e.getCode().toString();

                    // remap WASD to arrow keys
                    if (code.equals("W"))
                        code = "UP";
                    if (code.equals("A"))
                        code = "LEFT";
                    if (code.equals("S"))
                        code = "DOWN";
                    if (code.equals("D"))
                        code = "RIGHT";
                    
                    input.remove( code );
                }
            });
            
    }
    /**
     * method handleMouseInput handles mouse input on different pages (does work)
     * @param   scene   scene to listen to
     * @param   stage   stage to potentially modify
     * Pranav, Jane, Keerthana
     */
    private void handleMouseInput(Scene scene, Stage stage)
    {
        scene.setOnMouseClicked(
        new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent e)
            {
                // values stores only on click
                double x = e.getX();
                double y = e.getY();
                
                // splits up based on which page
                switch (state)
                {
                    case MENU:
                        // if it hits any of the buttons
                        if (x >= 150 && x <= 750)
                        {
                            if (y >= 330 && y <= 380) // play
                                state = GameState.WORLD_SELECT;
                            else
                                if (y >= 405 && y <= 455) // about
                                    state = GameState.ABOUT;
                                else
                                    if (y >= 480 && y <= 530) // quit
                                        stage.close();
                        }
                        break;
                    case WORLD_SELECT: //gc.fillRect(45, 45, 227.5, 510); <- TIM
                        if (y >= 45 && y <= 555)
                        {
                            if (x >= 45 && x <= 272.5) //henry
                            {
                                myWorld = 0;
                                state = GameState.LEVEL_SELECT;                                
                            }
                                
                            else // gc.fillRect(327.5, 45, 245, 510); <- MR. L
                                if (x >= 327.5 && x <= 572.5) //tim
                                {
                                    myWorld = 1;
                                    state = GameState.LEVEL_SELECT;                                    
                                }
                                else 
                                    if (x >= 627.5 && x <= 855) //lants
                                    {
                                        myWorld = 2;
                                        state = GameState.LEVEL_SELECT;                                        
                                    }
                        }
                        else
                        {
                            if (y >= 10 && y <= 35) //840
                            {
                                if (x >= 780 && x <= 830)
                                    state = GameState.MENU;
                                else
                                    if (x >= 840 && x <= 890)
                                        Platform.exit();
                            }
                        }
                        break;
                    case LEVEL_SELECT: //765,10,65,25
                        if (y >= 10 && y <= 35)
                            if (x >= 840 && x <= 890)
                                state = GameState.MENU; 
                            else
                                if (x >= 765 && x <= 830)
                                    state = GameState.WORLD_SELECT;
                        if (myWorld == 0)
                        {
                            resetAll();
                            if (Math.sqrt(Math.pow(800 - x,2) + Math.pow(500 - y,2)) < 75)
                            {    
                                myLevel = 2; 
                                state = GameState.GAME; //100,125
                            }
                            else //400,250,150,150
                                if (Math.sqrt(Math.pow(475 - x,2) + Math.pow(325 - y,2)) < 75)
                                {
                                    myLevel = 1; 
                                    state = GameState.GAME;
                                }
                                else
                                    if (Math.sqrt(Math.pow(175 - x,2) + Math.pow(200 - y,2)) < 75)
                                    {
                                        myLevel = 0; 
                                        state = GameState.GAME;
                                    }
                        }
                        else
                            if (myWorld == 1)
                            { 
                                resetAll();
                                if (Math.sqrt(Math.pow(795 - x,2) + Math.pow(492.5 - y,2)) < 50)
                                {    
                                    myLevel = 2; 
                                    state = GameState.GAME;
                                }
                                else
                                    if (Math.sqrt(Math.pow(487.5 - x,2) + Math.pow(337.5 - y,2)) < 87.5)
                                    {
                                        myLevel = 1; 
                                        state = GameState.GAME;
                                    }
                                    else
                                        if (Math.sqrt(Math.pow(150 - x,2) + Math.pow(150 - y,2)) < 45)
                                        {
                                            myLevel = 0; 
                                            state = GameState.GAME; 
                                        }
                            }
                            else
                                if (myWorld == 2) //400, 250/ 750, 400
                                {
                                    resetAll();
                                    if (Math.sqrt(Math.pow(775 - x,2) + Math.pow(425 - y,2)) < 25)
                                    {    
                                        myLevel = 2; 
                                        state = GameState.GAME;
                                    }
                                    else
                                        if (Math.sqrt(Math.pow(425 - x,2) + Math.pow(275 - y,2)) < 25)
                                        {
                                            myLevel = 1; 
                                            state = GameState.GAME;
                                        }
                                        else
                                            if (Math.sqrt(Math.pow(125 - x,2) + Math.pow(325 - y,2)) < 25)
                                            {
                                                myLevel = 0; 
                                                state = GameState.GAME;
                                            }
                                }

                    case GAME:
                        boolean test;
                        //test to see if thereis a block
                        for(int i = 0; i < myQBlocks.size(); i++)
                        {
                            QBlock q = myQBlocks.get(i);
                            if(x >= -camX + WIDTH/2 + q.getX() && x <= -camX + WIDTH/2 + q.getX() + q.getWidth()
                                && y >= -camY + HEIGHT/2 + q.getY() && y <= -camY + HEIGHT/2 + q.getY() + q.getHeight())
                            {
                               // no more opportunity to get ammo
                               myQBlocks.remove(q);
                               mySprites.remove(q);
                               myBlocks.remove(q); 
                               state = GameState.QUESTION;
                            }
                        }
                        // when clicking
                        if(y <= 90 && y >= 40  && x >= 25 && x <= 245)
                        {
                            if(y >= 40 && y <= 90 && x >= 25 && x <= 95)
                            {
                                test = myPlayer.setWeapon(0);
                                if(test)
                                {
                                    isSelected0 = true;
                                    isSelected1 = isSelected2 = false;
                                }
                            }
                            else
                            {
                                if(y >= 40 && y <= 90 && x > 100 && x <= 170)
                                {
                                    test = myPlayer.setWeapon(1);
                                    if(test)
                                    {
                                        isSelected1 = true;
                                        isSelected0 = isSelected2 = false;
                                    }
                                }
                                else
                                {
                                    if(y >= 40 && y <= 90 && x > 175 && x <= 245)
                                    {
                                        test = myPlayer.setWeapon(2); 
                                        if(test)
                                        {
                                            isSelected2 = true;
                                            isSelected0 = isSelected1 = false;
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            if (y >= 10 && y <= 35)
                            {
                                if (x >= 840 && x <= 890)
                                    state = GameState.MENU;
                                else
                                    if (x >= 765 && x <= 830)
                                        state = GameState.WORLD_SELECT;
                             }
                            Weapon s = null;
                            if (lastShot > 1000)
                            {
                                s = myPlayer.shoot((int)(x + camX - WIDTH/2), (int)(y + camY - HEIGHT/2));
                                lastShot = 0;
                                // Sound.play("pew_pew_pew.mp3", 0.3);
                            }
                            if (s != null)
                            {
                                mySprites.add(s);
                                myWeapons.add(s);
                            }
                         }                  
                        break;
                   case WIN_SCREEN:
                    if(y >= 400 && y <= 470)
                    {
                        if(myLevel < 2)
                        {
                            if(x >= 255 && x <= 355)
                                state = GameState.MENU;
                            if(x >= 530 && x <= 630)
                            {
                                myLevel++;
                                state = GameState.GAME;
                                resetAll();
                            }
                        }
                        else
                        {
                            if(x >= 255 && x <= 355)
                                state = GameState.MENU;
                            if(x >= 530 && x <= 630)
                                state = GameState.WORLD_SELECT;
                        }
                    }
                    break;
                    case QUESTION:
                        if (chosenOne == null)
                        {
                            if (x >= 150 && x <= 750)
                            {
                                //showResult is replacing showQuestion
                                if (y >= 310 && y <= 365)
                                    chosenOne = displayed.getAnswer(1);
                                else
                                    if (y >= 370 && y <= 425)
                                        chosenOne = displayed.getAnswer(2);
                                    else
                                        if (y >= 430 && y <= 485)
                                            chosenOne = displayed.getAnswer(3);
                                        else
                                            if (y >= 490 && y <= 545)
                                                chosenOne = displayed.getAnswer(4);
                            }
                            if (chosenOne != null && displayed.correct(chosenOne))
                                myPlayer.addAmmo(1 + (int)(Math.random() * 2));
                        }
                        else
                        {
                            if (x >= 375 && x <= 525 && y >= 400 && y <= 450)
                            {
                                displayed = null;
                                chosenOne = null;
                                state = GameState.GAME;
                            }
                        }
                        break;
                    case ABOUT:
                        if (y >= 10 && y <= 35) //840
                        {
                            if (x >= 780 && x <= 830)
                                state = GameState.MENU;
                            else
                                if (x >= 720 && x <= 770)
                                    state = GameState.WORLD_SELECT;
                                else
                                    if (x >= 840 && x <= 890)
                                            Platform.exit();
                        }
                        break;
                }
            }
        });
    }
    /**
     * method gameLoop runs the main loop of the game given the graphics context (to draw on) and input list (to interact with)
     * @param gc - the Graphics Context for displaying stuff
     * @param input - in regards to the keys pressed and mouse clicked location
     * Pranav
     */
    private void gameLoop(GraphicsContext gc, ArrayList<String> input)
    {
        // start game loop, check time
        final long startNanoTime = System.nanoTime();
        // creates an anonymous class to run the game loop
        new AnimationTimer()
        {
            // hold the last frame
            private long last = startNanoTime;
            // hold time since last frame
            private int delta;
            public void handle(long currentNanoTime)
            {
                delta = (int)(System.nanoTime() - last);
                last = System.nanoTime();
                
                // based on state of game, display a different thing
                switch (state)
                {
                    case MENU:
                        showMenu(gc);
                        break;
                    case ABOUT:
                        showAbout(gc);
                        break;
                    case WORLD_SELECT:
                        showWorlds(gc);
                        break;
                    case LEVEL_SELECT:
                        showLevels(gc);
                        break;
                    case GAME:
                        showGame(gc, delta/1000000, input);
                        break;
                   case QUESTION:
                        showQuestion(gc, list);                       
                        break;
                   case WIN_SCREEN:
                        showWinScreen(gc);

                }

            }
        }.start();
    }
    /**
     *  Code for displaying the menu onto a graphics context
     *  @param gc   thing to draw on
     *  Keerthana
     */
    private void showMenu(GraphicsContext gc)
    {
        gc.setTextAlign(TextAlignment.CENTER); //self explanatory
        
        gc.fillRect(0,0,WIDTH,HEIGHT); //automatically fills the background as black
        
        gc.setFont(Font.font("Futura", FontWeight.BOLD, 100)); //TITLE
        gc.setFill(Color.PAPAYAWHIP); 
        gc.fillText("LANTSBERGER", 450, 150);
        gc.fillText("ATTAAAACK", 450, 265);
                
        gc.setFill(Color.MAROON);
        gc.fillRect(150, 330, 600, 50); //creates a maroon rectangle
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Futura",40));
        gc.fillText("PLAY", 450, 370, 600); //PLAY button
        
        gc.setFill(Color.FIREBRICK);
        gc.fillRect(150, 405, 600, 50); //creates a firebrick rectangle
        gc.setFill(Color.BLACK);
        gc.fillText("ABOUT", 450, 445, 600); //ABOUT button
        
        gc.setFill(Color.RED);
        gc.fillRect(150, 480, 600, 50); //creates a red rectangle
        gc.setFill(Color.BLACK);
        gc.fillText("QUIT", 450, 520, 600); //QUIT button 
    }
    /**
     * code for displaying the About screen onto a graphics context
     * credits, how to play, authors
     * @param gc    thing to draw on
     * Keerthana
     */
    private void showAbout(GraphicsContext gc)
    {
       gc.setTextAlign(TextAlignment.CENTER); //self explanatory
       gc.setFont(new Font("Futura",15));
              
       gc.setFill(Color.BLACK);
       gc.fillRect(0,0,900,600); //automatically fills the background as black
              
       gc.setFill(Color.RED); //quit button
       gc.fillRect(840,10,50,25);
       gc.setFill(Color.BLACK);
       gc.fillText("QUIT",865,30);  
       
       gc.setFill(Color.CRIMSON); //return to menu
       gc.fillRect(780,10,50,25);
       gc.setFill(Color.BLACK);
       gc.fillText("MENU",805,30);
       
       gc.setFill(Color.TOMATO); //play the game
       gc.fillRect(720,10,50,25);
       gc.setFill(Color.BLACK);
       gc.fillText("PLAY",745,30);
       
       gc.setFont(Font.font("Futura", FontWeight.BOLD,100));  //title - about
       gc.setFill(Color.PAPAYAWHIP); 
       gc.fillText("ABOUT", 450, 100);
       
       gc.setFill(Color.FIREBRICK); //rectangle where text will be in
       gc.fillRect(50, 125, 800, 425);
       
       gc.setFill(Color.BLACK); //introduction
       gc.setFont(Font.font("Futura", FontWeight.BOLD,35));
       gc.fillText("Welcome to Lantsberger AttAAAACK!", 450, 170);
       
       gc.setFont(new Font("Futura",20)); //why the game was made
       gc.fillText("This game was created for the benefit of Mr. Lantsberger, our unbelievably", 450, 210);
       gc.fillText("awesome AP Computer Science teacher, and his future students.", 450, 235);
       gc.fillText("That, or it was a project that was worth 30% of our grade.", 450, 260);
       gc.fillText("You can look at it either way.", 450, 285);
       
       //the whole point of the game - what it contains
       gc.fillText("This game is meant to improve students' knowledge of Java, in", 450, 320);
       gc.fillText("addition to basic computer/gaming skills.", 450, 345); 
       
       //ways to operate it
       gc.fillText("Press the arrow keys to move your character and make him jump.", 450, 380);
       gc.fillText("To shoot an enemy, hold your mouse over the figure and click.", 450, 405);
       gc.fillText("To complete a level, you must defeat all of the enemies in the area.", 450, 430);
       
       gc.setFont(Font.font("Futura", FontWeight.BOLD, 25));
       gc.fillText("Good luck!", 450, 470); //meh
       
       gc.setFont(new Font("Futura",20)); //creators
       gc.fillText("Creators: Pranav Patil, Keerthana Sundar, Jane Wang, Aruna Sudarshan", 450, 500);
       gc.setFont(new Font("Futura",15));
       gc.fillText("Graphics / Contributors: Isabella Wilson, Darshan Jeganathan and Skandan Sundar", 450, 523);
       
       gc.setFont(new Font("Futura",10));
       gc.fillText("Disclaimer: None of the characters are actually ours, except for Mr.L.", 450, 540);
    } 
    /**
     * code for display world select on a graphics context
     * mr L, henry, tim
     * @param gc    thing to draw on
     * Keerthana
     */
    private void showWorlds(GraphicsContext gc)
    {        
       gc.setTextAlign(TextAlignment.CENTER); //self explanatory
       gc.setFont(new Font("Futura",15));
       
       gc.setFill(Color.BLACK);
       gc.fillRect(0,0,900,600); //automatically fills the background as black
       
       gc.setFill(Color.RED); //quit button
       gc.fillRect(840,10,50,25);
       gc.setFill(Color.BLACK);
       gc.fillText("QUIT",865,30);  
       
       gc.setFill(Color.CRIMSON); //return to menu
       gc.fillRect(780,10,50,25);
       gc.setFill(Color.BLACK);
       gc.fillText("MENU",805,30);
       
       gc.setFont(Font.font("Futura", FontWeight.BOLD, 20)); 
       gc.setFill(Color.MEDIUMTURQUOISE); //TIM THE ENCHANTER'S WORLD
       gc.fillRect(327.5, 45, 245, 510); //45,45,227.5,510
       gc.setFill(Color.MIDNIGHTBLUE);
       gc.fillText("THE MAGICAL REALM", 450, 270); //158.5 
       gc.fillText("OF TIM THE", 450, 300);
       gc.fillText("ENCHANTER", 450, 330);
       
       gc.setFill(Color.KHAKI); //MR. L's WORLD
       gc.fillRect(627.5, 45, 227.5, 510); //327.5, 45, 245, 510
       gc.setFill(Color.DARKSLATEGREY); 
       gc.fillText("THE WONDERFUL",741.25 , 270);
       gc.fillText("CLASSROOM OF", 741.25, 300);
       gc.fillText("MR. LANTSBERGER", 741.25, 330);
       
       gc.setFill(Color.THISTLE); //STRESSED HENRY's WORLD
       gc.fillRect(45, 45, 227.5, 510); //627.5, 45, 227.5, 510
       gc.setFill(Color.BLACK);
       gc.fillText("THE ROCK N' ROLL", 158.75, 270); //741.25, 270
       gc.fillText("CITY OF STRESSED", 158.75, 300); //741.25, 300
       gc.fillText("HENRY", 158.75, 330); //330

    }
    /**
     * shows the levels to choose from for each world: mr. L, tim, henry
     * @param gc    thing to draw on
     * Keerthana
     */
    private void showLevels(GraphicsContext gc)
    {
       gc.setTextAlign(TextAlignment.CENTER); //self explanatory
       
       if (myWorld == 2) //Mr. L
       {
           gc.setFont(new Font("Futura",15)); 
           
           gc.setFill(Color.KHAKI);
           gc.fillRect(0,0,900,600); //automatically fills the background as khaki
           
           gc.setFill(Color.DIMGREY); //quit button
           gc.fillRect(840,10,50,25);
           gc.setFill(Color.WHITE);
           gc.fillText("MENU",865,30);  
               
           gc.setFill(Color.DARKGOLDENROD); //return to menu
           gc.fillRect(765,10,65,25);
           gc.setFill(Color.WHITE);
           gc.fillText("WORLDS",797.5,30);  
           
           gc.setFill(Color.DIMGREY); //mr. L
           gc.setFont(Font.font("Futura", FontWeight.BOLD, 50));
           gc.fillText("THE WONDERFUL CLASSROOM",475, 110);
           
           gc.setStroke(Color.GOLDENROD);
           gc.setFont(Font.font("Futura", FontWeight.BOLD, 25));
           
           gc.setFill(Color.SADDLEBROWN);
           gc.fillRect(75,275,100,200);
           gc.setFill(Color.WHITE);  //lvl 1         
           gc.strokeOval(100,300,50,50);
           gc.fillOval(100,300,50,50);
           gc.setFill(Color.BLACK);
           gc.fillText("1",125,334);
          
           gc.setFill(Color.SADDLEBROWN);
           gc.fillRect(375,225,100,200);
           gc.setFill(Color.WHITE); //lvl 2
           gc.strokeOval(400,250,50,50);
           gc.fillOval(400,250,50,50);
           gc.setFill(Color.BLACK);
           gc.fillText("2",425,284);
           
           gc.setFill(Color.SADDLEBROWN);
           gc.fillRect(725,375,100,200);
           gc.setFill(Color.WHITE); //lvl 3
           gc.strokeOval(750,400,50,50);
           gc.fillOval(750,400,50,50);
           gc.setFill(Color.BLACK);
           gc.fillText("3",775,434);
       }
       else
           if (myWorld == 1) //tim
           {
               gc.setFont(new Font("Futura",15));  
               gc.setStroke(Color.BLACK);
               
               gc.setFill(Color.MEDIUMTURQUOISE);
               gc.fillRect(0,0,900,600); //automatically fills the background as turquoise
               
               gc.setFill(Color.NAVY); //quit button
               gc.fillRect(840,10,50,25);
               gc.setFill(Color.WHITE);
               gc.fillText("MENU",865,30);  
               
               gc.setFill(Color.SEAGREEN); //return to menu
               gc.fillRect(765,10,65,25);
               gc.setFill(Color.WHITE);
               gc.fillText("WORLDS",797.5,30);       
                                
               gc.setFill(Color.MIDNIGHTBLUE);
               gc.setFont(Font.font("Futura", FontWeight.BOLD, 50));
               gc.fillText("THE MAGICAL REALM",575, 110); //title
               
               gc.setFont(Font.font("Futura", FontWeight.BOLD, 25));  
               
               gc.setFill(Color.LAWNGREEN);  //lvl 1         
               gc.strokeOval(100,100,100,100);
               gc.fillOval(100,100,100,100);
               gc.setFill(Color.MIDNIGHTBLUE);
               gc.fillText("1",150,160);
               
               gc.setFill(Color.LAWNGREEN); //lvl 2
               gc.strokeOval(400,250,175,175);
               gc.fillOval(400,250,175,175);
               gc.setFill(Color.MIDNIGHTBLUE);
               gc.fillText("2",487.5,345);
               
               gc.setFill(Color.LAWNGREEN); //lvl 3
               gc.strokeOval(750,450,90,90);
               gc.fillOval(750,450,90,90);
               gc.setFill(Color.MIDNIGHTBLUE);
               gc.fillText("3",795,500);
           }
           else
               if (myWorld == 0) //henry
               {
                   gc.setFont(new Font("Futura",15));      
               
                   gc.setFill(Color.THISTLE);
                   gc.fillRect(0,0,900,600); //automatically fills the background as turquoise
                   
                   gc.setFill(Color.DARKMAGENTA); //quit button
                   gc.fillRect(840,10,50,25);
                   gc.setFill(Color.WHITE);
                   gc.fillText("MENU",865,30);  
                   
                   gc.setFill(Color.MEDIUMORCHID); //return to menu
                   gc.fillRect(765,10,65,25);
                   gc.setFill(Color.WHITE);
                   gc.fillText("WORLDS",797.5,30);       
                                    
                   gc.setFill(Color.BLACK);
                   gc.setFont(Font.font("Futura", FontWeight.BOLD, 50));
                   gc.fillText("THE ROCK N' ROLL CITY",575, 110); //title
                   
                   gc.setFont(Font.font("Futura", FontWeight.BOLD, 25));  
                   gc.setLineWidth(2);
                                      
                   gc.setFill(Color.BLACK);
                   gc.fillOval(100,125,150,150);
                   gc.setStroke(Color.FUCHSIA);  //lvl 1         
                   gc.strokeOval(100,125,150,150);
                   gc.setFill(Color.FUCHSIA);
                   gc.fillText("1",175,210);
                   
                   gc.setFill(Color.BLACK);  //lvl 2
                   gc.fillOval(400,250,150,150);
                   gc.strokeOval(400,250,150,150); 
                   gc.setFill(Color.FUCHSIA);
                   gc.fillText("2",475,335);
                                   
                   gc.setFill(Color.BLACK); //lvl 3
                   gc.fillOval(725,425,150,150);                   
                   gc.strokeOval(725,425,150,150);
                   gc.setFill(Color.FUCHSIA);
                   gc.fillText("3",800,510);
               }
    }
    /**
     * code for display world select on a graphics context
     * @param gc
     * @param millis time that has passed
     * @param input from keyboard and clicking
     * Jane, Keerthana, Pranav
     */
    private void showGame(GraphicsContext gc, int millis, List<String> input)
    {
        int ammo; 
        // if the game needs to be initialized...
        if (isInit)
        {
            if(myWorld == 0)
            {
                background = new Image("city.png");
                myPlayer = new StressedHenry(100, 100);
            }
            if(myWorld == 1)
            {
                background = new Image("mountains.png");
                myPlayer = new EnchanterTim(100, 100);
            }
            if(myWorld == 2)
            {
                background = new Image("classroom.png");
                myPlayer = new MrL(100, 100);
            }
            enemyAI.setTarget(myPlayer);
            myFighters.add(myPlayer);
            loadMap(myMap[myWorld][myLevel]);
            isInit = false;
            lastShot = 0;
        }
        lastShot += millis;
        
        //testing if enemy is dead
        for(int i = 0; i < myEnemies.size(); i++)
        {
            Enemy e = myEnemies.get(i); 
            if(e.getHealth() <= 0 || e.getY() > 100)
            {
                mySprites.remove(e);
                myFighters.remove(e);
                myEnemies.remove(e);
                i--; 
            }
        }
        //killed all enemies
        if(!isInit && myEnemies.size() == 0)
            state = GameState.WIN_SCREEN;
        
        for (int i = 0; i < myWeapons.size(); i ++)
        {
            Weapon w = myWeapons.get(i);
            if (w.getX() < camX - WIDTH || w.getX() > camX + WIDTH ||
                    w.getY() < camY - HEIGHT || w.getY() > camY + HEIGHT)
            {
                mySprites.remove(w);
                myWeapons.remove(w);
                i --;
            }
            for (int j = 0; j < myEnemies.size(); j ++)
            {
                Enemy e = myEnemies.get(j);
                if (e.isCollision(w))
                {
                    e.setHealth(e.getHealth() - w.getDestruction());
                    j = myEnemies.size();
                    mySprites.remove(w);
                    myWeapons.remove(w);
                    i --;
                }
            }
        }
        //Moving the screen (centered around x and y of player)
        camX += (myPlayer.getX() - camX)/10;
        camY += (myPlayer.getY() - camY)/10;
        gc.setTransform(new Affine(new Translate(-camX + WIDTH/2, -camY + HEIGHT/2)));

        // display background
        int x = (int)(camX - WIDTH/2);
        int y = (int)(camY - HEIGHT/2);
        
        if (myWorld == 0)
        {
            gc.setFill(Color.rgb(26, 51, 73));
            gc.fillRect(camX - WIDTH/2, camY - HEIGHT/2, WIDTH, HEIGHT);
            for (int i = x - (x % 1667) - 1667; i < x + WIDTH; i += 1667)
                gc.drawImage(background, i, -1017 + 120);
        }
        if (myWorld == 1)
        {
            gc.setFill(Color.rgb(193, 224, 255));
            gc.fillRect(camX - WIDTH/2, camY - HEIGHT/2, WIDTH, HEIGHT);
            for (int i = x - (x % 602) - 602; i < x + WIDTH; i += 602)
                gc.drawImage(background, i, -1017 + 300);
        }
        if (myWorld == 2)
        {
            gc.setFill(Color.rgb(236, 236, 236));
            gc.fillRect(camX - WIDTH/2, camY - HEIGHT/2, WIDTH, HEIGHT);
            for (int i = x - (x % 283) - 283; i < x + WIDTH; i += 283)
                gc.drawImage(background, i, -1017);
        }
        // create Arsenal
        loadArsenal(gc); 
        
        // left arrow down move left
        if (input.contains("LEFT"))
            myPlayer.moveLeft();
        // right arrow down move right
        if (input.contains("RIGHT"))
            myPlayer.moveRight();
        // up arrow then jump
        if (input.contains("UP") && myPlayer.getYvel() == 0)
            myPlayer.jump();
        myPhysics.update(millis);
        // display and update all the sprites
        enemyAI.update(millis);
        
        for (Sprite s : mySprites)
        {
            s.update(millis);
            s.draw(gc, millis);
        }
        myPlayer.update(millis);
        if (myPlayer.getY() > 100)
        {
            myPlayer.reset(myXstart, myYstart);
            resetAll();
            // Sound.play("aaaack.mp3", 0.4);
        }
        myPlayer.draw(gc, millis);
        gc.setFont(Font.font("Futura", FontWeight.BOLD, 15));
        
        gc.setFill(Color.NAVY); //return to menu
        gc.fillRect(camX - WIDTH/2 + 765,camY - HEIGHT/2 + 10, 65, 25);
        gc.setFill(Color.WHITE);
        gc.fillText("WORLDS",camX - WIDTH/2 + 797.5, camY - HEIGHT/2 + 30);
        
        gc.setFill(Color.NAVY); //return to menu
        gc.fillRect(camX - WIDTH/2 + 840,camY - HEIGHT/2 + 10, 50, 25);
        gc.setFill(Color.WHITE);
        gc.fillText("MENU",camX - WIDTH/2 + 865, camY - HEIGHT/2 + 30);
        
        gc.setTransform(new Affine(new Translate(0, 0)));
        
        if (myPlayer.getHealth() <= 0)
            resetAll();
    }
    
    /**
     * method loadMap - takes the given map and updates all the
     * lists for the sprites and the blocks to be correct
     * @param map for the level
     * Jane
     */
    private void loadMap (String[] map)
    {
        Block b;
        QBlock qb; 
        Enemy e;
        for (int i = 0; i < map.length; i ++)
        {
            for (int j = 0; j < map[i].length(); j ++)
            {
                // I'm sorry Mr. L but it's better this way
                char c = map[i].charAt(j);
                int x = j * 60;
                int y = -map.length * 60 + i * 60;
                switch (c)
                {
                    case 'x':
                       while (j < map[i].length() && map[i].charAt(j) == 'x')
                           j ++;
                       if (myWorld == 0)
                           b = new CBlock(x, y, j * 60 - x, 60);
                       else
                           if (myWorld == 1)
                               b = new Block(x, y, j * 60 - x, 60, new Image("rBlock.png"));
                           else
                               b = new Block(x, y, j * 60 - x, 60, new Image("blocks.png"));
                       
                       myBlocks.add(b);
                       mySprites.add(b);
                       break;


                    case '0':
                        myPlayer.setPosition(x, y + 60 - myPlayer.getHeight());
                        myXstart = j * 60;
                        myYstart = (int)(y + 60 - myPlayer.getHeight());
                        break;
                    case '?':
                        qb = new QBlock(x, y);
                        myBlocks.add(qb);
                        myQBlocks.add(qb);
                        mySprites.add(qb);
                        break; 
                    case '!':
                        if(myWorld == 0)
                            e = new BasicEnemy(x, y + 30, (int)(Math.random() * 2));
                        else
                        {
                            if(myWorld == 1)
                                e = new MedEnemy(x, y + 30, new Image("piv.png"));
                            else
                                e = new HardEnemy(x, y + 30, new Image("bug.png"));
                        }
                        mySprites.add(e);
                        myFighters.add(e);
                        myEnemies.add(e);
                        break;

                }
            }
        }
    }
    /**
     * shows the arsenal details 
     * Keerthana, Jane
     * @param gc
     */
    private void loadArsenal(GraphicsContext gc)
    {
        int ammo;
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Futura", FontWeight.BOLD, 15));
        
        gc.fillText("Arsenal: Choose Your Weapon",camX - WIDTH/2 + 120, camY - HEIGHT/2 + 30);
        //creating 1st box
        if(isSelected0)//selecting color
            gc.setFill(Color.BLACK);
        else
            gc.setFill(Color.DARKBLUE);        
        gc.fillRoundRect(camX - WIDTH/2 + 25, camY - HEIGHT/2 + 40, 70, 60,10,10);
        gc.setFont(Font.font("Futura", FontWeight.BOLD, 10));      
        gc.setFill(Color.WHITE);
        gc.fillText("Ammo: inf",camX - WIDTH/2 + 60,camY - HEIGHT/2 + 72);
        
        //creates 2nd box
        if(isSelected1)//selecting color
            gc.setFill(Color.BLACK);
        else
            gc.setFill(Color.BLUE);
        gc.fillRoundRect(camX - WIDTH/2 + 100,camY - HEIGHT/2 + 40, 70, 60,10,10);        
        ammo = myPlayer.getAmmo(1);        
        gc.setFill(Color.WHITE);
        gc.fillText("Ammo: " + ammo,camX - WIDTH/2 + 135,camY - HEIGHT/2 + 72);
        
        //creates 3rd box
        if(isSelected2)//selecting color
            gc.setFill(Color.BLACK);
        else
            gc.setFill(Color.LIGHTBLUE);
        gc.fillRoundRect(camX - WIDTH/2 + 175,camY - HEIGHT/2 + 40, 70, 60,10,10);
        ammo = myPlayer.getAmmo(2);        
        gc.setFill(Color.WHITE);
        gc.fillText("Ammo: " + ammo,camX - WIDTH/2 + 210, camY - HEIGHT/2 + 72);
    }
    /**
     * displays the win on the screen
     * Jane
     * @param gc    thing which you draw on
     */
    private void showWinScreen(GraphicsContext gc)
    {
       //you win
       gc.setFont(Font.font("Futura", FontWeight.BOLD, 100));   
       gc.setFill(Color.BLACK);
       gc.fillText("YOU WIN!", 450, 300);
       if(myLevel < 2)
       {
           //menue buttom
           gc.setFill(Color.MIDNIGHTBLUE);
           gc.fillRect(255,400,100,70);
           gc.setFill(Color.WHITE);
           gc.setFont(new Font("Futura", 20));
           gc.fillText("MENU", 305, 440);
           //next level button
           gc.setFill(Color.DARKBLUE);
           gc.fillRect(530,400,100,70);
           gc.setFill(Color.WHITE);
           gc.setFont(new Font("Futura", 20));
           gc.fillText("NEXT", 580, 435);
           gc.fillText("LEVEL", 580, 455);
        }
       else
       {   //menue buttom
           gc.setFill(Color.MIDNIGHTBLUE);
           gc.fillRect(255,400,100,70);
           gc.setFill(Color.WHITE);
           gc.setFont(new Font("Futura", 20));
           gc.fillText("MENU", 305, 440);
           //next level button
           gc.setFill(Color.DARKBLUE);
           gc.fillRect(530,400,100,70);
           gc.setFill(Color.WHITE);
           gc.setFont(new Font("Futura", 20));
           gc.fillText("WORLDS", 580, 440);
        }
    }
    /**
     * displays the question on the screen
     * Keerthana
     * @param gc    thing to draw on
     * @param list  list of questions
     */
    private void showQuestion(GraphicsContext gc, QuestionList list)
    {                
        Translate t = new Translate(0, 0);
        gc.setTransform(new Affine(t)); 
        
        gc.setTextAlign(TextAlignment.CENTER); //self explanatory
        gc.setFill(Color.LAVENDER);
        gc.fillRect(50,50,800,500); 
        gc.setStroke(Color.GOLDENROD);
        if (displayed == null)
            displayed = list.getQuestion();
        
        gc.strokeRect(150, 310, 600,55);
        gc.strokeRect(150, 370, 600, 55);
        gc.strokeRect(150, 430, 600,55);
        gc.strokeRect(150,490,600,55);
        
        gc.setFill(Color.VIOLET);
        gc.fillRect(150, 310, 600,55);
        gc.fillRect(150, 370, 600, 55);
        gc.fillRect(150, 430, 600,55);
        gc.fillRect(150, 490,600,55);
        
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Futura",15));    
        
        isMultiLine(gc, displayed.getQString(),450,100);
        isMultiLine(gc, displayed.getAnswer(1), 450,330);
        isMultiLine(gc, displayed.getAnswer(2), 450, 390);
        isMultiLine(gc, displayed.getAnswer(3), 450, 450);
        isMultiLine(gc, displayed.getAnswer(4), 450, 510);
        
        if (chosenOne != null)    
            showResult(displayed.correct(chosenOne), displayed, gc);
    }
    /**
     * isMultiLine - draw a string and interpret \\n as a newline
     * helper method for showQuestion()
     * @param gc        this that draws
     * @param question that is displayed
     * @param x, y      where to put the question  
     * Keerthana
     */
    private void isMultiLine(GraphicsContext gc, String question, double x, double y)
    {
        int index,count = 0;
        gc.setFont(new Font("Futura",20));
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);                

        index = question.indexOf("\\n");
        while (index >= 0)
        {
            gc.fillText(question.substring(0, index), x, y + count*20);
            question = question.substring(index + 2);
            index = question.indexOf("\\n");
            count++;
        }
        gc.fillText(question, x, y + count*20);

    }
    /**
     * method showResult - shows the result of the question
     * @param   correct     if the question was answered correctly
     * @param   question    the question to use (for explanation)
     * @param   gc          what to draw on
     * Keerthana
     */
    private void showResult(boolean correct, Question question, GraphicsContext gc)
    {
        gc.setTextAlign(TextAlignment.CENTER); //self explanatory
        
        gc.setFont(Font.font("Futura", FontWeight.BOLD, 15));      

        gc.setFill(Color.LAVENDER);
        gc.fillRect(75,50,750,500);
        
        gc.setFill(Color.STEELBLUE); //return to game
        gc.fillRect(375,400,150,50);
        gc.setFill(Color.BLACK);
        gc.fillText("RETURN TO GAME",450,433);
        if (correct)
        {           
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("Futura", FontWeight.BOLD, 60));
            gc.fillText("CORRECT!", 450, 200);

            isMultiLine(gc, question.getExp(), 450, 275);
        }
        else
        {
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("Futura", FontWeight.BOLD, 60));
            gc.fillText("ACK!", 450, 200);

            isMultiLine(gc, question.getExp(), 450, 275);
        }
    }
    /**
     * transfers questions from a file into a QuestionList
     * @param list  list of questions
     * Keerthana
     */
    private void readQs(QuestionList list)
    {
        Scanner questions = null;
        try
        {
            questions = new Scanner (new FileInputStream("Questions.txt"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        int totalQs, rightNum;
        String question, answer1, answer2, answer3, answer4, rightAns, explanation;
        String fileName = "Questions.txt";
        Question toAdd;

        totalQs = questions.nextInt(); //number of numbers in file
        questions.nextLine();

        //to fill in all values of the file into the BinaryTree
        for (int k = 0; k < totalQs; k++)
        {
            question = questions.nextLine();
            rightNum = questions.nextInt();
            questions.nextLine();
            answer1 = questions.nextLine();
            answer2 = questions.nextLine();
            answer3 = questions.nextLine();
            answer4 = questions.nextLine();
            explanation = questions.nextLine();
            if (rightNum == 1)
                rightAns = answer1;
            else 
                if (rightNum == 2)
                    rightAns = answer2;
                else
                    if (rightNum == 3)
                        rightAns = answer3;
                    else    
                        rightAns = answer4;

            String[] answers = {answer1, answer2, answer3, answer4};
            toAdd = new Question(answers, rightAns, question, explanation);
            toAdd.answerShuffle();
            list.loadQuestions(toAdd);
        }
    }
    /**
     * this method resets the following ArrayLists: mySprites, 
     * myFighters, myEnemies, myBlocks , and myqBlocks
     * @author Keerthana 
     */
    private void resetAll() //sprites fighter enemies blocks qBlocks
    {
        mySprites.clear();
        myEnemies.clear();
        myFighters.clear();
        myBlocks.clear();
        myQBlocks.clear();
        isInit = true;
    }    
}



