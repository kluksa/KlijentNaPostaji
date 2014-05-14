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
public class IdentityFileListGenerator implements FileListGenerator {
    private static final Logger log = Logger.getLogger(IdentityFileListGenerator.class.getName());
    private String name;

    @Override
    public File[] getFileList(Date pocetak) {
        return new File[]{new File(name)};
    }

    @Override
    public void setBaseName(String name) {
        this.name = name;
    }

}
