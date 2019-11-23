import java.io.*;
import java.util.*;

public class Processing {
		private ArrayList<String> formatted; // keeps track of total output after loading multiple files
		ArrayList<String> intermediate= new ArrayList<String>(); // List that keeps formatted lines of current loaded file. 
		//intermediate is needed in case the file being loaded has invalid flags somewhere in the file. Intermediate is added to 
		//formatted once all the lines have been read in the file
		
		private char justification= 'l'; // 'l'for left, 'r' for right, and 'c' for center
		private boolean single = true;  // true for single space, false for double spaced
		private boolean title = false;  // true for centered title otherwise does nothing
		private boolean indentation = false; // true for indentation otherwise does nothing
		private boolean block = false; // true for block format otherwise does nothing
		private boolean column1 = true; // true for one column, false for two columns
		
		
		public Processing() {
			formatted = new ArrayList<String>();
		}
		
		public void readFile(String fileName) throws IOException{
			File file= new File(fileName);
			BufferedReader reader= new BufferedReader(new FileReader(file));
			ArrayList<String> linesToFormat = new ArrayList<String>();
			String str;
			str = reader.readLine();
			boolean firstFlag = true; // keeps track whether flag being read is the first flag in a formatting section
			while(str != null) { // reads file until end of file is reached
				if(str.length()>0) {
					if(str.charAt(0) == '-'){
						if(firstFlag) {// if first flag of new formatting section, then format all the lines that were read
							format(linesToFormat);
							linesToFormat.clear();
							firstFlag= false;
						}
						boolean valid= checkFlag(str); // checks if flags are valid and sets flag values to variables
						if(!valid) { // if not valid then all lines formatted in this file are erased and error is thrown
							intermediate.clear();
							//error handling goes here
							break;
						}
					}
					else {
						firstFlag = true;// since it is a regular line, then the next flag will be first flag of new section
						linesToFormat.add(str);
					}
					
				}
				str= reader.readLine();
			}
			if(linesToFormat.size()>0) {
				format(linesToFormat);
			}
			addToOutput();
			intermediate.clear();
			reader.close();
		}
		
		private boolean checkFlag(String str) {
			int num=0;
			for(int i=0; i<str.length(); i++) { //ignores any white space in line when counting length of flag string
				if(str.charAt(i) != ' ' && str.charAt(i) != '	')
					num++;
			}
			if(num == 2) { // if length ignoring white space is 2 then checks if given flag is part of valid list
				char x= str.charAt(1);
				if(x == 'r' || x == 'c' || x == 'l' || x == 't' || x== 'n' || x== 'd' || x== 's' || x == 'i' || x == 'b' || x == '2' || x == '1' || x == 'e') {
					System.out.println("Valid Flag given.");
					return true;
				}
				else {
					System.out.println("Invalid Flag given");
					return false;
				}
			}
			else {
				System.out.println("Invalid Flag given");
			}
				return false;
		}
		
		private void format(ArrayList<String> lines) {
			// code for formatting based on flags
			SampleFormat(lines);
		}
		
		private void addToOutput() { // adds intermediate to the total output
			for(int i=0; i< intermediate.size(); i++)
				formatted.add(intermediate.get(i));
		}
		
		public ArrayList<String> getOutput(){ // used for displaying output in preview window of GUI
			return formatted;
		}
		
		public void Save(String filepath) throws IOException { //saves output to text file and location specified by user
			BufferedWriter out= new BufferedWriter(new FileWriter(filepath));
			for(int i=0; i<formatted.size(); i++) {
				out.write(formatted.get(i));
				if(i< formatted.size() -1) {
					out.newLine();
				}
			}
			out.close();
		}
		
		
		
