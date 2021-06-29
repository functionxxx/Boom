package test.boomshakalaka;

import java.util.Random;

public class Boom {

    private int[][] gameBoard;
    private final int voidCount;
    private int treadCount;

    // 构造方法
    Boom(int rows, int columns, int bombCount) {

        Random rd = new Random();

        // 随机雷数
        if(bombCount==-1) while (bombCount<=0) bombCount = rd.nextInt((int)(rows*columns*0.75));
        this.voidCount = rows * columns - bombCount;

        // 指定雷数
        if(bombCount>0 && bombCount<rows*columns*0.75) {
            gameBoard = new int[rows][columns];
            for(int i=0; i<bombCount; i++) {
                int x = rd.nextInt(rows), y = rd.nextInt(columns);
                if (gameBoard[x][y] == -1) i--;
                else gameBoard[x][y] = -1;
            }
        } else {
            System.out.println("布雷数超出合法范围");
            System.exit(-1);
        }
    }

    public boolean isBomb(int x, int y) {
        return gameBoard[x][y] == -1;
    }

    // 踩点
    public int tread(int x, int y) {
        //中雷
        if(gameBoard[x][y] == -1) {
            return -1;

        } else {
            gameBoard[x][y] = 1;
            treadCount++;

            // 踩完所有无雷位置则赢
            if(treadCount==voidCount) {
                return 1;

            } else {
                return 0;
            }
        }
    }
}
