import java.util.ArrayList;
import java.util.List;

/**
 * Created by arpit on 6/11/16.
 * Similar to Amin's Delta but much simpler
 */
public class Delta<T> {
    private Delta<T> other;
    List<T> c;

    public Delta(){
        this.c = new ArrayList<T>();
    }
    public Delta(List<T> c){
        this.c = c;
    }

    public boolean equals(Delta<T> other){
        this.other = other;
        return this.c == other.c;
    }

    public int len(){
        return this.c.size();
    }

    public List<Delta> split(int n){
        List <Delta> result = new ArrayList<Delta>();
        List<T> intermediateStrList = new ArrayList<T>();
        int stepLen = this.len()/n;
        int count = stepLen;
        T s ;
        for (int i = 0; i < c.size(); i++){
            s = c.get(i);
            if (count == 0 ){
                result.add(new Delta(intermediateStrList));
                intermediateStrList = new ArrayList<T>();
                count = stepLen;
            }
            intermediateStrList.add(s);
            count --;

        }
        if (!intermediateStrList.isEmpty())
            result.add(new Delta(intermediateStrList));

        return result;
    }

    public Delta minus(List<Delta> deltaList){
        ArrayList<String> strlist = new ArrayList<String>();
        Delta d ;
        for (int i = 0; i < deltaList.size(); i++){
            d = deltaList.get(i);
            if (!this.equals(d))
                strlist.addAll(d.c);
        }
        return  new Delta(strlist);
    }

}
