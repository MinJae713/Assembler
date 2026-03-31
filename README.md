# Assembler
시스템 프로그래밍 기말 프로젝트 어셈블러 및 에뮬레이터 프로젝트입니다

# 테스트 방법
- source/test 참고!
- 코드는 아래와 같습니다 -> 아래를 어셈블리 코드로 넣으셈
```
.program test
  	nop
.header
	stack 1024
	heap 1024
.data
	sum 4
	average 4
	i 4
	max 4
.code
	move sum 0
	move average 0
	move i 0
	move max 10
	startLoop:
		compare i max
		ge exitLoop
		move r0 sum
		add r0 i
		move sum r0	
		move r1 i
		add r1 1
		move i r1
		jump startLoop
	exitLoop:
	halt
.end
```