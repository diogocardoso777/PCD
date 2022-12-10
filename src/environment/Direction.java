package environment;

import java.awt.event.KeyEvent;
import java.util.Random;

public enum Direction {
	UP(0,-1),DOWN(0,1),LEFT(-1,0),RIGHT(1,0);
	private Coordinate vector;
	Direction(int x, int y) {
		vector=new Coordinate(x, y);
	}
	public Coordinate getVector() {
		return vector;
	}
	public static Direction random() {
		Random generator = new Random();
		return values()[generator.nextInt(values().length)];
	}

	public static Direction directionFor(String name){
		if(name == null) return null;
		for(Direction d : values() )
			if(d.name().equals(name))
				return d;
		return null;
	}

	@Override
	public String toString() {
		if(this == null) return "null";
		return name();
	}
}
