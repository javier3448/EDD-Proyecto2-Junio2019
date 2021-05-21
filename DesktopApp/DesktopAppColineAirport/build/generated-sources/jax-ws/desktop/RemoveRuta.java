
package desktop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for removeRuta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="removeRuta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="keyRow" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="keyCol" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeRuta", propOrder = {
    "keyRow",
    "keyCol"
})
public class RemoveRuta {

    protected int keyRow;
    protected int keyCol;

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
     * Gets the value of the keyCol property.
     * 
     */
    public int getKeyCol() {
        return keyCol;
    }

    /**
     * Sets the value of the keyCol property.
     * 
     */
    public void setKeyCol(int value) {
        this.keyCol = value;
    }

}
