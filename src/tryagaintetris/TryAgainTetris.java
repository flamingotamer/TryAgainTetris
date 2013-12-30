package tryagaintetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import static tryagaintetris.TryAgainTetris.blocksArray;
        
public class TryAgainTetris extends JPanel implements ActionListener, KeyListener{
    
    private Timer timer = new Timer(10, this);
    private int numberLines = 0;
    private String name;
    protected static boolean pause = false;
    private boolean gameOver = false;
    private boolean showingScores = false;
    private int p = 0;
    private ImageIcon pikachu = new ImageIcon(getClass().getResource("pikachu.png"));
    private JLabel pausePoster = new JLabel(pikachu);
    private ImageIcon helloKitty = new ImageIcon(getClass().getResource("hellokitty.jpg"));
    private JLabel hkPoster = new JLabel(helloKitty);
                
    //Z Squiggle coordinates
    private int npointsZSquiggle = 10;
    private int[] xpointsZSquiggle = {105, 140, 175, 175, 210, 210, 175, 140, 140, 105};
    private int[] ypointsZSquiggle = {0, 0, 0, 35, 35, 70, 70, 70, 35, 35};
    private ZType ZBlock = new ZType(xpointsZSquiggle, ypointsZSquiggle, npointsZSquiggle);
    private Area ZBlockArea;
    
    //Back Z Squiggle coordinates
    private int npointsBackZSquiggle = 10;
    private int[] xpointsBackZSquiggle = {105, 140, 140, 175, 210, 210, 175, 175, 140, 105};
    private int[] ypointsBackZSquiggle = {35, 35, 0, 0, 0, 35, 35, 70, 70, 70};
    private BackZType backZBlock = new BackZType(xpointsBackZSquiggle, ypointsBackZSquiggle, npointsBackZSquiggle);
    private Area backZBlockArea;
    
    //L coordinates
    private int npointsL = 10;
    private int[] xpointsL = {105, 140, 175, 210, 210, 175, 140, 140, 105, 105};
    private int[] ypointsL = {0, 0, 0, 0, 35, 35, 35, 70, 70, 35};
    private LType LBlock = new LType(xpointsL, ypointsL, npointsL);
    private Area LBlockArea;
    
    //Back L coordinates
    private int nointsBackL = 10;
    private int[] xpointsBackL = {105, 140, 175, 210, 210, 210, 175, 175, 140, 105};
    private int[] ypointsBackL = {0, 0, 0, 0, 35, 70, 70, 35, 35, 35};
    private BackLType backLBlock = new BackLType(xpointsBackL, ypointsBackL, nointsBackL);
    private Area backLBlockArea;
    
    //T coordinates
    private int npointsT = 10;
    private int[] xpointsT = {105, 140, 175, 210, 210, 175, 175, 140, 140, 105};
    private int[] ypointsT = {0, 0, 0, 0, 35, 35, 70, 70, 35, 35};
    private TType TBlock = new TType(xpointsT, ypointsT, npointsT);
    private Area TBlockArea;
    
    private LineType lineBlock = new LineType(105, -35, 140, 35);
    private SquareType squareBlock = new SquareType(140, -35, 70, 70);
        
    protected static boolean line, square, zsquiggle, L, backzsquiggle, backlblock, T;
        
    private int placeInList = 0;
    private ArrayList<Integer> listOfUpcomingBlocks = new ArrayList<Integer>();
    
    private int[] TetrisGods = {0, 1, 2, 3, 4, 5, 6};
    //0 = line, 1 = square, 2 = Zsquiggle, 3 = L, 4 = backzsquiggle
    //5 = backlblock
    
    protected static Rectangle[][] blocksArray = {new Rectangle[10], new Rectangle[10], new Rectangle[10], new Rectangle[10], new Rectangle[10],
    new Rectangle[10], new Rectangle[10], new Rectangle[10], new Rectangle[10], new Rectangle[10], new Rectangle[10], new Rectangle[10], new Rectangle[10], new Rectangle[10], new Rectangle[10],
    new Rectangle[10], new Rectangle[10], new Rectangle[10], new Rectangle[10], new Rectangle[10]};
    protected static boolean[][] booleanBlocksArrayEmpty = {new boolean[10], new boolean[10], new boolean[10], new boolean[10], new boolean[10],
    new boolean[10], new boolean[10], new boolean[10], new boolean[10], new boolean[10],new boolean[10], new boolean[10], new boolean[10], new boolean[10], new boolean[10],
    new boolean[10], new boolean[10], new boolean[10], new boolean[10], new boolean[10]}; 
    private int[][] colors = {new int[10], new int[10], new int[10], new int[10], new int[10],
    new int[10], new int[10], new int[10], new int[10], new int[10], new int[10], new int[10], new int[10], new int[10], new int[10],
    new int[10], new int[10], new int[10], new int[10], new int[10],}; 
    
