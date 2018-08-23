package sample;

import com.iflytek.cloud.speech.*;
import org.json.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


    public class MainView extends Application {

    private Scene mScene;
    public TextField textFieldValue;
    public Label statusLabel;

    public SpeechRecognizer mIat;
    public RecognizerListener mRecognizerListener;

    public MainView(){
        this.mScene=new Scene(new Group());
        Button startBtn=new Button();
        Button stopBtn=new Button();
        Button exitBtn=new Button();
        Label titleLabel=new Label();
        this.statusLabel = new Label();
        this.textFieldValue=new TextField();
        VBox vb=new VBox();
        HBox hb1=new HBox();
        HBox hb2=new HBox();
        HBox hb3=new HBox();
        HBox hb4=new HBox();
        titleLabel.setText("This speech to string");
        startBtn.setText("Start");
        startBtn.setOnAction((ActionEvent e)->{
            mIat.startListening(mRecognizerListener);
            startBtn.setDisable(true);
            stopBtn.setDisable(false);
        });
        stopBtn.setText("Stop");
        stopBtn.setDisable(true);
        stopBtn.setOnAction((ActionEvent e)->{
            mIat.stopListening();
            startBtn.setDisable(false);
            stopBtn.setDisable(true);
        });
        textFieldValue.setText("Speech to Text Area!");
        statusLabel.setText("Status area");
        hb1.getChildren().add(titleLabel);
        hb2.getChildren().add(startBtn);
        hb2.getChildren().add(stopBtn);
        hb3.getChildren().add(textFieldValue);
        hb4.getChildren().add(statusLabel);
        vb.getChildren().add(hb1);
        vb.getChildren().add(hb2);
        vb.getChildren().add(hb3);
        vb.getChildren().add(hb4);
        ((Group)mScene.getRoot()).getChildren().add(vb);


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
                    textFieldValue.setText(JsonParser.parseIatResult(results.getResultString()));
                }
                if(isLast){
                    System.out.println("IS LAST");
                }
            }
            public void onBeginOfSpeech(){
                System.out.println("Start listener");
                //statusLabel.setText("Start listening");
            }
            public void onVolumeChanged(int volume){
                //statusLabel.setText("VOLUME: "+volume);
                if(volume<0)    volume=0;
                else if(volume>15) volume=15;
            }
            public void onEndOfSpeech(){
                System.out.println("End of Listener");
                //statusLabel.setText("End of listening");
            }
            public void onError(SpeechError error){
                System.out.println(error.getErrorDescription(true));
                textFieldValue.setText(error.getErrorDescription(true));
            }
            public void onEvent(int eventType,int arg12,int arg2,String msg){
            }
        };

    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("SpeechMainView");
        primaryStage.setWidth(200);
        primaryStage.setHeight(300);
        primaryStage.setResizable(false);
        primaryStage.setScene(this.mScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}