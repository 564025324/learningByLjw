package com.lijingwen.unless;

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.InputEvent;

public class MouseTest {
	public static void main(String[] args) throws Exception {
		Robot m1 = new Robot();
		for (int i = 0; i <= 100; i++) {
			System.out.println("i="+i);
			m1.mouseMove((int) (Math.random() * 1000), (int) (Math.random() * 1000));
			Thread.sleep(1);
		}
	}
}
