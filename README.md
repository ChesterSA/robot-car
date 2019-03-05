# Robot Car Driver
Uses Clojure ops-search from github.com/cognesence to work out the route for a robot car to move stock around a warehouse

## Specification
The warehouse is split into zones with one car in each zone. Cars cannot move between
zones but it is possible to move stock from one zone to another because zones connect
to each other by Exchanges, shared areas where one car can leave stock for another
(note there is only room for one piece of stock in an exchange).

Each zone is laid out in a grid formation where Corridors run north-south or east-west
and connect to each other at Junctions. The corridors are full of Bays, the bays are
where stock is stored.
The stock is always longer than the width of the corridors so they can only
be carried along the corridors if correctly aligned, this means that cars sometimes
need to rotate stock at corridor junctions.

## Assumptions
* Bays are bi-directional
* Exchanges are bi-directional
* Cars have bi-directional movement
* Item rotates with car

## Operators
 ### collect-stock
 from the car's current bay/exchange (cars must already have travelled to the correct bay), note that cars can only hold one
stock item at a time
### deposit-stock
 put the stock at the bay/exchange where the car is
### move-to-bay
 move a car to a bay in its current corridor;
### move-to-junction
 move a car to a junction/exchange from its current corridor
### rotate-car
 rotate a car (and its stock) this can only happen at a junction


## Full world with 2 zones
![alt text](https://raw.githubusercontent.com/ChesterSA/robot-car/full-world-2-zones.png)
## Full world with 1 zone
![alt text](https://raw.githubusercontent.com/ChesterSA/robot-car/full-world.png)


## Worlds
### World 1
 A world which has 2 corridors connected by a junction, created to test the system at a small size
 ### World 2
 -A world which has 2 zones, 2 corridors split up by an exchange.
 -There is a robot in each zone to aid in moving between zones.
 -Stack overflows due to bigger world size.
 ### World 3
 -A world which has all of the elements shown in the image above.
 -There is a robot in each zone to aid in moving between zones.
 -Stack overflows due to bigger world size.



## Version 1
-The first app we solution we created.
-This solution has 7 operators. collect-stock, deposit-stock, move-to-bay, move-to-junction,
    move-to-corridor-from-bay, move-to-corridor-from-junction, rotate-car.
-Because of the amount of operators the time for operations is very high

## Version 2
-The second solution we created
-This solution has 6 operators. collect-stock, deposit-stock, move-to-bay, move-to-junction, move-to-corridor &
    rotate-car.
    -Because of the amount of operators the time for operations is not as high as version 1 but still high
## Version 3
-The final solution we created
-This solution had 5 operators. collect-stock, deposit-stock, move-to-bay, move-to-junction and rotate-car.
-Because we slimmed down the amount of operators the solution gained a big performance increase but due to  op-search limitations it was as optimised as possible.