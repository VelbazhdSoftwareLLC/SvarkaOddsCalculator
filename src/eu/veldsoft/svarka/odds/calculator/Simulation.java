package eu.veldsoft.svarka.odds.calculator;

import java.util.Arrays;

class Simulation {

	private enum Outcome {
		WIN, SVARKA, LOSE
	}

	static private int YONCHEV = Constants.CARD_SUIT_CLUBS
			| Constants.CARD_KIND_SEVEN | Constants.CARD_SCORE_SEVEN;

	private int deck[] = {
			Constants.CARD_SUIT_CLUBS | Constants.CARD_KIND_SEVEN
					| Constants.CARD_SCORE_SEVEN,
			Constants.CARD_SUIT_CLUBS | Constants.CARD_KIND_EIGHT
					| Constants.CARD_SCORE_EIGHT,
			Constants.CARD_SUIT_CLUBS | Constants.CARD_KIND_NINE
					| Constants.CARD_SCORE_NINE,
			Constants.CARD_SUIT_CLUBS | Constants.CARD_KIND_TEN
					| Constants.CARD_SCORE_TEN,
			Constants.CARD_SUIT_CLUBS | Constants.CARD_KIND_JACK
					| Constants.CARD_SCORE_JACK,
			Constants.CARD_SUIT_CLUBS | Constants.CARD_KIND_QUEEN
					| Constants.CARD_SCORE_QUEEN,
			Constants.CARD_SUIT_CLUBS | Constants.CARD_KIND_KING
					| Constants.CARD_SCORE_KING,
			Constants.CARD_SUIT_CLUBS | Constants.CARD_KIND_ACE
					| Constants.CARD_SCORE_ACE,

			Constants.CARD_SUIT_DIAMONDS | Constants.CARD_KIND_SEVEN
					| Constants.CARD_SCORE_SEVEN,
			Constants.CARD_SUIT_DIAMONDS | Constants.CARD_KIND_EIGHT
					| Constants.CARD_SCORE_EIGHT,
			Constants.CARD_SUIT_DIAMONDS | Constants.CARD_KIND_NINE
					| Constants.CARD_SCORE_NINE,
			Constants.CARD_SUIT_DIAMONDS | Constants.CARD_KIND_TEN
					| Constants.CARD_SCORE_TEN,
			Constants.CARD_SUIT_DIAMONDS | Constants.CARD_KIND_JACK
					| Constants.CARD_SCORE_JACK,
			Constants.CARD_SUIT_DIAMONDS | Constants.CARD_KIND_QUEEN
					| Constants.CARD_SCORE_QUEEN,
			Constants.CARD_SUIT_DIAMONDS | Constants.CARD_KIND_KING
					| Constants.CARD_SCORE_KING,
			Constants.CARD_SUIT_DIAMONDS | Constants.CARD_KIND_ACE
					| Constants.CARD_SCORE_ACE,

			Constants.CARD_SUIT_HEARTS | Constants.CARD_KIND_SEVEN
					| Constants.CARD_SCORE_SEVEN,
			Constants.CARD_SUIT_HEARTS | Constants.CARD_KIND_EIGHT
					| Constants.CARD_SCORE_EIGHT,
			Constants.CARD_SUIT_HEARTS | Constants.CARD_KIND_NINE
					| Constants.CARD_SCORE_NINE,
			Constants.CARD_SUIT_HEARTS | Constants.CARD_KIND_TEN
					| Constants.CARD_SCORE_TEN,
			Constants.CARD_SUIT_HEARTS | Constants.CARD_KIND_JACK
					| Constants.CARD_SCORE_JACK,
			Constants.CARD_SUIT_HEARTS | Constants.CARD_KIND_QUEEN
					| Constants.CARD_SCORE_QUEEN,
			Constants.CARD_SUIT_HEARTS | Constants.CARD_KIND_KING
					| Constants.CARD_SCORE_KING,
			Constants.CARD_SUIT_HEARTS | Constants.CARD_KIND_ACE
					| Constants.CARD_SCORE_ACE,

			Constants.CARD_SUIT_SPADES | Constants.CARD_KIND_SEVEN
					| Constants.CARD_SCORE_SEVEN,
			Constants.CARD_SUIT_SPADES | Constants.CARD_KIND_EIGHT
					| Constants.CARD_SCORE_EIGHT,
			Constants.CARD_SUIT_SPADES | Constants.CARD_KIND_NINE
					| Constants.CARD_SCORE_NINE,
			Constants.CARD_SUIT_SPADES | Constants.CARD_KIND_TEN
					| Constants.CARD_SCORE_TEN,
			Constants.CARD_SUIT_SPADES | Constants.CARD_KIND_JACK
					| Constants.CARD_SCORE_JACK,
			Constants.CARD_SUIT_SPADES | Constants.CARD_KIND_QUEEN
					| Constants.CARD_SCORE_QUEEN,
			Constants.CARD_SUIT_SPADES | Constants.CARD_KIND_KING
					| Constants.CARD_SCORE_KING,
			Constants.CARD_SUIT_SPADES | Constants.CARD_KIND_ACE
					| Constants.CARD_SCORE_ACE, };

