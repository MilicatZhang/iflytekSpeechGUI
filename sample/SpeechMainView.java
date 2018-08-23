package sample;
import com.iflytek.cloud.speech.*;

public class SpeechMainView{
    public SpeechRecognizer mIat;
    public RecognizerListener mRecognizerListener;
    public SpeechMainView() {
        SpeechUtility.createUtility(SpeechConstant.APPID + "=b5813de");
        this.mIat = SpeechRecognizer.createRecognizer();
        this.mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        this.mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        this.mIat.setParameter(SpeechConstant.ACCENT,"mandarin");

        this.mRecognizerListener=new RecognizerListener(){
            public void onResult(RecognizerResult results,boolean isLast){
                if(results.getResultString().isEmpty()){
                    System.out.println("No results");
                }
                else{
                    System.out.println("Result: "+results.getResultString());
                }
                if(isLast){
                    System.out.println("Start :");
                }
            }
            public void onBeginOfSpeech(){
                System.out.println("Start Listener");
            }
            public void onVolumeChanged(int volume){
                if(volume<0)    volume=0;
                else if(volume>10) volume=10;
            }
            public void onEndOfSpeech(){
                System.out.println("End of Listener");
            }
            public void onError(SpeechError error){
                System.out.println(error.getErrorDescription(true));
            }
            public void onEvent(int eventType,int arg12,int arg2,String msg){

            }
        };
    }
    public static void main(String[] args){
        SpeechMainView mainTest = new SpeechMainView();
        mainTest.mIat.startListening(mainTest.mRecognizerListener);
        System.out.println("This program for test class SpeechMainView");
    }
}