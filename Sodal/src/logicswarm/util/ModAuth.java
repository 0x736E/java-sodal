package logicswarm.util;

import logicswarm.net.irc.datatypes.ircData;

/*
 * Description: User authentication module,
 * 				this mod is an interoperator,
 * 				it is only used by other mods.
 * Notes: n/a
 * Author: Sean Nicholls
 * Date: today's date
 * 
 */
public class ModAuth {

	// data members
	public String[][] users	= null;		// login | nick | host | level
	
	// constructor(s)
	public ModAuth()
	{

	}

	// methods
	public final int getUserLevel( ircData data ) { 
		if(data == null || users == null) {
			return 0;
		} else {
			return getUserLevel( data.sourceLogin, data.sourceNick, data.sourceHostname );
		}
	}
	public final int getUserLevel( String login, String nick, String host ) {
		if( users !=null ) {
			String[] newuser = { login, nick, host, null };
			return getUserLevel(newuser);
		} else {
			return 0;
		}
	}
	public final int getUserLevel( String[] user ) {
		if(user == null || users == null) {
			return 0;
		} else {
			for(int i=0; i<users.length; i++) {
				if( compareUser(users[i],user) ) {
					return Integer.parseInt(users[i][3]);
				}
			}
			return 0;
		}
	}
	public final void addUser( ircData data, int level ) {
		if(data == null) {
			return;
		} else {
			addUser( data.sourceNick, data.sourceNick, data.sourceHostname, level );
		}
	}
	public final void addUser( String login, String nick, String host, int level ) {
		if(users != null) {
			String[] newuser = { login, nick, host, String.valueOf(level) };
			addUser(newuser);
		} else {
			return;
		}
	}
	public final void addUser( String[] user ) {
		if(user == null) {
			return;
		} else {
			if(users == null) {
				String[][] out = { user };
				users = out;
			} else {
				String[][] out = { user };
				users = Arrays.appendArray(users, out);
			}
		}
	}
	public final void addUser( String[][] user ) {
		if(user == null) {
			return;
		} else {
			if(users == null) {
				users = user;
			} else {
				for(int i=0; i<user.length; i++){
					addUser(user[i]);
				}
			}
		}
	}
	public final void removeUser( ircData data, int level ) {
		if(data == null || users == null) {
			return;
		} else {
			removeUser( data.sourceLogin, data.sourceNick, data.host, level );
		}
	}
	public final void removeUser( String login, String nick, String host, int level ) {
		if(users != null) {
			String[] user = { login, nick, host, String.valueOf(level) };
			removeUser(user);
		} else {
			return;
		}
	}
	public final void removeUser( String[] user ) {
		if(user == null || users == null) {
			return;
		} else {
			users = Arrays.removeFromArray(users, getUserIndex(user));
		}
	}
	public final int getUserIndex( String[] user ) {
		if(user == null || users == null) {
			return -1;
		} else {
			for(int i=0; i<users.length; i++) {
				if(	compareUser(user, users[i])) {
					return i;
				}
			}
			return -1;
		}
	}
	public final int getUserIndex( ircData data, int level ) {
		if(data == null || users == null) {
			return -1;
		} else {
			String[] user = { data.sourceLogin, data.sourceNick, data.sourceHostname, String.valueOf(level) };
			return getUserIndex(user);
		}
	}
	public final int getUserIndex( String login, String nick, String host, int level ) {
		if(users != null) {
			String[] user = { login, nick, host, String.valueOf(level) };
			return getUserIndex(user);
		} else {
			return -1;
		}
	}
	public final String[] getUserDetails( int index ) {
		if( index >= 0 && index <= users.length && users != null ) {
			return users[index];
		} else {
			return null;
		}
	}
	public final String[][] getUserDetails() {
		return users;
	}
	public final boolean compareUser( String[] user1, String[] user2 ) {
		if(user1 == null || user2 == null || users == null) {
			return false;
		} else {
			String u1, u2;
			for(int i=0; i<3; i++) {
				u1 = user1[i].toLowerCase();
				u2 = user2[i].toLowerCase();
				if(!u2.equals(u1) && !u2.matches( '^' +  u1 + '$' )) {
					return false;
				}
			}
			return true;
		}
	}
	public final boolean compareUser( ircData user1, int lvl1, ircData user2, int lvl2 ) {
		if(user1 == null || user2 == null || users == null) {
			return false;
		} else {
			String[] u1 = { user1.sourceLogin, user1.sourceNick, user1.sourceHostname, String.valueOf(lvl1) } ;
			String[] u2 = { user2.sourceLogin, user2.sourceNick, user2.sourceHostname, String.valueOf(lvl2) } ;
			return compareUser( u1, u2 );
		}
	}
	public final boolean validateUser( String[] user ) {
		if(user == null || users == null) {
			return false;
		} else {
			for(int i=0; i<users.length; i++) {
				if(compareUser(users[i],user)) {
					return true;
				}
			}
			return false;
		}
	}
	public final boolean validateUser( ircData data, String level ) {
		if(data == null || users == null) {
			return false;
		} else {
			String[] user = { data.sourceLogin, data.sourceNick, data.sourceHostname, level };
			return validateUser(user);
		}
	}
	public final boolean validateUser( ircData data, int level ) {
		return validateUser(data,String.valueOf(level));
	}
	public final boolean validateUser( String login, String nick, String host, String level ) {
		if(users == null) {
			return false;
		} else {
			String[] user = { login, nick, host, level };
			return validateUser(user);
		}
	}
	public final boolean validateUser( String login, String nick, String host, int level ) {
		return validateUser(login,nick,host,String.valueOf(level));
	}

}
