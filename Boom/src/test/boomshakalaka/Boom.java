package test.boomshakalaka;

import java.util.Random;

public class Boom {

    private int[][] gameBoard;

    private final int rows, columns;

    // 构造方法
    Boom (int rows, int columns, int n) {

        this.rows = rows;
        this.columns = columns;

        Random rd = new Random();

        // 随机雷数
        if(n==-1) while (n<=0) n = rd.nextInt((int)(rows * columns * 0.75));

        // 指定雷数
        if (n>0 && n<rows*columns*0.75) {
            gameBoard = new int[rows][columns];
            for (int i=0; i<n; i++) {
                int x = rd.nextInt(rows), y = rd.nextInt(columns);
                if (gameBoard[x][y] == -1) i--;
                else gameBoard[x][y] = -1;
            }
        }
        else {
            System.out.println("布雷数超出合法范围");
            System.exit(-1);
        }
    }

    // 输出列号
    private void printColumnNum(){
        for(int i=0; i<=columns; i++){
            if(i==0) System.out.print("\n"+"\t");
            else System.out.print(i+"\t");
        }
        System.out.println();
    }

    // 输出
    public void printBoard(boolean showBooms){
        printColumnNum();
        for(int i=0; i<rows; i++){
            System.out.print("\n"+String.valueOf(i+1)+"\t");  // 输出行号
            for(int j=0; j<columns; j++){
                if(gameBoard[i][j] == -1 && showBooms) System.out.print("@"+"\t"); // 显示炸弹
                else if(gameBoard[i][j] == 1) System.out.print("*"+"\t");  // 已踩点
                else System.out.print("●"+"\t");  // 未踩点
            }
            System.out.print(String.valueOf(i+1)+"\t\n");
        }
        printColumnNum();
    }

    // 踩点
    public void tread(int x, int y){
        //中雷
        if(gameBoard[x-1][y-1] == -1){
            printBoard(true);
            System.out.println("\n"+"你死了！");
            System.exit(0);
        }
        else{
            gameBoard[x-1][y-1] = 1;
            printBoard(false);
        }
    }
}
