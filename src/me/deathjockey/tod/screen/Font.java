package me.deathjockey.tod.screen;

public class Font {
	
	public static String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ    " + "0123456789-.!?/%$\\=*+,;:()&#\"' ";

    public static int getStringWidth(String s) {
        return s.length() * 12;
    }

    private Font() {
    }

    public static void draw(Screen screen, String msg, int x, int y) {
        msg = msg.toUpperCase();
        int length = msg.length();
        for (int i = 0; i < length; i++) {
            int c = letters.indexOf(msg.charAt(i));
            if (c < 0) continue;
            screen.render(Art.font[c % 30][c / 30], x, y);
            x += 12;
        }
    }
	
}
