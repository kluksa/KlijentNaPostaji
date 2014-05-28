/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijentnapostaji;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import klijentnapostaji.citac.CsvFileTicker;
import klijentnapostaji.citac.exceptions.PrihvatWSException;
import klijentnapostaji.webservice.PrihvatServisLocalImpl;
import org.apache.commons.configuration.ConfigurationException;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

/**
 *
 * @author kraljevic
 */
public class KlijentNaPostaji implements WrapperListener {

    static final Logger log = Logger.getLogger(KlijentNaPostaji.class.getName());

    private static void instaliraj() throws FileNotFoundException, IOException {
        try (InputStream is = KlijentNaPostaji.class.getResourceAsStream("xml/config.xml"); 
                OutputStream fos = new BufferedOutputStream(new FileOutputStream(new File("config.xml")))) {
            int read = -1;

            while ((read = is.read()) != -1) {
                fos.write(read);
            }

            fos.flush();
        }
    }

    private final ScheduledExecutorService scheduler;

    public KlijentNaPostaji() {
        scheduler = Executors.newScheduledThreadPool(1);
//        System.setProperty("javax.net.ssl.trustStore", "truststore");
//        System.setProperty("javax.net.ssl.trustStorePassword", "pasvord");
//        System.setProperty("javax.net.ssl.keyStore", "klijent_keystore");
//        System.setProperty("javax.net.ssl.keyStorePassword", "pasvord");
    }

    /**
     * @param args the comma
     * @throws java.io.IOException
     * @throws java.io.IOExceptionnd line arguments
     */
    public static void main(String[] args) throws IOException, Exception {
//        WrapperManager.start(new KlijentNaPostaji(), args);

        if (args.length == 1 && args[0].equals("--install")) {
            instaliraj();
        } if (args.length == 1 && args[0].equals("--test")) {
            new KlijentNaPostaji().test();
        } else {
            new KlijentNaPostaji().start(args);
        }

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
            URL resource = this.getClass().getResource("config.xml");
            Konfiguracija konfig2 = new Konfiguracija("config.xml");

            PrihvatServisLocalImpl servis = new PrihvatServisLocalImpl();
            konfig2.config(servis);
            
            

            Collection<CsvFileTicker> tikeri = konfig2.getFileTickeri();
            for (CsvFileTicker f : tikeri) {
                f.setServis(servis);
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
        } catch (MalformedURLException ex) {
            log.log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    private void test() {
        Properties prop = System.getProperties();
        prop.put("javax.net.ssl.trustStore","truststore");
        prop.put("javax.net.ssl.trustStorePassword", "pasvord@store");
        prop.put("javax.net.ssl.keyStore","keystore.jks");
        prop.put("javax.net.ssl.keyStorePassword", "changeit");
        System.setProperties(prop);
        try {
            Konfiguracija konfig2 = new Konfiguracija("config.xml");

            PrihvatServisLocalImpl servis = new PrihvatServisLocalImpl();
            konfig2.config(servis);
            String str = "Orao javi se, orao javi se, prijem.";
            String out  = servis.test(str);
            log.log(Level.INFO,str);
            log.log(Level.INFO,out);
//            String odgovor = servis.t
                    
                    
//            Date vrijemeZadnjeg = servis.getVrijemeZadnjeg(null, null, null);
//            System.out.println(vrijemeZadnjeg);
        } catch (ConfigurationException ex) {
            Logger.getLogger(KlijentNaPostaji.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(KlijentNaPostaji.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PrihvatWSException ex) {
            Logger.getLogger(KlijentNaPostaji.class.getName()).log(Level.SEVERE, null, ex);
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
