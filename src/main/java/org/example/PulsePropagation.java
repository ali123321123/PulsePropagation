package org.example;

import java.io.*;
import java.util.*;
import java.util.regex.*;

class Module {
    char prefix;
    String name;
    Map<String, Pulse> inNodes = new HashMap<>();
    List<String> outNodes = new ArrayList<>();
    enum Pulse {LOW, HIGH}

}

public class PulsePropagation {
    public static void main(String[] args) {

        Map<String, Module> actualValues = loadInput("src/main/java/org/example/input.txt");
        //@Todo : create process method for input to solve part1 and part2
    }

    static Map<String, Module> loadInput(String file) {
        Map<String, Module> modules = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            Pattern pattern = Pattern.compile("[%&]|[\\w]+");
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                List<String> strs = new ArrayList<>();
                while (matcher.find()) {
                    strs.add(matcher.group());
                }

                char prefix = strs.get(0).charAt(0);
                int i = 1;
                String name = prefix != 'b' ? strs.get(i++) : "broadcast";

                Module module = modules.computeIfAbsent(name, k -> new Module());
                module.name = name;
                module.prefix = prefix;
                for (; i < strs.size(); ++i) {
                    String out = strs.get(i);
                    module.outNodes.add(out);
                    Module outModule = modules.computeIfAbsent(out, k -> new Module());
                    outModule.inNodes.put(name, Module.Pulse.LOW);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modules;
    }
}