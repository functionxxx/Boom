package test.boomshakalaka;

import java.util.Random;

public class Boom {

    private int[][] gameBoard;
    private int treadCount;
    private final int rows, columns, voidCount;

    // 构造方法
    Boom(int rows, int columns, int boomCount) {

        Random rd = new Random();

        this.rows = rows;
        this.columns = columns;
        // 随机雷数
        if(boomCount==-1) while (boomCount<=0) boomCount = rd.nextInt((int)(rows * columns * 0.75));
        this.voidCount = rows*columns-boomCount;

        // 指定雷数
        if(boomCount>0 && boomCount<rows*columns*0.75) {
            gameBoard = new int[rows][columns];
            for(int i=0; i<boomCount; i++) {
                int x = rd.nextInt(rows), y = rd.nextInt(columns);
                if (gameBoard[x][y] == -1) i--;
                else gameBoard[x][y] = -1;
            }
        } else {
            System.out.println("布雷数超出合法范围");
            System.exit(-1);
        }
    }

    // 输出列号
    private void printColumnNum() {
		System.out.print("\t\t");
        for(int i=1; i<=columns; i++) {
            System.out.print(i+"\t");
        }
        System.out.print("\n\n");
    }

    // 输出
    public void printBoard(int showBooms){

        /*
         清屏(仅Windows CMD环境下有效)
         System.out.print("\033[H\033[2J");
         System.out.flush();
        */

        printColumnNum();
        for(int i=0; i<rows; i++) {
            System.out.print("\t"+ (i+1) +"\t");  // 输出行号
            for(int j=0; j<columns; j++) {
                if(gameBoard[i][j] == -1 && showBooms==1) {
                	System.out.print("@"+"\t"); // 显示炸弹
                } else if (gameBoard[i][j] == 1) {
                	System.out.print("-"+"\t");  // 已踩点
                } else {
                	System.out.print("●"+"\t");  // 未踩点
                }
            }
            System.out.print((i+1) +"\n\n");
        }
        printColumnNum();
        // System.out.println("\t"+" ● 未踩 "+"\t\t"+" - 已踩 "+"\t\t"+" @ 地雷 "+"\t");
    }

    // 踩点
    public int tread(int x, int y) {
        //中雷
        if(gameBoard[x-1][y-1] == -1) {
            printBoard(1);
            System.out.println("你死了！");
            return -1;

        } else {
            gameBoard[x-1][y-1] = 1;
            treadCount++;
            // 踩完所有无雷位置则赢
            if(treadCount==voidCount) {
                printBoard(1);
                System.out.println("你赢了！");
                return 1;

            } else {
                printBoard(0);
                return 0;
            }
        }
    }
}
