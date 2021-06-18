/*
 * For any game, the stateT structure records the current state
 * of the game.  For Nim, this is an integer representing the
 * number of coins remaining.
 *
 * In addition to the number of coins, the code stores a player
 * value to indicate whose turn it is.  
 */


public class StateNim extends State {
	
    public int coins = 13;
    
    public StateNim() {
        coins = 13;
        player = 1;
    }
    
    public StateNim(StateNim state) {
        this.coins = state.coins; 
        player = state.player;
    }
    
    public String toString() {
    	return "" + coins;
    }
}
