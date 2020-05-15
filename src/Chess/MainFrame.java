package Chess;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The class will run the project.
 *
 * <p>the following information is maintained:</p>
 * <ol>
 *	<li>two chess broads, a <code>ChessBroadForHumans</code> and a <code>ChessBroadForHumanVersusMachine</code></li>
 *	<li>
 *	a tool bar, a <code>JToolBar</code>, the followings are contained:
 *	<ul>
 *	<li>a button used for restarting, a <code>JButton</code>   </li>
 *	<li>a button used for retracting, a <code>JButton</code>   </li>
 *	<li>a button used for escaping, a <code>JButton</code>   </li>
 *	</ul>
 *	</li>
 *	<li>
 *	a menu bar, a <code>JMenuBar</code>, the followings are contained:
 * <ul>
 * <li>
 * a game menu, a <code>JMenu</code>, the followings are contained:
 * <ul>
 * <li>a menu item used for restarting, a <code>JMenuItem</code>   </li>
 * <li>a menu item used for retracting, a <code>JMenuItem</code>   </li>
 * <li>a menu item used for escaping, a <code>JMenuItem</code>   </li>
 * </ul>
 * </li>
 * <li>
 * a help menu, a <code>JMenu</code>, the followings are contained:
 * <ul>
 * <li>a menu item used for looking for help, a <code>JMenuItem</code>   </li>
 * <li>a menu item used for providing with some information about the key shortcut, a <code>JMenuItem</code>   </li>
 * <li>a menu item used for providing with some information about the copyright, a <code>JMenuItem</code>   </li>
 * </ul>
 * </li>
 * <li>
 * a setting menu, a <code>JMenu</code>, the followings are contained:
 * <ul>
 * <li>a menu item used for setting the winning condition, a <code>JMenuItem</code>   </li>
 * <li>a menu item used for setting the size of the chess broad, a <code>JMenuItem</code>   </li>
 * </ul>
 * </li>
 *	</ul>
 *	</li>
 * <li>
 * the panel, a <code>JPanel</code>, the followings are contained:
 * <ul>
 * <li>a label used for showing when the game started, a <code>JLabel</code></li>
 * <li>a format for showing the time, a <code>SimpleDateFormat</code></li>
 * </ul>
 * </li>
 * </ol>
 */
public class MainFrame extends JFrame {

    private ChessBoardContent chessBoardContent;
    private ChessBoardForHumans chessBoardForHumans;
    private ChessBoardForHumanVersusMachine chessBoardForHumanVersusMachine;
    private String option;
    private String option1 = "玩家对战";
    private String option2 = "人机对战";

    private JToolBar jToolBar;
    private JButton restartButton, retractButton, escapeButton;

    private JMenuBar jMenuBar;
    private JMenu gameMenu;
    private JMenuItem restartItem, retractItem, escapeItem;
    private JMenu helpMenu;
    private JMenuItem ruleItem, keyShortcutItem, copyrightItem;
    private JMenu settingMenu;
    private JMenuItem settingNumberItem, settingSizeItem;

    private JLabel time;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    private JPanel panel;


