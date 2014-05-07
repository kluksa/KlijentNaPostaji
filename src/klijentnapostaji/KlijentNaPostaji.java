/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klijentnapostaji;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.xml.parsers.ParserConfigurationException;
import klijentnapostaji.citac.FileTicker;
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
        WrapperManager.start(new KlijentNaPostaji(), args);
//		new Main().start(args);
    }

    @Override
    public int stop(int exitCode) {
        scheduler.shutdown();
        log.info("Zavrsetak programa");
        return exitCode;
    }

    @Override
    public Integer start(String[] strings) {
        try {
            FileHandler fh = new FileHandler("klijent.log");
            fh.setFormatter(new SimpleFormatter());
            Logger.getLogger("").addHandler(fh);
            Logger.getLogger("").setLevel(Level.FINE);

        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            log.log(Level.SEVERE, null, ex);
        }

        log.info("Pokretanje programa");
        final Konfiguracija konfiguracija = new Konfiguracija();
        try {
            konfiguracija.validiraj();
            konfiguracija.procitaj();
            for (FileTicker f : konfiguracija.getFileLista()) {
                log.info(f.toString());
                scheduler.scheduleAtFixedRate(f, 10, 600, TimeUnit.SECONDS);
            }
            log.info("Zavrsio pokretanje");
            return null;
        } catch (SQLException ex) {
            log.log(Level.SEVERE, null, ex);
            return -1;
        } catch (ParserConfigurationException ex) {
            log.log(Level.SEVERE, null, ex);
            return -2;
        } catch (SAXException ex) {
            log.log(Level.SEVERE, null, ex);
            return -3;
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
            return -4;
        } catch (Exception ex) {
            log.log(Level.SEVERE, null, ex);
            return -666;
        }
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
