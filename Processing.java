import java.io.*;
import java.util.*;

public class Processing {
		private ArrayList<Paragraph> formatted; // keeps track of total output after loading multiple files
		private ArrayList<Paragraph> intermediate= new ArrayList<Paragraph>(); // List that keeps formatted lines of current loaded file.
		//intermediate is needed in case the file being loaded has invalid flags somewhere in the file. Intermediate is added to
		//formatted once all the lines have been read in the file

		public Processing() {
			formatted = new ArrayList<Paragraph>();
		}

		public void readFile(String fileName) throws Exception{
			File file= new File(fileName);
			BufferedReader reader= new BufferedReader(new FileReader(file));
			String str;
			str = reader.readLine();
			Paragraph paragraph= new Paragraph();
			int count =0;
			boolean firstFlag = true; // keeps track whether flag being read is the first flag in a formatting section
			if (file.length() == 0){
				throw new EmptyFile("file empty");
			}
			while(str != null) { // reads file until end of file is reached
				if(str.length()>0) {
					if(str.charAt(0) == '-'){
						if(firstFlag) {// if first flag of new formatting section, then format all the lines that were read
							if(count>0) {
							intermediate.add(paragraph);
							paragraph = new Paragraph(paragraph);
							}
							firstFlag= false;
							count ++;
						}
						boolean valid= paragraph.checkFlag(str); // checks if flags are valid and sets flag values to variables
						if(!valid) { // if not valid then all lines formatted in this file are erased and error is thrown
							intermediate.clear();
							throw new InvalidFlag("bad flag");
						}
					}
					else {
						firstFlag = true;// since it is a regular line, then the next flag will be first flag of new section
						paragraph.addLine(str);
					}

				}
				str= reader.readLine();
			}
			if(paragraph.getUnformattedSize()>0) {
				intermediate.add(paragraph);
			}
			addToOutput();
			intermediate.clear();
			reader.close();
		}

		private void addToOutput() { // adds intermediate to the total output
			for(int i=0; i< intermediate.size(); i++) {
				intermediate.get(i).format();
				formatted.add(intermediate.get(i));
			}
		}

		public ArrayList<Paragraph> getOutput(){ // used for displaying output in preview window of GUI
			return formatted;
		}

		public void Save(String filepath) throws IOException { //saves output to text file and location specified by user
			BufferedWriter out= new BufferedWriter(new FileWriter(filepath));
			for(int i=0; i<formatted.size(); i++) {
				Paragraph temp= formatted.get(i);
				for(int j= 0; j<temp.getFormattedSize(); j++) {
					out.write(temp.getFormattedLine(j));
					if(j< temp.getFormattedSize()-1)
						out.newLine();
				}
				if(i<formatted.size()-1)
					out.newLine();
			}
			out.close();
		}
		public void clear() {
			formatted.clear();
		}
}

class InvalidFlag extends Exception{
	public InvalidFlag(String s) {
		super(s);
	}
	class EmptyFile extends Exception{
		public EmptyFile(String s) {
			super(s);
		}
}
