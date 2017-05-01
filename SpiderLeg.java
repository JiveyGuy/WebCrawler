package prog3.crawler;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.io.*;
import java.io.File;

//Jsoup Libary
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg
{
  boolean debug = false;
  private String currentLink="";
  // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
  private static final String USER_AGENT =
    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
  private List<String> links = new LinkedList<String>();
  private Document htmlDocument;
  
  public SpiderLeg(boolean d)
  {
    debug=d;
  }
  
  /**
   * This performs all the work. It makes an HTTP request, checks the response, and then gathers
   * up all the links on the page. Perform a searchForWord after the successful crawl
   * 
   * @param url
   *            - The URL to visit
   * @return whether or not the crawl was successful
   */
  public boolean crawl(String url)
  {
    currentLink=url;
    try
    {
      Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
      Document htmlDocument = connection.get();
      this.htmlDocument = htmlDocument;
      if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
        // indicating that everything is great.
      {
        if ( debug ){
          System.out.println("\n**Visiting** Received web page at " + url);
        }
      }
      if(!connection.response().contentType().contains("text/html"))
      {
        if ( debug ){
          System.out.println("**Failure** Retrieved something other than HTML");
        }
        return false;
      }
      Elements linksOnPage = htmlDocument.select("a[href]");
      if ( debug ){
        System.out.println("Found (" + linksOnPage.size() + ") links");
      }
      for(Element link : linksOnPage)
      {
        this.links.add(link.absUrl("href"));
      }
      return true;
    }
    catch(IOException ioe)
    {
      
      // We were not successful in our HTTP request
      return false;
    }
  }
  
  
  /**
   * Performs a search on the body of on the HTML document that is retrieved. This method should
   * only be called after a successful crawl.
   * 
   * @param searchWord
   *            - The word or string to look for
   * @return whether or not the word was found
   */
  public boolean searchForWord(String searchWord)
  {
    // Defensive coding. This method should only be used after a successful crawl.
    if(this.htmlDocument == null)
    {
      if ( debug ){
        System.out.println("ERROR! Call crawl() before performing analysis on the document");
      }
      return false;
    }
    if ( debug ){
      System.out.println("Searching for the word " + searchWord + "...");
    }
    String bodyText = this.htmlDocument.body().text();
    
    //for presentation at class
    //prints text in the webPage
    //System.out.print(bodyText);
    try
    {
      indexing(bodyText);
    }
    catch(Exception e)
    {
    }
    return bodyText.toLowerCase().contains(searchWord.toLowerCase());
  }
  
  
  public List<String> getLinks()
  {
    return this.links;
  }
  
  
  public void indexing(String words)throws IOException
  {
    
    BufferedWriter bw = null;
    FileWriter fw = null;
    
    try {
      
      
      String FILENAME="C:\\\\Users\\jtruji61\\Desktop\\prog3\\crawler\\indexFile.txt";
        File file = new File(FILENAME);
      
      // if file doesnt exists, then create it
      if (!file.exists()) {
        file.createNewFile();
      }
      
      // true = append file
      fw = new FileWriter(file.getAbsoluteFile(), true);
      bw = new BufferedWriter(fw);
      
      bw.write(currentLink+ " |||" + words+ "   $$\n");
      
      System.out.println("Done");
      
    } catch (IOException e) {
      
      e.printStackTrace();
      
    } finally {
      
      try {
        
        if (bw != null)
          bw.close();
        
        if (fw != null)
          fw.close();
        
      } catch (IOException ex) {
        
        ex.printStackTrace();
        
      }
    }
    
  }
  
}
