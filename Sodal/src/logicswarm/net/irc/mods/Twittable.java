package logicswarm.net.irc.mods;

import java.util.List;

import logicswarm.net.irc.IrcBot;
import logicswarm.net.irc.IrcModule;
import winterwell.jtwitter.*;
import org.jibble.jmegahal.JMegaHal;

/*
 * Description: Mod that allows the bot to communicate with Twitter
 * Notes: n/a
 * Author: Sean Nicholls
 * Date: 18 January 2008
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

//TODO: change the way the bot handles tweets so that it only keeps status of the last 20 tweets, but keeps a list of the id's it has broadcast
public class Twittable extends IrcModule {

	// data members
	public static final long 	serialVersionUID 	= 1;
	
	public String username = "";							// JoeTheBot
	public String password = "";							// i@7~>jkW4~xW
	
	private List<Twitter.Status> tweets;					// the latest 20 tweets
	private List<Twitter.Status> tweetCache;				// cache the last 20 tweets
	public int maxTweets = 2;								// max new tweets to broadcast
	
	Twitter objTwitter;
	
	// constructor(s)
	public Twittable( String user, String pass, IrcBot owner )
	{
		super(owner);
		
		username = user;
		password = pass;
		
		initialize("Twittable");
	}
	
	// initialise
	public void onInitialize()
	{
		objTwitter = new Twitter(username, password);			// TODO: Error handling
		this.start();	// TODO: make this automatic by the bot
	}
	
	// this is where all sexy stuff happens
	public void run() {
			
		while(true) {
			
			if(isModLoaded()) {			// the mod must be fully initialized first
				
				try {			
					tweets = objTwitter.getFriendsTimeline();	
					log("got the tweets for " + username + "!");
				} catch(Exception e) {
					log("could not get tweets: " + e.getMessage());
				}
				
				sayTweets();
				
				try {
					Thread.sleep(30000);
				} catch(Exception e) {
					log("Error: " + e.getMessage());
				}
				
			}
		}		
	}
	
	// print out the new tweets
	private void sayTweets() {
				
		// just set it up, dont spam the channels
		if(tweetCache == null) {
			tweetCache = tweets;
			return;
		}
		
		int chanTweets = 0;					// the number of times we've broadcasted new tweets
		
		for(int i=0; i<tweets.size(); i++) {
						
			// if a tweet isnt int the cache, say it in the channel
			// ignore everything from the bot's nick
			if(!tweets.get(i).user.screenName.equalsIgnoreCase(username) && !tweetIdExists(tweets.get(i).id)) {
				
				// reply to tweets
				if(tweets.get(i).text.contains("@" + username)) {			// message contains @Username
					String myTweet = "";
					JMH_Brain temp = (JMH_Brain) parent.getModule("Brain");
					if(temp != null) {
						
						// make it short enough for twitter
						do {
							myTweet = temp.getSentence();
						} while(myTweet.length() > 150);
						
						objTwitter.updateStatus("@" + tweets.get(i).user.screenName + " " + myTweet);
					}
				} else if(chanTweets < maxTweets) {
					String[][] tmp = getChannels();
					
					// say the new tweet in each channel im connected to
					for(int j=0; j<tmp.length; j++) {					
						// say it here
						log(tweets.get(i).user.screenName + " says '" + tweets.get(i).text + "'" );
						parent.SendMessage(tmp[j][0], "" + tweets.get(i).user.screenName + " says '" + tweets.get(i).text + "'" );
					}
					
					chanTweets ++;
				}
				
			} else {
				break;
			}
		}
		
		// update the cache
		tweetCache = tweets;
		
	}
	
	// finds out if a tweet id exists
	private boolean tweetIdExists(long id){
		for(int i=0; i<tweetCache.size(); i++) {
			if(tweetCache.get(i).id == id) {
				return true;
			}
		}
		return false;
	}
	

}
