/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klijentnapostaji;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.xml.parsers.ParserConfigurationException;
import klijentnapostaji.citac.CsvFileTicker;
import klijentnapostaji.citac.CsvOmotnicaBuilder;
import klijentnapostaji.citac.filelistgeneratori.MLUFileListGenerator;
import klijentnapostaji.webservice.PrihvatServisLocalImpl;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;
import org.xml.sax.SAXException;

/**
 *
 * @author kraljevic
 */
public class KlijentNaPostaji implements WrapperListener {

    static final Logger log = Logger.getLogger(KlijentNaPostaji.class.getName());
    private final ScheduledExecutorService scheduler;

    public KlijentNaPostaji() {
        scheduler = Executors.newScheduledThreadPool(1);
//        System.setProperty("javax.net.ssl.trustStore", "truststore");
//        System.setProperty("javax.net.ssl.trustStorePassword", "pasvord");
//        System.setProperty("javax.net.ssl.keyStore", "klijent_keystore");
//        System.setProperty("javax.net.ssl.keyStorePassword", "pasvord");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Exception {
//        WrapperManager.start(new KlijentNaPostaji(), args);
        new KlijentNaPostaji().start(args);
    }

    @Override
    public int stop(int exitCode) {
        scheduler.shutdown();
        log.info("Zavrsetak programa");
        return exitCode;
    }

    @Override
    public Integer start(String[] strings) {
//        try {
//            FileHandler fh = new FileHandler("klijent.log");
//            fh.setFormatter(new SimpleFormatter());
//            Logger.getLogger("").addHandler(fh);
//            Logger.getLogger("").setLevel(Level.FINE);
//
//        } catch (IOException ex) {
//            log.log(Level.SEVERE, null, ex);
//        } catch (SecurityException ex) {
//            log.log(Level.SEVERE, null, ex);
//        }
        
        CsvFileTicker f = new CsvFileTicker();
        f.setServis(new PrihvatServisLocalImpl());
        f.setStupciSaVremenom(new Integer[]{0});
        f.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        MLUFileListGenerator mfg = new MLUFileListGenerator();
        f.setFileListGen(mfg);
        CsvOmotnicaBuilder ob = new CsvOmotnicaBuilder();
        ob.setDatoteka(mfg.getFileList(null, null)[0].getName());
        ob.setIzvor("Tece tece jedan slap");
        ob.setPostaja("DHMZ");
        f.setCsvOBuilder(ob);
        log.info("Pokretanje programa");
        f.run();
//        final Konfiguracija konfiguracija = new Konfiguracija();
//        try {
//            konfiguracija.validiraj();
//            konfiguracija.procitaj();
//            for (CsvFileTicker f : konfiguracija.getFileLista()) {
//                log.info(f.toString());
//                scheduler.scheduleAtFixedRate(f, 10, 600, TimeUnit.SECONDS);
//            }
//            log.info("Zavrsio pokretanje");
//            return null;
//        } catch (SQLException ex) {
//            log.log(Level.SEVERE, null, ex);
//            return -1;
//        } catch (ParserConfigurationException ex) {
//            log.log(Level.SEVERE, null, ex);
//            return -2;
//        } catch (SAXException ex) {
//            log.log(Level.SEVERE, null, ex);
//            return -3;
//        } catch (IOException ex) {
//            log.log(Level.SEVERE, null, ex);
//            return -4;
//        } catch (Exception ex) {
//            log.log(Level.SEVERE, null, ex);
//            return -666;
//        }
        return 0;
    }

    @Override
    public void controlEvent(int event) {
        if ((event == WrapperManager.WRAPPER_CTRL_LOGOFF_EVENT)
                && (WrapperManager.isLaunchedAsService() || WrapperManager.isIgnoreUserLogoffs())) {

        } else {
            WrapperManager.stop(0);
        }

    }

//	private static void startTray() {
//		if ( SystemTray.isSupported() ) {
//			SystemTray tray = SystemTray.getSystemTray();
//			Image image = Toolkit.getDefaultToolkit().getImage("seahorse-sign-ok.svg");
//
//
//			ActionListener exitListener = new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					System.out.println("Exiting...");
//					System.exit(0);
//				}
//		    };
//
//			PopupMenu popup = new PopupMenu();
//			MenuItem defaultItem = new MenuItem("Exit");
//			defaultItem.addActionListener(exitListener);
//			popup.add(defaultItem);
//			TrayIcon trayIcon = new TrayIcon(image, "Tray demo", popup);
//			trayIcon.setImageAutoSize(true);
//			try {
//				tray.add(trayIcon);
//			} catch (AWTException e){
//				System.err.println("Eto");
//			}
//		}
//	}
}
