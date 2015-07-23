
import javax.sound.midi.*;
import java.util.Scanner;
import java.lang.*;
import java.util.InputMismatchException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//Created by Matthew Flanders
//Created July 2015
//Text-Music program
//matthewf615@gmail.com

public class AudioText implements ActionListener{
    public static int bpm = 0;
    public static int key = 60;
    public static boolean quart = true;
    public static boolean maj = true;
    public static boolean hasBass = true;
    public static JFrame frame;
    public static JPanel content;
    public static JTextArea text_pane;
    public JTextField input_pane;
    public static JLabel label;
    public static String[] mode = {"Major", "Minor"};
    public static String[] keys = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    public static JComboBox modeList = new JComboBox(mode);
    public static JComboBox keyList = new JComboBox(keys);
    
    public AudioText(){
        makeFrame();
    }
    public static void main( String[] args ) {
        controlLoop();
    }
    
    public static void controlLoop(){
        findBpm();
        getInput();
    }
    
    private void makeFrame(){
        frame = new JFrame("Audio to Text");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(820,700));
        content = (JPanel) frame.getContentPane();
        
        modeList.setSelectedIndex(0);
        modeList.addActionListener(this);
        keyList.setSelectedIndex(0);
        keyList.addActionListener(this);
        content.add(modeList);
        content.add(keyList);
       
       
        
        frame.pack();
        frame.setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == modeList){
            JComboBox cb = (JComboBox)e.getSource();
            String mode = (String)cb.getSelectedItem();
            switch(mode){
                case "Major": maj = true;
                break;
                case "Minor": maj = false;
            }
        } else if(e.getSource() == keyList){
            JComboBox cb = (JComboBox)e.getSource();
            String wkey = (String)cb.getSelectedItem();
            switch(wkey){
                case "C": key = 60;
                break;
                case "D": key = 62;
            }
        }
        System.out.println(key);
    }
   
   
    
    public static void play(int note, int channel, int dur, int vol, int bass){
        try {
            Synthesizer syn = MidiSystem.getSynthesizer();
            syn.open();
            Instrument[] instr = syn.getDefaultSoundbank().getInstruments();
            syn.loadInstrument(instr[1]);
            MidiChannel[] channels = syn.getChannels();
            
            channels[channel].noteOn( note, vol );
            if(hasBass){
            channels[channel].noteOn( bass, vol );
            channels[channel].noteOn( bass+7, vol);
           }
            Thread.sleep( dur );
            channels[channel].noteOff( note );
             if(hasBass){
            channels[channel].noteOff( bass );
            channels[channel].noteOff( bass+7, vol);
           }

          }
           catch (Exception e) {
            e.printStackTrace();
         }
        }
    
    public static void getInput(){
        String s;
        String findKey;
        String isMaj;
        String isQuart;
        String isTreb;
        
        Scanner in1 = new Scanner(System.in);
        System.out.println("Enter the key (Capital letter):");
        findKey = in1.nextLine();
        if(findKey.equals("c")){
            key = 60;
        } else if(findKey.equals("C#") || findKey.equals("Db")){
            key = 61;
        } else if(findKey.equals("D")){
            key = 62;
        } else if(findKey.equals("D#") || findKey.equals("Eb")){
            key = 63;
        } else if(findKey.equals("E")){
            key = 64;
        } else if(findKey.equals("F")){
            key = 65;
        } else if(findKey.equals("F#") || findKey.equals("Gb")){
            key = 66;
        } else if(findKey.equals("G")){
            key = 67;
        } else if(findKey.equals("G#") || findKey.equals("Ab")){
            key = 68;
        } else if(findKey.equals("A")){
            key = 69;
        } else if(findKey.equals("A#") || findKey.equals("Bb")){
            key = 70;
        } else if(findKey.equals("B")){
            key = 71;
        }
        
        Scanner in2 = new Scanner(System.in);
        System.out.println("Major of Minor key?");
        isMaj = in2.nextLine();
        if(isMaj.equals("Major") || isMaj.equals("major")){
            maj = true;
        }else{
            maj = false;
        }
        
        Scanner in3 = new Scanner(System.in);
        System.out.println("All quarter notes?");
        isQuart = in3.nextLine();
        if(isQuart.equals("yes") || isQuart.equals("Yes")){
            quart = true;
        }else{
            quart = false;
        }
            
        Scanner in4 = new Scanner(System.in);
        System.out.println("Just Treble?");
        isTreb = in4.nextLine();
        if(isTreb.equals("yes") || isTreb.equals("Yes")){
             hasBass = false;
        }else{
            hasBass = true;
        }        
        
        Scanner in5 = new Scanner(System.in);
        System.out.println("Enter some text:");
        s = in5.nextLine();
        
        noteCalc(s);
    }
    
    public static void findBpm(){
     Scanner sc = new Scanner(System.in);
      try
      {
       System.out.println("BPM:");
       int n=sc.nextInt();
       bpm = 60000/n;
     }
     catch(InputMismatchException exception)
     {
       findBpm();
     }
     return;
        
    }
    
    public static void noteCalc(String s){
        String text = s;
        char[]cList = text.toCharArray();
        int num;
        int note;
        int bass;
        int dur;
        int finalDur;
        int finalNote;
        int finalBass;
        for(int i = 0; i < cList.length; i++){
            num = (int)cList[i];
            
            note = (num%8);
         if(maj){
            if (note == 0) {
            finalNote = key;
        } else if (note == 1) {
            finalNote = key+2;
        } else if (note == 2) {
            finalNote = key+4;
        } else if (note == 3) {
            finalNote = key+5;
        } else if (note == 4) {
            finalNote = key+7;
        } else if (note == 5) {
            finalNote = key+9;
        } else if (note == 6) {
            finalNote = key+11;
        } else {
            finalNote = key+12;  
        }
        } else{
          if (note == 0) {
            finalNote = key;
        } else if (note == 1) {
            finalNote = key+2;
        } else if (note == 2) {
            finalNote = key+3;
        } else if (note == 3) {
            finalNote = key+5;
        } else if (note == 4) {
            finalNote = key+7;
        } else if (note == 5) {
            finalNote = key+8;
        } else if (note == 6) {
            finalNote = key+10;
        } else {
            finalNote = key+12;  
        }  
        }
        bass = (num%3);
        if(maj){
            if(bass == 0){
                finalBass = key-12;
        } else if (bass == 1){
            finalBass = key-7;
        } else{
            finalBass = key-5;
        }
       } else{
         if(bass == 0){
                finalBass = key-12;
        } else if (bass == 1){
            finalBass = key-7;
        } else{
            finalBass = key-5;
        }  
       }
        
         dur = (num%5);
            if(quart == false){
            if (dur == 0) {
            finalDur = bpm/4;
         } else if (dur == 1) {
            finalDur = bpm/2;
         } else if (dur == 2) {
            finalDur = bpm;
         } else if (dur == 3) {
            finalDur = bpm*2;
         } else {
            finalDur = (bpm*2)+bpm;
         }
        } else{
            finalDur = bpm;
        }
            play(finalNote, 0, finalDur, 100, finalBass);
           }
           controlLoop();

     }
}
