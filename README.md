***TLDR ON HOW TO USE THE PROGRAM***

To run the program, run src/Model/Restaurant.java

1. Login screen

There will be a login screen requiring an ID and a password, both of which are configurable in config.txt (the IDs
should follow the same convention as in phase 1) See below for more detail. The ID used to sign in will determine
which interface you see. Logging in as a server will open the server UI, logging in as a cook opens the cook UI,
and logging in as a manager opens the manager UI.

If you do not wish to configure passwords, then these are some IDs and passwords can be used to log in.

ID       Password         Opens UI for
s0       server           Server
s1       server           Server
c0       cook             Cook
c1       cook             Cook
m0       manager          Manager
m1       manager          Manager

2. Server Interface

Logging in as a server opens the server interface. Instructions for each action:

Creating an order: Click a table -> Click create order -> Select the item -> Make modifications if desired using +/-
                   -> Click confirm

Cancelling an order: Click cancel order -> Enter order number -> Enter reason for cancellation -> Confirm

Delivering an order: Click deliver next order -> Enter order number -> Click confirm -> Enter seat number(see below)
                     -> Click confirm.

Receive a shipment: Click receive shipment -> Enter ingredient name & amount -> Click confirm

Printing one bill for table: Click a table -> Click print bill -> Click confirm payment (to confirm)

Split bill for table: Click a table -> Click split bill -> Click confirm payment (to confirm), for each bill

3. Cook Interface

Logging in as a cook opens the cook interface. Instructions for each action:

Start an order: Enter order number -> Click start order

Finishing an order: Click finish order (automatically finishes the order that was started)

Receive a shipment: Click receive shipment -> Enter ingredient name & amount -> Click confirm

4. Manager Interface

Logging in as a manager opens the manager interface. Instructions for each action:

Check inventory: Click inventory printout

Check undelivered orders: Click undelivered orders

Check payment record: Click payment record

Receive a shipment: Click receive shipment -> Enter ingredient name & amount -> Click confirm

5. Notes on flow of orders, cancellations and send backs

The flow of orders is summarized below for brevity. For more details refer to phase 1 instructions below.

Created(i.e. Unseen) -> Started(i.e. Seen) -> Finished -> Delivered/Sent Back

Orders may be cancelled at any stage before being delivered.

Sending an order back sets the order's status back to unseen. The order retains its number and contents.
This means, for example, when order 5 is sent back, a cook must start and finish order 5 again before it
can be delivered.

---------------------------------------------------------------------
***CHANGES/NEW FEATURES FOR PHASE 2***
---------------------------------------------------------------------

1. Login system & passwords

There is now a password protected login system. Each staff has their own ID and password to log in to the
system. Passwords are initialized in config.txt. Each line in config.txt after line 1 will now be in the format:

Job, name, id, password

where the first 3 inputs follow the same convention as they did in phase 1. Passwords may contain any
alphanumeric characters, and is case sensitive.

Examples of valid lines in config.txt:

Server, Jay Z, s0, 420693573
Manager, Donald Trump, m0, AbCdEf99821
Cook, Gordon Ramsay, c0, wheresthelambsauce

Note that the system prevents multiple logins from the same employee (i.e. using the same ID)

2. Restrictions on server and cook behaviour.

As per the phase 2 requirements, there are now additional restrictions on what servers and cooks may do
in certain situations.

Servers cannot place an order for any table, if there are any orders currently waiting to be delivered.

Cooks may only work on one order at a time. That is if they start an order, they must finish it before
they are allowed to start another one.

Pop-up notifications exist in the program to notify the user should any of these restrictions, plus the
restrictions from phase 1, are violated.

3. Table capacity

Each individual table may now have a seating capacity associated with it. This is initialized through line
1 in config.txt. The line now has the form:

Tables: num1, num2, num3, num4, num5, num6....

Where each comma separated term is a positive integer denoting the number of seats at that table. The program
can handle anywhere from 1 to 15 tables.

Example: To initialize a restaurant with 5 tables, with 2 tables of capacity 4 and 3 tables of capacity 6:

Tables: 4, 4, 6, 6, 6

The order of the terms do not matter.

4. Delivering order to a seat

There is now the option of delivering an order to a specific seat at a table. In this program this
will appear as a prompt to enter a seat number after confirming successful delivery. Valid
seat numbers are integers from 0 to the table's seating capacity minus 1, inclusive.

For example, a table of capacity 6 will have seats with IDs 0 through 5, inclusive.

A notification will appear when entering an invalid seat number.

Successfully delivering an order to a seat will set its status to occupied, for the purposes of
printing the bill(s).

5. Bill splitting & gratuity

As per the phase 2 requirements, there is now the option of splitting the bill for a table, or print one
bill for the whole table. These options will appear when clicking on a table from the Server interface.
Splitting the bill will produce one bill on the screen for each customer. Printing the bill for the entire
table produces one.

