package Chess;


public class Copyright {
    private static Copyright instance;

    private String string = "All rights reserved.\n" +
            "It's not allowed to use this program in any form include reproduce \n" +
            "    and modify without the original author's permission\n" +
            "Made by TongZhaoqi and JiangHengyu.";

    private Copyright() {}

    public static Copyright getInstance() {
        if (instance == null) {
            instance = new Copyright();
        }
        return instance;
    }

    public String getString() {
        return string;
    }
}
