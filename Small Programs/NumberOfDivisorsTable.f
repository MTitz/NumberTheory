      PROGRAM DIVTBL
      PARAMETER (N=250000)
      INTEGER D(N)
      DO 10 I=1,N
   10 D(I)=0
      DO 20 J=1,INT(SQRT(DBLE(N)))
      IDX=J*J
      D(IDX)=D(IDX)+1
      DO 20 K=J+1,N/J
      IDX=IDX+J
   20 D(IDX)=D(IDX)+2
      PRINT 30,D
   30 FORMAT(10I5)
      END
