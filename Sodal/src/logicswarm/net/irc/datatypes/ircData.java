package logicswarm.net.irc.datatypes;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.User;

public class ircData {

	public String channel;
	public String sourceNick;
	public String sourceLogin;
	public String sourceHostname;
	public String filename;
	public String recipient;
	public String oldNick;
	public String newNick;
	public String topic;
	public String setBy;
	public String response;
	public String sender;
	public String host;
	public String login;
	public String msg;
	
	public User[] users;
	
	public DccFileTransfer transfer;
	
	public Exception e;
		
	public long address;
	public long date;
	
	public int port;
	public int size;
	public int limit;
	public int code;
	public int userCount;
	
	public boolean changed;
		
}
