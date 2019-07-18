package org.tennis.game.model;

import java.util.ArrayList;
import java.util.List;

import org.tennis.game.model.enumeration.PartyStatus;

public class TennisParty {

	private Player player1;
	private Player player2;
	private List<Set> scoreSets = new ArrayList<>();
	private Set currentSet = new Set();
	private PartyStatus partyStatus = PartyStatus.IN_PROGRESS;

	public TennisParty() {
		super();
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public List<Set> getScoreSets() {
		return scoreSets;
	}

	public void setScoreSets(List<Set> scoreSets) {
		this.scoreSets = scoreSets;
	}

	public Set getCurrentSet() {
		return currentSet;
	}

	public void setCurrentSet(Set currentSet) {
		this.currentSet = currentSet;
	}

	public PartyStatus getPartyStatus() {
		return partyStatus;
	}

	public void setPartyStatus(PartyStatus partyStatus) {
		this.partyStatus = partyStatus;
	}

}
