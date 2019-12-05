import java.util.ArrayList;

public class Paragraph {
	private ArrayList<String> formatted= new ArrayList<String>();
	private ArrayList<String> paragraph= new ArrayList<String>();
	private char justification; // 'l'for left, 'r' for right, and 'c' for center
	private boolean single;  // true for single space, false for double spaced
	private boolean title;  // true for centered title otherwise does nothing
	private boolean indentation; // true for indentation otherwise does nothing
	private boolean block; // true for block format otherwise does nothing
	private boolean column1;// true for one column, false for two columns

	
	public Paragraph() {
		justification= 'l'; 
		single = true;  
		title = false; 
		indentation = false; 
		block = false; 
		column1 = true;
	}
	
	public Paragraph(Paragraph previous) {
		justification = previous.getJustification();
		single= previous.getSingle();
		indentation= previous.getIndentation();
		block = previous.getBlock();
		column1= previous.getColumn();
	}
	public char getJustification() {
		return justification;
	}
	public boolean getSingle() {
		return single;
	}
	public boolean getIndentation() {
		return indentation;
	}
	public boolean getBlock() {
		return block;
	}
	public boolean getColumn() {
		return column1;
	}
	public void addLine(String line) {
		paragraph.add(line);
	}
	public int getUnformattedSize() {
		return paragraph.size();
	}
	public int getFormattedSize() {
		return formatted.size();
	}
	public boolean checkFlag(String str) {
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
					formatted.add("");
					formatted.add("");
				}
				else
					formatted.add("");
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
	
	public void format() {
		if(title) {
			formatTitle(paragraph.get(0));
			if(!single)
				formatted.add("");
			paragraph.remove(0);
		}
		if(indentation) {
			formatIndent();
		}
		else if(block) {
			formatBlock();
		}
		else if(justification == 'l') {
			ArrayList<String> temp =leftJustify();
			if(column1) {
				for(int i=0; i<temp.size(); i++)
					formatted.add(temp.get(i));
			}
			else
				formatTwoColumns(temp);
		}
		else if(justification == 'r') {
			ArrayList<String> temp =rightJustify();
			if(column1) {
				for(int i=0; i<temp.size(); i++)
					formatted.add(temp.get(i));
			}
			else
				formatTwoColumns(temp);
		}
		else if(justification == 'c') {
			ArrayList<String> temp =centerJustify();
			if(column1) {
				for(int i=0; i<temp.size(); i++)
					formatted.add(temp.get(i));
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
		formatted.add(temp);
	}
	
	private void formatIndent() {
		String temp= "";
		for(int i=0; i<5; i++) {
			temp= temp + " ";
		}
		for(int i=0; i< paragraph.size(); i++) {
			String line= paragraph.get(i);
			String [] words= line.split(" ");
			for(int j=0; j< words.length; j++) {
				String add= words[j];
				if(temp.length() + add.length() +1 >80) {
					formatted.add(temp);
					if(!single)
						formatted.add("");
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
			formatted.add(temp);
			if(!single)
				formatted.add("");
		}
	}
	
	private void formatBlock() {
		String temp= "";
		for(int i=0; i<10; i++) {
			temp= temp + " ";
		}
		for(int i=0; i< paragraph.size(); i++) {
			String line= paragraph.get(i);
			String [] words= line.split(" ");
			for(int j=0; j< words.length; j++) {
				String add= words[j];
				if(temp.length() + add.length() +1 >80) {
					formatted.add(temp);
					if(!single)
						formatted.add("");
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
			formatted.add(temp);
			if(!single)
				formatted.add("");
		}
	}
	
	private ArrayList<String> leftJustify() {
		ArrayList<String> list = new ArrayList<String>();
		String temp= "";
		int size;
		if(column1) 
			size = 80;
		else
			size = 35;
		for(int i=0; i< paragraph.size(); i++) {
			String line= paragraph.get(i);
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
			if(!single&& column1)
				formatted.add("");
		}
		return list;
	}
	
	private ArrayList<String> rightJustify(){
		ArrayList<String> list = new ArrayList<String>();
		String temp= "";
		int size;
		if(column1)
			size = 80;
		else
			size = 35;
		for(int i=0; i< paragraph.size(); i++) {
			String line= paragraph.get(i);
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
			if(!single && column1)
				formatted.add("");
		}
		return list;	
	}
	
	private ArrayList<String> centerJustify(){
		ArrayList<String> list = new ArrayList<String>();
		String temp= "";
		int size;
		if(column1)
			size = 80;
		else
			size = 35;
		for(int i=0; i< paragraph.size(); i++) {
			String line= paragraph.get(i);
			String [] words= line.split(" ");
			for(int j=0; j< words.length; j++) {
				String add= words[j];
				if(temp.length() + add.length() +1 >size) {
					temp = centerHelper(temp, size);
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
			temp = centerHelper(temp, size);
			list.add(temp);
			if(!single && column1)
				formatted.add("");
		}
		return list;
	}
	
	private String centerHelper(String line, int size) {
		String result = "";
		String [] words= line.split(" ");
		int spaces = size - line.length();
		if(words.length == 1) {
			result = result +words[0];
		}
		else {
			int evenDistribution = spaces / (words.length -1);
			int oddDistribution= spaces % (words.length -1);
			for(int i=0; i< words.length; i++) {
				result= result + words[i];
				if(i < words.length -1) { 
					result = result + " ";
					for(int j = 0; j< evenDistribution; j++) {
						result = result + " ";
					}
					if(i < oddDistribution) {
						result = result + " ";
					}
				}
			}
		}
		return result;
	}
	
	private void formatTwoColumns(ArrayList<String> lines) {
		int size= lines.size();
		int halfSize;
		if(size %2 == 0) {
			halfSize = size/2;
		}
		else {
			halfSize = size/2 +1;
		}
		for(int i=0; i<halfSize; i++) {
			String temp= lines.get(i);
			if(i+ halfSize < size) {
				for(int j=0; j<10; j++) {
					temp= temp+ " ";
				}
				temp= temp+lines.get(i+halfSize);
			}
			formatted.add(temp);
			if(!single)
				formatted.add("");
		}
	}
	
	public void printParagraph() {
		for(int i=0; i< formatted.size(); i++)
			System.out.println(formatted.get(i));
	}
	
	public String getFormattedLine(int index) {
		return formatted.get(index);
	}
}
