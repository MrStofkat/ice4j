/*
 * Ice4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.ice4j.attribute;

import org.ice4j.*;

/**
 * The PEER-ADDRESS attribute is given by a TURN client to
 * indicates the peer destination address of its relayed packet.
 *
 * It has the same format as XOR-MAPPED-ADDRESS.
 *
 * @author Sebastien Vincent
 * @version 0.1
 */
public class PeerAddressAttribute extends XorMappedAddressAttribute
{
  /**
   * Attribute name.
   */
  public static final String NAME = "PEER-ADDRESS";

  /**
   * Constructor.
   */
  PeerAddressAttribute()
  {
    super(PEER_ADDRESS);
  }
}

