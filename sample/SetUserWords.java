package sample;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class SetUserWords {
    public JSONObject userJsonWords;
    public  SetUserWords() {
        this.userJsonWords = new JSONObject();
        System.out.println("Set JSON struct");
    }

    public static void main(String[] args){
        SetUserWords uw=new SetUserWords();
        try{
            uw.userJsonWords.put("name","ZhangMiao");
            uw.userJsonWords.put("age",22);
            System.out.println(uw.userJsonWords.toString());
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
