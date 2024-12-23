package tv.flixbox.handler;

import java.security.MessageDigest;

public class PoW {

    public static int solveChallenge(String challenge, int difficulty){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            int nonce = 0;

            byte[] challengeBytes = challenge.getBytes();

            byte[] target = new byte[difficulty];
            for(int i = 0; i < difficulty; i++){
                target[i] = 0x00;
            }

            while(true){
                byte[] nonceBytes = Integer.toString(nonce).getBytes();

                byte[] input = new byte[challengeBytes.length+nonceBytes.length];
                System.arraycopy(challengeBytes, 0, input, 0, challengeBytes.length);
                System.arraycopy(nonceBytes, 0, input, challengeBytes.length, nonceBytes.length);

                byte[] hash = digest.digest(input);

                boolean matches = true;
                int fullBytesToCheck = difficulty/2;
                int remainingBitsToCheck = difficulty%2*4;

                for(int i = 0; i < fullBytesToCheck; i++){
                    if(hash[i] != 0){
                        matches = false;
                    }
                }

                if(remainingBitsToCheck > 0){
                    byte lastByte = hash[fullBytesToCheck];
                    byte mask = (byte) (0xFF << (8 - remainingBitsToCheck));
                    if((lastByte & mask) != 0){
                        matches = false;
                    }
                }

                if(matches){
                    return nonce;
                }

                nonce++;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return -1;
    }
}
