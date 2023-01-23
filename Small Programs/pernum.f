      PROGRAM PERNUM
C Calculation of Perfect Numbers
C see: George E. Andrews "Number Theory", page 46
      INTEGER N,M,S
      DO 20 N=2,500
      S=0
      DO 10 M=1,N/2
      IF (MOD(N,M).EQ.0) S=S+M
   10 CONTINUE
      IF (N.EQ.S) PRINT *,N
   20 CONTINUE
      END
