package warGame;

public class NimbleCharacterBuilder extends CharacterBuilder{

	@Override
	void buildStrength() {
		// TODO Auto-generated method stub
		characterProduct.setStrength(score[2]);
	}

	@Override
	void buildDexterity() {
		// TODO Auto-generated method stub
		characterProduct.setDexterity(score[0]);
	}

	@Override
	void buildConstitution() {
		// TODO Auto-generated method stub
		characterProduct.setConstitution(score[1]);
	}

	@Override
	void buildIntelligence() {
		// TODO Auto-generated method stub
		characterProduct.setIntelligence(score[3]);
	}

	@Override
	void buildWisdom() {
		// TODO Auto-generated method stub
		characterProduct.setWisdom(score[5]);
	}

	@Override
	void buildCharisma() {
		// TODO Auto-generated method stub
		characterProduct.setCharisma(score[4]);
	}

}
