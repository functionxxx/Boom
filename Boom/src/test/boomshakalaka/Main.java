package test.boomshakalaka;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        int r, c, n;

        @SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

        System.out.print("请输入行数，列数(以逗号分隔):");
        String[] paras = sc.next().split(",");
        r = Integer.parseInt(paras[0]);
        c = Integer.parseInt(paras[1]);

        System.out.print("请输入地雷个数(输入-1为随机):");
        n = sc.nextInt();

        Boom tanmou = new Boom(r, c, n);

        tanmou.printBoard(false);

        while (true){
            System.out.print("\n"+"请输入扫雷位置，以(行,列)表示:");
            paras = sc.next().split(",");
            tanmou.tread(Integer.parseInt(paras[0]), Integer.parseInt(paras[1]));
        }
    }
}
