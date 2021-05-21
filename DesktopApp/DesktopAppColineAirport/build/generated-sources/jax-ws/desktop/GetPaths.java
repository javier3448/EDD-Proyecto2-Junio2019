
package desktop;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getPaths complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getPaths">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="startKey" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="endKey" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPaths", propOrder = {
    "startKey",
    "endKey"
})
public class GetPaths {

    protected int startKey;
    protected int endKey;

    /**
     * Gets the value of the startKey property.
     * 
     */
    public int getStartKey() {
        return startKey;
    }

    /**
     * Sets the value of the startKey property.
     * 
     */
    public void setStartKey(int value) {
        this.startKey = value;
    }

    /**
     * Gets the value of the endKey property.
     * 
     */
    public int getEndKey() {
        return endKey;
    }

    /**
     * Sets the value of the endKey property.
     * 
     */
    public void setEndKey(int value) {
        this.endKey = value;
    }

}
