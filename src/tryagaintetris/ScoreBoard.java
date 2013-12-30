package tryagaintetris;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ScoreBoard extends JPanel{
        
    private String name;
    private int score;
    private int x = 25, y = 50;
    
    public ScoreBoard(String n, int s){
//        JFrame frame = new JFrame("Scores");
//        frame.setSize(470, 300);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.add(this);
//        frame.setVisible(true);
        
        if(s > 0) addNewScore(n, s);
        
        try{
            File xmlFile = new File("scores.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            
            doc.getDocumentElement().normalize();
            
            NodeList list = doc.getElementsByTagName("person");
            
            for(int i = 0; i < list.getLength(); i++){
                Node currentNode = list.item(i);
                
                if(currentNode.getNodeType() == Node.ELEMENT_NODE){
                    Element e = (Element)currentNode;

                    System.out.println(e.getElementsByTagName("username").item(0).getTextContent() + 
                            "     " + e.getElementsByTagName("lines").item(0).getTextContent());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
    public void addNewScore(String name, int lines){
        try{
            String filepath = "c:\\scores.xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            
            Node scores = doc.getFirstChild();
            NodeList list = scores.getChildNodes();
            System.out.println(list.getLength());
            Node person = doc.getElementsByTagName("person").item(list.getLength()-((2/3)*list.getLength()));
            
            
            Element newPerson = doc.createElement("person");
            scores.appendChild(newPerson);
            
            Element username = doc.createElement("username");
            username.appendChild(doc.createTextNode(name));
            newPerson.appendChild(username);
            
            Element score = doc.createElement("lines");
            String s = Integer.toString(lines);
            score.appendChild(doc.createTextNode(s));
            newPerson.appendChild(score);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            
        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
        }catch(TransformerException tfe){
		tfe.printStackTrace();
	}catch(IOException ioe){
            ioe.printStackTrace();
        }catch (SAXException sae){
            sae.printStackTrace();
        }
    }
    
}
