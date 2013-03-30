package logicswarm.net.irc.mods;

import logicswarm.net.irc.IrcBot;
import logicswarm.net.irc.IrcModule;

/*
 * Description: Rejoins any channel the bot was kicked/parted from after a delay
 * Notes: incomplete
 * Author: Sean Nicholls
 * Date: today's date
 * 
 */
public class AutoJoin extends IrcModule {

	// data members
	public static final long 	serialVersionUID 	= 1;
	private int 				timeToSleep;
	private boolean				verbose;
	
	// constructor(s)
	public AutoJoin( IrcBot owner )
	{
		super(owner);
		initialize("AutoJoin");
	}
	public AutoJoin( int time, IrcBot owner )
	{
		super(owner);
		initialize("AutoJoin");
		
		timeToSleep = time * 1000;
	}
	public AutoJoin( boolean debug, IrcBot owner )
	{
		super(owner);
		initialize("AutoJoin");
		verbose = debug;
	}
	public AutoJoin( int time, boolean debug, IrcBot owner )
	{
		super(owner);
		initialize("AutoJoin");
		timeToSleep = time * 1000;
		verbose = debug;
	}

	// methods
	
	// rejoins a channel
	// note: present version only tries once
	// if an irc error occurs, ie: channel or 
	// server unavailable or something like that
	// it wont try again.
	// TODO: rejoin numerous times, upto a fixed limit
	private boolean reJoin( String channel )
	{			
		String[][] chans = getChannels();
		for( int i = 0; i < chans.length; i++ ) {				
			if(chans[i][0] != null && chans[i][0].equals(channel)){					
				try {
					Thread.sleep(timeToSleep);
					parent.joinChannel(chans[i][0], chans[i][1]);
					
					if(verbose)
						parent.log("[AutoJoin] rejoining channel " + chans[i]);
					
					return true;
				} catch(InterruptedException e) {
					
					if(verbose)
						parent.log("[AutoJoin] could not rejoin channel (could not sleep), aborting.");
					
					return false;
				}
			}
		}			
		return false;	

	}
	
	// events
	public void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason)
	{
		if(recipientNick.equals(parent.getNick())) {
			log(parent.getNick() + " was kicked from " + channel + ", autojoin in " + (timeToSleep / 1000) + "sec");
			reJoin(channel);
		}
	}
	public void onPart(String channel, String sender, String login, String hostname)
	{
		if(sender.equals(parent.getNick())) {
			log(parent.getNick() + " was parted from " + channel + ", autojoin in " + (timeToSleep / 1000) + "sec");
			reJoin(channel);
		}
	}
}
