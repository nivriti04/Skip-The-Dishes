/* 
 * PROGRAM DESCRIPTION: The program helps keep track of the delivery employees of
 * Skip The Dishes and the items people are purchasing through their website. This is achieved
 * through storing the list of items purchased, sorting them using insertion sort in ascending 
 * lexicographic order (A to Z) and displaying the total number of items ordered in a text file
 * called Items.txt. The status of their delivery personnel is stored in an another text file 
 * called Delivery.text. This file is used to determine the number of orders that day, the number 
 * of orders that were late, and the order number to start at for the next day. The processed 
 * results are then written to another text file called DeliveryResults.txt. After this is done,
 * the user is told on the console that the program is finished and that the file was successfully
 * written!
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class skip {

	public static void main(String[] args) {

		String line; //variable to hold the file string line
		int totalitems=0; 
		
		//arrayList to store the items ordered
		ArrayList <String> data= new ArrayList<String>();
		
		//calling the method to check for the presence of the file
		exist();
		
		// Reading and Writing to Items file
		try
		{
			// Creates a file object
			File item = new File("Items.txt");  
			FileReader in=new FileReader (item);
			BufferedReader readFile = new BufferedReader(in);
			
			// reads each line until the end of the file 
			while (((line=readFile.readLine())!=null))
			{
				//adds the line that was read to the arrayList
				data.add(line);	
				//counter to see how many things are ordered
				totalitems++;
			}
			
			//call to the sorting method which will sort our arrayList
			insertionsort(data);
			
			readFile.close(); //Close BufferedReader
			in.close(); // Close the FileReader
			 
			//making output file stream and buffered stream writer
			FileWriter out = new FileWriter (item); 
			BufferedWriter writeFile = new BufferedWriter(out);  
			
			//for loop runs through the length of the arrayList
			for (int j=0;j<data.size();j++)
			{
				//writes the contents at each index to the file
				writeFile.write(data.get(j));
				writeFile.newLine();	
			}
			
			//display the number of items ordered
			writeFile.newLine();
			writeFile.write("ITEMS ORDERED: " + totalitems);
			
			writeFile.close(); //Close BufferedWriter
			out.close(); // Close the FileWriter
		}
		
		//code to execute if exception thrown
		catch (Exception e)
		{
			System.out.print("ERROR " + e.getMessage());
		}
		
		//Reading from Delivery File and writing to Delivery Results file
		try
		{
			int late=0, delnum;
			int ocounter=0, dcounter=0;
			
			//creates a file object, input file stream and Buffered stream Reader
			File delivery = new File("Delivery.txt");  
			FileReader in=new FileReader (delivery);
			BufferedReader readFile = new BufferedReader(in);
			
			//making a file object, output file stream and buffered stream writer
			File deliverywrite = new File("DeliveryResults.txt");  
			FileWriter out = new FileWriter (deliverywrite); 
			BufferedWriter writeFile = new BufferedWriter(out);  
		
			//variable storing the delivery number (on the first line of the file) as an integer
			delnum=Integer.parseInt(line=readFile.readLine());
			
			// reads each line until the end of the file 
			while (((line=readFile.readLine())!=null))
			{
				//if the line has ordered written on it (an order was placed on Skip The Dishes)
				if (line.equalsIgnoreCase("ORDERED"))
				{
					//increase the delivery number by 1
					delnum++;
					
					//increase the order counter by 1
					ocounter++;
					
				}
				
				// if the line reads delivered (the order was successfully delivered)
				else if ((line.equalsIgnoreCase("DELIVERED")))
				{
					//increase the delivery counter by 1
					dcounter++;
				}
				
				//if the line reads closed (meaning that the day is over, any remaining orders are late)
				else if ((line.equalsIgnoreCase("CLOSED")))
				{
					//calculate late orders by comparing the number of orders vs the number of orders delivered
					late=(ocounter-dcounter);
				
					//every time the word "closed" is encountered, display the number of orders placed (ocounter), the number of orders
					//late (late) and the delivery number of the driver (delnum)
					writeFile.write(ocounter + " " + late + " " + delnum);
					
					writeFile.newLine();
					
					//reset the orders placed and orders delivered each time the store is closed
					ocounter=0;
					dcounter=0;
				}
			}
			
			readFile.close(); //Close BufferedReader
			in.close(); // Close the FileReader

			writeFile.close(); //Close BufferedWriter
			out.close(); // Close the FileWriter
			
		}
		
		//code to execute if exception thrown
		catch (Exception e)
		{
			System.out.print("ERROR " + e.getMessage());
		}	
		
		//printing to the console
		System.out.print("PROGRAM COMPLETE!" + "\nFILE WAS SUCCESSFULLY WRITTEN.");
	}	
	
	//method checking for the existence of the output file; creating it if doesn't exist
	public static void exist()
	{
		// creating a file object
		File dataFile = new File("DeliveryResults.txt");  
			
		//if file doesn't exist
			if (!dataFile.exists()) 
			{  
				//create the file
				try 
				{  
					dataFile.createNewFile();   
				}  
				
				//code to execute if file cannot be created
				catch (IOException e) 
				{  
					System.out.println("File could not be created.");  
					System.out.println("IOException "+e.getMessage());  
				} 
			}
	}
	
	//method for performing insertion sort on the arrayList storing all the items ordered
	public static void insertionsort (ArrayList<String> data)
		{
			int end;
		
			// Controls the pointer for the sorted left half of the array	
			for(end = 1; end < data.size(); end++) 
		    { 
				String item = data.get(end); 
				int i = end;  
				
		   // Searching to see where to insert the value 
		        while(i>0 && item.compareTo(data.get(i-1))<0) 
		        { 
		        // Moves the larger value over right. 
		          data.set(i,data.get(i-1));  
		           i--; 
		        } 
		   // Move the smaller value to the sorted left hand side.   
		        data.set(i,item);  
		    }
			
		}
}
