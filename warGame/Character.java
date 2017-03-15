package warGame;

public class Character {

	private int Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma;
	
	public void setStrength(int newStrength){
		Strength = newStrength;
	}
	public void setDexterity(int newDexterity){
		Dexterity = newDexterity;
	}
	public void setConstitution(int newConstitution){
		Constitution = newConstitution;
	}
	public void setIntelligence(int newIntelligence){
		Intelligence = newIntelligence;
	}
	public void setWisdom(int newWisdom){
		Wisdom = newWisdom;
	}
	public void setCharisma(int newCharisma){
		Charisma = newCharisma;
	}
	
	public int getStrength(){
		return Strength;
	}
	public int getDexterity(){
		return Dexterity;
	}
	public int getConstitution(){
		return Constitution;
	}
	public int getIntelligence(){
		return Intelligence;
	}
	public int getWisdom(){
		return Wisdom;
	}
	public int getCharisma(){
		return Charisma;
	}
}