Clicking 'confirm payment' on a bill will clear the seat's list of delivered orders and set its status back
to unoccupied, such that future bills created for this seat will not include old ones that have already been
paid.

If a table has 8 or more seats with delivered orders, then printing one bill or splitting the bill will
cause the program to automatically attach a 15% gratuity to the bill(s), as per the requirement.

Note that if the table has capacity >=8, less than 8 customers (i.e. less than 8 occupied seats), then
the mandatory gratuity will not be applied.

6. Manager features

The manager retains their ability of viewing the inventory at any time. As per the phase 2 requirements,
they are now able to see a list of all finished but undelivered orders (orders ready for delivery) and
the record of all payments made in an instance of the program.

**Phase 1**
----------------------------------------------------------------------
Contents
----------------------------------------------------------------------
1. Description of design
2. How to configure restaurant staff and tables through config.txt (SEE PHASE 2 INSTRUCTIONS FOR UPDATES)
3. How to configure restaurant's inventory through Inventory.txt
4. How to configure restaurant's menu through Menu.txt
5. How to simulate events through events.txt (OUTDATED)

----------------------------------------------------------------------
1. Design

For our restaurant design, we have an overlying restaurant class which also has the main method. The restaurant
has tables, employee, and a menu. For the purposes of our design, we treat tables as our only customers. We are
uninterested in the number of people at a table. There may be x number of people at a table, we just take orders
for the whole table, knowing that the order will be delivered to the table which there is theoretically people on.

An Order (class) has a single menuItem that represents the food item that was ordered.
This means that even if there are multiple people at a table, there is one order per menuItem so one person's
order at a table does not affect another person's order. The order for the whole table does not have to be filled
at once because again, the menu items are treated individually. Menu items are chosen from a Menu class which
constrains a list of MenuItems.

Any modification may be made to the item, long as the ingredient exists in the inventory. There is no limit
to the amount of modifications that may be made.

Order represents the object that is dealt with by the employee which consists of cooks and servers.
It is passed around accordingly to finish the order. Menu menuItem is associated with the ingredients and the actual
details of the food.

Any employee may as the receiver for incoming shipments. There is an updateInventory method in employee as
a result.

----------------------------------------------------------------------
2. config.txt

The amount of tables in the restaurant, as well as all of its employees, can be configured through config.txt.

The first line of config.txt sets the amount of tables this restaurant has. It has format "Table: number", where
number can be set to any positive integer value.

All subsequent lines of config.txt are used to configure the employees. Each line contains the settings required
to instantiate one employee. It has format "Job, Name, EmployeeID", where Job is one of Server, Cook, or Manager.
Name is any sequence of characters denoting the employee's name, and EmployeeID is any string denoting the employee's
ID.

**Naming convention of EmployeeID:

Although employeeID can be set to any string. The convention (which will make it easier to call events through
events.txt) is the following:

EmployeeID should begin with a single character, either s, c, or m denoting server, cook and manager respectively.
It should be followed directly by a natural number, increasing by 1 for each employee of the same job. Employees
should not have the same ID as another employee.

Example inputs for employees in config.txt:

Server, Jay Z, s0
Manager, Donald Trump, m0
Server, Usher, s1
Cook, Gordon Ramsay, c0

----------------------------------------------------------------------
3. Inventory.txt

This file represents the inventory of ingredients the restaurant has at the beginning. It holds all the
ingredients' names, stocks, and thresholds. The threshold is a value used when the stock of the ingredient is
less than the threshold value. When this occurs for an ingredient and that ingredient's stock is being subtracted,
a request for the default amount of 20 units is written into the requests.txt if it is not there already.
If a request is already there, the amount being requested will be added onto the current request.

Each line in the requests.txt file is formatted like so if there are requests for an ingredient:

requested_amount_here units of ingredient_name_here

If there are no requests then requests.txt file either does not exist (never previously created) or
is empty.

The format of the Inventory.txt is quite simple. Every line is used for one ingredient and is formatted like so:

ingredient_name:ingredient_stock:ingredient_threshold

where ingredient_name is a string, ingredient_stock and ingredient_threshold are integers.

For example these are valid lines in Inventory.txt:

potato:61:60
salt:30:20

----------------------------------------------------------------------
4. Menu.txt

This file holds the names, prices, and ingredients required for every item on the Menu. The prices are in cents and
the ingredients required is the default amount of ingredients required for that item before any additions/subtractions.
Also something to note is that every ingredient in Menu.txt must also have a stock and threshold in Inventory.txt.

Each line in Menu.txt holds the information for one item. Each line is formatted like so:

ingredient_name, ingredient_price, ingredient_1_name, ingredient_1_required, ingredient_2_name, ingredient_2_required

