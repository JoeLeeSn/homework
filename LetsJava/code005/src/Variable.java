public class Variable {
    public static void main(String [] args){
        int a = 3;
        int b = 5;
        int c = 7;

        int x = 9;
        int y = a*x + b*x*x + c*x*x*x*x;

        System.out.println(y);
    }
}
