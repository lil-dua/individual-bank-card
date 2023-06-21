package bankcard;

import javacard.framework.Applet;
import javacard.framework.APDU;
import javacard.security.MessageDigest;
import javacard.framework.ISOException;
import javacard.framework.ISO7816;
import javacard.framework.JCSystem;
import javacard.framework.Util;
import javacard.security.AESKey;
import javacard.security.Signature;
import javacard.security.KeyPair;
import javacard.security.RSAPrivateKey;
import javacard.security.RSAPublicKey;
import javacard.security.KeyBuilder;
import javacardx.apdu.ExtendedLength;
public class bankcard extends Applet implements ExtendedLength
{
	
	private static final byte[]PIN_DEFAULT= new byte[]{(byte)'1',(byte)'2',(byte)'3',(byte)'4',(byte)'5',(byte)'6'};
	private static final byte PIN_RETRY = 3;
	private final byte[] pin;
	private byte retry;
	private byte tryRemaining;
	private final MessageDigest messageDigest;
    private boolean isValidated;
    private final AESKey key;
    private final AesConfig aes;
    
    private static final byte INS_VERIFY=(byte)0x00;
    private static final byte INS_CREATE=(byte)0x01;
    private static final byte INS_GET=(byte)0x02;
    private static final byte INS_UPDATE=(byte)0x03;
    private static final byte PIN=(byte)0x04;
	private static final byte BANK_INFORMATION=(byte)0x05;
	private static final byte SIGNATURE=(byte)0x06;
	private static final byte INFORMATION=(byte)0x07;
	private static final byte BALANCE=(byte)0x08;
	private static final byte AVATAR=(byte)0x09;
	private static final byte INS_RESET_TRY_PIN=(byte)0x10;
    
    private byte[] numberCard;
    private byte[] nameCard;
    private byte[] numberBalance;
    private byte[] createDate;
    private byte[] expirationDate;
    
    
    private short MAX_SIZE = (short)4096;
    private byte[] avatar;
    private byte[] avatarBuffer;
    private short sizeAvatar = 0;
    
    private final Signature signature;
    private final byte[] signatureBuf;
    private RSAPrivateKey privateKey;
	private RSAPublicKey publicKey;
    
    public bankcard(){
    	aes= new AesConfig();
	    pin = new byte[16];
	    retry = PIN_RETRY;
	    tryRemaining = PIN_RETRY;
	    isValidated = false;
	    messageDigest=MessageDigest.getInstance(MessageDigest.ALG_MD5,false);
	    messageDigest.doFinal(PIN_DEFAULT,(short)0,(short)PIN_DEFAULT.length,pin,(short)0);
        key=(AESKey)KeyBuilder.buildKey(KeyBuilder.TYPE_AES, (short) 128, false);
      
        numberCard= new byte[16];
        nameCard = new byte[16];
        numberBalance = new byte[16];
        createDate = new byte[16];
        expirationDate = new byte[16];
        avatar = new byte[MAX_SIZE];
        avatarBuffer = new byte[MAX_SIZE];
        signature = Signature.getInstance(Signature.ALG_RSA_SHA_PKCS1, false);
        
        signatureBuf = JCSystem.makeTransientByteArray((short) (KeyBuilder.LENGTH_RSA_1024 / 8), JCSystem.CLEAR_ON_RESET);
    }
    
