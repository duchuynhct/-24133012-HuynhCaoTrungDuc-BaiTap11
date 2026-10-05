package trungduc.vn.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EmailUtil_24133012Test {

    @Test
    void generateOtpReturnsSixDigitValue() {
        String otp = EmailUtil_24133012.generateOtp();

        assertTrue(otp.matches("[1-9]\\d{5}"));
    }

    @Test
    void generateOtpStaysWithinSixDigitRange() {
        for (int i = 0; i < 100; i++) {
            int otp = Integer.parseInt(EmailUtil_24133012.generateOtp());

            assertTrue(otp >= 100000 && otp <= 999999);
        }
    }
}
