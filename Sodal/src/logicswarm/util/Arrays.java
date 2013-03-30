package logicswarm.util;

public final class Arrays {

	
	// append to an array //
	public static int[] appendArray( int[] in, int val ) {
		int[] out = new int[in.length + 1];
		for(int i=0; i<in.length; i++){
			out[i] = in[i];
		}
		out[in.length] = val;
		return out;
	}
	public static char[] appendArray( char[] in, char val ) {
		char[] out = new char[in.length + 1];
		for(int i=0; i<in.length; i++){
			out[i] = in[i];
		}
		out[in.length] = val;
		return out;
	}
	public static String[] appendArray( String[] in, String val ) {
		String[] out = new String[in.length + 1];
		for(int i=0; i<in.length; i++){
			out[i] = in[i];
		}
		out[in.length] = val;
		return out;
	}
	public static String[][] appendArray( String[][] in, String[][] val ) {			// join two arrays
		
		if(in == null) {
			return val;
		}
		
		// init
		int[] size = maxArrayIndexSize(in);
		int[] sizeb = maxArrayIndexSize(val);
		
		int[] sizec = size;
		
		if(sizeb[0] > size[0])
			sizec[0] = sizeb[0];
		
		if(sizeb[1] > size[1])
			sizec[1] = sizeb[1];
				
		String[][] out = new String[in.length + val.length][sizec[1]];
		
		// clone
		for(int i=0; i<in.length; i++){
			for(int j=0; j<in[i].length; j++){
				out[i][j] = in[i][j];
			}
		}
		
		// append
		int v = 0;
		for(int i=in.length; i<out.length; i++) {
			for(int j=0; j<sizec[1]; j++) {
				out[i][j] = val[v][j];
			}
			v++;
		}
		
		return out;
	}
	public static String[][][] appendArray( String[][][] in, String[][][] val ) {
		
		// init
		int[] inSize = maxArrayIndexSize(in);
		int[] valSize = maxArrayIndexSize(val);
		
		// measure how big the new array should be
		int[] size = { in.length + val.length, inSize[1], inSize[2] };
	
		if(valSize[1] > inSize[1])
			size[1] = valSize[1];
		
		if(valSize[2] > inSize[2])
			size[2] = valSize[2];
		
		// create a new array
		String[][][] out = new String[size[0]][size[1]][size[2]];
		
		// clone
		for(int i=0; i<in.length; i++){
			for(int j=0; j<in[i].length; j++){
				for(int k=0; k<in[i][j].length; k++)
					out[i][j][k] = in[i][j][k];
			}
		}
		
		// append
		int v = 0;
		for(int i=inSize[0]; i<(inSize[0] + val.length); i++, v++) {
			for(int j=0; j<val[v].length; j++) {
				for(int k=0; k<val[v][j].length; k++)
					out[i][j][k] = val[v][j][k];
			}
		}
		
		return out;
	}
	public static boolean[] appendArray(boolean[] in, boolean val)
	{
		if(in == null || in.length == 0)
		{
			boolean[] out = { val };
			return out;			
		}
		else 
		{
			boolean[] out = expandArray(in,1);
			out[out.length - 1] = val;
			return out;
		}
	}
	
	// unique append //
	public static String[] uniqueAppendArray(String[] in, String val)			// Append a string to an array, but only if it doesnt already exist
	{
		if(in == null || in.length <=0){
			String[] out = { val };
			return out;
		}

		
		String[] out = new String[in.length + 1];
		
		for(int i=0; i<in.length; i++){
			if(in[i].equalsIgnoreCase(val)){
				return in;
			} else {
				out[i] = in[i];
			}
		}
		
		out[in.length] = val;
		return out;
	}
	public static String[][] uniqueAppendArray(String[][] in, String val) {		// Append element to a string array
		if(!existsInArray( in, val )) {
			String[][] tmp = { { val , "" } };
			String[][] out = appendArray(in, tmp);
			return out;
		}
		
		return in;
	}
		
	// remove from an array //
	public static int[] removeFromArray( int[] in , int index ) {
		int[] out = new int[in.length - 1];
		for(int i=0, pos = 0; i<in.length; i++) {
			if(i != index) {
				out[pos] = in[i];
				pos++;
			}
		}
		
		return out;
	}
	public static char[] removeFromArray( char[] in , int index ) {
		char[] out = new char[in.length - 1];
		for(int i=0, pos = 0; i<in.length; i++) {
			if(i != index) {
				out[pos] = in[i];
				pos++;
			}
		}
		
		return out;
	}
	public static String[] removeFromArray( String[] in , int index ) {
		String[] out = new String[in.length - 1];
		for(int i=0, pos = 0; i<in.length; i++) {
			if(i != index) {
				out[pos] = in[i];
				pos++;
			}
		}
		
		return out;
	}
	public static String[][] removeFromArray( String[][] in, int index ) {
		String[][] out = new String[in.length - 1][in[0].length];
		for(int i=0, pos = 0; i<in.length; i++) {
			if(i != index) {
				out[pos] = in[i];
				pos++;
			}
		}
		
		return out;
	}
	
	// remove item from an array//
	public static String[] removeFromArray( String[] in, String element ) {
		
		if(in == null)
			return in;
		
		String[] out = new String[in.length];
		for(int i=0; i<out.length; i++)
			out[i] = in[i];		
		
		for(int i=0; i<in.length; i++){
			if(in[i].equalsIgnoreCase(element)) {
				out = removeFromArray(in,i);
			}
		}
		
		return out;
		
	}
	public static String[][] removeFromArray( String[][] in, String element ) { 	// searches first index
		
		if(in == null)
			return in;
		
		String[][] out = new String[in.length][in.length];
		for(int i=0; i<out.length; i++)
			out[i] = in[i];		
		
		for(int i=0; i<in.length; i++){
			if(in[i][0].equalsIgnoreCase(element)) {
				out = removeFromArray(in,i);
			}
		}
		
		return out;
		
	}
	
