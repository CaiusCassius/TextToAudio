import javax.sound.midi.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.*;


public class Reader{
    public static void main(String[] args){
        getInput();
    }
    
    public static void getInput(){
        System.out.println("Enter some text:");
        String text;
        Scanner in = new Scanner(System.in);
        text = in.nextLine();
        
        char[]cList = text.toCharArray();
        int length = cList.length;
        int duration = 0;
        int time = 500;
        
        for(int i = 0;i < length; i+=2){
            int note = (int)cList[i];
            int dur =(int)cList[i+1];
          if(dur == 0){
            duration = time/4;
           } else if(dur == 1){
            duration =time/2;
           } else if(dur == 2){
            duration =time;
           } else if(dur == 3){
            duration =time+(time/2);
           } else if(dur == 4){
            duration =time*2;
           } else if(dur == 5){
            duration = time*3;
           } else if(dur == 6){
            duration =time*4;
           } 
                       if(i+2 > length){
                break;
            }else{
                       play(note, 0, duration, 80);
                    }
        }
       }
    
    
    
    public static void play(int note, int channel, int dur, int vol){
        
        try {
            Synthesizer syn = MidiSystem.getSynthesizer();
            syn.open();
            Instrument[] instr = syn.getDefaultSoundbank().getInstruments();
            syn.loadInstrument(instr[0]);
            MidiChannel[] channels = syn.getChannels();
            
            channels[channel].noteOn( note, vol );
            Thread.sleep( dur );
            channels[channel].noteOff( note );
          }
           catch (Exception e) {
            e.printStackTrace();
         }
        }
}