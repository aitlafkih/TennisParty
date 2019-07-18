package org.tennis.game.service;

import java.util.concurrent.TimeUnit;

import org.tennis.game.model.Player;
import org.tennis.game.model.Score;
import org.tennis.game.model.Set;
import org.tennis.game.model.TennisParty;
import org.tennis.game.model.enumeration.PartyStatus;
import org.tennis.game.model.enumeration.PointEnum;
import org.tennis.game.tools.Helper;

public class TennisPartyService {

	public static void configureParty(TennisParty party) {
		party.setPlayer1(new Player(Helper.readText("Entrer player 1 : ")));
		party.setPlayer2(new Player(Helper.readText("Entrer player 2 : ")));
	}

	public static void playGame(TennisParty party) throws InterruptedException {

		displayPlayers(party);

		initGame(party);

	}

	private static void initGame(TennisParty party) throws InterruptedException {
		boolean a = false;
		do {
			Double p1 = Helper.getRandomNumber();
			Double p2 = Helper.getRandomNumber();

			a = caculatePoints(party, p1 > p2);

			displayPoints(party);

			TimeUnit.SECONDS.sleep(1);
		} while (!a);
	}

	private static boolean caculatePoints(TennisParty party, boolean gameResult) throws InterruptedException {

		// Current Points
		PointEnum scoreWinner = (gameResult) ? party.getCurrentSet().getPoints().getScorePlayer1()
				: party.getCurrentSet().getPoints().getScorePlayer2();
		PointEnum ScoreLoser = (gameResult) ? party.getCurrentSet().getPoints().getScorePlayer2()
				: party.getCurrentSet().getPoints().getScorePlayer1();

		PointEnum newScore = PointEnum.ZERO;

		switch (scoreWinner) {
		case ZERO:
			newScore = PointEnum.FIFTEEN;
			break;
		case FIFTEEN:
			newScore = PointEnum.THIRTY;
			break;
		case THIRTY:
			newScore = PointEnum.FOURTY;
			break;
		case DEUCE:
			newScore = PointEnum.ADVANTAGE;
			break;
		case FOURTY:
		case ADVANTAGE: {
			if (PointEnum.FOURTY.equals(scoreWinner) && PointEnum.FOURTY.equals(ScoreLoser))
				newScore = PointEnum.ADVANTAGE;
			else if (PointEnum.FOURTY.equals(scoreWinner) && PointEnum.ADVANTAGE.equals(ScoreLoser))
				newScore = PointEnum.DEUCE;
			else {
				calculateCurrentSetPoint(party, gameResult);
				return true;
			}
			break;
		}
		}

		// Set New Scores
		SetPoints(party.getCurrentSet(), gameResult, newScore);
		return false;
	}

	// Update points
	private static void SetPoints(Set currentSet, boolean gameResult, PointEnum newScore) {
		if (PointEnum.ADVANTAGE.equals(newScore)) {
			currentSet.getPoints().setScorePlayer1(newScore);
			currentSet.getPoints().setScorePlayer2(PointEnum.FOURTY);
		} else if (PointEnum.DEUCE.equals(newScore)) {
			currentSet.getPoints().setScorePlayer1(newScore);
			currentSet.getPoints().setScorePlayer2(newScore);
		} else if (gameResult) {
			currentSet.getPoints().setScorePlayer1(newScore);
		} else {
			currentSet.getPoints().setScorePlayer2(newScore);
		}

	}

	// calculate
	private static void calculateCurrentSetPoint(TennisParty party, boolean gameResult) throws InterruptedException {
		party.getCurrentSet().setPoints(new Score<PointEnum>(PointEnum.ZERO, PointEnum.ZERO));
		Integer scoreWinner = (gameResult) ? party.getCurrentSet().getManche().getScorePlayer1() : party.getCurrentSet().getManche().getScorePlayer2();
		Integer ScoreLoser = (gameResult) ? party.getCurrentSet().getManche().getScorePlayer2() : party.getCurrentSet().getManche().getScorePlayer1();
		
		int dif = Math.abs(scoreWinner.intValue() - ScoreLoser.intValue());
		if (scoreWinner.intValue() == 7 || (scoreWinner.intValue() == 6 && dif >= 2)) {
			party.getScoreSets().add(party.getCurrentSet());
	
			// calculateMatchStatus()
			calculateMatchStatus(party, gameResult);
			
			party.setCurrentSet(new Set());
			if (gameResult) {
				party.getCurrentSet().getManche().setScorePlayer1(1);
			} else {
				party.getCurrentSet().getManche().setScorePlayer2(1);
			}

		} else if (scoreWinner == 6 && ScoreLoser == 6) {
			tieBreak(party);
		} else {
			if (gameResult) {
				party.getCurrentSet().getManche().setScorePlayer1(scoreWinner + 1);
			} else {
				party.getCurrentSet().getManche().setScorePlayer2(scoreWinner + 1);
			}
		}

		
		initGame(party);

	}

