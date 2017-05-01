package prog3.crawler;
import java.util.Scanner;
public class SpiderTest
{
  private static boolean debug = false;
  public static void main(String[] arguments)
  {
    if ( arguments.length > 0 && arguments[0].toUpperCase().startsWith("-D") ) debug = ! debug;
    System.out.println ( "Debug is " + ( debug ? "" : "not " ) + "enabled" );
    
    String userInput;
    String userURL;
    Scanner sc = new Scanner(System.in);
    System.out.println("Test?"); 
    userInput = sc.nextLine();
    if(userInput.equals("y"))
    {
      System.out.println("insert the word"); 
      userInput = sc.nextLine();
      System.out.println("Insert URL or n for start on wikipedia"); 
      userURL = sc.nextLine();
      sc.close();
      if(userURL.equals("n"))
      {
        userURL="https://en.wikipedia.org/wiki/Main_Page";
      }
      Spider spider = new Spider();
      spider.search(userURL, userInput,debug);
      if(spider.getWebCrawlerOutput().trim().length()!=0)
      {
        System.out.print("WebPages where you can find the word " +userInput+":\n"+spider.getWebCrawlerOutput());
      } 
    }
    else
    {
      sc.close();
      userInput=" ";
      Spider spider = new Spider();
      spider.search("https://en.wikipedia.org/wiki/Main_Page", userInput,debug);
      if(spider.getWebCrawlerOutput().trim().length()!=0)
      {
        System.out.print("WebPages saved on file " +userInput+":\n"+spider.getWebCrawlerOutput());
      }
    }
  }
}
