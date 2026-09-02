import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.InputMismatchException;

public class Zpm {  
    
    public static void main(String args[]) {        
        Scanner scannerrObject = new Scanner(args[0]);
        HashMap<String, String> objects = new HashMap<>();
        int currentLine = 0;        
        
        while (scannerrObject.hasNextLine()) {            
          currentLine++;
          String data = scannerrObject.nextLine();
          StringTokenizer tokenizer = new StringTokenizer(data, " ()");     
          
          while (tokenizer.hasMoreTokens()) {                
              String token = tokenizer.nextToken();
              
              if(token.equals("PRINT")) { //Code for printing out a value (Done)                  
                  try {                      
                      System.out.println(objects.get(tokenizer.nextToken()));                      
                  }catch(InputMismatchException er) {                      
                      System.out.println("RUNTIME ERROR: line " + currentLine);  
                  }
              }else if(token.equals("FOR")) { //Code for for loops
                  
              }else {                  
                  String tempVar = token;
                  
                  if (objects.containsKey(token)) {                     
                      String tempArg = tokenizer.nextToken();
                      String tempCompliment = tokenizer.nextToken();
                      
                      if (tempArg.equals("+=")) { //Code for += (done)                          
                          if(canBeInt(objects.get(tempVar)) && canBeInt(objects.get(tempCompliment))) {                             
                              objects.put(tempVar, Integer.toString(Integer.parseInt(objects.get(tempVar)) + Integer.parseInt(objects.get(tempCompliment))));                             
                          } else {                              
                              objects.put(tempVar, objects.get(tempVar) + objects.get(tempCompliment));                              
                          }                                       
                      } else if (tempArg.equals("*=")) { //Code for *= (done)                          
                          if(canBeInt(objects.get(tempVar)) && canBeInt(objects.get(tempCompliment))) {                              
                              objects.put(tempVar, Integer.toString(Integer.parseInt(objects.get(tempVar)) * Integer.parseInt(objects.get(tempCompliment))));                              
                          } else {                              
                              scannerrObject.close();
                              throw new IllegalArgumentException("ERROR: cannot multiply Strings on line " + currentLine);                              
                          }
                      } else if (tempArg.equals("-=")) { //Code for -= (done)                          
                          if(canBeInt(objects.get(tempVar)) && canBeInt(objects.get(tempCompliment))) {                              
                              objects.put(tempVar, Integer.toString(Integer.parseInt(objects.get(tempVar)) - Integer.parseInt(objects.get(tempCompliment))));                              
                          } else {                              
                              scannerrObject.close();
                              throw new IllegalArgumentException("ERROR: cannot subtract Strings on line " + currentLine);                              
                          }    
                      } else {
                          scannerrObject.close();
                          throw new IllegalArgumentException("ERROR: illegal argument on line " + currentLine);
                      }
                  } else {                      
                      if(tokenizer.nextToken().equals("=")) { //Code for adding values (Done)                          
                          String tempCompliment = tokenizer.nextToken();                         
                          if (objects.containsKey(tempCompliment)) {                              
                              objects.put(tempVar, objects.get(tempCompliment));                              
                          } else {                              
                              objects.put(tempVar, tempCompliment);                              
                          }
                      } else {                          
                          scannerrObject.close();
                          throw new IllegalArgumentException("ERROR: " + tempVar + "does not exist on line " + currentLine);
                      }
                  }
              }
          }
          if(!tokenizer.nextToken().equals(";")) { //Code to check for semicolons
              scannerrObject.close();
              throw new IllegalArgumentException("ERROR: line " + currentLine + " does not end in a semicolon");
          }
        }        
        //Closing the file
        scannerrObject.close();
    }   
    
    
    public static boolean canBeInt(String str) { //helper method for checking if a value is an int
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
