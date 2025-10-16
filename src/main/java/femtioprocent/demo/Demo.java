package femtioprocent.demo;

import femtioprocent.logging.LogDemo;
import femtioprocent.logging.MyLogger;

public class Demo {
    public static void main(String[] args) {
        MyLogger.INSTANCE.getLogOut().info("Hello - info");
    }
}
