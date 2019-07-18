package org.tennis.game.model.enumeration;

public enum PointEnum {
	ZERO("0"), FIFTEEN("15"), THIRTY("30"), FOURTY("40"), DEUCE("Deuce"), ADVANTAGE("Advantage");

	private String value;

	private PointEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
