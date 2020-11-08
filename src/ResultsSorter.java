

public class ResultsSorter {
	public static String sortResults(String results){
		
		String tab[] = results.split(";\n");
		String sortedResult = "";
	        String temp;
	        for (int i = 1; i < tab.length; i++) {
	            for(int j = i ; j > 0 ; j--){
	            	int number1 = Integer.valueOf(tab[j].split(",")[1]);
	            	int number2 = Integer.valueOf(tab[j-1].split(",")[1]);
	                if(number1 > number2){
	                    temp = tab[j];
	                    tab[j] = tab[j-1];
	                    tab[j-1] = temp;
	                }
	            }
	        }
	        
	    for(String s : tab){
	    	if(s!=";\n" || s!="\n")
	    		sortedResult+=s + ";\n";
	    }
	       
	    return sortedResult;
		
	}
}
