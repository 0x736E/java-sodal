package logicswarm.net.test;

import logicswarm.util.Arrays;

public class arraytest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// measure the max size of an array //
		String[][][] test = {
								{
									{ "000", "001", "002" },
								},

								{
									{ "100", "101", "102" },
									{ "110", "111", "112" },
								},

								{
									{ "200", "201", "202" },
									{ "210", "211", "212", "213" },
									{ "220", "221", "222" },
								}
							};
		
		// result should be: [3][3][4] //
		
		// how big is the array?
		int[] size = Arrays.maxArrayIndexSize(test);
		
		for(int i=0; i<size.length; i++)
			System.out.print("[" + size[i] + "]");
		
		System.out.println();
				
		// append something to the array
		String[][][] tmp = { { { "300", "301", "303" , "304" , "305" } } };
		test = Arrays.appendArray(test, tmp);
				
		// result should be: [4][3][4] //
		
		// is it the right size?
		size = Arrays.maxArrayIndexSize(test);
		
		for(int i=0; i<size.length; i++)
			System.out.print("[" + size[i] + "]");
		
		System.out.println();
		
		// lets see
		Arrays.printStringMatrix(test);
		
		
	}

}
