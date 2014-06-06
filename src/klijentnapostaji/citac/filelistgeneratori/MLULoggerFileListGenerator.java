/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentnapostaji.citac.filelistgeneratori;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kraljevic
 */
public class MLULoggerFileListGenerator implements FileListGenerator {

    private static final Logger log = Logger.getLogger(MLULoggerFileListGenerator.class.getName());
    private String path;
    private String[] elementi;
    private Date pocetak;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    private TimeZone timeZone = TimeZone.getTimeZone("UTC");
    
    @Override
    public File[] getFileList(Date pocetak) {
        this.pocetak = pocetak;
        
        
        List<String> fileList = fileList(path);

        return new File[]{new File("/home/kraljevic/tmp/20140430_153301_AH2G_00219_AH2G_00219_39252.csv")};
    }

    @Override
    public void setElementiImena(String[] elementi) {
        this.elementi = elementi;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }
    

    public List<String> fileList(String directory) {
        sdf.setTimeZone(timeZone);
        String pS = "(" + elementi[0]  + "_" + elementi[1] + ")" + "_" + elementi[2] + "_" + elementi[3] + "_" +  elementi[4] + "." +elementi[5];
        Pattern pattern = Pattern.compile(pS);
        
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("regex:" +  pS);
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory),"*." + elementi[5])) {
            for (Path path : directoryStream) {
                Path fileName = path.getFileName();
            
                if ( matcher.matches(fileName)){
                    String fn = fileName.toString();
                    Matcher m = pattern.matcher(fn);
                    if ( m.matches()) {
                        String vrijemeStr = m.group(1);
                        Date vrijeme = sdf.parse(vrijemeStr);
                        if ( vrijeme.after(pocetak)) {
                            fileNames.add(path.toString());
                        }

                    }
                }
            }
        } catch (IOException ex) {} catch (ParseException ex) {
            Logger.getLogger(MLULoggerFileListGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileNames;
    }
}
