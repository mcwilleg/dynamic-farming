package com.neo.dynfarming.util;

public class Utils {
	public static double clamp(double x, double min, double max) {
		return Math.min(max, Math.max(min, x));
	}
	
	public static double adjustScale(double x, double min, double max) {
		if(max <= min) {
			throw new IllegalArgumentException("min must be strictly less than max");
		}
		x = clamp(x, 0, 1);
		return (max - min) * x + min;
	}
	
	public static double evaluateGradient(double x1, double x2, double y1, double y2, double inputX) {
		if(inputX <= x1) {
			return y1;
		} else if(inputX >= x2) {
			return y2;
		}
		return ((y2 - y1) * (inputX - x1)) / (x2 - x1) + y1;
	}
}
