package dds.monedero.model;

import java.time.LocalDate;

//Movimiento podria tener dos clases, una que sea deposito y una que sea extraccions. Code smell de primitive al tener deposito como 
//variable

public class Movimiento {
  private LocalDate fecha;
  //En ningún lenguaje de programación usen jamás doubles para modelar dinero en el mundo real
  //siempre usen numeros de precision arbitraria, como BigDecimal en Java y similares
  private double monto;
  private boolean esDeposito;//////

  public Movimiento(LocalDate fecha, double monto, boolean esDeposito) {
    this.fecha = fecha;
    this.monto = monto;
    this.esDeposito = esDeposito; //////
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public boolean fueDepositado(LocalDate fecha) {
    return isDeposito() && esDeLaFecha(fecha);
  }
//codigo duplicado 
  public boolean fueExtraido(LocalDate fecha) {
    return isExtraccion() && esDeLaFecha(fecha);
  }
  
  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public boolean isDeposito() {
    return esDeposito; 
  }
//type test pareceria ser esto
  public boolean isExtraccion() {
    return !esDeposito;
  }

  public void agregateA(Cuenta cuenta) {
    cuenta.setSaldo(calcularValor(cuenta)); 
    //deberia ser responsabilidad de la cuenta cambiar el saldo cuando se agrega movimiento
    cuenta.agregarMovimiento(fecha, monto, esDeposito);
  }

  public double calcularValor(Cuenta cuenta) { //misplaced method; este es un metodo que deberia estar en cuenta y no aca
    if (esDeposito) {
      return cuenta.getSaldo() + getMonto();
    } else {
      return cuenta.getSaldo() - getMonto();
    }
  }
}
