
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.Instrument;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.*;
import java.util.Scanner;
import java.lang.*;
//Created by Matthew Flanders
//Text-Music program

public class AudioText {
    public static int bpm = 0;
    public static boolean quarter = false;

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
            channels[channel].noteOn( bass, vol );
            Thread.sleep( dur );
            channels[channel].noteOff( note );
            channels[channel].noteOff( bass );

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
        String s2;
        Scanner in = new Scanner(System.in);
        System.out.println("Do you want all quarter notes?");
        s2 = in.nextLine();
        Scanner in2 = new Scanner(System.in);
        System.out.println("Enter some text:");
        s = in.nextLine();
        if(s2 == "yes"){
            quarter = true;           
        } else{
            quarter = false;
        }
        
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
            if (note == 0) {
            finalNote = 60;
        } else if (note == 1) {
            finalNote = 62;
        } else if (note == 2) {
            finalNote = 64;
        } else if (note == 3) {
            finalNote = 65;
        } else if (note == 4) {
            finalNote = 67;
        } else if (note == 5) {
            finalNote = 69;
        } else if (note == 6) {
            finalNote = 71;
        } else {
            finalNote = 72;
            
        }
        bass = (num%3);
            if(bass == 0){
                finalBass = 48;
        } else if (bass == 1){
            finalBass = 53;
        } else{
            finalBass = 55;
        }
        
         dur = (num%5);
            if(quarter == true){
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
