import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MainFrame frame=new MainFrame();
    }

    public static void gameOfLife(int[][] board) {
        int[][] copy= new int[board.length][board[0].length];
        for(int i=0;i<copy.length;i++)
            System.arraycopy(board[i], 0, copy[i], 0, copy[0].length);


        for(int i=0;i<copy.length;i++){
            for(int j=0;j<copy[0].length;j++){
                board[i][j]=0;
                if(cellWillLive(copy,i,j))
                    board[i][j]=1;
            }
        }

    }
    public static boolean cellWillLive(int[][] board,int i,int j){
        int neighbors=0, lenX=board[i].length-1, lenY= board.length-1;

        if(i!=0 && j!=0 && board[i-1][j-1]==1) neighbors++; //up left
        if(i!=0 && board[i-1][j]==1) neighbors++; // up
        if(i!=0 && j!=lenX && board[i-1][j+1]==1) neighbors++; // up right
        if(j!=lenX && board[i][j+1]==1) neighbors++; //right
        if(i!=lenY && j!=lenX && board[i+1][j+1]==1) neighbors++; //down right
        if(i!=lenY && board[i+1][j]==1) neighbors++; //down
        if(i!=lenY && j!=0 && board[i+1][j-1]==1) neighbors++; //down left
        if(j!=0 && board[i][j-1]==1) neighbors++; //left


        return (board[i][j]==1 &&(neighbors==2 || neighbors==3)) || (board[i][j]==0 && neighbors==3);
    }

}