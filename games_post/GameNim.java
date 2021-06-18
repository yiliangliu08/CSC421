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

    @Override
    public boolean isWinState(State state) {
        StateNim tstate = (StateNim) state;

        int previous_player = (state.player == 0 ? 1 : 0);

        if (tstate.coins == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isStuckState(State state) {
        if (isWinState(state))
            return false;
        StateNim tstate = (StateNim) state;
        return false;
    }


    @Override
    public Set<State> getSuccessors(State state) {
        if(isWinState(state) || isStuckState(state))
            return null;

        Set<State> successors = new HashSet<State>();
        StateNim tstate = (StateNim) state;
        StateNim successor_state;
        for(int i = 1; i <= 3; i++) {
            successor_state = new StateNim(tstate);
            successor_state.coins = successor_state.coins - i;
            successor_state.player = (state.player == 0 ? 1 : 0);
            successors.add(successor_state);
        }
        return successors;
    }

    @Override
    public double eval(State state) {
        if(isWinState(state)){
            int previous_player = (state.player==0 ? 1: 0);
            if(previous_player==0){
                return WinningScore;
            }else{
                return LosingScore;
            }
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        Game game = new GameNim();
        Search search = new Search(game);
        int depth = 13;

        int previous_coins = 0;

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            StateNim nextState = null;
            switch (game.currentState.player) {
                case 1: //human
                    int in_1 = 0;
                    boolean is_valid = false;
                    while(is_valid == false) {
                        System.out.print("Enter a number between 1-3: ");
                        in_1 = Integer.parseInt(in.readLine());
                        if (in_1 >= 1 && in_1 <= 3) {
                            is_valid = true;
                        }else{
                            is_valid = false;
                        }
                    }
                    nextState = new StateNim((StateNim) game.currentState);
                    nextState.player = 1;
                    nextState.coins = nextState.coins - in_1;
                    previous_coins = nextState.coins;
                    System.out.println("Player 1 takes " + Integer.toString(in_1) + " coins. Coins Remain: "+ nextState);
                    break;
                case 0: //Computer
                    System.out.println("Computer's Turn");
                    nextState = (StateNim)search.bestSuccessorState(depth);
                    nextState.player = 0;
                    System.out.println("Computer takes " + (previous_coins - nextState.coins)+" coins. Coins Remain: "+ nextState);
                    break;
            }
            game.currentState = nextState;
            game.currentState.player = (game.currentState.player == 0 ? 1 : 0);
            if (game.isWinState(game.currentState)) {

                if (game.currentState.player == 1) //i.e. last move was by the computer
                    System.out.println("Computer wins!");
                else
                    System.out.println("You wins");

                break;
            }

            if (game.isStuckState(game.currentState)) {
                System.out.println("Cat's game!");
                break;
            }
        }
    }
}