    private final int borderCornerX = 395;
    private final int borderCornerY = 185;
    private final int borderWidth = 150; 
    private final int borderHeight = 175;
    
    private int[] xpointsZSample = {borderCornerX + (45/2), borderCornerX + (45/2)+70, borderCornerX + (45/2)+70, borderCornerX + (45/2)+105, 
        borderCornerX + (45/2)+105, borderCornerX + (45/2)+35, borderCornerX + (45/2)+35, borderCornerX + (45/2)};
    private int[] ypointsZSample = {borderCornerY + (105/2), borderCornerY + (105/2), borderCornerY + (105/2)+35, borderCornerY + (105/2)+35, 
        borderCornerY + (105/2)+70, borderCornerY + (105/2)+70, borderCornerY + (105/2)+35, borderCornerY + (105/2)+35};
    private Area ZSample = new Area(new Polygon(xpointsZSample, ypointsZSample, 8));
    
    private int[] xpointsLSample = {borderCornerX + (45/2), borderCornerX + (45/2) + 105, borderCornerX + (45/2) +105, borderCornerX + (45/2) + 35,
        borderCornerX + (45/2) + 35, borderCornerX + (45/2)};
    private int[] ypointsLSample = {borderCornerY + (105/2), borderCornerY + (105/2), borderCornerY + (105/2) + 35, borderCornerY + (105/2) + 35,
        borderCornerY + (105/2) + 70, borderCornerY + (105/2) + 70};
    private Area LSample = new Area(new Polygon(xpointsLSample, ypointsLSample, 6));    
     
    private int[] xpointsBackZSample = {borderCornerX + (45/2), borderCornerX + (45/2) + 35, borderCornerX + (45/2) + 35, borderCornerX + (45/2) + 105, borderCornerX + (45/2) + 105, borderCornerX + (45/2) + 70,
        borderCornerX + (45/2) + 70, borderCornerX + (45/2)};
    private int[] ypointsBackZSample = {borderCornerY + (105/2) + 35, borderCornerY + (105/2) + 35, borderCornerY + (105/2), borderCornerY + (105/2), borderCornerY + (105/2) + 35, borderCornerY + (105/2) + 35, 
        borderCornerY + (105/2) + 70, borderCornerY + (105/2) + 70};
    private Area backZSample = new Area(new Polygon(xpointsBackZSample, ypointsBackZSample, 8));
    
    private int[] xpointsBackLSample = {borderCornerX + (45/2), borderCornerX+(45/2)+105, borderCornerX+(45/2)+105, borderCornerX+(45/2)+70, borderCornerX+(45/2)+70,
        borderCornerX+(45/2)};
    private int[] ypointsBackLSample = {borderCornerY+(105/2), borderCornerY+(105/2), borderCornerY+(105/2)+70, borderCornerY+(105/2)+70, borderCornerY+(105/2)+35,
        borderCornerY+(105/2)+35};
    private Area backLSample = new Area(new Polygon(xpointsBackLSample, ypointsBackLSample, 6));
    
    private int[] xpointsTSample = {borderCornerX+(45/2), borderCornerX+(45/2)+105, borderCornerX+(45/2)+105, borderCornerX+(45/2)+70, borderCornerX+(45/2)+70,
        borderCornerX+(45/2)+35, borderCornerX+(45/2)+35, borderCornerX+(45/2)};
    private int[] ypointsTSample = {borderCornerY+(105/2), borderCornerY+(105/2), borderCornerY+(105/2)+35, borderCornerY+(105/2)+35, borderCornerY+(105/2)+70, 
        borderCornerY+(105/2)+70, borderCornerY+(105/2)+35, borderCornerY+(105/2)+35};
    private Area TSample = new Area(new Polygon(xpointsTSample, ypointsTSample, 8));
    
