package logicswarm.net.irc.mods;

import logicswarm.net.irc.IrcBot;
import logicswarm.net.irc.IrcModule;

/*
 * Description: Automatically register with the nickserv on connection
 * Notes: this version is dumb, should check for response, repeat etc.
 * Author: Sean Nicholls
 * Date: 04/01/09
 * 
 */
public class AutoRegister extends IrcModule {

	// data members
	public static final long 	serialVersionUID 	= 1;
	public String password = "ithinkthereforeispam";
	
	// constructor(s)
	public AutoRegister( IrcBot owner )
	{
		super(owner);
		initialize("AutoRegister");
	}

	// methods
	
	
	// events
	@Override
	public void onConnect()
	{
		parent.sendMessage("nickserv", "identify " + password);
	}
	
}
