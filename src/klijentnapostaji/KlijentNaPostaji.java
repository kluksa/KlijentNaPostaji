/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentnapostaji;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import klijentnapostaji.citac.CsvFileTicker;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

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
        log.info("Pokretanje programa");
        try {
            Konfiguracija konfig = new Konfiguracija("/home/kraljevic/config.xml");
            Collection<CsvFileTicker> tikeri = konfig.getFileTickeri();
            
            for (CsvFileTicker f : tikeri){
                
                f.run();
            }
        } catch (ConfigurationException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            log.log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            log.log(Level.SEVERE, null, ex);
        }

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