		//code below is just sample implementation of various settings. Logic might work for actual implementation but this code
		// does not work for actual implementation of the project as is.
		private void SampleFormat(ArrayList<String> test) { //left justified 1 column
			String temp= "";
			for(int i=0; i< test.size(); i++) {
				String line= test.get(i);
				String [] words= line.split(" ");
				for(int j=0; j< words.length; j++) {
					String add= words[j];
					if(temp.length() + add.length() +1 >80) {
						intermediate.add(temp);
						temp= "";
						temp= temp + add;
					}
					else {
						if(temp.length() == 0) {
							temp= temp + add;
						}
						else {
						temp= temp +" "+ add;
						}
					}
				}
			}
			if(temp.length()>0)
				intermediate.add(temp);
		}
		private void SampleFormat2(ArrayList<String> test) { // right justified 1 column
			String temp= "";
			for(int i=0; i< test.size(); i++) {
				String line= test.get(i);
				String [] words= line.split(" ");
				for(int j=0; j< words.length; j++) {
					String add= words[j];
					if(temp.length() + add.length() +1 >80) {
						while(temp.length() <80) {
							temp= " "+temp;
						}
						intermediate.add(temp);
						temp= "";
						temp= temp + add;
					}
					else {
						if(temp.length() == 0) {
							temp= temp + add;
						}
						else {
						temp= temp +" "+ add;
						}
					}
				}
			}
			if(temp.length()>0)
				while (temp.length() <80) {
					temp= " " +temp;
				}
				intermediate.add(temp);
		}
		
		private void SampleFormat3(ArrayList<String> test) { // block
			String temp= "";
			for(int i=0; i<10; i++) {
				temp= temp + " ";
			}
			for(int i=0; i< test.size(); i++) {
				String line= test.get(i);
				String [] words= line.split(" ");
				for(int j=0; j< words.length; j++) {
					String add= words[j];
					if(temp.length() + add.length() +1 >80) {
						intermediate.add(temp);
						temp= "";
						for(int z=0; z<10; z++) {
							temp= temp + " ";
						}
						temp= temp + add;
					}
					else {
						if(temp.length() == 10) {
							temp= temp + add;
						}
						else {
						temp= temp +" "+ add;
						}
					}
				}
			}
			if(temp.length()>0)
				intermediate.add(temp);
}
		private void SampleFormat4(ArrayList<String> test) { // indentation
			String temp= "";
			for(int i=0; i<5; i++) {
				temp= temp + " ";
			}
			for(int i=0; i< test.size(); i++) {
				String line= test.get(i);
				String [] words= line.split(" ");
				for(int j=0; j< words.length; j++) {
					String add= words[j];
					if(temp.length() + add.length() +1 >80) {
						intermediate.add(temp);
						temp= "";
						temp= temp + add;
					}
					else {
						if(temp.length() == 5) {
							temp= temp + add;
						}
						else {
						temp= temp +" "+ add;
						}
					}
				}
			}
			if(temp.length()>0)
				intermediate.add(temp);
}
		private void SampleFormat5(ArrayList<String> test) {// centered
			String temp = test.get(0);
			while(temp.length() <80) {
				if(temp.length() == 79) {
					temp= " "+ temp;
				}
				else {
					temp = " "+ temp + " ";
				}
			}
			intermediate.add(temp);
			temp= "";
			for(int i=1; i< test.size(); i++) {
				String line= test.get(i);
				String [] words= line.split(" ");
				for(int j=0; j< words.length; j++) {
					String add= words[j];
					if(temp.length() + add.length() +1 >80) {
						intermediate.add(temp);
						temp= "";
						temp= temp + add;
					}
					else {
						if(temp.length() == 0) {
							temp= temp + add;
						}
						else {
						temp= temp +" "+ add;
						}
					}
				}
			}
			if(temp.length()>0)
				intermediate.add(temp);
		}
		
		private void SampleFormat6(ArrayList<String> test) {// individual line of 2 column
			String temp= "";
			for(int i=0; i< test.size(); i++) {
				String line= test.get(i);
				String [] words= line.split(" ");
				for(int j=0; j< words.length; j++) {
					String add= words[j];
					if(temp.length() + add.length() +1 >35) {
						while(temp.length() <35) {
							temp= temp + " "; 
						}
						intermediate.add(temp);
						temp= "";
						temp= temp + add;
					}
					else {
						if(temp.length() == 0) {
							temp= temp + add;
						}
						else {
						temp= temp +" "+ add;
						}
					}
				}
			}
			if(temp.length()>0)
				intermediate.add(temp);
		}
		
}
