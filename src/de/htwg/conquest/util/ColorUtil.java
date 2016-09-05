package de.htwg.conquest.util;

import java.awt.Color;

public class ColorUtil {

	private int colorNumber;
	private Color[] colorSet;
	
	public ColorUtil(int num) {
		this.colorNumber = num;
		this.colorSet = new Color[num];
		for(int i = 0; i < colorNumber; i++) {
			
		}
	}

	public Color[] getColorSet() {
		return colorSet;
	}
//	public enum Colors {
//		Red (Color.RED),
//		Green (Color.GREEN),
//		Blue (Color.BLUE),
//		Yellow (Color.YELLOW),
//		Orange (Color.ORANGE),
//		Purple (Color.MAGENTA),
//		Brown (Color.GRAY);
//		
//		private final Color color;
//		
//		Colors(Color color) {
//			this.color = color;
//		}
//		
//		public Color color() {
//			return color;
//		}
//	}
}
