
package converters;

import entities.Bem;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;


@FacesConverter(forClass = Bem.class)
@Named
public class BemConverter extends EntityConverter<Bem> {
    
    public BemConverter() {
        super(Bem.class);
    }
}
