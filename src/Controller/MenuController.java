package Controller;

import GUI.ModifyIngredientsGUI;
import Model.MenuItem;
import Model.Server;
import Model.Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
This class is responsible for handling button clicks in the MenuGUI
 */
public class MenuController {
    public static class OrderListener implements ActionListener{
        private MenuItem menuItem;
        private Server server;
        private Table table;

        public OrderListener(MenuItem menuItem, Server server, Table table) {
            this.menuItem = menuItem;
            this.server = server;
            this.table = table;
        }

        /**
         * clicking the order button opens a new window where the server can edit the ingredients
         * @param actionEvent
         */
        public void actionPerformed(ActionEvent actionEvent) {
            ModifyIngredientsGUI modifyIngredientsUI = new ModifyIngredientsGUI(menuItem.clone(), server, table);
        }
    }
}