	// search array //
	public static boolean existsInArray( int[] in, int search ) {
		
		for(int i=0; i<in.length; i++) {
			if(in[i] == search) {
				return true;
			}
		}
		
		return false;
	}
	public static boolean existsInArray( char[] in, char search ) {
		
		for(int i=0; i<in.length; i++) {
			if(in[i] == search) {
				return true;
			}
		}
		
		return false;
	}
	public static boolean existsInArray( String[] in, String search ) {
		
		for(int i=0; i<in.length; i++) {
			if(in[i].equals(search)) {
				return true;
			}
		}
		
		return false;
	}
	public static boolean existsInArray( String[][] in, String search ) {		// searches first index for needle
		if(in == null)
			return false;
		
		for(int i=0; i<in.length; i++) {
			if(in[i][0].equalsIgnoreCase(search))
				return true;
		}
		return false;
	}
	
	// explode a string into an array
	public static String[] explode( String str, String delimiter ) {
		
		if(str.indexOf(delimiter) > 0) {
			
			// init
			int numItems = 0,pos = 0;
			
			// count
			do {
				numItems++;
			} while( (pos = str.indexOf(delimiter, pos + 1)) > 0 );
			
			// intialize string
			String[] out = new String[numItems];
			
			// do it
			int start = 0, end = str.indexOf(delimiter);
			for(int i=0; i<numItems; i++) {
				out[i] = str.substring(start, end);				
				
				start = end + 1;
				if((end = str.indexOf(delimiter, start)) == -1 )
					end = str.length();
			}
			
			// return to caller
			return out;
			
		} else {
			String[] out = { str };
			return out;
		}
	}
	public static String implode( String[] str, String delimiter ) {
		String out = "";
		for(int i=0; i<str.length - 1; i++) {
			out += str[i] + delimiter;
		}
		out += str[ str.length - 1 ];
		return out;
	}
	public static String[][][] expandArray(String[][][] str, int[] num)		// add n* amount of empty array entries
	{
		if(str == null || str.length == 0)
			return new String[num[0]][num[1]][num[2]];
		
		// resize array
		int[] size = maxArrayIndexSize(str);
		
		String[][][] out = new String[size[0] + num[0]][size[1] + num[1]][size[2] + num[2]];
		
		// fill with old values
		for(int i=0; i<str.length; i++)
		{
			for(int j=0; j<str[i].length; j++)
			{
				for(int k=0; k<str[i][j].length; k++)
				{
					out[i][j][k] = str[i][j][k];
				}
			}			
		}
		
		return out;
	}
	public static boolean[] expandArray(boolean[] Bool, int num)		// add n* amount of empty array entries
	{
		if(Bool == null || Bool.length == 0)
			return new boolean[0];
		
		// resize array		
		boolean[] out = new boolean[Bool.length + 1];
		
		// fill with old values
		for(int i=0; i<Bool.length; i++)
		{
			out[i] = Bool[i];
		}
		
		return out;
	}
	
	// measure the array
	public static int[] maxArrayIndexSize(String[][] str)				// find the maximum length of each index of an array
	{
		if(str == null){
			int[] out = { 0, 0 };
			return out;
		}
		
		
		int[] out = { 0, 1 };				// 1-based value index, instead of 0
		int[] temp = new int[2];
		
		// loop through every index
		// counting each entry
		// if this loop has more than
		// the last, put it in out[][][]
		
		for(int i=0; i<str.length; i++)
		{
			out[0]++;
			
			for(int j=0; j<str[i].length; j++)
			{
				if(temp[1]++ >= out[1])
					out[1] = temp[1];
			}
			
			temp[1] = 0;
		}
		
		return out;
	}
	public static int[] maxArrayIndexSize(String[][][] str)				// find the maximum length of each index of an array
	{
		int[] out = { 0, 1, 1 };				// 1-based value index, instead of 0
		int[] temp = new int[3];
		
		// loop through every index
		// counting each entry
		// if this loop has more than
		// the last, put it in out[][][]
		
		for(int i=0; i<str.length; i++)
		{
			out[0]++;
			
			for(int j=0; j<str[i].length; j++)
			{
				if(temp[1]++ >= out[1])
					out[1] = temp[1];
				
				for(int k=0; k<str[i][j].length; k++)
				{
					if(temp[2]++ >= out[2])
						out[2] = temp[2];
				}
				
				temp[2] = 0;
			}
			
			temp[1] = 0;
		}
		
		return out;
	}
	
	
	// print out a string matrix
	// helpful for testing purposes
	// prints out the string matrix
	// onto the console in human
	// readable form
	public static void printStringMatrix(String[][][] str)
	{
		for(int i=0; i<str.length; i++)
		{
			System.out.print("\n\t{\n\t\t");
			for(int j=0; j<str[i].length; j++)
			{
				System.out.print("{ ");
				for(int k=0; k<str[i][j].length; k++)
				{
					System.out.print(str[i][j][k]);
					
					if(k != str[i][j].length -1 )
						System.out.print(",\t");
				}
				System.out.print(" }\n\t\t");
			}
			System.out.print("\n\t}\n");		
		}
	}

}