    public MainFrame() {
        JOptionPane.showMessageDialog(null, "欢迎您进行“n子棋”游戏！");

        // 设置标题“n子棋”
        setTitle("n子棋");

        // 选择棋盘
        Object[] options = {option1, option2};
        int choiceInt = JOptionPane.showOptionDialog(null, "请选择对战方式", "选择",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        Container contentPane = getContentPane();

        // 放置棋盘
        switch (choiceInt) {
            case JOptionPane.YES_OPTION :
                option = option1;
                chessBoardContent = new ChessBoardContent(ChessBoardForHumans.getInstance());
                chessBoardForHumans = (ChessBoardForHumans)chessBoardContent.getChessBoard();
                contentPane.add(chessBoardForHumans);
                chessBoardForHumans.setOpaque(true);
                break;
            case JOptionPane.NO_OPTION :
                option = option2;
                chessBoardContent = new ChessBoardContent(ChessBoardForHumanVersusMachine.getInstance());
                chessBoardForHumanVersusMachine = (ChessBoardForHumanVersusMachine)chessBoardContent.getChessBoard();
                contentPane.add(chessBoardForHumanVersusMachine);
                chessBoardForHumanVersusMachine.setOpaque(true);
                break;
            default :
                break;
        }

        // 设置菜单jMenuBar
        jMenuBar = new JMenuBar();
        // 添加“游戏”“帮助”“设置”“模式”菜单至菜单栏
        gameMenu = new JMenu("游戏(G)");
        helpMenu = new JMenu("帮助(H)");
        settingMenu = new JMenu("设置(S)");
        // 为菜单添加快捷键
        gameMenu.setMnemonic('G');
        helpMenu.setMnemonic('H');
        settingMenu.setMnemonic('S');


        // 设置“游戏”菜单
        jMenuBar.add(gameMenu);
        // 初始化“游戏”菜单的菜单项(restartItem, retractItem, escapeItem)
        restartItem = new JMenuItem("重新开始(C)");
        retractItem = new JMenuItem("悔棋(H)");
        escapeItem = new JMenuItem("退出(T)");
        // 设置菜单项(restartItem, retractItem, escapeItem)的快捷键
        restartItem.setMnemonic('C');
        retractItem.setMnemonic('H');
        escapeItem.setMnemonic('T');
        // 为“游戏”菜单添加菜单项(restartItem, retractItem, escapeItem)
        gameMenu.add(restartItem);
        gameMenu.add(retractItem);
        gameMenu.add(escapeItem);
        // 为“游戏”菜单的菜单项(restartItem, retractItem, escapeItem)添加时间监听器
        restartItem.addActionListener(event -> {
            System.out.println("重新开始\t\t\t\t\t\t" + sdf.format(new Date()));
            if (chessBoardForHumans != null) {
                chessBoardForHumans.restart();
            }
            if (chessBoardForHumanVersusMachine != null) {
                chessBoardForHumanVersusMachine.restart();
            }
        });
        retractItem.addActionListener(event -> {
            if (chessBoardForHumans != null) {
                chessBoardForHumans.retract();
            }
            if (chessBoardForHumanVersusMachine != null) {
                chessBoardForHumanVersusMachine.retract();
            }
        });
        escapeItem.addActionListener(event -> {
            System.exit(0);
        });

        // 设置“帮助”菜单
        jMenuBar.add(helpMenu);
        // 初始化“帮助”菜单的菜单项(ruleItem, keyShortcutItem, copyrightItem)
        ruleItem = new JMenuItem("规则(R)");
        keyShortcutItem = new JMenuItem("快捷键说明(K)");
        copyrightItem = new JMenuItem("版权声明(C)");
        // 设置菜单项(ruleItem, keyShortcutItem, copyrightItem)的快捷键
        ruleItem.setMnemonic('R');
        keyShortcutItem.setMnemonic('K');
        copyrightItem.setMnemonic('C');
        // 为“帮助”菜单添加菜单项(ruleItem, keyShortcutItem, copyrightItem)
        helpMenu.add(ruleItem);
        helpMenu.add(keyShortcutItem);
        helpMenu.add(copyrightItem);
        // 为“帮助”菜单的菜单项(ruleItem, keyShortcutItem, copyrightItem)添加时间监听器
        ruleItem.addActionListener(event -> {
            JOptionPane.showMessageDialog(null,
                    "下面介绍游戏规则:\n" +
                            "（1）对局双方各执一色棋子。\n" +
                            "（2）空棋盘开局。\n" +
                            "（3）黑先、白后，交替下子，每次只能下一子。\n" +
                            "（4）棋子下在棋盘的空白点上，棋子下定后，不得向\n" +
                            "        其它点移动，不得从棋盘上拿掉或拿起另落别处。\n" +
                            "（5）黑方的第一枚棋子可下在棋盘任意交叉点上。\n" +
                            "（6）轮流下子是双方的权利。");
        });
        keyShortcutItem.addActionListener(event -> {
            JOptionPane.showMessageDialog(null,
                    "快捷键说明\n" +
                            "    游戏：Alt + G\n" +
                            "        重新开始：Alt + C\n" +
                            "        悔棋：Alt + H\n" +
                            "        退出：Alt + T\n" +
                            "    帮助：Alt + H\n" +
                            "        规则：Alt + R\n" +
                            "        快捷键说明：Alt + K\n" +
                            "        版权说明：Alt + C\n" +
                            "    设置：Alt + S\n" +
                            "        设置获胜所需棋子数：Alt + N\n" +
                            "        设置棋盘大小：Alt + S");
        });
        copyrightItem.addActionListener(event -> {
            JOptionPane.showMessageDialog(null,
                    Copyright.getInstance().getString());
        });

        // 设置“设置”菜单
        jMenuBar.add(settingMenu);
        // 初始化“设置”菜单的菜单项(settingNumberItem, settingSizeItem)
        settingNumberItem = new JMenuItem("设置获胜所需棋子数(N)");
        settingSizeItem = new JMenuItem("设置棋盘大小(S)");
        // 设置菜单项(settingNumberItem, settingSizeItem)的快捷键
        settingNumberItem.setMnemonic('N');
        settingSizeItem.setMnemonic('S');
        // 为“设置”菜单添加菜单项(settingNumberItem, settingSizeItem)
        settingMenu.add(settingNumberItem);
        settingMenu.add(settingSizeItem);
        // 为“帮助”菜单的菜单项(settingNumberItem, settingSizeItem)添加时间监听器
        settingNumberItem.addActionListener(event -> {
            // 自定义获胜所需棋子数量
            JOptionPane.showMessageDialog(null,
                    "请设置您所想要的\n获胜所需棋子的数量\n若想选择默认数量\n请直接按下“确定”或“取消”");
            // 设置默认获胜所需棋子数量，默认值为5
            String number = JOptionPane.showInputDialog("获胜所需棋子数");
            if (number == null || number.equals("")) {
                if (option.equals(option1)) {
                    chessBoardForHumans.setChessNumberRequiredToWin(5);
                } else if (option.equals(option2)) {
                    chessBoardForHumanVersusMachine.setChessNumberRequiredToWin(5);
                }
            } else {
                if (option.equals(option1)) {
                    chessBoardForHumans.setChessNumberRequiredToWin(Integer.parseInt(number));
                } else if (option.equals(option2)) {
                    chessBoardForHumanVersusMachine.setChessNumberRequiredToWin(Integer.parseInt(number));
                }
            }
        });
        settingSizeItem.addActionListener(event -> {
            // 自定义棋盘大小
            JOptionPane.showMessageDialog(null,
                    "请设置您所想要的棋盘大小\n若想选择默认大小\n请直接按下“确定”或者“取消”按钮");
            // 设置棋盘的行数，默认值为15
            String rowString = JOptionPane.showInputDialog("棋盘行数");
            if (rowString == null || rowString.equals("")) {
                if (option.equals(option1)) {
                    chessBoardForHumans.setRow(15);
                } else if (option.equals(option2)) {
                    chessBoardForHumanVersusMachine.setRow(15);
                }
            } else {
                if (option.equals(option1)) {
                    chessBoardForHumans.setRow(Integer.parseInt(rowString));
                } else if (option.equals(option2)) {
                    chessBoardForHumanVersusMachine.setRow(Integer.parseInt(rowString));
                }
            }
            // 设置棋盘的列数，默认值为15
            String columnString = JOptionPane.showInputDialog("棋盘列数");
            if (columnString == null || columnString.equals("")) {
                if (option.equals(option1)) {
                    chessBoardForHumans.setColumn(15);
                } else if (option.equals(option2)) {
                    chessBoardForHumanVersusMachine.setColumn(15);
                }
            } else {
                if (option.equals(option1)) {
                    chessBoardForHumans.setColumn(Integer.parseInt(columnString));
                } else if (option.equals(option2)) {
                    chessBoardForHumanVersusMachine.setColumn(Integer.parseInt(columnString));
                }
            }

            // 自适应界面大小以适应新棋盘大小
            pack();
        });

        // 将JMenuBar设置为菜单项
        setJMenuBar(jMenuBar);

        // 初始化panel
        panel = new JPanel();

        // 制作工具面板
        jToolBar = new JToolBar();
        // 初始化按钮
        restartButton = new JButton("重新开始");
        retractButton = new JButton("悔棋");
        escapeButton = new JButton("退出");
        // 把按钮加入工具面板
        jToolBar.add(restartButton);
        jToolBar.add(retractButton);
        jToolBar.add(escapeButton);
        // 为三个按钮添加事件监听器
        restartButton.addActionListener(event -> {
            System.out.println("重新开始\t\t\t\t\t\t" + sdf.format(new Date()));
            if (chessBoardForHumans != null) {
                chessBoardForHumans.restart();
            }
            if (chessBoardForHumanVersusMachine != null) {
                chessBoardForHumanVersusMachine.restart();
            }
        });
        retractButton.addActionListener(event -> {
            if (chessBoardForHumans != null) {
                chessBoardForHumans.retract();
            }
            if (chessBoardForHumanVersusMachine != null) {
                chessBoardForHumanVersusMachine.retract();
            }
        });
        escapeButton.addActionListener(event -> {
            System.exit(0);
        });
        // 将工具面板添加至panel
        panel.add(jToolBar, BorderLayout.WEST);

        // 将时间添加至time，并显示游戏开始的时间
        time = new JLabel("棋局开始于" + sdf.format(new Date()));
        panel.add(time, BorderLayout.EAST);

        // 将panel添加至主面板
        add(panel, BorderLayout.SOUTH);

    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            // 设置默认关闭方式为“System.exit”
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // 自适应大小
            frame.pack();
            // 设置可见性为“可见”
            frame.setVisible(true);
        });
    }


}
