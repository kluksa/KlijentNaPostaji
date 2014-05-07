/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klijentnapostaji.citac;

import java.io.File;
import java.util.Date;

/**
 *
 * @author kraljevic
 */
public interface FileListGenerator {
    public File[] getFileList(String basename, Date pocetak);
}
