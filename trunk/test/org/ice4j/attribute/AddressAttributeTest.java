/*
 * Stun4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.ice4j.attribute;

import junit.framework.*;

import java.util.Arrays;

import org.ice4j.*;
import org.ice4j.attribute.*;

/**
 *
 * <p>Title: Stun4J.</p>
 * <p>Description: Simple Traversal of UDP Through NAT</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Organisation:  <p> Organisation: Louis Pasteur University, Strasbourg, France</p>
 *                   <p> Network Research Team (http://www-r2.u-strasbg.fr)</p></p>
 * @author Emil Ivov
 * @version 0.1
 */
public class AddressAttributeTest extends TestCase {
    private AddressAttribute addressAttribute = null;
    private MsgFixture msgFixture;

    public AddressAttributeTest(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();

        addressAttribute = new MappedAddressAttribute();
        msgFixture = new MsgFixture();

        msgFixture.setUp();
    }

    protected void tearDown() throws Exception {
        addressAttribute = null;
        msgFixture.tearDown();

        msgFixture = null;
        super.tearDown();
    }

    /**
     * Verify that AddressAttribute descendants have correctly set types and
     * names.
     */
    public void testAddressAttributeDescendants() {
        char expectedType;
        char actualType;
        String expectedName;
        String actualName;

        //MAPPED-ADDRESS
        addressAttribute = new MappedAddressAttribute();

        expectedType = Attribute.MAPPED_ADDRESS;
        actualType = addressAttribute.getAttributeType();

        expectedName = "MAPPED-ADDRESS";
        actualName = addressAttribute.getName();

        assertEquals("MappedAddressAttribute does not the right type.",
                     expectedType, actualType);
        assertEquals("MappedAddressAttribute does not the right name.",
                     expectedName, actualName);


        //SOURCE-ADDRESS
        addressAttribute = new SourceAddressAttribute();

        expectedType = Attribute.SOURCE_ADDRESS;
        actualType = addressAttribute.getAttributeType();

        expectedName = "SOURCE-ADDRESS";
        actualName = addressAttribute.getName();

        assertEquals("SourceAddressAttribute does not the right type.",
                     expectedType, actualType);
        assertEquals("SourceAddressAttribute does not the right name.",
                     expectedName, actualName);


        //CHANGED-ADDRESS
        addressAttribute = new ChangedAddressAttribute();

        expectedType = Attribute.CHANGED_ADDRESS;
        actualType = addressAttribute.getAttributeType();

        expectedName = "CHANGED-ADDRESS";
        actualName = addressAttribute.getName();

        assertEquals("ChangedAddressAttribute does not the right type.",
                     expectedType, actualType);
        assertEquals("ChangedAddressAttribute does not the right name.",
                     expectedName, actualName);


        //RESPONSE-ADDRESS
        addressAttribute = new ResponseAddressAttribute();

        expectedType = Attribute.RESPONSE_ADDRESS;
        actualType = addressAttribute.getAttributeType();

        expectedName = "RESPONSE-ADDRESS";
        actualName = addressAttribute.getName();

        assertEquals("ResponseAddressAttribute does not the right type.",
                     expectedType, actualType);
        assertEquals("ResponseAddressAttribute does not the right name.",
                     expectedName, actualName);


        //REFLECTED-FROM
        addressAttribute = new ReflectedFromAttribute();

        expectedType = Attribute.REFLECTED_FROM;
        actualType = addressAttribute.getAttributeType();

        expectedName = "REFLECTED-FROM";
        actualName = addressAttribute.getName();

        assertEquals("ReflectedFromAttribute does not the right type.",
                     expectedType, actualType);
        assertEquals("ReflectedFromAttribute does not the right name.",
                     expectedName, actualName);

        //REFLECTED-FROM
        addressAttribute = new ReflectedFromAttribute();

        expectedType = Attribute.REFLECTED_FROM;
        actualType = addressAttribute.getAttributeType();

        expectedName = "REFLECTED-FROM";
        actualName = addressAttribute.getName();

        assertEquals("ReflectedFromAttribute does not the right type.",
                     expectedType, actualType);
        assertEquals("ReflectedFromAttribute does not the right name.",
                     expectedName, actualName);

        //XOR-MAPPED-ADDRESS
        addressAttribute = new XorMappedAddressAttribute();

        expectedType = Attribute.XOR_MAPPED_ADDRESS;
        actualType = addressAttribute.getAttributeType();

        expectedName = "XOR-MAPPED-ADDRESS";
        actualName = addressAttribute.getName();

        assertEquals("XorMappedAddressAttribute does not the right type.",
                     expectedType, actualType);
        assertEquals("XorMappedAddressAttribute does not the right name.",
                     expectedName, actualName);

        /* ALTERNATE-SERVER */
        addressAttribute = new AlternateServerAttribute();

        expectedType = Attribute.ALTERNATE_SERVER;
        actualType = addressAttribute.getAttributeType();

        expectedName = "ALTERNATE-SERVER";
        actualName = addressAttribute.getName();

        assertEquals("AlternateServerAttribute does not the right type.",
                     expectedType, actualType);
        assertEquals("AlternateAttribute does not the right name.",
                     expectedName, actualName);


        /* XOR-PEER-ADDRESS */
        addressAttribute = new XorPeerAddressAttribute();

        expectedType = Attribute.XOR_PEER_ADDRESS;
        actualType = addressAttribute.getAttributeType();

        expectedName = "XOR-PEER-ADDRESS";
        actualName = addressAttribute.getName();

        assertEquals("XorPeerAddressAttribute does not the right type.",
                     expectedType, actualType);
        assertEquals("XorPeerAddressAttribute does not the right name.",
                     expectedName, actualName);

        /* XOR-RELAYED-ADDRESS */
        addressAttribute = new XorRelayedAddressAttribute();

        expectedType = Attribute.XOR_RELAYED_ADDRESS;
        actualType = addressAttribute.getAttributeType();

        expectedName = "XOR-RELAYED-ADDRESS";
        actualName = addressAttribute.getName();

        assertEquals("XorRelayedAddressAttribute does not the right type.",
                     expectedType, actualType);
        assertEquals("XorRelayedAddressAttribute does not the right name.",
                     expectedName, actualName);
    }