    public void process(APDU apdu)
	{
		if (selectingApplet())
		{
			return;
		}

		byte[] buf = apdu.getBuffer();
		switch (buf[ISO7816.OFFSET_INS])
		{
		case INS_VERIFY:
			verify(apdu);
			break;
		case INS_CREATE:
			create(apdu);
			break;
		case INS_GET:
			get(apdu);
			break;
		case INS_UPDATE:
			update(apdu);
		    break;
		default:
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
    
    private void get(APDU apdu) throws ISOException{
		if(numberCard==null){
			return;
		}
		byte[] buf=apdu.getBuffer();
		if(buf[ISO7816.OFFSET_P1]!=BANK_INFORMATION){
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		switch(buf[ISO7816.OFFSET_P2]){
		case INFORMATION:
			getInformation(apdu);
			break;
		case BALANCE:
			getBalance(apdu);
			break;
		case AVATAR:
			getAvatar(apdu);
			break;
		default:
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
	}
	private void getBalance(APDU apdu){
	    byte[]buf=apdu.getBuffer();
	    short length= (byte)getLength(aes.decode(numberBalance,(short)0,(short)numberBalance.length,key, buf,(short)0),(short)0);
	    apdu.setOutgoingAndSend((short)0,length);
    }
	private void update(APDU apdu) throws ISOException{
		if(nameCard[0x00]==0x00){
			ISOException.throwIt(ISO7816.SW_COMMAND_NOT_ALLOWED);
		}
		byte[]buf=apdu.getBuffer();
		switch(buf[ISO7816.OFFSET_P1]){
		case BANK_INFORMATION:
			break;
		case PIN:
			updatePin(apdu);
			return;
		default:
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		byte P2=buf[ISO7816.OFFSET_P2];
		if(P2==AVATAR){
			updateAvatar(apdu);
			return;
		}
		if(buf[ISO7816.OFFSET_LC]==(byte)0x00){
			ISOException.throwIt(ISO7816.SW_DATA_INVALID);
		}
		switch(P2){
		case INFORMATION:
			updateInformation(buf);
			break;
		case BALANCE:
			updateBalance(buf);
			break;
		default:
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
	}
	private void updateInformation(byte[]buf){
	    byte offset;
	    short length;
	    JCSystem.beginTransaction();
	    offset=ISO7816.OFFSET_CDATA;
	    length=(short) buf[offset];
	    nameCard = aes.encode(buf,(short)(offset+1),length,key,nameCard);
	   
	    JCSystem.commitTransaction();
    }
    private void updateBalance(byte[]buf){
	    short offset=ISO7816.OFFSET_CDATA;
	    short length=buf[ISO7816.OFFSET_LC];
	    JCSystem.beginTransaction();
	    numberBalance = aes.encode(buf,(short)offset,length,key,numberBalance);
	    JCSystem.commitTransaction();
    }
	private void updatePin(APDU apdu) throws ISOException{
	    byte[]buf=apdu.getBuffer();
	    byte offset=ISO7816.OFFSET_CDATA;
	    short length=(short) buf[offset];
	    if(match(buf,(byte)(offset+1),length)){
		    offset+=(byte)(length+1);
		    length=(short)buf[offset];
		    update(buf,(byte)(offset+1),length);
		    return;
	    }
	    buf[ISO7816.OFFSET_CDATA]=getTryRemaining();
	    apdu.setOutgoingAndSend(ISO7816.OFFSET_CDATA,(short)1);
	    ISOException.throwIt(ISO7816.SW_WRONG_DATA);
    }
    //kiem tra ma pin nhap vao va ma pin hien tai
    //checkma pin(bam) va ma pin dang co (bam) trong the
    public boolean match(byte[]buf,byte offset,short length){
		if(tryRemaining==(byte)0x00){
			return false;
		
		}
		byte[]temp=JCSystem.makeTransientByteArray((short) pin.length, JCSystem.CLEAR_ON_DESELECT);
		messageDigest.reset();
		messageDigest.doFinal(buf, (short) offset, length, temp, (short) 0);
		if(Util.arrayCompare(pin,(short)0,temp,(short)0,(short)pin.length)==(byte)0x00){
			this.tryRemaining=retry;
			this.isValidated=true;
			return true;
		}
		tryRemaining--;
		return false;
	}
    public void update(byte[]buf,byte offset,short length){
		if(length<1){
			ISOException.throwIt(ISO7816.SW_DATA_INVALID);
		}
		messageDigest.reset();
		messageDigest.doFinal(buf,(short)offset,length,pin,(short)0);
		tryRemaining=retry;
	}
	private short getLength(byte[]output,short outOffset){
		short length;
		for(length=(short)(output.length-1);length>=0;length--){
			if(output[length]!=(byte)0x00){
				break;
			}
		}
		return (short)(length-outOffset+1);
	}
	//giai ma --->lay du lieu ---> gui den app
    private void getInformation(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		byte offset;
		
		offset = (byte) 0x00;
	
		buffer[offset] = (byte)getLength(aes.decode(numberCard,(short)0,(short)numberCard.length,key, buffer,(short)(offset+1)),(short)(offset+1));

		offset += (short) (buffer[offset] + 1);
		buffer[offset] = (byte) getLength(aes.decode(nameCard,(short)0,(short)nameCard.length,key, buffer,(short)(offset+1)),(short)(offset+1));

        offset += (short) (buffer[offset] + 1);
		buffer[offset] = (byte) getLength(aes.decode(createDate,(short)0,(short)createDate.length,key, buffer,(short)(offset+1)),(short)(offset+1));
		
		offset += (short) (buffer[offset] + 1);
		buffer[offset] = (byte) getLength(aes.decode(expirationDate,(short)0,(short)expirationDate.length,key, buffer,(short)(offset+1)),(short)(offset+1));

		offset += (short) (buffer[offset] + 1);
		buffer[offset] = (byte) getLength(aes.decode(numberBalance,(short)0,(short)numberBalance.length,key, buffer,(short)(offset+1)),(short)(offset+1));
		apdu.setOutgoingAndSend((short) 0, (short) (offset + buffer[offset] + 1));
	}
	private void getAvatar(APDU apdu) {
		aes.decode(avatar,(short)0,MAX_SIZE,key,avatarBuffer,(short)0);
		short size = sizeAvatar;
		short maxLength = apdu.setOutgoing();
		short length = 0;
		short pointer = 0;
		//bo dem apdu
		apdu.setOutgoingLength(size);
		while (size > 0) {
			length = getMin(size, maxLength);
			apdu.sendBytesLong(avatarBuffer, pointer, length);
			size -= length;
			pointer += length;
		}
	}
	private short getMin(short lengthOne,short lengthTwo){
	    if(lengthOne<=lengthTwo){
		    return lengthOne;
	    }
	    return lengthTwo;
    }
	private void updateAvatar(APDU apdu){
	    byte[]buf=apdu.getBuffer();
	    short received=apdu.setIncomingAndReceive();
	    short offset=apdu.getOffsetCdata();
	    short pointer=0;
	    while(received>0){
		    Util.arrayCopyNonAtomic(buf,offset,avatarBuffer,pointer,received);
		    pointer+=received;
		    received=apdu.receiveBytes(offset);
	    }
        this.sizeAvatar = pointer;
	    aes.encode(avatarBuffer,(short)0,MAX_SIZE,key,avatar);
    }
    
    private void verify(APDU apdu){
		byte[] buf=apdu.getBuffer();
		byte offset=ISO7816.OFFSET_CDATA;
		short length=buf[ISO7816.OFFSET_LC];
		if(checkPin(buf,offset,length)){
			return;
		}
		buf[ISO7816.OFFSET_CDATA]=getTryRemaining();
		apdu.setOutgoingAndSend(ISO7816.OFFSET_CDATA,(short)1);
		ISOException.throwIt(ISO7816.SW_WRONG_DATA);
	}
	private void create(APDU apdu) throws ISOException{
		byte[] buf=apdu.getBuffer();
		switch(buf[ISO7816.OFFSET_P1]){
		case BANK_INFORMATION:
			createInformation(apdu);
			break;
		case SIGNATURE:
			createSignature(apdu);
			break;
		default:
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
	}
	//app  --gui du lieu -->  ma hoa ---> luu vao the
	public void createInformation(APDU apdu) throws ISOException{
		if(nameCard[0x00]!=0x00){
			ISOException.throwIt(ISO7816.SW_COMMAND_NOT_ALLOWED);
		}
		byte[]buf=apdu.getBuffer();
		if(buf[ISO7816.OFFSET_LC]==(byte)0x00){
		     ISOException.throwIt(ISO7816.SW_DATA_INVALID);
	     }
	     byte offset;
	     short length;
	     key.setKey(getPIN(),(short)0);
	     JCSystem.beginTransaction();
	     //numberCard
	     offset=ISO7816.OFFSET_CDATA;
	     length=(short)buf[offset];
	     numberCard = aes.encode(buf,(short)(offset+1),length,key,numberCard);
	     //nameCard
	     offset+=(byte)(length+1);
	     length=(short)buf[offset];
	     nameCard = aes.encode(buf,(short)(offset+1),length,key,nameCard);
	     //createCard
	     offset+=(byte)(length+1);
	     length=(short)buf[offset];
	     createDate = aes.encode(buf,(short)(offset+1),length,key,createDate);
	     
	     //expirationCard
	     offset+=(byte)(length+1);
	     length=(short)buf[offset];
	     expirationDate = aes.encode(buf,(short)(offset+1),length,key,expirationDate);
	     
	     //numberBalance
	     offset+=(byte)(length+1);
	     length=(short)buf[offset];
	     numberBalance = aes.encode(buf,(short)(offset+1),length,key,numberBalance);
	     
	     JCSystem.commitTransaction();
	     KeyPair keyPair=RsaConfig.generateKeyPair();
	     privateKey=(RSAPrivateKey)keyPair.getPrivate();
	     publicKey=(RSAPublicKey) keyPair.getPublic();
	    
	     length=RsaConfig.serializePublicKey(publicKey,buf,(short)0);
	    //gui public key -> App, App nhan duoc public key => thong bao thanh cong khoi tao thong tin
	     apdu.setOutgoingAndSend((short)0,length);
	}
    private boolean checkPin(byte[]buf,byte offset,short length){
	    if(tryRemaining==(byte)0x00){
		    return false;
	    }
	    byte[]temp=JCSystem.makeTransientByteArray((short) pin.length, JCSystem.CLEAR_ON_DESELECT);
		messageDigest.reset();
		messageDigest.doFinal(buf, (short) offset, length, temp, (short) 0);
		if(Util.arrayCompare(pin,(short)0,temp,(short)0,(short)pin.length)==(byte)0x00){
			this.tryRemaining=retry;
			this.isValidated=true;
			return true;
		}
		tryRemaining--;
		return false;
    }
    public void updatePin(byte[]buf,byte offset,short length){
		if(length<1){
			ISOException.throwIt(ISO7816.SW_DATA_INVALID);
		}
		messageDigest.reset();
		messageDigest.doFinal(buf,(short)offset,length,pin,(short)0);
		tryRemaining=retry;
	}
    public byte[]getPIN(){
		return pin;
	}
	public byte getTryRemaining(){
		return tryRemaining;
	}
	public boolean isValidated(){
		return isValidated;
	}
	
	private void createSignature(APDU apdu) throws ISOException {
		if (nameCard[0x00] == 0x00) {	
			ISOException.throwIt(ISO7816.SW_COMMAND_NOT_ALLOWED);
		}
		
		byte[] buffer = apdu.getBuffer();
		short length = buffer[ISO7816.OFFSET_LC];
		
		if (length == (byte) 0x00) {
			ISOException.throwIt(ISO7816.SW_DATA_INVALID);
		}
		signature.init(privateKey, Signature.MODE_SIGN);
		signature.sign(buffer, (short) ISO7816.OFFSET_CDATA, length, signatureBuf, (short) 0);
		apdu.setOutgoing();
		apdu.setOutgoingLength((short) signatureBuf.length);
		apdu.sendBytesLong(signatureBuf, (short) 0, (short) signatureBuf.length);
	}
	
	public static void install(byte[] bArray, short bOffset, byte bLength) 
	{
		new bankcard().register(bArray, (short) (bOffset + 1), bArray[bOffset]);
	}

	

}
