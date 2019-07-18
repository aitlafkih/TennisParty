package org.tennis.game.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.tennis.game.model.Player;
import org.tennis.game.model.TennisParty;
import org.tennis.game.model.enumeration.PartyStatus;
import org.tennis.game.tools.Helper;

import junit.framework.TestCase;

public class TennisPartyServiceTest  extends TestCase{

	private TennisParty party;
	
	@Override
	protected void setUp() throws Exception {		
		super.setUp();
		party = new TennisParty();
        party.setPlayer1(new Player("A"));
        party.setPlayer2(new Player("B"));
	}

	@Test
	public void testPlayGame() throws InterruptedException {
		TennisPartyService.playGame(party);
		assertFalse(party.getScoreSets().isEmpty());
		assertNotEquals(party.getPartyStatus(), PartyStatus.IN_PROGRESS);
		
	}

}
