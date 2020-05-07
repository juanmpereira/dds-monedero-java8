package dds.monedero.model;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;


//existe un refactor que es coverage y te dice cuanto estas cubriendo del codigo con los test. 
// es boton derecho, coverage as -> JunitTest y te dice. Si voy al codigo, lo verde es lo testeado y lo rojo/amarillo no.
//Ahi queda en nosotros que hace falta probar y que no.

//sino mirar getMontoExtraido de cuenta que es como ver si un metodo esta testeado.

public class MonederoTest {
  private Cuenta cuenta;

  @Before
  public void init() {
    cuenta = new Cuenta();
  }
  
 //TODO METODO DEBE TENER ASSERT! excepto los que lanzan excepcion por que ese seria el assertes
  @Test
  public void siNoIndicoElSaldoEs0() {
	  assert( 0 ==  cuenta.getSaldo());
  }


@Test
  public void Poner() {
    cuenta.poner(1500);
    assertEquals(1500,cuenta.getSaldo(),2);
    //assert (1500==cuenta.getSaldo());
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
    assertEquals(suma, cuenta.getSaldo(),2 );
    //podria hacer un assert para ver que se coloco bien el dinero
  }

  @Test(expected = MaximaCantidadDepositosException.class)
  public void MasDeTresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    cuenta.poner(245);

  }

  @Test(expected = SaldoMenorException.class)
  public void ExtraerMasQueElSaldo() {
    cuenta.setSaldo(90);
    cuenta.sacar(1001);

  }

  @Test(expected = MaximoExtraccionDiarioException.class)
  public void ExtraerMasDe1000() {
    cuenta.setSaldo(5000);
    cuenta.sacar(1001);
    
  }

  @Test(expected = MontoNegativoException.class)
  public void ExtraerMontoNegativo() {
    cuenta.sacar(-500);
  }
  
  @Test
  public void montosDelDia() { //test creado para getMontoExtraidoA(localDate.now())
	  //deberia setear la cuenta con movimientos (ej: uno de ayer y otro de hoy)
	  // o podria setear un deposito y una extraccion y ver que solo tenga en cuenta la extraccion.
	  //y despues verificar que me devuelve la extraccion de hoy nomas
	  // hago assertEqualas (x , getMontoExtraidoA(localDate.now()), 2 );
  }

}