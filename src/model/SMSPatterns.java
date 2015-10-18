package model;

import java.util.ArrayList;
import java.util.List;

public class SMSPatterns {
	private List<String> patterns;

	private SMSPatterns() {

	}

	public List<String> getPatternList() {
		if (patterns == null) {
			patterns = new ArrayList<String>();
			patterns.add("");
		}
		return patterns;
	}

	public List<String> addNewPattern(List<String> newPatterns) {
		getPatternList().addAll(newPatterns);
		return patterns;
	}
}
