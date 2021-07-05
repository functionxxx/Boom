package test.boomshakalaka;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class GameView extends JFrame implements ActionListener {

    private final int rows, columns, bombCount;
    private final Boom bombLayout;
    private final ImageIcon iconBomb = new ImageIcon(this.getClass().getResource("icon-32x32.png"));
    private int statusFlag;

    // 定义组件
    JPanel bombArea, buttonArea, leftSpareArea, rightSpareArea, topSpareArea;
    JButton btnAgain, btnBack;
    List<JButton> blocks = new ArrayList<>();

    // 构造方法
    GameView(int rows, int columns, int bombCount) {
        super(" 扫雷游戏");
        this.rows = rows;
        this.columns = columns;
        this.bombCount = bombCount;

        // 直接叉掉窗口时的默认操作
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                GameView.super.dispose();
                StartView.myView.setVisible(true);
            }
        });

        bombLayout = new Boom(rows, columns, bombCount);

        // ########################## 地雷区 #############################
        bombArea = new JPanel();
        bombArea.setLayout(new GridLayout(rows, columns, 0, 0));

        for(int i=0; i<rows*columns; i++) {
            blocks.add(i, new JButton());
            blocks.get(i).addActionListener(this);
            bombArea.add(blocks.get(i));
        }
        this.add(bombArea);
        // ########################## 地雷区 #############################


        // ########################## 按钮区 #############################
        buttonArea = new JPanel();
        buttonArea.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));

        btnAgain = new JButton("重来");
        btnAgain.setFont(StartView.myFont);
        btnAgain.addActionListener(this);
        buttonArea.add(btnAgain);

        btnBack = new JButton("返回");
        btnBack.setFont(StartView.myFont);
        btnBack.addActionListener(this);
        buttonArea.add(btnBack);

        this.add(buttonArea,"South");
        // ########################## 按钮区 #############################


        // ######################## 左-右-顶留白区 ########################
        leftSpareArea = new JPanel();
        leftSpareArea.setPreferredSize(new Dimension(10,1));
        this.add(leftSpareArea,"West");

        rightSpareArea = new JPanel();
        rightSpareArea.setPreferredSize(new Dimension(10,1));
        this.add(rightSpareArea,"East");

        topSpareArea = new JPanel();
        topSpareArea.setPreferredSize(new Dimension(1,10));
        this.add(topSpareArea,"North");
        // ######################## 左-右-顶留白区 ########################


        this.setSize(columns*36+20, rows*40+55);
        this.setMinimumSize(new Dimension(200,260));
        this.setResizable(false);
        this.setLocationRelativeTo(null); // 居中显示
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // 交由WindowListener处理
        this.setVisible(true);
    }

    private void drawBomb() {
        for(int i=0; i<rows; i++) {
            for(int j=0; j<columns; j++) {
                if(bombLayout.isBomb(i, j)) {
                    int idx = i==0? j:i*columns+j;  // x,y坐标到index转换
                    blocks.get(idx).setIcon(iconBomb);
                }
            }
        }
    }

    // 事件响应
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnBack)) {
            // 点击返回
            this.dispose();
            StartView.myView.setVisible(true);

        } else if(e.getSource().equals(btnAgain)) {
            // 点击重来
            this.dispose();
            new GameView(rows, columns, bombCount);

        } else if(statusFlag == Boom.STATUS_ALIVE) {
            int idx = blocks.indexOf(e.getSource());
            blocks.get(idx).setEnabled(false);

            int x = idx/columns;
            int y = idx<columns? idx:idx%columns;  // index到x,y坐标转换
            statusFlag = bombLayout.tread(x, y);

            if(statusFlag == Boom.STATUS_DEAD) {
                blocks.get(idx).setIcon(iconBomb);
                blocks.get(idx).setBackground(Color.RED);
                JOptionPane.showMessageDialog(this, " 你死了！ ");
                drawBomb();

            } else if(statusFlag == Boom.STATUS_WON) {
                JOptionPane.showMessageDialog(this, " 你赢了！ ");
                drawBomb();
            }
        }
    }
}
