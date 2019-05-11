package com.hacksearch;

import com.hacksearch.domain.Text;
import com.hacksearch.domain.Time;
import com.hacksearch.service.TranslationLineService;
import com.hacksearch.service.dto.TranslationLineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

@Component
public class ParseTranscript {



    @Autowired
    TranslationLineService translationLineService;

    public void parseInputStream(InputStream inputStream,Long captionId){
        ArrayList<TranslationLineDTO> translationLines = new ArrayList<TranslationLineDTO>();
        ArrayList<Time> times = new ArrayList<Time>();
        ArrayList<Text> texts = new ArrayList<Text>();
        ArrayList<String> list = new ArrayList<String>();
        try (LineNumberReader rdr = new LineNumberReader(new InputStreamReader(inputStream))) {
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
//            String numberOnly= captionId;
//            int epsiodeNumber = Integer.valueOf(numberOnly);
            int time = 1;
            int text = 2;
            int id = 0;
            for (String line = null; (line = rdr.readLine()) != null; ) {
                if (rdr.getLineNumber() == time) {
                    Time timeObject = new Time();
                    timeObject.setTime(line);
                    timeObject.setId(id);
//                    timeObject.setEpisode(epsiodeNumber);
                    times.add(timeObject);
//                    System.out.println("Time: " + line);
                    time = time + 3;
                }
                if (rdr.getLineNumber() == text) {
                    Text textObject = new Text();
                    textObject.setText(line);
                    textObject.setId(id);
//                    textObject.setEpisode(epsiodeNumber);
                    texts.add(textObject);
//                    System.out.println("Text: " + line);
                    text = text + 3;
                    id++;
                }


            }
//        String[] { sb1.toString(), sb2.toString() };
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Time time : times) {
            for (Text text : texts) {
                if ((time.getId() == text.getId())) {
                    TranslationLineDTO translationLine = new TranslationLineDTO();
                    translationLine.setSndId(time.getId());
                    translationLine.setEpisode(time.getEpisode());
                    translationLine.setText(text.getText());
                    String[] values = time.getTime().split(",");
                    translationLine.setTimeStart(values[0]);
                    translationLine.setTimeEnd(values[1]);
                    translationLine.setCaptionIdId(captionId);
                    translationLines.add(translationLine);
                    translationLineService.save(translationLine);
                }
            }
        }
    }



//    public List<TranslationLineDTO> getEpisodes() {
//        TranslationLineService translationLineService;
//        File folder = new File("hacks");
//        File[] listOfFiles = folder.listFiles();
//
//        for (int i = 0; i < listOfFiles.length; i++) {
//            if (listOfFiles[i].isFile()) {
//                System.out.println("File " + listOfFiles[i].getName());
//            } else if (listOfFiles[i].isDirectory()) {
//                System.out.println("Directory " + listOfFiles[i].getName());
//            }
//        }
//
//        ArrayList<TranslationLineDTO> translationLines = new ArrayList<TranslationLineDTO>();
//        ArrayList<Time> times = new ArrayList<Time>();
//        ArrayList<Text> texts = new ArrayList<Text>();
//        ArrayList<String> list = new ArrayList<String>();
//        String filename = "caption.sbv";
//
//        for (File file : listOfFiles) {
////            try (LineNumberReader rdr = new LineNumberReader(new FileReader(file.getName()))) {
//            try (LineNumberReader rdr = new LineNumberReader(new FileReader("hacks/"+file.getName()))) {
//                StringBuilder sb1 = new StringBuilder();
//                StringBuilder sb2 = new StringBuilder();
//                String numberOnly= file.getName().replaceAll("[^0-9]", "");
//                int epsiodeNumber = Integer.valueOf(numberOnly);
//                int time = 1;
//                int text = 2;
//                int id = 0;
//                for (String line = null; (line = rdr.readLine()) != null; ) {
////                TranslationLine translationLine = new TranslationLine();
////                System.out.println("linenumber " + rdr.getLineNumber());
////                System.out.println("time variable " + Integer.toString(time));
//                    if (rdr.getLineNumber() == time) {
//                        Time timeObject = new Time();
//                        timeObject.setTime(line);
//                        timeObject.setId(id);
//                        timeObject.setEpisode(epsiodeNumber);
//                        times.add(timeObject);
////                    System.out.println("Time: " + line);
//                        time = time + 3;
//                    }
//                    if (rdr.getLineNumber() == text) {
//                        Text textObject = new Text();
//                        textObject.setText(line);
//                        textObject.setId(id);
//                        textObject.setEpisode(epsiodeNumber);
//                        texts.add(textObject);
////                    System.out.println("Text: " + line);
//                        text = text + 3;
//                        id++;
//                    }
//
//
//                }
////        String[] { sb1.toString(), sb2.toString() };
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        for (Time time : times) {
//            for (Text text : texts) {
//                if ((time.getId() == text.getId()) && (time.getEpisode() == text.getEpisode())) {
//                    TranslationLineDTO translationLine = new TranslationLineDTO();
//                    translationLine.setSndId(time.getId());
//                    translationLine.setEpisode(time.getEpisode());
//                    translationLine.setText(text.getText());
//                    String[] values = time.getTime().split(",");
//                    translationLine.setTimeStart(values[0]);
//                    translationLine.setTimeEnd(values[1]);
//                    translationLines.add(translationLine);
//                    saveTranslation(translationLine);
//
//                }
//            }
//        }
//        System.out.println("tetst");
//
//        return translationLines;
//    }



    public void saveTranslation(TranslationLineDTO translationLineDTO){
        translationLineService.save(translationLineDTO);
    }

}
