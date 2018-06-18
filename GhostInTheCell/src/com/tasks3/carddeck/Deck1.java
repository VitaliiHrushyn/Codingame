package com.tasks3.carddeck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck1 {
	
	public List<Card> cards = new ArrayList<>();
	private Random random = new Random();
	
	public Deck1 () {
	
		for (int j = 0; j < Suit.values.length; j++) {
			for (int k = 0; k < Rank.values.length; k ++) {
				cards.add(new Card(Rank.values[k], Suit.values[j]));
			}
		}
	}
    
    //Перемішує колоду у випадковому порядку 
    public void shuffle() {	
    	List<Card> shuffledCards = new ArrayList<>();    	
    	while (cards.size() > 0) {
			shuffledCards.add(cards.remove(random.nextInt(cards.size())));
		}
    	cards = shuffledCards;
    	
    	
    }
    /* * Впорядкування колоди за мастями та значеннями 
    * Порядок сотрування: 
    * Спочатку всі карти з мастю HEARTS, потім DIAMONDS, CLUBS, SPADES 
    * для кожної масті порядок наступний: Ace,King,Queen,Jack,10,9,8,7,6 
    * Наприклад 
    * HEARTS Ace 
    * HEARTS King 
    * HEARTS Queen 
    * HEARTS Jack 
    * HEARTS 10 
    * HEARTS 9 
    * HEARTS 8 
    * HEARTS 7 
    * HEARTS 6 
    * І так далі для DIAMONDS, CLUBS, SPADES */
    public void order() {
    //	cards.sort(null);
    }
    
    //Повертає true у випадку коли в колоді ще доступні карти
    public boolean hasNext() {
    	if (cards.size() > 0) {
    		return true;
    	} else {
    		return false;
    	}    	
    }
    
    //"Виймає" одну карту з колоди, коли буде видано всі 36 карт повертає null
    //Карти виймаються з "вершини" колоди. Наприклад перший виклик видасть SPADES 6 потім
    //SPADES 7, ..., CLUBS 6, ..., CLUBS Ace і так далі до HEARTS Ace
    public Card drawOne() {
    	return cards.remove(cards.size() - 1);		
    }

	@Override
	public String toString() {
		return "Deck [cards=" + cards.toString() + "]";
	} 
    
}  
      