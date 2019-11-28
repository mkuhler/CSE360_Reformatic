import java.util.*;

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
					while(temp.length() <size) {
						temp= " "+temp+" ";
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
				temp= " " +temp+" ";
			}
			list.add(temp);
		}
		return list;
	}

	private void formatTwoColumns(ArrayList<String> lines) {
		for (int i=0; i<2; i++){
			int length =MATH;
				.max(
				maxLengths.get(i),
				lines[i].length()
				);
			maxLengths.set(i, length);
		}
		lines.add(Arrays.asList(line));

		return this; 
	}
