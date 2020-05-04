package dds.monedero.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public class Cuenta {
	
  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>(); 

  public Cuenta() { //saldo ya esta inicializado en 0, no hace falta tenerlo aca
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial; 
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void poner(double cuanto) {
	  chequearMonto(cuanto);
	  
    if (getMovimientos().stream().filter(movimiento -> movimiento.isDeposito()).count() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }

    new Movimiento(LocalDate.now(), cuanto, true).agregateA(this);
  }
  //mucho codigo duplicado en poner y sacar
  public void sacar(double cuanto) {
	 chequearMonto(cuanto);
	 chequearSaldo(cuanto);
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now()); //metodo muy largo, pdria tener un metodo que verifique no exceso limite
    double limite = 1000 - montoExtraidoHoy;
    chequeoExcesoExtraccion (cuanto,limite);
    new Movimiento(LocalDate.now(), cuanto, false).agregateA(this);
    }
  
  
  public void chequeoExcesoExtraccion(double cuanto,double limite) {
	  if (cuanto > limite) {
	      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
	          + " diarios, límite: " + limite);
	    }
  }
  
  public void chequearMonto(double cuanto) {
	    if (cuanto <= 0) {
	        throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
	      }
  }
  
  public void chequearSaldo(double cuanto) {
	    if (getSaldo() - cuanto < 0) {
	        throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
	      }
  }
  //3 chequeos. mmm no puede haber una clase Chequeos que se encargue de eso?
  
  public void agregarMovimiento(LocalDate fecha, double cuanto, boolean esDeposito) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento);
    agregarAlSaldo(movimiento);
  }
  
  public void agregarAlSaldo(Movimiento mov) {
	    if (mov.isDeposito()) {
	       saldo += mov.getMonto();
	      } else {
	       saldo -= mov.getMonto();
	      }
  }

 public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> ! movimiento.fueExtraido(LocalDate.now())) 
        //el filter podria pedir al movimiento que se fije si fue depositado ese dia. Esto es un feature envy. RESUELTO
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}
