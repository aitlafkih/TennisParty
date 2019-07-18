package org.tennis.game.model;

import org.tennis.game.model.enumeration.PointEnum;

public class Set {

	private Score<Integer> manche = new Score<>(0, 0);
	private Score<PointEnum> points = new Score<>(PointEnum.ZERO, PointEnum.ZERO);
	private Score<Integer> tieBreak = new Score<>(0, 0);

	public Set() {
		super();
	}

	public Score<Integer> getManche() {
		return manche;
	}

	public void setManche(Score<Integer> manche) {
		this.manche = manche;
	}

	public Score<PointEnum> getPoints() {
		return points;
	}

	public void setPoints(Score<PointEnum> points) {
		this.points = points;
	}

	public Score<Integer> getTieBreak() {
		return tieBreak;
	}

	public void setTieBreak(Score<Integer> tieBreak) {
		this.tieBreak = tieBreak;
	}

}