    public TryAgainTetris(){
        this.setLayout(null);
        setBackground(Color.BLACK);
        
        JFrame frame = new JFrame("Try Again Tetris");
        frame.setSize(600, 696); //window (365, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setVisible(true);
        timer.setDelay(500);
        timer.start();
        
        for(int n = 0; n < blocksArray.length; n++){
            for(int j = 0; j < blocksArray[n].length; j++){
                blocksArray[n][j] = new Rectangle(j*35, n*35, 35, 35);
                booleanBlocksArrayEmpty[n][j] = true;
                colors[n][j] = 0;
            }
        }
                
        preparePieces(placeInList);
        allHailTetrisGods();
    }
    
    public void preparePieces(int s){
        for(int n = s; n < placeInList + 100; n++){
            int randNum = (int)Math.floor(Math.random()*7);
            listOfUpcomingBlocks.add(randNum);
        }
    }
    
    public void allHailTetrisGods(){
        line = false; square = false; zsquiggle = false; L = false; 
        backzsquiggle = false; backlblock = false; T = false;
        int num = listOfUpcomingBlocks.get(placeInList);
        if(num == 0){
            line = true;
            lineBlock = new LineType(105, 0, 140, 35);
        }else if(num == 1){
            square = true;
            squareBlock = new SquareType(140, 0, 70, 70);
        }else if(num == 2){
            zsquiggle = true;
            ZBlock = new ZType(xpointsZSquiggle, ypointsZSquiggle, npointsZSquiggle);
            ZBlockArea = new Area(ZBlock);
        }else if(num == 3){
            L = true;
            LBlock = new LType(xpointsL, ypointsL, npointsL);
            LBlockArea = new Area(LBlock);
        }else if(num == 4){
            backzsquiggle = true;
            backZBlock = new BackZType(xpointsBackZSquiggle, ypointsBackZSquiggle, npointsBackZSquiggle);
            backZBlockArea = new Area(backZBlock);
        }else if(num == 5){
            backlblock = true;
            backLBlock = new BackLType(xpointsBackL, ypointsBackL, nointsBackL);
            backLBlockArea = new Area(backLBlock);
        }else if(num == 6){
            T = true;
            TBlock = new TType(xpointsT, ypointsT, npointsT);
            TBlockArea = new Area(TBlock);
        }
        placeInList++;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
                
        for(int n = 0; n < blocksArray.length; n++){
            int rowBlocks = 0;
            for(int j = 0; j < blocksArray[n].length; j++){
                if(booleanBlocksArrayEmpty[n][j]){
                    g2.setColor(Color.BLACK);
                    g2.fillRect(blocksArray[n][j].x, blocksArray[n][j].y, 35, 35);
                }else{
                    if(colors[n][j] == 1) g2.setColor(Color.ORANGE);
                    else if(colors[n][j] == 2) g2.setColor(Color.RED);
                    else if(colors[n][j] == 3) g2.setColor(Color.GREEN);
                    else if(colors[n][j] == 4) g2.setColor(Color.BLUE);
                    else if(colors[n][j] == 5) g2.setColor(Color.CYAN);
                    else if(colors[n][j] == 6) g2.setColor(Color.MAGENTA);
                    else if(colors[n][j] == 7) g2.setColor(Color.YELLOW);
                    g2.fillRect(blocksArray[n][j].x, blocksArray[n][j].y, 35, 35);
                    rowBlocks++;
                }
                
                if(rowBlocks == 10) deleteRow(n);
            }
        }        
        
        if(line){
            g2.setColor(Color.ORANGE);
            g2.fillRect(lineBlock.x, lineBlock.y, lineBlock.width, lineBlock.height);
        }else if(square){
            g2.setColor(Color.RED);
            g2.fillRect(squareBlock.x, squareBlock.y, squareBlock.width, squareBlock.height);
        }else if(zsquiggle){
            g2.setColor(Color.GREEN);
            g2.fill(ZBlockArea);
        }else if(L){
            g2.setColor(Color.BLUE);
            g2.fill(LBlockArea);
        }else if(backzsquiggle){
            g2.setColor(Color.CYAN);
            g2.fill(backZBlockArea);
        }else if(backlblock){
            g2.setColor(Color.MAGENTA);
            g2.fill(backLBlock);
        }else if(T){
            g2.setColor(Color.YELLOW);
            g2.fill(TBlockArea);
        }
               
        
        //next block predictor
        g2.setColor(Color.BLACK);
        g2.fillRect(385, 175, 170, 230);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRect(395, 185, 150, 175);       
        
        if(listOfUpcomingBlocks.get(placeInList) == 0){
            g2.setColor(Color.ORANGE);
            g2.fillRect(borderCornerX+(borderWidth/2)- 70, borderCornerY+(borderHeight/2)-17, 140, 35);
        }else if(listOfUpcomingBlocks.get(placeInList) == 1){
            g2.setColor(Color.RED);
            g2.fillRect(borderCornerX+(borderWidth/2)-(squareBlock.width/2), borderCornerY+(borderHeight/2)-(squareBlock.height/2), squareBlock.width, squareBlock.height);
        }else if(listOfUpcomingBlocks.get(placeInList) == 2){
            g2.setColor(Color.GREEN);
            g2.fill(ZSample);
        }else if(listOfUpcomingBlocks.get(placeInList) == 3){
            g2.setColor(Color.BLUE);
            g2.fill(LSample);
        }else if(listOfUpcomingBlocks.get(placeInList) == 4){
            g2.setColor(Color.CYAN);
            g2.fill(backZSample);
        }else if(listOfUpcomingBlocks.get(placeInList) == 5){
            g2.setColor(Color.MAGENTA);
            g2.fill(backLSample);
        }else if(listOfUpcomingBlocks.get(placeInList) == 6){
            g2.setColor(Color.YELLOW);
            g2.fill(TSample);
        }
        
        g2.setColor(Color.WHITE);
        Font font = new Font("Serif", Font.PLAIN, 22);
        g2.setFont(font);
        g2.drawString("Lines: " + numberLines, 395, 390);
        
        
        g2.setStroke(new BasicStroke(1));
        for(int n = 0; n < 385; n+=35){
            g2.setColor(Color.WHITE);
            g2.drawLine(n, 0, n, 665);
        }
        
        for(int j = 0; j < 700; j+=35){
            g2.setColor(Color.WHITE);
            g2.drawLine(0, j, 350, j);
        }
        
        if(pause){
            pausePoster.setBounds(0, 0, 365, 700);
            this.add(pausePoster);
        }else this.remove(pausePoster);
        
        if(gameOver){
            hkPoster.setBounds(0, 0, 365, 700);
            this.add(hkPoster);
            
            if(numberLines > 0) addNewScore(name, numberLines);
            
            try{
                File xmlFile = new File("scores.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);

                doc.getDocumentElement().normalize();

                NodeList list = doc.getElementsByTagName("person");
                
                g2.setColor(Color.WHITE);
                g2.drawString("SCORES", 420, 430);
                int yscores = 450;

                for(int i = 0; i < list.getLength(); i++){
                    Node currentNode = list.item(i);

                    if(currentNode.getNodeType() == Node.ELEMENT_NODE){
                        Element e = (Element)currentNode;

                        g2.drawString(e.getElementsByTagName("username").item(0).getTextContent(), 395, yscores+(i*20));
                        g2.drawString(e.getElementsByTagName("lines").item(0).getTextContent(), 525, yscores+(i*20));
                        
                        System.out.println(e.getElementsByTagName("username").item(0).getTextContent() + 
                                "     " + e.getElementsByTagName("lines").item(0).getTextContent());
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
                        
        }
        
    }
    
    public void addNewScore(String name, int lines){
        try {
                System.out.println("hola");
		String filepath = "scores.xml";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);
 
		// Get the root element
		Node company = doc.getFirstChild();

		Node staff = doc.getElementsByTagName("person").item(0);

                Node persons = doc.getFirstChild();
                
                Element newPerson = doc.createElement("person");
                persons.appendChild(newPerson);
                
                Element username = doc.createElement("username");
                username.appendChild(doc.createTextNode(name));
                newPerson.appendChild(username);

                Element score = doc.createElement("lines");
                String s = Integer.toString(lines);
                score.appendChild(doc.createTextNode(s));
                newPerson.appendChild(score);
                
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filepath));
		transformer.transform(source, result);
 
		System.out.println("Done");
 
	   } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	   } catch (TransformerException tfe) {
		tfe.printStackTrace();
	   } catch (IOException ioe) {
		ioe.printStackTrace();
	   } catch (SAXException sae) {
		sae.printStackTrace();
	   }
    }
    
