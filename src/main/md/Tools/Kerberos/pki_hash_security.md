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

## How to find if key is private or public-key?
If the key is used to decrypt data or to sign data, it's likely a private key.
If the key is used to encrypt data or to verify digital signatures, it's likely a public key.

## What is RA?
1. The Registration Authority (RA) is an entity that assists the Certificate Authority (CA) in the process of verifying the identity of certificate applicants and managing the enrollment of digital certificates.
2. RAs often handle tasks such as identity verification, authentication, and gathering necessary information from certificate applicants.
3. RAs act as intermediaries between users or devices and the CA, facilitating the certificate issuance process while ensuring that proper identity validation procedures are followed.

## Certificate Signing Request (CSR):

1. A Certificate Signing Request (CSR) is a data file containing information about a certificate applicant, including their public key and identifying information (such as organization name and domain name).
2. When an entity wants to obtain a digital certificate, they generate a CSR and submit it to a CA or RA for processing.
3. The CSR is signed with the applicant's private key to prove control over the corresponding public key, but it does not include the digital certificate itself.
4. The CA or RA uses the information in the CSR to generate a digital certificate, which is then signed by the CA's private key to attest to the authenticity of the information provided in the CSR.   
