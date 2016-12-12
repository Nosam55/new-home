
public class Tester {
	public static void main(String[] args){
		char[][] chars = {
				{'x','x','x','x','x'},
				{'x','x','x','x','x'},
				{'x','x','x','x','x'}
		};
		Stage stage = new Stage(chars, 50,50);
		System.out.println(stage.isInBounds(0, 0));
	}
}
