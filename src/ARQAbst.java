import java.net.DatagramPacket;
import java.util.logging.Logger;

/** @author Jörg Vogt */
public abstract class ARQAbst implements ARQ {
  protected int MTU = 1400;               // Client max. payload from higher layer
  protected final int retryCounter = 20;  // number of transmissions for one packet for client
  protected final int receiveTimeout = 10000; // server interrupts after time without receive
  protected final int headerSize = 3;     // protocol header length for data packets
  protected final int headerSizeAck = 4;  // protocol header length for ack packets
  protected final int pNumbers = 256;     // packet numbers 0...255
  protected int backData;                 // data for feedback, CRC (piggy backing)
  protected int pNr = 0;                  // actual packet number, protocol starts with number 0
  protected int sessionID = 0;            // only packets with correct ID are used
  protected int retransmissionCounter;    // statistics, summarized retransmissions
  protected int retryCounterStat = 0;     // statistics, max. retransmissions for one packet
  protected Socket socket;
  protected Logger logger;

  /**
   * Receiver, locks in a new sessionID
   * @param socket Class which offers the packet transmit and receive
   */
  public ARQAbst(Socket socket) {
    this.socket = socket;
    logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  }
  /**
   * Sender
   * @param socket Class which offers the packet transmit and receive
   * @param sessionID is set from higher layer
   */
  public ARQAbst(Socket socket, int sessionID)  {
    this.socket = socket;
    this.sessionID = sessionID;
    logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  }
  /**
   * Sender: Request the transmission of a data packet
   * @param hlData DataPacket from Higher Layer
   * @param hlSize Size of the DataPacket
   * @return true implies a correct ACK is received
   */
  @Override
  public abstract boolean data_req(byte[] hlData, int hlSize, boolean lastTransmission);

  /**
   * Sender: Wait until RTO for the ACK-packet, checks sessionID and pNr
   * @param packetNr ACK-Number we are waiting for
   * @return true if correct ACK is received in time
   */
  protected abstract boolean waitForAck(int packetNr);

  protected abstract int getPacketNr(DatagramPacket packet);

  /**
   * Sender: Collects the gbn and backdata from the ACK packet -> member variables
   * @param packet
   */
  protected abstract void getAckData(DatagramPacket packet);

  protected abstract int getSessionID(DatagramPacket packet);

  /**
   * Sender: Adds protocol header: sessionID, pNr
   * @param sendData HL-data
   * @param dataSize uses size of HL-data array
   * @return SW-packet
   */
  protected abstract byte[] generateDataPacket(byte[] sendData, int dataSize);

  // ******************************** Receiver *****************************************************

  /**
   * Receiver/Server: Request to receive a data packet (first packet is start)
   * @param values Optional data for the Sender/Client
   * @return DataPacket without SW-Header
   * @throws TimeoutException Timeout of Socket
   */
  @Override
  public abstract byte[] data_ind_req(int... values) throws TimeoutException;

  /**
   * Receiver: generates ACK accordingly to protocol definition Session ID + PacketNr.
   * @param packetNr to Acknowledge
   * @return the ACK packet
   */
  abstract byte[] generateAckPacket(int packetNr);

  /**
   * Receiver: generates and sends ACK accordingly to protocol definition Session ID + PacketNr + (CRC)
   * @param nr
   */
  abstract void sendAck(int nr);

  /**
   * Receiver: Checks for start packet
   * @param packet UDP
   * @return true, if packet includes phrase "Start"
   */
  abstract boolean checkStart(DatagramPacket packet);


  // Getter and Setter *****************************************************************************
  public int getBackData() { return backData; }

  @Override
  public int getRetransmissionCounter() {
    return retransmissionCounter;
  }

  public int getRetryCounterStat() {return retryCounterStat;}

  @Override
  public int getMTU() {
    return MTU;
  }

  @Override
  public void setMTU(int MTU) {
    this.MTU = MTU;
  }
}