    public static void main(String[] args) {
        new TryAgainTetris();
    }
    
    public void checkIntersection(){
        int numberOfBlocksChecked = 0;
        for(int n = 0; n < blocksArray.length; n++){
            for(int j = 0; j < blocksArray[n].length; j++){
                if(line){
                    if(lineBlock.intersects(blocksArray[n][j])){
                        booleanBlocksArrayEmpty[n][j] = false;
                        colors[n][j] = 1;
                        numberOfBlocksChecked++;
                        if(numberOfBlocksChecked > 3) allHailTetrisGods();
                    }
                }else if(square){
                    if(squareBlock.intersects(blocksArray[n][j])){
                        booleanBlocksArrayEmpty[n][j] = false;
                        colors[n][j] = 2;
                        numberOfBlocksChecked++;
                        if(numberOfBlocksChecked > 3) allHailTetrisGods();
                    }
                }else if(zsquiggle){
                    if(ZBlockArea.intersects(blocksArray[n][j])){
                        booleanBlocksArrayEmpty[n][j] = false;
                        colors[n][j] = 3;
                        numberOfBlocksChecked++;
                        if(numberOfBlocksChecked > 3) allHailTetrisGods();
                    }
                }else if(L){
                    if(LBlockArea.intersects(blocksArray[n][j])){
                        booleanBlocksArrayEmpty[n][j] = false;
                        colors[n][j] = 4;
                        numberOfBlocksChecked++;
                        if(numberOfBlocksChecked > 3) allHailTetrisGods();
                    }
                }else if(backzsquiggle){
                    if(backZBlockArea.intersects(blocksArray[n][j])){
                        booleanBlocksArrayEmpty[n][j] = false;
                        colors[n][j] = 5;
                        numberOfBlocksChecked++;
                        if(numberOfBlocksChecked > 3) allHailTetrisGods();
                    }
                }else if(backlblock){
                    if(backLBlockArea.intersects(blocksArray[n][j])){
                        booleanBlocksArrayEmpty[n][j] = false;
                        colors[n][j] = 6;
                        numberOfBlocksChecked++;
                        if(numberOfBlocksChecked > 3) allHailTetrisGods();
                    }
                }else if(T){
                    if(TBlockArea.intersects(blocksArray[n][j])){
                        booleanBlocksArrayEmpty[n][j] = false;
                        colors[n][j] = 7;
                        numberOfBlocksChecked++;
                        if(numberOfBlocksChecked > 3) allHailTetrisGods();
                    }
                }
            }
        }
        
        if(placeInList>98) preparePieces(placeInList);
    }
    
