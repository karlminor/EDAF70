package model;

import java.util.ArrayList;
import java.util.Random;

public class Robot {
	int x, y, h, maxX, maxY, maxH;

	public Robot(int x, int y, int h, int maxX, int maxY, int maxH) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxH = maxH;
	}

	public void move() {
		setHeading();
		switch (h) {
		case 0:
			y -= 1;
			break;
		case 1:
			x += 1;
			break;
		case 2:
			y += 1;
			break;
		case 3:
			x -= 1;
			break;
		}
	}

	private void setHeading() {
		Random rand = new Random();
		if ((rand.nextInt(100) + 1) <= 70 && check(h)) {
			return;
		}
		ArrayList<Integer> possibleHeadings = new ArrayList<>();
		for (int i = 0; i < maxH; i++) {
			if (check(i) && i != h) {
				possibleHeadings.add(i);
			}
		}
		h = possibleHeadings.get(rand.nextInt(possibleHeadings.size()));
	}

	private boolean check(int h) {
		int dx = 0;
		int dy = 0;
		switch (h) {
		case 0:
			dy = -1;
			break;
		case 1:
			dx = 1;
			break;
		case 2:
			dy = 1;
			break;
		case 3:
			dx = -1;
			break;
		}
		return x + dx < maxX && x + dx >= 0 && y + dy < maxY && y + dy >= 0;
	}
}
