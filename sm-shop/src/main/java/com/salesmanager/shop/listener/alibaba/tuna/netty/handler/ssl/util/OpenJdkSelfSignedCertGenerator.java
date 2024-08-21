/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.salesmanager.shop.listener.alibaba.tuna.netty.handler.ssl.util;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

import static com.salesmanager.shop.listener.alibaba.tuna.netty.handler.ssl.util.SelfSignedCertificate.*;

/**
 * Generates a self-signed certificate using {@code sun.security.x509} package provided by OpenJDK.
 */
final class OpenJdkSelfSignedCertGenerator {

    static String[] generate(String fqdn, KeyPair keypair, SecureRandom random) throws Exception {
        PrivateKey key = keypair.getPrivate();
        X500Name owner = new X500Name("CN=" + fqdn);

        // Define the certificate's validity period
        Date notBefore = new Date(System.currentTimeMillis() - 86400000L);  // 1 day before
        Date notAfter = new Date(System.currentTimeMillis() + (365 * 86400000L)); // 1 year validity

        // Create the certificate
        X509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(
                owner, new BigInteger(64, random), notBefore, notAfter, owner, keypair.getPublic());

        // Sign the certificate with SHA256 with RSA encryption
        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA").build(key);
        X509Certificate cert = new JcaX509CertificateConverter().getCertificate(builder.build(signer));

        // Verify the certificate
        cert.verify(keypair.getPublic());

        return newSelfSignedCertificate(fqdn, key, cert);
    }

    private OpenJdkSelfSignedCertGenerator() { }
}
