package entities;

import entities.Bem.Tipo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bem.class)
public abstract class Bem_ {

	public static volatile SingularAttribute<Bem, Boolean> seguroAtivo;
	public static volatile SingularAttribute<Bem, Tipo> tipo;
	public static volatile SingularAttribute<Bem, String> contratante;
	public static volatile SingularAttribute<Bem, String> mensalidade;
	public static volatile SingularAttribute<Bem, Long> id;

}

