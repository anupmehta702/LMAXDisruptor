# LMAXDisruptor
Code to test lmax disruptor

Links -https://www.baeldung.com/lmax-disruptor-concurrency
https://www.infoq.com/presentations/LMAX/

Output -

Adding event -->ValueEvent{value=0, updatedBy='null'}
Adding event -->ValueEvent{value=1, updatedBy='null'}
Adding event -->ValueEvent{value=2, updatedBy='null'}
Adding event -->ValueEvent{value=3, updatedBy='null'}
Printing the event --> ValueEvent{value=0, updatedBy='null'} placed at sequence number --> 0 by --> firstConsumer
Printing the event --> ValueEvent{value=1, updatedBy='null'} placed at sequence number --> 1 by --> firstConsumer
Printing the event --> ValueEvent{value=2, updatedBy='null'} placed at sequence number --> 2 by --> firstConsumer
Printing the event --> ValueEvent{value=3, updatedBy='null'} placed at sequence number --> 3 by --> firstConsumer
Printing the event --> ValueEvent{value=0, updatedBy='null'} placed at sequence number --> 0 by --> secondConsumer
Printing the event --> ValueEvent{value=1, updatedBy='null'} placed at sequence number --> 1 by --> secondConsumer
Adding event -->ValueEvent{value=4, updatedBy='null'}
Printing the event --> ValueEvent{value=2, updatedBy='null'} placed at sequence number --> 2 by --> secondConsumer
Printing the event --> ValueEvent{value=3, updatedBy='null'} placed at sequence number --> 3 by --> secondConsumer
Adding event -->ValueEvent{value=5, updatedBy='null'}
Adding event -->ValueEvent{value=6, updatedBy='null'}
Printing the event --> ValueEvent{value=4, updatedBy='null'} placed at sequence number --> 4 by --> firstConsumer
Adding event -->ValueEvent{value=7, updatedBy='null'}
Printing the event --> ValueEvent{value=5, updatedBy='null'} placed at sequence number --> 5 by --> firstConsumer
Printing the event --> ValueEvent{value=4, updatedBy='null'} placed at sequence number --> 4 by --> secondConsumer
Printing the event --> ValueEvent{value=6, updatedBy='null'} placed at sequence number --> 6 by --> firstConsumer
Printing the event --> ValueEvent{value=5, updatedBy='null'} placed at sequence number --> 5 by --> secondConsumer
Printing the event --> ValueEvent{value=7, updatedBy='null'} placed at sequence number --> 7 by --> firstConsumer
Printing the event --> ValueEvent{value=6, updatedBy='null'} placed at sequence number --> 6 by --> secondConsumer
Printing the event --> ValueEvent{value=7, updatedBy='null'} placed at sequence number --> 7 by --> secondConsumer