	private int score = 0;

	private int opponent = 0;

	private Outcome outcome = Outcome.WIN;

	private long win = 0;

	private long lose = 0;

	private long svarka = 0;

	private long count = 0;

	private int numberOfPlayers = 0;

	private void fullShuffle() {
		for (int last = deck.length - 1, r = -1, swap = -1; last > 0; last--) {
			r = Util.PRNG.nextInt(last + 1);
			swap = deck[last];
			deck[last] = deck[r];
			deck[r] = swap;
		}
	}

	private void partialShuffle() {
		for (int last = deck.length - 1, r = -1, swap = -1; last > 2; last--) {
			r = 3 + Util.PRNG.nextInt(last - 2);
			swap = deck[last];
			deck[last] = deck[r];
			deck[r] = swap;
		}
	}

	private void partialSort() {
		for (int p = 0, swap; p < numberOfPlayers * 3; p += 3) {
			if (deck[p] > deck[p + 1]) {
				swap = deck[p + 1];
				deck[p + 1] = deck[p];
				deck[p] = swap;
			}
			if (deck[p] > deck[p + 2]) {
				swap = deck[p + 2];
				deck[p + 2] = deck[p];
				deck[p] = swap;
			}
			if (deck[p + 1] > deck[p + 2]) {
				swap = deck[p + 2];
				deck[p + 2] = deck[p + 1];
				deck[p + 1] = swap;
			}
		}
	}

	private void pullPlayersCardsInfront(Integer... hand) {
		int j = 0;
		for (Integer card : hand) {
			for (int i = j; i < deck.length; i++) {
				if (card == deck[i]) {
					int swap = deck[i];
					deck[i] = deck[j];
					deck[j] = swap;

					j++;
					break;
				}
			}
		}
	}

	private boolean hasYonchev(int p) {
		if (deck[p] == YONCHEV) {
			return true;
		}
		if (deck[p + 1] == YONCHEV) {
			return true;
		}
		if (deck[p + 2] == YONCHEV) {
			return true;
		}

		return false;
	}

	private int threeOfKind(int p) {
		if ((deck[p] & Constants.KIND_MASK) == (deck[p + 1] & Constants.KIND_MASK)
				&& (deck[p] & Constants.KIND_MASK) == (deck[p + 2] & Constants.KIND_MASK)) {
			return (3 * (deck[p] & Constants.SCORE_MASK))
					+ (((deck[p] & Constants.KIND_MASK) == Constants.CARD_KIND_SEVEN) ? 13
							: 0);
		}

		return 0;
	}

	private int allSameSuit(int p) {
		if ((deck[p] & Constants.SUIT_MASK) == (deck[p + 1] & Constants.SUIT_MASK)
				&& (deck[p] & Constants.SUIT_MASK) == (deck[p + 2] & Constants.SUIT_MASK)) {
			return (deck[p] & Constants.SCORE_MASK)
					+ (deck[p + 1] & Constants.SCORE_MASK)
					+ (deck[p + 2] & Constants.SCORE_MASK);
		}

		return 0;
	}

	private int twoAces(int p) {
		if (((deck[p] & Constants.KIND_MASK) == Constants.CARD_KIND_ACE)
				&& ((deck[p + 1] & Constants.KIND_MASK) == Constants.CARD_KIND_ACE)
				&& ((deck[p + 2] & Constants.KIND_MASK) != Constants.CARD_KIND_ACE)

		) {
			return 2 * (deck[p] & Constants.SCORE_MASK);
		}

		if (((deck[p] & Constants.KIND_MASK) == Constants.CARD_KIND_ACE)
				&& ((deck[p + 2] & Constants.KIND_MASK) == Constants.CARD_KIND_ACE)
				&& ((deck[p + 1] & Constants.KIND_MASK) != Constants.CARD_KIND_ACE)

		) {
			return 2 * (deck[p] & Constants.SCORE_MASK);
		}

		if (((deck[p + 1] & Constants.KIND_MASK) == Constants.CARD_KIND_ACE)
				&& ((deck[p + 2] & Constants.KIND_MASK) == Constants.CARD_KIND_ACE)
				&& ((deck[p] & Constants.KIND_MASK) != Constants.CARD_KIND_ACE)

		) {
			return 2 * (deck[p + 1] & Constants.SCORE_MASK);
		}

		return 0;
	}

