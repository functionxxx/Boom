package test.boomshakalaka;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class StartView extends JFrame implements ActionListener {

    public static StartView myView;
    public static final Font myFont = new Font("微软雅黑", Font.BOLD, 13);
    private final int MIN_LAYOUT_SIZE = 4, DEF_LAYOUT_SIZE = 6, MAX_LAYOUT_SIZE = 16;    //  指定最小\默认\最大行列数

    // 定义组件
    JLabel lbRows, lbColumns, lbBombCount, lbBlank;
    JTextField tfBombCount;
    JComboBox<Integer> listRows, listColumns;
    JCheckBox ckbSquare, ckbRandom;
    JButton btnStart, btnCancel;
    JPanel inputArea, buttonArea, topSpareArea;

    ItemListener takeSomeAction = new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
            if(e.getSource().equals(listRows)) {
                // 行数更改
                if(ckbSquare.isSelected()) listColumns.setSelectedIndex(listRows.getSelectedIndex());
                if(!ckbRandom.isSelected()) tfBombCount.setText(generateDefaultBombCount());

            } else if(e.getSource().equals(listColumns)) {
                // 列数更改
                if(!ckbRandom.isSelected()) tfBombCount.setText(generateDefaultBombCount());

            } else if(e.getSource().equals(ckbSquare)) {
                // "正方形"CheckBox更改
                if(e.getStateChange()==ItemEvent.SELECTED) {
                    listColumns.setEnabled(false);
                    listColumns.setSelectedIndex(listRows.getSelectedIndex());

                } else if(e.getStateChange()==ItemEvent.DESELECTED) {
                    listColumns.setEnabled(true);
                }
            } else if(e.getSource().equals(ckbRandom)) {
                // "随机"CheckBox更改
                if(e.getStateChange()==ItemEvent.SELECTED) {
                    tfBombCount.setEnabled(false);
                    tfBombCount.setText("");

                } else if(e.getStateChange()==ItemEvent.DESELECTED) {
                    tfBombCount.setEnabled(true);
                    tfBombCount.setText(generateDefaultBombCount());
                }
            }
        }
    };

    StartView() {
        super("游戏设定");

        // ######################## 顶部留白区 ############################
        topSpareArea = new JPanel();
        topSpareArea.setPreferredSize(new Dimension(1,10));
        this.add(topSpareArea,"North");
        // ######################## 顶部留白区 ############################

        // ########################## 输入区 #############################
        inputArea = new JPanel();
        inputArea.setLayout(new GridLayout(3, 3, 20, 20));

        lbRows = new JLabel("行数:  ", SwingConstants.RIGHT);
        lbRows.setFont(myFont);
        inputArea.add(lbRows);

        listRows = new JComboBox<>();
        inputArea.add(listRows);

        ckbSquare = new JCheckBox(" 正方形",true);
        ckbSquare.setFont(myFont);
        ckbSquare.addItemListener(takeSomeAction);
        inputArea.add(ckbSquare);

        lbColumns = new JLabel("列数:  ", SwingConstants.RIGHT);
        lbColumns.setFont(myFont);
        inputArea.add(lbColumns);

        listColumns = new JComboBox<>();
        listColumns.setEnabled(false);
        inputArea.add(listColumns);

        for(int i=MIN_LAYOUT_SIZE; i<=MAX_LAYOUT_SIZE; i++) {            // 根据行列数限制生成候选列表
            listRows.addItem(i);
            listColumns.addItem(i);
        }
        listRows.setSelectedIndex(DEF_LAYOUT_SIZE - MIN_LAYOUT_SIZE);
        listColumns.setSelectedIndex(DEF_LAYOUT_SIZE - MIN_LAYOUT_SIZE); // 选择默认布局

        listRows.addItemListener(takeSomeAction);
        listColumns.addItemListener(takeSomeAction);

        lbBlank = new JLabel();
        inputArea.add(lbBlank);

        lbBombCount = new JLabel("雷数:  ", SwingConstants.RIGHT);
        lbBombCount.setFont(myFont);
        inputArea.add(lbBombCount);

        tfBombCount = new JTextField();
        tfBombCount.setText(generateDefaultBombCount());
        tfBombCount.addKeyListener(new KeyAdapter() {       // 限制输入数字
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if(ch<'0'||ch>'9') e.consume();
            }
        });
        tfBombCount.addMouseListener(new MouseAdapter() {   // 单击全选文字
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                tfBombCount.selectAll();
            }
        });
        inputArea.add(tfBombCount);

        ckbRandom = new JCheckBox(" 随机",false);
        ckbRandom.setFont(myFont);
        ckbRandom.addItemListener(takeSomeAction);
        inputArea.add(ckbRandom);

        this.add(inputArea);
        // ########################## 输入区 #############################


        // ########################## 按钮区 #############################
        buttonArea = new JPanel();
        buttonArea.setLayout(new FlowLayout(FlowLayout.CENTER,30,10));

        btnStart = new JButton("开始");
        btnStart.setFont(myFont);
        btnStart.addActionListener(this);
        buttonArea.add(btnStart);

        btnCancel = new JButton("取消");
        btnCancel.setFont(myFont);
        btnCancel.addActionListener(this);
        buttonArea.add(btnCancel);

        this.add(buttonArea,"South");
        // ########################## 按钮区 #############################


        this.setSize(265,225);
        this.setResizable(false);
        this.setLocationRelativeTo(null);       // 居中显示
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // 事件响应
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(btnCancel)) {
            // 点击取消
            this.dispose();
            System.exit(0);

        } else if(e.getSource().equals(btnStart)) {
            // 点击开始
            int rows = listRows.getSelectedIndex() + MIN_LAYOUT_SIZE;
            int columns = listColumns.getSelectedIndex() + MIN_LAYOUT_SIZE;
            int bombCount = ckbRandom.isSelected()? -1:Integer.parseInt(tfBombCount.getText());
            if(bombCount==-1 || bombCount>0 && bombCount<=rows*columns*Boom.MAX_BOMB_DENSITY) {    // 判断布雷数是否合法
                this.setVisible(false);
                new GameView(rows, columns, bombCount);

            } else {
                JOptionPane.showMessageDialog(this, " 布雷数超出合法范围！ ");
            }
        }
    }

    // 计算默认布雷数
    private String generateDefaultBombCount() {
        double bCount = (listRows.getSelectedIndex() + MIN_LAYOUT_SIZE)
                        *(listColumns.getSelectedIndex() + MIN_LAYOUT_SIZE)
                        *Boom.DEF_BOMB_DENSITY;
        return String.valueOf((int)bCount);
    }

    public static void main(String[] args) {
        myView = new StartView();
    }
}
