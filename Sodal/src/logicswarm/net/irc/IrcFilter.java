package logicswarm.net.irc;

import logicswarm.net.irc.datatypes.ircData;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.User;

public class IrcFilter {
	
	// data members
	public static final long 	serialVersionUID 	= 1;
	public IrcBot				parent;
	public boolean				status				= false;	// initial state is off
	private String				name				= "UnknownFilter";

	// constructor
	public IrcFilter( IrcBot owner )
	{
		parent = owner;
	}
	public IrcFilter( boolean state, IrcBot owner )
	{
		parent = owner;
		status = state;
	}	

	// destructor
	@Override
	public void finalize()
	{
	}
	
	// dispose of this filter
	protected void dispose()
	{
		finalize();
		
		// do stuff
	}
	
	// methods
	public final void Activate()
	{
		status = true;
	}
	public final void Deactivate()
	{
		status = false;
	}
	public final void setName( String str )
	{
		name = str;
	}
	public final String getName()
	{
		return name;
	}
	
	// custom events//
	public String[] onSendAction(String target, String action)
	{
    	return new String[] { target, action };
	}
	public String[] onSendMessage(String target, String message)
	{
    	return new String[] { target, message };
	}
	public String[] onSendNotice(String target, String notice) 
	{
    	return new String[] { target, notice };		
	}

