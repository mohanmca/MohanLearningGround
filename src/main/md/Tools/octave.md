octave:8> eye(3)(1:3,1:3)
ans =

Permutation Matrix

   1   0   0
   0   1   0
   0   0   1

%select 1st to second row, and 1st column to 2nd column
octave:9> eye(3)(1:2,1:2)
ans =

Diagonal Matrix

   1   0
   0   1
   
%select 1st and third row, and 1st column and 3rd column   
octave:9> eye(3)([1,3],[1,3])

octave:25> A = [[3:16];[3:16];[3:16];[3:16];[3:16]]
ans =

    3    4    5    6    7    8    9   10   11   12   13   14   15   16
    3    4    5    6    7    8    9   10   11   12   13   14   15   16
    3    4    5    6    7    8    9   10   11   12   13   14   15   16
    3    4    5    6    7    8    9   10   11   12   13   14   15   16
    3    4    5    6    7    8    9   10   11   12   13   14   15   16

octave:26> [[3:16];[3:16];[3:16];[3:16];[3:16]]([1:3:5],[1:3:8])
ans =

   3   6   9
   3   6   9
   
octave:25> A'

octave:28> a'
ans =

    3    3    3    3    3
    4    4    4    4    4
    5    5    5    5    5
    6    6    6    6    6
    7    7    7    7    7
    8    8    8    8    8
    9    9    9    9    9
   10   10   10   10   10
   11   11   11   11   11
   12   12   12   12   12
   13   13   13   13   13
   14   14   14   14   14
   15   15   15   15   15
   16   16   16   16   16

~~~

octave:8> x
x =

   1   2
   2   3

octave:9> y
y =

   1   2

octave:10> x*y'
ans =

   5
   8

~~~


octave:3> function [a,b] = sample()
 a=1
 b=2
end

octave:14> [a,b]=sample()
