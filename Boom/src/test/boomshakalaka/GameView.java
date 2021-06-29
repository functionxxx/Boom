package test.boomshakalaka;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class GameView extends JFrame implements ActionListener {

    private final int rows, columns, bombCount, totalCount;
    private int statusFlag;
    private final Boom tanmou;
    private final ImageIcon imgBomb = new ImageIcon("images/32x32.png");

    // 定义组件
    JPanel bombArea, buttonArea;
    JButton jbAgain, jbClose;
    List<JButton> blocks = new ArrayList<>();

    // 构造方法
    GameView(int rows, int columns, int bombCount) {
        super(" 扫雷游戏");
        this.rows = rows;
        this.columns = columns;
        this.bombCount = bombCount;
        this.totalCount = rows*columns;

        tanmou = new Boom(rows, columns, bombCount);

        bombArea = new JPanel();
        bombArea.setLayout(new GridLayout(rows, columns, 0, 0));

        for(int i=0; i<rows*columns; i++) {
            blocks.add(i, new JButton());
            blocks.get(i).addActionListener(this);
            bombArea.add(blocks.get(i));
        }

        buttonArea = new JPanel();
        buttonArea.setLayout(new FlowLayout(FlowLayout.CENTER,20,5));

        jbAgain = new JButton("重来");
        jbAgain.addActionListener(this);
        buttonArea.add(jbAgain);

        jbClose = new JButton("关闭");
        jbClose.addActionListener(this);
        buttonArea.add(jbClose);

        this.add(bombArea);
        this.add(buttonArea,"South");

        this.setSize(columns*36, rows*40+40);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // 居中显示
        this.setResizable(false);
        this.setVisible(true);
    }

    private void printBomb() {
        for(int i=0; i<rows; i++) {
            for(int j=0; j<columns; j++) {
                if(tanmou.isBomb(i, j)) {
                    int idx = i==0? j:i*columns+j;  // x,y坐标到index转换
                    blocks.get(idx).setIcon(imgBomb);
                }
            }
        }
    }

    // 事件响应器
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(jbClose)) {
            // 点击关闭
            this.dispose();

        } else if(e.getSource().equals(jbAgain)) {
            // 点击重来
            this.dispose();
            new GameView(rows, columns, bombCount);

        } else if(statusFlag == 0) {
            for(int i=0; i<totalCount; i++) {
                if(e.getSource().equals(blocks.get(i))) {
                    blocks.get(i).setEnabled(false);
                    
                    int x = i/columns, y = i<columns? i:i%columns;  // index到x,y坐标转换
                    statusFlag = tanmou.tread(x, y);

                    if(statusFlag==-1) {
                        blocks.get(i).setIcon(imgBomb);
                        blocks.get(i).setBackground(Color.RED);
                        JOptionPane.showMessageDialog(this, " 你死了！ ");
                        printBomb();

                    } else if(statusFlag==1) {
                        JOptionPane.showMessageDialog(this, " 你赢了！ ");
                        printBomb();

                    }
                }
            }
        }
        // e.getSource().setEnabled(false); ??
    }
}
