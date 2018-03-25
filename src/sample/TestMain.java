package sample;

import java.util.List;

public class TestMain{
    public static void main(String[] args) {
        Porter porter = new Porter();
        List<String> res = porter.stem("Выделение основы слова стеммером Портера. Привет");
        System.out.println(res.toString());

    }
}