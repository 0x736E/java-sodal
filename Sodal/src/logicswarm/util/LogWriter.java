package logicswarm.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
@Deprecated
public class LogWriter implements Serializable
{
	// data members
	public static final long 	serialVersionUID 	= 1;
	public boolean debug;
	public boolean append;	
	public String path;
	private BufferedWriter fout;
	
	// constructor(s)
	public LogWriter( String str , boolean aflag )
	{
		path = str;
		append = aflag;
		openFile();
	}	
	public LogWriter( String str , boolean aflag, boolean dflag )
	{
		path = str;
		append = aflag;
		debug = dflag;
		openFile();
	}
	
	// prepare the file for writing, by opening it
	private boolean openFile()
	{
		try {
			fout = new BufferedWriter( new FileWriter( path , append ) );
			return true;
		} catch ( IOException e ) {
			if(debug)
				System.out.println( "Could not open file: " + e.getMessage() );
			return false;
		}
	}
	
	// close, and thereby, save the file
	private void saveFile() throws IOException
	{
		if( fout == null ) {
			throw new IOException();
		} else {
			fout.close();
		}
	}
	
	// close the file
	public boolean close()
	{
		try {
			fout.close();
			return true;
		} catch( IOException e ) {
			return false;
		}
	}
	
	// write a line
	public boolean writeln( String str )
	{	
		if(debug)
			System.out.println("attempting to write to file...");
		
		try {
			if(!openFile())
				throw new IOException();

			fout.write(str);
			fout.newLine();
						
			saveFile();
			return true;
		} catch ( IOException e ) {
			if(debug)
				System.out.println( "Could not save file: " + e.getMessage() );
			return false;
		}		
	}
	
	// write an entire string array
	public boolean writeStringArray( String[] str )
	{
		if(debug)
			System.out.println("attempting to write to file...");
		
		try {
			if(!openFile())
				throw new IOException();

			for( int i = 0; i < str.length; i++ ) {
				
				if(debug)
					System.out.println("writing [" + i + "] " + str[i]);

				fout.write(str[i]);
				fout.newLine();
			}
						
			saveFile();
			return true;
		} catch ( IOException e ) {
			if(debug)
				System.out.println( "Could not save file: " + e.getMessage() );
			return false;
		}
	}
	
	
}
