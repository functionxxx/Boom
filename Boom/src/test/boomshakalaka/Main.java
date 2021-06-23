package test.boomshakalaka;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        int r, c, n;

        @SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

        System.out.print("请输入行数，列数(以逗号分隔):");
        String[] params = sc.next().split(",");
        r = Integer.parseInt(params[0]);
        c = Integer.parseInt(params[1]);

        System.out.print("请输入地雷个数(输入-1为随机):");
        n = sc.nextInt();

        Boom tanmou = new Boom(r, c, n);

        tanmou.printBoard(false);

        while (true){
            System.out.print("\n"+"请输入扫雷位置，以(行,列)表示:");
            params = sc.next().split(",");
            try {
                tanmou.tread(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
            }
            catch (Exception e) {
                System.out.println("所输入位置无效，请重新输入！");
            }
        }
    }
}
