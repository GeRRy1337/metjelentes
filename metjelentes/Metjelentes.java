package metjelentes;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.Scanner;

public class Metjelentes {

    private static ArrayList<Data> list=new ArrayList<>();
    
    public static void main(String[] args) {
        elsoFeladat();
    }
    
    private static void elsoFeladat(){
        dbConnect sql=new dbConnect();
        ResultSet result = sql.getResult("Select * from tavirathu13");
        try{
            while(result.next()){
               list.add(new Data(result.getString("telepules"),result.getString("ido"),result.getString("szeliranyEsErosseg"),result.getInt("homerseklet")));
            }
            result.close();
            sql.close();
            masodikFeladat();
        }catch(Exception e){
            System.err.println(e.getMessage() + "");
        }
    }
    
    private static void masodikFeladat(){
        System.out.println("2. feladat");
        System.out.print("Adja meg egy település kódját! Település:");
        Scanner sc=new Scanner(System.in);
        String tKod=sc.nextLine().toUpperCase();
        while(tKod.length()!=2){
            System.out.println("Hibás kód!");
            System.out.print("Adja meg egy település kódját! Település:");
            tKod=sc.nextLine().toUpperCase();        
        }
        int found=-1,INDEX=0;
        for(Data d:list){
            if(tKod.equals(d.getTelepules())){
                found=INDEX;
            }
            INDEX++;
        }
        if (found==-1)
            System.out.println("A település nem található az adatbázisban.");
        else
            System.out.printf("Az utolsó mérési adat a megadott településről %s-kor érkezett.\n",(list.get(found).getIdo()));
        harmadikFeladat();
    }
    
    private static void harmadikFeladat(){
        System.out.println("3. feladat");
        int min=100,max=-100,MIN=0,MAX=0,INDEX=0;
        for(Data d:list){
            if(d.getHomerseklet()>max){
                max=d.getHomerseklet();
                MAX=INDEX;
            }
            if(d.getHomerseklet()<min){
                min=d.getHomerseklet();
                MIN=INDEX;
            }
            INDEX++;
        }
        Data minD=list.get(MIN),maxD=list.get(MAX);
        System.out.println(String.format("A legalacsonyabb hőmérséklet: %s %s %d fok.",minD.getTelepules(),minD.getIdo(),min));
        System.out.println(String.format("A legmagasabb hőmérséklet: %s %s %d fok.",maxD.getTelepules(),maxD.getIdo(),max));
        negyedikFeladat();
    }
    
    private static void negyedikFeladat(){
        System.out.println("4. feladat");
    }
}
