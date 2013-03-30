package logicswarm.net.irc.mods;

import java.util.Random;

import logicswarm.net.irc.IrcBot;
import logicswarm.net.irc.IrcModule;
import logicswarm.util.LogReader;
import logicswarm.util.LogWriter;

/*
 * Description: Provides a chatroom quotation service
 * Notes: incomplete. bypasses filters
 * Author: Sean Nicholls
 * Date: 29/10/07
 * 
 */

public class Quotes extends IrcModule {

	// data members
	public static final long 	serialVersionUID 	= 1;
	
	private String 		path;
	private boolean 	debug;
	LogReader 			fin;
	private String 		chan;
	
	// constructor(s)
	public Quotes( String str , IrcBot owner )
	{
		super(owner);
		super.setName("Quotes");
		
		path 	= str;
		chan	= "";
		
		fin 	= new LogReader( path );
	}
	public Quotes( String str, boolean flag, IrcBot owner )
	{
		super(owner);
		super.setName("Quotes");
		
		path 	= str;
		debug 	= flag;
		chan	= "";
		
		fin 	= new LogReader( path , debug);
	}
	public Quotes( String str , String channel, IrcBot owner )
	{
		super(owner);
		super.setName("Quotes");
		
		path 	= str;
		chan 	= channel;
		
		fin 	= new LogReader( path , debug);
	}
	public Quotes( String str, String channel, boolean flag, IrcBot owner )
	{
		super(owner);
		super.setName("Quotes");
		
		path 	= str;
		debug 	= flag;
		chan	= channel;
		
		fin 	= new LogReader( path , debug);
	}
	
	// get a quote from the database
	public String getQuote( int index )
	{
		if( index-- < 0 )
			index = 0;
		
		String str = fin.getLine( index );
		
		if( str == null ) {
			return "Could not find quote number " + (index + 1) + ".";
		} else if(str.length() >= 5) {
			return (index + 1) + ": " + str;
		} else {
			return "Invalid quote at index " + (index + 1) + ".";
		}
	}
	
	// find a quote with the specified search string
	public String findQuote( String search )
	{
		search = search.toLowerCase();
		String[] hay = fin.toStringArray();
		
		for(int i = 0; i < hay.length; i++) {
			if(hay[i].toLowerCase().contains(search.toLowerCase()))
				return (i + 1) + ": " + hay[i];
		}

		return "No quote exists which matches that query.";
	}

	// find a quote with the specified regular expression
	public String findQuoteEx( String search )
	{
		search = search.toLowerCase();
		String[] hay = fin.toStringArray();
		
		for(int i = 0; i < hay.length; i++) {
			if(hay[i].matches(search))
				return "Quote " + (i + 1) + ": " + hay[i];
		}

		return "No quote exists which matches that query.";
	}
	
	// pick a quote at random
	public String getRandomQuote()
	{
		int count = getQuoteCount();
		
		if(count == 1) {
			return getQuote(1);
		} else if(count > 1) {
			Random r = new Random();		
			return getQuote( r.nextInt(count -1)+1 );
		} else {
			return "There are no quotes at this time. Why not add one?";
		}
	}
	
	// count the number of quotes are available
	public int getQuoteCount()
	{
		int c = fin.getLineCount();
		
		if(c <= 0) {
			return 0;
		} else {
			return c;
		}
	}
	
	// add a quote
	public String addQuote( String str )
	{
		LogWriter fout = new LogWriter( path , true );
		
		if(str.length() < 5) {
			return "Your quote '" + str + "' is too short, it has not been added.";
		} else if(fout.writeln(str)) {
			return "Quote added at number " + getQuoteCount();
		} else {
			return "Could not add your quote at this time.";
		}
	}
	
	// add a quote at a specified index
	public String insertQuote( int index , String str )
	{
		
		return "Could not add your quote at this time.";
	}
	
	// replace a quote at the given index
	public String replaceQuote( int index , String str )
	{
		index--;
		
		int len = getQuoteCount();
		
		if(index < 0 || index > len) {
			return "Could not replace quote at index " + index + " because it is an invalid index.";
		}
		
		String in[] = fin.toStringArray();
		String out[] = new String[len];
		
		for( int i = 0; i < len; i++ ) {
			if(i != index ) {
				out[i] = in[i];
			} else {
				out[i] = str;
			}
		}

		// clear file
		clearQuotes();
		
		// save to file
		LogWriter fout = new LogWriter( path , false );
		if(fout.writeStringArray(out)) {
			return "Sucessfully replaced quote number " + (index + 1) + ".";
		} else {	
			return "Could not replace your quote at this time.";
		}
	}
	
