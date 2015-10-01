package edu.tamu.cardsofchaos;
import java.util.Random;
import java.util.Vector;


// a container for data used in GameMechanics
public class GameState {
	// For inside OpponentAI
	public Vector<GameCard> AI_card_deck;
	public Vector<GameCard> AI_card_hand;
	
	public GameEnums.DIFFICULTY_TYPE game_difficulty;
	public Vector<GameCard> user_card_deck;
	public Vector<GameRule> game_rules;
	
	public GameCard user_card_a;
	public GameCard user_card_b;
	public GameCard user_card_c;
	public GameCard user_card_d;
	public GameCard user_card_e;
	public boolean cardAvailableA;
	public boolean cardAvailableB;
	public boolean cardAvailableC;
	public boolean cardAvailableD;
	public boolean cardAvailableE;
	
	public GameCard played_card_user;
	public GameCard played_card_AI;
	public boolean cardAvailablePlayedCardUser;
	public boolean cardAvailablePlayedCardAI;
	
	public int score_user;
	public int score_AI;
	
	public GameState() 
	{
		AI_card_deck = new Vector<GameCard>();
		AI_card_hand = new Vector<GameCard>();
		
		user_card_deck = new Vector<GameCard>();;
		game_rules = new Vector<GameRule>();;
	}
}