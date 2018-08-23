package sample;

import com.iflytek.cloud.speech.*;
import javafx.geometry.Pos;
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
    private TextField textFieldValue;
    private Label statusLabel;

    public SpeechRecognizer mIat;
    public RecognizerListener mRecognizerListener;

    public MainView(){
        this.mScene=new Scene(new Group());
        Button startBtn=new Button();
        Button stopBtn=new Button();
        Button exitBtn=new Button();
        Button cancelBtn=new Button();
        Label titleLabel=new Label();
        this.statusLabel = new Label();
        this.textFieldValue=new TextField();
        VBox vb=new VBox();
        HBox hb1=new HBox();
        HBox hb2=new HBox();
        HBox hb3=new HBox();
        HBox hb4=new HBox();
        titleLabel.setText("This is a program for speech-to-text");

        startBtn.setText("Start");
        startBtn.setOnAction((ActionEvent e)->{
            mIat.startListening(mRecognizerListener);
            startBtn.setDisable(true);
            stopBtn.setDisable(false);
            cancelBtn.setDisable(false);
            statusLabel.setText("Start listening");
        });

        stopBtn.setText("Stop");
        stopBtn.setDisable(true);
        stopBtn.setOnAction((ActionEvent e)->{
            mIat.stopListening();
            startBtn.setDisable(false);
            stopBtn.setDisable(true);
            cancelBtn.setDisable(true);
            statusLabel.setText("Stop listening");
        });

        exitBtn.setText("Exit");
        exitBtn.setOnAction((ActionEvent e)->{
            Platform.exit();
        });

        cancelBtn.setText("Cancel");
        cancelBtn.setDisable(true);
        cancelBtn.setOnAction((ActionEvent e)->{
            mIat.cancel();
            startBtn.setDisable(false);
            stopBtn.setDisable(true);
            cancelBtn.setDisable(true);
            statusLabel.setText("Cancel listening");
        });

        textFieldValue.setText("Speech to Text Area!");
        textFieldValue.setPrefSize(380,100);
        textFieldValue.setAlignment(Pos.TOP_LEFT);

        statusLabel.setText("Status area");

        hb1.getChildren().add(titleLabel);
        hb2.getChildren().add(startBtn);
        hb2.getChildren().add(stopBtn);
        hb2.getChildren().add(cancelBtn);
        hb2.getChildren().add(exitBtn);
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
            }
            public void onVolumeChanged(int volume){
                if(volume<0)    volume=0;
                else if(volume>15) volume=15;
            }
            public void onEndOfSpeech(){
                System.out.println("End of Listener");
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
        primaryStage.setWidth(400);
        primaryStage.setHeight(300);
        primaryStage.setResizable(false);
        primaryStage.setScene(this.mScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}