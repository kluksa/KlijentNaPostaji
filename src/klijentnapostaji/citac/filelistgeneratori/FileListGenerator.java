/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentnapostaji.citac.filelistgeneratori;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kraljevic
 */
public interface FileListGenerator {

    public File[] getFileList(Date pocetak);

    public void setElementiImena(String[] baseName);

    public void setPath(String path);
}
