package warGame.JUnitTest;
import junit.framework.*;

public class TestRunner{
	
	public static Test testSuite(){
		TestSuite ModelSuite = new TestSuite();
		ModelSuite.addTestSuite(MapModelTest1.class);
		ModelSuite.addTestSuite(MapModelTest2.class);
		ModelSuite.addTestSuite(MapModelTest3.class);
		ModelSuite.addTestSuite(MapModelTest4.class);
		ModelSuite.addTestSuite(CharacterModelTest1.class);
		ModelSuite.addTestSuite(CharacterModelTest2.class);
		ModelSuite.addTestSuite(CharacterModelTest3.class);
		ModelSuite.addTestSuite(CharacterModelTest4.class);
		ModelSuite.addTestSuite(CharacterModelTest5.class);
		ModelSuite.addTestSuite(CharacterModelTest6.class);
		ModelSuite.addTestSuite(CharacterModelTest7.class);
		ModelSuite.addTestSuite(CharacterModelTest8.class);
		ModelSuite.addTestSuite(ItemModelTest1.class);
		ModelSuite.addTestSuite(ItemModelTest2.class);
		ModelSuite.addTestSuite(ItemModelTest3.class);
		ModelSuite.addTestSuite(StartModelTest1.class);
		ModelSuite.addTestSuite(StartModelTest2.class);
		ModelSuite.addTestSuite(CampaignModelTest1.class);
		return ModelSuite;
	}
	
	public static void main(String args[]){
		junit.textui.TestRunner.run(testSuite());
	}
	
}