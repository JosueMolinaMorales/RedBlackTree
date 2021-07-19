import java.io.*;
import java.util.*;

public class HW03_B1_MolinaMorales_Josue {
    public static void main(String[] args){
        RedBlackTree RBTree = new RedBlackTree();
        
        generateRandomNumbers(5, 10);

        runTests(RBTree);

    }

    public static void runTests(RedBlackTree RBTree){
        File inFile = new File("hw2_input.txt");
        Scanner scan = null;
        String[] numbers = null;
        int testCase = 1;

        if(!inFile.exists()){
            System.out.println("File not found. Program terminating..");
            System.exit(0);
        }

        try{
            scan = new Scanner(inFile);
            while(scan.hasNext()){
                numbers = scan.nextLine().split(" ");
                for(int i = 0; i < numbers.length; i++){
                    RBTree.insert(Integer.parseInt(numbers[i]));
                }
                System.out.println("======Test Case " + testCase + "==========");
                RBTree.printByLevel();
                testCase++;
                RBTree = new RedBlackTree();
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        
    }

    public static void generateRandomNumbers(int numOfCases, int n){
        int num = 0;
        Random rand = new Random();
        ArrayList<Integer> usedNumbers = new ArrayList<Integer>();
        FileWriter numFile = null;
        try{
            numFile = new FileWriter("hw2_input.txt");
            for(int i = 1; i <= numOfCases; i++){
                for(int j = 0; j < n; j++){
                    num = rand.nextInt(10*n);
                    if(usedNumbers.contains(num)){
                        j--; 
                        continue;
                    }
                    usedNumbers.add(num);
                    numFile.write(num + " ");
                }
                numFile.write("\n");
                usedNumbers.clear();
            }
            numFile.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

        
    }
}