	private static void calculateMatchStatus(TennisParty party, boolean gameResult) {
		if (party.getScoreSets().size() >= 3) {
			Score<Integer> result = new Score<>(0, 0);
			party.getScoreSets().stream().forEach(set -> {
				if (set.getManche().getScorePlayer1() > set.getManche().getScorePlayer2())
					result.setScorePlayer1(result.getScorePlayer1() + 1);
				else
					result.setScorePlayer2(result.getScorePlayer2() + 1);
			});

			if (gameResult && result.getScorePlayer1().intValue() == 3 || result.getScorePlayer2().intValue() == 3) {
				party.setPartyStatus(PartyStatus.PLAYER1WINS);
				System.out.println(party.getPartyStatus().getValue());
				System.exit(0);
			} else if (result.getScorePlayer2().intValue() == 3) {
				party.setPartyStatus(PartyStatus.PLAYER2WINS);
				System.out.println(party.getPartyStatus().getValue());
				System.exit(0);
			}

		}
	}

	// Tie Break
	private static void tieBreak(TennisParty party) throws InterruptedException {

		boolean a = false;
		do {
			Double p1 = Helper.getRandomNumber();
			Double p2 = Helper.getRandomNumber();

			Integer scoreWinner = (p1 > p2) ? party.getCurrentSet().getTieBreak().getScorePlayer1()
					: party.getCurrentSet().getTieBreak().getScorePlayer2();
			Integer ScoreLoser = (p1 > p2) ? party.getCurrentSet().getTieBreak().getScorePlayer2()
					: party.getCurrentSet().getTieBreak().getScorePlayer1();

			if (p1 > p2) {
				party.getCurrentSet().getTieBreak().setScorePlayer1(scoreWinner + 1);
			} else {
				party.getCurrentSet().getTieBreak().setScorePlayer2(scoreWinner + 1);
			}

			int dif = Math.abs(scoreWinner.intValue() - ScoreLoser.intValue());
			if (scoreWinner.intValue() >= 7 && dif >= 2) {
				a = true;
				if (p1 > p2) {
					party.getCurrentSet().getManche()
							.setScorePlayer1(party.getCurrentSet().getManche().getScorePlayer1() + 1);
				} else {
					party.getCurrentSet().getManche()
							.setScorePlayer2(party.getCurrentSet().getManche().getScorePlayer2() + 1);
				}

				party.getCurrentSet().setTieBreak(new Score<>(0, 0));

			}
			if (!a)
				System.out.printf("Tie-Break : { %s, %s }\n", party.getCurrentSet().getTieBreak().getScorePlayer1(),
						party.getCurrentSet().getTieBreak().getScorePlayer2());

			TimeUnit.SECONDS.sleep(1);
		} while (!a);
	}

	// Display Points
	private static void displayPoints(TennisParty party) {
		System.out.print("Score : ");
		party.getScoreSets().stream().forEach(set -> System.out.printf("( %s , %s ) ",
				set.getManche().getScorePlayer1(), set.getManche().getScorePlayer2()));
		System.out.printf("( %s : %s ) \n", party.getCurrentSet().getManche().getScorePlayer1(),
				party.getCurrentSet().getManche().getScorePlayer2());
		System.out.printf("Current game status : %s - %s  \n",
				party.getCurrentSet().getPoints().getScorePlayer1().getValue(),
				party.getCurrentSet().getPoints().getScorePlayer2().getValue());
		System.out.println(party.getPartyStatus().getValue());
	}

	// Display Players
	private static void displayPlayers(TennisParty party) {
		System.out.printf("Player 1 : %s \n", party.getPlayer1().getName());
		System.out.printf("Player 2 : %s \n", party.getPlayer2().getName());
	}

}
