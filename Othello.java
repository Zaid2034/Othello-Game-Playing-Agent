import java.io.*;
import java.util.*;

public class Othello {
    int turn;
    int winner;
    int z;
    int board[][];

    public Othello(String filename) throws Exception {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        turn = sc.nextInt();
        z=turn;
        board = new int[8][8];
        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j){
                board[i][j] = sc.nextInt();
            }
        }
        winner = -1;
    }


public int boardScore() {
      
        int countBlack=0;
        int countWhite=0;
      
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(board[i][j]==0){
                    countBlack++;
                }
                else if(board[i][j]==1){
                    countWhite++;
                }
            }
        }
        if(z==0){
            return countBlack-countWhite;
        }
        else{
            return countWhite-countBlack;
        }
    }
    public int bestMove(int k) {
        int[] a = new int[1];
        if (k % 2 == 0) {
            a[0] = Integer.MAX_VALUE;
        } else {
            a[0] = Integer.MIN_VALUE;
        }
        int startTurn = turn;
        Boolean [][] vis=new Boolean [8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                vis[i][j]=false;
            }
        }
        int[] ans=new int[1];
        ans[0]=Integer.MAX_VALUE;
        solve(a, k, 0, startTurn, turn,vis,ans);
        return ans[0];
    }

    public ArrayList<Integer> fullGame(int k) {
        ArrayList<Integer> w=new ArrayList<>();
        while(true){
            int z=0;
            if(turn==0){
                z=bestMove(k);
                turn=1;
            }
            else if(turn==1){
                z=bestMove(k);
                turn=0;
            }
            if(z==Integer.MAX_VALUE || z==Integer.MIN_VALUE){
                break;
            }
            else{
                
                w.add(z);
                int i=z/8;
                int j=z%8;
                if(turn==0){
                    checkInAllDirection(i,j,1);
                }
                else {
                    checkInAllDirection(i,j,0);

                }
            }

        }
        int countBlack=0;
        int countWhite=0;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(board[i][j]==0){
                    countBlack++;
                }
                else if(board[i][j]==1){
                    countWhite++;
                }
            }
        }
        if(countBlack>countWhite){
            winner=0;
        }
        else if(countWhite>countBlack){
            winner=1;
        }
        return w;
    }

    public int[][] getBoardCopy() {
        int copy[][] = new int[8][8];
        for(int i = 0; i < 8; ++i)
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        return copy;
    }

    public int getWinner() {
        return winner;
    }

    public int getTurn() {
        return turn;
    }
    private int solve(int []a,int k,int d,int start,int turn,Boolean[][] vis,int[] ans){

        if(d==k){
        
            return boardScore();
        }
        if(start==0) {
            if (turn == 0) {
                int maxi = Integer.MIN_VALUE;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j] == 1) {
                            if (i + 1 < 8 && board[i + 1][j] == -1 && !vis[i + 1][j]) {
                                int[][] temp = new int[8][8];

                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i + 1, j, 0)) {
                                    vis[i + 1][j] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], ((i + 1) * 8 + j));
                                        }
                                        else if(z>maxi){
                                            ans[0]=((i+1)*8)+(j);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);

                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i - 1 >= 0 && board[i - 1][j] == -1 && !vis[i - 1][j]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];

                                    }
                                }
                                if (checkInAllDirection(i - 1, j, 0)) {
                                    vis[i - 1][j] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], ((i - 1) * 8 + j));
                                        }
                                        else if(z>maxi){
                                            ans[0]=((i-1)*8)+(j);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (j + 1 < 8 && board[i][j + 1] == -1 && !vis[i][j + 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];

                                    }
                                }
                                if (checkInAllDirection(i, j + 1, 0)) {
                                    vis[i][j + 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], (i * 8 + (j + 1)));
                                        }
                                        else if(z>maxi){
                                            ans[0]=((i)*8)+(j+1);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (j - 1 >= 0 && board[i][j - 1] == -1 && !vis[i][j - 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i, j - 1, 0)) {
                                    vis[i][j - 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], (i * 8 + (j - 1)));
                                        }
                                        else if(z>maxi){
                                            ans[0]=(i*8)+(j-1);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i + 1 < 8 && j + 1 < 8 && board[i + 1][j + 1] == -1 && !vis[i + 1][j + 1]) {
                                int[][] temp = new int[8][8];

                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i + 1, j + 1, 0)) {
                                    vis[i + 1][j + 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], ((i + 1) * 8 + (j + 1)));
                                        }
                                        else if(z>maxi){
                                            ans[0]=((i+1)*8)+(j+1);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i + 1 < 8 && j - 1 >= 0 && board[i + 1][j - 1] == -1 && !vis[i + 1][j - 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i + 1, j - 1, 0)) {
                                    vis[i + 1][j - 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], ((i + 1) * 8 + (j - 1)));
                                        }
                                        else if(z>maxi){
                                            ans[0]=((i+1)*8)+(j-1);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i - 1 >= 0 && j + 1 < 8 && board[i - 1][j + 1] == -1 && !vis[i - 1][j + 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i - 1, j + 1, 0)) {
                                    vis[i - 1][j + 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], ((i - 1) * 8 + (j + 1)));
                                        }
                                        else if(z>maxi){
                                            ans[0]=((i-1)*8)+(j+1);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i - 1 >= 0 && j - 1 >= 0 && board[i - 1][j - 1] == -1 && !vis[i - 1][j - 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i - 1, j - 1, 0)) {
                                    vis[i - 1][j - 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== maxi) {
                                            ans[0] = Math.min(ans[0], ((i - 1) * 8 + (j - 1)));
                                        }
                                        else if(z>maxi){
                                            ans[0]=((i-1)*8)+(j-1);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }

                        }

                    }
                }
                return maxi;
            }
            else {
                int mini = Integer.MAX_VALUE;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j] == 0) {
                            if (i + 1 < 8 && board[i + 1][j] == -1 && !vis[i + 1][j]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i + 1, j, 1)) {
                                    vis[i + 1][j] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i +1) * 8 + (j )));
                                        }
                                        else if(z<mini){
                                            ans[0]=((i+1)*8)+(j);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i - 1 >= 0 && board[i - 1][j] == -1 && !vis[i - 1][j]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i - 1, j, 1)) {
                                    vis[i - 1][j] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i -1) * 8 + (j )));
                                        }
                                        else if(z<mini){
                                            ans[0]=((i-1)*8)+(j);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (j + 1 < 8 && board[i][j + 1] == -1 && !vis[i][j + 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i, j + 1, 1)) {
                                    vis[i][j + 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i ) * 8 + (j+1 )));
                                        }
                                        else if(z<mini){
                                            ans[0]=((i)*8)+(j+1);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (j - 1 >= 0 && board[i][j - 1] == -1 && !vis[i][j - 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i, j - 1, 1)) {
                                    vis[i][j - 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i ) * 8 + (j-1 )));
                                        }
                                        else if(z<mini){
                                            ans[0]=((i)*8)+(j-1);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;

                            }
                            if (i + 1 < 8 && j + 1 < 8 && board[i + 1][j + 1] == -1 && !vis[i + 1][j + 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i + 1, j + 1, 1)) {
                                    vis[i + 1][j + 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i +1) * 8 + (j+1 )));
                                        }
                                        else if(z<mini){
                                            ans[0]=((i+1)*8)+(j+1);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i + 1 < 8 && j - 1 >= 0 && board[i + 1][j - 1] == -1 && !vis[i + 1][j - 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i + 1, j - 1, 1)) {
                                    vis[i + 1][j - 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i +1) * 8 + (j-1 )));
                                        }
                                        else if(z<mini){
                                            ans[0]=((i+1)*8)+(j-1);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                        
                            if (i - 1 >= 0 && j + 1 < 8 && board[i - 1][j + 1] == -1 && !vis[i - 1][j + 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i - 1, j + 1, 1)) {
                                    vis[i - 1][j + 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i -1) * 8 + (j+1 )));
                                        }
                                        else if(z<mini){
                                            ans[0]=((i-1)*8)+(j+1);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i - 1 >= 0 && j - 1 >= 0 && board[i - 1][j - 1] == -1 && !vis[i - 1][j - 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i - 1, j - 1, 1)) {
                                    vis[i - 1][j - 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i -1) * 8 + (j-1 )));
                                        }
                                        else if(z<mini){
                                            ans[0]=((i-1)*8)+(j-1);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                        }
                    }
                }
              
                return mini;
            }
        }
        else{
            if (turn == 1) {
                int maxi = Integer.MIN_VALUE;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j] == 0) {
                            if (i + 1 < 8 && board[i + 1][j] == -1 && !vis[i + 1][j]) {
                                int[][] temp = new int[8][8];

                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i + 1, j, 1)) {
                                    vis[i + 1][j] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], ((i + 1) * 8 + j));
                                        }
                                        else if(z>maxi){
                                            ans[0]=((i+1)*8)+(j);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);

                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i - 1 >= 0 && board[i - 1][j] == -1 && !vis[i - 1][j]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];

                                    }
                                }
                                if (checkInAllDirection(i - 1, j, 1)) {
                                    vis[i - 1][j] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], ((i - 1) * 8 + j));
                                        }
                                        else if(z>maxi){
                                            ans[0]=((i-1)*8)+(j);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (j + 1 < 8 && board[i][j + 1] == -1 && !vis[i][j + 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];

                                    }
                                }
                                if (checkInAllDirection(i, j + 1, 1)) {
                                    vis[i][j + 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], (i * 8 + (j + 1)));
                                        }
                                        else if(z>maxi){
                                            ans[0]=((i)*8)+(j+1);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (j - 1 >= 0 && board[i][j - 1] == -1 && !vis[i][j - 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i, j - 1, 1)) {
                                
                                    vis[i][j - 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], (i * 8 + (j - 1)));

                                        }
                                        else if(z>maxi){
                                            ans[0]=(i*8)+(j-1);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                                  
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i + 1 < 8 && j + 1 < 8 && board[i + 1][j + 1] == -1 && !vis[i + 1][j + 1]) {

                                int[][] temp = new int[8][8];

                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i + 1, j + 1, 1)) {
                                 
                                    vis[i + 1][j + 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], ((i + 1) * 8 + (j + 1)));

                                        }
                                        else if(z>maxi){
                                            ans[0]=((i+1)*8)+(j+1);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                              
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                          
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i + 1 < 8 && j - 1 >= 0 && board[i + 1][j - 1] == -1 && !vis[i + 1][j - 1]) {


                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i + 1, j - 1, 1)) {
                              
                                    vis[i + 1][j - 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], ((i + 1) * 8 + (j - 1)));

                                        }
                                        else if(z>maxi){
                                            ans[0]=((i+1)*8)+(j-1);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                           
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i - 1 >= 0 && j + 1 < 8 && board[i - 1][j + 1] == -1 && !vis[i - 1][j + 1]) {

                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i - 1, j + 1, 1)) {
                             
                                    vis[i - 1][j + 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z == maxi) {
                                            ans[0] = Math.min(ans[0], ((i - 1) * 8 + (j + 1)));

                                        }
                                        else if(z>maxi){
                                            ans[0]=((i-1)*8)+(j+1);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                               
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i - 1 >= 0 && j - 1 >= 0 && board[i - 1][j - 1] == -1 && !vis[i - 1][j - 1]) {

                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i - 1, j - 1, 1)) {
                                   
                                    vis[i - 1][j - 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 0, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== maxi) {
                                            ans[0] = Math.min(ans[0], ((i - 1) * 8 + (j - 1)));
                                   
                                        }
                                        else if(z>maxi){
                                            ans[0]=((i-1)*8)+(j-1);
                                        }
                                    }
                                    maxi = Math.max(maxi, z);
                                    a[0] = Math.max(a[0], z);
                                    board = temp;
                                    d = temp2;
                                 
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }

                        }

                    }
                }
                
                return maxi;
            }
            else {
                int mini = Integer.MAX_VALUE;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j] == 1) {
                       
                            if (i + 1 < 8 && board[i + 1][j] == -1 && !vis[i + 1][j]) {

                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i + 1, j, 0)) {
                              
                                    vis[i + 1][j] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i +1) * 8 + (j )));

                                        }
                                        else if(z<mini){
                                            ans[0]=((i+1)*8)+(j);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                          
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i - 1 >= 0 && board[i - 1][j] == -1 && !vis[i - 1][j]) {


                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i - 1, j, 0)) {
                                    vis[i - 1][j] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i -1) * 8 + (j )));

                                        }
                                        else if(z<mini){
                                            ans[0]=((i-1)*8)+(j);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                        
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                           
                            }
                            if (j + 1 < 8 && board[i][j + 1] == -1 && !vis[i][j + 1]) {

                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i, j + 1, 0)) {

                                    vis[i][j + 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i ) * 8 + (j+1 )));

                                        }
                                        else if(z<mini){
                                            ans[0]=((i)*8)+(j+1);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                             
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (j - 1 >= 0 && board[i][j - 1] == -1 && !vis[i][j - 1]) {
                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i, j - 1, 0)) {

                                    vis[i][j - 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i ) * 8 + (j-1 )));

                                        }
                                        else if(z<mini){
                                            ans[0]=((i)*8)+(j-1);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;

                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;

                            }
                            if (i + 1 < 8 && j + 1 < 8 && board[i + 1][j + 1] == -1 && !vis[i + 1][j + 1]) {

                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i + 1, j + 1, 0)) {
                                 
                                    vis[i + 1][j + 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i +1) * 8 + (j+1 )));

                                        }
                                        else if(z<mini){
                                            ans[0]=((i+1)*8)+(j+1);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                              
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i + 1 < 8 && j - 1 >= 0 && board[i + 1][j - 1] == -1 && !vis[i + 1][j - 1]) {

                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i + 1, j - 1, 0)) {
                                  
                                    vis[i + 1][j - 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i +1) * 8 + (j-1 )));                                  
                                        }
                                        else if(z<mini){
                                            ans[0]=((i+1)*8)+(j-1);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                                
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                    
                            if (i - 1 >= 0 && j + 1 < 8 && board[i - 1][j + 1] == -1 && !vis[i - 1][j + 1]) {


                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i - 1, j + 1, 0)) {
        
                                    vis[i - 1][j + 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i -1) * 8 + (j+1 )));

                                        }
                                        else if(z<mini){
                                            ans[0]=((i-1)*8)+(j+1);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                            if (i - 1 >= 0 && j - 1 >= 0 && board[i - 1][j - 1] == -1 && !vis[i - 1][j - 1]) {

                                int[][] temp = new int[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        temp[l][m] = board[l][m];
                                    }
                                }
                                if (checkInAllDirection(i - 1, j - 1, 0)) {
                            
                                    vis[i - 1][j - 1] = true;
                                    int temp2 = d;
                                    d = d + 1;
                                    int z = solve(a, k, d, start, 1, vis, ans);
                                    if (temp2 == 0) {
                                        if (z== mini) {
                                            ans[0] = Math.min(ans[0], ((i -1) * 8 + (j-1 )));

                                        }
                                        else if(z<mini){
                                            ans[0]=((i-1)*8)+(j-1);
                                        }
                                    }
                                    mini = Math.min(mini, z);
                                    a[0] = Math.min(a[0], z);
                                    board = temp;
                                    d = temp2;
                                    
                                }
                                Boolean[][] tempVis = new Boolean[8][8];
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        tempVis[l][m] = vis[l][m];
                                    }
                                }
                                vis = tempVis;
                            }
                        }
                    }
                }
                return mini;
            }
        }
    }
    private Boolean checkInAllDirection(int i,int j,int turn){
           boolean flag=false;
        if(turn==0){
            board[i][j]=0;
        }
        else{
            board[i][j]=1;
        }
           if(solve1(i+1,j,turn,0)){
               flag=true;
           }
           if(solve2(i-1,j,turn,0)){
               flag=true;

           }
           if(solve3(i,j+1,turn,0)){
               flag=true;
                 
           }
           if(solve4(i,j-1,turn,0)){
               flag=true;
                 
           }
           if(solve5(i+1,j+1,turn,0)){
               flag=true;
                 
           }
           if(solve6(i+1,j-1,turn,0)){
               flag=true;
                  
           }
           if(solve7(i-1,j+1,turn,0)){
               flag=true;
           }
           if(solve8(i-1,j-1,turn,0)){
               flag=true;
           }
           if(!flag){
               board[i][j]=-1;
           }
           return flag;
    }
    private Boolean solve1(int a,int b,int turn,int count){
        if(a==8){
            return false;
        }
        if(board[a][b]==-1){
           
            return false;
        }
        if(turn ==0 && board[a][b]==0){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        if(turn==1 && board[a][b]==1){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        count++;
        boolean flag= solve1(a+1,b,turn,count);
        if(flag){
            if(turn==0){
                board[a][b]=0;
            }
            else if(turn==1){
                board[a][b]=1;
           }

        }
        return flag;
    }
    private Boolean solve2(int a,int b,int turn,int count){
        if(a<0){
            return false;
        }
        if(board[a][b]==-1){
            return false;
        }
        if(turn ==0 && board[a][b]==0){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        if(turn==1 && board[a][b]==1){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        count++;
        boolean flag=solve2(a-1,b,turn,count);
        if(flag){
            if(turn==0){
                board[a][b]=0;
            }
            else if(turn==1){
                board[a][b]=1;
            }

        }
        return flag;
    }
    private Boolean solve3(int a,int b,int turn,int count){
        if(b==8){
            return false;
        }
        if(board[a][b]==-1){
            return false;
        }
        if(turn ==0 && board[a][b]==0){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        if(turn==1 && board[a][b]==1){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        count++;
        boolean flag=solve3(a,b+1,turn,count);
        if(flag){
            if(turn==0){
                board[a][b]=0;
            }
            else if(turn==1){
                board[a][b]=1;
            }

        }
        return flag;
    }
    private Boolean solve4(int a,int b,int turn,int count){

        if(b<0){
            return false;
        }
        if(board[a][b]==-1){
            return false;
        }
        if(turn ==0 && board[a][b]==0){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        if(turn==1 && board[a][b]==1){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        count++;
        boolean flag=solve4(a,b-1,turn,count);
        if(flag){
            if(turn==0){
                board[a][b]=0;
            }
            else if(turn==1){
                board[a][b]=1;
            }

        }
        return flag;
    }
    private Boolean solve5(int a,int b,int turn,int count){
        if(a==8 || b==8){
            return false;
        }
        if(board[a][b]==-1){
            return false;
        }
        if(turn ==0 && board[a][b]==0){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        if(turn==1 && board[a][b]==1){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        count++;
        boolean flag=solve5(a+1,b+1,turn,count);
        if(flag){
            if(turn==0){
                board[a][b]=0;
            }
            else if(turn==1){
                board[a][b]=1;
            }

        }
        return flag;
    }
    private Boolean solve6(int a,int b,int turn,int count){
        if(a==8 || b<0){
            return false;
        }
        if(board[a][b]==-1){
            return false;
        }
        if(turn ==0 && board[a][b]==0){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        if(turn==1 && board[a][b]==1){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        count++;
        boolean flag=solve6(a+1,b-1,turn,count);
        if(flag){
            if(turn==0){
                board[a][b]=0;
            }
            else if(turn==1){
                board[a][b]=1;
            }


        }
        return flag;
    }
    private Boolean solve7(int a,int b,int turn,int count){
        if(a<0 || b==8){
            return false;
        }
        if(board[a][b]==-1){
            return false;
        }
        if(turn ==0 && board[a][b]==0){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        if(turn==1 && board[a][b]==1){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        count++;
        boolean flag=solve7(a-1,b+1,turn,count);
        if(flag){
            if(turn==0){
                board[a][b]=0;
            }
            else if(turn==1){
                board[a][b]=1;
            }

        }
        return flag;
    }
    private Boolean solve8(int a,int b,int turn,int count){
        if(a<0 || b<0){
            return false;
        }
        if(board[a][b]==-1){
            return false;
        }
        if(turn ==0 && board[a][b]==0){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        if(turn==1 && board[a][b]==1){
            if(count>0){
                return true;
            }
            else{
                return false;
            }
        }
        count++;
        boolean flag=solve8(a-1,b-1,turn,count);
        if(flag){
            if(turn==0){
                board[a][b]=0;
            }
            else if(turn==1){
                board[a][b]=1;
            }

        }
        return flag;
    }
}