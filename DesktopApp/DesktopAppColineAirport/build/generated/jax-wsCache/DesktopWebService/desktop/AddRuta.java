
package desktop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addRuta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addRuta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="keyRow" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="keyColumn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="costo" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="minutes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="piloto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addRuta", propOrder = {
    "keyRow",
    "keyColumn",
    "costo",
    "minutes",
    "piloto"
})
public class AddRuta {

    protected int keyRow;
    protected int keyColumn;
    protected double costo;
    protected int minutes;
    protected String piloto;

    /**
     * Gets the value of the keyRow property.
     * 
     */
    public int getKeyRow() {
        return keyRow;
    }

    /**
     * Sets the value of the keyRow property.
     * 
     */
    public void setKeyRow(int value) {
        this.keyRow = value;
    }

    /**
     * Gets the value of the keyColumn property.
     * 
     */
    public int getKeyColumn() {
        return keyColumn;
    }

    /**
     * Sets the value of the keyColumn property.
     * 
     */
    public void setKeyColumn(int value) {
        this.keyColumn = value;
    }

    /**
     * Gets the value of the costo property.
     * 
     */
    public double getCosto() {
        return costo;
    }

    /**
     * Sets the value of the costo property.
     * 
     */
    public void setCosto(double value) {
        this.costo = value;
    }

    /**
     * Gets the value of the minutes property.
     * 
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Sets the value of the minutes property.
     * 
     */
    public void setMinutes(int value) {
        this.minutes = value;
    }

    /**
     * Gets the value of the piloto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiloto() {
        return piloto;
    }

    /**
     * Sets the value of the piloto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiloto(String value) {
        this.piloto = value;
    }

}
