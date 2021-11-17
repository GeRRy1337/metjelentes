package metjelentes;
public class Data {
    private String telepules,ido,sze;
    private int homerseklet;

    public Data(String telepules, String ido, String sze, int homerseklet) {
        this.telepules = telepules;
        if(ido.length()==4)
            this.ido = ido.substring(0,2)+":"+ido.substring(2,ido.length());
        else if(ido.length()==3)
            this.ido = "0"+ido.substring(0,1)+":"+ido.substring(1,ido.length());
        else if(ido.length()==2)
            this.ido = "00:"+ido;
        else if(ido.length()==1)
            this.ido = "00:0"+ido;
        this.sze = sze;
        this.homerseklet = homerseklet;
    }

    public String getTelepules() {
        return telepules;
    }

    public String getIdo() {
        return ido;
    }

    public String getSze() {
        return sze;
    }

    public int getHomerseklet() {
        return homerseklet;
    }
}