	private int twoOfSuit(int p) {
		if ((deck[p] & Constants.SUIT_MASK) == (deck[p + 1] & Constants.SUIT_MASK)
				&& (deck[p] & Constants.SUIT_MASK) != (deck[p + 2] & Constants.SUIT_MASK)) {
			return (deck[p] & Constants.SCORE_MASK)
					+ (deck[p + 1] & Constants.SCORE_MASK);
		}

		if ((deck[p] & Constants.SUIT_MASK) != (deck[p + 1] & Constants.SUIT_MASK)
				&& (deck[p] & Constants.SUIT_MASK) == (deck[p + 2] & Constants.SUIT_MASK)) {
			return (deck[p] & Constants.SCORE_MASK)
					+ (deck[p + 2] & Constants.SCORE_MASK);
		}

		if ((deck[p] & Constants.SUIT_MASK) != (deck[p + 1] & Constants.SUIT_MASK)
				&& (deck[p + 1] & Constants.SUIT_MASK) == (deck[p + 2] & Constants.SUIT_MASK)) {
			return (deck[p + 1] & Constants.SCORE_MASK)
					+ (deck[p + 2] & Constants.SCORE_MASK);
		}

		return 0;
	}

	private int singleStrongest(int p) {
		if ((deck[p] & Constants.SCORE_MASK) >= (deck[p + 1] & Constants.SCORE_MASK)) {
			if ((deck[p] & Constants.SCORE_MASK) >= (deck[p + 2] & Constants.SCORE_MASK)) {
				return (deck[p] & Constants.SCORE_MASK);
			} else {
				return (deck[p + 2] & Constants.SCORE_MASK);
			}
		} else {
			if ((deck[p + 1] & Constants.SCORE_MASK) >= (deck[p + 2] & Constants.SCORE_MASK)) {
				return (deck[p + 1] & Constants.SCORE_MASK);
			} else {
				return (deck[p + 2] & Constants.SCORE_MASK);
			}
		}
	}

	private int yonchevSingleStrongest(int p) {
		if ((deck[p + 1] & Constants.SCORE_MASK) >= (deck[p + 2] & Constants.SCORE_MASK)) {
			return (deck[p + 1] & Constants.SCORE_MASK)
					+ Constants.CARD_SCORE_YONCHEV;
		} else {
			return (deck[p + 2] & Constants.SCORE_MASK)
					+ Constants.CARD_SCORE_YONCHEV;
		}
	}

	private int yonchevAllSameSuit(int p) {
		if ((deck[p + 1] & Constants.SUIT_MASK) == (deck[p + 2] & Constants.SUIT_MASK)) {
			return (deck[p + 1] & Constants.SCORE_MASK)
					+ (deck[p + 2] & Constants.SCORE_MASK)
					+ Constants.CARD_SCORE_YONCHEV;
		}

		return 0;
	}

	private int yonchevTwoOfKind(int p) {
		if ((deck[p + 1] & Constants.KIND_MASK) == (deck[p + 2] & Constants.KIND_MASK)) {
			return (2 * (deck[p + 1] & Constants.SCORE_MASK))
					+ Constants.CARD_SCORE_YONCHEV;
		}

		return 0;
	}

	private int calculateScore(int p) {
		int value = 0;
		int result = 0;

		value = yonchevTwoOfKind(p);
		if (value > result) {
			result = value;
		}

		value = yonchevAllSameSuit(p);
		if (value > result) {
			result = value;
		}

		value = yonchevSingleStrongest(p);
		if (value > result) {
			result = value;
		}

		value = threeOfKind(p);
		if (value > result) {
			result = value;
		}

		value = allSameSuit(p);
		if (value > result) {
			result = value;
		}

		value = twoAces(p);
		if (value > result) {
			result = value;
		}

		value = twoOfSuit(p);
		if (value > result) {
			result = value;
		}

		value = singleStrongest(p);
		if (value > result) {
			result = value;
		}

		return result;
	}

	public Simulation(int numberOfPlayers, Integer... hand) {
		super();

		this.numberOfPlayers = numberOfPlayers;

		fullShuffle();
		pullPlayersCardsInfront(hand);
		partialSort();

		score = calculateScore(0);
	}

	public void round() {
		outcome = Outcome.WIN;

		for (int p = 1; p < numberOfPlayers; p++) {
			opponent = calculateScore(p * 3);

			if (score < opponent) {
				outcome = Outcome.LOSE;
				break;
			}

			if (score == opponent) {
				outcome = Outcome.SVARKA;
			}
		}

		switch (outcome) {
		case WIN:
			win++;
			break;
		case SVARKA:
			svarka++;
			break;
		case LOSE:
			lose++;
			break;
		}

		partialShuffle();
		partialSort();
		count++;
	}
	
	public double[] result() {
		double result[] = { (double) win / (double) count,
				(double) svarka / (double) count,
				(double) lose / (double) count };
		return result;
	}	

	@Override
	public String toString() {
		return "Simulation [deck=" + Arrays.toString(deck) + ", score=" + score
				+ ", opponent=" + opponent + ", outcome=" + outcome + ", win="
				+ win + ", lose=" + lose + ", svarka=" + svarka + ", count="
				+ count + ", numberOfPlayers=" + numberOfPlayers + "]";
	}

}
