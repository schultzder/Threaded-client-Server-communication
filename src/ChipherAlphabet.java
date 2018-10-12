/*
 * This class is essentially the protocall for the server. This protocall is a 
 * ceasear chipher for encryping messages with a certain pattern. The message 
 * that is used for encrypting is sent from the ServerThread class. 
 */

/**
 *
 * @author schultzder
 */
public class ChipherAlphabet 
{
    private final int LENGTH = 5;
    private cipher[] pattern;
    private cipher c1; 
    private cipher c2;
    
    /*
     * Constuctor. It initalizes the pattern for the chipher which is 
     * C1 = 9, C2 = 15, and C1, C2, C2, C1, C2. The array repeates for every
     * 5 itterations of the message. 
     */
    
    public ChipherAlphabet(int c1, int c2)
    { 
        this.c1 = new cipher(c1);
        this.c2 = new cipher(c2);
        this.pattern = new cipher[LENGTH];
        
        pattern[0] = this.c1;
        pattern[1] = this.c2;
        pattern[2] = this.c2;
        pattern[3] = this.c1;
        pattern[4] = this.c2;
        
    }
    
    /*
     * The encrypt method takes the messages and breaks it down by each 
     * character. Then it has a check to see The character parameters, and 
     * concatonates the message depending on which sequence the pattern happens
     * to be in. 
     */
     
    public String encrypt(String message)
    {
        String encryption = "";
        int x = 0;
        for(int i = 0; i < message.length(); i ++)
        {
            if((message.charAt(i) >= 'a' && message.charAt(i) <= 'z') || 
                    (message.charAt(i) >= 'A' & message.charAt(i) <= 'Z'))
            {
                encryption += pattern[x].shiftLetter(message.charAt(i));
                x = (x + 1) % (this.pattern.length);
            }
            else
                encryption += message.charAt(i);
        }
        return encryption;
    }
    
    	// public cipher getC1() { return c1; }

	// public cipher getC2() { return c2; }
	
	public class cipher
	{ 
		private final int shiftValue;
		
		public cipher(int shiftValue)
		{
			this.shiftValue = shiftValue;
		}
		public char shiftLetter(char letter)
		{
			char c = (char)(letter + shiftValue);
			if((c > 'Z' && c < 'a') || c > 'z')
				 return(char)(letter - (26 - shiftValue));
			else 
				return(char)(letter + shiftValue);
		}
	}
}
