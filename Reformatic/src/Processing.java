import java.io.*;
import java.util.*;

public class Processing {
		private ArrayList<String> formatted;
		private ArrayList<String> test = new ArrayList<String>();
		
		public Processing() {
			formatted = new ArrayList<String>();
		}
		
		public void format(String fileName) throws IOException{
			File file= new File(fileName);
			BufferedReader br= new BufferedReader(new FileReader(file));
			String str;
			str = br.readLine();
			while(str != null) {
				if(str.length()>0) {
					if(str.charAt(0) == '-'){
						boolean valid= checkFlag(str);
						if(!valid) {
							// error handling goes here
						}
					}
					else {
						test.add(str);
					}
					
				}
				str= br.readLine();
			}
			br.close();
			SampleFormat2(test);
		}
		
		private void SampleFormat(ArrayList<String> test) { //left justified 1 column
			String temp= "";
			for(int i=0; i< test.size(); i++) {
				String line= test.get(i);
				String [] words= line.split(" ");
				for(int j=0; j< words.length; j++) {
					String add= words[j];
					if(temp.length() + add.length() +1 >80) {
						formatted.add(temp);
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
				formatted.add(temp);
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
						formatted.add(temp);
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
				formatted.add(temp);
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
						formatted.add(temp);
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
				formatted.add(temp);
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
						formatted.add(temp);
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
				formatted.add(temp);
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
			formatted.add(temp);
			temp= "";
			for(int i=1; i< test.size(); i++) {
				String line= test.get(i);
				String [] words= line.split(" ");
				for(int j=0; j< words.length; j++) {
					String add= words[j];
					if(temp.length() + add.length() +1 >80) {
						formatted.add(temp);
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
				formatted.add(temp);
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
						formatted.add(temp);
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
				formatted.add(temp);
		}
		
		public ArrayList<String> getOutput(){
			return formatted;
		}
		
		public void Save() throws IOException {
			BufferedWriter out= new BufferedWriter(new FileWriter("sample.txt"));
			for(int i=0; i<formatted.size(); i++) {
				out.write(formatted.get(i));
				if(i< formatted.size() -1) {
					out.newLine();
				}
			}
			out.close();
		}
		
		private boolean checkFlag(String str) {
			int num=0;
			for(int i=0; i<str.length(); i++) {
				if(str.charAt(i) != ' ' && str.charAt(i) != '	')
					num++;
			}
			if(num == 2) {
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
}