    public void deleteRow(int n){
        for(int c = n; c > 0; c--){
            for(int j = 0; j < blocksArray[c].length; j++){
                colors[c][j] = colors[c-1][j];
                booleanBlocksArrayEmpty[c][j] = booleanBlocksArrayEmpty[c-1][j];
            }
        }
        numberLines++;
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(zsquiggle) ZBlockArea = new Area(ZBlock);
        else if(L) LBlockArea = new Area(LBlock);
        else if(backzsquiggle) backZBlockArea = new Area(backZBlock);
        else if(backlblock) backLBlockArea = new Area(backLBlock);
        else if(T) TBlockArea = new Area(TBlock);
        
        for(int n = 1; n < blocksArray.length; n ++){
            for(int j = 0; j < blocksArray[n].length; j++){
                if(line){
                    if(lineBlock.checkdy(blocksArray, booleanBlocksArrayEmpty) <= 0) checkIntersection();
                }else if(square){
                    if(squareBlock.checkdy(blocksArray, booleanBlocksArrayEmpty) <= 0) checkIntersection();
                }else if(zsquiggle){
                    if(ZBlock.checkdy(blocksArray, booleanBlocksArrayEmpty) <= 0) checkIntersection();
                }else if(L){
                    if(LBlock.checkdy(blocksArray, booleanBlocksArrayEmpty) <= 0) checkIntersection();
                }else if(backzsquiggle){
                    if(backZBlock.checkdy(blocksArray, booleanBlocksArrayEmpty) <= 0) checkIntersection();
                }else if(backlblock){
                    if(backLBlock.checkdy(blocksArray, booleanBlocksArrayEmpty) <= 0) checkIntersection();
                }else if(T){
                    if(TBlock.checkdy(blocksArray, booleanBlocksArrayEmpty) <= 0) checkIntersection();
                }
            }
        }
                
        if(booleanBlocksArrayEmpty[0][3] == false || booleanBlocksArrayEmpty[0][4] == false || booleanBlocksArrayEmpty[0][5] == false){
            gameOver = true;
            timer.stop();
            lineBlock.gameOver();
            squareBlock.gameOver();
            backLBlock.gameOver();
            backZBlock.gameOver();
            LBlock.gameOver();
            TBlock.gameOver();
            ZBlock.gameOver();
             
            if(numberLines > 0) name = JOptionPane.showInputDialog("Enter Thy Name");
        }
        
        repaint();
    }
    
    
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        switch(keyCode){
            case KeyEvent.VK_LEFT:
                if(line) lineBlock.moveLeft();
                else if(square) squareBlock.moveLeft();
                else if(zsquiggle){
                    ZBlock.moveLeft();
                    ZBlockArea = new Area(ZBlock);
                }else if(L){
                    LBlock.moveLeft();
                    LBlockArea = new Area(LBlock);
                }else if(backzsquiggle){
                    backZBlock.moveLeft();
                    backZBlockArea = new Area(backZBlock);
                }else if(backlblock){
                    backLBlock.moveLeft();
                    backLBlockArea = new Area(backLBlock);
                }else if(T){
                    TBlock.moveLeft();
                    TBlockArea = new Area(TBlock);
                }
                repaint();
                break;
            case KeyEvent.VK_RIGHT:
                if(line) lineBlock.moveRight();
                else if(square) squareBlock.moveRight();
                else if(zsquiggle){
                    ZBlock.moveRight();
                    ZBlockArea = new Area(ZBlock);
                }else if(L){
                    LBlock.moveRight();
                    LBlockArea = new Area(LBlock);
                }else if(backzsquiggle){
                    backZBlock.moveRight();
                    backZBlockArea = new Area(backZBlock);
                }else if(backlblock){
                    backLBlock.moveRight();
                    backLBlockArea = new Area(backLBlock);
                }else if(T){
                    TBlock.moveRight();
                    TBlockArea = new Area(TBlock);
                }
                repaint();
                break;
            case KeyEvent.VK_DOWN:
                if(line) lineBlock.moveDown();
                else if(square) squareBlock.moveDown();
                else if(zsquiggle){
                    ZBlock.moveDown();
                    ZBlockArea = new Area(ZBlock);
                }
                else if(L){
                    LBlock.moveDown();
                    ZBlockArea = new Area(ZBlock);
                }else if(backzsquiggle){
                    backZBlock.moveDown();
                    backZBlockArea = new Area(backZBlock);
                }else if(backlblock){
                    backLBlock.moveDown();
                    backLBlockArea = new Area(backLBlock);
                }else if(T){
                    TBlock.moveDown();
                    TBlockArea = new Area(TBlock);
                }
                repaint();
                break;
            case KeyEvent.VK_UP:
                if(line) lineBlock.rotateARGH(lineBlock.width, lineBlock.height);
                else if(zsquiggle){
                    ZBlock.rotateARGH();
                    ZBlockArea = new Area(ZBlock);
                }else if(L){
                    LBlock.rotateARGH();
                    LBlockArea = new Area(LBlock);
                }else if(backzsquiggle){
                    backZBlock.rotateARGH();
                    backZBlockArea = new Area(backZBlock);
                }else if(backlblock){
                    backLBlock.rotateARGH();
                    backZBlockArea = new Area(backLBlock);
                }else if(T){
                    TBlock.rotateARGH();
                    TBlockArea = new Area(TBlock);
                }
                repaint();
                break;
            case KeyEvent.VK_SPACE:
                if(line) lineBlock.hardDrop();
                else if(square) squareBlock.hardDrop();
                else if(zsquiggle){
                    ZBlock.hardDrop();
                    ZBlockArea = new Area(ZBlock);
                }else if(L){
                    LBlock.hardDrop();
                    LBlockArea = new Area(LBlock);
                }else if(backzsquiggle){
                    backZBlock.hardDrop();
                    backZBlockArea = new Area(backZBlock);
                }else if(backlblock){
                    backLBlock.hardDrop();
                    backLBlockArea = new Area(backLBlock);
                }else if(T){
                    TBlock.hardDrop();
                    TBlockArea = new Area(TBlock);
                }
                repaint();
                break;
            case KeyEvent.VK_P:
                if(!gameOver){
                    p++;
                    if(p%2 == 1 && p > 0) pause = true;
                    else if (p%2 == 0 && p > 0) pause = false;
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

}
