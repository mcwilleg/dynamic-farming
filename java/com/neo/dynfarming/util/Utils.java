package com.neo.dynfarming.util;

public class Utils {
	public static double clamp(double x, double min, double max) {
		return Math.min(max, Math.max(min, x));
	}
	
	public static double adjustScale(double x, double min, double max) {
		if(max < min) {
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
	
	public static double evaluateIdeal(double x, double min, double ideal, double max, double edge, double peak) {
		if(max < min || max < ideal || ideal < min) {
			throw new IllegalArgumentException(
					"min must be strictly less than ideal, and ideal must be strictly less than max"
			);
		}
		if(x < ideal) {
			return evaluateGradient(min, ideal, edge, peak, x);
		} else if(x > ideal) {
			return evaluateGradient(ideal, max, peak, edge, x);
		}
		return (ideal - min) / (max - min);
	}
	
	public static double evaluateIdealNormalRange(double x, double min, double idealMin, double idealMax, double max) {
		if(x >= idealMin && x <= idealMax) {
			return 1;
		} else if(x < idealMin) {
			return evaluateGradient(min, idealMin, 0, 1, x);
		}
		return evaluateGradient(idealMax, max, 1, 0, x);
	}
	
	public static double evaluateIdealNormal(double x, double min, double ideal, double max) {
		return evaluateIdeal(x, min, ideal, max, 0, 1);
	}
	
	public static double evaluateIdealReverse(double x, double min, double ideal, double max) {
		return evaluateIdeal(x, min, ideal, max, 1, 0);
	}
}
