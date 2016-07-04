/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.csv.*;
import puresoccerfx.model.PlayerStatistic;

/**
 *
 * @author s145633
 */
public class LoadSettings {
    private CSVParser parser = null;
    private File file;
    // private ArrayList<Team> teams;
    
    public LoadSettings(File path){
        this.file = path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public LoadSettings() {
        
    }
    
    public ObservableList<String> readList(){
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            System.out.println("inside " +file.canRead());
            parser = CSVParser.parse(file, Charset.forName("UTF-8"), CSVFormat.DEFAULT);
//            parser = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.DEFAULT);
            for (CSVRecord r : parser) {
                list.add(r.get(0));
            }
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            String text = null;
//            while((text = reader.readLine())!= null){
//                list.add(text);
//            }
            
        } catch (Exception ex) {
            Logger.getLogger(LoadSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        FXCollections.sort(list);
        //list.add("none");
        return list;
    }
    
    public HashMap<String, PlayerStatistic> readStatisticList(){
        HashMap<String, PlayerStatistic> hm = new HashMap<>();
        
        try {
            System.out.println("inside " +file.canRead());
            parser = CSVParser.parse(file, Charset.forName("UTF-8"), CSVFormat.DEFAULT);

            for (CSVRecord r : parser) {
                PlayerStatistic ps = new PlayerStatistic();
                ps.setName(r.get(config.DataSetConfig.S_NAME));
                config.GlobalVariable.PLAYERATTRIBUTE.add(r.get(config.DataSetConfig.S_NAME));
                ps.setCategory(r.get(config.DataSetConfig.S_CATEGORY));
                String tmp[] = r.get(config.DataSetConfig.S_ATTRIBUTE).split("[|]");
                System.out.println(tmp[0]);
                for(int i = 0;i<tmp.length;i++){
                    ps.getAttribute().add(tmp[i]);
                }
                tmp = r.get(config.DataSetConfig.S_DEFINITION).split("[|]");
                for(int i = 0;i<tmp.length;i++){
                    ps.getDefinition().add(tmp[i]);
                }
                if(r.get(config.DataSetConfig.S_CUSTOMIZED).equals("false"))
                    ps.setCustomized(false);
                else
                    ps.setCustomized(true);
                hm.put(ps.getName(), ps);
            }
               FXCollections.sort(config.GlobalVariable.PLAYERATTRIBUTE);
               //config.GlobalVariable.PLAYERATTRIBUTE.add("New Statistic");
        } catch (Exception ex) {
            Logger.getLogger(LoadSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hm;
    }
}
