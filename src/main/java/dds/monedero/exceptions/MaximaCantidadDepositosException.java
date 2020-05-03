package dds.monedero.exceptions;

public class MaximaCantidadDepositosException extends RuntimeException {

  public MaximaCantidadDepositosException(String message) {
    super(message);
  }

}

//se podria hacer una unica excepcion que sea supera el maximo y en el mensaje le agregas si es extraccion o deposito