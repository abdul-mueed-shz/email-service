package com.abdul.email.domain.email.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class OtpGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String NUMBERS = "0123456789";
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private OtpGenerator() {
        throw new AssertionError("Cannot instantiate utility class.");
    }

    /**
     * Generate a 4-digit numeric OTP (common for SMS)
     *
     * @return 4-digit numeric OTP
     */
    public static String generateSixDigitNumericOTP() {
        return String.format("%06d", secureRandom.nextInt(10000));
    }

    /**
     * Generate numeric OTP with no repeating digits
     *
     * @param length Length of the OTP (max 10)
     * @return Numeric OTP with unique digits
     */
    public static String generateUniqueDigitOTP(int length) {
        if (length > 10) {
            throw new IllegalArgumentException("Length cannot exceed 10 for unique digits");
        }

        StringBuilder digits = new StringBuilder(NUMBERS);
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(digits.length());
            otp.append(digits.charAt(index));
            digits.deleteCharAt(index); // Remove used digit
        }

        return otp.toString();
    }

    /**
     * Generate a numeric OTP of specified length
     *
     * @param length Length of the OTP
     * @return Numeric OTP string
     */
    public static String generateNumericOTP(int length) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(secureRandom.nextInt(10));
        }
        return otp.toString();
    }

    /**
     * Generate a 6-digit numeric OTP (most common)
     *
     * @return 6-digit numeric OTP
     */
    public static String generateSixDigitOTP() {
        return String.format("%06d", secureRandom.nextInt(1000000));
    }

    /**
     * Generate alphanumeric OTP
     *
     * @param length Length of the OTP
     * @return Alphanumeric OTP string
     */
    public static String generateAlphanumericOTP(int length) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(ALPHANUMERIC.charAt(secureRandom.nextInt(ALPHANUMERIC.length())));
        }
        return otp.toString();
    }

    /**
     * Generate OTP with timestamp for uniqueness
     *
     * @param length Length of numeric part
     * @return Timestamped OTP
     */
    public static String generateTimestampedOTP(int length) {
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(7); // Last 6 digits
        String randomPart = generateNumericOTP(length);
        return timestamp + randomPart;
    }

    /**
     * Generate OTP using ThreadLocalRandom (faster for non-cryptographic use)
     *
     * @param length Length of the OTP
     * @return Numeric OTP string
     */
    public static String generateFastOTP(int length) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(ThreadLocalRandom.current().nextInt(10));
        }
        return otp.toString();
    }

    /**
     * Generate OTP with custom character set
     *
     * @param length  Length of the OTP
     * @param charset Custom character set
     * @return OTP string from custom charset
     */
    public static String generateCustomOTP(int length, String charset) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(charset.charAt(secureRandom.nextInt(charset.length())));
        }
        return otp.toString();
    }

    /**
     * Generate a highly unique OTP with prefix
     *
     * @param prefix Prefix for the OTP
     * @param length Length of numeric part
     * @return Prefixed unique OTP
     */
    public static String generatePrefixedOTP(String prefix, int length) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmm"));
        String randomPart = generateNumericOTP(length);
        return prefix + timestamp + randomPart;
    }

    /**
     * Generate UUID-based OTP (extremely unique)
     *
     * @param length Desired length (will be truncated if UUID is longer)
     * @return UUID-based OTP
     */
    public static String generateUUIDBasedOTP(int length) {
        String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, Math.min(length, uuid.length())).toUpperCase();
    }

    /**
     * Generate OTP with checksum for validation
     *
     * @param length Length of the OTP (excluding checksum)
     * @return OTP with checksum
     */
    public static String generateOTPWithChecksum(int length) {
        String otp = generateNumericOTP(length);
        int checksum = calculateChecksum(otp);
        return otp + (checksum % 10);
    }

    /**
     * Calculate simple checksum for OTP validation
     *
     * @param otp The OTP string
     * @return Checksum value
     */
    private static int calculateChecksum(String otp) {
        int sum = 0;
        for (char c : otp.toCharArray()) {
            sum += Character.getNumericValue(c);
        }
        return sum;
    }

    /**
     * Validate OTP with checksum
     *
     * @param otpWithChecksum OTP with checksum
     * @return true if valid, false otherwise
     */
    public static boolean validateOTPWithChecksum(String otpWithChecksum) {
        if (otpWithChecksum.length() < 2) {
            return false;
        }

        String otp = otpWithChecksum.substring(0, otpWithChecksum.length() - 1);
        int providedChecksum = Character.getNumericValue(otpWithChecksum.charAt(otpWithChecksum.length() - 1));
        int calculatedChecksum = calculateChecksum(otp) % 10;

        return providedChecksum == calculatedChecksum;
    }
}
