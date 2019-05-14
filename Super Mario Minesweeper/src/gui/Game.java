/*
 * This class models the gui for the main game
 */
package gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import logic.*;
/**
 *
 * @author Eric
 */
public class Game extends javax.swing.JFrame {
    /**
     * Creates new form Game
     */
    private GameManager myGame;
    private SoundManager mySoundManager;
    private Timer timer;
    
    public Game(String difficulty, GameManager manager) {
        CustomCursor();
        initComponents();
        myGame = manager;
        timeTxt.setForeground(new Color(255, 255, 255));
        if(manager != null)
        {
            myGame.setGrid(manager.getGrid());
            timeTxt.setText(Integer.toString(myGame.getTimePassed()));
        }
        else
        {
            timeTxt.setText("0");
        }
        mySoundManager = new SoundManager();
        
        gameOverTxt.setVisible(false);
        wonTxt.setVisible(false);
        
        flagUI.setForeground(new Color(255, 255,255));
        gameOpt.setEnabled(true);
        saveOpt.setEnabled(true);
        int[] bLoc = new int[2];
        switch(difficulty)
        {
            case "easy":
                setEasyMode(bLoc);                
            break;
            case "medium":
                 setMediumMode(bLoc);
            break;
            case "hard":
                setHardMode(bLoc);
            break;
        }
        //action listener for timer
        ActionListener countTime = new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt)
            {
                
                myGame.setTimePassed(1);
                timeTxt.setText(Integer.toString(myGame.getTimePassed()));
            }
        };
        timer = new Timer(1000 ,countTime);
        
        setLocationRelativeTo(null);
        if(myGame.getGameOver() && myGame.getWon())
        {
            mySoundManager.stopMusic();
            wonTxt.setVisible(true);
            removeButtonsListeners();
        }
        else if(myGame.getGameOver() && !myGame.getWon())
        {
            mySoundManager.stopMusic();
            gameOverTxt.setVisible(true);
            removeButtonsListeners();
        }
    }
    
    public void setEasyMode(int[] bLoc)
    {
        mySoundManager.playSound("music/new game.wav");
        mySoundManager.playMusic("music/easy.wav");
        easyOpt.setEnabled(false);
        medOpt.setEnabled(true);
        hardOpt.setEnabled(true);
        if(myGame == null)
            myGame = new GameManager("easy");
        this.setSize(new Dimension(500, 530));
        this.setTitle("Super Mario MineSweeper-Easy");    
        flagUI.setText(Integer.toString(myGame.getFlagsLeft()));
             
        top.setSize(new Dimension(500, 80));
        gridGUI.setLayout(new java.awt.GridLayout(8, 8));
        gridGUI.setSize(new Dimension(288, 288));
        gridGUI.setBounds((int)((this.getSize().getWidth()/2)-(gridGUI.getSize().getWidth()/2)),(int)(((this.getSize().getHeight()-100)/2)-(gridGUI.getSize().getHeight()/2)),288,288);
        gameOverTxt.setBounds((int)((this.getSize().getWidth()/2)-200),(int)((this.getSize().getHeight()/2)-50)-(int)(top.getSize().getHeight()/2)-(int)(menu.getSize().getHeight()/2),400,100);
        wonTxt.setBounds((int)((this.getSize().getWidth()/2)-138),(int)((this.getSize().getHeight()/2)-85)-(int)(top.getSize().getHeight()/2)-(int)(menu.getSize().getHeight()/2),275,170);
        bkg.setLocation(0, 0);
        bkg.setSize(new Dimension(500, 420));
        bkg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/first.png")));
        //create button grid
        for(int i = 0; i < myGame.getRen(); i++)
        {
            for(int j = 0; j < myGame.getRen();j++)
            {
                JButton b = new JButton();
                b.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/block.png"))); // NOI18N
                b.setDisabledIcon(null);
                b.setSize(new java.awt.Dimension(36, 36));
                b.setBackground(new Color(51,51,55, 90));
                b.setForeground(new Color(222,222,222, 90));
                gridGUI.add(b);
                     
                //set col value
                bLoc[0] = j;
                //set row value
                bLoc[1] = i;
                openLoadedGame(b, bLoc);
                        
                b.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        blockClicked(evt);
                    }
                });
            }
        }
    }
    
    public void setMediumMode(int[] bLoc)
    {
        mySoundManager.playSound("music/new game.wav");
        mySoundManager.playMusic("music/water.wav");
        easyOpt.setEnabled(true);
        medOpt.setEnabled(false);
        hardOpt.setEnabled(true);
        if(myGame == null)
            myGame = new GameManager("medium");
                
        this.setSize(new Dimension(750, 780));
        this.setTitle("Super Mario MineSweeper-Medium");
        top.setSize(new Dimension(750, 100));
        flagUI.setText(Integer.toString(myGame.getFlagsLeft()));
        gridGUI.setLayout(new java.awt.GridLayout(16, 16));
        gridGUI.setSize(new Dimension(576, 576));
        gridGUI.setBounds((int)((this.getSize().getWidth()/2)-(gridGUI.getSize().getWidth()/2)),(int)(((this.getSize().getHeight()-100)/2)-(gridGUI.getSize().getWidth()/2)),576,576);                
                
        gameOverTxt.setBounds((int)((this.getSize().getWidth()/2)-200),(int)((this.getSize().getHeight()/2)-50)-(int)(top.getSize().getHeight()/2)-(int)(menu.getSize().getHeight()/2),400,100);
        wonTxt.setBounds((int)((this.getSize().getWidth()/2)-138),(int)((this.getSize().getHeight()/2)-85)-(int)(top.getSize().getHeight()/2)-(int)(menu.getSize().getHeight()/2),275,170);
                
        bkg.setLocation(0, 0);
        bkg.setSize(new Dimension(750, 670));
        bkg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/water.png")));
        for(int i = 0; i < myGame.getRen(); i++)
        {
            for(int j = 0; j < myGame.getRen(); j++)
            {
                JButton b = new JButton();
                b.setBackground(new java.awt.Color(153, 204, 255));
                b.setForeground(new java.awt.Color(255, 255, 255));
                b.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/block.png"))); // NOI18N
                b.setDisabledIcon(null);
                b.setMaximumSize(new java.awt.Dimension(36, 36));
                b.setMinimumSize(new java.awt.Dimension(36, 36));
                b.setPreferredSize(new java.awt.Dimension(36, 36));
                gridGUI.add(b);
                //set col value
                bLoc[0] = j;
                //set row value
                bLoc[1] = i;
                openLoadedGame(b, bLoc);
                b.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        blockClicked(evt);
                    }
                });     
            }
        }        
    }
    
    public void setHardMode(int[] bLoc)
    {
        mySoundManager.playSound("music/new game.wav");
        mySoundManager.playMusic("music/castle.wav");
        easyOpt.setEnabled(true);
        medOpt.setEnabled(true);
        hardOpt.setEnabled(false);
        if(myGame == null)
            myGame = new GameManager("hard");
        flagUI.setText(Integer.toString(myGame.getFlagsLeft()));
        this.setTitle("Super Mario MineSweeper-Hard");
        this.setSize(new Dimension(1000, 1030));
        top.setSize(new Dimension(1000, 100));
        gridGUI.setLayout(new java.awt.GridLayout(24, 24));
        gridGUI.setSize(new Dimension(864, 864));
        gridGUI.setBounds((int)((this.getSize().getWidth()/2)-(gridGUI.getSize().getWidth()/2)),(int)(((this.getSize().getHeight()-100)/2)-(gridGUI.getSize().getWidth()/2)),864,864);
               
        gameOverTxt.setBounds((int)((this.getSize().getWidth()/2)-200),(int)((this.getSize().getHeight()/2)-50)-(int)(top.getSize().getHeight()/2)-(int)(menu.getSize().getHeight()/2),400,100);
        wonTxt.setBounds((int)((this.getSize().getWidth()/2)-138),(int)((this.getSize().getWidth()/2)-85)-(int)(top.getSize().getHeight()/2)-(int)(menu.getSize().getHeight()/2),275,170);
                
        bkg.setLocation(0, 0);
        bkg.setSize(new Dimension(1000, 920));
        bkg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/castle.png")));
               
        for(int i = 0; i < myGame.getRen(); i++)
        {
            for(int j = 0; j < myGame.getRen(); j++)
            {
                JButton b = new JButton();
                b.setBackground(new java.awt.Color(153, 204, 255));
                b.setForeground(new java.awt.Color(255, 255, 255));
                b.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/block.png"))); // NOI18N
                b.setDisabledIcon(null);
                b.setSize(new java.awt.Dimension(36, 36));
                gridGUI.add(b);
                //set col value
                bLoc[0] = j;
                //set row value
                bLoc[1] = i;
                openLoadedGame(b, bLoc);
                b.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        blockClicked(evt);
                    }
                });     
            }
        }
    }
    
    public void CustomCursor()
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image img = toolkit.getImage("marioCursor.png");
        Point point = new Point(0, 0);
        Cursor cursor = toolkit.createCustomCursor(img, point, "Cursor");
        
        setCursor(cursor);
    }
    
    public void openLoadedGame(JButton b, int[] bLoc)
    {
        if(myGame.getGrid()[bLoc[0]][bLoc[1]].getIsOpen())
        {
            openCells(b, bLoc);
        }
        if(myGame.getGrid()[bLoc[0]][bLoc[1]].getIsFlagged())
        {
            b.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/flagged block.png")));
            b.setEnabled(false);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        top = new javax.swing.JPanel();
        flagUI = new javax.swing.JLabel();
        restartBtn = new javax.swing.JButton();
        timeTxt = new javax.swing.JLabel();
        center = new javax.swing.JPanel();
        gameOverTxt = new javax.swing.JLabel();
        wonTxt = new javax.swing.JLabel();
        gridGUI = new javax.swing.JPanel();
        bkg = new javax.swing.JLabel();
        menu = new javax.swing.JMenuBar();
        gameOpt = new javax.swing.JMenu();
        easyOpt = new javax.swing.JMenuItem();
        medOpt = new javax.swing.JMenuItem();
        hardOpt = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        restartOpt = new javax.swing.JMenuItem();
        saveOpt = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuOpt = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        top.setBackground(new java.awt.Color(51, 51, 255));
        top.setForeground(new java.awt.Color(51, 51, 255));
        top.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 90, 5));

        flagUI.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        flagUI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/flag.png"))); // NOI18N
        top.add(flagUI);

        restartBtn.setBackground(new java.awt.Color(51, 51, 255));
        restartBtn.setForeground(new java.awt.Color(51, 51, 255));
        restartBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/normal face.png"))); // NOI18N
        restartBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                restartBtnMouseClicked(evt);
            }
        });
        top.add(restartBtn);

        timeTxt.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        timeTxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/clock.png"))); // NOI18N
        top.add(timeTxt);

        getContentPane().add(top, java.awt.BorderLayout.PAGE_START);

        center.setBackground(new java.awt.Color(153, 153, 255));
        center.setLayout(null);

        gameOverTxt.setFont(new java.awt.Font("Comic Sans MS", 1, 48)); // NOI18N
        gameOverTxt.setForeground(new java.awt.Color(255, 0, 51));
        gameOverTxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/game over.png"))); // NOI18N
        gameOverTxt.setToolTipText("");
        center.add(gameOverTxt);
        gameOverTxt.setBounds(40, 80, 380, 100);

        wonTxt.setFont(new java.awt.Font("Comic Sans MS", 1, 48)); // NOI18N
        wonTxt.setForeground(new java.awt.Color(255, 255, 0));
        wonTxt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/win.png"))); // NOI18N
        center.add(wonTxt);
        wonTxt.setBounds(110, 90, 300, 180);

        gridGUI.setBackground(new Color(51,51,55, 90));
        gridGUI.setForeground(new Color(222,222,222, 90));
        gridGUI.setLayout(new java.awt.GridLayout(8, 8));
        center.add(gridGUI);
        gridGUI.setBounds(94, 12, 0, 0);

        bkg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/first.png"))); // NOI18N
        center.add(bkg);
        bkg.setBounds(0, 0, 500, 500);

        getContentPane().add(center, java.awt.BorderLayout.CENTER);

        gameOpt.setText("Game");

        easyOpt.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        easyOpt.setText("Easy Mode");
        easyOpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                easyOptActionPerformed(evt);
            }
        });
        gameOpt.add(easyOpt);

        medOpt.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        medOpt.setText("Medium Mode");
        medOpt.setToolTipText("");
        medOpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medOptActionPerformed(evt);
            }
        });
        gameOpt.add(medOpt);

        hardOpt.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        hardOpt.setText("Hard Mode");
        hardOpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hardOptActionPerformed(evt);
            }
        });
        gameOpt.add(hardOpt);
        gameOpt.add(jSeparator1);

        restartOpt.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        restartOpt.setText("Restart Game");
        restartOpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartOptActionPerformed(evt);
            }
        });
        gameOpt.add(restartOpt);

        saveOpt.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveOpt.setText("Save Game");
        saveOpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveOptActionPerformed(evt);
            }
        });
        gameOpt.add(saveOpt);
        gameOpt.add(jSeparator2);

        menuOpt.setText("Main Menu");
        menuOpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOptActionPerformed(evt);
            }
        });
        gameOpt.add(menuOpt);

        menu.add(gameOpt);

        setJMenuBar(menu);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void restartBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_restartBtnMouseClicked
        // TODO add your handling code here:
        if(SwingUtilities.isLeftMouseButton(evt))
        {
            mySoundManager.stopMusic();
            this.setVisible(false);
            new Game(myGame.getDifficulty(), null).setVisible(true);
        }
    }//GEN-LAST:event_restartBtnMouseClicked

    private void easyOptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_easyOptActionPerformed
        // TODO add your handling code here:
        if(easyOpt.isEnabled())
        {
            mySoundManager.stopMusic();
            this.setVisible(false);
            new Game("easy", null).setVisible(true);
        }
    }//GEN-LAST:event_easyOptActionPerformed

    private void medOptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medOptActionPerformed
        // TODO add your handling code here:
        if(medOpt.isEnabled())
        {
            mySoundManager.stopMusic();
            this.setVisible(false);
            new Game("medium", null).setVisible(true);
        }
    }//GEN-LAST:event_medOptActionPerformed

    private void hardOptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hardOptActionPerformed
        // TODO add your handling code here:
        if(hardOpt.isEnabled())
        {
            mySoundManager.stopMusic();
            this.setVisible(false);
            new Game("hard", null).setVisible(true);
        }
    }//GEN-LAST:event_hardOptActionPerformed

    private void restartOptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartOptActionPerformed
        // TODO add your handling code here:
        mySoundManager.stopMusic();
        this.setVisible(false);
        new Game(myGame.getDifficulty(), null).setVisible(true);
        
    }//GEN-LAST:event_restartOptActionPerformed

    private void menuOptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOptActionPerformed
        // TODO add your handling code here:
        mySoundManager.stopMusic();
        this.setVisible(false);
        new MainMenu().setVisible(true);
    }//GEN-LAST:event_menuOptActionPerformed

    private void saveOptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveOptActionPerformed
        // TODO add your handling code here:
        
        timer.stop();
        new SaveGame(myGame).setVisible(true);
    }//GEN-LAST:event_saveOptActionPerformed
    
    
    private void blockClicked(java.awt.event.MouseEvent evt)
    {
        timer.start();
        JButton selectedBlock = (JButton)evt.getSource();
        int[] location = new int[2];
        //set col value
        location[0] = (int) Math.floor(selectedBlock.getLocation().getX()/36);
        //set row value
        location[1] = (int) Math.floor(selectedBlock.getLocation().getY()/36);
        
        if(selectedBlock.isEnabled() && SwingUtilities.isLeftMouseButton(evt))
        {
           leftClick(selectedBlock, location);
        }
        //place or remove flags
        else if(SwingUtilities.isRightMouseButton(evt))
        {
            rightClick(selectedBlock, location);
        }
    }
    
    
    
    private void openCells(JButton selectedBlock, int[] location)
    {
        //open cells if they don't have a flag or a mine
        if(!myGame.getGrid()[location[0]][location[1]].getIsFlagged() || !myGame.getGrid()[location[0]][location[1]].getHasMine())
        {
            //If the cell has a flag, remove it
            if(myGame.getGrid()[location[0]][location[1]].getIsFlagged())
            {
                myGame.removeFlag(location);
                flagUI.setText(Integer.toString(myGame.getFlagsLeft()));
            }
            
            //if it doesn't have a flag and it has a mine reveal it
            if(!myGame.getGrid()[location[0]][location[1]].getIsFlagged() && myGame.getGrid()[location[0]][location[1]].getHasMine())
            {
                selectedBlock.setIcon(null);
                selectedBlock.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/mine.png")));
                selectedBlock.setEnabled(false);
            }
            //if the cell is open in the logic grid
            else if(myGame.getGrid()[location[0]][location[1]].getIsOpen())
            {
                //set its icon depending on the surrounding mines
                switch(myGame.getGrid()[location[0]][location[1]].getSurroundingMines())
                {
                    case 0:
                        openEmptyCell(selectedBlock, location);
                    break;
                    case 1:
                        selectedBlock.setIcon(null);
                        selectedBlock.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/1.png")));
                        selectedBlock.setEnabled(false);
                    break;
                    case 2:
                        selectedBlock.setIcon(null);
                        selectedBlock.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/2.png")));
                        selectedBlock.setEnabled(false);
                    break;
                    case 3:
                        selectedBlock.setIcon(null);
                        selectedBlock.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/3.png")));
                        selectedBlock.setEnabled(false);
                    break;
                    case 4:
                        selectedBlock.setIcon(null);
                        selectedBlock.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/4.png")));
                        selectedBlock.setEnabled(false);
                    break;
                    case 5:
                        selectedBlock.setIcon(null);
                        selectedBlock.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/5.png")));
                        selectedBlock.setEnabled(false);
                    break;
                    case 6:
                        selectedBlock.setIcon(null);
                        selectedBlock.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/6.png")));
                        selectedBlock.setEnabled(false);
                    break;
                    case 7:
                        selectedBlock.setIcon(null);
                        selectedBlock.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/7.png")));
                        selectedBlock.setEnabled(false);
                    break;
                    case 8:
                        selectedBlock.setIcon(null);
                        selectedBlock.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/8.png")));
                        selectedBlock.setEnabled(false);
                    break;
                }
            }
        } 
    }
        //function for getting the button component # of a certain cell
        private int getCompNum(int col, int ren)
        {
            int num = col;
            int size = myGame.getRen();
            if(ren != 0)
            {
                num += size*ren;
            }
            return num;
        }
        
        private void removeButtonsListeners()
        {
            for(int i = 0; i < myGame.getRen()*myGame.getRen(); i++)
                {
                    JButton b = (JButton)gridGUI.getComponent(i);
                    for(MouseListener ml : b.getMouseListeners())
                    {
                        b.removeMouseListener(ml);
                    }
                }
            
        }
        
        private void leftClick(JButton selectedBlock, int[] location)
        {
            mySoundManager.playSound("music/brick.wav");
            myGame.checkCell(location);
            if(myGame.getGrid()[location[0]][location[1]].getHasMine())
            {
                gameOver(selectedBlock);
            }
            else
            {
                myGame.checkCollidingCells(location);
                openCells(selectedBlock, location);
                myGame.checkOpenCells();
                if(myGame.getWon())
                {
                    win();
                }
            }
        }
        
        private void rightClick(JButton selectedBlock, int[] location)
        {
            mySoundManager.playSound("music/flag.wav");
            if(!selectedBlock.isEnabled() && myGame.getGrid()[location[0]][location[1]].getIsFlagged())
            {
                myGame.removeFlag(location);
                selectedBlock.setEnabled(true);
                flagUI.setText(Integer.toString(myGame.getFlagsLeft()));
            }
            else if(selectedBlock.isEnabled() && myGame.getFlagsLeft() > 0)
            {
                myGame.placeFlag(location);
                selectedBlock.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/flagged block.png")));
                selectedBlock.setEnabled(false);
                flagUI.setText(Integer.toString(myGame.getFlagsLeft()));
                myGame.checkOpenCells();
                if(myGame.getWon())
                {
                    win();
                }
                
            }
        }
        
        private void openEmptyCell(JButton selectedBlock, int[] location)
        {
            selectedBlock.setDisabledIcon(null);
            selectedBlock.setEnabled(false);
            myGame.openSurroundingCells(location);
            //open cells in gui
            int col = location[0];
            int ren = location[1];
            int[] newLoc = new int[2];
            JButton colBlock;
            /*
            *open every surrounding cell that exists
            */
            if(col-1 >= 0 && ren-1 >= 0 && !myGame.getGrid()[col-1][ren-1].getGuiOpen())
            {
                myGame.getGrid()[col-1][ren-1].setGuiOpen(true);
                colBlock = (JButton)gridGUI.getComponent(getCompNum(col-1, ren-1));
                newLoc[0] = (int)colBlock.getLocation().getX()/36;
                newLoc[1] = (int)colBlock.getLocation().getY()/36;
                openCells(colBlock, newLoc);
            }

            if(ren-1 >= 0 && !myGame.getGrid()[col][ren-1].getGuiOpen())
            {
                myGame.getGrid()[col][ren-1].setGuiOpen(true);
                colBlock = (JButton)gridGUI.getComponent(getCompNum(col, ren-1));
                newLoc[0] = (int)colBlock.getLocation().getX()/36;
                newLoc[1] = (int)colBlock.getLocation().getY()/36;
                openCells(colBlock, newLoc);
            }

            if(col+1 < myGame.getRen() && ren-1 >= 0 && !myGame.getGrid()[col+1][ren-1].getGuiOpen())
            {
                myGame.getGrid()[col+1][ren-1].setGuiOpen(true);
                colBlock = (JButton)gridGUI.getComponent(getCompNum(col+1, ren-1));
                newLoc[0] = (int)colBlock.getLocation().getX()/36;
                newLoc[1] = (int)colBlock.getLocation().getY()/36;
                openCells(colBlock, newLoc);
            }

            if(col-1 >= 0 && !myGame.getGrid()[col-1][ren].getGuiOpen())
            {
                myGame.getGrid()[col-1][ren].setGuiOpen(true);
                colBlock = (JButton)gridGUI.getComponent(getCompNum(col-1, ren));
                newLoc[0] = (int)colBlock.getLocation().getX()/36;
                newLoc[1] = (int)colBlock.getLocation().getY()/36;
                openCells(colBlock, newLoc);
            }
            if(col+1 < myGame.getRen() && !myGame.getGrid()[col+1][ren].getGuiOpen())
            {
                myGame.getGrid()[col+1][ren].setGuiOpen(true);
                colBlock = (JButton)gridGUI.getComponent(getCompNum(col+1, ren));
                newLoc[0] = (int)colBlock.getLocation().getX()/36;
                newLoc[1] = (int)colBlock.getLocation().getY()/36;
                openCells(colBlock, newLoc);
            }

            if(col-1 >= 0 && ren+1 < myGame.getRen() && !myGame.getGrid()[col-1][ren+1].getGuiOpen())
            {
                myGame.getGrid()[col-1][ren+1].setGuiOpen(true);
                colBlock = (JButton)gridGUI.getComponent(getCompNum(col-1, ren+1));
                newLoc[0] = (int)colBlock.getLocation().getX()/36;
                newLoc[1] = (int)colBlock.getLocation().getY()/36;
                openCells(colBlock, newLoc);
            }

            if(ren+1 < myGame.getRen() && !myGame.getGrid()[col][ren+1].getGuiOpen())
            {
                myGame.getGrid()[col][ren+1].setGuiOpen(true);
                colBlock = (JButton)gridGUI.getComponent(getCompNum(col, ren+1));
                newLoc[0] = (int)colBlock.getLocation().getX()/36;
                newLoc[1] = (int)colBlock.getLocation().getY()/36;
                openCells(colBlock, newLoc);
            }
            if(col+1 < myGame.getRen() && ren+1 < myGame.getRen() && !myGame.getGrid()[col+1][ren+1].getGuiOpen())
            {
                myGame.getGrid()[col+1][ren+1].setGuiOpen(true);
                colBlock = (JButton)gridGUI.getComponent(getCompNum(col+1, ren+1));
                newLoc[0] = (int)colBlock.getLocation().getX()/36;
                newLoc[1] = (int)colBlock.getLocation().getY()/36;
                openCells(colBlock, newLoc);
            }
        }
        
        private void gameOver(JButton selectedBlock)
        {
            for(int i = 0; i < myGame.getRen()*myGame.getRen(); i++)
            {
                JButton b = (JButton)gridGUI.getComponent(i);
                int[] loc = new int[2];
                loc[0] = (int)Math.floor(b.getX()/36);
                loc[1] = (int)Math.floor(b.getY()/36);
                openCells(b, loc);
                if(myGame.getGrid()[loc[0]][loc[1]].getHasMine() && !myGame.getGrid()[loc[0]][loc[1]].getIsFlagged())
                {
                    b.setIcon(null);
                    b.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/mine.png")));
                    b.setEnabled(false);
                }
            }
            selectedBlock.setIcon(null);
            selectedBlock.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/mine.png")));
            selectedBlock.setEnabled(false);
            
            mySoundManager.stopMusic();
            mySoundManager.playSound("music/game over.wav");
            
            gameOverTxt.setVisible(true);
            timer.stop();
            restartBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/sad mario.png")));
            removeButtonsListeners();
        }
        
        private void win()
        {
            mySoundManager.stopMusic();
            mySoundManager.playSound("music/win.wav");
            wonTxt.setVisible(true);
            timer.stop();
            restartBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/images/happy mario.png")));
            removeButtonsListeners();
        }
        
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bkg;
    private javax.swing.JPanel center;
    private javax.swing.JMenuItem easyOpt;
    private javax.swing.JLabel flagUI;
    private javax.swing.JMenu gameOpt;
    private javax.swing.JLabel gameOverTxt;
    private javax.swing.JPanel gridGUI;
    private javax.swing.JMenuItem hardOpt;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem medOpt;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenuItem menuOpt;
    private javax.swing.JButton restartBtn;
    private javax.swing.JMenuItem restartOpt;
    private javax.swing.JMenuItem saveOpt;
    private javax.swing.JLabel timeTxt;
    private javax.swing.JPanel top;
    private javax.swing.JLabel wonTxt;
    // End of variables declaration//GEN-END:variables
}