    /**
     * Verifies that xorred address-es are properly xor-ed for IPv4 addresses.
     */
    public void testXorMappedAddressXoring_v4()
    {
        XorMappedAddressAttribute addressAttribute = new XorMappedAddressAttribute();
        TransportAddress testAddress =
            new TransportAddress("130.79.95.53", 12120);

        addressAttribute.setAddress(testAddress);

        //do a xor with an id equal to the v4 address itself so that we get 0000..,
        TransportAddress xorredAddr =
            addressAttribute.applyXor(new byte[]{(byte)130,79,95,53,0,0,0,0,0,0,0,0,0,0,0,0,0});

        assertTrue("Xorring the address with itself didn't return 00000...",
            Arrays.equals(xorredAddr.getAddressBytes(), new byte[]{0,0,0,0}));

        assertTrue("Port was not xorred",
                       testAddress.getPort()  != xorredAddr.getPort());

        //Test xor-ing the original with the xored - should get the xor code
        addressAttribute.setAddress(testAddress);
        xorredAddr =
            addressAttribute.applyXor(new byte[]{21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36});

        xorredAddr =
            addressAttribute.applyXor(xorredAddr.getAddressBytes());

        assertTrue("Xorring the original with the xor-ed didn't return the code..",
            Arrays.equals(
                   xorredAddr.getAddressBytes(),
                   new byte[]{21,22,23,24}));

        assertTrue("Port was not xorred",
                       testAddress.getPort()  != 0xFFFF);

        //Test double xor-ing - should get the original
        addressAttribute.setAddress(testAddress);
        xorredAddr =
            addressAttribute.applyXor(new byte[]{21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36});

        addressAttribute.setAddress(xorredAddr);
        xorredAddr =
            addressAttribute.applyXor(new byte[]{21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36});

        assertEquals("Double xorring didn't give the original ...",
            testAddress, xorredAddr);
    }

