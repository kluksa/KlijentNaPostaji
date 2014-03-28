/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dhz.skz.citaci;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;
import wlcitac.exceptions.FtpKlijentException;

/**
 *
 * @author kraljevic
 */
@Stateful
public class FtpKlijent {

    private static final Logger log = Logger.getLogger(FtpKlijent.class.getName());

    private String host;
    private String user;
    private String passwd;
    private boolean spojen = false;

    private FTPClient ftp;
    private InputStream istream;

    public void setUri(URI uri) {
        host = uri.getHost();
        String userpass = uri.getUserInfo();
        int i = userpass.indexOf(":");
        user = userpass.substring(0, i);
        passwd = userpass.substring(i + 1);
    }

    public FTPFile[] getFileList(FTPFileFilter filter) throws FtpKlijentException, IOException {
        if (!spojen) {
            throw new FtpKlijentException("FTP not connected");
        }
        return ftp.listFiles(null, filter);
    }

    public InputStream getFileStream(FTPFile file) throws FtpKlijentException, IOException {
//        if (!spojen) {
//            throw new FtpKlijentException("FTP not connected");
//        }
        String filename = file.getName();
        istream = ftp.retrieveFileStream(filename);
        return istream;
//        return new BufferedInputStream(new FileInputStream ("/home/kraljevic/plitvicka jezera-20130103.csv"));
    }

    public boolean zavrsi() throws IOException {
        return ftp.completePendingCommand();
    }

    public void zatvoriStream() throws IOException {
        istream.close();
    }
    public void connect() throws FtpKlijentException {
        ftp = new FTPClient();
        try {
            ftp.connect(host);
            log.log(Level.INFO, "FTP Connected to {0} : {1} : {2}.", new Object[]{host, user, passwd});

            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                throw new FtpKlijentException("FTP server refused connection.");
            }
            if (!ftp.login(user, passwd)) {
                throw new FtpKlijentException("FTP error user password.");
            }
            ftp.enterLocalPassiveMode();
//                   ftp.setFileType(FTP.BINARY_FILE_TYPE);
            spojen = true;

        } catch (IOException e) {
            disconnect();
            throw new FtpKlijentException("Could not connect to server.", e);
        } 
    }

    public void disconnect() {
        spojen = false;
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (IOException f) {
            }
        }
    }
}
