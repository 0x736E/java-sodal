package logicswarm.util;

import logicswarm.util.Arrays;

/*
 * Description: describe the mod here
 * Notes: n/a
 * Author: your name
 * Date: today's date
 * 
 */
public final class CmdParser {

	// data members
	public static final long 	serialVersionUID 	= 1;
	public String[][] commands = null;		// commands are case-sensitive
											// cmd | level
	// constructor(s)
	public CmdParser()
	{
	}

	// methods
	public final int getCMDLevel( String cmd ) {
		if(commands != null) {
			for(int i=0; i<commands.length; i++){
				if(cmd.equalsIgnoreCase(commands[i][0]))
					return Integer.parseInt(commands[i][1]);
			}
			return 0;
		} else {
			return 0;
		}
	}
	public final int getCMDIndex( String cmd ) {
		if(commands != null) {
			for(int i=0; i<commands.length; i++) {
				if(cmd.equalsIgnoreCase(commands[i][0]))
					return i;
			}
			return -1;
		} else {
			return -1;
		}
	}
	public final boolean cmdExists( String cmd )
	{
		if(commands != null) {
			for(int i=0; i<commands.length; i++) {
				if(compareCMD(commands[i][0],cmd)) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
			
	}
	public final void addCMD( String cmd, int level ) {
		addCMD( cmd, String.valueOf(level) );
	}
	public final void addCMD( String cmd, String level ) {
		if(cmd != null && level != null && !cmdExists(cmd)) {
			int lvl = Integer.parseInt(level);
			if(lvl >= 0) {
				if(commands != null) {
					String[][] out = { { cmd , level } };
					commands = Arrays.appendArray(commands, out );
				} else {
					commands = new String[1][2];
					commands[0][0] = cmd;
					commands[0][1] = String.valueOf(level);				
				}
			} else {
				return;
			}
		} else {
			return;
		}
	}
	public final boolean addCMD( String[] cmd, int[] level ) {
		if(cmd.length == level.length && cmd != null && level != null) {
			for(int i=0; i<cmd.length; i++) {
				addCMD(cmd[0],level[0]);
			}
			return true;
		} else {
			return false;
		}
	}
	public final boolean addCMD( String[][] cmd ) {
		if(cmd != null) {
			for(int i=0; i<cmd.length; i++) {
				addCMD(cmd[i][0],cmd[i][1]);
			}
			return true;
		} else {
			return false;
		}
	}
	public final void removeCMD( String cmd ) {
		if(commands != null && cmd != null) {
			int i;
			if( (i = getCMDIndex(cmd)) != -1) {
				removeCMD(i);
			}
		}
	}
	public final void removeCMD( int index ) {
		if(commands != null && index >= 0) {
			commands = Arrays.removeFromArray(commands, index);
		}
	}
	public final String[][] getCommands() {
		return commands;
	}
	public final boolean compareCMD( String cmd1, String cmd2 ) {
		return cmd1.equals(cmd2);
	}
	public final String[] validateString( String in, int lvl ) {
		String[] out;
		if( (out = Arrays.explode(in," ")) != null ) {
			return validateString(out,lvl);
		} else {
			return null;
		}
	}
	public final String[] validateString( String[] in, int lvl ) {
		if(cmdExists(in[0]) && getCMDLevel(in[0]) <= lvl ) {
			return in;
		} else {
			return null;
		}
	}
	public final boolean validateCMD( String cmd, int lvl ) {
		String[] out = { cmd };
		return (validateString(out,lvl) != null);
	}
	public final String[] getCommand( String in ) {
		return Arrays.explode(in, " ");
	}
}
