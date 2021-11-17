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
        boolean found=false;
        for(Data d:list){
            if(d.getSze().equals("00000")){
                found=true;
                System.out.println(d.getTelepules()+" "+d.getIdo());
            }
        }
        
        if(!found){
            System.out.println("Nem volt szélcsend a mérések idején.");
        }
        
        otodikFeladat();
        
    }
    
    private static void otodikFeladat(){
        System.out.println("5. feladat");
        ArrayList<String> telepulesek=new ArrayList<>();
        for(Data d:list){
            if(telepulesek.indexOf(d.getTelepules())==-1){
                telepulesek.add(d.getTelepules());
            }
        }
        boolean osszesOra[]={false,false,false,false};
        int hDb=0,hSum=0,max=-100,min=100;
        for(String t:telepulesek){
            for(Data d:list){
                if(d.getTelepules().equals(t)){
                    if(d.getHomerseklet()>max){
                        max=d.getHomerseklet();
                    }
                    if(d.getHomerseklet()<min){
                        min=d.getHomerseklet();
                    }
                    String ora=(d.getIdo().split(":")[0]);
                    if(ora.equals("01")||ora.equals("07")||ora.equals("13")||ora.equals("19")){
                        hDb++;
                        hSum+=d.getHomerseklet();
                        switch(ora){
                            case "01":osszesOra[0]=true;break;
                            case "07":osszesOra[1]=true;break;
                            case "13":osszesOra[2]=true;break;
                            case "19":osszesOra[3]=true;break;
                        }
                    }
                }
            }
            if(osszesOra[0]&&osszesOra[1]&&osszesOra[2]&&osszesOra[3])
                System.out.println(String.format("%s Középhőmérséklete: %d; Hőmérséklet-ingadozás: %d",t,Math.round((double)hSum/(double)hDb),max-min));
            else
                System.out.println(String.format("%s NA; Hőmérséklet-ingadozás: %d",t,Math.round((double)hSum/(double)hDb),max-min));
            max=-100;
            min=100;
            hDb=0;
            hSum=0;
            osszesOra[0]=false;osszesOra[1]=false;osszesOra[2]=false;osszesOra[3]=false;
        }
    }
}
