## Why asymetric keys are not sued for bulk data encryption
1. Asymmetric encryption algorithms are computationally intensive compared to symmetric encryption algorithms.
2. Encrypting large amounts of data with asymmetric encryption can be slow and resource-intensive, making it impractical for bulk data encryption.
3. Asymmetric encryption algorithms require longer keys for equivalent security compared to symmetric encryption algorithms.
   1. For instance, RSA keys typically need to be much larger than symmetric keys to achieve a similar level of security.
   2. Handling large keys increases computational overhead and can also impact performance.

## What is hybrid approach (asymetric + symetric)
1. Symmetric Encryption for Data: A symmetric encryption algorithm (such as AES) is used to encrypt the actual data. Symmetric encryption is fast and efficient for encrypting large volumes of data.
2. Asymmetric Encryption for Key Exchange: Asymmetric encryption is used to securely exchange the symmetric key used for encrypting the data.
   1. The sender generates a symmetric key, encrypts the data with it, then encrypts the symmetric key itself with the recipient's public key.
   2. The recipient can then decrypt the symmetric key with their private key and use it to decrypt the data.


