/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klijentnapostaji.citac.filelistgeneratori;


import java.io.File;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author kraljevic
 */
public class MLULoggerFileListGenerator implements FileListGenerator{
    private static final Logger log = Logger.getLogger(MLULoggerFileListGenerator.class.getName());
    private  String baseName;

    
    
    
    @Override
    public File[] getFileList(Date pocetak) {
        return new File[]{new File("/home/kraljevic/tmp/20140430_153301_AH2G_00219_AH2G_00219_39252.csv")};
    }

    @Override
    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

}
