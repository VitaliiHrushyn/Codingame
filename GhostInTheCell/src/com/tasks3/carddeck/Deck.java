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
    
    //������� ������ � ����������� ������� 
    public void shuffle() {
    	Card[] newCards = new Card[36];
    	for (int i = 0; i < cards.length; i++) {
			newCards[i] = this.drawOne();			
		}
    	cards = newCards;
    }
    /* * ������������� ������ �� ������� �� ���������� 
    * ������� ����������: 
    * �������� �� ����� � ����� HEARTS, ���� DIAMONDS, CLUBS, SPADES 
    * ��� ����� ���� ������� ���������: Ace,King,Queen,Jack,10,9,8,7,6 
    * ��������� 
    * HEARTS Ace 
    * HEARTS King 
    * HEARTS Queen 
    * HEARTS Jack 
    * HEARTS 10 
    * HEARTS 9 
    * HEARTS 8 
    * HEARTS 7 
    * HEARTS 6 
    * � ��� ��� ��� DIAMONDS, CLUBS, SPADES */
    public void order() {
    	
    }
    
    
    //������� true � ������� ���� � ����� �� ������� �����
    public boolean hasNext() {
    	for (int i = 35; i > -1; i--) {
			if (cards[i] != null) {
				return true;
			}
    	}
    	return false;
    }
    
    //"�����" ���� ����� � ������, ���� ���� ������ �� 36 ���� ������� null
    //����� ���������� � "�������" ������. ��������� ������ ������ ������� SPADES 6 ����
    //SPADES 7, ..., CLUBS 6, ..., CLUBS Ace � ��� ��� �� HEARTS Ace
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
      