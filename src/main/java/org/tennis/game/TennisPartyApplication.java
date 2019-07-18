package org.tennis.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tennis.game.model.TennisParty;
import org.tennis.game.service.TennisPartyService;

@SpringBootApplication
public class TennisPartyApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(TennisPartyApplication.class, args);
		TennisParty party = new TennisParty();
		TennisPartyService.configureParty(party);
		TennisPartyService.playGame(party);
	}

}
