package act.mashup.util.Similarity;

public class Word {

	private String keyString;// 字符串
	private int wordFrequency;// 字符串频度计数
	private double characterValue;// 权值

	public Word(String key) {
		this.keyString = key;
	}

	public String getKeyString() {
		return keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

	public int getWordFrequency() {
		return wordFrequency;
	}

	public void setWordFrequency(int wordFrequency) {
		this.wordFrequency = wordFrequency;
	}
	
	public void addWordFrequency() {
		this.wordFrequency++;
	}

	public double getCharacterValue() {
		return characterValue;
	}

	public void setCharacterValue(double characterValue) {
		this.characterValue = characterValue;
	}

}
