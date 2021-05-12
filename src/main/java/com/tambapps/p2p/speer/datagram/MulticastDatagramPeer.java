package com.tambapps.p2p.speer.datagram;

import com.tambapps.p2p.speer.Peer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

@AllArgsConstructor
public class MulticastDatagramPeer implements IDatagramPeer {

  @Getter
  @Setter
  private int defaultBufferSize;
  @Getter
  private final MulticastSocket socket;

  public MulticastDatagramPeer(Peer peer) throws IOException {
    this(new MulticastSocket(peer.toSocketAddress()));
  }

  public MulticastDatagramPeer(int port) throws IOException {
    this(new MulticastSocket(port));
  }
  public MulticastDatagramPeer() throws IOException {
    this(new MulticastSocket());
  }

  public MulticastDatagramPeer(InetAddress address, int port) throws IOException {
    this(new MulticastSocket(new InetSocketAddress(address, port)));
  }

  public MulticastDatagramPeer(MulticastSocket socket) {
    this.socket = socket;
    this.defaultBufferSize = 1024;
  }

  public void send(DatagramPacket packet) throws IOException {
    socket.send(packet);
  }

  public void receive(DatagramPacket packet) throws IOException {
    socket.receive(packet);
  }

  public void joinGroup(InetAddress multicastAddress) throws IOException {
    socket.joinGroup(multicastAddress);
  }

  public void leaveGroup(InetAddress multicastAddress) throws IOException {
    socket.leaveGroup(multicastAddress);
  }

  @Override
  public void close() {
    socket.close();
  }
}
