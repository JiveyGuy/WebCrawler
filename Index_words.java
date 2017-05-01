package prog3.crawler;
import java.util.Scanner;
import java.io.*;

public class Index_words
{
  public static void main(String [] args)throws IOException{
    Scanner input = new Scanner(System.in);
    {
      int x;
      
      File file = new File("C:\\\\Users\\jtruji61\\Desktop\\prog3\\crawler\\indexFile.txt");
      
      Scanner inputFile = new Scanner(file).useDelimiter("\\s*|||\\s*");
      System.out.println("Insert word/words to search");
      String words=input.nextLine();
      while(inputFile.hasNext())
      {
        
        String line = inputFile.nextLine();
        if(line.toLowerCase().contains(words.toLowerCase()))
        {
          for(int i=0; line.charAt(i)!='|';i++)
          {
            System.out.print(line.charAt(i));
          }
          System.out.println();
        }
      }
      inputFile.close();
    }
  }
}