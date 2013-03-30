package logicswarm.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

@Deprecated
public class LogReader implements Serializable {

	public static final long 	serialVersionUID 	= 1;
	public boolean debug;
	public String path;
	public BufferedReader fin;
	
	public LogReader( String str )
	{
		path = str;
		openFile();
	}
	public LogReader( String str , boolean flag )
	{
		path = str;
		debug = flag;
		openFile();
	}
	
	// prepare the file for reading by opening it
	private boolean openFile()
	{
		try {
			fin = new BufferedReader( new FileReader( path ) );
			return true;
		} catch ( IOException e ) {
			if(debug)
				System.out.println( "Could not open file: " + e.getMessage() );
			return false;
		}
	}
	
	// read a line
	public String readln()
	{
		try {
			if(openFile()) {
				return fin.readLine();
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}
	
	// get the number of lines
	public int getLineCount()
	{
		int i;
		
		try {
			if(openFile()) {
				for( i = 0; fin.readLine() != null; i++ ) {
					// do nothing; all we want to do is increment i.
				}
				return i;
			}
		} catch(IOException e) {
			return 0;
		}
		
		return 0;
	}
	
	// read a given line
	public String getLine( int line )
	{
		String str;
		
		try {
			if(openFile()) {
				for( int i = 0; (str = fin.readLine()) != null; i++ ) {
					if( i == line )
						return str;						
				}
			}
		} catch(IOException e) {
			return null;
		}
		
		return null;
	}
	
	// read a specified number of lines of a file as a string array
	public String[] toStringArray( int max )
	{		
		String[] strOut = new String[max];
		String str;
		
		try {
			if(openFile()) {
				for( int i = 0; (str = fin.readLine()) != null && i < max; i++ )
					strOut[i] = str;
			}
		} catch(IOException e) {
			return null;
		}
		
		return strOut;
	}
	
	// read the entire file to a string array
	public String[] toStringArray()
	{		
		return toStringArray(getLineCount());
	}
	
	// read a specified number of lines into a string
	public String toString( int max )
	{
		String strOut = new String();
		String str;
		
		try {
			if(openFile()) {
				for( int i = 0; (str = fin.readLine()) != null && i < max; i++ )
					strOut += str;
			}
		} catch(IOException e) {
			return null;
		}
		
		return strOut;
	}
	
	// read entire file into a string
	@Override
	public String toString()
	{
		return toString(getLineCount());
	}
}
