package logicswarm.net.irc.mods;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import logicswarm.net.irc.IrcBot;
import logicswarm.net.irc.IrcModule;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;

/*
 * Description: Chatroom logger, logs conversations in human readable format
 * Notes: some features missing.
 * Author: Sean Nicholls
 * Date: 31 October 2007
 * 
 */

public class ChatLogger extends IrcModule {

	// data members
	public static final long 	serialVersionUID 	= 1;	
	private BufferedWriter 		fout;	
	private Timestamp			timestamp;	
	private String 				path;
	
	// constructor(s)
	public ChatLogger( String strpath, IrcBot owner )
	{
		super(owner);
		initialize("ChatLogger");
		
		path = strpath;
		
		newSession();
	}

	// destructor
	public void finalize()
	{
		endSession();
	}
	
	// methods
	public boolean newSession()
	{		
		try {
			if(openFile()) {
				fout.newLine();
				fout.write("**** BEGIN LOGGING AT " + getTimestamp() + " ****");
				fout.newLine();
				return true;				
			} else {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}
	public boolean endSession()
	{		
		try {			
			fout.newLine();
			fout.write("**** END LOGGING AT " + getTimestamp() + " ****");
			fout.newLine();
			return true;
		} catch (IOException e) {
			return false;
		}
	}	
	public boolean writeLn( String str )
	{
		try {
			fout.write(str);
			fout.newLine();
			saveFile();
			return true;
		} catch(IOException e) {
			System.out.println("Error occurred while trying to write to file at '" + path + "' " + e.getMessage());
			return false;
		}	
	}
	private String getTimestamp()
	{
		timestamp = new Timestamp(new Date().getTime());
		return "[" + timestamp.toString() + "]";
	}
	public boolean logEvent( String str )
	{
		/*
		if(verbose)
			log(str);
		*/
		return writeLn( getTimestamp() + " " + str );
	}	
	public boolean closeFile()
	{
		try{
			fout.close();
			return true;
		} catch(IOException e) {
			System.out.println("Error occurred while attempting to close file at '" + path + "' " + e.getMessage());
			return false;
		}
	}
	public boolean openFile()
	{
		try {
			fout = new BufferedWriter( new FileWriter(path, true) );
			return true;
		} catch ( IOException e ) {
			System.out.println("Error occurred while attempting to access file at '" + path + "' " + e.getMessage());
			return false;
		}
	}
	public boolean saveFile()
	{
		if(closeFile()) {
			return openFile();
		} else {
			return false;
		}
	}
	
	// custom events //
	public void onSendAction(String target, String action)
	{
    	logEvent(" * " + parent.getNick() + " " + action);
	}
	public void onSendMessage(String target, String message)
	{
    	logEvent(" <" + parent.getNick() + "> " + message);
	}
	public void onSendNotice(String target, String notice) 
	{
    	logEvent(" -" + parent.getNick() + "- " + notice);
	}
	
	// PircBot events //
    public void onAction(String sender, String login, String hostname, String target, String action)
    {
    	logEvent(" * " + sender + " " + action);
    }   
    public void onConnect()
    {
    	logEvent(" >> Connected to " + parent.getServer());
    }
    public void onDeop(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient)
    {
    	logEvent(channel + ": * " + recipient + " was deOp by " + sourceNick);
    }
    public void onDeVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) 
    {
    	logEvent(channel + ": * " + recipient + " was deVoiced by " + sourceNick);
    }
    public void onDisconnect()
    {
    	logEvent(" >> Disconnected from " + parent.getServer());
    }
    public void onIncomingChatRequest(DccChat chat) 
    {
    	logEvent(" >> Incoming chat request from " + chat.getNick());
    }
    public void onIncomingFileTransfer(DccFileTransfer transfer) 
    {
    	logEvent(" >> Incoming file transfer from " + transfer.getNick());
    }
    public void onInvite(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String channel) 
    {
    	logEvent(channel + ": * " + targetNick + " was invited to " + channel + " by " + sourceNick);
    }
    public void onJoin(String channel, String sender, String login, String hostname)
    {
    	logEvent(channel + ": * " + sender + " joined " + channel);
    }
    public void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason)
    {
    	logEvent(channel + ": * " + kickerNick + " has kicked " + recipientNick + " reason: " + reason);
    }
    public void onMessage(String channel, String sender, String login, String hostname, String message) 
    {
    	logEvent(channel + ": <" + sender + "> " + message);
    }
    public void onMode(String channel, String sourceNick, String sourceLogin, String sourceHostname, String mode) 
    {
    	logEvent(channel + ": * " + sourceNick + " has set mode " + mode);
    }
	public void onNickChange(String oldNick, String login, String hostname, String newNick) 
    {
		logEvent(" * " + oldNick + " is now known as " + newNick);
    }
    public void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice)  
    {
    	logEvent(" -" + sourceNick + "- " + notice);    	
    }
    public void onOp(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) 
    {
    	logEvent(channel + ": * " + sourceNick + " has given ops to " + recipient);
    }
    public void onPart(String channel, String sender, String login, String hostname)  
    {
    	logEvent(channel + ": * " + sender + " has parted " + channel);    	
    }
    public void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason) 
    {
    	logEvent("* " + sourceNick + " has quit (" + reason + ")");
    }
    public void onRemoveChannelBan(String channel, String sourceNick, String sourceLogin, String sourceHostname, String hostmask)
    {
    	logEvent(channel + ": * " + sourceNick + " has removed 'channel-ban' from " + channel);
    }
    public void onRemoveChannelKey(String channel, String sourceNick, String sourceLogin, String sourceHostname, String key) 
    {
    	logEvent(channel + ": * " + sourceNick + " has removed 'channel key' from " + channel);
    }
    public void onRemoveChannelLimit(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	logEvent(channel + ": * " + sourceNick + " has removed 'channel limit' from " + channel);
    }
    public void onRemoveInviteOnly(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	logEvent(channel + ": * " + sourceNick + " has removed 'invite-only' from " + channel);
    }
    public void onRemoveModerated(String channel, String sourceNick, String sourceLogin, String sourceHostname)  
    {
    	logEvent(channel + ": * " + sourceNick + " has removed 'moderated' from " + channel);
    }
    public void onRemoveNoExternalMessages(String channel, String sourceNick, String sourceLogin, String sourceHostname)
    {
    	logEvent(channel + ": * " + sourceNick + " has removed 'no-external-messages' from " + channel);    	
    }
    public void onRemovePrivate(String channel, String sourceNick, String sourceLogin, String sourceHostname)
    {
    	logEvent(channel + ": * " + sourceNick + " has removed 'private' from " + channel);
    }
    public void onRemoveSecret(String channel, String sourceNick, String sourceLogin, String sourceHostname)
    {
    	logEvent(channel + ": * " + sourceNick + " has removed 'secret' from " + channel);
    }
    public void onRemoveTopicProtection(String channel, String sourceNick, String sourceLogin, String sourceHostname)
    {
    	logEvent(channel + ": * " + sourceNick + " has removed 'topic protection' from " + channel);
    }
    public void onSetChannelBan(String channel, String sourceNick, String sourceLogin, String sourceHostname, String hostmask)
    {
    	logEvent(channel + ": * " + sourceNick + " has set 'channel-ban' on " + channel);
    }
    public void onSetChannelKey(String channel, String sourceNick, String sourceLogin, String sourceHostname, String key)
    {
    	logEvent(channel + ": * " + sourceNick + " has set 'channel-key' on " + channel + " key(" + key + ")");
    }
    public void onSetChannelLimit(String channel, String sourceNick, String sourceLogin, String sourceHostname, int limit)
    {
    	logEvent(channel + ": * " + sourceNick + " has set 'channel-limit' to " + limit + " on " + channel);
    }
    public void onSetInviteOnly(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	logEvent(channel + ": * " + sourceNick + " has set 'invite-only' on " + channel);
    }
    public void onSetModerated(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	logEvent(channel + ": * " + sourceNick + " has set 'moderated' on " + channel);
    }
    public void onSetNoExternalMessages(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	logEvent(channel + ": * " + sourceNick + " has set 'no-external-messages' on " + channel);
    }
    public void onSetPrivate(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	logEvent(channel + ": * " + sourceNick + " has set 'private' on " + channel);
    }
    public void onSetSecret(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	logEvent(channel + ": * " + sourceNick + " has set 'secret' on " + channel);
    }
    public void onSetTopicProtection(String channel, String sourceNick, String sourceLogin, String sourceHostname) 
    {
    	logEvent(channel + ": * " + sourceNick + " has set 'topic-protection' on " + channel);
    }
    public void onTopic(String channel, String topic, String setBy, long date, boolean changed) 
    {
    	logEvent(channel + ": * " + setBy + " has set 'topic' to " + topic + " on " + channel);
    }
    public void onUserMode(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String mode) 
    {
    	logEvent("* " + sourceNick + " has set 'user-mode' to " + mode + " on " + targetNick);
    }
    public void onVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) 
    {
    	logEvent(channel + ": * " + sourceNick + " has set 'voice' on " + recipient);
    }
}
