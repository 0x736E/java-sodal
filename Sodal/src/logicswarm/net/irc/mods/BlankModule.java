package logicswarm.net.irc.mods;

import logicswarm.net.irc.IrcBot;
import logicswarm.net.irc.IrcModule;

/*
 * Description: describe the mod here
 * Notes: n/a
 * Author: your name
 * Date: today's date
 * 
 */

/*
 * READ ME
 * 
 * When developing a module, there are some important
 * concepts you need to bear in mind while programming.
 * 
 * 1)	Many callback methods (onKick etc) are only
 * 		called if the module is 'in that channel'. 
 * 		it determains this by comparing the channels
 * 		array using the IrcModule.hasChannels(..) 
 * 		method. 
 * 
 * 		When using these callbacks, ensure that the
 * 		module has been passed the channels you wish 
 * 		to monitor like so:
 * 
 * 		myMod mod = new myMod(..);
 * 		mod.addChannels(..);
 * 
 * 
 */
public class BlankModule extends IrcModule {

	// data members
	public static final long 	serialVersionUID 	= 1;
	
	// constructor(s)
	public BlankModule( IrcBot owner )
	{
		super(owner);
		initialize("BlankModule");
	}
	
	// initialise
	public void onInitialize()
	{
		// TODO: initialise your module's variables
		myMethod();
	}

	// methods
	private void myMethod()
	{
		// TODO: create your own methods
		log("Hello World");
	}
	
	// events
	@Override
	public void onConnect()
	{
		// TODO: connection handling
	}
	@Override
	public void onDisconnect()
	{
		// TODO: disconnection handling
	}
	@Override
	public void onMessage(String channel, String sender,String login, String hostname, String message)
	{
		// TODO: message handling
	}
	@Override
	public void onAction(String sender, String login, String hostname, String target, String action)
	{
		// TODO: action handling
	}
	
}
