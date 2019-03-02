package eu.saltyscout.regionmanager.flag.type.list;

import eu.saltyscout.regionmanager.flag.Flag;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 05-Dec-16.
 */
public class MaterialListFlag extends ListFlag<Material> {
    public MaterialListFlag(Object value) {
        super(value);
    }
    
    @Override
    public Material parseElement(String str) {
        Material result;
        if(str == null) {
            result = null;
        } else {
            result = Material.getMaterial(str.toUpperCase().replace(' ', '_'));
            if(result == null) {
                /*try {
                    int id = Integer.parseInt(str);
                    result = Material.getMaterial(id);
                } catch (Exception e) {
                    throw new UnsupportedOperationException("Unknown Material '" + str + "'.");
                }*/
                throw new UnsupportedOperationException("Unknown Material '" + str + "'.");
            }
        }
        return result;
    }
    
    @Override
    public String toString(Material element) {
        return String.valueOf(element);
    }

    @Override
    public String getTypeDescription() {
        return "Material List";
    }

    @Override
    public Flag<List<Material>> clone() {
        return new MaterialListFlag(new ArrayList<>(getValue()));
    }
}
