package com.tambapps.p2p.speer.seek.strategy;

import com.tambapps.p2p.speer.Peer;
import com.tambapps.p2p.speer.util.PeerUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.InetAddress;

public class LastOctetSeekingStrategy implements SeekingStrategy {

  private final byte[] address;
  protected final int port;
  protected final byte start;

  @Getter(AccessLevel.PROTECTED)
  private int i;

  public LastOctetSeekingStrategy(String address, int port) {
    this(PeerUtils.getAddress(address), port);
  }

  public LastOctetSeekingStrategy(InetAddress address, int port) {
    this(address, port, (byte) 0);
  }

  public LastOctetSeekingStrategy(InetAddress address, int port, byte start) {
    this.address = address.getAddress();
    this.port = port;
    this.start = start;
    reset();
  }

  @Override
  public void reset() {
    i = 0;
    this.address[3] = start;
  }

  @Override
  public boolean hasNext() {
    return i <= 255;
  }

  @SneakyThrows
  @Override
  public Peer next() {
    Peer peer = Peer.of(InetAddress.getByAddress(address), port);
    i++;
    address[3] = nextLastOctet();
    return peer;
  }

  protected byte nextLastOctet() {
    return (byte) (start + i);
  }
}
