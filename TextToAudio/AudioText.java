import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;
import javax.sound.midi.*;
import java.io.*;
import java.util.Random;

 
public class AudioText extends JFrame {
    static final String modeList[] = {"Major", "Minor"};
    static final String keyList[] = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    static final String voiceList[] ={"Treble", "Treble and Bass"};
    String finMode = "Major";
    String finVoice = "Treble"; 
    String key = "C";
    int bpm;
    int keyNote = 60;
    int noteRand = 0;
    int bassRand = 0;
    int durRand = 0;
    
    final static int maxGap = 20;
    JComboBox modeComboBox;
    JComboBox keyComboBox;
    JComboBox voiceComboBox;
    JTextField bpmInput;
    JTextField userIn;
    JButton convertButton = new JButton("Convert");
    JButton randButton = new JButton("Randomize Layout");
    JButton resetButton = new JButton("Reset");
    GridLayout experimentLayout = new GridLayout(1,0);
     
    public AudioText(String name) {
        super(name);
        setResizable(false);
    }
     
    public void initComboBox() {
        modeComboBox = new JComboBox(modeList);
        keyComboBox = new JComboBox(keyList);
        voiceComboBox = new JComboBox(voiceList);
    }
     
    public void addComponentsToPane(final Container pane) {
        initComboBox();
        final JPanel text_pane = new JPanel();
        text_pane.setLayout(experimentLayout);
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(0,2));
         
        //Set up components preferred size
        text_pane.setPreferredSize(new Dimension(300,280));
         
        //Add textField
        userIn = new JTextField("", 0);
        getContentPane().add(userIn);
        userIn.setSize(400,220);
        userIn.setVisible(true);
        
        bpmInput = new JTextField("120", 0);
        bpmInput.setSize(20,20);
        bpmInput.setVisible(true);
        
         
        //Add controls to set up horizontal and vertical gaps
        controls.add(new Label("Mode"));
        controls.add(new Label("BPM"));
        controls.add(modeComboBox);
        controls.add(bpmInput);
        controls.add(new Label("Key"));
        controls.add(new Label("Voices"));
        
        controls.add(keyComboBox);
        controls.add(voiceComboBox);
        controls.add(convertButton);
        controls.add(resetButton);
        controls.add(randButton);
         
        //Process the conversion 
        convertButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Get the final mode
                finMode = (String)modeComboBox.getSelectedItem();
                switch(finMode){
                    case "Major": finMode = "Major";
                    break;
                    case "Minor": finMode = "Minor";
                }
                
                //Get the final voice
                finVoice = (String)voiceComboBox.getSelectedItem();
                switch(finVoice){
                    case "Treble": finVoice = "Treble";
                    break;
                    case "Treble and Bass": finVoice = "Treble and Bass";
                }
                
                //Get the final key
                key = (String)keyComboBox.getSelectedItem();
                switch(key){
                    case "C": keyNote = 60;
                    break;
                    case "C#": keyNote = 61;
                    break;
                    case "D": keyNote = 62;
                    break;
                    case "D#": keyNote = 63;
                    break;
                    case "E": keyNote = 64;
                    break;
                    case "F": keyNote = 65;
                    break;
                    case "F#": keyNote = 66;
                    break;
                    case "G": keyNote = 67;
                    break;
                    case "G#": keyNote = 68;
                    break;
                    case "A": keyNote = 69;
                    break;
                    case "A#": keyNote = 70;
                    break;
                    case "B": keyNote = 71;
                    break;
                }
                
