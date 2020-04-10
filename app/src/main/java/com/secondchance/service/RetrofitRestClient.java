package com.secondchance.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.secondchance.utils.Configuration;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRestClient {

    public static OkHttpClient trustcert(OkHttpClient client, String TimeOut) {
        return configureClient(client, TimeOut);
    }

    //Data Theorem Fix
    // Retrieve the system's default trust manager
    private static final X509TrustManager defaultTrustManager = getSystemTrustManager();

    private static X509TrustManager getSystemTrustManager() {
        X509TrustManager systemTrustManager = null;
        TrustManagerFactory trustManagerFactory;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm()
            );
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Should never happen");
        }

        try {
            trustManagerFactory.init((KeyStore) null);
        } catch (KeyStoreException e) {
            throw new IllegalStateException("Should never happen");
        }

        for (TrustManager trustManager : trustManagerFactory.getTrustManagers()) {
            if (trustManager instanceof X509TrustManager) {
                systemTrustManager = (X509TrustManager) trustManager;
            }
        }

        if (systemTrustManager == null) {
            throw new IllegalStateException("Should never happen");
        }
        return systemTrustManager;
    }

    @SuppressWarnings("null")
    public static OkHttpClient configureClient(final OkHttpClient client,
                                               String TimeOut) {
        final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain,
                                           final String authType) throws CertificateException {
                //Data Theorem Fix
                // Use the system's trust manager to do baseline SSL validation
                defaultTrustManager.checkServerTrusted(chain, authType);
                // If the default validation succeeded, add additional checks on the certificate here
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain,
                                           final String authType) throws CertificateException {
            }
        }};

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (final java.security.GeneralSecurityException ex) {
            ex.printStackTrace();
        }

        OkHttpClient.Builder builder = client.newBuilder();
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            if (ctx != null) {
                builder.sslSocketFactory(ctx.getSocketFactory(), trustManager);
            }
            int timeOut = Integer.parseInt(TimeOut);
            builder.readTimeout(timeOut, TimeUnit.SECONDS);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    public RetrofitApi urlInfoRetrofit(String baseUrl) {

        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header(Configuration.HEADER_KEY, Configuration.HEADER_VALUE)
                        .build();

                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(trustcert(client, "300"))
                .build();
        return retrofit.create(RetrofitApi.class);
    }
}
