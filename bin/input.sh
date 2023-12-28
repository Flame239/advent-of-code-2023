#!/bin/sh
if [ -z "$1" ]
  then
    echo "Provide day"
    exit 1
fi

if [ "$2" = "ex" ]; then
  aocd "$1" --example > "input/Day$1-ex.txt"
else
  aocd "$1" > "input/Day$1.txt"
fi

