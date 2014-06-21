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
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    private final TimeZone timeZone = TimeZone.getTimeZone("UTC");

    @Override
    public File[] getFileList(Date pocetak) {
        this.pocetak = pocetak;

        List<File> fileList = fileList(path);
        
        return fileList.toArray(new File[fileList.size()]);
//        File[] lista = new File[fileList.size()];
//        int i = 0;
//        for (String fname : fileList) {
//            lista[i]=new File(fname);
//            log.log(Level.INFO, "FAJL:{0}", fname);
//        }
//        return lista;
    }

    @Override
    public void setElementiImena(String[] elementi) {
        this.elementi = elementi;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    public List<File> fileList(String directory) {
        sdf.setTimeZone(timeZone);
        String pS = "(" + elementi[0] + "_" + elementi[1] + ")";
        pS += "_" + elementi[2] + "_" + elementi[3];
        pS += "_" + elementi[4];
        pS += "." + elementi[5];
        Pattern pattern = Pattern.compile(pS);
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("regex:" + pS);
        List<File> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) { // , pppS)) {
            for (Path filePath : directoryStream) {
                String fn = filePath.getFileName().toString();
                Matcher m = pattern.matcher(fn);
                if (m.matches() && sdf.parse(m.group(1)).after(pocetak)) {
                    fileNames.add(filePath.toFile());
                }
            }
        } catch (ParseException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        return fileNames;
    }
}
