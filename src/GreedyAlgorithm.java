import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GreedyAlgorithm {

    static List<Item> globalItems = new ArrayList<>();
    static int people = 0;
    static int globalMin = 1000000000;
    static List<Item> peopleItems = new ArrayList<>();
    static List<Item> result = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        people = Integer.parseInt(sc.nextLine());
        int t = Integer.parseInt(sc.nextLine());
        while (t-- > 0) {
            String[] st = sc.nextLine().split(" ");
            String name = st[0];
            int count = Integer.parseInt(st[1]);
            int price = Integer.parseInt(st[2]);


            for (int i = 1; i <= count; i++) {
                Item item = new Item(name, price);
                globalItems.add(item);
            }
        }
        long st = System.currentTimeMillis();
        for (int i = 0; i < people; i++) {
            Item item = new Item("", 0);
            peopleItems.add(item);
        }
        Collections.sort(globalItems);
        DFS(0);
        result.stream().map(item -> "item = " + item.toString() + "\n").forEach(System.out::println);
        sc.close();
        System.out.println((System.currentTimeMillis() - st) / 1000);
    }

    public static void DFS(int n) {
        if (n == globalItems.size()) {
                int temp = getMax() - getMin();
                if (temp < globalMin) {
                    globalMin = temp;
                    copy(result, peopleItems);
                }
        } else {
            int loop = globalItems.size() - n + 1;
            for (int i = 0; i < (loop > people ? people : globalItems.size() - n + 1); i++) {
//            for (int i = 0; i < people; i++) {
                peopleItems.set(i, new Item(
                        peopleItems.get(i).name + "|" + globalItems.get(n).name,
                        peopleItems.get(i).price + globalItems.get(n).price));
                DFS(n + 1);
                peopleItems.set(i, new Item(
                        peopleItems.get(i).name.substring(0, peopleItems.get(i).name.length() - (globalItems.get(n).name.length() + 1)),
                        peopleItems.get(i).price - globalItems.get(n).price));
            }
        }
    }

    private static void copy(List<Item> result, List<Item> peopleItems) {
        result.clear();
        for (Item item : peopleItems) {
            Item item1 = new Item(item.name, item.price);
            result.add(item1);
        }
    }

    public static int getMax() {
        int max = 0;
        for (int i = 0; i < peopleItems.size(); i++) {
            if (max < peopleItems.get(i).price) {
                max = peopleItems.get(i).price;
            }
        }
        return max;
    }

    public static int getMin() {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < peopleItems.size(); i++) {
            if (min > peopleItems.get(i).price) {
                min = peopleItems.get(i).price;
            }
        }
        return min;
    }

    static class Item implements Comparable<Item> {
        String name;
        int price;

        Item(String name, int price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }

        @Override
        public int compareTo(Item o) {
            return price - o.price;
        }
    }
}
