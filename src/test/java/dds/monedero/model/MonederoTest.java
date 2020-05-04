package dds.monedero.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public class MonederoTest {
  private Cuenta cuenta;

  @Before
  public void init() {
    cuenta = new Cuenta();
  }
  
  
 //TODO METODO DEBE TENER ASSERT!  
  @Test
  public void siNoIndicoElSaldoEs0() {
	  assert( 0 ==  cuenta.getSaldo());
  }


@Test
  public void Poner() {
    cuenta.poner(1500);
    assert (1500==cuenta.getSaldo());
//ver como comparar con double
  }//podria hacer el assert equals para ver que se coloco donde debia ser

  @Test(expected = MontoNegativoException.class)
  public void PonerMontoNegativo() {
    cuenta.poner(-1500);
    assert (-1500 == cuenta.getSaldo());
  }

  @Test
  public void TresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    double suma = 1500 + 456 + 1900;
    assert (suma == cuenta.getSaldo());
    //podria hacer un assert para ver que se coloco bien el dinero
  }

  @Test(expected = MaximaCantidadDepositosException.class)
  public void MasDeTresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    cuenta.poner(245);
    double suma = 1500 + 456 + 1900 + 245;
    assert (suma == cuenta.getSaldo());
  }

  @Test(expected = SaldoMenorException.class)
  public void ExtraerMasQueElSaldo() {
    cuenta.setSaldo(90);
    cuenta.sacar(1001);
    double resta = 90 -1001;
    assert (resta == cuenta.getSaldo());
  }

  @Test(expected = MaximoExtraccionDiarioException.class)
  public void ExtraerMasDe1000() {
    cuenta.setSaldo(5000);
    cuenta.sacar(1001);
    double resta = 5000 -1001;
    assert (resta == cuenta.getSaldo());
    
  }

  @Test(expected = MontoNegativoException.class)
  public void ExtraerMontoNegativo() {
    cuenta.sacar(-500);
    assert (-500 == cuenta.getSaldo());
  }

}