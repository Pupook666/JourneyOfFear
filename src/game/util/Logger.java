package game.util;

public class Logger {
    
    public static void log(Object o) {
        log(o.toString());
    }
    
    public static void log(String s) {
        System.out.println(s);
    }
}