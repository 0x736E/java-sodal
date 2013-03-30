package logicswarm.net.irc.mods;

import logicswarm.net.irc.IrcBot;
import logicswarm.net.irc.IrcModule;

/*
 * Description: Automatically reconnects to the server when disconnected.
 * Notes: incomplete.
 * Author: Sean Nicholls
 * Date: 29/10/07
 * 
 */

public class AutoReconnect extends IrcModule {

	// data members
	public static final long 	serialVersionUID 	= 1;
	
	private int 				timeToSleep;
	private boolean				verbose;
	private int					max_attempts		= 3;
	private int					attempts;
	
	// constructor(s)
	public AutoReconnect( int temp , IrcBot owner )
	{
		super(owner);
		initialize("AutoReconnect");
		
		timeToSleep 	= temp * 1000;
	}
	public AutoReconnect( int temp, int maxAttempts , IrcBot owner )
	{
		super(owner);
		initialize("AutoReconnect");
		
		timeToSleep 	= temp * 1000;
		max_attempts 	= maxAttempts;
	}
	public AutoReconnect( int temp , boolean verb, IrcBot owner )
	{
		super(owner);
		initialize("AutoReconnect");
		
		timeToSleep 	= temp * 1000;
		verbose 		= verb;
	}
	public AutoReconnect( int temp, int maxAttempts , boolean verb, IrcBot owner )
	{
		super(owner);
		initialize("AutoReconnect");
		
		timeToSleep 	= temp;
		max_attempts 	= maxAttempts;
		verbose 		= verb;
	}
	
	// methods
	private void reconnect()
	{
		if(attempts++ >= max_attempts)
			return;
		
		try {			
    		Thread.sleep(timeToSleep);
    		
    		if(verbose)
    			log("re-connecting to '" + parent.getServer() + "'");
    		
    		parent.connect();
    		if(parent.isConnected()) {
    			attempts = 0;
    			for( int i = 0; i < getChannels().length; i++ )
    				parent.joinChannel(getChannels()[i][0], getChannels()[i][1]);
    		} else {
    			reconnect();
    		}
		} catch(InterruptedException e) {
			if(verbose)
				log("could not reconnect: " + e.getMessage());	
		}
	}
	
	// events
	@Override
	public void onDisconnect()
	{
    	if( timeToSleep > 0 ) {
			if(verbose)
				log("Disconnected from server, AutoReconnect in " + (timeToSleep / 1000) + "sec" );
			
			reconnect();
    	}
	}	
	@Override
	public void onNickInUse()
	{
    	if( timeToSleep > 0 ) {
			if(verbose)
				log("Disconnected from server (Nick in use), AutoReconnect in " + (timeToSleep / 1000) + "sec" );
			
			reconnect();
    	}
	}
	@Override
	public void onHostUnreachable()
	{
    	if( timeToSleep > 0 ) {
			if(verbose)
				log("Disconnected from server (Host unreachable), AutoReconnect in " + (timeToSleep / 1000) + "sec" );
			
			reconnect();
    	}
	}
	
}
