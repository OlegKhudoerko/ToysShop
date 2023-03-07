package view;

import java.util.Scanner;


public class ConsoleViewImpl implements View {

    Scanner sc = new Scanner(System.in);

    @Override
    public void set(String string) {
        System.out.println(string);

    }

    @Override
    public String get() {
        return sc.nextLine();
    }
}
