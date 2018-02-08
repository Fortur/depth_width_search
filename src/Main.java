import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Node> nodes = new ArrayList<>();
    ArrayList<Object> OpenOut=new ArrayList();
    ArrayList<Object> ClosedOut=new ArrayList<>();
    int test=0;

    static class Node {
        String data;
        boolean visited;

        Node(String data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data;
        }
    }


    public ArrayList findNeighbours(int matrix[][], Node x) {
        int nodeIndex = -1;

        ArrayList neighbours = new ArrayList();
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).equals(x)) {
                nodeIndex = i;
                break;
            }
        }
        if (nodeIndex != -1) { //добавляет соседей
            for (int j = 0; j < matrix[nodeIndex].length; j++) {
                if (matrix[nodeIndex][j] == 1) { //если 1, то добавить
                    neighbours.add(nodes.get(j));
                }
            }
        }
        return neighbours;
    }

    public void dfs(int matrix[][], Node node, String k) {
        if (test==0){

            outputOpen(matrix, node);
            if (node.data.equals(k)) {
                System.out.println(k+": Поиск окончен");
                test=1;
            }
            ArrayList neighbours = findNeighbours(matrix, node); //передаёт в очередь след.точку
            for (int i = 0; i < neighbours.size(); i++) { //идёт вглубь
                Node n = (Node) neighbours.get(i); //рассматривает все ветви от точки

                if (n != null && !n.visited) {
                    dfs(matrix, n, k);
                    n.visited = true;
                }
            }
        }
    }

    public void dfs1(int matrix[][], Node node, String k) {
        if (test==0){
            outputOpen(matrix, node);
            if (node.data.equals(k)) {
                System.out.println(k+": Поиск окончен");
                test=1;
            }
            ArrayList neighbours = findNeighbours(matrix, node); //передаёт в очередь след.точку
            for (int i = 0; i < neighbours.size(); i++) { //идёт вглубь
                Node n = (Node) neighbours.get(i); //рассматривает все ветви от точки

                if (n != null && !n.visited) {
                    n.visited = true;
                }
            }
        }
    }

    private void outputOpen(int[][] nod, Node k) {
        ArrayList arrayList = findNeighbours(nod, k);//содержит список соседей по точке
        ArrayList result = new ArrayList();
        result.addAll(arrayList);
        for (Object i : arrayList) {//заполняет оставшихся соседей и составляет карту
            ArrayList array = findNeighbours(nod, (Node) i);
            result.addAll(array);
        }
        outputList(arrayList,k);
    }

    private void outputList(ArrayList array,Node k) {
        if (array.size() != 0) {
            System.out.print(k+": Open: ");
            for (Object i : array) {
                OpenOut.add(i);
            }
            OpenOut.remove(k);
            OpenOut.trimToSize();
            for (Object i : OpenOut) {
                System.out.print(i);
            }
            ClosedOut.add(k);
            System.out.print(" Closed: ");
            for (Object i : ClosedOut) {
                System.out.print(i);
            }
            System.out.println();
        }else {
            OpenOut.remove(k);
            OpenOut.trimToSize();
            if (OpenOut.size()!=0){
                System.out.print(k+": Open: ");
                for (Object i : OpenOut) {
                    System.out.print(i);
                }
                ClosedOut.add(k);
                System.out.print(" Closed: ");
                for (Object i : ClosedOut) {
                    System.out.print(i);
                }
                System.out.println();
            }
        }
    }

    public static void main(String arg[]) {
        System.out.print("Введите количество вершин: ");
        Scanner in=new Scanner(System.in);
        int kol=Integer.parseInt(in.nextLine());
        int[][] matrix=new int[kol][kol];
        System.out.print("Введите названия всех вершин без пробелов по порядку(1 символ-1 вершина):");
        String node=in.nextLine();
        System.out.print("Введите искомую вершину:");
        String search=in.nextLine();
        System.out.print("1-в глубину\n2-в ширину\nВыбор режима:");
        int regime= Integer.parseInt(in.nextLine());

        char[] array = node.toCharArray();
        for(int i=0; i < array.length;i++) {
            nodes.add(new Node(""+array[i]));
        }

        System.out.println("Введите матрицу:");
        for (int i = 0; i < kol; i++) {
            System.out.print("Матрица для "+node.charAt(i)+":");
            String s=in.nextLine();
            char[] schar = s.toCharArray();
            for (int j = 0; j < schar.length; j++) {
                matrix[i][j]=Character.getNumericValue(schar[j]);
            }
        }

        clearVisitedFlags();

        if (regime==1){
            Main dfsExample = new Main();
            dfsExample.dfs(matrix, nodes.get(0), search);
        }
        else {
            Main dfs1Example = new Main();
            for (int i = 0; i < kol; i++) {
                dfs1Example.dfs1(matrix, nodes.get(i), search);
            }
        }
    }

    public static void clearVisitedFlags() {
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).visited = false;
        }
    }
}