/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentnapostaji.citac.filelistgeneratori;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author kraljevic
 */
public class IdentityFileListGenerator implements FileListGenerator {

    private static final Logger log = Logger.getLogger(IdentityFileListGenerator.class.getName());
    private String name;
    private String path;

    @Override
    public File[] getFileList(Date pocetak) {
        return new File[]{new File(name)};
    }

    @Override
    public void setElementiImena(String[] elementi) {
        this.name = elementi[0];
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }
}
