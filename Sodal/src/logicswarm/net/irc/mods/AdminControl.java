package logicswarm.net.irc.mods;

import logicswarm.net.irc.IrcBot;
import logicswarm.net.irc.IrcModule;
import logicswarm.net.irc.datatypes.ircData;
import logicswarm.util.ModAuth;
import logicswarm.util.CmdParser;

/*
 * Description: describe the mod here
 * Notes: n/a
 * Author: your name
 * Date: today's date
 * 
 */
public class AdminControl extends IrcModule {

	// data members
	public static final long 	serialVersionUID 	= 1;
	public ModAuth objAuth = new ModAuth();
	public CmdParser objCmd = new CmdParser();
	private String[][] commands =	{	{"!op","10","2","MODE $CHANNEL +o $1"},			// cmd | cmd level | property count | command to execute
										{"!deop","10","2","MODE $CHANNEL -o $1"},
										{"!hop","7","2","MODE $CHANNEL +hop $1"},
										{"!dehop","7","2","MODE $CHANNEL -hop $1"},
										{"!voice","5","2","MODE $CHANNEL +v $1"},
										{"!devoice","5","2","MODE $CHANNEL -v $1"},
										{"!join","5","1","JOIN $1"},
										{"!nick","7","1","NICK $1"},
										{"!topic","5","1","TOPIC $CHANNEL :$RAW"},
										{"!raw","10","1","$RAW"},
									};

	// constructor(s)
	public AdminControl( IrcBot owner )
	{
		super(owner);
		initialize("AdminControl");
	}


	// initializer
	public void onInitialize() 
	{	
		if(commands != null)
			objCmd.addCMD(commands);
	}
	
	// methods
	private void doCommand( String[] cmd, ircData data ) {
		
		for(int i=0; i<commands.length; i++) {
			if(cmd[0].toLowerCase().equals(commands[i][0]) && cmd.length >= Integer.parseInt(commands[i][2])) {
								
				// inserts the entire message given by the user
				// after the initial command
				String raw = commands[i][3];
				raw = raw.replace("$RAW", data.msg.substring(cmd[0].length() + 1));
				
				// numeric variables ($1, $2, $3) represent the id
				// of the command parameters the user supplied
				for(int j=0; j<cmd.length;j++)
					raw = raw.replace("$" + j, cmd[j]);
				
				// replaces the $CHANNEL variable with the name
				// of the channel
				raw = raw.replace("$CHANNEL", data.channel);
							
				// send the raw IRC data to the server
				parent.sendRawLineViaQueue(raw);
				
				// end the loop
				break;
			}
		}

		
	}
	// use data from input message, process it for commands etc
	private void proccessInput(ircData data)
	{
		
		// only act on commands
		if(data.msg.substring(0, 1).equals("!"))
		{
			log("Validating user " + data.sender + " as level " + objAuth.getUserLevel(data.login,data.sender,data.host));
			
			//debug
			log(data.sender + ", " + data.login + ", " + data.host);
			
			int lvl;
			if( (lvl = objAuth.getUserLevel(data.login,data.sender,data.host)) > 0 ) {
				
				// help command to show what actions are available
				if(data.msg.toLowerCase().equals("!help"))
				{
					String outMSG = "[AdminControl] available commands to you: ";
					for(int i=0; i<commands.length; i++)
					{
						if(lvl >= Integer.parseInt(commands[i][1]))
							outMSG += commands[i][0] + ", ";
					}
					
					// send help message
					parent.SendNotice(data.sender, outMSG);
					
				}	
				else if(data.msg.toLowerCase().equals("!quit"))
				{
					parent.dispose();
					return;
				}
				else if(data.msg.toLowerCase().equals("!admin-list"))
				{
					if(objAuth.users != null)
					{
						for(int j=0; j<objAuth.users.length; j++)
							parent.SendNotice(data.sender, "[AdminControl] " + objAuth.users[j][1] + ", level " + objAuth.users[j][3]);
					}
					return;
				}
				else
				{
					String[] out = objCmd.validateString(data.msg, lvl);
					if( out != null) {						
						try{
							doCommand(out,data);
						}
						catch(Exception e)
						{
							parent.log("[AdminControl] encountered an error: " + e.getMessage());
						}
					}
				}
			}
		}	
	}
	public void addUser( String[] user ) {
		objAuth.addUser( user );
	}
	public void addUsers( String[][] users ) {
		objAuth.addUser(users);
	}
	
	// events
	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		ircData data = new ircData();
			data.channel = channel;
			data.sender = sender;
			data.login = login;
			data.host = hostname;
			data.msg = message;
		proccessInput(data);
	}
	public void onPrivateMessage(String sender, String login, String hostname, String message)
	{
		ircData data = new ircData();
			data.sender = sender;
			data.login = login;
			data.host = hostname;
			data.msg = message;
		proccessInput(data);
	}	
}
