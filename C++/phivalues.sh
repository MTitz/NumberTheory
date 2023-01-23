#!/bin/sh
path=${0%/*}
for i in 1 2 3 4 5 6 7 8 9
do
    echo "phivalues $i"
    time $path/phivalues $i > phivalues$i.txt
    echo "----------------------"
done > phivalues_times.txt 2>&1
