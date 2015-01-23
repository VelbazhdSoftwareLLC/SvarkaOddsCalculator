package eu.veldsoft.svarka.odds.calculator;

class Constants {
	static final int SCORE_MASK = 0x00F;
	static final int KIND_MASK = 0x0F0;
	static final int SUIT_MASK = 0xF00;

	static final int CARD_SCORE_YONCHEV = 11;

	static final int CARD_SCORE_SEVEN = 7;
	static final int CARD_SCORE_EIGHT = 8;
	static final int CARD_SCORE_NINE = 9;
	static final int CARD_SCORE_TEN = 10;
	static final int CARD_SCORE_JACK = 10;
	static final int CARD_SCORE_QUEEN = 10;
	static final int CARD_SCORE_KING = 10;
	static final int CARD_SCORE_ACE = 11;

	static final int CARD_KIND_SEVEN = 1 << 4;
	static final int CARD_KIND_EIGHT = 2 << 4;
	static final int CARD_KIND_NINE = 3 << 4;
	static final int CARD_KIND_TEN = 4 << 4;
	static final int CARD_KIND_JACK = 5 << 4;
	static final int CARD_KIND_QUEEN = 6 << 4;
	static final int CARD_KIND_KING = 7 << 4;
	static final int CARD_KIND_ACE = 8 << 4;

	static final int CARD_SUIT_CLUBS = 1 << 8;
	static final int CARD_SUIT_DIAMONDS = 2 << 8;
	static final int CARD_SUIT_HEARTS = 3 << 8;
	static final int CARD_SUIT_SPADES = 4 << 8;
}
