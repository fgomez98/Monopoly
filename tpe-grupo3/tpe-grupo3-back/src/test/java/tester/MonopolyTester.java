package tester;

import com.company.BoardGame;
import com.company.Dice;
import com.company.OwnableBox;
import com.company.Property;
import org.junit.Test;

import org.junit.*;

public class MonopolyTester {

	static BoardGame g;


	@Before
	public void initAll()
	{
		//Inicializa un juego con 3 jugadores y $1500 cada uno
		g = new BoardGame();
		g.addPlayers(3, 2000);

	}

	@Test
	public void diceTest()
	{
		//Al tirar el dado por primera vez, no deberia moverse mas espacios que el maximo permitido por el dado
		g.movePlayer(g.nowPlaying());
		Assert.assertEquals(false, g.nowPlaying().getPosition() > Dice.MAX);
	}

	@Test
	public void skipPrisonerTest()
	{
		g.nowPlaying().setJailTime();
		//Pasa de P1 a P2
		g.nextTurn();
		//Pasa de P2 a P3
		g.nextTurn();
		//Pasa de P3 a P2, salteandose a P1 porque está preso
		g.nextTurn();
		//nowPlaying deberia estar en el indice 1 de la lista, es decir, ser el segundo jugador
		Assert.assertEquals(g.getPlayers().get(1), g.nowPlaying());
	}

	@Test
	public void BankrupcyTest()
	{
		g.nowPlaying().subtractMoney(2000);
		Assert.assertEquals(true, g.nowPlaying().isBroke());;
	}


	@Test
	public void jailExitTest()
	{
		g.getBoxes().get(30).OnLandedAction(g.nowPlaying()); //Cae en la carcel
		Assert.assertEquals(10, g.nowPlaying().getPosition());
	}

	@Test
	public void propertyBuyingTest()
	{
		//Los casilleros 1, 5 y 6 son casilleros OwnableBox
		OwnableBox prop1 = (OwnableBox) g.getBoxes().get(1);
		OwnableBox prop2 = (OwnableBox) g.getBoxes().get(5);
		OwnableBox prop3 = (OwnableBox) g.getBoxes().get(6);
		prop1.buyBox(g.nowPlaying());
		g.nextTurn(); //pasa a P2
		prop2.buyBox(g.nowPlaying());
		g.nextTurn(); //pasa a P3
		prop3.buyBox(g.nowPlaying());
		Assert.assertEquals(g.getPlayers().get(0), prop1.getOwner());
		Assert.assertEquals(g.getPlayers().get(1), prop2.getOwner());
		Assert.assertEquals(g.getPlayers().get(2), prop3.getOwner());
	}

	@Test
	public void trainRentTest()

	{
		OwnableBox prop1 = (OwnableBox) g.getBoxes().get(5);
		OwnableBox prop2 = (OwnableBox) g.getBoxes().get(15);
		OwnableBox prop3 = (OwnableBox) g.getBoxes().get(25);
		OwnableBox prop4 = (OwnableBox) g.getBoxes().get(35);

		prop1.buyBox(g.nowPlaying());
		Assert.assertEquals(2000 - prop1.getPrice(), g.nowPlaying().getMoney());
		Assert.assertEquals(25, prop1.getRentPrice());

		prop2.buyBox(g.nowPlaying());
		Assert.assertEquals(50, prop1.getRentPrice());
		Assert.assertEquals(50, prop2.getRentPrice());

		prop3.buyBox(g.nowPlaying());
		Assert.assertEquals(75, prop1.getRentPrice());
		Assert.assertEquals(75, prop2.getRentPrice());
		Assert.assertEquals(75, prop3.getRentPrice());

		prop4.buyBox(g.nowPlaying());
		Assert.assertEquals(100, prop1.getRentPrice());
		Assert.assertEquals(100, prop2.getRentPrice());
		Assert.assertEquals(100, prop3.getRentPrice());
		Assert.assertEquals(100, prop4.getRentPrice());
	}

	@Test
	public void MonopolyTest()
	{
		Property prop1 = (Property) g.getBoxes().get(1);
		prop1.buyBox(g.nowPlaying());

		Property prop2 = (Property) g.getBoxes().get(3);
		prop2.buyBox(g.nowPlaying());

		Assert.assertEquals(false, prop1.partOfMonopoly());
		Property prop3 = (Property) g.getBoxes().get(4);
		prop3.buyBox(g.nowPlaying());

		Property prop4 = (Property) g.getBoxes().get(6);
		prop4.buyBox(g.nowPlaying());
		Assert.assertEquals(true, prop1.partOfMonopoly());
	}

	@Test
	public void houseRentBoostTest()
	{
		Property prop1 = (Property) g.getBoxes().get(39);
		prop1.buyBox(g.nowPlaying());
		int aux = prop1.getRentPrice();
		prop1.buyHouse(g.nowPlaying());
		Assert.assertEquals(true, aux < prop1.getRentPrice());

		prop1.buyHouse(g.nowPlaying());
		Assert.assertEquals(true, aux < prop1.getRentPrice());

		prop1.buyHouse(g.nowPlaying());
		Assert.assertEquals(true, aux < prop1.getRentPrice());
	}
}