    /**
     * Verifies that xorred address-es are properly xor-ed for IPv6 addresses.
     */
    public void testXorMappedAddressXoring_v6()
    {
        XorMappedAddressAttribute addressAttribute = new XorMappedAddressAttribute();
        TransportAddress testAddress =
            new TransportAddress("2001:660:4701:1001:202:8aff:febe:130b", 12120);

        addressAttribute.setAddress(testAddress);

        //do a xor with an id equal to the v4 address itself so that we get 0000..,
        TransportAddress xorredAddr =
            addressAttribute.applyXor(
                new byte[]{(byte)0x20, (byte)0x01, (byte)0x06, (byte)0x60,
                           (byte)0x47, (byte)0x01, (byte)0x10, (byte)0x01,
                           (byte)0x02, (byte)0x02, (byte)0x8a, (byte)0xff,
                           (byte)0xfe, (byte)0xbe, (byte)0x13, (byte)0x0b});

        assertTrue("Xorring the address with itself didn't return 00000...",
            Arrays.equals(xorredAddr.getAddressBytes(),
                          new byte[]{0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0,
                                     0,0,0,0}));

        assertTrue("Port was not xorred",
                       testAddress.getPort()  != xorredAddr.getPort());

        //Test xor-ing the original with the xored - should get the xor code
        addressAttribute.setAddress(testAddress);
        xorredAddr =
            addressAttribute.applyXor(new byte[]{21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36});

        xorredAddr =
            addressAttribute.applyXor(xorredAddr.getAddressBytes());

        assertTrue("Xorring the original with the xor-ed didn't return the code..",
            Arrays.equals(
                   xorredAddr.getAddressBytes(),
                   new byte[]{21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36}));

        assertTrue("Port was not xorred",
                       testAddress.getPort()  != 0xFFFF);

        //Test double xor-ing - should get the original
        addressAttribute.setAddress(testAddress);
        xorredAddr =
            addressAttribute.applyXor(new byte[]{21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36});

        addressAttribute.setAddress(xorredAddr);
        xorredAddr =
            addressAttribute.applyXor(new byte[]{21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36});

        assertEquals("Double xorring didn't give the original ...",
            testAddress, xorredAddr);
    }

    /**
     * Test whetner sample binary arrays are correctly decoded.
     * @throws StunException
     */
    public void testDecodeAttributeBody() throws StunException {
        byte[] attributeValue = msgFixture.mappedAddress;
        char offset = Attribute.HEADER_LENGTH;
        char length = (char)(attributeValue.length - offset);

        addressAttribute.decodeAttributeBody(attributeValue, offset, length);


        assertEquals("AddressAttribute.decode() did not properly decode the port field.",
                     msgFixture.ADDRESS_ATTRIBUTE_PORT,
                     addressAttribute.getPort());
        assertTrue("AddressAttribute.decode() did not properly decode the address field.",
                     Arrays.equals( msgFixture.ADDRESS_ATTRIBUTE_ADDRESS,
                                    addressAttribute.getAddressBytes()));


    }

    /**
     * Test whetner sample binary arrays are correctly decoded.
     * @throws StunException
     */
    public void testDecodeAttributeBodyv6() throws StunException {
        byte[] attributeValue = msgFixture.mappedAddressv6;
        char offset = Attribute.HEADER_LENGTH;
        char length = (char)(attributeValue.length - offset);

        addressAttribute.decodeAttributeBody(attributeValue, offset, length);


        assertEquals("decode() failed for an IPv6 Addr's port.",
                     msgFixture.ADDRESS_ATTRIBUTE_PORT,
                     addressAttribute.getPort());
        assertTrue("AddressAttribute.decode() failed for an IPv6 address.",
                     Arrays.equals( msgFixture.ADDRESS_ATTRIBUTE_ADDRESS_V6,
                     addressAttribute.getAddressBytes()));


    }

    /**
     * Test whether attributes are properly encoded.
     *
     * @throws StunException java.lang.Exception if we fail
     */
    public void testEncode()
        throws StunException
    {
        byte[] expectedReturn = msgFixture.mappedAddress;

        addressAttribute.setAddress(
                            new TransportAddress(msgFixture.ADDRESS_ATTRIBUTE_ADDRESS,
                                        msgFixture.ADDRESS_ATTRIBUTE_PORT));

        byte[] actualReturn = addressAttribute.encode();
        assertTrue("AddressAttribute.encode() did not properly encode a sample attribute",
                     Arrays.equals( expectedReturn, actualReturn));
    }

    /**
     * Test whether attributes are properly encoded.
     *
     * @throws StunException java.lang.Exception if we fail
     */
    public void testEncodev6()
        throws StunException
    {
        byte[] expectedReturn = msgFixture.mappedAddressv6;

        addressAttribute.setAddress(
            new TransportAddress(msgFixture.ADDRESS_ATTRIBUTE_ADDRESS_V6,
                            msgFixture.ADDRESS_ATTRIBUTE_PORT));

        byte[] actualReturn = addressAttribute.encode();
        assertTrue("An AddressAttribute did not properly encode an IPv6 addr.",
                     Arrays.equals( expectedReturn, actualReturn));
    }


