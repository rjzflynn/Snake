
package Snake;

public class App {
      
    public static void main(String args[]){
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        Grid g = new Grid();
        g.startGame();
    }
}