    // PircBot events //
    public String[] onAction(String sender, String login, String hostname, String target, String action)
    {
    	return new String[] { sender, login, hostname, target, action };
    }   
    public ircData onChannelInfo(String channel, int userCount, String topic) 
    {
    	ircData data = new ircData();
    		data.channel = channel;
    		data.userCount = userCount;
    		data.topic = topic;
    		
    	return data;
    }
    public ircData onDccChatRequest(String sourceNick, String sourceLogin, String sourceHostname, long address, int port)
    {
    	ircData data = new ircData();
    		data.sourceNick = sourceNick;
    		data.sourceLogin = sourceLogin;
    		data.sourceHostname = sourceHostname;
    		data.address = address;
    		data.port = port;
    		
    	return data;
    } 
    public ircData onDccSendRequest(String sourceNick, String sourceLogin, String sourceHostname, String filename, long address, int port, int size) 
    {
    	ircData data = new ircData();
    		data.sourceNick = sourceNick;
    		data.sourceLogin = sourceLogin;
    		data.sourceHostname = sourceHostname;
    		data.filename = filename;
    		data.address = address;
    		data.port = port;
    		data.size = size;
    	
    	return data;
    }
    public String[] onDeop(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient)
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname, recipient };
    }
    public String[] onDeVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) 
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname, recipient };
    }
    public ircData onFileTransferFinished(DccFileTransfer transfer, Exception e) 
    {
    	ircData data = new ircData();
    		data.transfer = transfer;
    		data.e = e;
    		
    	return data;
    }
    public String[] onFinger(String sourceNick, String sourceLogin, String sourceHostname, String target) 
    {
    	return new String[] { sourceNick, sourceLogin, sourceHostname, target };
    }
    public DccChat onIncomingChatRequest(DccChat chat) 
    {
    	return chat;
    }
    public DccFileTransfer onIncomingFileTransfer(DccFileTransfer transfer) 
    {
    	return transfer;
    }
    public String[] onInvite(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String channel) 
    {
    	return new String[] { targetNick, sourceNick, sourceLogin, sourceHostname, channel };
    }
    public String[] onJoin(String channel, String sender, String login, String hostname)
    {
    	return new String[] { channel, sender, login, hostname };
    }
    public String[] onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason)
    {
    	return new String[] { channel, kickerNick, kickerLogin, kickerHostname, recipientNick, reason };
    }
    public String[] onMessage(String channel, String sender, String login, String hostname, String message) 
    {
    	return new String[] { channel, sender, login,  hostname,  message };
    }
    public String[] onMode(String channel, String sourceNick, String sourceLogin, String sourceHostname, String mode) 
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname, mode };
    }
	public String[] onNickChange(String oldNick, String login, String hostname, String newNick) 
    {
    	return new String[] { oldNick, login, hostname, newNick };
    }
    public String[] onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice)  
    {
    	return new String[] { sourceNick, sourceLogin, sourceHostname, target, notice };
    }
    public String[] onOp(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) 
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname, recipient };
    }
    public String[] onPart(String channel, String sender, String login, String hostname)  
    {
    	return new String[] { channel, sender, login, hostname };
    }
    public String[] onPing(String sourceNick, String sourceLogin, String sourceHostname, String target, String pingValue)
    {
    	return new String[] { sourceNick, sourceLogin, sourceHostname, target, pingValue };
    }
    public String[] onPrivateMessage(String sender, String login, String hostname, String message) 
    {
    	return new String[] { sender, login, hostname, message };
    }
    public String[] onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason) 
    {
    	return new String[] { sourceNick, sourceLogin, sourceHostname, reason };
    }
    public String[] onRemoveChannelBan(String channel, String sourceNick, String sourceLogin, String sourceHostname, String hostmask)
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname, hostmask };
    }
    public String[] onRemoveChannelKey(String channel, String sourceNick, String sourceLogin, String sourceHostname, String key) 
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname, key };
    }
    public String[] onRemoveChannelLimit(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname };
    }
    public String[] onRemoveInviteOnly(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname };
    }
    public String[] onRemoveModerated(String channel, String sourceNick, String sourceLogin, String sourceHostname)  
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname };
    }
    public String[] onRemoveNoExternalMessages(String channel, String sourceNick, String sourceLogin, String sourceHostname)
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname };
    }
    public String[] onRemovePrivate(String channel, String sourceNick, String sourceLogin, String sourceHostname)
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname };
    }
    public String[] onRemoveSecret(String channel, String sourceNick, String sourceLogin, String sourceHostname)
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname };
    }
    public String[] onRemoveTopicProtection(String channel, String sourceNick, String sourceLogin, String sourceHostname)
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname };
    }
    public String onServerPing(String response) 
    {
    	return response;
    }
    public ircData onServerResponse(int code, String response) 
    {
    	ircData data = new ircData();
    		data.code = code;
    		data.response = response;
    		
    	return data;
    }
    public String[] onSetChannelBan(String channel, String sourceNick, String sourceLogin, String sourceHostname, String hostmask)
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname, hostmask };
    }
    public String[] onSetChannelKey(String channel, String sourceNick, String sourceLogin, String sourceHostname, String key)
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname, key };
    }
    public ircData onSetChannelLimit(String channel, String sourceNick, String sourceLogin, String sourceHostname, int limit)
    {
    	ircData data = new ircData();
    		data.channel = channel;
    		data.sourceNick = sourceNick;
    		data.sourceLogin = sourceLogin;
    		data.sourceHostname = sourceHostname;
    		data.limit = limit;
    	
    	return data;
    }
    public String[] onSetInviteOnly(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname };
    }
    public String[] onSetModerated(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname };
    }
    public String[] onSetNoExternalMessages(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname };
    }
    public String[] onSetPrivate(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname };
    }
    public String[] onSetSecret(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname };
    }
    public String[] onSetTopicProtection(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname };
    }
    public String[] onTime(String sourceNick, String sourceLogin, String sourceHostname, String target)  
    {
    	return new String[] { sourceNick, sourceLogin, sourceHostname, target };
    }   
    public String[] onTopic(String channel, String topic) 
    {
    	return new String[] { channel, topic };
    }
    public ircData onTopic(String channel, String topic, String setBy, long date, boolean changed) 
    {
    	ircData data = new ircData();
    		data.channel = channel;
    		data.topic = topic;
    		data.setBy = setBy;
    		data.date = date;
    		data.changed = changed;
    	
    	return data;
    }
    public String onUnknown(String line) 
    {
    	return line;
    }
    public ircData onUserList(String channel, User[] users) 
    {
    	ircData data = new ircData();
    		data.channel = channel;
    		data.users = users;
    		
    	return data;
    }
    public String[] onUserMode(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String mode) 
    {
    	return new String[] { targetNick, sourceNick, sourceLogin, sourceHostname, mode };
    }
    public String[] onVersion(String sourceNick, String sourceLogin, String sourceHostname, String target) 
    {
    	return new String[] { sourceNick, sourceLogin, sourceHostname, target };
    }
    public String[] onVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) 
    {
    	return new String[] { channel, sourceNick, sourceLogin, sourceHostname, recipient };
    }
    
}