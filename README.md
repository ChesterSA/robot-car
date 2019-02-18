# Robot Car Driver
Uses Clojure ops-search from github.com/cognesence to work out the route for a robot car to move stock around a warehouse

## Specification
The warehouse is split into zones with one car in each zone. Cars cannot move between
zones but it is possible to move stock from one zone to another because zones connect
to each other by Exchanges – shared areas where one car can leave stock for another
(note there is only room for one piece of stock in an exchange).

Each zone is laid out in a grid formation where Corridors run north-south or east-west
and connect to each other at Junctions. The corridors are full of Bays – the bays are
where stock is stored. 
The stock is always longer than the width of the corridors so they can only
be carried along the corridors if correctly aligned, this means that cars sometimes
need to rotate stock at corridor junctions.

Operators
- collect-stock - from the car's current bay/exchange (cars must already have
travelled to the correct bay), note that cars can only hold one
stock item at a time (you may split this into 2 operators if you
wish);
- deposit-stock - put the stock at the bay/exchange where the car is (you may
split this into 2 operators if you wish);
- move-to-bay - move a car to a bay in its current corridor;
- move-to-junction - move a car to a junction/exchange from its current corridor
(you may combine this with other move operators if you wish)
- rotate-car - rotate a car (and its stock) this can only happen at a junction
You will also need to specify world knowledge (static/unchanging tuples) to capture
details about which junctions connect to which corridors, where the bays are and the
location of exchanges.