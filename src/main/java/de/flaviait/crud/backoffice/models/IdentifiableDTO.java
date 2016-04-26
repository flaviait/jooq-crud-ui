package de.flaviait.crud.backoffice.models;

import java.io.Serializable;

public interface IdentifiableDTO<T> extends Serializable {

  T getId();

}