    /**
     * Tests the equals method against a null, a different and an identical
     * object.
     *
     * @throws StunException java.lang.Exception if we fail
     */
    public void testEquals()
        throws StunException
    {
        //null test
        AddressAttribute target = null;
        boolean expectedReturn = false;
        boolean actualReturn = addressAttribute.equals(target);

        assertEquals("AddressAttribute.equals() failed against a null target.",
                     expectedReturn, actualReturn);

        //difference test
        target = new MappedAddressAttribute();

        char port = (char)(msgFixture.ADDRESS_ATTRIBUTE_PORT + 1 );
        target.setAddress(  new TransportAddress(msgFixture.ADDRESS_ATTRIBUTE_ADDRESS,
                                        port));

        addressAttribute.setAddress(
                             new TransportAddress(msgFixture.ADDRESS_ATTRIBUTE_ADDRESS,
                                msgFixture.ADDRESS_ATTRIBUTE_PORT ));

        expectedReturn = false;
        actualReturn = addressAttribute.equals(target);
        assertEquals("AddressAttribute.equals() failed against a different target.",
                     expectedReturn, actualReturn);

        //equality test
        target.setAddress( new TransportAddress( msgFixture.ADDRESS_ATTRIBUTE_ADDRESS,
                                       msgFixture.ADDRESS_ATTRIBUTE_PORT ));

        expectedReturn = true;
        actualReturn = addressAttribute.equals(target);
        assertEquals("AddressAttribute.equals() failed against an equal target.",
                     expectedReturn, actualReturn);

        //ipv6 equality test
        target.setAddress(  new TransportAddress(msgFixture.ADDRESS_ATTRIBUTE_ADDRESS_V6,
                                        msgFixture.ADDRESS_ATTRIBUTE_PORT));

        addressAttribute.setAddress(
                             new TransportAddress(msgFixture.ADDRESS_ATTRIBUTE_ADDRESS_V6,
                                msgFixture.ADDRESS_ATTRIBUTE_PORT ));
        expectedReturn = true;
        actualReturn = addressAttribute.equals(target);
        assertEquals("AddressAttribute.equals() failed for IPv6 addresses.",
                     expectedReturn, actualReturn);
    }

    /**
     * Tests whether data length is properly calculated.
     *
     * @throws StunException java.lang.Exception if we fail
     */
    public void testGetDataLength()
        throws StunException
    {
        char expectedReturn = 8;//1-padding + 1-family + 2-port + 4-address

        addressAttribute.setAddress(
                            new TransportAddress( msgFixture.ADDRESS_ATTRIBUTE_ADDRESS,
                                         msgFixture.ADDRESS_ATTRIBUTE_PORT));

        char actualReturn = addressAttribute.getDataLength();

        assertEquals("Datalength is not propoerly calculated",
                     expectedReturn, actualReturn);

        expectedReturn = 20;//1-padding + 1-family + 2-port + 16-address
        addressAttribute.setAddress(
                    new TransportAddress( msgFixture.ADDRESS_ATTRIBUTE_ADDRESS_V6,
                                     msgFixture.ADDRESS_ATTRIBUTE_PORT));

        actualReturn = addressAttribute.getDataLength();

        assertEquals("Datalength is not propoerly calculated",
                     expectedReturn, actualReturn);
    }

    /**
     * Tests that the address family is always 1.
     */
    public void testGetFamily() {
        byte expectedReturn = 1;
        addressAttribute.setAddress(
                    new TransportAddress( msgFixture.ADDRESS_ATTRIBUTE_ADDRESS,
                                     msgFixture.ADDRESS_ATTRIBUTE_PORT));
        byte actualReturn = addressAttribute.getFamily();
        assertEquals("Address family was not 1 for an IPv4",
                     expectedReturn, actualReturn);

        //ipv6
        expectedReturn = 2;
        addressAttribute.setAddress(
                    new TransportAddress( msgFixture.ADDRESS_ATTRIBUTE_ADDRESS_V6,
                                     msgFixture.ADDRESS_ATTRIBUTE_PORT));
        actualReturn = addressAttribute.getFamily();
        assertEquals("Address family was not 2 for an IPv6 address",
                     expectedReturn, actualReturn);

    }

}