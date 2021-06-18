import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class GameNim extends Game {
	
    int WinningScore = 10;
    int LosingScore = -10;
    int NeutralScore = 0;    
    
    public GameNim() {
    	currentState = new StateNim();
    }
    
    public boolean isWinState(State state)
    {
        StateNim tstate = (StateNim) state; 
        int previous_player = (state.player==0 ? 1 : 0);
		
        if(tstate.coins == 1 || tstate.coins == 0) {
			return true;
		} else {
			return false;
		}
    }
    
    public boolean isStuckState(State state) {
    	
        if (isWinState(state)) 
            return false;
        
        StateNim tstate = (StateNim) state;
        
        if(tstate.coins < 0){
			return true;
		} else {
			return false;
		}
        
    }
	
	
	public Set<State> getSuccessors(State state)
    {
		if(isWinState(state) || isStuckState(state))
			return null;
		
		Set<State> successors = new HashSet<State>();
        StateNim tstate = (StateNim) state;
        
        StateNim successor_state;        
        
		if(tstate.coins >= 3){
			//Taking 3 coins is possible
			successor_state = new StateNim(tstate);
			successor_state.coins = successor_state.coins - 3;
			successor_state.player = (state.player==0 ? 1 : 0);
			successors.add(successor_state);
		}
		
		if(tstate.coins >= 2){
			//Taking 2 coins is possible
			successor_state = new StateNim(tstate);
			successor_state.coins = successor_state.coins - 2;
			successor_state.player = (state.player==0 ? 1 : 0);
			successors.add(successor_state);
		}
		
		if(tstate.coins >= 1){
			//Taking 1 coin is possible
			successor_state = new StateNim(tstate);
			successor_state.coins = successor_state.coins - 1;
			successor_state.player = (state.player==0 ? 1 : 0);
			successors.add(successor_state);
		}
		
		return successors;
    }	
    
    public double eval(State state) 
    {   
		StateNim tstate = (StateNim) state;
    	if(isWinState(state)) {
    		//player who made last move
    		int previous_player = (state.player==0 ? 1 : 0);
			
			//Computer wins if play takes all coins, or if player's turn with coins == 1
	    	if ((previous_player==0 && tstate.coins==1) || (previous_player==1 && tstate.coins==0)) 
	            return WinningScore;
			else //human wins
				return LosingScore;
    	}

        return NeutralScore;
    }
    
    
    public static void main(String[] args) throws Exception {
        
        Game game = new GameNim(); 
        Search search = new Search(game);
        int depth = 13;
        
        //needed to get human's move
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        while (true) {
        	
        	StateNim 	nextState = null;
        	
            switch ( game.currentState.player ) {
              case 1: //Human
                  
            	  //get human's move
                  System.out.print("Enter your *valid* move> ");
                  int taken = Integer.parseInt( in.readLine() );
            	  
                  nextState = new StateNim((StateNim)game.currentState);
                  nextState.player = 1;
                  nextState.coins = nextState.coins - taken;
                  System.out.println("Human: \n" + nextState);
                  break;
                  
              case 0: //Computer
            	  System.out.println("Computer's Turn");
            	  nextState = (StateNim)search.bestSuccessorState(depth);
            	  nextState.player = 0;
            	  System.out.println("Computer: \n" + nextState);
                  break;
            }
                        
            game.currentState = nextState;
            //change player
            game.currentState.player = (game.currentState.player==0 ? 1 : 0);
            
            //Who wins?
			int current_coins = ((StateNim)game.currentState).coins;
			
            if ( game.isWinState(game.currentState) ) {
				//Computer wins if the player takes the last coins, or if it leaves the player with only 1 coin to take
            	if ((game.currentState.player==1 && (current_coins==1)) || (game.currentState.player==0 && (current_coins==0))){
					System.out.println("Computer wins!");
				} else {
            		System.out.println("You win!");
				}
            	
            	break;
            }
            
            if ( game.isStuckState(game.currentState) ) { 
            	System.out.println("Tie!");
            	break;
            }
        }
    }
}