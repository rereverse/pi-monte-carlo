# pi-monte-carlo

Estimate PI, using Monte Carlo simulation

## Usage

#### Load Core in REPL
    Starting nREPL server...
    Loading src/pi_monte_carlo/core.clj... done
    
#### Approx PI
    (approx-pi 10)
    => 2.8
    (approx-pi 100)
    => 3.12
    (approx-pi 1000)
    => 3.128
    (approx-pi 10000)
    => 3.1324
    (approx-pi 100000)
    => 3.14608
    (approx-pi 1000000)
    => 3.14106
    (approx-pi 10000000)
    => 3.1408032

#### Sequence of Approx Pi
    (take 100 approx-pi-seq)
    =>
    (0.0
     2.0
     2.666666666666667
     3.0
     3.2
     3.333333333333333
     3.428571428571429
     3.5
     3.555555555555556
     ...
     ...
     
#### Run
    lein run
![alt text](https://github.com/rereverse/pi-monte-carlo/blob/master/resources/experiment.png "experiment.png")

#### License ####

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
