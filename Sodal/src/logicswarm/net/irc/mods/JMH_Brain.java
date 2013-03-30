package logicswarm.net.irc.mods;

import logicswarm.net.irc.IrcBot;
import logicswarm.net.irc.IrcModule;
import logicswarm.util.LogReader;
import logicswarm.util.LogWriter;

import org.jibble.jmegahal.JMegaHal;

/*
 * Description: Artificial AI simulator using the JMegaHal library, not very 'intelligent'.
 * Notes: incomplete.
 * Author: Sean Nicholls
 * Date: 29/10/07
 * 
 */

public class JMH_Brain extends IrcModule {

	// data members
	public static final long 	serialVersionUID 	= 1;
	
	public boolean 				verbose;	
	private LogWriter 			brainsaver;
	private String 				path;
	private static int 			memoryCapacity 		= 18000;
	private JMegaHal			brain;
	
	
	// constructor(s)	
	public JMH_Brain( String str , IrcBot owner )
	{
		super(owner);
		super.setName("Brain");
		
		path 	= str;
		
		brainsaver = new LogWriter(path, verbose);
		brain = new JMegaHal();
		loadBrain();
	}
	public JMH_Brain( String str , boolean flag, IrcBot owner )
	{
		super(owner);
		super.setName("Brain");
		
		path 		= str;
		verbose 	= flag;
		
		brainsaver = new LogWriter(path, verbose);
		brain = new JMegaHal();
		loadBrain();
	}
	
	// load the brain file
	public void loadBrain()
	{
		LogReader brainloader = new LogReader(path , verbose);
		
		if(verbose)
			System.out.println("loading brain file from: '" + brainloader.path + "'");
		
		String[] str = brainloader.toStringArray(memoryCapacity);

        for( int i = 0; i < memoryCapacity && str[i] != null; i++ ) {       	
        	if( str[i].length() > 5 )
            	brain.add( str[i] );
        }
	}

	// get a random 'intelligent' sentence
	public String getSentence()
	{
		String str = brain.getSentence();
		
		if(str.length() == 0) {
			return "I dont have anything to say right now.";
		} else {
			return str;
		}
	}
	
	// get a random 'intelligent' sentence, seeded with a word said in the sentence
	// must fix: convert sentence into an array of words and pick one at random.
	public String getSentence(String msg)
	{
		String str = brain.getSentence(msg);
		
		if(str.length() == 0) {
			return "I dont have anything to say right now.";
		} else {
			return str;
		}
	}	
	
	// add a sentence to the brain
	public void add( String msg )
	{
		brain.add(msg);
		brainsaver.writeln(msg);
	}	
		
	// events
	@Override
	public void onMessage(String channel, String sender,String login, String hostname, String message)
	{	
		if(!sender.equals("Anna") && !sender.equals("FSRadio") && !sender.equals(parent.getNick()))
		{
			if(message.indexOf("!") < 0){
				if( message.toLowerCase().indexOf( parent.getName().toLowerCase() ) >= 0 ){
					
					
					
					parent.SendMessage( channel , getSentence() );
				} else {
	    			add( message );
		    	}
			}
		}
	}
}
