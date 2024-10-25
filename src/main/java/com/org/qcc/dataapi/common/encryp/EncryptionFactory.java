package com.org.qcc.dataapi.common.encryp;

import com.org.qcc.dataapi.common.encryp.impl.AESEncryption;
import com.org.qcc.dataapi.common.encryp.impl.DESEncryption;
import com.org.qcc.dataapi.common.encryp.impl.MD5Encryption;

public class EncryptionFactory {

    public static EncryptionStrategy getEncryptionStrategy(EncryptionAlgorithm algorithm) {
        switch (algorithm) {
            case MD5:
                return new MD5Encryption();
            case AES:
                return new AESEncryption();
            case DES:
                return new DESEncryption();
            default:
                throw new IllegalArgumentException("不支持的加密算法: " + algorithm);
        }
    }
}
