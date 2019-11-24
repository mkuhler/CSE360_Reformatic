import java.io.*;
import java.util.*;

public class Processing {
		private ArrayList<String> formatted; // keeps track of total output after loading multiple files
		private ArrayList<String> intermediate= new ArrayList<String>(); // List that keeps formatted lines of current loaded file. 
		//intermediate is needed in case the file being loaded has invalid flags somewhere in the file. Intermediate is added to 
		//formatted once all the lines have been read in the file
		
		private char justification; // 'l'for left, 'r' for right, and 'c' for center
		private boolean single;  // true for single space, false for double spaced
		private boolean title;  // true for centered title otherwise does nothing
		private boolean indentation; // true for indentation otherwise does nothing
		private boolean block; // true for block format otherwise does nothing
		private boolean column1;// true for one column, false for two columns
		
		
		public Processing() {
			formatted = new ArrayList<String>();
		}
		
		public void readFile(String fileName) throws IOException{
			File file= new File(fileName);
			BufferedReader reader= new BufferedReader(new FileReader(file));
			ArrayList<String> linesToFormat = new ArrayList<String>();
			setDefaults();
			String str;
			str = reader.readLine();
			boolean firstFlag = true; // keeps track whether flag being read is the first flag in a formatting section
			while(str != null) { // reads file until end of file is reached
				if(str.length()>0) {
					if(str.charAt(0) == '-'){
						if(firstFlag) {// if first flag of new formatting section, then format all the lines that were read
							format(linesToFormat);
							if(!single)
								intermediate.add("");
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
				if(x == 'r' || x == 'c') {
					indentation= false;
					block= false;
					justification = x;
				}
				else if(x == 'l') {
					justification = x;
				}
				else if( x == 't') {
					title = true;
					indentation = false;
					block = false;
				}
				else if(x == 'n' ) {
					indentation = false;
				}
				else if(x == 'd') {
					single = false;
				}
				else if(x == 's') {
					single = true;
				}
				else if(x == 'i') {
					indentation = true;
					block = false;
					justification = 'l';
					column1 = true;
					title = false;
				}
				else if(x == 'b') {
					indentation = false;
					block = true;
					justification = 'l';
					column1 = true;
					title = false;
				}
				else if(x == '2') {
					indentation = false;
					block = false;
					column1 = false;
				}
				else if(x == '1') {
					column1 = true;
				}
				else if(x == 'e') {
					if(!single) {
						intermediate.add("");
						intermediate.add("");
					}
					else
						intermediate.add("");
				}
				else {
					return false;
				}
				return true;
			}
			else {
				return false;
			}
		}
		
		private void setDefaults() {
			justification= 'l'; 
			single = true;  
			title = false; 
			indentation = false; 
			block = false; 
			column1 = true;
		}
		
		private void format(ArrayList<String> lines) {
			if(title) {
				formatTitle(lines.get(0));
				if(!single)
					intermediate.add("");
				lines.remove(0);
			}
			if(indentation) {
				formatIndent(lines);
			}
			else if(block) {
				formatBlock(lines);
			}
			else if(justification == 'l') {
				ArrayList<String> temp =leftJustify(lines);
				if(column1) {
					for(int i=0; i<temp.size(); i++)
						intermediate.add(temp.get(i));
				}
				else
					formatTwoColumns(temp);
			}
			else if(justification == 'r') {
				ArrayList<String> temp =rightJustify(lines);
				if(column1) {
					for(int i=0; i<temp.size(); i++)
						intermediate.add(temp.get(i));
				}
				else
					formatTwoColumns(temp);
			}
			else if(justification == 'c') {
				ArrayList<String> temp =centerJustify(lines);
				if(column1) {
					for(int i=0; i<temp.size(); i++)
						intermediate.add(temp.get(i));
				}
				else
					formatTwoColumns(temp);
			}
		}
		
		private void formatTitle(String line) {
			String temp = line;
			while(temp.length() <80) {
				if(temp.length() == 79) {
					temp= " "+ temp;
				}
				else {
					temp = " "+ temp + " ";
				}
			}
			intermediate.add(temp);
		}
		
		private void formatIndent(ArrayList<String> lines) {
			String temp= "";
			for(int i=0; i<5; i++) {
				temp= temp + " ";
			}
			for(int i=0; i< lines.size(); i++) {
				String line= lines.get(i);
				String [] words= line.split(" ");
				for(int j=0; j< words.length; j++) {
					String add= words[j];
					if(temp.length() + add.length() +1 >80) {
						intermediate.add(temp);
						if(!single)
							intermediate.add("");
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
			if(temp.length()>0) {
				intermediate.add(temp);
			}
		}
		
		private void formatBlock(ArrayList<String> lines) {
			String temp= "";
			for(int i=0; i<10; i++) {
				temp= temp + " ";
			}
			for(int i=0; i< lines.size(); i++) {
				String line= lines.get(i);
				String [] words= line.split(" ");
				for(int j=0; j< words.length; j++) {
					String add= words[j];
					if(temp.length() + add.length() +1 >80) {
						intermediate.add(temp);
						if(!single)
							intermediate.add("");
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
			if(temp.length()>0) {
				intermediate.add(temp);
			}
		}
		
		private ArrayList<String> leftJustify(ArrayList<String> lines) {
			ArrayList<String> list = new ArrayList<String>();
			String temp= "";
			int size;
			if(column1) 
				size = 80;
			else
				size = 35;
			for(int i=0; i< lines.size(); i++) {
				String line= lines.get(i);
				String [] words= line.split(" ");
				for(int j=0; j< words.length; j++) {
					String add= words[j];
					if(temp.length() + add.length() +1 >size) {
						while(temp.length()<size) {
							temp= temp+ " ";
						}
						list.add(temp);
						if(!single && column1)
							list.add("");
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
			if(temp.length()>0) {
				while(temp.length()<size)
					temp= temp+ " ";
				list.add(temp);
			}
			return list;
		}
		
		private ArrayList<String> rightJustify(ArrayList<String> lines){
			ArrayList<String> list = new ArrayList<String>();
			String temp= "";
			int size;
			if(column1)
				size = 80;
			else
				size = 35;
			for(int i=0; i< lines.size(); i++) {
				String line= lines.get(i);
				String [] words= line.split(" ");
				for(int j=0; j< words.length; j++) {
					String add= words[j];
					if(temp.length() + add.length() +1 >size) {
						while(temp.length() <size) {
							temp= " "+temp;
						}
						list.add(temp);
						if(!single && column1)
							list.add("");
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
			if(temp.length()>0) {
				while (temp.length() <size) {
					temp= " " +temp;
				}
				list.add(temp);
			}
			return list;	
		}
		
		private ArrayList<String> centerJustify(ArrayList<String> lines){
			ArrayList<String> list = new ArrayList<String>();
			return list;
		}
		
		private void formatTwoColumns(ArrayList<String> lines) {
			
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
}

