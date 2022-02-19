#!/bin/sh

for i in * 
do
    if test -f "$i"
    then
       echo $i
       base="${i%.*}"
       echo $base.mp3
       ffmpeg -i $i $base.mp3
    fi
done

