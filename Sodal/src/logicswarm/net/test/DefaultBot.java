
package logicswarm.net.test;

import logicswarm.net.irc.*;
import logicswarm.net.irc.mods.*;

public class DefaultBot {

	private static String[][] chan		= 	{ {"#Sodal",""}};
	private static String nick			= 	"Sodal";
	private static String host			= 	"irc.server.tld";
	private static int port				= 	6667;
	private static boolean debug		= 	true;
	private static String[][] opers		= 	{ 	
												{ "IDENT", "USER", ".+", "10" },
											};
	
	public static void main(String[] args) {

		// basic bot info
		IrcBot sodal = new IrcBot( nick , host, "" , port, debug );
		sodal.verbose(debug);
				
		/////////////////
		// add plugins //
		/////////////////
		
		// module manager
		sodal.addMod(new ModuleMan(sodal));
				
		// administrator control
		AdminControl acon = new AdminControl(sodal);
		acon.addUsers(opers);
		sodal.addMod(acon);
				
		// auto reconnect
		AutoReconnect reconnect = new AutoReconnect(60, true, sodal);
		sodal.addMod(reconnect);
		
		// auto join		
		AutoJoin ajoin = new AutoJoin(10, true, sodal);
		sodal.addMod(ajoin);
		
		// create a chat logger for each channel
		ChatLogger chlog[] = new ChatLogger[chan.length];
		int index = -1;
		for(int i=0; i<chan.length; i++) {
			chlog[i] = new ChatLogger(chan[i][0] + ".log",sodal);			
			index = sodal.addMod(chlog[i]);
			sodal.applyModToAllChannels(index, false);
			sodal.addModChannel(index, chan[i][0]);
		}
				
		// quotes
		Quotes quote = new Quotes("jquotes.log",true,sodal);
		sodal.addMod(quote);
		
		// brain
		JMH_Brain brain = new JMH_Brain("jbrain.log",true,sodal);
		sodal.addMod(brain);
		
		
		/////////////////
		// connect	   //
		/////////////////	
		
		if(sodal.connect()) {
			sodal.joinChannels(chan);
		}
		else {
			sodal.log("Could not connect to server.");
		}
		
	}
}
