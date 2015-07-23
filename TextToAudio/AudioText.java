
import javax.sound.midi.*;
import java.util.Scanner;
import java.lang.*;
//Created by Matthew Flanders
//Created July 2015
//Text-Music program
//matthewf615@gmail.com

public class AudioText {
    public static int bpm = 0;
    public static int key = 60;
    public static boolean quart = true;
    public static boolean maj = true;
    public static boolean hasBass = true;
    public static void main( String[] args ) {
        controlLoop();
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
        
    public static void controlLoop(){
        findBpm();
        getInput();
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
        if(isQuart.equals("yes")){
            quart = true;
        }else{
            quart = false;
        }
            
        Scanner in4 = new Scanner(System.in);
        System.out.println("Just Treble?");
        isTreb = in4.nextLine();
        if(isTreb.equals("yes")){
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
        int n;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter BPM:");
        n = in.nextInt();
        bpm = 60000/n;
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
