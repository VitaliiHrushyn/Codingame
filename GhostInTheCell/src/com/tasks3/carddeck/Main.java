package com.tasks3.carddeck;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Deck1 deck = new Deck1();
		
		System.out.println(deck.toString());
		System.out.println("draw one: "+deck.drawOne());
		System.out.println(deck.toString());
		
		deck.shuffle();
		System.out.println("after shuffle "+deck.toString());
		System.out.println("draw one: "+deck.drawOne());		
		deck.order();
		
		
		System.out.println("after order "+deck.toString());
		

	}

}
