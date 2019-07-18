package org.tennis.game.model.enumeration;

public enum PartyStatus {
	IN_PROGRESS("Match status : in progress"), PLAYER1WINS("Match status : player 1 wins"), PLAYER2WINS("Match status : player 2 wins");

	private String value;

	public String getValue() {
		return value;
	}

	private PartyStatus(String value) {
		this.value = value;
	}

}
