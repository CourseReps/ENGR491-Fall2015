package edu.tamu.cardsofchaos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

// Performs file read/write operations on the app's default internal memory app directory files 
public class GameFileSystem {
	
	Context context;
	
	public GameFileSystem(Context the_context)
	{
		context = the_context;
		getDirectoryFileList();
	}
	
	// Overwrites the file in default internal memory private app directory with string_to_write
	public void overwriteToFile(String filename, String string_to_write)
	{
		try {
			File myFile = new File(context.getFilesDir(), filename);
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(string_to_write);
			myOutWriter.close();
			fOut.close();		
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// returns a list of filenames valid in the context.getFilesDir()
	public Vector<String> getDirectoryFileList()
	{
		Vector<String> result = new Vector<String>();
		
		File directory = new File(context.getFilesDir().getAbsolutePath());
		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) 
		{
			if (file.isFile()) result.add(file.getName());
		}
		return result;
	}
	
	// Reads this file from the default internal memory private app directory and returns a Vector of Strings of the lines in the file
	public Vector<String> readFileLines(String filename)
	{
		Vector<String> result = new Vector<String>();
		try {
			
			FileInputStream fis = context.openFileInput(filename);
			InputStreamReader in = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(in);
			String line = br.readLine();
			while (line != null) { result.add(line); line = br.readLine(); }
			br.close();
			in.close();
			fis.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// Reads the entire file into a string lol. The max_width trims individual lines
	public String readFileLines(String filename, int max_width)
	{
		Vector<String> vec_result = new Vector<String>();
		try {
			
			FileInputStream fis = context.openFileInput(filename);
			InputStreamReader in = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(in);
			String line = br.readLine();
			while (line != null) { vec_result.add(line); line = br.readLine(); }
			br.close();
			in.close();
			fis.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < vec_result.size(); i++) if (vec_result.elementAt(i).length() > max_width) vec_result.set(i, vec_result.elementAt(i).substring(0, max_width) + "..."); 
		String result = "";
		for (int i = 0; i < vec_result.size(); i++) result += vec_result.elementAt(i) + "\n";		
		return result;
		
	}
	
	// Tries to write the GameState as an XML to the internal memory
	// A copy is placed on SD, if available
	// returns true if successful
	public boolean writeGameState(String filename, GameState the_game_state)
	{
		boolean was_successful = false;
		try {
			// These classes are used as an interface to XML formatting
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
			
			
			// Define elements and their attributes. This is where the tree of the document is built
			// An Element can be though of as a category
			// An Attr is a named piece of data within this category
			// Elements can be nodes of other Elements
			// Attrs can be nodes of Elements
			Element rootElement = document.createElement("GameState"); document.appendChild(rootElement);
			Element aiCardDeck = document.createElement("AICardDeck"); rootElement.appendChild(aiCardDeck);
			for (int i = 0; i < the_game_state.AI_card_deck.size(); i++) { Attr attr = document.createAttribute("GameCard" + String.valueOf(i)); attr.setValue(the_game_state.AI_card_deck.elementAt(i).toString()); aiCardDeck.setAttributeNode(attr); }
			Element aiCardHand = document.createElement("AICardHand"); rootElement.appendChild(aiCardHand);
			for (int i = 0; i < the_game_state.AI_card_hand.size(); i++) { Attr attr = document.createAttribute("GameCard" + String.valueOf(i)); attr.setValue(the_game_state.AI_card_hand.elementAt(i).toString()); aiCardHand.setAttributeNode(attr); }
			Attr attrGameDifficulty = document.createAttribute("GameDifficulty"); attrGameDifficulty.setValue(the_game_state.game_difficulty.name()); rootElement.setAttributeNode(attrGameDifficulty);
			Element userCardDeck = document.createElement("UserCardDeck"); rootElement.appendChild(userCardDeck);
			for (int i = 0; i < the_game_state.user_card_deck.size(); i++) { Attr attr = document.createAttribute("GameCard" + String.valueOf(i)); attr.setValue(the_game_state.user_card_deck.elementAt(i).toString()); userCardDeck.setAttributeNode(attr); }
			Element gameRules = document.createElement("GameRules"); rootElement.appendChild(gameRules);
			for (int i = 0; i < the_game_state.game_rules.size(); i++) { Attr attr = document.createAttribute("GameRule" + String.valueOf(i)); attr.setValue(the_game_state.game_rules.elementAt(i).toString()); gameRules.setAttributeNode(attr); }
			Element userCardHand = document.createElement("UserCardHand"); rootElement.appendChild(userCardHand);
			Attr attrUserCardA = document.createAttribute("UserCardA"); attrUserCardA.setValue(the_game_state.user_card_a.toString()); userCardHand.setAttributeNode(attrUserCardA);
			Attr attrUserCardB = document.createAttribute("UserCardB"); attrUserCardB.setValue(the_game_state.user_card_b.toString()); userCardHand.setAttributeNode(attrUserCardB);
			Attr attrUserCardC = document.createAttribute("UserCardC"); attrUserCardC.setValue(the_game_state.user_card_c.toString()); userCardHand.setAttributeNode(attrUserCardC);
			Attr attrUserCardD = document.createAttribute("UserCardD"); attrUserCardD.setValue(the_game_state.user_card_d.toString()); userCardHand.setAttributeNode(attrUserCardD);
			Attr attrUserCardE = document.createAttribute("UserCardE"); attrUserCardE.setValue(the_game_state.user_card_e.toString()); userCardHand.setAttributeNode(attrUserCardE);
			Element userCardHandAvailable = document.createElement("UserCardHandAvailable"); rootElement.appendChild(userCardHandAvailable);
			Attr attrUserCardAvailableA = document.createAttribute("UserCardAvailableA"); attrUserCardAvailableA.setValue(String.valueOf(the_game_state.cardAvailableA)); userCardHandAvailable.setAttributeNode(attrUserCardAvailableA);
			Attr attrUserCardAvailableB = document.createAttribute("UserCardAvailableB"); attrUserCardAvailableB.setValue(String.valueOf(the_game_state.cardAvailableB)); userCardHandAvailable.setAttributeNode(attrUserCardAvailableB);
			Attr attrUserCardAvailableC = document.createAttribute("UserCardAvailableC"); attrUserCardAvailableC.setValue(String.valueOf(the_game_state.cardAvailableC)); userCardHandAvailable.setAttributeNode(attrUserCardAvailableC);
			Attr attrUserCardAvailableD = document.createAttribute("UserCardAvailableD"); attrUserCardAvailableD.setValue(String.valueOf(the_game_state.cardAvailableD)); userCardHandAvailable.setAttributeNode(attrUserCardAvailableD);
			Attr attrUserCardAvailableE = document.createAttribute("UserCardAvailableE"); attrUserCardAvailableE.setValue(String.valueOf(the_game_state.cardAvailableE)); userCardHandAvailable.setAttributeNode(attrUserCardAvailableE);
			Element playedCards = document.createElement("PlayedCards"); rootElement.appendChild(playedCards);
			Attr attrPlayedCardUser = document.createAttribute("PlayedCardUser"); attrPlayedCardUser.setValue(the_game_state.played_card_user.toString()); playedCards.setAttributeNode(attrPlayedCardUser);
			Attr attrPlayedCardAI = document.createAttribute("PlayedCardAI"); attrPlayedCardAI.setValue(the_game_state.played_card_AI.toString()); playedCards.setAttributeNode(attrPlayedCardAI);
			Element playedCardsAvailable = document.createElement("PlayedCardsAvailable"); rootElement.appendChild(playedCardsAvailable);
			Attr attrPlayedCardAvailableUser = document.createAttribute("PlayedCardAvailableUser"); attrPlayedCardAvailableUser.setValue(String.valueOf(the_game_state.cardAvailablePlayedCardUser)); playedCardsAvailable.setAttributeNode(attrPlayedCardAvailableUser);
			Attr attrPlayedCardAvailableAI = document.createAttribute("PlayedCardAvailableAI"); attrPlayedCardAvailableAI.setValue(String.valueOf(the_game_state.cardAvailablePlayedCardAI)); playedCardsAvailable.setAttributeNode(attrPlayedCardAvailableAI);
			Element scores = document.createElement("Scores"); rootElement.appendChild(scores);
			Attr attrScoreUser = document.createAttribute("ScoreUser"); attrScoreUser.setValue(String.valueOf(the_game_state.score_user)); scores.setAttributeNode(attrScoreUser);
			Attr attrScoreAI = document.createAttribute("ScoreAI"); attrScoreAI.setValue(String.valueOf(the_game_state.score_AI)); scores.setAttributeNode(attrScoreAI);
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(context.getFilesDir(), filename));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);		
			was_successful = true;
		
			try { // Also try to write a copy to SD
				File[] dirs = context.getExternalFilesDirs(Environment.DIRECTORY_DOCUMENTS);
				Vector<String> directories = new Vector<String>();
				for (int i = 0; i < dirs.length; i++) { directories.add(dirs[i].getAbsolutePath());}
				CharSequence sdcard_cs = "sdcard";
				String filepath = null;
				for (int i = 0; i < directories.size(); i++) if (directories.elementAt(i).contains(sdcard_cs)) filepath = directories.elementAt(i);
				
				if (filepath != null)
				{
					StreamResult SD_result = new StreamResult(new File(filepath, filename));
					transformer.transform(source, SD_result);
				}
			}
			
			catch(Exception e) {
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return was_successful;		
	}
	
	// returns null if unsuccessful
	public GameState readGameState(String filename)
	{
		GameState result = new GameState();
		try {
			// Prep files and helper classes
			File fXmlFile = new File(context.getFilesDir(), filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			// Fill a new GameState
			result.game_difficulty = GameEnums.DIFFICULTY_TYPE.valueOf(doc.getElementsByTagName("GameState").item(0).getAttributes().getNamedItem("GameDifficulty").getTextContent());
	 		// Read in AI Card Deck
			for (int card_index = 0;; card_index++) { org.w3c.dom.Node game_card_node = doc.getElementsByTagName("AICardDeck").item(0).getAttributes().getNamedItem("GameCard" + String.valueOf(card_index));  if (game_card_node != null) result.AI_card_deck.add(GameCard.fromString(game_card_node.getTextContent())); else break; } 
			// Read in AI Card Hand
			for (int card_index = 0;; card_index++) { org.w3c.dom.Node game_card_node = doc.getElementsByTagName("AICardHand").item(0).getAttributes().getNamedItem("GameCard" + String.valueOf(card_index));  if (game_card_node != null) result.AI_card_hand.add(GameCard.fromString(game_card_node.getTextContent())); else break; }
			// Read in User Card Hand
			for (int card_index = 0;; card_index++) { org.w3c.dom.Node game_card_node = doc.getElementsByTagName("UserCardDeck").item(0).getAttributes().getNamedItem("GameCard" + String.valueOf(card_index));  if (game_card_node != null) result.user_card_deck.add(GameCard.fromString(game_card_node.getTextContent())); else break; }
			// Read in Rules Vector
			for (int card_index = 0;; card_index++) { org.w3c.dom.Node game_card_node = doc.getElementsByTagName("GameRules").item(0).getAttributes().getNamedItem("GameRule" + String.valueOf(card_index));  if (game_card_node != null) result.game_rules.add(GameRule.fromString(game_card_node.getTextContent())); else break; }
			// Read in User Card Hand
			result.user_card_a = GameCard.fromString(doc.getElementsByTagName("UserCardHand").item(0).getAttributes().getNamedItem("UserCardA").getTextContent());
			result.user_card_b = GameCard.fromString(doc.getElementsByTagName("UserCardHand").item(0).getAttributes().getNamedItem("UserCardB").getTextContent());
			result.user_card_c = GameCard.fromString(doc.getElementsByTagName("UserCardHand").item(0).getAttributes().getNamedItem("UserCardC").getTextContent());
			result.user_card_d = GameCard.fromString(doc.getElementsByTagName("UserCardHand").item(0).getAttributes().getNamedItem("UserCardD").getTextContent());
			result.user_card_e = GameCard.fromString(doc.getElementsByTagName("UserCardHand").item(0).getAttributes().getNamedItem("UserCardE").getTextContent());
			// Read in User Card Available
			result.cardAvailableA = Boolean.parseBoolean(doc.getElementsByTagName("UserCardHandAvailable").item(0).getAttributes().getNamedItem("UserCardAvailableA").getTextContent());
			result.cardAvailableB = Boolean.parseBoolean(doc.getElementsByTagName("UserCardHandAvailable").item(0).getAttributes().getNamedItem("UserCardAvailableB").getTextContent());
			result.cardAvailableC = Boolean.parseBoolean(doc.getElementsByTagName("UserCardHandAvailable").item(0).getAttributes().getNamedItem("UserCardAvailableC").getTextContent());
			result.cardAvailableD = Boolean.parseBoolean(doc.getElementsByTagName("UserCardHandAvailable").item(0).getAttributes().getNamedItem("UserCardAvailableD").getTextContent());
			result.cardAvailableE = Boolean.parseBoolean(doc.getElementsByTagName("UserCardHandAvailable").item(0).getAttributes().getNamedItem("UserCardAvailableE").getTextContent());
			// Read in cards most recently played; i.e. on the table
			result.played_card_AI = GameCard.fromString(doc.getElementsByTagName("PlayedCards").item(0).getAttributes().getNamedItem("PlayedCardAI").getTextContent());
			result.played_card_user = GameCard.fromString(doc.getElementsByTagName("PlayedCards").item(0).getAttributes().getNamedItem("PlayedCardUser").getTextContent());
			// Read in whether cards most recentliy played are visible; i.e. available
			result.cardAvailablePlayedCardAI = Boolean.parseBoolean(doc.getElementsByTagName("PlayedCardsAvailable").item(0).getAttributes().getNamedItem("PlayedCardAvailableAI").getTextContent());
			result.cardAvailablePlayedCardUser = Boolean.parseBoolean(doc.getElementsByTagName("PlayedCardsAvailable").item(0).getAttributes().getNamedItem("PlayedCardAvailableUser").getTextContent());
			// Read in scores
			result.score_AI = Integer.parseInt(doc.getElementsByTagName("Scores").item(0).getAttributes().getNamedItem("ScoreAI").getTextContent());
			result.score_user = Integer.parseInt(doc.getElementsByTagName("Scores").item(0).getAttributes().getNamedItem("ScoreUser").getTextContent());
			// All done!
		} 
		catch (Exception e) {
			result = null;
		}
		return result;		
	}
}