	// clear the database of all quotes
	public String clearQuotes()
	{
		LogWriter fout = new LogWriter( path , false );
		fout.close();
		
		if(getQuoteCount() <= 0) {
			return "The quotes database was sucessfully cleared.";
		} else {
			return "Could not sucessfully clear the quotes database.";
		}
	}
	
	// remove a specific quote
	public String removeQuote( int index )
	{
		index--;
		
		String[] str = fin.toStringArray();
		clearQuotes();
		LogWriter fout = new LogWriter( path , true );
		
		for( int i = 0; i < (str.length); i++ ) {
			if( i != index && str.length >= 5 ) {
				if(fout.writeln(str[i]) && debug){
					System.out.println("Saved quote " + i + " to file.");
				}else if(debug) {
					System.out.println("Could not save quote " + i + " to file!");
				}
			} else if(debug && i != index) {
				System.out.println("Could not write quote, possible cause: too short.");
			}
		}
		
		return "Sucessfully removed quote at index " + (index + 1);
	}
	
	
	// events
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{	
    	if(sender.equals("Anna"))		// ignore Anna
    		return;
    	
    	// quit if message isnt from our specified channel
    	if( chan.length() >= 2 && !chan.toLowerCase().equals(channel.toLowerCase()) )
    		return;
    	
    	if( message.startsWith("!") )	// commands go here
    	{
    		// turn message lower
    		String msg = message.toLowerCase();
    		
    	
    		if(msg.equals("!quote")) {
        		parent.sendMessage( channel , getRandomQuote() );
    		} else if( msg.indexOf("!quote add") == 0 ) {
    			
    			if(msg.length() <= 14) {
    				parent.sendMessage( channel , "Your quote is too short, it has not been added." );
    			} else if(msg.length() >= 400 ) {
    				parent.sendMessage( channel , "Your quote is too long, it has not been added." );
    			} else {
    				parent.sendMessage( channel , addQuote(message.substring(11)) );
    			}
    			
    		} else if( msg.indexOf("!quote remove") == 0) {
    			
    			String str = message.substring(14);
    			
    			if(str.length() >= 1) {
        			parent.sendMessage( channel , removeQuote( Integer.parseInt(str)) );
    			} else {
    				parent.sendMessage( channel, "Cannot process your request, index does not exist or badly formed." );
    			}
    			
    		} else if( msg.equals("!quote count") ) {
    			parent.sendMessage( channel , "There are currently " + getQuoteCount() + " quotes." );
    		} else if( msg.indexOf("!quote read") == 0 ) {
    			
    			String str = message.substring(12);
    			
    			if(str.length() >= 1) {
        			parent.sendMessage( channel , getQuote( Integer.parseInt(str)) );
    			} else {
    				parent.sendMessage( channel, "Cannot process your request, index does not exist or is badly formed." );
    			}
    			
    		} else if( msg.indexOf("!quote clear") == 0 ) {
				parent.sendMessage( channel , clearQuotes() );
    		}  else if( msg.indexOf("!quote findex") == 0 ) {
    			
    			String str = message.substring(14);
    			
    			if( str.length() >= 1 ) {
        			parent.sendMessage( channel, findQuoteEx(str) );
    			} else {
        			parent.sendMessage( channel, "Cannot process your request, search term is too short." );
    			}
    			
    		}  else if( msg.indexOf("!quote find") == 0 ) {
    			
    			String str = message.substring(12);
    			
    			if( str.length() >= 3 ) {
        			parent.sendMessage( channel, findQuote(str) );
    			} else {
        			parent.sendMessage( channel, "Cannot process your request, search term is too short." );
    			}
    			
    		} else if( msg.indexOf("!quote replace ") == 0) {

    			String str = message.substring(15 , message.indexOf(" ", 15));
    			String str2;
    			
				str2 = message.substring( 15 + str.length() + 1 );
    			
    			if(str.length() >= 1 && str2.length() >= 3 && str2.length() <= 400 ) {
    				parent.sendMessage( channel, replaceQuote( Integer.parseInt(str) , str2 ) );
    			} else {
    				parent.sendMessage( channel, "Cannot process your request, check your command syntax or quote length." );
    			}
    			
    		} else {
    			
    		} 
    	}
	}
	
}
