package com.czajczykmarcin.jatt.core.helpers;

import com.czajczykmarcin.jatt.core.KeyCharacters;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.testng.Assert.*;

public class AsciiKeyCharactersTest {

    @Test
    public void zero() {
        run(new AsciiKeyCharacters.Zero(), 1, "Zero");
    }

    @Test
    public void one() {
        run(new AsciiKeyCharacters.One(1), 1, "One");
    }

    @Test
    public void two() {
        KeyCharacters keyCharacters = new AsciiKeyCharacters.Two(1, 2);
        run(keyCharacters, 1, "Two");
        run(keyCharacters, 2, "Two");
    }

    @DataProvider(name = "set")
    public Object[][] setDataProvider() {
        return new Object[][] {
                {3},
                {10},
                {20},
                {30}
        };
    }

    @Test(dataProvider = "set")
    public void set_tree(int size) {
        Set<Integer> set = new TreeSet<>();
        for (int i = 1; i <= size; i++) {
            set.add(i);
        }
        KeyCharacters keyCharacters = new AsciiKeyCharacters.Set(set);
        String name = "TreeSet("+ size + ")";
        for (int i = 1; i <= size; i++) {
            run(keyCharacters, i, name);
        }
    }

    @Test(dataProvider = "set")
    public void set_hash(int size) {
        Set<Integer> set = new HashSet<>();
        for (int i = 1; i <= size; i++) {
            set.add(i);
        }
        KeyCharacters keyCharacters = new AsciiKeyCharacters.Set(set);
        String name = "HashSet("+ size + ")";
        for (int i = 1; i <= size; i++) {
            run(keyCharacters, i, name);
        }
    }

    private void run(KeyCharacters keyCharacters, int number, String name) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000_000; i++) {
            keyCharacters.contains(number);
        }
        System.out.println(name + " [" + number + "] - " + (System.currentTimeMillis()-startTime) + " ms");
    }

}
