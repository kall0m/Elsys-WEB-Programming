package org.elsys.netprog.rest.models;

import java.util.Random;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.elsys.netprog.rest.interfaces.Game;

@JsonPropertyOrder({ "gameId", "cowsNumber", "bullsNumber", "turnsCount", "success" })
public class CowsAndBulls implements Game {
	private static final Integer MIN = 1000;
	private static final Integer MAX = 9999;
	private static final Random RANDOM = new Random();
	
	public static boolean isGuessingNumberValid(Integer number) {
		return number.toString().length() == 4 && isNumberWithDifferentDigits(number);
	}
	
	private String id;
	private String secret;
	private Integer cowsNumber;
	private Integer bullsNumber;
	private Integer turnsCount;
	private Boolean success;
	
	public CowsAndBulls(String id) {
		this.id = id;
		this.secret = String.valueOf(generateValidSecretNumber());
		this.cowsNumber = 0;
		this.bullsNumber = 0;
		this.turnsCount = 0;
		this.success = false;
	}

	@JsonProperty("gameId")
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonIgnore
	public String getSecret() {
		return this.secret;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	public Integer getCowsNumber() {
		return cowsNumber;
	}

	public void setCowsNumber(Integer cowsNumber) {
		this.cowsNumber = cowsNumber;
	}

	public Integer getBullsNumber() {
		return bullsNumber;
	}

	public void setBullsNumber(Integer bullsNumber) {
		this.bullsNumber = bullsNumber;
	}

	public Integer getTurnsCount() {
		return turnsCount;
	}

	public void setTurnsCount(Integer turnsCount) {
		this.turnsCount = turnsCount;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public void play(Integer number) {
		this.turnsCount++;
		
		char[] digitsOfGuessingNumber = number.toString().toCharArray();
		
		checkCowsNumber(digitsOfGuessingNumber);
		checkBullsNumber(digitsOfGuessingNumber);
		
		if (this.bullsNumber == 4) {
			this.success = true;
		}
	}
	
	public void reset() {
		this.cowsNumber = 0;
		this.bullsNumber = 0;
		this.success = false;
	}
	
	private void checkCowsNumber(char[] digitsOfGuessingNumber) {
		String secretNumberToString = this.secret.toString();
		
		for (int i = 0; i < digitsOfGuessingNumber.length; i++) {
			if (secretNumberToString.indexOf(digitsOfGuessingNumber[i]) >= 0) {
				this.cowsNumber++;
			}
		}
	}
	
	private void checkBullsNumber(char[] digitsOfGuessingNumber) {
		char[] digitsOfSecretNumber = this.secret.toString().toCharArray();
		
		for (int i = 0; i < digitsOfGuessingNumber.length; i++) {
			if (digitsOfGuessingNumber[i] == digitsOfSecretNumber[i]) {
				this.bullsNumber++;
			}
		}
	}

	private Integer generateValidSecretNumber() {
		int number = RANDOM.nextInt((MAX - MIN) + 1) + MIN;
		
		while (!isNumberWithDifferentDigits(number)) {
			number = RANDOM.nextInt((MAX - MIN) + 1) + MIN;
		}
		
		return number;
	}

	private static boolean isNumberWithDifferentDigits(Integer number) {
		char[] digits = number.toString().toCharArray();
		
		for (int i = 0; i < digits.length; i++) {
			for (int j = 0; j < digits.length; j++) {
				if (i != j) {
					if (digits[i] == digits[j]) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
}
