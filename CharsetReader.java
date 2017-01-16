import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class CharsetReader {
	private File file;
	public CharsetReader(String filename){
		setFile(filename);
	}
	public void setFile(String filename){
		file = new File(filename);
	}
	public char[][] getChars(){
		List<Character> list = new ArrayList<Character>();
		char[][] ret;
		try(FileInputStream fis = new FileInputStream(file);
				InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)){
			reader.read();
			while(reader.ready()){
				list.add((char)reader.read());
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		for(Character c : list){
			System.out.print(c);
		}
		int rows = 1;
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).charValue() == '\n'){
				rows++;
				list.remove(i);
				i--;
			}
		}
		int perRow = list.size()/rows;
		ret = new char[rows][perRow];
		int count = 0;
		for(int r = 0; r < ret.length; r++){
			for(int c = 0; c < ret[r].length; c++){
				if(list.get(count).charValue() == '\r')
					count++;
				ret[r][c] = list.get(count);
				count++;
			}
		}
		for(int r = 0; r < ret.length; r++){
			for(int c = 0; c < ret[r].length; c++){
				System.out.print(ret[r][c]);
			}
		}
		return ret;
		
	}
}
