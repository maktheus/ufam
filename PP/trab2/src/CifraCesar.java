import java.util.Scanner;

class CifraCesar{ 
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int tam = scan.nextInt();
        String frase = scan.nextLine();
        char x,y,z;
        frase = frase.toLowerCase();
        for(int i=0; i<frase.length(); i++){
            if(frase.charAt(i)>=97 && frase.charAt(i)<=122){
                if((int) (frase.charAt(i)+tam) >122){
                    x = (char) (frase.charAt(i)+tam);
                    y = (char) (x-122);
                    z = (char) (96+y);
                }else
                    z = (char) (frase.charAt(i)+ tam);
                
                System.out.printf("%C", z);
            }else
                if(i!=0)
                    System.out.printf("%C", frase.charAt(i));

        }


        scan.close();
    }
    
}