                //get the bpm by
                int i = Integer.parseInt(bpmInput.getText());
                bpm = 60000/i;
                String s = userIn.getText();
                noteCalc(s);
            }
        });
        
        //process the randomizer
        randButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Random rand = new Random(); 
                int value = rand.nextInt(8); 
                int value2 = rand.nextInt(5);
                int value3 = rand.nextInt(3);
                int modeRand = rand.nextInt(2);
                int keyRand = rand.nextInt(12);
                int bpmRand = rand.nextInt(300) + 60;
                int voiceRand = rand.nextInt(2);
                
                //switch the mode
                switch(modeRand){
                    case 0: modeComboBox.setSelectedItem("Major");
                    break;
                    case 1: modeComboBox.setSelectedItem("Minor");
                    break;
                }
                
                //switch the key
                switch(keyRand){
                    case 0: keyComboBox.setSelectedItem("C");
                    break;
                    case 1: keyComboBox.setSelectedItem("C#");
                    break;
                    case 2: keyComboBox.setSelectedItem("D");
                    break;
                    case 3: keyComboBox.setSelectedItem("D#");
                    break;
                    case 4: keyComboBox.setSelectedItem("E");
                    break;
                    case 5: keyComboBox.setSelectedItem("F");
                    break;
                    case 6: keyComboBox.setSelectedItem("F#");
                    break;
                    case 7: keyComboBox.setSelectedItem("G");
                    break;
                    case 8: keyComboBox.setSelectedItem("G#");
                    break;
                    case 9: keyComboBox.setSelectedItem("A");
                    break;
                    case 10: keyComboBox.setSelectedItem("A#");
                    break;
                    case 11: keyComboBox.setSelectedItem("B");
                    break;
                }
                
                //switch the voicing
                switch(voiceRand){
                    case 0: voiceComboBox.setSelectedItem("Treble");
                    break;
                    case 1: voiceComboBox.setSelectedItem("Treble and Bass");
                    break;
                }
                bpmInput.setText(String.valueOf(bpmRand));
                noteRand = value;
                durRand = value2;
                bassRand = value3;
            }
        });
        
        //process the reset 
        resetButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                noteRand = 0;
                bassRand = 0;
                durRand = 0;
                voiceComboBox.setSelectedItem("Treble");
                modeComboBox.setSelectedItem("Major");
                keyComboBox.setSelectedItem("C");
                bpmInput.setText(String.valueOf(120));
            }
        });
        
        pane.add(text_pane, BorderLayout.CENTER);
        pane.add(new JSeparator(), BorderLayout.NORTH);
        pane.add(controls, BorderLayout.SOUTH);
    }

 
    public void noteCalc(String s){
        String text = s;
        char[]cList = text.toCharArray();
        int num;
        int note;
        int bass;
        int dur;
        int finalDur;
        int finalNote;
        int finalBass;
        
        //create an arry and store values of root note
        int[]noteRandList = new int[8];
        int current = noteRand;
        for(int i = 0; i < noteRandList.length; i++){
            noteRandList[i] = current;
            current++;
            if(current >= 8){
                current = current%8;
            }
        }
        
        //create an arry and store values of root bass
        int[]bassRandList = new int[3];
        int current2 = bassRand;
        for(int i = 0; i < bassRandList.length; i++){
            bassRandList[i] = current2;
            current2++;
            if(current2 >= 3){
                current = current%3;
            }
        }
        
        //create an arry and store values of root duration
        int[]durRandList = new int[5];
        int current3 = durRand;
        for(int i = 0; i < durRandList.length; i++){
            durRandList[i] = current3;
            current2++;
            if(current2 >= 5){
                current = current%5;
            }
        }
        
        for(int i = 0; i < cList.length; i++){
            num = (int)cList[i];
            
            note = (num%8);
            if(finMode.equals("Major")){
            if (note == noteRandList[0]) {
            finalNote = keyNote;
         } else if (note == noteRandList[1]) {
            finalNote = keyNote+2;
         } else if (note == noteRandList[2]) {
            finalNote = keyNote+4;
         } else if (note == noteRandList[3]) {
            finalNote = keyNote+5;
         } else if (note == noteRandList[4]) {
            finalNote = keyNote+7;
         } else if (note == noteRandList[5]) {
            finalNote = keyNote+9;
         } else if (note == noteRandList[6]) {
            finalNote = keyNote+11;
         } else{
            finalNote = keyNote+12;  
         }
        } else{
          if (note == 0) {
            finalNote = keyNote;
        } else if (note == 1) {
            finalNote = keyNote+2;
        } else if (note == 2) {
            finalNote = keyNote+3;
        } else if (note == 3) {
            finalNote = keyNote+5;
        } else if (note == 4) {
            finalNote = keyNote+7;
        } else if (note == 5) {
            finalNote = keyNote+8;
        } else if (note == 6) {
            finalNote = keyNote+10;
        } else{
            finalNote = keyNote+12;  
        }  
        }
        bass = (num%3);
        if(finMode.equals("Major")){
            if(bass == bassRandList[0]){
                finalBass = keyNote-12;
        } else if (bass == bassRandList[1]){
            finalBass = keyNote-7;
        } else{
            finalBass = keyNote-5;
        }
       } else{
         if(bass == 0){
                finalBass = keyNote-12;
        } else if (bass == 1){
            finalBass = keyNote-7;
        } else{
            finalBass = keyNote-5;
        } 
       }
       
        
         dur = (num%5);
          
            if (dur == 0) {
            finalDur = bpm/4;
         } else if (dur == 1) {
            finalDur = bpm/2;
         } else if (dur == 2) {
            finalDur = bpm;
         } else if (dur == 3) {
            finalDur = bpm*2;
         } else {
            finalDur = (bpm*3);
         }
            play(finalNote, 0, finalDur, 100, finalBass);
           }


     }
    

     
    public  void play(int note, int channel, int dur, int vol, int bass){
        try {
            Synthesizer syn = MidiSystem.getSynthesizer();
            syn.open();
            Instrument[] instr = syn.getDefaultSoundbank().getInstruments();
            syn.loadInstrument(instr[1]);
            MidiChannel[] channels = syn.getChannels();
            
            channels[channel].noteOn( note, vol );
            if(finVoice.equals("Treble and Bass")){
            channels[channel].noteOn( bass, vol );
           }
            Thread.sleep( dur );
            channels[channel].noteOff( note );
            if(finVoice.equals("Treble and Bass")){
            channels[channel].noteOff( bass );
           }
          }
           catch (Exception e) {
            e.printStackTrace();
         }
        }

    private static void createAndShowGUI() {
        //Create and set up the window.
        AudioText frame = new AudioText("Note for Note");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        frame.setPreferredSize(new Dimension(400,400));
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
     
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e){}
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}