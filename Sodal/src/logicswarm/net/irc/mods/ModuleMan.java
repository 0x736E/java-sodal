package logicswarm.net.irc.mods;

import logicswarm.net.irc.IrcBot;
import logicswarm.net.irc.IrcModule;

/*
 * Description: module & filter manager
 * Notes: n/a
 * Author: Sean Nicholls
 * Date: 31 October 2007
 * 
 */
public class ModuleMan extends IrcModule {

	// data members
	public static final long 	serialVersionUID 	= 1;
	
	// constructor(s)
	public ModuleMan( IrcBot owner )
	{
		super(owner);
		initialize("ModuleManager");
	}

	// events
	@Override
	public void onMessage(String channel, String sender,String login, String hostname, String message)
	{	
		String msg = message.toLowerCase();
				
		if(msg.indexOf("!activate mod ") == 0) {

			int i = Integer.parseInt(msg.substring(14));
			
			if( i >= parent.countMods() || i < 0 ) {
				parent.sendNotice( sender, "A module at that index does not yet exist." );
			} else if( parent.getModule(i).getName().equals(this.getName()) ) {
				parent.sendNotice( sender, "Cannot modify the module which provides module activation/deactivation functionality." );
			} else {
				this.parent.activateMod(i);
				parent.sendNotice( sender, "Module at index " + i + " (" + this.parent.getModule(i).getName() + ") has been activated." );
			}

		} else if(msg.indexOf("!activate filter ") == 0) {

			int i = Integer.parseInt(msg.substring(17));
			
			if( i >= parent.countFilters() || i < 0 ) {
				parent.sendNotice( sender, "A filter at that index does not yet exist." );
			} else {
				this.parent.activateFilter(i);
				parent.sendNotice( sender, "Filter at index " + i + " (" + this.parent.getFilter(i).getName() + ") has been activated." );
			}
			
		} else if(msg.indexOf("!deactivate mod ") == 0) {

			int i = Integer.parseInt(msg.substring(16));
			
			if( i >= parent.countMods() || i < 0 ) {
				parent.sendNotice( sender, "A module at that index does not yet exist." );
			} else if( i >= parent.countMods() || parent.getModule(i).getName().equals(this.getName()) ) {
				parent.sendNotice( sender, "Cannot modify the module which provides module activation/deactivation functionality." );
			} else {
				this.parent.deactivateMod(i);
				parent.sendNotice( sender, "Module at index " + i + " (" + this.parent.getModule(i).getName() + ") has been deactivated." );
			}			
			
		} else if(msg.indexOf("!deactivate filter ") == 0) {

			int i = Integer.parseInt(msg.substring(19));
			
			if( i >= parent.countFilters() || i < 0 ) {
				parent.sendNotice( sender, "A filter at that index does not yet exist." );
			} else {
				this.parent.deactivateFilter(i);
				parent.sendNotice( sender, "Filter at index " + i + " (" + this.parent.getFilter(i).getName() + ") has been deactivated." );
			}			
			
		} else if(msg.indexOf("!delete mod ") == 0) {

			int i = Integer.parseInt(msg.substring(12));
			
			if( i >= parent.countMods() || i < 0 ) {
				parent.sendNotice( sender, "A module at that index does not yet exist." );
			} else if( i >= parent.countMods() || parent.getModule(i).getName().equals(this.getName()) ) {
				parent.sendNotice( sender, "Cannot modify the module which provides module activation/deactivation functionality." );
			} else {
				String tmp = this.parent.getModule(i).getName();
				this.parent.removeMod(i);
				parent.sendNotice( sender, "Module at index " + i + " (" + tmp + ") has been deleted." );
			}
			
		}  else if(msg.indexOf("!delete filter ") == 0) {

			int i = Integer.parseInt(msg.substring(15));
			
			if( i >= parent.countFilters() || i < 0) {
				parent.sendNotice( sender, "A filter at that index does not yet exist." );
			} else {
				String tmp = this.parent.getFilter(i).getName();
				this.parent.removeFilter(i);
				parent.sendNotice( sender, "Filter at index " + i + " (" + tmp + ") has been deleted." );
			}
			
		} else if(msg.indexOf("!list mods") == 0) {
			
			// modules
			for(int i = 0; i < parent.countMods(); i++){
				if(parent.getModule(i).status){
					parent.sendNotice( sender, "Module at index " + i + " (" + this.parent.getModule(i).getName() + ") is Active" );
				} else {
					parent.sendNotice( sender, "Module at index " + i + " (" + this.parent.getModule(i).getName() + ") is Inactive" );					
				}
			}
			
		} else if(msg.indexOf("!list filters") == 0) {

			
			// filters
			for(int i = 0; i < parent.countFilters(); i++){
				if(parent.getFilter(i).status){
					parent.sendNotice( sender, "Filter at index " + i + " (" + this.parent.getFilter(i).getName() + ") is Active" );
				} else {
					parent.sendNotice( sender, "Filter at index " + i + " (" + this.parent.getFilter(i).getName() + ") is Inactive" );					
				}
			}
		}
	}
	
}
