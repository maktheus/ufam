import java.util.Scanner;

class RaizDois{ 
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        double num = scan.nextDouble(); 
        double d=1;
        for(int i=0; i<num; i++){
            d = 2+1/d;
            System.out.printf("%.14f\n",1+1/d);
        }
        scan.close();
     
    }

}