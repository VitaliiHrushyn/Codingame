package com.tasks3.carddeck;

import java.util.Arrays;

public class Deck {
	
	public Card[] cards = new Card[36];
	
	
	public Deck () {
		int i =0;
		for (int j = 0; j < Suit.values.length; j++) {
			for (int k = 0; k < Rank.values.length; k ++) {
				cards[i++] = new Card(Rank.values[k], Suit.values[j]);
			}
		}
	}
    
    //Перемішує колоду у випадковому порядку 
    public void shuffle() {
    	Card[] newCards = new Card[36];
    	for (int i = 0; i < cards.length; i++) {
			newCards[i] = this.drawOne();			
		}
    	cards = newCards;
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
    	
    }
    
    
    //Повертає true у випадку коли в колоді ще доступні карти
    public boolean hasNext() {
    	for (int i = 35; i > -1; i--) {
			if (cards[i] != null) {
				return true;
			}
    	}
    	return false;
    }
    
    //"Виймає" одну карту з колоди, коли буде видано всі 36 карт повертає null
    //Карти виймаються з "вершини" колоди. Наприклад перший виклик видасть SPADES 6 потім
    //SPADES 7, ..., CLUBS 6, ..., CLUBS Ace і так далі до HEARTS Ace
    public Card drawOne() {
    	Card card = null;
    	for (int i = 35; i > -1; i--) {
			if (cards[i] != null) {
				card = cards[i];
				cards[i] = null;
				return card;
			}
		}
    	return card;
    }

	@Override
	public String toString() {
		return "Deck [cards=" + Arrays.toString(cards) + "]";
	} 
    
}  
      