and so on if there are more ingredients. Things to note is that the price is in cents, and the ingredients required
are integer values. Also, there is no comma at the end of the line.

Here are valid sample lines:

fries, 200, potato, 1, salt, 2, vegetable oil, 1
beef burger, 500, bun, 2, lettuce, 1, mayonnaise, 1, cheese, 2, beef patty, 1, tomato, 1, bacon, 3

----------------------------------------------------------------------
5. events.txt

Events that may be simulated through events.txt include:
- Server taking an order from a table
- Cook confirming that an order has been seen and is being prepared
- Cook confirming that an order has been filled (i.e. cooked)
- Server confirming that an order has been delivered to a table successfully
- Server confirming that an order has been sent back by a table due to a specified reason
- Server confirming that an order has been cancelled by a table
- Server printing the bill of a table
- Manager checking the contents of the inventory
- Any employee scanning a shipment of ingredients into the inventory

Format of events.txt:
---------------------
**All input begins with the follow three arguments:

employeeID event eventID

where employeeID is the ID of the employee handling the event. It is always a lowercase character (either s, c or m,
denoting server, cook and manager respectively) followed by an integer. The valid IDs can be found in config.txt.
Attempting to call an event using an invalid employeeID will cause the program to throw an InvalidIDException.

Example of valid employeeID -> s0, c1, m12

**event is an underscore joined string denoting the type of event being simulated. All valid inputs for event,
in the order corresponding to the list at the beginning of this section:

- creates_order_from_table
- starts_order
- finishes_order
- delivers_order
- returns_order
- cancels_order
- prints_bill_from_table
- prints_inventory
- accepts_shipment

**Note that certain events may only be called by certain types of employees.

All employees may call:
- accepts_shipment

Only servers may call:
- creates_order_from_table
- delivers_order
- returns_order
- cancels_order
- prints_bill_from_table

Only cooks may call:
- starts_order
- finishes_order

Only managers may call:
- prints_inventory

**eventID is an integer specific to the event being called. Details:

For creates_order_from_table and prints_bill_from_table, eventID is the table number of the table the event refers to.
For starts_order, finishes_order, delivers_order, returns_order and cancels_order, eventID is the order number.
For prints_inventory and accepts_shipment, eventID is always -1, as there is no unique ID associated with these events.

**Regarding eventID for orders:

When creating order, order numbers are assigned automatically, starting from 0 and adding one for each new order.
This is how one knows what order number to use for event that makes cook starts order, for example.
As another example, if there were 3 orders created previously in events and new one is created at table 7,
then you know order at table 7 has order id 3.

Attempting to call an order-related method with an invalid order number (an order number which has no corresponding
order in the system, or an order which has been cancelled) will cause the program to throw an OrderNotFoundException.

**Inputs with additional arguments:

creates_order_from_table, returns_order and cancels_order require additional arguments beyond the first three.
Additional inputs are written after a whitespace followed by a colon followed by a whitespace,
after the first three arguments.

returns_order and cancels_order requires one additional argument, which is the reason why the order was
cancelled/returned. This argument may contain any characters.

Example -> s0 cancels_order 1 : tastes like cardboard

accepts_shipment must have one or more additional argument in the form: "ingredient, quantity", denoting the ingredient
received and the amount of that ingredient received, each entry set is separated by commas, and there is no comma
at the end of the line.

Example -> m0 accepts_shipment : cheese, 500
           m1 accepts_shipment : cheese, 500, salt, 1000, potato, 50

creates_order_from_table requires one additional argument, which is the item being ordered. Attempting to
order an item not on the menu will cause the program to throw NotOnMenuException. It may also take in optional arguments
which denote addition/subtraction of ingredients for this item. Each modification is denoted by a string of
the format: "ingredient, amount" indicating addition or: "ingredient, -amount" indicating subtraction.

Example -> s0 creates_order_from_table 1 : fries
        -> s0 creates_order_from_table 1 : pizza slice, kale, 11, cheese, -2, flour, 2

**Sequence of calling order-related inputs:

The intended flow of an order's status goes from created -> started -> finished -> delivered/returned, where the
order may be cancelled at any time after it has been created and before it has been delivered. Attempting to simulate
events out of this order (for example, creating an order then going straight to completing an order, skipping the
start step) will cause the console to print a message stating that this event cannot be executed, and the order
will remain as it was.

**Example of inputs with proper formatting for reference:

s0 creates_order_from_table 0 : fries
c0 starts_order 0
c0 finishes_order 1
s0 delivers_order 3
s3 cancels_order 0 : too slow
s0 returns_order 2 : too salty
s2 prints_bill_from_table 2
s0 creates_order_from_table 4 : fries, potato, 1, salt, -1
m1 accepts_shipment 1 : cheese, 520
m1 prints_inventory 4


