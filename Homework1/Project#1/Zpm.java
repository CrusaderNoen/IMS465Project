import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Zpm {  
    
    public static void main(String[] args)throws FileNotFoundException {        
        Scanner scannerObject = new Scanner(new File(args[0]));
        //Scanner scannerObject = new Scanner(new File("prog3.zpm"));
        HashMap<String, String> objects = new HashMap<>();
        int currentLine = 0;
        
        while (scannerObject.hasNextLine()) {            
            currentLine++;
            //System.out.println(currentLine);
            String data = scannerObject.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(data, " ()");     
          
                        
            String tempVar = tokenizer.nextToken();
              
            if (tempVar.equals("FOR")) { //Code for for loops
                int loopCount = Integer.parseInt(tokenizer.nextToken());
                for (int i = 0; i < loopCount; i++) {
                    StringTokenizer tempizer = new StringTokenizer(data, " ()");
                    tempizer.nextToken(); 
                    tempizer.nextToken();
                    String newToken = tempizer.nextToken();
                    boolean exit = false;
                    while (!exit) {
                        zpmArg(newToken, tempizer, scannerObject, 
                                objects, currentLine);
                        tempizer.nextToken();
                        newToken = tempizer.nextToken();
                        if (newToken.equals("ENDFOR")) {
                            exit = true;
                        }
                    }
                }
            } else {                                                    
                zpmArg(tempVar, tokenizer, scannerObject, objects, currentLine);
            }
        }
                
        //Closing the file
        scannerObject.close();
    }   
    
    //helper method for printing 
    public static void zpmPrint(String val, HashMap<String, String> objects, int currentLine) {             
        System.out.println(objects.get(val));                      
    }
    
    //method for performing arguments
    public static void zpmArg(String tempVar, StringTokenizer tokenizer, Scanner scannerObject, HashMap<String, String> objects, int currentLine) {
        if (objects.containsKey(tempVar)) {                     
            String tempArg = tokenizer.nextToken();
            if (tempArg.equals("+=")) { //Code for += (done)  
                String tempCompliment = tokenizer.nextToken();
                if (tempCompliment.substring(0, 1).equals("\"")) {
                    objects.put(tempVar, objects.get(tempVar) + getFullString(tokenizer, tempCompliment));
                } else if(canBeInt(tempCompliment)) {
                    objects.put(tempVar, Integer.toString(Integer.parseInt(objects.get(tempVar)) + Integer.parseInt(tempCompliment)));
                }else if (objects.containsKey(tempCompliment)) {
                    objects.put(tempVar, objects.get(tempVar)+ objects.get(tempCompliment));
                } else {
                    scannerObject.close();
                    throw new IllegalArgumentException("ERROR: " + tempCompliment + " does not exist on line " + currentLine);
                }        
            } else if (tempArg.equals("*=")) { //Code for *= (done)  
                String tempCompliment = tokenizer.nextToken();
                if(canBeInt(tempCompliment)) {
                    objects.put(tempVar, Integer.toString(Integer.parseInt(objects.get(tempVar)) * Integer.parseInt(tempCompliment)));
                }else if (objects.containsKey(tempCompliment)) {
                    objects.put(tempVar, Integer.toString(Integer.parseInt(objects.get(tempVar)) * Integer.parseInt(objects.get(tempCompliment))));
                } else {
                    scannerObject.close();
                    throw new IllegalArgumentException("ERROR: " + tempCompliment + " does not exist on line " + currentLine);
                }
            } else if (tempArg.equals("-=")) { //Code for -= (done)  
                String tempCompliment = tokenizer.nextToken();
                if(canBeInt(tempCompliment)) {
                    objects.put(tempVar, Integer.toString(Integer.parseInt(objects.get(tempVar)) - Integer.parseInt(tempCompliment)));
                }else if (objects.containsKey(tempCompliment)) {
                    objects.put(tempVar, Integer.toString(Integer.parseInt(objects.get(tempVar)) - Integer.parseInt(objects.get(tempCompliment))));
                } else {
                    scannerObject.close();
                    throw new IllegalArgumentException("ERROR: " + tempCompliment + " does not exist on line " + currentLine);
                }           
            }
        } else if (tempVar.equals("PRINT")) {
            zpmPrint(tokenizer.nextToken(), objects, currentLine);
        } else {                      
            if (tokenizer.nextToken().equals("=")) { //Code for adding values (Done)                          
                String tempCompliment = getFullString(tokenizer, tokenizer.nextToken());
                if (objects.containsKey(tempCompliment)) {                              
                    objects.put(tempVar, objects.get(tempCompliment));                              
                } else {                              
                    objects.put(tempVar, tempCompliment);                              
                }
            } else {                          
                scannerObject.close();
                throw new IllegalArgumentException("ERROR: " + tempVar + " does not exist on line " + currentLine);
            }
        }
    }
    
    
    public static boolean canBeInt(String str) { //helper method for checking if a value is an int
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static String getFullString(StringTokenizer tokenizer, String temp) { //helper method for getting a full string
        String tempCompliment = temp;
        if (tempCompliment.startsWith("\"")) {
            while (!tempCompliment.endsWith("\"")) {
                tempCompliment += " " + tokenizer.nextToken();
            }
            tempCompliment = tempCompliment.substring(1, tempCompliment.length() - 1);
        }
        return tempCompliment;
    }
    
    public static String parseQuotes(String str) {
        String temp = str;
        if (str.substring(0, 1).equals("\"")) {
            temp = temp.substring(1);
        }
        if (str.substring(str.length() - 1).equals("\"")) {
            temp = temp.substring(0, str.length() - 2);
        }
        return temp;
    }
}
