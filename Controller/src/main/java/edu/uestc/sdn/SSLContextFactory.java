package edu.uestc.sdn;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;


import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class SSLContextFactory {
    public static SSLContext sslContext = null;

    public static SSLContext getSSLContext(String KSPath, String KSPass,String TrustKSPath, String TrustKSPass){
        if(sslContext == null) {

            try {
                sslContext = SSLContext.getInstance("SSL");
            } catch (NoSuchAlgorithmException e) {
                // TODO AYouTu-generated catch block
                e.printStackTrace();
            }

            try {
                sslContext.init(get_KeyManagers(KSPath,KSPass), get_TrustManagers(TrustKSPath,TrustKSPass), null);
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

            sslContext.createSSLEngine().getSupportedCipherSuites();

        }  //if(serve_sslContext==null

        return sslContext;

    }


    public static TrustManager[] get_TrustManagers(String ca_Path,String ca_pass) {

        TrustManager[] trustManagers = null;
        TrustManagerFactory factory = null;
        KeyStore keyStore = null;

        try {
            keyStore = get_keystore(ca_Path, ca_pass);
        } catch (Exception e) {
            throw e;
        }

        if(keyStore==null) {return null;}

        try {
            factory = TrustManagerFactory.getInstance("SunX509");
        } catch (NoSuchAlgorithmException e) {
            // TODO AYouTu-generated catch block
            e.printStackTrace();
        }
        try {
            factory.init(keyStore);
        } catch (KeyStoreException e) {
            // TODO AYouTu-generated catch block
            e.printStackTrace();
        }
        trustManagers = factory.getTrustManagers();

        return trustManagers;

    }

    public static KeyManager[] get_KeyManagers(String store_path,String store_pass) {

        KeyManager[] keyManagers = null;
        KeyManagerFactory factory = null;
        KeyStore keyStore = null;

        try {
            keyStore = get_keystore(store_path, store_pass);
        } catch (Exception e) {
            throw e;
        }

        if(keyStore==null)
        {
            return null;
        }

        try {
            factory = KeyManagerFactory.getInstance("SunX509");
        } catch (NoSuchAlgorithmException e) {
            // TODO AYouTu-generated catch block
            e.printStackTrace();
        }
        try {
            factory.init(keyStore, store_pass.toCharArray());
        } catch (UnrecoverableKeyException e) {
            // TODO AYouTu-generated catch block
            e.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO AYouTu-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO AYouTu-generated catch block
            e.printStackTrace();
        }

        keyManagers = factory.getKeyManagers();

        return keyManagers;
    }

    public static KeyStore get_keystore(String store_Path,String store_pass ) {

        InputStream inputStream = SSLContextFactory.class.getResourceAsStream(store_Path);//从main/resource文件夹下读取文件

        if(inputStream==null) {try {
            throw new IOException("找不到数字证书！");
        } catch (IOException e) {
            // TODO AYouTu-generated catch block
            e.printStackTrace();
        }

            return null;
        }

        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        } catch (KeyStoreException e) {
            // TODO AYouTu-generated catch block
            e.printStackTrace();
        }

        try {
            keyStore.load(inputStream, store_pass.toCharArray());
        } catch (NoSuchAlgorithmException e) {
            // TODO AYouTu-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO AYouTu-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO AYouTu-generated catch block
            e.printStackTrace();
        }

        if(inputStream!=null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                // TODO AYouTu-generated catch block
                e.printStackTrace();
            }
        }

        return keyStore;

    }


}
