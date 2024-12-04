import model.ThreeTriosModel;
import model.ThreeTriosModelImpl;
import model.grid.Grid;
import players.Player;
import players.PlayerImpl;
import view.GameGUI;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test class for the {@code GameGUI} to verify its initialization and components.
 */
public class GameGUITest {

  @Test
  public void testGameGUIInitialization() {
    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");
    Grid grid = new Grid(3, 3);
    ThreeTriosModel model = new ThreeTriosModelImpl(grid, redPlayer, bluePlayer);

    GameGUI gui = new GameGUI(model);

    assertNotNull(gui.getLeftPanel());
    assertNotNull(gui.getRightPanel());
    assertNotNull(gui.getGridPanel());
  }

  @Test
  public void testGridPanelCellCount() {
    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");
    Grid grid = new Grid(3, 3);
    ThreeTriosModel model = new ThreeTriosModelImpl(grid, redPlayer, bluePlayer);

    GameGUI gui = new GameGUI(model);
    JPanel gridPanel = gui.getGridPanel();

    // Check that the grid panel has the correct number of cells
    assertEquals(9, gridPanel.getComponentCount());
  }

  @Test
  public void testCellClickListener() {
    Player redPlayer = new PlayerImpl("RED");
    Player bluePlayer = new PlayerImpl("BLUE");
    Grid grid = new Grid(3, 3);
    ThreeTriosModel model = new ThreeTriosModelImpl(grid, redPlayer, bluePlayer);

    GameGUI gui = new GameGUI(model);
    JButton testButton = new JButton();
    ActionListener listener = gui.createCellClickListener(0, 0, testButton);

    assertNotNull(listener);
  }
}
