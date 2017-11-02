import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) throws IOException
	{
		String str = "qqqaaaaaabbbcccaaa";
		int i=str.indexOf("a");
		System.out.println(i);
		System.out.println(str.replace("aaa", "_"));
	}
}
