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
public class MLUFileListGenerator implements FileListGenerator{
    private static final Logger log = Logger.getLogger(MLUFileListGenerator.class.getName());

    @Override
    public File[] getFileList(String basename, Date pocetak) {
        return new File[]{new File("/home/kraljevic/tmp/20140430_153301_AH2G_00219_AH2G_00219_39252.csv")};
    }

}
