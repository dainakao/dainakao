package search;

public class Main {
	
	public static void main(String[] args) {
		Tex_Read r = new Tex_Read();
		
		
		//String A にテキストファイルをコピー、出力
		String A = ""; 
		A = r.Read_String(A);
		System.out.print(A);
		
		//Searchする。
		Search s = new Search(A);
	}
}
