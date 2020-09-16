
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static int countCities;
    static List<String[]> distances = new ArrayList<>();
    public static List<String> visitedCities = new ArrayList<>();
    public static List<String[]> visitedNodes = new ArrayList<>();
    public static List<String> results = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        readFromFile(args[0]);
        solve();
        getRes();
    }

    public static void getRes(){
        results.add(visitedNodes.get(0)[0]);
        results.add(visitedNodes.get(0)[1]);
        visitedNodes.remove(0);

        String[] temp;
        for(int i=0; i < visitedNodes.size(); i++){
            for(int j=1; j < (visitedNodes.size() - i); j++){
                if(Integer.parseInt(visitedNodes.get(j-1)[2]) < Integer.parseInt(visitedNodes.get(j)[2])){
                    //swap elements
                    temp = visitedNodes.get(j-1);
                    visitedNodes.set(j-1, visitedNodes.get(j));
                    visitedNodes.set(j, temp);
                }
            }
        }

        for (int i = 0; i < visitedNodes.size(); i++) {
            if(results.get(results.size() - 1).equals(visitedNodes.get(i)[0])){
                results.add(visitedNodes.get(i)[1]);
                visitedNodes.remove(i);
                i = 0;
            }else if(results.get(results.size() - 1).equals(visitedNodes.get(i)[1])) {
                results.add(visitedNodes.get(i)[0]);
                visitedNodes.remove(i);
                i = 0;
            }
        }


        if(results.get(results.size() - 1).equals(visitedNodes.get(0)[0])){
            results.add(visitedNodes.get(0)[0]);
        }else if(results.get(results.size() - 1).equals(visitedNodes.get(0)[1])){
            results.add(visitedNodes.get(0)[1]);
        }
        System.out.println(results + " answer");

        List<Integer> res = new ArrayList<>();

        for (String result : results) {
            res.add(Integer.parseInt(result));
        }

        for(int i = 0; i < countCities; i++){
            if(!res.contains(i)){
                res.add(i);
            }
        }

        for (Integer re : res) {
            System.out.print(re);
        }

        System.out.println();

    }

    public static void solve() {
        for (int i = 0; i < countCities; i++) {
            getDistance(i, distances);
        }
    }

    public static void getDistance(int city, List<String[]> dists) {
        System.out.println(city + " - city");
        List<String[]> temp = new ArrayList<>();
        for (String[] dist : dists) {
            if (city == Integer.parseInt(dist[0]) || city == Integer.parseInt(dist[1])) {
                if (!visitedNodes.contains(dist)) {
                    temp.add(dist);
                }
            }
        }
        for (String[] tmp : temp) {
            for (String s : tmp) {
                System.out.print(s + " ");
            }
            System.out.println();
        }

        countMinDist(temp);
        visitedCities.add(String.valueOf(city));
        for (String[] visitedNode : visitedNodes) {
            for (String s : visitedNode) {
                System.out.print(s + " ");
            }
            System.out.println(" visited nodes");
        }
        System.out.println(visitedCities.toString());
    }

    public static void countMinDist(List<String[]> distances) {

        try{
            for (int j = 0; j < visitedCities.size(); j++){
                if (visitedCities.get(j).equals(distances.get(0)[0]) || visitedCities.get(j).equals(distances.get(0)[1])) {
                    distances.remove(0);
                    j = 0;
                }
            }
        }catch (Exception ignored){}

        int min = Integer.MAX_VALUE;
        String[] tmp = null;
        boolean flag = false;
        for (String[] dist : distances) {
            for (int j = 0; j < dist.length - 1; j++) {
                int tmpMin = min;
                for (String visitedCity : visitedCities) {
                    if (visitedCity.equals(dist[0]) || visitedCity.equals(dist[1])) {
                        flag = true;
                        break;
                    } else {
                        if (tmpMin > Integer.parseInt(dist[2])) {
                            tmpMin = Integer.parseInt(dist[2]);
                        }
                    }
                }
                if (flag) {
                    flag = false;
                    break;
                }
                if (min > tmpMin) {
                    min = tmpMin;
                    tmp = dist;
                }

            }
        }

        if(tmp != null)
            visitedNodes.add(tmp);

        System.out.println(min + " - min");

    }

    public static void readFromFile(String path) throws IOException {

        BufferedReader bfr = new BufferedReader(new FileReader(path));

        countCities = Integer.parseInt(bfr.readLine()) + 1;
        System.out.println(countCities);
        String line = bfr.readLine();
        while (line != null) {
            String[] tmp = line.split(" ");
            System.out.println(Arrays.toString(tmp));
            line = bfr.readLine();
            distances.add(tmp);
        }
        System.out.println("- --- -");
    }
}