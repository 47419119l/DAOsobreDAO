//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.20 at 04:44:30 PM CEST 
//


package generated;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for dadesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dadesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clients" type="{}clientsType"/>
 *         &lt;element name="empleats" type="{}empleatsType"/>
 *         &lt;element name="factures" type="{}facturesType"/>
 *         &lt;element name="cataleg" type="{}catalegType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dadesType", propOrder = {
    "clients",
    "empleats",
    "factures",
    "cataleg"
})
@XmlRootElement(name = "dades")
public class DadesType {

    @XmlElement(required = true)
    protected ClientsType clients;
    @XmlElement(required = true)
    protected EmpleatsType empleats;
    @XmlElement(required = true)
    protected FacturesType factures;
    @XmlElement(required = true)
    protected CatalegType cataleg;

    /**
     * Gets the value of the clients property.
     * 
     * @return
     *     possible object is
     *     {@link ClientsType }
     *     
     */
    public ClientsType getClients() {
        return clients;
    }

    /**
     * Sets the value of the clients property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientsType }
     *     
     */
    public void setClients(ClientsType value) {
        this.clients = value;
    }

    /**
     * Gets the value of the empleats property.
     * 
     * @return
     *     possible object is
     *     {@link EmpleatsType }
     *     
     */
    public EmpleatsType getEmpleats() {
        return empleats;
    }

    /**
     * Sets the value of the empleats property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmpleatsType }
     *     
     */
    public void setEmpleats(EmpleatsType value) {
        this.empleats = value;
    }

    /**
     * Gets the value of the factures property.
     * 
     * @return
     *     possible object is
     *     {@link FacturesType }
     *     
     */
    public FacturesType getFactures() {
        return factures;
    }

    /**
     * Sets the value of the factures property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacturesType }
     *     
     */
    public void setFactures(FacturesType value) {
        this.factures = value;
    }

    /**
     * Gets the value of the cataleg property.
     * 
     * @return
     *     possible object is
     *     {@link CatalegType }
     *     
     */
    public CatalegType getCataleg() {
        return cataleg;
    }

    /**
     * Sets the value of the cataleg property.
     * 
     * @param value
     *     allowed object is
     *     {@link CatalegType }
     *     
     */
    public void setCataleg(CatalegType value) {
        this.cataleg = value;
    }

}
